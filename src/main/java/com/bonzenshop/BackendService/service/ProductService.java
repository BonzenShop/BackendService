package com.bonzenshop.BackendService.service;

import com.bonzenshop.BackendService.model.Product;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

import static java.util.Arrays.asList;

@Service
public class ProductService {

    public List<Product> getProducts() throws SQLException {
        return DatabaseService.getProducts();
    }
}
