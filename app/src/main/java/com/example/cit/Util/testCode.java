package com.example.cit.Util;

public class testCode {


   /* long time = SystemClock.elapsedRealtime() - cArg.getBase();
    int h   = (int)(time /3600000);
    int m = (int)(time - h*3600000)/60000;
    int s= (int)(time - h*3600000- m*60000)/1000 ;
    String hh = h < 10 ? "0"+h: h+"";
    String mm = m < 10 ? "0"+m: m+"";
    String ss = s < 10 ? "0"+s: s+"";
      cArg.setText(hh+":"+mm+":"+ss);*/



/*
    package codewithcal.au.timer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

    public class MainActivity extends AppCompatActivity
    {
        TextView timerText;
        Button stopStartButton;

        Timer timer;
        TimerTask timerTask;
        Double time = 0.0;

        boolean timerStarted = false;

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            timerText = (TextView) findViewById(R.id.timerText);
            stopStartButton = (Button) findViewById(R.id.startStopButton);

            timer = new Timer();

        }

        public void resetTapped(View view)
        {
            AlertDialog.Builder resetAlert = new AlertDialog.Builder(this);
            resetAlert.setTitle("Reset Timer");
            resetAlert.setMessage("Are you sure you want to reset the timer?");
            resetAlert.setPositiveButton("Reset", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    if(timerTask != null)
                    {
                        timerTask.cancel();
                        setButtonUI("START", R.color.green);
                        time = 0.0;
                        timerStarted = false;
                        timerText.setText(formatTime(0,0,0));

                    }
                }
            });

            resetAlert.setNeutralButton("Cancel", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    //do nothing
                }
            });

            resetAlert.show();

        }

        public void startStopTapped(View view)
        {
            if(timerStarted == false)
            {
                timerStarted = true;
                setButtonUI("STOP", R.color.red);

                startTimer();
            }
            else
            {
                timerStarted = false;
                setButtonUI("START", R.color.green);

                timerTask.cancel();
            }
        }

        private void setButtonUI(String start, int color)
        {
            stopStartButton.setText(start);
            stopStartButton.setTextColor(ContextCompat.getColor(this, color));
        }

        private void startTimer()
        {
            timerTask = new TimerTask()
            {
                @Override
                public void run()
                {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            time++;
                            timerText.setText(getTimerText());
                        }
                    });
                }

            };
            timer.scheduleAtFixedRate(timerTask, 0 ,1000);
        }


        private String getTimerText()
        {
            int rounded = (int) Math.round(time);

            int seconds = ((rounded % 86400) % 3600) % 60;
            int minutes = ((rounded % 86400) % 3600) / 60;
            int hours = ((rounded % 86400) / 3600);

            return formatTime(seconds, minutes, hours);
        }

        private String formatTime(int seconds, int minutes, int hours)
        {
            return String.format("%02d",hours) + " : " + String.format("%02d",minutes) + " : " + String.format("%02d",seconds);
        }

    }*/









/*
    // Java program to find the minimum distance
// between two occurrences of the same element

import java.util.*;
import java.math.*;

    class GFG {

        // Function to find the minimum
        // distance between the same elements
        static int minimumDistance(int[] a)
        {

            // Create a HashMap to
            // store (key, values) pair.
            HashMap<Integer, Integer> hmap
                    = new HashMap<>();
            int minDistance = Integer.MAX_VALUE;

            // Initialize previousIndex
            // and currentIndex as 0
            int previousIndex = 0, currentIndex = 0;

            // Traverse the array and
            // find the minimum distance
            // between the same elements with map
            for (int i = 0; i < a.length; i++) {

                if (hmap.containsKey(a[i])) {
                    currentIndex = i;

                    // Fetch the previous index from map.
                    previousIndex = hmap.get(a[i]);

                    // Find the minimum distance.
                    minDistance
                            = Math.min(
                            (currentIndex - previousIndex),
                            minDistance);
                }

                // Update the map.
                hmap.put(a[i], i);
            }

            // return minimum distance,
            // if no such elements found, return -1
            return (
                    minDistance == Integer.MAX_VALUE
                            ? -1
                            : minDistance);
        }

        // Driver code
        public static void main(String args[])
        {

            // Test Case 1:
            int a1[] = { 1, 2, 3, 2, 1 };
            System.out.println(minimumDistance(a1));

            // Test Case 2:
            int a2[] = { 3, 5, 4, 6, 5, 3 };
            System.out.println(minimumDistance(a2));

            // Test Case 3:
            int a3[] = { 1, 2, 1, 4, 1 };
            System.out.println(minimumDistance(a3));
        }
    }
*/






}
