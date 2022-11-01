package com.example.cit.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cit.Adapter.CompletetTaskAdapter;
import com.example.cit.BuildConfig;
import com.example.cit.Database.SqlLiteHelper;
import com.example.cit.R;
import com.example.cit.model.StartClockModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CompletedtaskActivity extends AppCompatActivity {

    private ListView comptasklist;
    private EditText search;
    private ArrayAdapter<StartClockModel> arrayAdapter;
    private AlertDialog.Builder alertdialog;
    private AlertDialog dialog;
    SqlLiteHelper sqlLiteHelper;
    private TextView projDesc,taskDesc,timeTaken,endTime;
    private Button delTask,print;
    List<StartClockModel> list;
    private  Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completedtask);


        sqlLiteHelper = new SqlLiteHelper(this);

        comptasklist = findViewById(R.id.CompTaskList);
        search = findViewById(R.id.searchHistory);

        list = sqlLiteHelper.getCompletedTasks();

        arrayAdapter = new CompletetTaskAdapter(list,CompletedtaskActivity.this);
        comptasklist.setAdapter(arrayAdapter);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrayAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        comptasklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                StartClockModel startClockModel = new StartClockModel();
                startClockModel = (StartClockModel) comptasklist
                        .getItemAtPosition(position);

                /*startClockModel.getProjectName();
                startClockModel.getTaskName();
                startClockModel.getTtlTimeTaken();
                startClockModel.getStartTime();
                startClockModel.getEndTime();
                startClockModel.getEstimateTime();*/


               alertdialog = new AlertDialog.Builder(CompletedtaskActivity.this);
               final View popup = getLayoutInflater().inflate(R.layout.pop_up,null);

                projDesc = popup.findViewById(R.id.projDesc);
                taskDesc = popup.findViewById(R.id.taskDesc);
                timeTaken = popup.findViewById(R.id.timeTaken);
                endTime = popup.findViewById(R.id.endTime);
                delTask = popup.findViewById(R.id.deleteTask);
                print = popup.findViewById(R.id.Print);

                projDesc.setText(startClockModel.getProjectName());
                taskDesc.setText( startClockModel.getTaskName());
                timeTaken.setText(startClockModel.getTtlTimeTaken());
                endTime.setText(startClockModel.getEndTime());

                String prjname = startClockModel.getProjectName();
                String taskname = startClockModel.getTaskName();
                String ttaken = startClockModel.getTtlTimeTaken();
                String etime = startClockModel.getEndTime();

                print.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createPdf(prjname,taskname,ttaken,etime);
                        dialog.dismiss();
                    }
                });


                delTask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean result = sqlLiteHelper.DeleteSelectedTask(prjname,taskname);

                        if(result){
                            Toast.makeText(CompletedtaskActivity.this,"Task deleted",Toast.LENGTH_SHORT).show();
                            list = sqlLiteHelper.getCompletedTasks();
                            dialog.dismiss();
                            arrayAdapter = new CompletetTaskAdapter(list,CompletedtaskActivity.this);
                            comptasklist.setAdapter(arrayAdapter);
                        }else{
                            Toast.makeText(CompletedtaskActivity.this,"Task not deleted",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
                 alertdialog.setView(popup);
                 dialog = alertdialog.create();
                 dialog.show();
            }

        });



    }


    public  void createPdf(String prjname,String taskname,String timetaken,String endtime){

        PdfDocument mypdfDocument = new PdfDocument();
        Paint myPaint = new Paint();

        PdfDocument.PageInfo pageInfo1 = new PdfDocument.PageInfo.Builder(250,400,1).create();
        PdfDocument.Page page1 = mypdfDocument.startPage(pageInfo1);
        Canvas canvas = page1.getCanvas();


        myPaint.setTextAlign(Paint.Align.CENTER);
        myPaint.setTextSize(12.0f);
        myPaint.setFlags(Paint.UNDERLINE_TEXT_FLAG);
        myPaint.setColor(Color.RED);
        if(prjname.length() > pageInfo1.getPageWidth()){

        }
        canvas.drawText(prjname,pageInfo1.getPageWidth()/2,20,myPaint);

        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(8.0f);
        myPaint.setUnderlineText(false);
        myPaint.setColor(Color.BLACK);
        canvas.drawText("Task Name : "+taskname,10,40,myPaint);

        /*myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(8.0f);
        myPaint.setUnderlineText(false);
        myPaint.setColor(Color.BLACK);
        canvas.drawText(taskname,65,40,myPaint);*/

        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(8.0f);
        myPaint.setColor(Color.BLACK);
        canvas.drawText("Task Completed in : " + timetaken,10,60,myPaint);

        /*myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(8.0f);
        myPaint.setColor(Color.BLACK);
        canvas.drawText(timetaken,95,60,myPaint);*/

        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(8.0f);
        myPaint.setColor(Color.BLACK);
        canvas.drawText("Task End time : "+ endtime,10,80,myPaint);

        /*myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(8.0f);
        myPaint.setColor(Color.BLACK);
        canvas.drawText(endtime,75,80,myPaint);*/

        mypdfDocument.finishPage(page1);

       // File file = new File(Environment.DIRECTORY_DOWNLOADS,"/CIT.pdf");
       File file = new File(Environment.getExternalStorageDirectory()+ "/" +Environment.DIRECTORY_DOWNLOADS,"/CIT.pdf");

        try {
            mypdfDocument.writeTo(new FileOutputStream(file));

            File outputFile = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS,"CIT.pdf");
            Uri uri = null;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                uri =  FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", outputFile);
            }

            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.setType("application/pdf");
            share.putExtra(Intent.EXTRA_STREAM,uri);
            share.setPackage("com.whatsapp");
            startActivity(share);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}