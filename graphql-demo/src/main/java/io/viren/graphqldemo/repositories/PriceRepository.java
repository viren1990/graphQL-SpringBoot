package io.viren.graphqldemo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.viren.graphqldemo.models.Price;

@Repository
public interface PriceRepository extends CrudRepository<Price, Long>{
	

}
