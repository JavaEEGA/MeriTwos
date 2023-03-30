package se.iths.meritwos.ad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import se.iths.meritwos.company.Company;

@Table
@Entity
@Setter
@Getter
@ToString
public class Ad {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    public Ad(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Ad() {
    }
}
