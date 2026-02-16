package com.s23358.ecommercex.product.repository;

import com.s23358.ecommercex.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findAllByBelongsToCategory_Id(Long categoryId, Pageable pageable);
    boolean existsByNameAndBelongsToCategory_Id(String name, Long categoryId);

}
