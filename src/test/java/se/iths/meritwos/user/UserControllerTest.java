package se.iths.meritwos.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import se.iths.meritwos.ResponseBodyMatchers;
import se.iths.meritwos.mapper.Mapper;
import se.iths.meritwos.user.UserController;
import se.iths.meritwos.user.UserRepository;

import java.util.List;
import java.util.Optional;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserRepository repository;
    @MockBean
    Mapper mapper;


    @Test
    void getAllUserShouldReturnOK() throws Exception {
        var user = new User(1L, "Oliver", "12345", User.Role.ADMIN);
        var user2 = new User(2L, "William", "2345", User.Role.STUDENT);
        var userList = List.of(user, user2);

        when(repository.findAll()).thenReturn(userList);

        mockMvc.perform(get("/users")).andExpect(status().isOk());
    }

}