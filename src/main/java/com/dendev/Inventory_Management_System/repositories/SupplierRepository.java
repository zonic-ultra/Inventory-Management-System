package com.dendev.Inventory_Management_System.repositories;

import com.dendev.Inventory_Management_System.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
