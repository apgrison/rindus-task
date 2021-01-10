package apgrison.codingtask.controller;

import apgrison.codingtask.model.Resource;
import apgrison.codingtask.model.User;
import apgrison.codingtask.service.ResourceService;
import static apgrison.codingtask.util.MapperUtil.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This test suite makes sure the controller returns the expected HttpStatuses when no errors occur
 */
@WebMvcTest(ResourceController.class)
public class ResourceControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResourceService resourceService;

    @Test
    public void givenAnObjectReturnedByGetById_whenGetRequestIsCorrect_thenControllerReturnsThatObjectAsJson() throws Exception {
        User expectedUser = new User();
        expectedUser.setId(1);
        Mockito.when(resourceService.get(Resource.USERS, 1)).thenReturn(expectedUser);

        MvcResult result = this.mockMvc.perform(get("/users/1")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo(toJson(expectedUser));
    }

    @Test
    public void givenAListReturnedByGetAll_whenGetRequestIsCorrect_thenControllerReturnsThatListAsJson() throws Exception {

        User user1 = new User();
        user1.setId(1);
        user1.setEmail("user1@email.com");

        User user2 = new User();
        user2.setId(2);
        user2.setEmail("user2@email.com");

        User[] users = new User[]{user1, user2};

        Mockito.when(resourceService.getAll(Resource.USERS_LIST)).thenReturn(users);

        MvcResult result = this.mockMvc.perform(get("/users")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo(toJson(users));
    }

    @Test
    public void givenAnObjectReturnedByCreate_whenPostRequestIsCorrect_thenControllerReturnsThatObjectAsJson() throws Exception {
        User newUser = new User();
        newUser.setEmail("user@email.com");
        newUser.setId(11);

        Mockito.when(resourceService.create(Mockito.any(Resource.class), Mockito.any(Object.class))).thenReturn(newUser);

        MvcResult result = this.mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(toJson(new User()))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo(toJson(newUser));
    }

    @Test
    public void givenAnObjectReturnedByPatch_whenPatchRequestIsCorrect_thenControllerReturnsThatObjectAsJson() throws Exception {
        User user = new User();
        user.setId(11);
        user.setEmail("user@email.com");

        Mockito.when(resourceService.patch(
                Mockito.any(Resource.class),
                Mockito.any(Object.class),
                Mockito.anyInt()
        )).thenReturn(user);

        MvcResult result = this.mockMvc.perform(
                patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(toJson(new User()))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo(toJson(user));
    }

    @Test
    public void givenAnObjectReturnedByReplace_whenPutRequestIsCorrect_thenControllerReturnsThatObjectAsJson() throws Exception {
        User user = new User();
        user.setId(11);
        user.setEmail("user@email.com");

        Mockito.when(resourceService.replace(
                Mockito.any(Resource.class),
                Mockito.any(Object.class),
                Mockito.anyInt()
        )).thenReturn(user);

        MvcResult result = this.mockMvc.perform(
                put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(toJson(new User()))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo(toJson(user));
    }


}
