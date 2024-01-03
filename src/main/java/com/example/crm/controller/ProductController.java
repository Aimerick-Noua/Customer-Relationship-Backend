package com.example.crm.controller;

import com.example.crm.model.Product;
import com.example.crm.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

   @GetMapping("/{id}")
   public Product getProductById(@PathVariable Integer id){
        return productService.getProductById(id);
   }
   @PostMapping
    public Product addProduct(@RequestBody Product product){
        return productService.addProduct(product);
   }
   @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Integer id, @RequestBody Product product){
        return productService.updateProduct(id,product);
   }
   @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id){
        productService.deleteProduct(id);
   }
}
