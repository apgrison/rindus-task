package apgrison.codingtask.util;

import apgrison.codingtask.model.FileFormat;
import apgrison.codingtask.model.FileLocator;
import apgrison.codingtask.model.Resource;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FileNameTests extends FileUtil {

    @Test
    public void givenAResourceTypeAndFormat_whenGetFileNameIsCalled_thenReturnExpectedFileName() {
        String actual = getFileName(Resource.USERS_LIST.getType(), FileFormat.XML);
        assertThat(actual).isEqualTo("UserList.xml");

        actual = getFileName(Resource.USERS_LIST.getType(), FileFormat.JSON);
        assertThat(actual).isEqualTo("UserList.json");

        actual = getFileName(Resource.TODOS_LIST.getType(), FileFormat.XML);
        assertThat(actual).isEqualTo("TodoList.xml");

        actual = getFileName(Resource.TODOS_LIST.getType(), FileFormat.JSON);
        assertThat(actual).isEqualTo("TodoList.json");
    }

}
