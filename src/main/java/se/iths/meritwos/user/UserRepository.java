package se.iths.meritwos.user;

import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User,Long> {
}