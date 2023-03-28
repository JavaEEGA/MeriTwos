package se.iths.meritwos.student;

import org.springframework.data.repository.ListCrudRepository;
import se.iths.meritwos.user.User;

public interface StudentRepository extends ListCrudRepository<Student, Long> {

}
