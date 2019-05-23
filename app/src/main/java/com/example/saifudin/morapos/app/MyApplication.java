package com.example.saifudin.morapos.app;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

//Kita harus membuat konfigurasi default Realm ke dalam class yang meng-extends Application.
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("pemesanan.db")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}
