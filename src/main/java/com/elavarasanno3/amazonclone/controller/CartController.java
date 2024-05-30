package com.elavarasanno3.amazonclone.controller;

import com.elavarasanno3.amazonclone.model.Cart;
import com.elavarasanno3.amazonclone.model.Product;
import com.elavarasanno3.amazonclone.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:3000")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/api/carts/add")
    public ResponseEntity<Cart> addToCart(@RequestBody CartRequest cartRequest) {
        // Assuming you have some way to determine if the user is authenticated
        boolean isAuthenticated = checkIfUserIsAuthenticated();

        // If user is not authenticated, return unauthorized status
        if (!isAuthenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Assuming you have a way to get the authenticated user's email
        String userEmail = getAuthenticatedUserEmail();

        // Add product to cart for the authenticated user
        Cart cart = cartService.addProductToCart(userEmail, cartRequest.getProduct());

        // Return the cart item in the response
        return ResponseEntity.ok(cart);
    }

    // Static inner class representing the request body for adding a product to the cart
    static class CartRequest {
        private Product product;

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }
    }

    // Mock method to check if user is authenticated (replace with your actual implementation)
    private boolean checkIfUserIsAuthenticated() {
        // Replace this with your authentication logic
        return true;
    }

    // Mock method to get authenticated user's email (replace with your actual implementation)
    private String getAuthenticatedUserEmail() {
        // Replace this with your logic to get the authenticated user's email
        return "user@example.com";
    }
}
