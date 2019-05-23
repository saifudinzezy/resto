package com.example.saifudin.morapos.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseKategori{

	@SerializedName("path")
	private String path;

	@SerializedName("code")
	private int code;

	@SerializedName("kategori")
	private List<KategoriItem> kategori;

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

	public void setKategori(List<KategoriItem> kategori){
		this.kategori = kategori;
	}

	public List<KategoriItem> getKategori(){
		return kategori;
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
			"ResponseKategori{" + 
			"path = '" + path + '\'' + 
			",code = '" + code + '\'' + 
			",kategori = '" + kategori + '\'' + 
			",message = '" + message + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}
}