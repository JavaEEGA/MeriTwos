package se.iths.meritwos.company;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import se.iths.meritwos.mapper.Mapper;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyRepository repo;

    private final Mapper mapper;

    public CompanyController (CompanyRepository companyRepository, Mapper mapper) {
        this.repo = companyRepository;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    Optional<CompanyDTO> getCompany(@PathVariable long id) {
        Company company = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return mapper.mapCompanyToDTO(company);
    }

    @GetMapping("")
    List<CompanyDTO> getAllCompanies() {return mapper.mapCompanyToDTO(repo.findAll());}
}
