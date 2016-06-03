package rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rest.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
