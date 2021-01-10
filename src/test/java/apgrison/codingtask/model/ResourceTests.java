package apgrison.codingtask.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceTests {

    @Test
    public void givenAPath_whenPathExists_thenResourceIsReturned() {
        Resource resource = Resource.getByPath("users", true);
        assertUserListResource(resource);
    }

    @Test
    public void givenAResourceRoutePath_whenPathExists_thenResourceIsReturned() {
        Resource resource = Resource.getByPath(ResourceRoute.USERS.path(), true);
        assertUserListResource(resource);
    }

    public void assertUserListResource(Resource resource) {
        assertThat(resource.getPath()).isEqualTo("users");
        assertThat(resource.isList()).isEqualTo(true);
        assertThat(resource.getType()).isEqualTo(User[].class);
        assertThat(resource.getEndpoint()).isEqualTo("https://jsonplaceholder.typicode.com/users");
    }

    @Test
    public void givenAPath_whenPathDoesNotExists_then() {
        Resource resource = Resource.getByPath("user", true);
        assertThat(resource).isEqualTo(null);
    }

    @Test
    public void givenAUsersPath_whenListIsFalse_thenResourceIsUsers() {
        Resource actual = Resource.getByPath("users", false);
        Resource expectedResource = Resource.USERS;
        assertThat(expectedResource).isEqualTo(actual);
    }

    @Test
    public void givenAUsersPath_whenListIsTrue_thenResourceIsUsersList() {
        Resource actual = Resource.getByPath("users", true);
        Resource expectedResource = Resource.USERS_LIST;
        assertThat(expectedResource).isEqualTo(actual);
    }

    @Test
    public void givenATodosPath_whenListIsFalse_thenResourceIsTodosList() {
        Resource actual = Resource.getByPath("todos", false);
        Resource expectedResource = Resource.TODOS;
        assertThat(expectedResource).isEqualTo(actual);
    }

    @Test
    public void givenATodosPath_whenListIsTrue_thenResourceIsTodosList() {
        Resource actual = Resource.getByPath("todos", true);
        Resource expectedResource = Resource.TODOS_LIST;
        assertThat(expectedResource).isEqualTo(actual);
    }

    @Test
    public void givenAPhotosPath_whenListIsFalse_thenResourceIsPhotosList() {
        Resource actual = Resource.getByPath("photos");
        Resource expectedResource = Resource.PHOTOS;
        assertThat(expectedResource).isEqualTo(actual);
    }

    @Test
    public void givenAPhotosPath_whenListIsTrue_thenResourceIsPhotosList() {
        Resource actual = Resource.getByPath("photos", true);
        Resource expectedResource = Resource.PHOTOS_LIST;
        assertThat(expectedResource).isEqualTo(actual);
    }

    @Test
    public void givenTheGetListResourcesMethod_whenIteratingOnEachResource_thenIsListIsTrue() {
        for (Resource r : Resource.getListResources()) {
            assertThat(r.isList()).isTrue();
        }
    }

}
