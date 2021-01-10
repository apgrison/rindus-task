package apgrison.codingtask.repository;

import apgrison.codingtask.model.FileLocator;
import static apgrison.codingtask.model.FileLocator.FileInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.Instant;

public class FileLocatorTest {

    private FileLocator fileLocator;

    @BeforeEach
    public void init() {
        fileLocator = new FileLocator();
    }

    @Test
    public void givenFileInfoAdded_whenRetrieved_thenCorrect() {

        String fileName = "UserList.json";
        Duration timeToLive = Duration.ofMinutes(60);
        String location = "/data/UserList.json";
        Instant createdAt = Instant.now();

        fileLocator.addFileInfo(fileName, new FileInfo(timeToLive, location, createdAt));
        FileInfo fileInfo = fileLocator.getFileInfo(fileName);

        assertThat(fileInfo.getTimeToLive()).isEqualTo(timeToLive);
        assertThat(fileInfo.getLocation()).isEqualTo(location);
        assertThat(fileInfo.getCreatedAt()).isEqualTo(createdAt);

    }

}
