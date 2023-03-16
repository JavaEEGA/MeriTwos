package se.iths.meritwos.ad;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdDTO {
    public AdDTO(){}
    private Long id;
    private String name;
    private String company;
    private String description;
}
