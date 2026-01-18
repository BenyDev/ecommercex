package com.s23358.ecommercex.wishList.repository;

import com.s23358.ecommercex.wishList.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList,Long> {
}
