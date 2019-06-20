package com.riskified.models;

import java.util.*;
import java.util.jar.Attributes;

import com.riskified.validations.*;

public class LineItem implements IValidated {

    private Double price;
    private Integer quantity;
    private String title;
    private String sku;
    private String productId;
    private String fulfillmentService;
    private String fulfillmentStatus;
    private Float grams;
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
	private String category;
    private String subCategory;
    private String condition;
    private Seller seller;
    private String brand;
    private String productType;
    private String size;
    private Date deliveredAt;
    private String deliveredTo;
    private String color;
    private RegistryType registryType;


    public LineItem(double price, int quantity, String title) {
        this.properties = new ArrayList<Attributes>();
        this.taxLines = new ArrayList<TaxLine>();
        this.price = price;
        this.quantity = quantity;
        this.title = title;
    }
    
    public LineItem(double price, int quantity, String title, String productId) {
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
    
    public String getColor() {
        return color;
    }

  
    public void setColor(String color) {
        this.color = color;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
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
    
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Date getDeliveredAt() {
		return deliveredAt;
	}

	public void setDeliveredAt(Date deliveredAt) {
		this.deliveredAt = deliveredAt;
	}

	public String getDeliveredTo() {
		return deliveredTo;
	}

	public void setDeliveredTo(String deliveredTo) {
		this.deliveredTo = deliveredTo;
	}

    public RegistryType getRegistryType() { return registryType; }

    public void setRegistryType(RegistryType registryType) { this.registryType = registryType; }





}
