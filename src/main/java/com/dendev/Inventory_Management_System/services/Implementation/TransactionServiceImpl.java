package com.dendev.Inventory_Management_System.services.Implementation;

import com.dendev.Inventory_Management_System.dtos.Response;
import com.dendev.Inventory_Management_System.dtos.TransactionDto;
import com.dendev.Inventory_Management_System.dtos.TransactionRequest;
import com.dendev.Inventory_Management_System.enums.TransactionStatus;
import com.dendev.Inventory_Management_System.enums.TransactionType;
import com.dendev.Inventory_Management_System.exceptions.NameValueRequiredException;
import com.dendev.Inventory_Management_System.exceptions.NotFoundException;
import com.dendev.Inventory_Management_System.models.Product;
import com.dendev.Inventory_Management_System.models.Supplier;
import com.dendev.Inventory_Management_System.models.Transaction;
import com.dendev.Inventory_Management_System.models.User;
import com.dendev.Inventory_Management_System.repositories.ProductRepository;
import com.dendev.Inventory_Management_System.repositories.SupplierRepository;
import com.dendev.Inventory_Management_System.repositories.TransactionRepository;
import com.dendev.Inventory_Management_System.services.TransactionService;
import com.dendev.Inventory_Management_System.services.UserService;
import com.dendev.Inventory_Management_System.specification.TransactionFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public Response purchase(TransactionRequest transactionRequest) {
        Long productId = transactionRequest.getProductId();
        Long supplierId = transactionRequest.getSupplierId();
        Integer quantity =  transactionRequest.getQuantity();

        if(supplierId == null) throw new NameValueRequiredException("Supplier Id is required");

        Product product = productRepository.findById(productId)
                .orElseThrow(()->new NotFoundException("Product not found!"));

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(()->new NotFoundException("Supplier not found!"));

        User user = userService.getCurrentLoggedInUsers();

        //update the stock quantity and re-save
        product.setStockQuantity(product.getStockQuantity() + quantity);
        productRepository.save(product);

        //create transaction
        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.PURCHASE)
                .transactionStatus(TransactionStatus.COMPLETED)
                .product(product)
                .user(user)
                .supplier(supplier)
                .totalProducts(quantity)
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .description(transactionRequest.getDescription())
                .note(transactionRequest.getNote())
                .build();
        transactionRepository.save(transaction);

        return Response.builder()
                .status(200)
                .message("Purchase made successfully")
                .build();

    }

    @Override
    public Response sell(TransactionRequest transactionRequest) {
        Long productId = transactionRequest.getProductId();
        Integer quantity =  transactionRequest.getQuantity();

        Product product = productRepository.findById(productId)
                .orElseThrow(()->new NotFoundException("Product not found!"));

        User user = userService.getCurrentLoggedInUsers();

        //update the stock quantity and re-save
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);

        //create a transaction
        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.PURCHASE)
                .transactionStatus(TransactionStatus.COMPLETED)
                .product(product)
                .user(user)
                .totalProducts(quantity)
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .description(transactionRequest.getDescription())
                .note(transactionRequest.getNote())
                .build();
        transactionRepository.save(transaction);

        return Response.builder()
                .status(200)
                .message("Product Sale made successfully")
                .build();

    }

    @Override
    public Response returnToSupplier(TransactionRequest transactionRequest) {

        Long productId = transactionRequest.getProductId();
        Long supplierId = transactionRequest.getSupplierId();
        Integer quantity =  transactionRequest.getQuantity();

        if(supplierId == null) throw new NameValueRequiredException("Supplier Id is required");

        Product product = productRepository.findById(productId)
                .orElseThrow(()->new NotFoundException("Product not found!"));

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(()->new NotFoundException("Supplier not found!"));

        User user = userService.getCurrentLoggedInUsers();

        //update the stock quantity and re-save
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);

        //create a transaction
        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.RETURN_TO_SUPPLIER)
                .transactionStatus(TransactionStatus.PROCESSING)
                .product(product)
                .user(user)
                .supplier(supplier)
                .totalProducts(quantity)
                .totalPrice(BigDecimal.ZERO)
                .description(transactionRequest.getDescription())
                .note(transactionRequest.getNote())
                .build();
        transactionRepository.save(transaction);

        return Response.builder()
                .status(200)
                .message("Product returned in progress")
                .build();

    }

    @Override
    public Response getAllTransactions(int page, int size, String filter) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));


        //use the Transaction specification
        Specification<Transaction> transactionSpecification = TransactionFilter.byFilter(filter);
        Page<Transaction> transactionPage = transactionRepository.findAll(transactionSpecification, pageable);

        List<TransactionDto> transactionDtos = modelMapper.map(transactionPage.getContent(),new TypeToken<List<List<TransactionDto>>>(){}.getType());

        transactionDtos.forEach(transactionDto -> {
            transactionDto.setUser(null);
            transactionDto.setProduct(null);
            transactionDto.setSupplier(null);
        });
        return Response.builder()
                .status(200)
                .message("success")
                .transactions(transactionDtos)
                .totalElements(transactionPage.getTotalElements())
                .totalPages(transactionPage.getTotalPages())
                .build();

    }

    @Override
    public Response getTransactionById(Long id) {

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Transaction not found!"));

        TransactionDto transactionDto = modelMapper.map(transaction, TransactionDto.class);

        transactionDto.setUser(null);

        return Response.builder()
                .status(200)
                .message("success")
                .transaction(transactionDto)
                .build();
    }

    @Override
    public Response getTransactionsByMonthAndYear(int month, int year) {

        List<Transaction> transactionList = transactionRepository.findAll(TransactionFilter.byMonthAndYear(month, year));
        List<TransactionDto> transactionDtoList = modelMapper.map(transactionList, new TypeToken<List<TransactionDto>>() {}.getType());

        transactionDtoList.forEach(transactionDto -> {
            transactionDto.setUser(null);
            transactionDto.setProduct(null);
            transactionDto.setSupplier(null);
        });

        return Response.builder()
                .status(200)
                .message("success")
                .transactions(transactionDtoList)
                .build();
    }

    @Override
    public Response getTransactionStatus(Long transactionId, TransactionStatus transactionStatus) {
        Transaction exitingTransaction = transactionRepository.findById(transactionId)
                .orElseThrow(()-> new NotFoundException("Transaction not found!"));

        exitingTransaction.setTransactionStatus(transactionStatus);
        exitingTransaction.setUpdatedAt(LocalDateTime.now());

        transactionRepository.save(exitingTransaction);

        return Response.builder()
                .status(200)
                .message("Transaction status successfully updated")
                .build();
    }
}
