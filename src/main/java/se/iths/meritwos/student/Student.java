package se.iths.meritwos.student;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import se.iths.meritwos.ad.Ad;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String program;
    private String mail;
    @ManyToMany
    private Set<Ad> ads = new HashSet<>();

    //list of ads
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return 1;
    }


}
