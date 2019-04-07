package io.viren.graphql.graphqldemo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.viren.graphql.graphqldemo.models.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

}
