package com.dendev.Inventory_Management_System.controller;

import com.dendev.Inventory_Management_System.dtos.ProductDto;
import com.dendev.Inventory_Management_System.dtos.Response;
import com.dendev.Inventory_Management_System.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<Response> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> saveProduct(
    @RequestParam("imageFile")MultipartFile imageFile,
    @RequestParam("name")String name,
    @RequestParam("sku")String sku,
    @RequestParam("price") BigDecimal price,
    @RequestParam("stockQuantity")Integer stockQuantity,
    @RequestParam("categoryId")Long categoryId,
    @RequestParam(value = "description", required = false)String description){
            ProductDto productDto = new ProductDto();
                productDto.setName(name);
                productDto.setSku(sku);
                productDto.setPrice(price);
                productDto.setStockQuantity(stockQuantity);
                productDto.setCategoryId(categoryId);
                productDto.setDescription(description);

        return ResponseEntity.ok(productService.saveProduct(productDto,imageFile));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateProduct(
            @RequestParam("productId")Long productId,
            @RequestParam(value = "imageFile", required = false)MultipartFile imageFile,
            @RequestParam(value = "name", required = false)String name,
            @RequestParam(value = "sku", required = false)String sku,
            @RequestParam(value = "price", required = false) BigDecimal price,
            @RequestParam(value = "stockQuantity", required = false)Integer stockQuantity,
            @RequestParam(value = "categoryId", required = false)Long categoryId,
            @RequestParam(value = "description", required = false)String description){
        ProductDto productDto = new ProductDto();
            productDto.setProductId(productId);
            productDto.setName(name);
            productDto.setSku(sku);
            productDto.setPrice(price);
            productDto.setStockQuantity(stockQuantity);
            productDto.setCategoryId(categoryId);
            productDto.setDescription(description);

        return ResponseEntity.ok(productService.updateProduct(productDto,imageFile));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteProduct(@PathVariable Long id){
        return ResponseEntity.ok(productService.deleteProduct(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Response> searchProduct(@RequestParam String input){
        return ResponseEntity.ok(productService.searchProduct(input));
    }

}


