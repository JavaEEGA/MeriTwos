package se.iths.meritwos.company;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class CompanyDTO {

    private Long id;
    private String name;
    private String website;
    private String email;
}
