package apgrison.codingtask.repository;

import apgrison.codingtask.model.FileFormat;

import java.io.File;
import java.io.IOException;

/**
 * The file repository allows writing data to a file in the given format and retrieving a file.
 */
public interface FileRepository {
    /**
     * Create a file and write the object in the given format (json or xml)
     * @param data The data that will be written to the file
     * @param fileName The name of the file without any path or extension
     * @param fileFormat The file format enum (json or xml)
     * @return The file's absolute path
     */
    String saveToFile(Object data, String fileName, FileFormat fileFormat) throws IOException;

    /**
     * Retrieve a file from its absolute path
     * @param absolutePath The file's location (absolute path returned by the saveToFile method)
     * @return The actual file
     */
    File retrieveFile(String absolutePath);
}
