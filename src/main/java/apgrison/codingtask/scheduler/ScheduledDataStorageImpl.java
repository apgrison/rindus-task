package apgrison.codingtask.scheduler;

import apgrison.codingtask.model.FileFormat;
import apgrison.codingtask.model.Resource;
import apgrison.codingtask.service.DataStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.IOException;
import static apgrison.codingtask.util.FileUtil.getObjectName;
import static apgrison.codingtask.util.MapperUtil.*;

@ConditionalOnProperty(value = "data.storage.scheduler.enabled", havingValue = "true", matchIfMissing = true)
@Component
public class ScheduledDataStorageImpl implements ScheduledDataStorage {

    private static final Logger log = LoggerFactory.getLogger(ScheduledDataStorageImpl.class);

    @Autowired
    private DataStorageService dataStorageService;

    @Scheduled(fixedDelayString = "${data.storage.scheduler.store.delayMillis}",
             initialDelayString = "${data.storage.scheduler.store.initialDelayMillis}")
    @Override
    public void storeData() throws IOException {
        for (Resource resource : Resource.getListResources()) {
            for (FileFormat fileFormat : FileFormat.values()) {
                log.info("Storing {} data to {} file", getObjectName(resource.getType()), fileFormat.name());
                dataStorageService.storeData(resource, fileFormat);
            }
        }
    }

    @Scheduled(fixedDelayString = "${data.storage.scheduler.retrieve.delayMillis}",
             initialDelayString = "${data.storage.scheduler.retrieve.initialDelayMillis}")
    @Override
    public void retrieveData() throws IOException {
        for (Resource resource : Resource.getListResources()) {
            log.info("Processing the {} data", getObjectName(resource.getType()));
            Object object = dataStorageService.retrieveData(resource);
            log.debug(toJson(object));
            // TODO: do something with the data
        }
    }

}
