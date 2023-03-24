package se.iths.meritwos.company;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.iths.meritwos.ad.Ad;
import se.iths.meritwos.ad.AdRepository;
import se.iths.meritwos.mapper.Mapper;
import se.iths.meritwos.student.Student;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyRepository companyRepository;
    private final AdRepository adRepository;

    private final Mapper mapper;

    public CompanyController(CompanyRepository companyRepository, AdRepository adRepository, Mapper mapper) {
        this.companyRepository = companyRepository;
        this.adRepository = adRepository;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    Optional<CompanyDTO> getCompany(@PathVariable long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return mapper.mapCompanyToDTO(company);
    }

    @GetMapping("")
    List<CompanyDTO> getAllCompanies() {
        return mapper.mapCompanyToDTO(companyRepository.findAll());
    }

    @PostMapping
    void addCompany(@Valid @RequestBody Company company) {
        companyRepository.save(company);
    }

    @PostMapping("/{companyId}/newadd")
    void addAdToStudent(@PathVariable long companyId, @RequestBody Ad ad) {
        var company = companyRepository.findById(companyId).orElseThrow();
        adRepository.save(ad);
        var adFound = adRepository.findByName(ad.getName());
        company.getAds().add(adFound);

    }

    @PostMapping(value = "/newcompany", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<Void> addCompanyByForm(@ModelAttribute Company company) {
        companyRepository.save(company);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/newcompany"))
                .build();
    }


    @PostMapping(value = "/newad", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<Void> addNewAdByForm(@ModelAttribute Ad ad, @RequestParam("companyId") long companyId) {

        var company = companyRepository.findById(companyId).orElseThrow();
        adRepository.save(ad);

        var adFound = adRepository.findByName(ad.getName());
        company.getAds().add(adFound);
        companyRepository.save(company);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/newad"))
                .build();
    }


    @PutMapping("/{id}")
    void updateCompanyById(@PathVariable long id, @Valid @RequestBody Company company) {
        var companyToUpdate = companyRepository.findById(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatusCode
                    .valueOf(404), "Company doesn't exist");
        });
        companyToUpdate.setName(company.getName());
        companyToUpdate.setWebsite(company.getWebsite());
        companyToUpdate.setEmail(company.getEmail());
        companyRepository.save(companyToUpdate);
    }

    @DeleteMapping("/{id}")
    void deleteCompany(@PathVariable long id) {
        companyRepository.deleteById(id);
    }
}
