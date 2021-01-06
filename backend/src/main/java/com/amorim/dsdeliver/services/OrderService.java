package com.amorim.dsdeliver.services;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amorim.dsdeliver.dto.OrderDTO;
import com.amorim.dsdeliver.dto.ProductDTO;
import com.amorim.dsdeliver.entities.Order;
import com.amorim.dsdeliver.entities.OrderStatus;
import com.amorim.dsdeliver.entities.Product;
import com.amorim.dsdeliver.repositories.OrderRepository;
import com.amorim.dsdeliver.repositories.ProductRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Transactional(readOnly = true)
	public List<OrderDTO> findAll() {
		return repository.findOrdersWithProducts().stream().map(x -> OrderDTO.of(x)).collect(Collectors.toList());
	}
	
	@Transactional
	public OrderDTO insert(OrderDTO dto) {
		Order order = Order.of(null, dto.getAddress(), dto.getLatitude(), dto.getLongitude(), Instant.now(), OrderStatus.PENDING);
		for (ProductDTO p : dto.getProducts()) {
			Product product = productRepository.getOne(p.getId());
			order.getProducts().add(product);
		}
		order = repository.save(order);
		return OrderDTO.of(order);
	}
	
	@Transactional
	public OrderDTO setDelivered(Long id) {
		Order order = repository.getOne(id);
		order.setStatus(OrderStatus.DELIVERED);
		order = repository.save(order);
		return OrderDTO.of(order);
	}
}
