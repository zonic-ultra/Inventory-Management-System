package com.dendev.Inventory_Management_System.dtos;

import com.dendev.Inventory_Management_System.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    //Generic
    private int status;
    private String message;

    //For login
    private String token;
    private UserRole role;
    private String expirationTime;

    //For pagination
    private Integer totalPages;
    private Long totalElements;

    //Data output optionals
    private UserDto user;
    private List<UserDto> users;

    private SupplierDto supplier;
    private List<SupplierDto> suppliers;

    private CategoryDto category;
    private List<CategoryDto> categories;

    private ProductDto product;
    private List<ProductDto> products;

    private TransactionDto transaction;
    private List<TransactionDto> transactions;

    private final LocalDateTime timestamp = LocalDateTime.now();

}
