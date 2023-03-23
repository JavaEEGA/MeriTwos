package se.iths.meritwos.company;


import lombok.Getter;
import lombok.Setter;
import se.iths.meritwos.ad.Ad;
import se.iths.meritwos.ad.AdDTO;
import se.iths.meritwos.mapper.Mapper;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class CompanyDTO {

    private Long id;
    private String name;
    private String website;
    private String email;
    private List<AdDTO> ads;

    public CompanyDTO(Company company, Mapper mapper) {
        this.id = company.getId();
        this.name = company.getName();
        this.website = company.getWebsite();
        this.email = company.getEmail();
        this.ads = mapper.mapAdToDTO(company.getAds());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanyDTO that = (CompanyDTO) o;

        if (!id.equals(that.id)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(website, that.website)) return false;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
