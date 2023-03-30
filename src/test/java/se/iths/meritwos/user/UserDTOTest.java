package se.iths.meritwos.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDTOTest {

    @Test
    void UserDTOEquals() {
        var userDTO1 = new UserDTO(new User("Test", "test", "ADMIN"));
        var userDTO2 = new UserDTO();
        userDTO2.setName("Test");

        assertEquals(userDTO1, userDTO2);
    }

}
