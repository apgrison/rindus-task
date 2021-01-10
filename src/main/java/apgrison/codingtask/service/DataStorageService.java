package apgrison.codingtask.service;

import apgrison.codingtask.model.FileFormat;
import apgrison.codingtask.model.Resource;

import java.io.IOException;

/**
 * The data storage service allows to store and retrieve data
 */
public interface DataStorageService {
    /**
     * Store resource data in the given file format
     * @param resource The resource to be stored
     * @param fileFormat The enum representing the file's format (json or xml)
     */
    void storeData(Resource resource, FileFormat fileFormat) throws IOException;

    /**
     * Parse a saved file and return the data as an object
     * @param resource The resource to be retrieved
     * @return The object retrieved from the store
     */
    Object retrieveData(Resource resource) throws IOException;
}
