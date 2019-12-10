package com.bonzenshop.BackendService.controller;

import com.bonzenshop.BackendService.model.*;
import com.bonzenshop.BackendService.service.DatabaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Der Controller, welcher alle Endpunkte beinhaltet, die nicht mit der Authentifizierung zu tun haben.
 */
@CrossOrigin()
@RestController
@RequestMapping
public class ShopController {

    public ShopController(){

    }

    /**
     * Endpunkt zum Laden der MainInfos des Webshops
     * @return HTTP-Response mit den MainInfos im Body. Status-Code 404 falls der Datenbankaufruf fehlschlägt.
     */
    @GetMapping("/mainInfos")
    public ResponseEntity<MainInfos> getMainInfos() {
        try{
            return new ResponseEntity<>(DatabaseService.getMainInfos().get(), HttpStatus.OK);
        }catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpunkt zum Laden der Produktliste
     * @return HTTP-Response mit der Liste aller Produkte im Body. Status-Code 404 falls der Datenbankaufruf fehlschlägt.
     */
    @GetMapping("/productList")
    public ResponseEntity<List<Product>> getProductList() {
        try{
            return new ResponseEntity<>(DatabaseService.getProducts().get(), HttpStatus.OK);
        }catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpunkt zum Laden der Benutzerliste. Dieser Endpunkt ist nur für Benutzer mit den Rollen Mitarbeiter oder Admin erreichbar.
     * @return HTTP-Response mit der Liste aller Benutzerkonten im Body. Status-Code 404 falls der Datenbankaufruf fehlschlägt.
     */
    @PreAuthorize("hasAnyRole('Mitarbeiter', 'Admin')")
    @GetMapping("/userList")
    public ResponseEntity<List<Account>> getUserList() {
        try{
            return new ResponseEntity<>(DatabaseService.getAccounts().get(), HttpStatus.OK);
        }catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpunkt zum Erstellen einer Bestellung. Dieser Endpunkt ist nur für eingeloggte Benutzer erreichbar.
     * @param orderList Liste der zur erstellenden Bestellungen.
     * @return HTTP-Response ohne Body. Status-Code 400 falls der Datenbankaufruf fehlschlägt.
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/order")
    public ResponseEntity placeOrder(@RequestBody List<Order> orderList) {
        int rowsAffected = DatabaseService.createOrder(orderList);
        if(rowsAffected > 0){
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpunkt zum Laden der Liste aller Bestellungen. Dieser Endpunkt ist nur für Benutzer mit den Rollen Mitarbeiter oder Admin erreichbar.
     * @return HTTP-Response mit Liste aller Bestellungen im Body. Status-Code 404 falls der Datenbankaufruf fehlschlägt.
     */
    @PreAuthorize("hasAnyRole('Mitarbeiter', 'Admin')")
    @GetMapping("/orderList")
    public ResponseEntity<List<Order>> getOrderList(){
        try{
            return new ResponseEntity<>(DatabaseService.getOrders().get(), HttpStatus.OK);
        }catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpunkt zum Laden der Liste aller eigenen Bestellungen. Dieser Endpunkt ist nur für eingeloggte Benutzer erreichbar.
     * @return HTTP-Response mit Liste aller eigenen Bestellungen im Body. Status-Code 404 falls der Datenbankaufruf fehlschlägt.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/myOrderList")
    public ResponseEntity<List<Order>> getMyOrderList() {
        try{
            String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            return new ResponseEntity<>(DatabaseService.getOrders(userEmail).get(), HttpStatus.OK);
        }catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpunkt zum Speichern (Ändern/Hinzufügen) eines Produktes. Dieser Endpunkt ist nur für Benutzer mit den Rollen Mitarbeiter oder Admin erreichbar.
     * @param request Beinhaltet das zu speichernde Produkt und das dazugehörige Produktbild.
     * @return HTTP-Response mit der neuen Liste aller Produkte im Body. Status-Code 400 falls der Datenbankaufruf fehlschlägt.
     */
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

    /**
     * Endpunkt zum der Rolle eines Benutzers. Dieser Endpunkt ist nur für Benutzer mit der Rolle Admin erreichbar.
     * @param request ID des Benutzers, dessen Rolle geändert werden soll und die neue Rolle.
     * @return HTTP-Response ohne Body. Status-Code 400 falls der Datenbankaufruf fehlschlägt.
     */
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

    /**
     * Endpunkt zum Zurücksetzen des Passworts eines Benutzers. Dieser Endpunkt ist nur für Benutzer mit der Rolle Admin erreichbar.
     * @param userId ID des Benutzers, dessen Passwort zurückgesetzt werden soll.
     * @return HTTP-Response ohne Body. Status-Code 400 falls der Datenbankaufruf fehlschlägt.
     */
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

    /**
     * Endpunkt zum Löschen eines Produktes. Dieser Endpunkt ist nur für Benutzer mit den Rollen Mitarbeiter oder Admin erreichbar.
     * @param productId ID des Produktes, welches gelöscht werden soll.
     * @return HTTP-Response mit der neuen Liste aller Produkte im Body. Status-Code 400 falls der Datenbankaufruf fehlschlägt.
     */
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

    /**
     * Endpunkt zum Laden aller Produktbilder. Dieser Aufruf kann mehr Zeit in Anspruch nehmen als alle anderen Aufrufe.
     * @return HTTP-Response mit Liste aller Produktbilder im Body. Status-Code 404 falls der Datenbankaufruf fehlschlägt.
     */
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
    /**
     * Endpunkt, welcher zur index.html umleitet.
     * Dieser ist nötig, wenn ein bestimmter Pfad in der Angular-App direkt geladen werden soll, da die Angular-App nur über die index.html erreicht werden kann.
     * Dieser Endpunkt wird nur erreicht, wenn kein anderer Endpunkt erreicht wurde.
     * @param response Die HTTP-Response, welche vom System erstellt wurde.
     * @throws IOException Falls das Redirect fehlschlägt.
     */
    @GetMapping("/**/{path:[^.]*}")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/");
    }

}
