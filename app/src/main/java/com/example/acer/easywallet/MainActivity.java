package com.example.acer.easywallet;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.acer.easywallet.adapter.WalletListAdapter;
import com.example.acer.easywallet.db.DbHelper;
import com.example.acer.easywallet.model.WalletItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DbHelper mHelper;
    private SQLiteDatabase mDb;
    private Button button1, button2;
    private ArrayList<WalletItem> mWalletItemList = new ArrayList<>();
    private WalletListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        mHelper = new DbHelper(this);
        mDb = mHelper.getReadableDatabase();

        loadDataFromDb();

        mAdapter = new WalletListAdapter(
                this,
                R.layout.item,
                mWalletItemList
        );

        ListView lv = findViewById(R.id.list_view);
        lv.setAdapter(mAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                WalletItem item = mWalletItemList.get(position);
                int walletMoney = item.money;

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + walletMoney));
                startActivity(intent);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                String[] items = new String[]{"แก้ไขข้อมูล", "ลบข้อมูล"};

                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) { // แก้ไขข้อมูล
                            WalletItem item = mWalletItemList.get(position);
                            int walletId = item.id;

                            ContentValues cv = new ContentValues();
                            cv.put(DbHelper.COL_MONEY, "12345");

                            mDb.update(
                                    DbHelper.TABLE_NAME,
                                    cv,
                                    DbHelper.COL_ID + "=?",
                                    new String[]{String.valueOf(walletId)}
                            );
                            loadDataFromDb();
                            mAdapter.notifyDataSetChanged();

                        } else if (i == 1) { // ลบข้อมูล
                            WalletItem item = mWalletItemList.get(position);
                            int walletId = item.id;

                            mDb.delete(
                                    DbHelper.TABLE_NAME,
                                    DbHelper.COL_ID + "=?",
                                    new String[]{String.valueOf(walletId)}
                            );
                            loadDataFromDb();
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                    Intent a = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(a);

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent a1 = new Intent(MainActivity.this, Main3Activity.class);
                startActivity(a1);

            }
        });
    } // ปิดเมธอด onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123) {
            if (resultCode == RESULT_OK) {
                loadDataFromDb();
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    private void loadDataFromDb() {
        Cursor cursor = mDb.query(
                DbHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        mWalletItemList.clear();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DbHelper.COL_ID));
            String detail = cursor.getString(cursor.getColumnIndex(DbHelper.COL_TITLE));
            int money = cursor.getInt(cursor.getColumnIndex(DbHelper.COL_MONEY));

            WalletItem item = new WalletItem(id, detail, money);
            mWalletItemList.add(item);
        }
    }
}
