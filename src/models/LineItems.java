package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.jar.Attributes;

public class LineItems {
	Float price;
	Integer quantity;
	String title;
	String sku;
	String product_id;
	String fulfillment_service;
	String fulfillment_status;
	Float grams;
	String id;
	String variant_id;
	String variant_title;
	String variant_inventory_management;
	String vendor;
	String name;
	Boolean requires_shipping;
	Boolean taxable;
	Boolean product_exists;
	ArrayList<Attributes> properties;
	ArrayList<TaxLines> tax_lines;
	String event_sub_category_name;
	String event_name;
	String event_section_name;
	Date event_date;
}
