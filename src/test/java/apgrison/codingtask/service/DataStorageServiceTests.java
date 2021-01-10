package apgrison.codingtask.service;

import apgrison.codingtask.model.FileFormat;
import apgrison.codingtask.model.FileLocator;
import static apgrison.codingtask.model.FileLocator.FileInfo;
import apgrison.codingtask.model.Resource;
import apgrison.codingtask.model.Todo;
import apgrison.codingtask.repository.FileRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

import static apgrison.codingtask.util.MapperUtil.toJson;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DataStorageServiceTests {

    private static Logger log = LoggerFactory.getLogger(DataStorageServiceTests.class);

    @Mock
    private ResourceService resourceService;

    @Mock
    private FileLocator fileLocator;

    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private DataStorageService dataStorageService = new DataStorageServiceImpl();

    @Test
    public void givenAnExistingTodoListFile_whenDataIsRetrieved_thenNotFetchedViaResourceService() throws IOException {
        Todo todo1 = new Todo();
        todo1.setTitle("First Todo");
        Todo todo2 = new Todo();
        todo2.setTitle("Second Todo");
        Todo[] todos = new Todo[]{todo1, todo2};

        File file = new File("TodoList.json");
        Files.write(file.toPath(), toJson(todos).getBytes());
        Mockito.when(fileRepository.retrieveFile("TodoList.json")).thenReturn(file);

        FileLocator.FileInfo fileInfo = new FileLocator.FileInfo(Duration.ofMinutes(60), "TodoList.json");
        Mockito.when(fileLocator.getFileInfo("TodoList.json")).thenReturn(fileInfo);

        Object object = dataStorageService.retrieveData(Resource.TODOS_LIST);

        assertThat(object).isInstanceOf(Todo[].class);
        assertThat(((Todo[])object)[1].getTitle()).isEqualTo("Second Todo");
        assertThat(file.delete()).isTrue();
    }

    @Test
    public void givenAnEmptyFileRepo_whenDataIsRetrieved_thenFetchedViaResourceService() throws IOException {
        Todo todo1 = new Todo();
        todo1.setTitle("First Todo");
        Todo todo2 = new Todo();
        todo2.setTitle("Second Todo");
        Todo[] todos = new Todo[]{todo1, todo2};

        Mockito.when(resourceService.getAll(Resource.TODOS_LIST)).thenReturn(todos);

        Object object = dataStorageService.retrieveData(Resource.TODOS_LIST);

        assertThat(object).isInstanceOf(Todo[].class);
        assertThat(((Todo[])object)[1].getTitle()).isEqualTo("Second Todo");
    }

    @Test
    public void givenAnEmptyFileRepo_whenDataIsStored_thenFetchedViaResourceService() throws IOException {
        Todo todo1 = new Todo();
        todo1.setTitle("First Todo");
        Todo todo2 = new Todo();
        todo2.setTitle("Second Todo");
        Todo[] todos = new Todo[]{todo1, todo2};

        Mockito.when(resourceService.getAll(Resource.TODOS_LIST)).thenReturn(todos);

        Mockito.when(fileRepository.saveToFile(todos, "TodoList.json", FileFormat.JSON)).thenReturn("TodoList.json");

        Mockito.doCallRealMethod().when(fileLocator).addFileInfo(Mockito.any(String.class), Mockito.any(FileInfo.class));
        Mockito.doCallRealMethod().when(fileLocator).getFileInfo(Mockito.any(String.class));

        FileInfo fileInfo = fileLocator.getFileInfo("TodoList.json");
        assertThat(fileInfo).isNull();

        dataStorageService.storeData(Resource.TODOS_LIST, FileFormat.JSON);

        fileInfo = fileLocator.getFileInfo("TodoList.json");
        assertThat(fileInfo).isNotNull();

        log.debug("fileInfo = {}", fileLocator.getFileInfo("TodoList.json"));
    }

}
