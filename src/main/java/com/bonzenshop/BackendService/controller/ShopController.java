package com.bonzenshop.BackendService.controller;

import com.bonzenshop.BackendService.model.Product;
import com.bonzenshop.BackendService.service.ProductService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ShopController {

    private ProductService productService;

    public ShopController(ProductService productService){
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/productList")
    public List<Product> getProductList() throws SQLException {
        return productService.getProducts();
    }

}
