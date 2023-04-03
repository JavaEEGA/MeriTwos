package se.iths.meritwos.ad;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface AdRepository extends ListCrudRepository<Ad, Long> {
    public Optional<Ad> findByName(String name);
}
