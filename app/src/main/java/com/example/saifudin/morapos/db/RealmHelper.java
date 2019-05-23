package com.example.saifudin.morapos.db;

import android.util.Log;
import android.widget.HeaderViewListAdapter;
import android.widget.Toast;

import com.example.saifudin.morapos.db.model.Pemesanan;
import com.example.saifudin.morapos.model.ProdukItem;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

//Class Helper ini digunakan sebagai class pembantu dalam proses mengakses database.
public class RealmHelper {
    Realm realm;
    RealmHelperListener listener;

    public RealmHelperListener getListener() {
        return listener;
    }

    public void setListener(RealmHelperListener listener) {
        this.listener = listener;
    }

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    // untuk menyimpan data
    public void save(final Pemesanan pemesanan) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null) {
                    Log.e("Created", "Database was created");
                    Number currentIdNum = realm.where(Pemesanan.class).max("id");
                    int nextId;
                    if (currentIdNum == null) {
                        nextId = 1;
                    } else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    pemesanan.setId(nextId);
                    pemesanan.setQuantity(1);
                    pemesanan.setProdukItem(pemesanan.produkItem);

                    realm.copyToRealm(pemesanan);
                } else {
                    Log.e("ppppp", "execute: Database not Exist");
                }
            }
        });
    }

    // untuk memanggil semua data
    public List<Pemesanan> getAllMahasiswa() {
        RealmResults<Pemesanan> results = realm.where(Pemesanan.class).findAll();
        return results;
    }

    //increment and decrement value
    public void initNewCart(final ProdukItem product, final int quantity) {
        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Pemesanan cartItem = realm.where(Pemesanan.class)
                        .equalTo("id", product.id)
                        .findFirst();
                if (cartItem == null) {
                    Pemesanan ci = new Pemesanan();
                    ci.id = product.id;
                    ci.produkItem = product;
                    ci.setQuantity(quantity);
                    realm.copyToRealmOrUpdate(ci);
                    Log.e("notif", "onSuccess: Save nih");
                } else {
                    int x = cartItem.getQuantity() + quantity;
                    cartItem.setQuantity(x);
//                    cartItem.produkItem = product;
                    Log.e("notif", "onSuccess: Update nih");
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e("notif", "onSuccess: Update Successfully");
                listener.reset();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e("notif", "error " + error.getMessage());
            }
        });
    }

    public void removeCartItem(final ProdukItem product) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Pemesanan cartItem = realm.where(Pemesanan.class).equalTo("id", product.getId()).findFirst();
                if (cartItem != null) {
                    if (cartItem.quantity == 1) {
                        cartItem.deleteFromRealm();
                    } else {
                        cartItem.quantity -= 1;
                        realm.copyToRealmOrUpdate(cartItem);
                    }
                }
            }
        });
    }

    public static int getCartPrice(List<Pemesanan> cartItems) {
        int price = 0;
        for (int i = 0; i < cartItems.size(); i++) {
            double prices = Double.parseDouble(cartItems.get(i).getProdukItem().getPrice());
            price += prices * cartItems.get(i).getQuantity();
        }
        return price;
    }

    public interface RealmHelperListener {
        void reset();
    }

    // untuk menghapus data
    public void delete(Integer id){
        final RealmResults<Pemesanan> model = realm.where(Pemesanan.class).equalTo("id", id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                model.deleteFromRealm(0);
            }
        }/*, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e("notif", "onSuccess: delete nih");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e("notif", "onError: error nih "+error.getMessage());
            }
        }*/);
    }
}