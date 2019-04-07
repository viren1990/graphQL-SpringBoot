package io.viren.graphql.graphqldemo.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.viren.graphql.graphqldemo.models.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{
	
	Optional<Product> findByCode(final String code);

}
