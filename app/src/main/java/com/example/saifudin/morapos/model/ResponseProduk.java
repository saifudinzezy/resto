package com.example.saifudin.morapos.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseProduk{

	@SerializedName("path")
	private String path;

	@SerializedName("code")
	private int code;

	@SerializedName("produk")
	private List<ProdukItem> produk;

	@SerializedName("message")
	private String message;

	@SerializedName("error")
	private String error;

	public void setPath(String path){
		this.path = path;
	}

	public String getPath(){
		return path;
	}

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setProduk(List<ProdukItem> produk){
		this.produk = produk;
	}

	public List<ProdukItem> getProduk(){
		return produk;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setError(String error){
		this.error = error;
	}

	public String getError(){
		return error;
	}

	@Override
 	public String toString(){
		return 
			"ResponseProduk{" + 
			"path = '" + path + '\'' + 
			",code = '" + code + '\'' + 
			",produk = '" + produk + '\'' + 
			",message = '" + message + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}
}