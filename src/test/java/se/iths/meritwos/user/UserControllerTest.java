package se.iths.meritwos.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import se.iths.meritwos.mapper.Mapper;
import se.iths.meritwos.security.SecurityConfig;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = UserController.class)
@Import(SecurityConfig.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserRepository repository;
    @MockBean
    BCryptPasswordEncoder encoder;
    @MockBean
    Mapper mapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @WithMockUser
    @Test
    void getAllUserShouldReturnOK() throws Exception {
        var user = new User("Oliver", "12345", User.Role.ADMIN);
        var user2 = new User("William", "2345", User.Role.STUDENT);
        var userList = List.of(user, user2);

        when(repository.findAll()).thenReturn(userList);

        mockMvc.perform(get("/api/users")).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    void getUserByIdShouldReturnOK() throws Exception {
        var user = new User("Oliver", "12345", User.Role.ADMIN);

        when(repository.findByName("Oliver")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/Oliver")).andExpect(status().isOk());
        //   .andExpect(ResponseBodyMatchers.responseBody().containsObjectAsJson(user,UserDTO.class));
    }

    @Test
    void getUserByNameShouldReturn401IfNotAuthorized() throws Exception {
        var user = new User("Oliver", "12345", User.Role.ADMIN);

        when(repository.findByName("Oliver")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/Oliver")).andExpect(status().isUnauthorized());

    }


    @WithMockUser(authorities = "ADMIN")
    @Test
    void addUserShouldReturnOk() throws Exception {
        var user = new User("Oliver", "12345", "ADMIN");
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    void addUserWithInvalidJSONShouldReturn400() throws Exception {
        var user = new User("Oliver", "", User.Role.ADMIN);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());

    }
    @WithMockUser(authorities = "STUDENT")
    @Test
    void addUserWithBadAuthoritiesShouldReturn403() throws Exception {
        var user = new User("Oliver", "12345", "ADMIN");
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    void updateUserByNameShouldReturnOkIfUpdated() throws Exception {
        var user = new User("Oliver", "12345", User.Role.ADMIN);
        var user2 = new User("William", "2345", User.Role.STUDENT);
        when(repository.findByName("Oliver")).thenReturn(Optional.of(user));


        mockMvc.perform(put("/api/users/Oliver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user2)))
                .andExpect(status().isOk());
        assertThat(user.getName()).isEqualTo(user2.getName());
        assertThat(user.getRole()).isEqualTo(user2.getRole());
        assertThat(user.getPassword()).isEqualTo(user2.getPassword());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    void updateUserWithInvalidJSONShouldReturn400() throws Exception {
        var user = new User("Oliver", "", User.Role.ADMIN);

        mockMvc.perform(put("/api/users/Oliver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());

    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    void deleteUserShouldReturnOK() throws Exception {

        mockMvc.perform(delete("/api/users/Oliver"))
                .andExpect(status().isOk());
        verify(repository).deleteByName("Oliver");
    }

}
