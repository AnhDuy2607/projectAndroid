package com.example.csgt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class loginActivity extends AppCompatActivity {

    private DataContext db;
    Button btnLogin;
    EditText txtUsername,txtPassword;
    Pattern regexUser=Pattern.compile("^[0-9]{5}$");
    Pattern regexPass=Pattern.compile("^\\w{5}$");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        db=new DataContext(getApplicationContext());
        btnLogin=findViewById(R.id.login);

        // Chĩ đc chạy lần đầu tiên, và khóa lại sau khi chạy
        db.TaoAcc(new Account("12345","1234a"));

        txtUsername=findViewById(R.id.username);
        txtPassword=findViewById(R.id.password);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    if(txtUsername.getText().length()==0 && txtPassword.getText().length()==0)
                    {
                        Toast.makeText(getApplicationContext(),"Hãy Nhập thông tin",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(txtUsername.getText().length()==0 && txtPassword.getText().length()>0)
                    {
                        Toast.makeText(getApplicationContext(),"Hãy Nhập password",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(txtUsername.getText().length()>0 && txtPassword.getText().length()==0)
                    {
                        Toast.makeText(getApplicationContext(),"Hãy Nhập username",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (!regexUser.matcher(txtUsername.getText()).matches() && !regexPass.matcher(txtPassword.getText()).matches())
                    {
                        Toast.makeText(getApplicationContext(),"Username và password không hợp lệ",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(!regexUser.matcher(txtUsername.getText()).matches() && regexPass.matcher(txtPassword.getText()).matches())
                    {
                        Toast.makeText(getApplicationContext(),"Username không hợp lệ. Ví Dụ: 12345",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(!regexPass.matcher(txtPassword.getText()).matches() && regexUser.matcher(txtUsername.getText()).matches())
                    {
                        Toast.makeText(getApplicationContext(),"Password không hợp lệ. Ví dụ: 1234a",Toast.LENGTH_LONG).show();
                        return;
                    }
                    int kq= db.CheckLogin(new Account(txtUsername.getText().toString(),txtPassword.getText().toString()));
                    if(kq>0)
                    {
                        Intent it=new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(it);
                    }
                    else
                    {

                        Toast.makeText(getApplicationContext(),"Đăng Nhập Không Thành Công",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
