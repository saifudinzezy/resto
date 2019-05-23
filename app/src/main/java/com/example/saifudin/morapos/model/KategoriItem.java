package com.example.saifudin.morapos.model;

import com.google.gson.annotations.SerializedName;
public class KategoriItem{

	@SerializedName("image")
	private String image;

	@SerializedName("code")
	private String code;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	public KategoriItem() {
	}

	public KategoriItem(String image, String code, String name, String id) {
		this.image = image;
		this.code = code;
		this.name = name;
		this.id = id;
	}

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

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"KategoriItem{" + 
			"image = '" + image + '\'' + 
			",code = '" + code + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}