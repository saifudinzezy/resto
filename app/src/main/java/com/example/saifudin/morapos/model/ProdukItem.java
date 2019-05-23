package com.example.saifudin.morapos.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProdukItem extends RealmObject{

	@SerializedName("image")
	private String image;

	@SerializedName("code")
	private String code;

	@SerializedName("cost")
	private String cost;

	@SerializedName("quantity")
	private String quantity;

	@SerializedName("tax")
	private String tax;

	@SerializedName("type")
	private String type;

	@SerializedName("tax_method")
	private String taxMethod;

	@SerializedName("category_id")
	private String categoryId;

	@SerializedName("price")
	private String price;

	@SerializedName("name")
	private String name;

	@SerializedName("barcode_symbology")
	private String barcodeSymbology;

	@SerializedName("details")
	private String details;

	@SerializedName("id")
	public Integer id;

	@SerializedName("alert_quantity")
	private String alertQuantity;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setCost(String cost){
		this.cost = cost;
	}

	public String getCost(){
		return cost;
	}

	public void setQuantity(String quantity){
		this.quantity = quantity;
	}

	public String getQuantity(){
		return quantity;
	}

	public void setTax(String tax){
		this.tax = tax;
	}

	public String getTax(){
		return tax;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setTaxMethod(String taxMethod){
		this.taxMethod = taxMethod;
	}

	public String getTaxMethod(){
		return taxMethod;
	}

	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}

	public String getCategoryId(){
		return categoryId;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setBarcodeSymbology(String barcodeSymbology){
		this.barcodeSymbology = barcodeSymbology;
	}

	public String getBarcodeSymbology(){
		return barcodeSymbology;
	}

	public void setDetails(String details){
		this.details = details;
	}

	public String getDetails(){
		return details;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
	}

	public void setAlertQuantity(String alertQuantity){
		this.alertQuantity = alertQuantity;
	}

	public String getAlertQuantity(){
		return alertQuantity;
	}

	@Override
 	public String toString(){
		return 
			"ProdukItem{" + 
			"image = '" + image + '\'' + 
			",code = '" + code + '\'' + 
			",cost = '" + cost + '\'' + 
			",quantity = '" + quantity + '\'' + 
			",tax = '" + tax + '\'' + 
			",type = '" + type + '\'' + 
			",tax_method = '" + taxMethod + '\'' + 
			",category_id = '" + categoryId + '\'' + 
			",price = '" + price + '\'' + 
			",name = '" + name + '\'' + 
			",barcode_symbology = '" + barcodeSymbology + '\'' + 
			",details = '" + details + '\'' + 
			",id = '" + id + '\'' + 
			",alert_quantity = '" + alertQuantity + '\'' + 
			"}";
		}
}