package com.riskified.models;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

public class ShippingLine implements IValidated {
    private Double price;
    private String title;
    private String code;
    private String source;
    private TaxLines taxLines;

    public ShippingLine(double price, String title) {
        this.price = price;
        this.title = title;
    }
    
    public void validate(Validation validationType)
			throws FieldBadFormatException {
		
    	Validate.notNull(this.price, "Price");
    	Validate.stringNotNullOrEmpty(this.title, "Title");
		
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

    public TaxLines getTaxLines() {
        return taxLines;
    }

    public void setTaxLines(TaxLines taxLines) {
        this.taxLines = taxLines;
    }

	
}
