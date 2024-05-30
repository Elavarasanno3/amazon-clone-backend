package com.elavarasanno3.amazonclone.controller;

import com.elavarasanno3.amazonclone.model.Product;
import com.elavarasanno3.amazonclone.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<String> registerProduct(
            @RequestParam("name") String name,
            @RequestParam("amount") double amount,
            @RequestParam("ratings") double ratings,
            @RequestParam("qty") int qty,
            @RequestParam("image") MultipartFile image) {
        try {
            // Save the product and encode image data to Base64
            Product product = productService.saveProduct(name, amount, ratings, qty, image.getBytes());
            return ResponseEntity.status(HttpStatus.OK).body("Product registered successfully: " + product.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Product registration failed");
        }
    }

    @GetMapping("/{id}") // Change the mapping to handle dynamic IDs
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Optional<Product> productOpt = productService.getProduct(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            byte[] imageBytes = product.getImage();
            String imageString = Base64.getEncoder().encodeToString(imageBytes);
            product.setImageString(imageString);
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        products.forEach(product -> {
            byte[] imageBytes = product.getImage();
            String imageString = Base64.getEncoder().encodeToString(imageBytes);
            product.setImageString(imageString);
        });
        return ResponseEntity.ok(products);
    }
}
