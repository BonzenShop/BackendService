package com.bonzenshop.BackendService.controller;

import com.bonzenshop.BackendService.model.Account;
import com.bonzenshop.BackendService.model.Product;
import com.bonzenshop.BackendService.service.DatabaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping
public class ShopController {

    public ShopController(){

    }

    @GetMapping("/productList")
    public List<Product> getProductList() throws SQLException {
        return DatabaseService.getProducts();
    }

    @PreAuthorize("hasAnyRole('Mitarbeiter', 'Admin')")
    @GetMapping("/userList")
    public List<Account> getUserList() throws SQLException {
        return DatabaseService.getAccounts();
    }

}
