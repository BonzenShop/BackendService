package com.bonzenshop.BackendService.controller;

import com.bonzenshop.BackendService.exception.EntityNotFoundException;
import com.bonzenshop.BackendService.model.*;
import com.bonzenshop.BackendService.service.DatabaseService;
import com.bonzenshop.BackendService.service.JwtTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import javax.xml.ws.Response;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@CrossOrigin()
@RestController
@RequestMapping
public class ShopController {

    public ShopController(){

    }

    @GetMapping("/mainInfos")
    public ResponseEntity<MainInfos> getMainInfos() {
        try{
            return new ResponseEntity<MainInfos>(DatabaseService.getMainInfos().get(), HttpStatus.OK);
        }catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/productList")
    public List<Product> getProductList() throws SQLException {
        return DatabaseService.getProducts().get();
    }

    @PreAuthorize("hasAnyRole('Mitarbeiter', 'Admin')")
    @GetMapping("/userList")
    public List<Account> getUserList() throws SQLException {
        return DatabaseService.getAccounts();
    }

    @PostMapping("/order")
    public ResponseEntity placeOrder(@RequestBody List<Order> orderList) {
        int rowsAffected = DatabaseService.createOrder(orderList);
        if(rowsAffected > 0){
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('Mitarbeiter', 'Admin')")
    @GetMapping("/orderList")
    public ResponseEntity<List<Order>> getOrderList(){
        try{
            return new ResponseEntity<>(DatabaseService.getOrders().get(), HttpStatus.OK);
        }catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/myOrderList")
    public ResponseEntity<List<Order>> getMyOrderList() {
        try{
            String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            return new ResponseEntity<>(DatabaseService.getOrders(userEmail).get(), HttpStatus.OK);
        }catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAnyRole('Mitarbeiter', 'Admin')")
    @PostMapping("/saveProduct")
    public ResponseEntity<List<Product>> saveProduct(@RequestBody SaveProductRequest request) {
        int rowsAffected = 0;
        if(request.getProduct().getId() > 0){
            rowsAffected = DatabaseService.updateProduct(request);
        }else{
            rowsAffected = DatabaseService.addProduct(request);
        }
        if(rowsAffected > 0){
            return new ResponseEntity(DatabaseService.getProducts().get(), HttpStatus.OK);
        }else{
            return  new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('Admin')")
    @PostMapping("/changeRole")
    public ResponseEntity changeRole(@RequestBody ChangeRoleRequest request) {
        int rowsAffected = 0;
        rowsAffected = DatabaseService.changeRole(request);
        if(rowsAffected > 0){
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return  new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('Admin')")
    @PostMapping("/resetPassword")
    public ResponseEntity resetPassword(@RequestBody int userId) {
        int rowsAffected = 0;
        rowsAffected = DatabaseService.resetPassword(userId);
        if(rowsAffected > 0){
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return  new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('Mitarbeiter', 'Admin')")
    @PostMapping("/deleteProduct")
    public ResponseEntity<List<Product>> deleteProduct(@RequestBody int productId) {
        int rowsAffected = 0;
        rowsAffected = DatabaseService.deleteProduct(productId);
        if(rowsAffected > 0){
            return new ResponseEntity(DatabaseService.getProducts().get(), HttpStatus.OK);
        }else{
            return  new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getImages")
    public ResponseEntity<List<Image>> getImages() {
        try{
            return new ResponseEntity<List<Image>>(DatabaseService.getImages().get(), HttpStatus.OK);
        }catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //endpoint to redirect to index.html
    //is needed if a specific angular path is called, that the backend does not not
    //in this case it is redirected to the angular app
    @GetMapping("/**/{path:[^.]*}")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/");
    }

}
