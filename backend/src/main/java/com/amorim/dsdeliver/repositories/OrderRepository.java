package com.amorim.dsdeliver.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.amorim.dsdeliver.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("select distinct o from Order o join fetch o.products where o.status = 0 order by o.moment ASC")
	List<Order> findOrdersWithProducts();
}
