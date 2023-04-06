package com.bikkadit.electronicstore.repository;

import com.bikkadit.electronicstore.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product , Integer> {



    Page<Product> findByTitleContaining(String subTitle , Pageable pageable);

    Page<Product> findByLiveTrue(Pageable pageable);
}
