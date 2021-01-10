package apgrison.codingtask.util;

import apgrison.codingtask.model.*;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ObjectNameTests extends FileUtil {

    @Test
    public void givenAType_whenArray_thenNameHasListSuffix() {
        String actualName = getObjectName(User[].class);
        assertThat(actualName).isEqualTo("UserList");

        actualName = getObjectName(Photo[].class);
        assertThat(actualName).isEqualTo("PhotoList");

        actualName = getObjectName(Todo[].class);
        assertThat(actualName).isEqualTo("TodoList");

        actualName = getObjectName(Album[].class);
        assertThat(actualName).isEqualTo("AlbumList");

        actualName = getObjectName(Comment[].class);
        assertThat(actualName).isEqualTo("CommentList");

        actualName = getObjectName(Post[].class);
        assertThat(actualName).isEqualTo("PostList");
    }
}
