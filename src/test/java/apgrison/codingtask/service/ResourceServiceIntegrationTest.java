package apgrison.codingtask.service;

import apgrison.codingtask.model.Resource;
import apgrison.codingtask.model.Todo;
import apgrison.codingtask.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import static org.assertj.core.api.Assertions.*;

/**
 * This integration test suite makes sure the resource service is returning expected objects and http statuses
 */
@SpringBootTest
public class ResourceServiceIntegrationTest extends ResourceServiceImpl {

    @Test
    public void givenACreateUserRequest_whenResourceAndObjectAreNull_thenReturnNotFound() {
        Object object = this.create(null, null);
        assertThat(object).isInstanceOf(ResponseEntity.class);
        assertThat(((ResponseEntity<?>)object).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void givenANestedCreateTodoRequest_whenCorrect_thenReturnCreatedTodo() {
        Object object = this.createNested(Resource.USERS, 1, Resource.TODOS, new Todo());
        assertThat(object).isInstanceOf(Todo.class);
        assertThat(((Todo)object).getId()).isEqualTo(201);
    }

    @Test
    public void givenACreateUserRequest_whenObjectIsNull_thenAnEmptyUserIsCreatedAndReturned() {
        Object object = this.create(Resource.USERS, null);
        assertThat(object).isInstanceOf(User.class);
        assertThat(((User)object).getId()).isEqualTo(11);
    }

    @Test
    public void givenAReplaceUserRequest_whenResourceIsNull_thenReturnNotFound() {
        Object object = this.replace(null, new User(), 1);
        assertThat(object).isInstanceOf(ResponseEntity.class);
        assertThat(((ResponseEntity<?>)object).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void givenAGetAllCall_whenPassingAUserListResource_thenAUserArrayIsReturned() {
        User[] users = (User[]) this.getAll(Resource.USERS_LIST);
        assertThat(users.length).isGreaterThan(0);
    }


}
