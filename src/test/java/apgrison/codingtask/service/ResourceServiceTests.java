package apgrison.codingtask.service;

import apgrison.codingtask.exception.ClientException;
import apgrison.codingtask.exception.InternalException;
import apgrison.codingtask.exception.NotFoundException;
import apgrison.codingtask.model.Resource;
import apgrison.codingtask.model.Todo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ResourceServiceTests {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ResourceService resourceService = new ResourceServiceImpl();

    @Test
    public void givenAGetRequest_whenRestTemplateReturnsExpectedObject_thenReturnThatObject() {
        Todo todo = new Todo();
        todo.setTitle("Test Todo");

        Mockito.when(restTemplate.getForObject("https://jsonplaceholder.typicode.com/todos/1", Todo.class))
                .thenReturn(todo);
        Object object = resourceService.get(Resource.TODOS, 1);
        assertThat(object).isInstanceOf(Todo.class);
        assertThat(((Todo)object).getTitle()).isEqualTo("Test Todo");
    }

    @Test
    public void givenAGetRequest_whenRestTemplateThrowsNotFoundException_thenReturnNotFound() {
        Mockito.when(restTemplate.getForObject("https://jsonplaceholder.typicode.com/todos/1", Todo.class))
                .thenThrow(new NotFoundException());
        Object object = resourceService.get(Resource.TODOS, 1);
        assertThat(object).isInstanceOf(ResponseEntity.class);
        assertThat(((ResponseEntity<?>)object).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void givenAGetRequest_whenRestTemplateThrowsClientException_thenReturnBadRequest() {
        Mockito.when(restTemplate.getForObject("https://jsonplaceholder.typicode.com/todos/1", Todo.class))
                .thenThrow(new ClientException());
        Object object = resourceService.get(Resource.TODOS, 1);
        assertThat(object).isInstanceOf(ResponseEntity.class);
        assertThat(((ResponseEntity<?>)object).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void givenAGetRequest_whenRestTemplateThrowsInternalException_thenReturnInternalServerError() {
        Mockito.when(restTemplate.getForObject("https://jsonplaceholder.typicode.com/todos/1", Todo.class))
                .thenThrow(new InternalException());
        Object object = resourceService.get(Resource.TODOS, 1);
        assertThat(object).isInstanceOf(ResponseEntity.class);
        assertThat(((ResponseEntity<?>)object).getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void givenAGetRequest_whenRestTemplateThrowsNullPointerException_thenReturnNotFound() {
        Mockito.when(restTemplate.getForObject("https://jsonplaceholder.typicode.com/todos/1", Todo.class))
                .thenThrow(new NullPointerException());
        Object object = resourceService.get(Resource.TODOS, 1);
        assertThat(object).isInstanceOf(ResponseEntity.class);
        assertThat(((ResponseEntity<?>)object).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
