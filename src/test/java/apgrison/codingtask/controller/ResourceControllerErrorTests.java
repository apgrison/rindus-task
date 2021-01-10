package apgrison.codingtask.controller;

import apgrison.codingtask.model.Resource;
import apgrison.codingtask.service.ResourceService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

/**
 * This test suite makes sure the controller returns the expected HttpStatuses when errors occur
 */
@WebMvcTest(ResourceController.class)
public class ResourceControllerErrorTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResourceService resourceService;

    @Test
    public void givenAGetRequest_whenServiceReturnsInternalError_thenControllerReturnsInternalError() throws Exception {
        Mockito.when(resourceService.get(Resource.USERS, 1)).thenReturn(new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR));
        this.mockMvc.perform(get("/users/1")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void givenAnIncorrectGetRequest_whenServiceReturnsNotFound_thenControllerReturnsNotFound() throws Exception {
        Mockito.when(resourceService.get(Resource.USERS, 11)).thenReturn(new ResponseEntity<>("{}", HttpStatus.NOT_FOUND));
        this.mockMvc.perform(get("/users/11")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenAnIncorrectPath_whenServiceReturnsNotFound_thenControllerReturnsNotFound() throws Exception {
        Mockito.when(resourceService.get(null, 1)).thenReturn(new ResponseEntity<>("{}", HttpStatus.NOT_FOUND));
        this.mockMvc.perform(get("/unknown/1")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenAUsersPathWithId_whenCalledWithPostMethod_thenControllerReturnsMethodNotAllowed() throws Exception {
        this.mockMvc.perform(post("/users/1")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void givenAUsersPath_whenCalledWithPostMethodAndNoBody_thenControllerReturnsUnsupportedMediaType() throws Exception {
        this.mockMvc.perform(post("/users")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void givenAUsersPathWithoutId_whenCalledWithPutMethod_thenControllerReturnsMethodNotAllowed() throws Exception {
        this.mockMvc.perform(put("/users")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void givenAUsersPathWithId_whenCalledWithPutMethodAndNoBody_thenControllerReturnsUnsupportedMediaType() throws Exception {
        this.mockMvc.perform(put("/users/1")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void givenAUsersPathWithId_whenCalledWithPatchMethodAndNoBody_thenControllerReturnsUnsupportedMediaType() throws Exception {
        this.mockMvc.perform(patch("/users/1")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isUnsupportedMediaType());
    }

}
