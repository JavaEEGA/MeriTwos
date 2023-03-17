package se.iths.meritwos.Student;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDTO {

    private Long id;
    private String name;
    private String program;
    private String mail;

    public StudentDTO(Student student){
        this.id=student.getId();
        this.name=student.getName();
        this.program=student.getProgram();
        this.mail=student.getMail();
    }
}
