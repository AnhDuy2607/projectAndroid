package com.example.csgt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class SearchActivity extends AppCompatActivity {

    Button btnSearch,btnThoat;
    DataContext db;
    ArrayList<ThongTinViPham> lst;
    EditText txtSearch;
    ListView lv;
    AdapterCustom adp;
    Pattern r1=Pattern.compile("^[0-9]{5}$");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        db=new DataContext(getApplicationContext());
        lst=new ArrayList<>();
        txtSearch=findViewById(R.id.seach_gplx);
        lv=findViewById(R.id.lvSearch);
        btnSearch=findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtSearch.getText().length()==0)
                {
                    Toast.makeText(getApplicationContext(),"chưa nhập giá trị tìm kiếm",Toast.LENGTH_LONG).show();
                    return;
                }
                if(txtSearch.getText().length()>5)
                {
                    Toast.makeText(getApplicationContext(),"Số GPLX phải nhỏ hơn, bằng 5 kí tự",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!r1.matcher(txtSearch.getText()).matches())
                {
                    Toast.makeText(getApplicationContext(),"giá trị tìm kiếm ko hợp lệ",Toast.LENGTH_LONG).show();
                    return;
                }
                lst=db.TimKiemThongTin(txtSearch.getText().toString());
                if(lst.size()>0)
                {
                    Toast.makeText(getApplicationContext(),"Tìm Thấy "+lst.size()+" Thông Tin",Toast.LENGTH_LONG).show();
                    adp=new AdapterCustom(getApplicationContext(),R.layout.layout_listitem,lst);
                    lv.setAdapter(adp);
                    adp.notifyDataSetChanged();
                    return;
                }
                adp=new AdapterCustom(getApplicationContext(),R.layout.layout_listitem,new ArrayList<ThongTinViPham>());
                lv.setAdapter(adp);
                Toast.makeText(getApplicationContext(),"Không Tìm Thấy",Toast.LENGTH_LONG).show();
            }
        });
        btnThoat=findViewById(R.id.btnQuitSearchToMain);
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(it);
            }
        });
    }
}
