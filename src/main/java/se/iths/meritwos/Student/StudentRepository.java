package se.iths.meritwos.Student;

import org.springframework.data.repository.ListCrudRepository;

public interface StudentRepository extends ListCrudRepository<Student,Long> {
}
