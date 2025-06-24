package com.dendev.Inventory_Management_System.services;

import com.dendev.Inventory_Management_System.dtos.CategoryDto;
import com.dendev.Inventory_Management_System.dtos.Response;

public interface CategoryService {
    Response createCategory(CategoryDto categoryDto);

    Response getAllCategories();

    Response getCategoryById(Long id);

    Response updateCategory(Long id, CategoryDto categoryDto);

    Response deleteCategory(Long id);


}
