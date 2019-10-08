package com.bonzenshop.BackendService.controller;

import com.bonzenshop.BackendService.model.Product;
import com.bonzenshop.BackendService.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShopController {

    private ProductService productService;

    public ShopController(ProductService productService){
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/productList")
    public List<Product> getProductList() {
        return productService.getProducts();
    }

}
