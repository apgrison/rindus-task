package apgrison.codingtask.repository;

import apgrison.codingtask.model.FileFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static apgrison.codingtask.util.MapperUtil.*;

@Repository
public class FileRepositoryImpl implements FileRepository {

    private static Logger log = LoggerFactory.getLogger(FileRepositoryImpl.class);

    @Override
    public String saveToFile(Object data, String fileName, FileFormat fileFormat) throws IOException {

        // Create the file
        String dir = FileRepositoryImpl.class.getResource("/").getPath();
        Path file = Paths.get(dir + fileName);
        Files.createDirectories(file.getParent());

        // Write the file's content
        if (fileFormat.equals(FileFormat.XML)) {
            log.debug("writing object to file as xml");
            Files.write(file, toXml(data).getBytes());
        } else {
            log.debug("writing object to file as json");
            Files.write(file, toJson(data).getBytes());
        }

        // Return the file's location
        String location = file.toAbsolutePath().toString();
        log.debug(fileName + " saved to " + location);

        return location;
    }

    @Override
    public File retrieveFile(String absolutePath) {
        log.debug("Retrieving file located at " + absolutePath);
        Path path = Paths.get(absolutePath);
        return path.toFile();
    }

}
