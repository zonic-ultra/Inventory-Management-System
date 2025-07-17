package com.dendev.Inventory_Management_System.services;

import com.dendev.Inventory_Management_System.dtos.Response;
import com.dendev.Inventory_Management_System.dtos.TransactionRequest;
import com.dendev.Inventory_Management_System.enums.TransactionStatus;

public interface TransactionService {
    Response purchase(TransactionRequest transactionRequest);
    Response sell(TransactionRequest transactionRequest);
    Response returnToSupplier(TransactionRequest transactionRequest);
    Response getAllTransactions(int page, int size, String filter);
    Response getTransactionById(Long id);
    Response getTransactionsByMonthAndYear(int month, int year);
    Response getTransactionStatus(Long transactionId, TransactionStatus transactionStatus);
}
