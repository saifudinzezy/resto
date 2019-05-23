package com.example.saifudin.morapos.helper;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.saifudin.morapos.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartInfoBar extends RelativeLayout {
    @BindView(R.id.txt_total)
    TextView txtTotal;
    @BindView(R.id.txt_count)
    TextView count;
    private CartInfoBarListener listener;

    public CartInfoBar(Context context) {
        super(context);
        init(context, null);
    }

    public CartInfoBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void init(Context context, @Nullable AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_cart_info_bar, null);
        ButterKnife.bind(this, view);
        addView(view);
    }

    public void setListener(CartInfoBarListener listener) {
        this.listener = listener;
    }

    @OnClick(R.id.container)
    void onContainerClick() {
        if (listener != null)
            listener.onClick();
    }

    public void setData(int itemCount, String price) {
        count.setText(""+itemCount);
//        txtTotal.setText(""+itemCount);
    }

    public interface CartInfoBarListener {
        void onClick();
    }
}
