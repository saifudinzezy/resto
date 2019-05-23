package com.example.saifudin.morapos.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.saifudin.morapos.R;
import com.example.saifudin.morapos.db.model.Pemesanan;
import com.example.saifudin.morapos.model.ProdukItem;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

import static com.example.saifudin.morapos.helper.Constan.BASE_URL_IMAGE;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ViewHolder> {
    private Context context;
    private List<ProdukItem> list;
    private ProductsAdapterListener listener;

    public ProdukAdapter(Context context, List<ProdukItem> list, ProductsAdapterListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public List<ProdukItem> getList() {
        return list;
    }

    public void setList(List<ProdukItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.produk_list, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        double price = Double.parseDouble((list.get(position).getPrice()));
        int x = (int) price;
        holder.price.setText(formatRupiah.format((Double.valueOf("" + x))));

        holder.name.setText(list.get(position).getName());
        holder.desc.setText(list.get(position).getDetails());
        Glide.with(context)
                .load(BASE_URL_IMAGE + list.get(position).getImage())
                .into(holder.thumbnail);

        holder.icAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onProductAddedCart(position, getList().get(position));
            }
        });

        holder.icRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onProductRemovedFromCart(position, getList().get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
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
        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.desc)
        TextView desc;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ProductsAdapterListener {
        void onProductAddedCart(int index, ProdukItem product);

        void onProductRemovedFromCart(int index, ProdukItem product);
    }
}