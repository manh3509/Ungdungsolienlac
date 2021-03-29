package com.example.ungdungsolienlac;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {
    Button btnCapNhat ,btnKetThuc;
    EditText editHoTen, editSdt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        anhxa();
        Intent intent = getIntent();
        Contact data =(Contact) intent.getSerializableExtra("MyObject");
        editSdt.setText(data.getSoDT());
        editHoTen.setText(data.getHoTen());
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ht =editHoTen.getText().toString();
                 String dt = editSdt.getText().toString();
                Contact ct = new Contact(ht,dt);
                Intent data = new Intent();
                data.putExtra("feedback" ,ct);
                    //trả kết quả đã thay đổi về cho MainActivity
                setResult(Activity.RESULT_OK,data);
                finish();
            }
        });
        btnKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void anhxa(){
        editHoTen = (EditText) findViewById(R.id.editHoten);
        editSdt = (EditText) findViewById(R.id.editSdt);
        btnCapNhat = (Button) findViewById(R.id.btnCapNhat);
        btnKetThuc = (Button) findViewById(R.id.btnKetThuc);
    }
}
