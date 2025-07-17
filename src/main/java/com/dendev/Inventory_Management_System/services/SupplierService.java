package com.dendev.Inventory_Management_System.services;

import com.dendev.Inventory_Management_System.dtos.Response;
import com.dendev.Inventory_Management_System.dtos.SupplierDto;

public interface SupplierService {
    Response addSupplier(SupplierDto supplierDto);

    Response getAllSuppliers();

    Response getSupplierById(Long id);

    Response updateSupplier(Long id, SupplierDto supplierDto);

    Response deleteSupplier(Long id);


}
