package com.example.csgt;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class LichSuActivity extends AppCompatActivity {

    DataContext db;
    ArrayList<ThongTinViPham> lst;
    ListView listView;
    Button btnQuit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su);
        db=new DataContext(getApplicationContext());
        listView=findViewById(R.id.listviewHis);
        lst=db.GetAll();
        AdapterCustom adp=new AdapterCustom(getApplicationContext(), R.layout.layout_listitem, lst);
        listView.setAdapter(adp);
        btnQuit=findViewById(R.id.btnQuit);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(it);
            }
        });
    }
}
