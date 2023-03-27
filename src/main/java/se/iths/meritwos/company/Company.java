package se.iths.meritwos.company;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import se.iths.meritwos.ad.Ad;

import java.util.List;
import java.util.Objects;
@Entity
@Getter
@Setter
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String website;
    private String email;
    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER)
    private List<Ad> ads;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
