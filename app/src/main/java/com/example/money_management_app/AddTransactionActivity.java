package com.example.money_management_app;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.money_management_app.database.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Date;

public class AddTransactionActivity extends AppCompatActivity {

    EditText edtFriend, edtItem, edtAmount, edtDate;
    RadioGroup typeGroup;
    Button btnSave;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        setTitle("Add Transaction");

        db = new DBHelper(this);

        edtFriend = findViewById(R.id.edtFriend);
        edtItem = findViewById(R.id.edtItem);
        edtAmount = findViewById(R.id.edtAmount);
        edtDate = findViewById(R.id.edtDate);
        typeGroup = findViewById(R.id.typeGroup);
        btnSave = findViewById(R.id.btnSave);

        // ✅ Auto set today's date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = sdf.format(new Date());
        edtDate.setText(currentDate);

        // ✅ Allow editing via DatePicker
        edtDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddTransactionActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String selectedDate = selectedYear + "-" +
                                String.format("%02d", selectedMonth + 1) + "-" +
                                String.format("%02d", selectedDay);
                        edtDate.setText(selectedDate);
                    },
                    year, month, day
            );

            datePickerDialog.show();
        });

        btnSave.setOnClickListener(v -> {
            try {
                String friend = edtFriend.getText().toString().trim();
                String item = edtItem.getText().toString().trim();
                String amtStr = edtAmount.getText().toString().trim();
                String date = edtDate.getText().toString().trim();
                int selected = typeGroup.getCheckedRadioButtonId();

                if (friend.isEmpty() || item.isEmpty() || amtStr.isEmpty() || date.isEmpty() || selected == -1) {
                    Toast.makeText(this, "⚠ Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                double amount;
                try {
                    amount = Double.parseDouble(amtStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "⚠ Invalid amount", Toast.LENGTH_SHORT).show();
                    return;
                }

                String type = (selected == R.id.radioIncome) ? "+" : "-";

                // 🔥 UPDATED METHOD CALL (with date)
                boolean ok = db.insertTransaction(friend, item, amount, type, date);

                if (ok) {
                    Toast.makeText(this, "✅ Transaction Added", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "❌ Failed to Add Transaction", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}