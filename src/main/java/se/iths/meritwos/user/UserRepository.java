package se.iths.meritwos.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
