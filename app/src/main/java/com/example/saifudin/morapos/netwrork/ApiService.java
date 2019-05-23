package com.example.saifudin.morapos.netwrork;

import com.example.saifudin.morapos.model.ResponseKategori;
import com.example.saifudin.morapos.model.ResponseProduk;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("produk/")
    Call<ResponseProduk> getProduk();

    @GET("produk/")
    Call<ResponseProduk> getProduk(@Query("id") String id);

    @GET("kategori/")
    Call<ResponseKategori> getKategori();
}