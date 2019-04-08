package io.viren.graphql.graphqldemo.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.viren.graphql.graphqldemo.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	Optional<User> findByUid(final String uid);
	
	

}
