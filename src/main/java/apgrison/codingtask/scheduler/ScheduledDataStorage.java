package apgrison.codingtask.scheduler;

import java.io.IOException;

public interface ScheduledDataStorage {

    void storeData() throws IOException;
    void retrieveData() throws IOException;

}
