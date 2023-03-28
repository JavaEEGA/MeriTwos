package se.iths.meritwos.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import se.iths.meritwos.mapper.Mapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WithMockUser(value = "user")
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserRepository repository;
    @MockBean
    Mapper mapper;

    private ObjectMapper objectMapper = new ObjectMapper();
    @WithMockUser(value = "user")
    @Test
    void getAllUserShouldReturnOK() throws Exception {
        var user = new User(1L, "Oliver", "12345", User.Role.ADMIN);
        var user2 = new User(2L, "William", "2345", User.Role.STUDENT);
        var userList = List.of(user, user2);

        when(repository.findAll()).thenReturn(userList);

        mockMvc.perform(get("/users")).andExpect(status().isOk());
    }

    @Test
    void getUserByIdShouldReturnOK() throws Exception {
        var user = new User(1L, "Oliver", "12345", User.Role.ADMIN);

        when(repository.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/1")).andExpect(status().isOk());
        //   .andExpect(ResponseBodyMatchers.responseBody().containsObjectAsJson(user,UserDTO.class));
    }
    @WithMockUser(username = "user")
    @Test
    void addUserShouldReturnOk() throws Exception {
        var user = new User(1L, "Oliver", "12345", User.Role.ADMIN);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    void addUserWithInvalidJSONShouldReturn400() throws Exception {
        var user = new User(1L, "Oliver", "", User.Role.ADMIN);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void updateUserByIdShouldReturnOkIfUpdated() throws Exception {
        var user = new User(1L, "Oliver", "12345", User.Role.ADMIN);
        var user2 = new User(2L, "William", "2345", User.Role.STUDENT);
        when(repository.findById(1L)).thenReturn(Optional.of(user));


        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user2)))
                .andExpect(status().isOk());
        assertThat(user.getName()).isEqualTo(user2.getName());
        assertThat(user.getRole()).isEqualTo(user2.getRole());
        assertThat(user.getPassword()).isEqualTo(user2.getPassword());
    }

    @Test
    void updateUserWithInvalidJSONShouldReturn400() throws Exception {
        var user = new User(1L, "Oliver", "", User.Role.ADMIN);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void deleteUserShouldReturnOK() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
        verify(repository).deleteById(1L);
    }

}