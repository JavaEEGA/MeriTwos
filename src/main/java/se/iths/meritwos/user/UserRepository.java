package se.iths.meritwos.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    public Optional<User> findByName(String userName);
    public void deleteByName(String name);
}
