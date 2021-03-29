package com.example.ungdungsolienlac;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editHoten,editSdt;
    Button btnXoaTrang,btnluuSQLite,btnTim;
    ListView listNote;
    public static final int MY_REQUEST_CODE = 100;
    private Contact  contact;
    private int index;
    private DBManager databast;
    private int i = 0;
    ArrayList<Contact> listContractSQLite;
    ArrayAdapter<Contact> adapterSQLite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        databast = new DBManager(this);
        listContractSQLite = databast.getAllContact();
        adapterSQLite = new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_1, listContractSQLite);
        listNote.setAdapter(adapterSQLite);
        adapterSQLite.notifyDataSetChanged();
        registerForContextMenu(listNote);
        listNote.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                contact = listContractSQLite.get(position);
                index = position;
                return false;
            }
        });
        btnluuSQLite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ht = editHoten.getText().toString();
                String sdt = editSdt.getText().toString();
                if (!ht.equals("") && !sdt.equals("")) {
                    Contact ct = new Contact(ht, sdt);
                    if (databast.checkContact(ct)) {
                        Toast.makeText(MainActivity.this, "Trùng dữ liệu",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        databast.themContact(ct);
                        adapterSQLite.add(ct);
                        listNote.setAdapter(adapterSQLite);
                        adapterSQLite.notifyDataSetChanged();
                    }
                } else
                    Toast.makeText(MainActivity.this, "Chưa nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }

        });
        btnTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ht = editHoten.getText().toString();
                if (ht.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập thông tin cần tìm",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Contact ct = databast.getContactByName(ht);
                    if (!ct.getHoTen().equals(""))
                        Toast.makeText(MainActivity.this,
                                ct.getHoTen() + "-" + ct.getSoDT(),
                                Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this,
                                "khong co du lieu can tim",
                                Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnXoaTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    editHoten.setText("");
                    editSdt.setText("");
                    editHoten.setFocusable(true);
            }
        });
    }
    public void anhxa(){
        editHoten = (EditText)findViewById(R.id.editHoten);
        editSdt = (EditText)findViewById(R.id.editSdt);
        btnXoaTrang = (Button) findViewById(R.id.btnXoaTrang);
        btnluuSQLite = (Button)findViewById(R.id.btnluuSQLite);
        btnTim = (Button)findViewById(R.id.btnTim);
        listNote = (ListView)findViewById(R.id.listNote);
    }
    public boolean oncreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.layout2,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void oncreateContextMenu(ContextMenu menu ,View v,ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu ,v,menuInfo);
        getMenuInflater().inflate(R.menu.layout2,menu);
    }
    //xử lý các mục chọn trên contextmenu
    public boolean onConTextItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        switch (id){
            case R.id.xoa:
                databast.deteleContact(contact);
                listContractSQLite.remove(index);
                adapterSQLite.notifyDataSetChanged();
                Toast.makeText(this,"xóa thành công",Toast.LENGTH_LONG).show();
                break;
            case R.id.xem:
                Intent intent = new Intent(this,Main2Activity.class);
                intent.putExtra("MyObject",contact);
                this.startActivityForResult(intent,MY_REQUEST_CODE);
                break;
            case R.id.goi:
                Uri uri=Uri.parse("tel:"+contact.getSoDT());
                Intent i = new Intent(Intent.ACTION_CALL,uri);
                startActivity(i);
                break;
            case R.id.guiSMS:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Activity.RESULT_OK && requestCode == MY_REQUEST_CODE){
            Contact ct = (Contact)data.getSerializableExtra("feedback");
            listContractSQLite.set(index,ct);
            adapterSQLite.notifyDataSetChanged();
            databast.Update(ct);
        } else
        {
            this.editHoten.setText("!?");
        }
    }
}