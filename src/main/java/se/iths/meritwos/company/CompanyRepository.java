package se.iths.meritwos.company;

import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends ListCrudRepository<Company, Long> {

    @EntityGraph(attributePaths = {"ads"})
    @Override
    @NonNull
    List<Company> findAll();

    Optional<Company> findByName(String name);
}
