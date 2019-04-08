package io.viren.graphqldemo.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.viren.graphqldemo.models.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{
	
	Optional<Product> findByCode(final String code);

}
