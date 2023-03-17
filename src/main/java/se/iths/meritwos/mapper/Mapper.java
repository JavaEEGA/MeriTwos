package se.iths.meritwos.mapper;

import org.springframework.stereotype.Component;
import se.iths.meritwos.Student.Student;
import se.iths.meritwos.Student.StudentDTO;

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
}
