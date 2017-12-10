package com.example.acer.easywallet;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.easywallet.db.DbHelper;
import com.example.acer.easywallet.model.WalletItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 12/10/2017.
 */

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {

    private EditText mWalletTitleEditText, mWalletMoneyEditText;
    private Button mSaveButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet1);

        mWalletTitleEditText = findViewById(R.id.wallet_title_edit_text);
        mWalletMoneyEditText = findViewById(R.id.wallet_money_edit_text);
        mSaveButton = findViewById(R.id.save_button);

        mSaveButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        saveDataToDb();
        setResult(RESULT_OK);
        finish();
    }

    private void saveDataToDb() {
        String walletTitle = mWalletTitleEditText.getText().toString();
        String walletMoney = mWalletMoneyEditText.getText().toString();

        ContentValues cv = new ContentValues();
        cv.put(DbHelper.COL_TITLE, walletTitle);
        cv.put(DbHelper.COL_MONEY, walletMoney);

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = db.insert(DbHelper.TABLE_NAME, null, cv);
        if (result == -1) {
            //
        }
    }


}
