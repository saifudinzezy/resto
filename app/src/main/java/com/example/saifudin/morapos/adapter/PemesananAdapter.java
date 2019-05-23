package com.example.saifudin.morapos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.saifudin.morapos.R;
import com.example.saifudin.morapos.db.model.Pemesanan;
import com.example.saifudin.morapos.model.ProdukItem;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.saifudin.morapos.helper.Constan.BASE_URL_IMAGE;

public class PemesananAdapter extends RecyclerView.Adapter<PemesananAdapter.ViewHolder> {
    private List<Pemesanan> list;
    private Context context;
    private PemesananAdapterListener listener;

    public PemesananAdapter() {
    }

    public PemesananAdapter(List<Pemesanan> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public PemesananAdapter(List<Pemesanan> list, Context context, PemesananAdapterListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    public List<Pemesanan> getList() {
        return list;
    }

    public void setList(List<Pemesanan> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        double harga = Double.parseDouble(getList().get(position).getProdukItem().getPrice());
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        holder.price.setText(kursIndonesia.format(harga) + " x " + getList().get(position).getQuantity());
        harga *= getList().get(position).getQuantity();
        holder.totHrg.setText(kursIndonesia.format(harga));
        holder.name.setText("" + getList().get(position).getProdukItem().getName());
        holder.productCount.setText("" + getList().get(position).getQuantity());
        Glide.with(context)
                .load(BASE_URL_IMAGE + list.get(position).getProdukItem().getImage())
                .into(holder.thumbnail);

        holder.icAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onProductAddedCart(position, getList().get(position), holder.price, holder.totHrg, holder.productCount);
            }
        });

        holder.icRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onProductRemovedFromCart(position, getList().get(position), holder.price, holder.totHrg, holder.productCount);
            }
        });

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCartItemRemoved(position, getList().get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return getList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thumbnail)
        ImageView thumbnail;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.ic_remove)
        ImageView icRemove;
        @BindView(R.id.product_count)
        TextView productCount;
        @BindView(R.id.ic_add)
        ImageView icAdd;
        @BindView(R.id.tot_hrg)
        TextView totHrg;
        @BindView(R.id.btn_remove)
        Button btnRemove;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface PemesananAdapterListener {
        void onProductAddedCart(int index, Pemesanan product, TextView price, TextView total, TextView quantity);
        void onProductRemovedFromCart(int index, Pemesanan product, TextView price, TextView total, TextView quantity);
        void onCartItemRemoved(int index, Pemesanan product);
    }
}