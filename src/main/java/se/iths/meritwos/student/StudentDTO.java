package se.iths.meritwos.student;

import lombok.Getter;
import lombok.Setter;
import se.iths.meritwos.ad.Ad;

import java.util.Set;

@Getter
@Setter
public class StudentDTO {

    private Long id;
    private String name;
    private String program;
    private String mail;
    private Set<Ad> ad;

    public StudentDTO(Student student){
        this.id=student.getId();
        this.name=student.getName();
        this.program=student.getProgram();
        this.mail=student.getMail();
        this.ad = student.getAds();
    }
}
