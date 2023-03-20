package se.iths.meritwos.company;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.iths.meritwos.mapper.Mapper;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyRepository repo;

    private final Mapper mapper;

    public CompanyController(CompanyRepository companyRepository, Mapper mapper) {
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
    List<CompanyDTO> getAllCompanies() {
        return mapper.mapCompanyToDTO(repo.findAll());
    }

    @PostMapping
    void addCompany(@Valid @RequestBody Company company) {
        repo.save(company);
    }


    @PutMapping("/{id}")
    void updateCompanyById(@PathVariable long id, @Valid @RequestBody Company company) {
        var companyToUpdate = repo.findById(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatusCode
                    .valueOf(404), "Company doesn't exist");
        });
        companyToUpdate.setName(company.getName());
        companyToUpdate.setWebsite(company.getWebsite());
        companyToUpdate.setEmail(company.getEmail());
        repo.save(companyToUpdate);
    }

    @DeleteMapping("/{id}")
    void deleteCompany(@PathVariable long id) {
        repo.deleteById(id);
    }
}
