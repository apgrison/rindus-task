package apgrison.codingtask.util;

import apgrison.codingtask.model.FileLocator;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FileTimeToLiveTests extends FileUtil {

    @Test
    public void givenAFileInfo_whenNewlyInstantiated_thenIsNotStale() {
        boolean isStale = fileIsStale(new FileLocator.FileInfo(Duration.ofMinutes(60), "irrelevant"));
        assertThat(isStale).isFalse();

        isStale = fileIsStale(new FileLocator.FileInfo(Duration.ofMinutes(60), "irrelevant", Instant.now()));
        assertThat(isStale).isFalse();
    }

    @Test
    public void givenAFileInfo_whenCreatedAtIsGreaterThanTimeToLive_thenIsStale() {
        Instant createdAt = Instant.now().minus(61, ChronoUnit.MINUTES);
        boolean isStale = fileIsStale(new FileLocator.FileInfo(Duration.ofMinutes(60), "irrelevant", createdAt));
        assertThat(isStale).isTrue();
    }

}
