package com.elavarasanno3.amazonclone.service;

import com.elavarasanno3.amazonclone.model.Cart;
import com.elavarasanno3.amazonclone.model.Product;
import com.elavarasanno3.amazonclone.model.User;
import com.elavarasanno3.amazonclone.repository.CartRepository;
import com.elavarasanno3.amazonclone.repository.ProductRepository;
import com.elavarasanno3.amazonclone.repository.UserRepository; // Import UserRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        try {
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
                // If the product is already in the cart, increment the quantity by one
                cartItem = existingCartItemOptional.get();
                cartItem.setQty(cartItem.getQty() + 1);
            } else {
                // If the product is not in the cart, create a new cart item with quantity 1
                cartItem = new Cart(user, productToAdd, 1);
            }

            // Save the cart item
            return cartRepository.save(cartItem);
        } catch (Exception e) {
            // Log the exception
            System.out.println("Error adding product to cart: " + e.getMessage());
            // Rethrow the exception to be handled by the caller
            throw e;
        }
    }
    public Cart updateCartItemQuantity(String userEmail, Long cartItemId, int delta) {
        Optional<User> userOptional = userRepository.findByEmailId(userEmail);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();

        Optional<Cart> cartItemOptional = cartRepository.findById(cartItemId);
        if (!cartItemOptional.isPresent()) {
            throw new RuntimeException("Cart item not found");
        }
        Cart cartItem = cartItemOptional.get();
        if (!cartItem.getUser().equals(user)) {
            throw new RuntimeException("Unauthorized access");
        }

        int newQty = cartItem.getQty() + delta;
        if (newQty <= 0) {
            cartRepository.delete(cartItem);
            return null; // or handle as needed, e.g., throw an exception or return a special value
        }

        cartItem.setQty(newQty);
        return cartRepository.save(cartItem);
    }



    public List<Cart> getCartItems(Optional<User> user) {
        return cartRepository.findByUser(user);
    }

    public void removeCartItem(User user, Long cartItemId) {
        Optional<Cart> cartItemOptional = cartRepository.findById(cartItemId);
        if (cartItemOptional.isPresent()) {
            Cart cartItem = cartItemOptional.get();
            if (cartItem.getUser().equals(user)) {
                cartRepository.delete(cartItem);
            } else {
                throw new IllegalArgumentException("Unauthorized access");
            }
        } else {
            throw new IllegalArgumentException("Cart item not found");
        }
    }
}
