package se.iths.meritwos.mapper;

import org.junit.jupiter.api.Test;
import se.iths.meritwos.user.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


class MapperTest {

    Mapper mapper = new Mapper();

    @Test
    void mapUserToDTOShouldReturnUserDTO() {
        var user = new User(1L, "Oliver", "12345", User.Role.Admin);

        var result = mapper.mapUserToDTO(user);

        assertThat(result.get().getName()).isSameAs(user.getName());
        assertThat(result.get().getId()).isSameAs(user.getId());
        assertThat(result.get().getRole()).isSameAs(user.getRole());
    }

    @Test
    void mapUserListToDtoShouldReturnDTOList() {
        var user = new User(1L, "Oliver", "12345", User.Role.Admin);
        var user2 = new User(2L, "William", "1245", User.Role.Student);
        var userlist = List.of(user, user2);

        var result = mapper.mapUserToDTO(userlist);

        assertThat(result.get(0).getName()).isSameAs(user.getName());
        assertThat(result.get(0).getId()).isSameAs(user.getId());
        assertThat(result.get(1).getName()).isSameAs(user2.getName());
        assertThat(result.get(1).getId()).isSameAs(user2.getId());

    }

}