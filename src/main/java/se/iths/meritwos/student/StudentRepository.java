package se.iths.meritwos.student;

import org.springframework.data.repository.ListCrudRepository;

public interface StudentRepository extends ListCrudRepository<Student,Long> {

}