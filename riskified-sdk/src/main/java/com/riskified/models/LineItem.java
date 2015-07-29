package com.riskified.models;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.jar.Attributes;

public class LineItem implements IValidated {

    private Double price;
    private Integer quantity;
    private String title;
    private String sku;
    private Integer productId;
    private String fulfillmentService;
    private String fulfillmentStatus;
    private Float grams;
    private String id;
    private String variantId;
    private String variantTitle;
    private String variantInventoryManagement;
    private String vendor;
    private String name;
    private Boolean requiresShipping;
    private Boolean taxable;
    private Boolean productExists;
    private List<Attributes> properties;
    private List<TaxLine> taxLines;
    private String eventSubCategoryName;
    private String eventName;
    private String eventSectionName;
    private String eventCountry;
    private String eventCity;
    private String eventLocation;
    private Date eventDate;
    private String condition;
    private Seller seller;
    private String brand;


    public LineItem(double price, int quantity, String title, int productId) {
        this.properties = new ArrayList<Attributes>();
        this.taxLines = new ArrayList<TaxLine>();
        this.price = price;
        this.quantity = quantity;
        this.title = title;
        this.productId = productId;
    }

    public void validate(Validation validationType)
    throws FieldBadFormatException {

        if (validationType == Validation.ALL) {
            Validate.notNull(this, this.price, "Price");
            Validate.notNull(this, this.quantity, "Quantity");
            Validate.notNullOrEmpty(this, this.title, "Title");
            Validate.notNull(this, this.productId, "Product Id");
        }

        if (seller != null) {
            seller.validate(validationType);
        }

    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getFulfillmentService() {
        return fulfillmentService;
    }

    public void setFulfillmentService(String fulfillmentService) {
        this.fulfillmentService = fulfillmentService;
    }

    public String getFulfillmentStatus() {
        return fulfillmentStatus;
    }

    public void setFulfillmentStatus(String fulfillmentStatus) {
        this.fulfillmentStatus = fulfillmentStatus;
    }

    public Float getGrams() {
        return grams;
    }

    public void setGrams(Float grams) {
        this.grams = grams;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public String getVariantTitle() {
        return variantTitle;
    }

    public void setVariantTitle(String variantTitle) {
        this.variantTitle = variantTitle;
    }

    public String getVariantInventoryManagement() {
        return variantInventoryManagement;
    }

    public void setVariantInventoryManagement(String variantInventoryManagement) {
        this.variantInventoryManagement = variantInventoryManagement;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRequiresShipping() {
        return requiresShipping;
    }

    public void setRequiresShipping(Boolean requiresShipping) {
        this.requiresShipping = requiresShipping;
    }

    public Boolean getTaxable() {
        return taxable;
    }

    public void setTaxable(Boolean taxable) {
        this.taxable = taxable;
    }

    public Boolean getProductExists() {
        return productExists;
    }

    public void setProductExists(Boolean productExists) {
        this.productExists = productExists;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public List<Attributes> getProperties() {
        return properties;
    }

    public void setProperties(List<Attributes> properties) {
        this.properties = properties;
    }

    public List<TaxLine> getTaxLines() {
        return taxLines;
    }

    public void setTaxLines(List<TaxLine> taxLines) {
        this.taxLines = taxLines;
    }

    public String getEventSubCategoryName() {
        return eventSubCategoryName;
    }

    public void setEventSubCategoryName(String eventSubCategoryName) {
        this.eventSubCategoryName = eventSubCategoryName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventSectionName() {
        return eventSectionName;
    }

    public void setEventSectionName(String eventSectionName) {
        this.eventSectionName = eventSectionName;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getEventCountry() {
		return eventCountry;
	}

	public void setEventCountry(String eventCountry) {
		this.eventCountry = eventCountry;
	}

	public String getEventCity() {
		return eventCity;
	}

	public void setEventCity(String eventCity) {
		this.eventCity = eventCity;
	}

	public String getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}


}
