package com.ecommerce.product.repositry;

import com.ecommerce.product.model.Product;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByCategory(String category);

}