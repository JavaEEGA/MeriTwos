package se.iths.meritwos.company;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface CompanyRepository extends ListCrudRepository <Company, Long>{
public Optional<Company> findByName(String name);
}
