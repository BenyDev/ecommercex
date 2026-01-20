package com.s23358.ecommercex.product.repository;

import com.s23358.ecommercex.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByBelongsToCategory_Id(Long categoryId);
}
