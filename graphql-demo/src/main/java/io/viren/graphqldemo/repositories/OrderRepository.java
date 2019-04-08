package io.viren.graphqldemo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.viren.graphqldemo.models.Order;
import io.viren.graphqldemo.models.User;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
	
	
	Iterable<Order> findAllByUser(final User user);
}
