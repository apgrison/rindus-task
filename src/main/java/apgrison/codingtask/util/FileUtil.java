package apgrison.codingtask.util;

import apgrison.codingtask.model.*;
import static apgrison.codingtask.model.FileLocator.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;

public class FileUtil {

    public static boolean fileIsStale(FileInfo fileInfo) {
        Duration timePast = Duration.between(fileInfo.getCreatedAt(), Instant.now());
        Duration timeLeft = fileInfo.getTimeToLive().minus(timePast);
        return timeLeft.isNegative();
    }

    public static String getObjectName(Class<?> type) {
        return type.getSimpleName().replace("[]", "List");
    }

    public static String getFileName(Class<?> type, FileFormat fileFormat) {
        return getObjectName(type) + fileFormat.extension();
    }

    public static String fileToString(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()));
    }

}
