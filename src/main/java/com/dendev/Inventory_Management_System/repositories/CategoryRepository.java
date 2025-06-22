package com.dendev.Inventory_Management_System.repositories;

import com.dendev.Inventory_Management_System.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
