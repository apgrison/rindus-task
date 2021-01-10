package apgrison.codingtask.controller;

import apgrison.codingtask.model.Todo;
import apgrison.codingtask.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This integration test suite calls our REST API and checks for expected objects and HttpStatuses
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResourceControllerIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private HttpHeaders headers;

    private TestRestTemplate rest;

    @BeforeEach
    public void instantiateTestRestTemplate() {
        rest = new TestRestTemplate();
    }

    @Test
    public void givenAnExistingUserId_whenGetPathIsCorrect_thenReturnUser() {
        ResponseEntity<User> entity = rest.getForEntity("http://localhost:" + port + "/users/1", User.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isInstanceOf(User.class);
    }

    @Test
    public void givenACorrectUsersPath_whenRequestedObjectIsUserArray_thenReturnUserArray() {
        ResponseEntity<User[]> entity = rest.getForEntity("http://localhost:" + port + "/users", User[].class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isInstanceOf(User[].class);
    }

    @Test
    public void givenACreateUserRequest_whenCalledCorrectly_thenReturnCreatedUser() {
        HttpEntity<User> httpEntity = new HttpEntity<>(new User(), headers);
        ResponseEntity<User> entity = rest.postForEntity("http://localhost:" + port + "/users", httpEntity, User.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(entity.getBody()).isInstanceOf(User.class);
    }

    @Test
    public void givenANestedCreateTodoRequest_whenCalledCorrectly_thenReturnCreatedTodo() {
        Todo todo = new Todo();
        todo.setUserId(1);
        todo.setTitle("Fix Car");
        todo.setCompleted(false);

        HttpEntity<Todo> httpEntity = new HttpEntity<>(todo, headers);
        ResponseEntity<Todo> entity = rest.postForEntity("http://localhost:" + port + "/users/1/todos", httpEntity, Todo.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(entity.getBody()).isInstanceOf(Todo.class);
        assertThat(Objects.requireNonNull(entity.getBody()).getTitle()).isEqualTo("Fix Car");
    }

    @Test
    public void givenAPatchUserRequest_whenCalledCorrectly_thenReturnPatchedUser() {
        User user = new User();
        user.setId(1);
        user.setName("New Name");

        HttpEntity<User> httpEntity = new HttpEntity<>(user, headers);
        ResponseEntity<User> entity = rest.exchange("http://localhost:" + port + "/users/1", HttpMethod.PATCH, httpEntity, User.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isInstanceOf(User.class);
        assertThat(Objects.requireNonNull(entity.getBody()).getName()).isEqualTo("New Name");
    }

    @Test
    public void givenAReplaceTodoRequest_whenCalledCorrectly_thenReturnReplacedTodo() {
        Todo todo = new Todo();
        todo.setId(1);
        todo.setUserId(1);
        todo.setTitle("Updated Title");
        todo.setCompleted(true);

        HttpEntity<Todo> httpEntity = new HttpEntity<>(todo, headers);
        ResponseEntity<Todo> entity = rest.exchange("http://localhost:" + port + "/todos/1", HttpMethod.PUT, httpEntity, Todo.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isInstanceOf(Todo.class);
        assertThat(Objects.requireNonNull(entity.getBody()).getTitle()).isEqualTo("Updated Title");
    }

    @Test
    public void givenAPatchUserRequest_whenCalledWithoutId_thenReturnMethodNotAllowed() {
        HttpEntity<User> httpEntity = new HttpEntity<>(new User(), headers);
        ResponseEntity<User> entity = rest.exchange("http://localhost:" + port + "/users", HttpMethod.PATCH, httpEntity, User.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Test
    public void givenAPatchUserRequest_whenCalledWithoutObject_thenReturnBadRequest() {
        HttpEntity<User> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<User> entity = rest.exchange("http://localhost:" + port + "/users/1", HttpMethod.PATCH, httpEntity, User.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void givenAnIncorrectPath_whenCalledViaGet_thenReturnNotFound() {
        ResponseEntity<User> entity = rest.getForEntity("http://localhost:" + port + "/user/1", User.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
