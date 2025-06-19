package com.dendev.Inventory_Management_System.dtos;

import com.dendev.Inventory_Management_System.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    @NotBlank(message = "Product Id is required!")
    private Long productId;

    @NotBlank(message = "Quantity is required!")
    private Integer quantity;

    private Long supplierId;

    private String description;

    private String note;
}
