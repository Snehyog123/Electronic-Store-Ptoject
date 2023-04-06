package com.bikkadit.electronicstore.repository;

import com.bikkadit.electronicstore.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category , Long> {

   Optional<List<Category>> findByTitleContaining(String keyword);

}
