package se.iths.meritwos.student;

import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface StudentRepository extends ListCrudRepository<Student, Long> {
    @EntityGraph(attributePaths = {"ads"})
    @Override
    @NonNull
    List<Student> findAll();
}
