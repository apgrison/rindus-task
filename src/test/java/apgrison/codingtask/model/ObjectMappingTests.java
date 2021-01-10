package apgrison.codingtask.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import static apgrison.codingtask.util.MapperUtil.jsonToObject;
import static org.assertj.core.api.Assertions.assertThat;

public class ObjectMappingTests {

    @Test
    public void givenADownloadedUser_whenParsed_thenAllFieldsAreProcessedCorrectly() throws JsonProcessingException {
        User user = jsonToObject(SampleData.user, User.class);

        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getName()).isEqualTo("Leanne Graham");
        assertThat(user.getUsername()).isEqualTo("Bret");
        assertThat(user.getEmail()).isEqualTo("Sincere@april.biz");
        assertThat(user.getAddress().getStreet()).isEqualTo("Kulas Light");
        assertThat(user.getAddress().getSuite()).isEqualTo("Apt. 556");
        assertThat(user.getAddress().getCity()).isEqualTo("Gwenborough");
        assertThat(user.getAddress().getZipcode()).isEqualTo("92998-3874");
        assertThat(user.getAddress().getGeo().getLat()).isEqualTo("-37.3159");
        assertThat(user.getAddress().getGeo().getLng()).isEqualTo("81.1496");
        assertThat(user.getPhone()).isEqualTo("1-770-736-8031 x56442");
        assertThat(user.getWebsite()).isEqualTo("hildegard.org");
        assertThat(user.getCompany().getName()).isEqualTo("Romaguera-Crona");
        assertThat(user.getCompany().getCatchPhrase()).isEqualTo("Multi-layered client-server neural-net");
        assertThat(user.getCompany().getBs()).isEqualTo("harness real-time e-markets");
    }


    @Test
    public void givenADownloadedTodo_whenParsed_thenAllFieldsAreProcessedCorrectly() throws JsonProcessingException {
        Todo todo = jsonToObject(SampleData.todo, Todo.class);

        assertThat(todo.getId()).isEqualTo(1);
        assertThat(todo.getUserId()).isEqualTo(1);
        assertThat(todo.getCompleted()).isEqualTo(false);
        assertThat(todo.getTitle()).isEqualTo("delectus aut autem");
    }

    @Test
    public void givenADownloadedAlbum_whenParsed_thenAllFieldsAreProcessedCorrectly() throws JsonProcessingException {
        Album album = jsonToObject(SampleData.album, Album.class);

        assertThat(album.getId()).isEqualTo(1);
        assertThat(album.getUserId()).isEqualTo(1);
        assertThat(album.getTitle()).isEqualTo("quidem molestiae enim");
    }

    @Test
    public void givenADownloadedPhoto_whenParsed_thenAllFieldsAreProcessedCorrectly() throws JsonProcessingException {
        Photo photo = jsonToObject(SampleData.photo, Photo.class);

        assertThat(photo.getId()).isEqualTo(1);
        assertThat(photo.getAlbumId()).isEqualTo(1);
        assertThat(photo.getTitle()).isEqualTo("accusamus beatae ad facilis cum similique qui sunt");
        assertThat(photo.getUrl()).isEqualTo("https://via.placeholder.com/600/92c952");
        assertThat(photo.getThumbnailUrl()).isEqualTo("https://via.placeholder.com/150/92c952");
    }

    @Test
    public void givenADownloadedComment_whenParsed_thenAllFieldsAreProcessedCorrectly() throws JsonProcessingException {
        Comment comment = jsonToObject(SampleData.comment, Comment.class);

        assertThat(comment.getId()).isEqualTo(1);
        assertThat(comment.getPostId()).isEqualTo(1);
        assertThat(comment.getName()).isEqualTo("id labore ex et quam laborum");
        assertThat(comment.getEmail()).isEqualTo("Eliseo@gardner.biz");
        assertThat(comment.getBody()).isEqualTo("laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium");

    }

    @Test
    public void givenADownloadedPost_whenParsed_thenAllFieldsAreProcessedCorrectly() throws JsonProcessingException {
        Post post = jsonToObject(SampleData.post, Post.class);

        assertThat(post.getId()).isEqualTo(1);
        assertThat(post.getUserId()).isEqualTo(1);
        assertThat(post.getTitle()).isEqualTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
        assertThat(post.getBody()).isEqualTo("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto");

    }

}
