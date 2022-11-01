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

public class TaskRunningAdapter extends ArrayAdapter<StartClockModel> {

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
    int seHour = 0;
    Chronometer cmeter;

    public TaskRunningAdapter(Activity context,List<StartClockModel> list,ArrayAdapter<StartClockModel> listAdapter) {
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
    //holder.Chronometer.start();

    holder.endtime.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            StartClockModel item = (StartClockModel) getItem(position);

            String prjName = item.getProjectName();
            String tskName = item.getTaskName();
            Startime = item.getStartTime();
            CurrentTime = holder.endtasktime.getText().toString();

            /*Startime = "12:15 PM";
            CurrentTime = "11:23 PM";

            Startime = "12:21 AM";
            CurrentTime = "9:51 AM";

            Startime = "2:15 PM";
            CurrentTime = "9:23 PM";*/

           /* Startime = "12:15 PM";
            CurrentTime = "6:23 PM";*/

            /*Startime = "4:15 PM";
            CurrentTime = "5:23 PM";*/

            /*Startime = "1:15 PM";
            CurrentTime = "12:23 PM";*/

            /*Startime = "12:15 PM";
            CurrentTime = "1:23 PM";*/



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


            //for now date is available but can be removed totally
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

                String stime = Startime.replace("PM", "").replace("pm","");
                String ctime = CurrentTime.replace("PM", "").replace("pm","");
                String Stime = stime.replace("AM", "").replace("am","");
                String Ctime = ctime.replace("AM", "").replace("am","");
                String starttime = Stime.replace(" ", "");
                String endtime = Ctime.replace(" ", "");

                String[] sarry = starttime.split(":");
                String[] carry1 = endtime.split(":");

                int sMin = Integer.parseInt(sarry[1]);
                int eMin = Integer.parseInt(carry1[1]);


                sHour = Integer.parseInt(sarry[0]);
                eHour = Integer.parseInt(carry1[0]);

                if(sHour > eHour){
                    seHour =  sHour + eHour;
                    hour = seHour-sHour;
                }

                /*if (sHour > eHour) {
                    hour = sHour - eHour;
                } else */if (eHour > sHour) {
                    hour = eHour - sHour;
                }

                if (sMin > eMin) {
                    min = sMin - eMin;
                } else if (eMin > sMin) {
                    min = eMin - sMin;
                }



                if (hour == 0) {
                    totaltime = String.valueOf(min) + "min";
                } else {
                    totaltime = String.valueOf(hour) + "hr" + String.valueOf(min) + "min";
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
