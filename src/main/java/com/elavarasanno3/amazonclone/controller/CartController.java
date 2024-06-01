package com.elavarasanno3.amazonclone.controller;

import com.elavarasanno3.amazonclone.model.Cart;
import com.elavarasanno3.amazonclone.model.Product;
import com.elavarasanno3.amazonclone.model.User;
import com.elavarasanno3.amazonclone.service.CartService;
import com.elavarasanno3.amazonclone.service.UserService; // Import UserService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService; // Autowire UserService

    @PostMapping("/api/carts/add")
    public ResponseEntity<Cart> addToCart(@RequestBody AddToCartRequest request) {
        String userEmail = request.getUserEmail(); // Extract user email from the request
        Product product = request.getProduct(); // Extract product from the request

        // Add product to cart for the user
        Cart cart = cartService.addProductToCart(userEmail, product);

        // Return the cart item in the response
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/api/carts/{userEmail}")
    public ResponseEntity<List<Cart>> getCartItems(@PathVariable String userEmail) {
        Optional<User> user = userService.findByEmailId(userEmail);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Cart> cartItems = cartService.getCartItems(user);
        return ResponseEntity.ok(cartItems);
    }
    @DeleteMapping("api/carts/{userEmail}/{cartItemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable String userEmail, @PathVariable Long cartItemId) {
        User user = userService.findByEmail(userEmail); // Find user by email
        if (user == null) {
            // Handle user not found error
            return ResponseEntity.notFound().build();
        }
        try {
            cartService.removeCartItem(user, cartItemId); // Remove cart item for the user
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            // Handle unauthorized access or cart item not found error
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Handle other unexpected errors
            return ResponseEntity.status(500).build();
        }
    }
    @PutMapping("/api/carts/{userEmail}/{cartItemId}/increment")
    public ResponseEntity<Cart> incrementCartItem(@PathVariable String userEmail, @PathVariable Long cartItemId) {
        try {
            Cart updatedCartItem = cartService.updateCartItemQuantity(userEmail, cartItemId, 1);
            return ResponseEntity.ok(updatedCartItem);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/api/carts/{userEmail}/{cartItemId}/decrement")
    public ResponseEntity<Cart> decrementCartItem(@PathVariable String userEmail, @PathVariable Long cartItemId) {
        try {
            Cart updatedCartItem = cartService.updateCartItemQuantity(userEmail, cartItemId, -1);
            return ResponseEntity.ok(updatedCartItem);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }



    // Static inner class representing the request body for adding a product to the cart
    static class AddToCartRequest {
        private String userEmail;
        private Product product;

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }
    }
}
