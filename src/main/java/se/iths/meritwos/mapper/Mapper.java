package se.iths.meritwos.mapper;

import org.springframework.stereotype.Component;
import se.iths.meritwos.Student.Student;
import se.iths.meritwos.Student.StudentDTO;
import se.iths.meritwos.user.User;
import se.iths.meritwos.user.UserDTO;


import java.util.List;
import java.util.Optional;

@Component
public class Mapper {

    public List<StudentDTO> mapStudentToDto(List<Student> students){
        return students.stream().map(StudentDTO::new).toList();
    }

    public Optional<StudentDTO> mapStudentToDto(Student student){
        return Optional.of(new StudentDTO(student));
    }

    public List<UserDTO> mapUserToDTO(List<User> users) {
        return users.stream().map(UserDTO::new).toList();
    }

    public Optional<UserDTO> mapUserToDTO(User user) {
        return Optional.of(new UserDTO(user));
    }

}
