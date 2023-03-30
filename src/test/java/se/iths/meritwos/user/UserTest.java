package se.iths.meritwos.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {
    User user = new User();

    @Test
    void convertRoleReturnsRole() {
        assertThat(user.convertRole("ADMIN")).isEqualTo(User.Role.ADMIN);
        assertThat(user.convertRole("STUDENT")).isEqualTo(User.Role.STUDENT);
        assertThat(user.convertRole("COMPANY")).isEqualTo(User.Role.COMPANY);
        assertThat(user.convertRole("Nothing")).isEqualTo(null);

    }

    @Test
    void UserEqualsUser() {
        var user2 = new User();
        assertEquals(user, user2);
    }
}
