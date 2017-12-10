package com.example.acer.easywallet.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.easywallet.R;
import com.example.acer.easywallet.model.WalletItem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by acer on 12/10/2017.
 */

public class WalletListAdapter extends ArrayAdapter<WalletItem> {
    private Context mContext;
    private int mLayoutResId;
    private ArrayList<WalletItem> mWalletItemList;

    public WalletListAdapter(@NonNull Context context, int layoutResId, @NonNull ArrayList<WalletItem> walletItemList) {
        super(context, layoutResId, walletItemList);

        this.mContext = context;
        this.mLayoutResId = layoutResId;
        this.mWalletItemList = walletItemList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemLayout = inflater.inflate(mLayoutResId, null);

        WalletItem item = mWalletItemList.get(position);

        TextView walletTitleTextView = itemLayout.findViewById(R.id.wallet_title_text_view);
        TextView walletMoneyTextView = itemLayout.findViewById(R.id.wallet_money_text_view);

        walletTitleTextView.setText(item.detail);
        walletMoneyTextView.setText(item.money);

        return itemLayout;
    }
}
