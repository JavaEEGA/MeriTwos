package se.iths.meritwos.ad;

import lombok.Getter;
import lombok.Setter;
import se.iths.meritwos.company.Company;

import java.util.Objects;

@Getter
@Setter
public class AdDTO {
    public AdDTO(){}
    public AdDTO(Ad ad){
        this.id = ad.getId();
        this.name = ad.getName();
        this.company = ad.getCompany();
        this.description = ad.getDescription();
    }
    private Long id;
    private String name;
    private Company company;
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdDTO adDTO = (AdDTO) o;
        return Objects.equals(id, adDTO.id);
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
