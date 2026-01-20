package com.s23358.ecommercex.category.repository;

import com.s23358.ecommercex.category.dto.CategoryDto;
import com.s23358.ecommercex.category.entity.Category;
import com.s23358.ecommercex.res.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);


}
