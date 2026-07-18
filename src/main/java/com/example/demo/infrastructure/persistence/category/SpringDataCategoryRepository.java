package com.example.demo.infrastructure.persistence.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataCategoryRepository  extends JpaRepository<CategoryEntity, Long> {
    boolean existsByName(String name);
}
