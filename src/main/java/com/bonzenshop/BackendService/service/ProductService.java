package com.bonzenshop.BackendService.service;

import com.bonzenshop.BackendService.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.asList;

@Service
public class ProductService {
    //Dummy Daten bis DB eingerichtet ist
    private static final List<Product> PRODUCTS = asList(
            new Product(1, "Kamel", "Transportmittel auf vier Beinen und mit zwei Höckern.", "transport" , 250000.00, 15),
            new Product(2, "Yacht", "Die Yacht, mit der Sie auf keinen Fall übersehen werden.", "transport", 1500000.00, 4),
            new Product(3, "Rolex", "Diese mit Diamanten besetzte Armbanduhr muss auf jeden Fall zu sehen sein.", "accessoires", 100000.00, 50),
            new Product(4, "Diamant", "Ein geschliffener Diamant mit 3 Karat", "sonstiges", 20000000.00, 2),
            new Product(5, "Untertan", "Dieser Untertan wird für Sie alle Aufgaben erledigen", "sontiges", 5000000.00, 20)
    );

    public List<Product> getProducts() {
        //TODO Produktliste von DB holen
        return PRODUCTS;
    }
}
