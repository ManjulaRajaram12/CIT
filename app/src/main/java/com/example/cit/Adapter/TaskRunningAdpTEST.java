package com.example.cit.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cit.Database.SqlLiteHelper;
import com.example.cit.R;
import com.example.cit.StartClockInActivity;
import com.example.cit.model.StartClockModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskRunningAdpTEST extends ArrayAdapter<StartClockModel> {

    private final List<StartClockModel> list;
    private final Activity context;

    private SqlLiteHelper sqlLiteHelper;
    StartClockInActivity startClockInActivity;
    private ArrayAdapter<StartClockModel> listAdapter;
    private int min=0;
    private int hour=0;
    String totaltime ="";
    String CurrentTime = "";
    String Startime = "";
    int sHour = 0;
    int eHour = 0;
    Chronometer cmeter;

    public TaskRunningAdpTEST(Activity context,List<StartClockModel> list,ArrayAdapter<StartClockModel> listAdapter) {
        super(context, R.layout.runningtasklayout,list);
        this.list = list;
        this.context = context;
        cmeter = new Chronometer(getContext());
    }

    static class ViewHolder {
        protected TextView prjname;
        protected TextView taskname;
        protected TextView starttime;
        protected Button endtime;
        protected TextClock endtasktime;
        protected TextView currentdate;
        protected Chronometer Chronometer;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {



        sqlLiteHelper =new SqlLiteHelper(getContext());
        View view = null;

        if(convertView ==null){
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.runningtasklayout,null);

            final ViewHolder holder = new ViewHolder();

            holder.prjname = view.findViewById(R.id.projName);
            holder.taskname = view.findViewById(R.id.Taskname);
            holder.starttime = view.findViewById(R.id.startedtime);
            holder.endtime = view.findViewById(R.id.endTask);
            holder.endtasktime = view.findViewById(R.id.endtaskTime);
            holder.currentdate= view.findViewById(R.id.crntdate);
            holder.Chronometer= view.findViewById(R.id.chronometer);

            view.setTag(holder);

        }else{

            view = convertView;

        }

        final ViewHolder holder = (ViewHolder) view.getTag();

        holder.prjname.setText(list.get(position).getProjectName());
        holder.taskname.setText(list.get(position).getTaskName());
        holder.starttime.setText(list.get(position).getStartTime());
        holder.currentdate.setText(list.get(position).getCurrentDate());
        holder.Chronometer.start();

        holder.endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int a[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };

                StartClockModel item = (StartClockModel) getItem(position);

                String prjName = item.getProjectName();
                String tskName = item.getTaskName();
                /*Startime = item.getStartTime();
                CurrentTime = holder.endtasktime.getText().toString();*/

                Startime = "12:15 PM";
                CurrentTime = "11:23 PM";



           /* long elapsedMillis = SystemClock.elapsedRealtime() - cmeter.getBase();
            int h   = (int)(elapsedMillis /3600000);
            int m = (int)(elapsedMillis - h*3600000)/60000;
            int s= (int)(elapsedMillis - h*3600000- m*60000)/1000 ;
            String hh = h < 10 ? "0"+h: h+"";
            String mm = m < 10 ? "0"+m: m+"";
            String ss = s < 10 ? "0"+s: s+"";
            String stopclock = hh +":"+mm+":"+ss;*/

                String Startedadte = holder.currentdate.getText().toString();
                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                String[] sDate = Startedadte.split("-");
                String[] eDate = date.split("-");

                int sdate1 = Integer.parseInt(sDate[0]);
                int sdate2 = Integer.parseInt(sDate[1]);
                int sdate3 = Integer.parseInt(sDate[2]);

                int edate1 = Integer.parseInt(eDate[0]);
                int edate2 = Integer.parseInt(eDate[1]);
                int edate3 = Integer.parseInt(eDate[2]);

                if(edate1 > sdate1 && sdate2 == edate2 && edate3 == sdate3){

                    totaltime = String.valueOf(edate1-sdate1) + " day";

                    boolean result = sqlLiteHelper.updateCompletedTast(prjName,tskName,Startime,CurrentTime,totaltime);

                    if(result) {

                        boolean deleted = sqlLiteHelper.DeleteEndtask(prjName, tskName);

                        list.remove(position);
                        notifyDataSetChanged();

                        Toast.makeText(getContext(), "Task completed successfully", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "Unknown error", Toast.LENGTH_SHORT).show();
                    }

                }else{

                    String stime = Startime.replace("PM", "");
                    String ctime = CurrentTime.replace("PM", "");
                    String Stime = stime.replace("AM", "");
                    String Ctime = ctime.replace("AM", "");
                    String starttime = Stime.replace(" ", "");
                    String endtime = Ctime.replace(" ", "");

                    String[] sarry = starttime.split(":");
                    String[] carry1 = endtime.split(":");

                    int sMin = Integer.parseInt(sarry[1]);
                    int eMin = Integer.parseInt(carry1[1]);

                    eHour = Integer.parseInt(carry1[0]);
                    sHour = Integer.parseInt(sarry[0]);


               /* if(CurrentTime.contains("PM")){
                    eHour = Integer.parseInt(carry1[0]) + 12;
                }else {
                    eHour = Integer.parseInt(carry1[0]);
                }
                if(Startime.contains("PM")){
                     sHour = Integer.parseInt(sarry[0]) + 12;
                }else{
                    sHour = Integer.parseInt(sarry[0]);
                }*/

                    int[] numbers = {0,1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
                    int element = sHour;
                    int element2 = eHour;
                    int index = -1;
                    int index2 = -1;

                    for(int i = 0; i < numbers.length; i++) {
                        if(numbers[i] == element || numbers[i] == element2) {
                            if(numbers[i] == element){
                            index = i;
                            }
                            if(numbers[i] == element2){
                            index2 = i;
                            }
                        }
                    }

                    int arrLength = numbers.length;
                    int firstIndex = index;
                    int secondIndex = index2;

                    int min_dist = Integer.MAX_VALUE;
                    for (int i = 0; i < 26; i++) {
                        for (int j = i + 1; j < arrLength; j++) {
                            if ((firstIndex == numbers[i] && secondIndex == numbers[j]
                                    || secondIndex == numbers[i] && firstIndex == numbers[j])
                                    && min_dist > Math.abs(i - j))
                                min_dist = Math.abs(i - j);
                        }
                    }

                    if (min_dist > arrLength) {
                        min_dist =  0;
                    }

                    System.out.println(min_dist);

                if (sMin > eMin) {
                            min = sMin - eMin;
                        } else if (eMin > sMin) {
                            min = eMin - sMin;
                        }
                if (sHour > eHour) {
                            hour = sHour - eHour;
                        } else if (eHour > sHour) {
                            hour = eHour - sHour;
                        }


                if (min_dist == 0) {
                            totaltime = String.valueOf(min) + "min";
                        } else {
                            totaltime = String.valueOf(min_dist) + "hr" + String.valueOf(min) + "min";
                        }

                        boolean result = sqlLiteHelper.updateCompletedTast(prjName, tskName, Startime, CurrentTime, totaltime);

                if (result) {

                            boolean deleted = sqlLiteHelper.DeleteEndtask(prjName, tskName);

                            list.remove(position);
                            notifyDataSetChanged();

                            Toast.makeText(getContext(), "Task completed successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Unknown error", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });

    return view;

        }

    }
