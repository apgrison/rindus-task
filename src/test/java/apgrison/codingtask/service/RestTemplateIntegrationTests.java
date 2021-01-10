package apgrison.codingtask.service;

import apgrison.codingtask.exception.NotFoundException;
import apgrison.codingtask.model.Todo;
import apgrison.codingtask.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This test suite checks that our configured RestTemplate throws our custom exceptions and supports all http methods
 */
@SpringBootTest
public class RestTemplateIntegrationTests {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private HttpHeaders headers;

    @Test
    public void  givenAGetRequest_whenUrlIsIncorrect_thenThrowNotFoundException() {
        assertThrows(NotFoundException.class, () -> {
            restTemplate.getForObject("https://jsonplaceholder.typicode.com/user", User[].class);
        });
    }

    @Test
    public void  givenAPostRequest_whenUrlIsIncorrect_thenThrowNotFoundException() {
        assertThrows(NotFoundException.class, () -> {
            User user = new User();
            user.setId(1);
            HttpEntity<Object> entity = new HttpEntity<>(user, headers);
            restTemplate.postForObject("https://jsonplaceholder.typicode.com/users/1", entity, User.class);
        });
    }

    @Test
    public void givenANewTodo_whenPosted_thenTodoIsReturnedWithNewId() {
        Todo todo = new Todo();
        todo.setUserId(1);
        todo.setCompleted(false);
        todo.setTitle("nec plus ultra");

        HttpEntity<Object> entity = new HttpEntity<>(todo, this.headers);
        ResponseEntity<?> responseEntity = restTemplate.exchange("https://jsonplaceholder.typicode.com/todos", HttpMethod.POST, entity, Todo.class);
        Todo returnedUser = (Todo) responseEntity.getBody();
        assert returnedUser != null;
        assertThat(returnedUser.getTitle()).isEqualTo("nec plus ultra");
        assertThat(returnedUser.getId()).isInstanceOf(Integer.class);
    }

    @Test
    public void givenAnExistingTodo_whenReplacedWithPut_thenReplacedTodoIsReturned() {
        Todo todo = new Todo();
        todo.setId(1);
        todo.setUserId(1);
        todo.setCompleted(false);
        todo.setTitle("nec plus ultra");

        HttpEntity<Object> entity = new HttpEntity<>(todo, this.headers);
        ResponseEntity<?> responseEntity = restTemplate.exchange("https://jsonplaceholder.typicode.com/todos/1", HttpMethod.PUT, entity, Todo.class);
        Todo returnedUser = (Todo) responseEntity.getBody();
        assert returnedUser != null;
        assertThat(returnedUser.getTitle()).isEqualTo("nec plus ultra");
        assertThat(returnedUser.getId()).isEqualTo(1);
    }

    @Test
    public void givenAnExistingTodo_whenPatched_thenPatchedTodoIsReturned() {
        Todo todo = new Todo();
        todo.setId(1);
        todo.setUserId(3);

        HttpEntity<Object> entity = new HttpEntity<>(todo, this.headers);
        ResponseEntity<?> responseEntity = restTemplate.exchange("https://jsonplaceholder.typicode.com/todos/1", HttpMethod.PATCH, entity, Todo.class);
        Todo returnedUser = (Todo) responseEntity.getBody();
        assert returnedUser != null;
        assertThat(returnedUser.getTitle()).isEqualTo("delectus aut autem");
        assertThat(returnedUser.getUserId()).isEqualTo(3);
    }

}
