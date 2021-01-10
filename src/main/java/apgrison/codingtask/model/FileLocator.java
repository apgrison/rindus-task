package apgrison.codingtask.model;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class FileLocator {

    private static Map<String, FileInfo> fileLocator;

    static {
        fileLocator = new HashMap<>();
    }

    public static class FileInfo {
        private Duration timeToLive;
        private Instant createdAt;
        private String location;

        public FileInfo(Duration timeToLive, String location) {
            this.timeToLive = timeToLive;
            this.location = location;
            this.createdAt = Instant.now();
        }

        public FileInfo(Duration timeToLive, String location, Instant createdAt) {
            this.timeToLive = timeToLive;
            this.location = location;
            this.createdAt = createdAt;
        }

        public Duration getTimeToLive() {
            return timeToLive;
        }

        public Instant getCreatedAt() {
            return createdAt;
        }

        public String getLocation() {
            return location;
        }
    }

    public FileInfo getFileInfo(String fileName) {
        return fileLocator.get(fileName);
    }

    public void addFileInfo(String fileName, FileInfo fileInfo) {
        fileLocator.put(fileName, fileInfo);
    }

}
