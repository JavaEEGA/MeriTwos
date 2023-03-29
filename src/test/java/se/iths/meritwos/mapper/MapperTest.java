package se.iths.meritwos.mapper;

import org.junit.jupiter.api.Test;
import se.iths.meritwos.user.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class MapperTest {

    Mapper mapper = new Mapper();

    @Test
    void mapUserToDTOShouldReturnUserDTO() {
        var user = new User("Oliver", "12345", User.Role.ADMIN);

        var result = mapper.mapUserToDTO(user);

        assertThat(result.get().getName()).isSameAs(user.getName());
        assertThat(result.get().getPassword()).isSameAs(user.getPassword());
        assertThat(result.get().getRole()).isEqualTo("[ADMIN]");
    }

    @Test
    void mapUserListToDtoShouldReturnDTOList() {
        var user = new User("Oliver", "12345", User.Role.ADMIN);
        var user2 = new User("William", "1245", User.Role.STUDENT);
        var userlist = List.of(user, user2);

        var result = mapper.mapUserToDTO(userlist);

        assertThat(result.get(0).getName()).isSameAs(user.getName());
        assertThat(result.get(0).getPassword()).isSameAs(user.getPassword());
        assertThat(result.get(1).getName()).isSameAs(user2.getName());
        assertThat(result.get(1).getRole()).isEqualTo("[STUDENT]");

    }

}
