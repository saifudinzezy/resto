package com.example.saifudin.morapos.activiy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.saifudin.morapos.R;
import com.example.saifudin.morapos.adapter.ProdukAdapter;
import com.example.saifudin.morapos.adapter.spiner.CustomAdapter;
import com.example.saifudin.morapos.db.RealmHelper;
import com.example.saifudin.morapos.db.model.Pemesanan;
import com.example.saifudin.morapos.model.KategoriItem;
import com.example.saifudin.morapos.model.ProdukItem;
import com.example.saifudin.morapos.model.ResponseKategori;
import com.example.saifudin.morapos.model.ResponseProduk;
import com.example.saifudin.morapos.netwrork.ApiService;
import com.example.saifudin.morapos.netwrork.RetroClient;
import com.google.gson.Gson;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.saifudin.morapos.db.RealmHelper.getCartPrice;
import static com.example.saifudin.morapos.helper.Constan.BASE_URL_IMAGE;
import static com.example.saifudin.morapos.helper.FormatRp.formatRp2;

public class MainActivity extends AppCompatActivity implements ProdukAdapter.ProductsAdapterListener {

    @BindView(R.id.spiner)
    Spinner spiner;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.loader)
    AVLoadingIndicatorView loader;
    public ProdukAdapter adapterProduk;
    List<ProdukItem> hasilPesan;
    List<KategoriItem> hasilPesanSpiner;
    @BindView(R.id.edt_cari)
    EditText edtCari;
    CustomAdapter adapterSpiner;
    @BindView(R.id.cv)
    CardView cv;
    @BindView(R.id.txt_count)
    TextView txtCount;
    @BindView(R.id.txt_total)
    TextView txtTotal;
    Realm realm;
    RealmHelper realmHelper;
    //var item_order
    TextView txtName, txtQuantity, txtDesc, txtPrice;
    ImageView imgPlus, imgMins, imgThubnail, imgClose;
    Button btnCheckout;
    int quantity;
    int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        rv.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        getProduk();
        getKategori();
        //adding a TextChangedListener
        //to call a method whenever there is some change on the EditText
        edtCari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                try {
                    if (editable.toString() == null || editable.toString().trim().isEmpty()) {
                        adapterProduk.setList(hasilPesan);
                        return;
                    } else {
                        filter(editable.toString());
                    }
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
            }
        });

        //spinner
        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), hasilPesan.get(position).getJudul(), Toast.LENGTH_SHORT).show();
                try {
//                    Toast.makeText(MainActivity.this, ""+hasilPesanSpiner.get(position).getId(), Toast.LENGTH_SHORT).show();
                    if (hasilPesanSpiner.get(position).getId().equalsIgnoreCase("0")) {
                        getProduk();
                    } else {
                        getProduk(hasilPesanSpiner.get(position).getId());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // hide fab
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && cv.getVisibility() == View.VISIBLE) {
                    cv.setVisibility(View.GONE);
                } else if (dy < 0 && cv.getVisibility() != View.VISIBLE) {
                    cv.setVisibility(View.VISIBLE);
                }
            }
        });

        //Set up Realm
        Realm.init(MainActivity.this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);

        //buat model real
        realmHelper = new RealmHelper(realm);
        pesanan();
    }

    //
    private void filter(String text) {
        ArrayList<ProdukItem> filteredValues = new ArrayList<>(hasilPesan);
        for (ProdukItem value : hasilPesan) {
            if (!value.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredValues.remove(value);
            }
        }
        adapterProduk.setList(filteredValues);
    }

    private void getProduk() {
        loader.setVisibility(View.GONE);
        ApiService apiService = RetroClient.getApiService();
        Call<ResponseProduk> call = apiService.getProduk();
        call.enqueue(new Callback<ResponseProduk>() {
            @Override
            public void onResponse(Call<ResponseProduk> call, Response<ResponseProduk> response) {
                hasilPesan = response.body().getProduk();
                Log.e("Tag", "Hasil List :" + new Gson().toJson(hasilPesan));
                if (response.body().getCode() == 200) {
                    try {
                        //
                        ArrayList<ProdukItem> list = new ArrayList<>();
                        list.addAll(hasilPesan);
                        adapterProduk = new ProdukAdapter(MainActivity.this, list, MainActivity.this);
                        //  swipeMain.setRefreshing(false);
                        rv.setAdapter(adapterProduk);
                        loader.setVisibility(View.GONE);
                    } catch (Exception e) {

                    }
                } else {
                    Log.e("Tag", "Gagal req data ");
                    loader.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseProduk> call, Throwable t) {
                loader.setVisibility(View.GONE);
            }
        });
    }

    private void getProduk(String id) {
        loader.setVisibility(View.GONE);
        ApiService apiService = RetroClient.getApiService();
        Call<ResponseProduk> call = apiService.getProduk(id);
        call.enqueue(new Callback<ResponseProduk>() {
            @Override
            public void onResponse(Call<ResponseProduk> call, Response<ResponseProduk> response) {
                Log.e("Tag", "Hasil List :" + new Gson().toJson(hasilPesan));
                try {
                    if (response.body().getCode() == 200) {
                        try {
                            hasilPesan = response.body().getProduk();
                            ArrayList<ProdukItem> list = new ArrayList<>();
                            list.addAll(hasilPesan);
                            adapterProduk = new ProdukAdapter(MainActivity.this, list, MainActivity.this);
                            //  swipeMain.setRefreshing(false);
                            rv.setAdapter(adapterProduk);
                            loader.setVisibility(View.GONE);
                        } catch (Exception e) {

                        }
                    } else {
                        Log.e("Tag", "Gagal req data ");
                        loader.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Kategori kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseProduk> call, Throwable t) {
                loader.setVisibility(View.GONE);
            }
        });
    }

    private void getKategori() {
        loader.setVisibility(View.GONE);
        ApiService apiService = RetroClient.getApiService();
        Call<ResponseKategori> call = apiService.getKategori();
        call.enqueue(new Callback<ResponseKategori>() {
            @Override
            public void onResponse(Call<ResponseKategori> call, Response<ResponseKategori> response) {
                hasilPesanSpiner = new ArrayList<>();
                hasilPesanSpiner.add(new KategoriItem("Semua", "0", "Semua", "0"));
                hasilPesanSpiner.addAll(response.body().getKategori());

                Log.e("Tag", "Hasil List :" + new Gson().toJson(hasilPesan));
                if (response.body().getCode() == 200) {
                    try {
                        //load jika ada data baru
                        adapterSpiner = new CustomAdapter(MainActivity.this, hasilPesanSpiner);
                        spiner.setAdapter(adapterSpiner);
                        adapterSpiner.notifyDataSetChanged();
                    } catch (Exception e) {

                    }
                } else {
                    Log.e("Tag", "Gagal req data ");
                    loader.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseKategori> call, Throwable t) {
                loader.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onProductAddedCart(int index, final ProdukItem product) {
        quantity = 1;
        View view = getLayoutInflater().inflate(R.layout.item_order, null);

        final BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog d = (BottomSheetDialog) dialog;

                FrameLayout bottomSheet = d.findViewById(android.support.design.R.id.design_bottom_sheet);
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        btnCheckout = view.findViewById(R.id.btn_checkout);
        imgClose = view.findViewById(R.id.ic_close);
        imgThubnail = view.findViewById(R.id.thumbnail);
        txtDesc = view.findViewById(R.id.desc);
        txtPrice = view.findViewById(R.id.price);
        imgMins = view.findViewById(R.id.ic_remove);
        imgPlus = view.findViewById(R.id.ic_add);
        txtName = view.findViewById(R.id.name);
        txtQuantity = view.findViewById(R.id.product_count);

        dialog.setContentView(view);
        dialog.show();

        Locale localeID = new Locale("in", "ID");
        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        final double price = Double.parseDouble((product.getPrice()));
        final int x1 = (int) price;
        x = (int) price;
        txtPrice.setText(formatRupiah.format((Double.valueOf("" + x))));
        txtName.setText(product.getName());
        txtDesc.setText(product.getDetails());
        Glide.with(MainActivity.this)
                .load(BASE_URL_IMAGE + product.getImage())
                .into(imgThubnail);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity += 1;
                x += x1;
                txtQuantity.setText("" + quantity);
                txtPrice.setText(formatRupiah.format((Double.valueOf("" + x))));
            }
        });

        imgMins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity == 1) {
                    Toast.makeText(MainActivity.this, "Pesanan minimal satu", Toast.LENGTH_SHORT).show();
                    return;
                }
                quantity -= 1;
                x -= x1;
                txtQuantity.setText("" + quantity);
                txtPrice.setText(formatRupiah.format((Double.valueOf("" + x))));

            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realmHelper.initNewCart(product, quantity);
                realmHelper.setListener(new RealmHelper.RealmHelperListener() {
                    @Override
                    public void reset() {
                        pesanan();
                    }
                });
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onProductRemovedFromCart(int index, ProdukItem product) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.history:

                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.cv)
    public void onViewClicked() {
        startActivity(new Intent(MainActivity.this, Pesanan.class));
    }

    private void setCartInfoBar(List<Pemesanan> cartItems) {
        int itemCount = 0;
        for (Pemesanan cartItem : cartItems) {
            itemCount += cartItem.getQuantity();
            Log.e("notif", "" + cartItem.getQuantity());
        }

        txtCount.setText("" + itemCount);
        formatRp2(getCartPrice(cartItems), txtTotal);
    }

    public void pesanan() {
        List<Pemesanan> cartItems = realmHelper.getAllMahasiswa();
        if (cartItems != null && cartItems.size() > 0) {
            setCartInfoBar(cartItems);
        } else {
            //Toast.makeText(MainActivity.this, "kosong", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        pesanan();
    }
}