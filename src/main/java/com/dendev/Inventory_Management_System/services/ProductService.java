package com.dendev.Inventory_Management_System.services;

import com.dendev.Inventory_Management_System.dtos.ProductDto;
import com.dendev.Inventory_Management_System.dtos.Response;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    Response saveProduct(ProductDto productDto, MultipartFile imageFile);

    Response updateProduct(ProductDto productDto, MultipartFile imageFile);

    Response getAllProducts();

    Response getProductById(Long id);

    Response deleteProduct(Long id);

    Response searchProduct(String input);

}
