package com.elavarasanno3.amazonclone.repository;

import com.elavarasanno3.amazonclone.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
