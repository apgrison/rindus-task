package apgrison.codingtask.service;

import apgrison.codingtask.model.FileFormat;
import apgrison.codingtask.model.FileLocator;
import apgrison.codingtask.model.Resource;
import apgrison.codingtask.repository.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import static apgrison.codingtask.util.FileUtil.*;
import static apgrison.codingtask.model.FileLocator.*;
import static apgrison.codingtask.util.MapperUtil.jsonToObject;
import static apgrison.codingtask.util.MapperUtil.xmlToObject;

@Service
public class DataStorageServiceImpl implements DataStorageService {

    private static Logger log = LoggerFactory.getLogger(DataStorageServiceImpl.class);

    @Value("${data.storage.file.timeToLiveMinutes}")
    private long timeToLiveMinutes;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileLocator fileLocator;

    @Override
    public void storeData(Resource resource, FileFormat fileFormat) throws IOException {

        String fileName = getFileName(resource.getType(), fileFormat);

        FileInfo fileInfo = fileLocator.getFileInfo(fileName);
        if (fileInfo != null && !fileIsStale(fileInfo)) {
            log.debug("file is still valid, returning");
            return;
        }

        log.debug("file's TTL is past or file does not exist");
        refreshFile(fileFormat, resource);
    }

    @Override
    public Object retrieveData(Resource resource) throws IOException {

        FileFormat fileFormat = FileFormat.JSON;

        String fileName = getFileName(resource.getType(), fileFormat);

        FileInfo fileInfo = fileLocator.getFileInfo(fileName);
        if (fileInfo != null && !fileIsStale(fileInfo)) {
            log.debug("file is still valid, returning data from stored file");
            File file = fileRepository.retrieveFile(fileInfo.getLocation());
            return parseFile(resource.getType(), file, fileFormat);
        }

        log.debug("file locator did not return a valid {} file", fileName);

        return refreshFile(fileFormat, resource);
    }

    private Object refreshFile(FileFormat fileFormat, Resource resource) throws IOException {

        log.debug("fetching the {} data from the resource service", getObjectName(resource.getType()));
        Object data = resourceService.getAll(resource);

        String fileName = getFileName(resource.getType(), fileFormat);

        log.debug("sending data to file repository");
        String location = fileRepository.saveToFile(data, fileName, fileFormat);

        log.debug("adding file location to fileLocator ({})", location);
        fileLocator.addFileInfo(fileName, new FileInfo(Duration.ofMinutes(timeToLiveMinutes), location));

        return data;
    }

    private <T> Object parseFile(Class<T> type, File file, FileFormat fileFormat) throws IOException {

        if (fileFormat.equals(FileFormat.XML)) {
            log.debug("converting xml file content to object");
            return xmlToObject(new String(Files.readAllBytes(file.toPath())), type);
        } else {
            log.debug("converting json file content to object");
            return jsonToObject(new String(Files.readAllBytes(file.toPath())), type);
        }

    }
}
