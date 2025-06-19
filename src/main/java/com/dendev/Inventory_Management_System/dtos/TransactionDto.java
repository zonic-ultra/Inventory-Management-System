package com.dendev.Inventory_Management_System.dtos;

import com.dendev.Inventory_Management_System.enums.TransactionStatus;
import com.dendev.Inventory_Management_System.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDto {
    private Long id;

    private Integer totalProducts;

    private BigDecimal totalPrice;

    private TransactionType transactionType; //purchase, sale, return

    private TransactionStatus transactionStatus;//pending, completed, processing

    private String description;

    private String note;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private ProductDto product;

    private UserDto user;

    private SupplierDto supplier;
}
