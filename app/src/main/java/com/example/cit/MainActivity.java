package com.example.cit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cit.Database.SqlLiteHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView registerLink;
    private EditText empid,pass;
    private TextView btnLogin;

    SqlLiteHelper sqlLiteHelper;
    SQLiteDatabase db;
    ImageView web,twt,link;
    Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sqlLiteHelper =new SqlLiteHelper(this);

        registerLink = findViewById(R.id.txt_regLink);
        empid =findViewById(R.id.edt_EmpId);
        pass =findViewById(R.id.edt_Password);
        btnLogin =findViewById(R.id.btn_Login);
        web =findViewById(R.id.web);
        twt =findViewById(R.id.twter);
        link =findViewById(R.id.link);
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www.ciglobaltechnologies.com/";
                Uri uriUrl = Uri.parse(url);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.linkedin.com/company/ci-global-technologies";
                Uri uriUrl = Uri.parse(url);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });
        twt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://twitter.com/ciglobal";
                Uri uriUrl = Uri.parse(url);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });



        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Login(empid.getText().toString(),pass.getText().toString());
            }
        });

    }

    private void Login(String empid,String pass) {

        if(empid.isEmpty() || pass.isEmpty()){
            Toast.makeText(MainActivity.this,"Enter login details",Toast.LENGTH_SHORT).show();
        }else{
            /*databseHelper.getReadableDatabase();
            List<String> result = databseHelper.CheckValidUser(empid,pass);
            databseHelper.closeDatabase();*/

            List<String> result = sqlLiteHelper.CheckValidUser(empid,pass);

            if(result.size() > 0 && result.get(4).contains(pass)){
                Intent i = new Intent(MainActivity.this,StartClockInActivity.class);
                i.putExtra("name",result.get(0));
                i.putExtra("empid",result.get(1));
                i.putExtra("emailid",result.get(2));
                i.putExtra("phoneno",result.get(3));
                startActivity(i);
            }else{
                Toast.makeText(MainActivity.this,"EmpID or Password invalid",Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onBackPressed() {
        /*  super.onBackPressed();*/
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle("confirmation?");
        alertDialog.setIcon(R.drawable.caution);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setMessage("Do you want to exit?");
        alertDialog.show();
    }

}