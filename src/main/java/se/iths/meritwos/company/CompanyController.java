package se.iths.meritwos.company;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyRepository repo;

    public CompanyController (CompanyRepository companyRepository) {
        repo = companyRepository;
    }

    @GetMapping("/{id}")
    Company getCompany(@PathVariable long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("")
    List<Company> getAllCompanies() {return repo.findAll();}
}
