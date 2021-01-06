package com.amorim.dsdeliver.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amorim.dsdeliver.dto.ProductDTO;
import com.amorim.dsdeliver.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Transactional(readOnly = true)
	public List<ProductDTO> findAll() {
		return productRepository.findAllByOrderByNameAsc().stream().map(x -> ProductDTO.of(x)).collect(Collectors.toList());
	}
	
}
