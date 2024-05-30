package com.elavarasanno3.amazonclone.service;

import com.elavarasanno3.amazonclone.model.Cart;
import com.elavarasanno3.amazonclone.model.Product;
import com.elavarasanno3.amazonclone.model.User;
import com.elavarasanno3.amazonclone.repository.CartRepository;
import com.elavarasanno3.amazonclone.repository.ProductRepository;
import com.elavarasanno3.amazonclone.repository.UserRepository; // Import UserRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository; // Autowire UserRepository

    public Cart addProductToCart(String userEmail, Product product) {
        // Retrieve the user by email
        Optional<User> userOptional = userRepository.findByEmailId(userEmail);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();

        // Retrieve the product by ID
        Optional<Product> productOptional = productRepository.findById(product.getId());
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Product not found");
        }
        Product productToAdd = productOptional.get();

        // Check if the product is already in the user's cart
        Optional<Cart> existingCartItemOptional = cartRepository.findByUserAndProduct(user, productToAdd);
        Cart cartItem;
        if (existingCartItemOptional.isPresent()) {
            // If the product is already in the cart, increment the quantity
            cartItem = existingCartItemOptional.get();
            cartItem.setQty(cartItem.getQty() + 1);
        } else {
            // If the product is not in the cart, create a new cart item with quantity 1
            cartItem = new Cart(user, productToAdd, 1);
        }

        // Save the cart item
        return cartRepository.save(cartItem);
    }
}
