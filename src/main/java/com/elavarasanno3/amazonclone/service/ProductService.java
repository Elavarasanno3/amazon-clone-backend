package com.elavarasanno3.amazonclone.service;

import com.elavarasanno3.amazonclone.model.Product;
import com.elavarasanno3.amazonclone.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(String name, double amount, double ratings, int qty, byte[] imageBytes) throws IOException {
        Product product = new Product();
        product.setName(name);
        product.setAmount(amount);
        product.setRatings(ratings);
        product.setQty(qty);
        product.setImage(imageBytes);
        return productRepository.save(product);
    }

    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
