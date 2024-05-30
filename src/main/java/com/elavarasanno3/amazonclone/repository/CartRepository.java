package com.elavarasanno3.amazonclone.repository;

import com.elavarasanno3.amazonclone.model.Cart;
import com.elavarasanno3.amazonclone.model.Product;
import com.elavarasanno3.amazonclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);
    Optional<Cart> findByUserAndProduct(User user, Product product);
}
