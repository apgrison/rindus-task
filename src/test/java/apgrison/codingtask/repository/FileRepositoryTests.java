package apgrison.codingtask.repository;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static apgrison.codingtask.util.MapperUtil.*;
import static apgrison.codingtask.util.FileUtil.*;
import apgrison.codingtask.model.FileFormat;
import apgrison.codingtask.model.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;

public class FileRepositoryTests extends FileRepositoryImpl {

    private static Logger log = LoggerFactory.getLogger(FileRepositoryTests.class);

    @Test
    public void givenAnObject_whenSentToSaveFile_thenCanBeRetrieved() throws IOException {

        Todo todo1 = new Todo();
        todo1.setId(1);
        todo1.setUserId(1);
        todo1.setCompleted(false);
        todo1.setTitle("go shopping");

        Todo todo2 = new Todo();
        todo2.setId(1);
        todo2.setUserId(1);
        todo2.setCompleted(false);
        todo2.setTitle("fix car");

        Todo[] todoList = {todo1, todo2};
        String location = this.saveToFile(todoList, "MyTodoList.xml", FileFormat.XML);

        log.info("location = {}", location);

        File file = this.retrieveFile(location);
        assertThat(file).exists();
        Todo[] parsedTodos = xmlToObject(fileToString(file), Todo[].class);
        assertThat(parsedTodos[0].getTitle()).isEqualTo("go shopping");
        assertThat(file.delete()).isTrue();
    }

}
