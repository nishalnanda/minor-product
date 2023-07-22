package com.ecommerce.product.service;

import com.ecommerce.product.dto.RequestDto;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.repositry.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {
    private static ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        ProductService.productRepository = productRepository;
    }

    public void createProduct(RequestDto requestDto) {
        Product product = Product.builder().title(requestDto.getTitle()).description(requestDto.getDescription()).category(requestDto.getCategory()).imageUrl(requestDto.getImage()).price(requestDto.getPrice()).rating(requestDto.getRating()).createdAt(LocalDateTime.now()).build();
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product getProductById(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            log.warn("Product with id {} not found", id);
            return null;
        }
    }

    public List<Product> getProductByCategory(String category) {
        List<Product> products = productRepository.findByCategory(category);
        if ((products != null)) return products;
        else log.warn("Product with category {} not found", category);
        return null;
    }

    public boolean deleteProductById(String id) {
        try {
            if (productRepository.existsById(id)) {
                productRepository.deleteById(id);
                log.info("Product with id {} is deleted", id);
                return true;
            } else {
                log.warn("Product with id {} not found", id);
                return false;
            }
        } catch (Exception ex) {
            log.error("Error while deleting product with id {}", id, ex);
            return false;
        }
    }

}
