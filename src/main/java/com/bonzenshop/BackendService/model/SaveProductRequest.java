package com.bonzenshop.BackendService.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Das Objekt, welches genutzt wird, wenn ein Request zum Speichern (Ändern/Hinzufügen) eines Produktes empfangen wird.
 */
public class SaveProductRequest {
    /**
     * Das Produkt, welches gespeichert werden soll.
     */
    private Product product;
    /**
     * Das dem Produkt zugehörige Bild.
     */
    private Image image;

    /**
     * Konstruktor für den Endpunkt. Alle Attribute werden übergeben und übernommen.
     * @param product Das Produkt.
     * @param image Das Produktbild.
     */
    @JsonCreator
    public SaveProductRequest(@JsonProperty Product product, @JsonProperty Image image) {
        this.product = product;
        this.image = image;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
