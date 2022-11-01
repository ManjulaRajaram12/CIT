package com.example.cit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cit.Database.SqlLiteHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RegisterActivity extends AppCompatActivity {

    private EditText etname,etemailid,etpass,etphoneno,etempid;
    private TextView btnRegister;
    SqlLiteHelper sqlLiteHelper = null;
    private FloatingActionButton backbtn_float;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        sqlLiteHelper =new SqlLiteHelper(this);

        etname = findViewById(R.id.edt_name);
        etempid = findViewById(R.id.edt_EmpId);
        etemailid = findViewById(R.id.edt_emailId);
        etphoneno = findViewById(R.id.edt_phoneno);
        etpass = findViewById(R.id.edt_Password);
        btnRegister = findViewById(R.id.btn_Register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result = RegisterUser();
                if(result.equals("success")){
                    Toast.makeText(RegisterActivity.this,"Register Success",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                }else if(result.equals("failed")) {
                    Toast.makeText(RegisterActivity.this,"Failed to Register",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegisterActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }

            }
        });
      //  backbtn_float = findViewById(R.id.backreg);
       /* backbtn_float.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });*/





    }


    public String RegisterUser(){
        String userRegistered= "";
        String name = etname.getText().toString();
        String empid = etempid.getText().toString();
        String emailid = etemailid.getText().toString();
        String phoneno = etphoneno.getText().toString();
        String pass = etpass.getText().toString();

        if(name.isEmpty() || empid.isEmpty() || emailid.isEmpty() || phoneno.isEmpty() || pass.isEmpty()){
             userRegistered = "failed";
        }else {
            /*databseHelper.getWritableDatabase();
            userRegistered = databseHelper.RegisterUserData(name, empid, emailid, phoneno, pass);
            databseHelper.closeDatabase();*/

            userRegistered = sqlLiteHelper.RegisterUserData(name, empid, emailid, phoneno, pass);

        }

        return userRegistered;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity( new Intent(RegisterActivity.this,MainActivity.class));
    }
}