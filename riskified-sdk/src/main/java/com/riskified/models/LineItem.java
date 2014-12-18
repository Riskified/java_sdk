package com.riskified.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.jar.Attributes;

public class LineItem {

    private double price;
    private Integer quantity;
    private String title;
    private String sku;
    private String productId;
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
    private List<TaxLines> taxLines;
    private String eventSubCategoryName;
    private String eventName;
    private String eventSectionName;
    private Date eventDate;

    public LineItem(double price, int quantity, String title, String productId, String sku) {
        properties = new ArrayList<Attributes>();
        taxLines = new ArrayList<TaxLines>();
        this.price = price;
        this.quantity = quantity;
        this.title = title;
        this.productId = productId;
        this.sku = sku;
    }

    @Override
    public String toString() {
        return "LineItem{" +
                "price=" + price +
                ", quantity=" + quantity +
                ", title='" + title + '\'' +
                ", sku='" + sku + '\'' +
                ", productId='" + productId + '\'' +
                ", fulfillmentService='" + fulfillmentService + '\'' +
                ", fulfillmentStatus='" + fulfillmentStatus + '\'' +
                ", grams=" + grams +
                ", id='" + id + '\'' +
                ", variantId='" + variantId + '\'' +
                ", variantTitle='" + variantTitle + '\'' +
                ", variantInventoryManagement='" + variantInventoryManagement + '\'' +
                ", vendor='" + vendor + '\'' +
                ", name='" + name + '\'' +
                ", requiresShipping=" + requiresShipping +
                ", taxable=" + taxable +
                ", productExists=" + productExists +
                ", properties=" + properties +
                ", taxLines=" + taxLines +
                ", eventSubCategoryName='" + eventSubCategoryName + '\'' +
                ", eventName='" + eventName + '\'' +
                ", eventSectionName='" + eventSectionName + '\'' +
                ", eventDate=" + eventDate +
                '}';
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

    public List<Attributes> getProperties() {
        return properties;
    }

    public void setProperties(List<Attributes> properties) {
        this.properties = properties;
    }

    public List<TaxLines> getTaxLines() {
        return taxLines;
    }

    public void setTaxLines(List<TaxLines> taxLines) {
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
}
