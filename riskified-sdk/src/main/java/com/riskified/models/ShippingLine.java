package com.riskified.models;

import java.util.List;

import com.riskified.validations.*;

public class ShippingLine implements IValidated {
    private Double price;
    private String title;
    private String code;
    private String source;
    private List<TaxLine> taxLines;

    public ShippingLine(double price, String title) {
        this.price = price;
        this.title = title;
    }

    public void validate(Validation validationType) throws FieldBadFormatException {

        if (validationType == Validation.ALL) {
            Validate.notNull(this, this.price, "Price");
            Validate.notNullOrEmpty(this, this.title, "Title");
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<TaxLine> getTaxLines() {
        return taxLines;
    }

    public void setTaxLines(List<TaxLine> taxLines) {
        this.taxLines = taxLines;
    }
}
