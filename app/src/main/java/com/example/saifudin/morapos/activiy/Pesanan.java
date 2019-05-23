package com.example.saifudin.morapos.activiy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saifudin.morapos.R;
import com.example.saifudin.morapos.adapter.PemesananAdapter;
import com.example.saifudin.morapos.db.RealmHelper;
import com.example.saifudin.morapos.db.model.Pemesanan;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Pesanan extends AppCompatActivity implements PemesananAdapter.PemesananAdapterListener{

    @BindView(R.id.ic_close)
    ImageView icClose;
    @BindView(R.id.btn_checkout)
    Button btnCheckout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Realm realm;
    RealmHelper realmHelper;
    List<Pemesanan> listPemesanan;
    PemesananAdapter pemesananAdapter;
    int quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cart_bottom_sheet);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // Set up
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);
        realmHelper = new RealmHelper(realm);

        listPemesanan = new ArrayList<>();
        listPemesanan = realmHelper.getAllMahasiswa();

        show();
    }

    @OnClick({R.id.ic_close, R.id.btn_checkout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_close:
                finish();
                break;
            case R.id.btn_checkout:
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        pemesananAdapter.notifyDataSetChanged();
        show();
    }

    public void show() {
        pemesananAdapter = new PemesananAdapter(listPemesanan, this, this);
        recyclerView.setAdapter(pemesananAdapter);
    }

    @Override
    public void onProductAddedCart(int index, Pemesanan product, TextView price, TextView total, TextView quantity) {

    }

    @Override
    public void onProductRemovedFromCart(int index, Pemesanan product, TextView price, TextView total, TextView quantity) {

    }

    @Override
    public void onCartItemRemoved(int index, Pemesanan product) {
        realmHelper.delete(product.getId());
        pemesananAdapter.notifyDataSetChanged();
    }
}