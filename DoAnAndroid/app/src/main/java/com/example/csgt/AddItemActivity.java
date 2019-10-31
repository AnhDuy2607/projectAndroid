package com.example.csgt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class AddItemActivity extends AppCompatActivity {

    Button btnSave,btnQuitAddToMain;
    Pattern cmnd=Pattern.compile("^[0-9]{5}$");
    Pattern gplx=Pattern.compile("^[0-9]{5}$");
    Pattern isNum=Pattern.compile("^[0-9]+$");
    EditText txtCMND,txtGPLX,txtSoLan,txtNoiDung;
    DataContext db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        db=new DataContext(getApplicationContext());
        btnSave=findViewById(R.id.save);
        btnQuitAddToMain=findViewById(R.id.btnQuitAddToMain);
        txtCMND=findViewById(R.id.cmnd);
        txtGPLX=findViewById(R.id.gplx);
        txtSoLan=findViewById(R.id.number_Of_Mistake);
        txtNoiDung=findViewById(R.id.mistake);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtCMND.getText().length()==0 ||txtGPLX.getText().length()==0 || txtNoiDung.getText().length()==0 ||txtSoLan.getText().length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Bạn chưa nhập thông tin đầy đủ",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!cmnd.matcher(txtCMND.getText()).matches()
                        && gplx.matcher(txtGPLX.getText()).matches()
                        && txtNoiDung.getText().length() > 0
                        && isNum.matcher(txtSoLan.getText()).matches())
                {
                    Toast.makeText(getApplicationContext(),"Số CMND không hợp lệ hoặc chưa nhập",Toast.LENGTH_LONG).show();
                    return;
                }
                if(txtGPLX.getText().length() > 5 || txtCMND.getText().length() >5)
                {
                    Toast.makeText(getApplicationContext(),"Số CMND hoặc GPLX ko vượt quá 5 kí tự",Toast.LENGTH_LONG).show();
                    return;
                }
                if(cmnd.matcher(txtCMND.getText()).matches()
                        && !gplx.matcher(txtGPLX.getText()).matches()
                        && txtNoiDung.getText().length() > 0
                        && isNum.matcher(txtSoLan.getText()).matches())
                {
                    Toast.makeText(getApplicationContext(),"Số GPLX không hợp lệ hoặc chưa nhập",Toast.LENGTH_LONG).show();
                    return;
                }
                if(cmnd.matcher(txtCMND.getText()).matches()
                        && gplx.matcher(txtGPLX.getText()).matches()
                        && txtNoiDung.getText().length() > 0
                        && !isNum.matcher(txtSoLan.getText()).matches())
                {
                    Toast.makeText(getApplicationContext(),"Số lần vi phạm không hợp lệ hoặc chưa nhập",Toast.LENGTH_LONG).show();
                    return;
                }
                if(isNum.matcher(txtNoiDung.getText()).matches())
                {
                    Toast.makeText(getApplicationContext(),"Nội Dung Không Hợp Lệ",Toast.LENGTH_LONG).show();
                    return;
                }
                try
                {
                     int result= db.AddViPham(new ThongTinViPham(0,txtGPLX.getText().toString(),
                            txtCMND.getText().toString(),
                            Integer.parseInt(txtSoLan.getText().toString()),
                            txtNoiDung.getText().toString()));
                     if(result==-1)
                     {
                         Toast.makeText(getApplicationContext(),"Thêm Thành Công",Toast.LENGTH_LONG).show();
                         txtCMND.setText("");
                         txtNoiDung.setText("");
                         txtSoLan.setText("");
                         txtGPLX.setText("");
                         return;
                     }
                     if(result!=-1 && result!=0 && result!=-2 && result!=-3)
                     {
                         Toast.makeText(getApplicationContext(),"Lần trước đã vi phạm: "+result+". Lần này phải là: "+(result+1),Toast.LENGTH_LONG).show();
                        return;
                     }
                     if(result==0)
                     {
                         Toast.makeText(getApplicationContext(),"Thêm Không Thành Công",Toast.LENGTH_LONG).show();
                         return;
                     }
                     if(result==-2)
                     {
                         Toast.makeText(getApplicationContext(),"Người Vi Phạm Mới, Số lần phải bằng 1",Toast.LENGTH_LONG).show();
                         return;
                     }
                    if(result==-3)
                    {
                        Toast.makeText(getApplicationContext(),"GPLX đã có trong dữ liệu, nhưng số CMND không trùng. Hoặc Ngược Lại\r\nKiểm Tra Lại Thông Tin ",Toast.LENGTH_LONG).show();
                        return;
                    }
                    return;
                }
                catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
        btnQuitAddToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(it);
            }
        });
    }
}
