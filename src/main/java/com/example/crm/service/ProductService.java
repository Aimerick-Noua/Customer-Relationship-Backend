package com.example.crm.service;

import com.example.crm.model.Product;
import com.example.crm.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    public List<Product> getAllProducts() {
        return  repository.findAll();
    }

    public Product getProductById(Integer id) {
        return repository.findById(id).orElseThrow(()->new RuntimeException("Product not found with id"+id));
    }

    public Product addProduct(Product product) {
        return repository.save(product);
    }

    public Product updateProduct(Integer id, Product product) {
        Product product1 = repository.findById(id).orElseThrow(()->new RuntimeException("Product not found with id"+id));
        product1.setName(product.getName());
        product1.setDescription(product.getDescription());
        product1.setPrice(product.getPrice());
        product1.setQuantity(product.getQuantity());
        return repository.save(product1);
    }

    public void deleteProduct(Integer id) {
        repository.deleteById(id);
    }
}
