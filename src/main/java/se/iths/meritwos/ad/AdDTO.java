package se.iths.meritwos.ad;

import lombok.Getter;
import lombok.Setter;

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
    private String company;
    private String description;
}
