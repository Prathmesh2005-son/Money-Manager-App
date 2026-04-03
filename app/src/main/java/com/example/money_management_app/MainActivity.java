package com.example.money_management_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.example.money_management_app.adapters.FriendAdapter;
import com.example.money_management_app.database.DBHelper;
import com.example.money_management_app.models.Friend;
import com.example.money_management_app.models.Transaction;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FriendAdapter adapter;
    private final ArrayList<Friend> friendList = new ArrayList<>();
    private DBHelper db;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setTitle("💰 Money Manager");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new DBHelper(this);
        adapter = new FriendAdapter(friendList);
        recyclerView.setAdapter(adapter);

        fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddTransactionActivity.class))
        );
    }

    private void loadData() {

        friendList.clear();
        HashMap<String, Friend> map = new HashMap<>();

        Cursor c = db.getAllTransactions();

        if (c != null && c.moveToFirst()) {

            int colFriend = c.getColumnIndexOrThrow(DBHelper.COL_FRIEND);
            int colItem = c.getColumnIndexOrThrow(DBHelper.COL_ITEM);
            int colAmount = c.getColumnIndexOrThrow(DBHelper.COL_AMOUNT);
            int colType = c.getColumnIndexOrThrow(DBHelper.COL_TYPE);
            int colDate = c.getColumnIndexOrThrow(DBHelper.COL_DATE); // 🔥 NEW

            do {
                String fname = c.getString(colFriend);
                String item = c.getString(colItem);
                double amt = c.getDouble(colAmount);
                String type = c.getString(colType);
                String date = c.getString(colDate); // 🔥 GET DATE

                if (!map.containsKey(fname)) {
                    map.put(fname, new Friend(fname));
                }

                // 🔥 UPDATED CONSTRUCTOR
                map.get(fname).addTransaction(
                        new Transaction(item, amt, type, date)
                );

            } while (c.moveToNext());

            c.close();
        }

        friendList.addAll(map.values());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        adapter.notifyDataSetChanged();
    }
}