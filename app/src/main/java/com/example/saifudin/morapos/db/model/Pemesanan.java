package com.example.saifudin.morapos.db.model;

import com.example.saifudin.morapos.model.ProdukItem;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Pemesanan extends RealmObject {
    @PrimaryKey
    public Integer id;
    public ProdukItem produkItem;
    public int quantity = 0;

    public Pemesanan() {
    }

    public Pemesanan(Integer id, ProdukItem produkItem, int quantity) {
        this.id = id;
        this.produkItem = produkItem;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProdukItem getProdukItem() {
        return produkItem;
    }

    public void setProdukItem(ProdukItem produkItem) {
        this.produkItem = produkItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
