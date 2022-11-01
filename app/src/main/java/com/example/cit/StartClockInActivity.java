package com.example.cit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cit.Adapter.TaskRunningAdapter;
import com.example.cit.Controller.CompletedtaskActivity;
import com.example.cit.Database.SqlLiteHelper;
import com.example.cit.model.StartClockModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StartClockInActivity extends AppCompatActivity {

    private TextView currentDate;
    private ListView taskListview;
    private TextClock currentTime;
    private EditText projectName,taskName,estimateTime;
    private Button StartClock;
    private ArrayAdapter<StartClockModel> listAdapter;

    SqlLiteHelper sqlLiteHelper;
    String prjName ="";
    String tskName ="";
    String estTime ="";
    String crntTime ="";
    String crntDate ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_clock_in);


        sqlLiteHelper = new SqlLiteHelper(this);


        taskListview = findViewById(R.id.taskListView);
        projectName = findViewById(R.id.projectName);
        taskName = findViewById(R.id.taskName);
        estimateTime = findViewById(R.id.TimeEstimate);
        StartClock = findViewById(R.id.btn_startClock);
        currentDate = findViewById(R.id.currentDate);
        currentTime = (TextClock) findViewById(R.id.CurrentTime);


        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        //String CurrentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        currentDate.setText(date);
        // currentTime.setText(String.valueOf(CurrentTime));

        String Empid = getIntent().getStringExtra("empid");

        new updateRunningTask().execute();


        StartClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prjName = projectName.getText().toString();
                tskName = taskName.getText().toString();
                estTime = estimateTime.getText().toString();
                crntTime = currentTime.getText().toString();
                crntDate = currentDate.getText().toString();

                if (prjName.isEmpty() || tskName.isEmpty() || estTime.isEmpty() || crntTime.isEmpty()) {

                    Toast.makeText(StartClockInActivity.this, "Please fill task details", Toast.LENGTH_SHORT).show();

                } else {

                    StartClockModel startClockModel = new StartClockModel();

                    startClockModel.setEmpid(Empid);
                    startClockModel.setProjectName(prjName);
                    startClockModel.setTaskName(tskName);
                    startClockModel.setEstimateTime(estTime);
                    startClockModel.setStartTime(crntTime);
                    startClockModel.setCurrentDate(crntDate);


                   /* databseHelper.getWritableDatabase();
                    boolean result = databseHelper.updateTaskStartDetails(startClockModel);
                    databseHelper.closeDatabase();*/

                    boolean result = sqlLiteHelper.updateTaskStartDetails(startClockModel);


                    if (result) {

                        new updateRunningTask().execute();

                        Toast.makeText(StartClockInActivity.this, "Task Updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(StartClockInActivity.this, "Task Not Updated successfully", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.taskmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_history:
                Intent intent = new Intent(StartClockInActivity.this, CompletedtaskActivity.class);
                startActivity(intent);
                break;


        }

        return true;
    }

    private class updateRunningTask extends AsyncTask<Void,String,String>{

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(StartClockInActivity.this);
            dialog.setCancelable(false);
            dialog.setTitle("Loading...");
            dialog.show();

        }

        @Override
        protected String doInBackground(Void... voids) {
             String result = "";

            /*databseHelper.getReadableDatabase();
            List<StartClockModel> tasklist = databseHelper.getStartedTaskList();
            databseHelper.closeDatabase();*/

            List<StartClockModel> tasklist = sqlLiteHelper.getStartedTaskList();

            if(tasklist.size() > 0){
                listAdapter = new TaskRunningAdapter(StartClockInActivity.this,tasklist,listAdapter);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        taskListview.setAdapter(listAdapter);
                        projectName.setText("");
                        taskName.setText("");
                        estimateTime.setText("");
                    }
                });
                result = "success";

            }else{
                result = "failure";
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
           if(dialog != null){
               dialog.dismiss();
           }
           if(s.equals("success")){
               Toast.makeText(StartClockInActivity.this,"Task added Successfully",Toast.LENGTH_SHORT).show();
           }/*else{
               Toast.makeText(StartClockInActivity.this,"failed to add task",Toast.LENGTH_SHORT).show();
           }*/
        }
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(StartClockInActivity.this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle("confirmation?");
        alertDialog.setIcon(R.drawable.caution);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               startActivity( new Intent(StartClockInActivity.this,MainActivity.class));
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setMessage("Do you want to Logout?");
        alertDialog.show();
    }


    public void RefreshListView(Context context){
        listAdapter.notifyDataSetChanged();
    }
}