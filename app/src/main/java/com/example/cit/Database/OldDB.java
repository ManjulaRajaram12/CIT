package com.example.cit.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.cit.Util.Supporter;
import com.example.cit.model.StartClockModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OldDB{

    private static final String DATABASE_NAME = "CIT.db";
    private File DATABASE_PATH = null;

    private Cursor mCursor;
    private SQLiteDatabase sqlDatabase;
    private String errorCode;

    public static final String TBL_REGISTER= "Register";
    public static final String TBL_TASKDETAILS= "TakDetails";
    public static final String TBL_COMPTASKS= "CompletedTast";

    public static final String REG_NAME = "Name";
    public static final String REG_EMPID = "Empid";
    public static final String REG_EMAILID = "Emailid";
    public static final String REG_PHONENO = "Phoneno";
    public static final String REG_PASSWORD = "Password";

    public static final String TASK_EMPID = "empId";
    public static final String TASK_PRJ_NAME = "projectName";
    public static final String TASK_EST_TIME = "estimatedTime";
    public static final String TASK_START_TIME = "startedTime";
    public static final String TASK_TASK_NAME = "taskName";
    public static final String TASK_END_TIME = "endTime";
    public static final String TASK_CURRENT_DATE = "currentDate";

    public static final String COMPL_PRJ_NAME = "compProjectName";
    public static final String COMPL_TASK_NAME = "compTaskName";
    public static final String COMPL_START_TIME = "CompStartedTime";
    public static final String COMPL_END_TIME = "compEndTime";
    public static final String COMPL_TIMETAKEN = "compTimeTaken";

    public static final String CREATE_QUERY_REGISTER= "create table "
            + TBL_REGISTER + "(" + REG_NAME + " text,"
            + REG_EMPID + " text," + REG_EMAILID
            + " text," + REG_PHONENO + " text,"
            + REG_PASSWORD + " text);";

    public static final String CREATE_QUERY_TASTDETAILS= "create table "
            + TBL_TASKDETAILS + "(" + TASK_EMPID + " text,"
            + TASK_PRJ_NAME + " text," + TASK_EST_TIME
            + " text," + TASK_START_TIME + " text,"
            + TASK_TASK_NAME + " text,"
            + TASK_END_TIME + " text,"
            + TASK_CURRENT_DATE + " text);";

    public static final String CREATE_QUERY_COMPLETED_TASKS= "create table "
            + TBL_COMPTASKS + "(" + COMPL_PRJ_NAME + " text,"
            + COMPL_TASK_NAME+ " text," + COMPL_START_TIME
            + " text," + COMPL_END_TIME + " text,"
            + COMPL_TIMETAKEN + " text);";

    public OldDB(Context context) {
        DATABASE_PATH = new File(Supporter.getAppCommonPath(), DATABASE_NAME);

        if (!DATABASE_PATH.exists()) {
            try {

                sqlDatabase = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH,
                        null);
                //sqlDatabase.setVersion(1);

                sqlDatabase.beginTransaction();

                sqlDatabase.execSQL(CREATE_QUERY_REGISTER);
                sqlDatabase.execSQL(CREATE_QUERY_TASTDETAILS);
                sqlDatabase.execSQL(CREATE_QUERY_COMPLETED_TASKS);

                sqlDatabase.setTransactionSuccessful();
            } catch (Exception exe) {
                sqlDatabase.endTransaction();
                errorCode = "Err-DB-1";
            } finally {
                sqlDatabase.endTransaction();
                sqlDatabase.close();
            }
        }
    }
    // methods to provide db access
    public SQLiteDatabase getReadableDatabase() {

        if (DATABASE_PATH.exists()) {
            String db_Path = DATABASE_PATH.getAbsolutePath();
            System.out.println("Database path: " + db_Path);
            sqlDatabase = SQLiteDatabase.openDatabase(db_Path, null,
                    SQLiteDatabase.OPEN_READONLY);
        }

        return sqlDatabase;
    }

    public SQLiteDatabase getWritableDatabase() {

        if (DATABASE_PATH.exists()) {
            String db_Path = DATABASE_PATH.getAbsolutePath();
            System.out.println("Database path: " + db_Path);
            sqlDatabase = SQLiteDatabase.openDatabase(db_Path, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }

        return sqlDatabase;
    }

    public String RegisterUserData(String name,String empid,String emailid,String phoneno,String pass){
        String success = "success";

        ContentValues cv = new ContentValues();

        try {

            cv.put(REG_NAME,name);
            cv.put(REG_EMPID,empid);
            cv.put(REG_EMAILID,emailid);
            cv.put(REG_PHONENO,phoneno);
            cv.put(REG_PASSWORD,pass);
            sqlDatabase.insert(TBL_REGISTER,null,cv);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;

    }

    public List<String> CheckValidUser(String empid, String pass){
        List<String> UserData = new ArrayList<String>();

        boolean result = false;
        Cursor cursor;

        try{

            cursor = sqlDatabase.rawQuery("select * from Register where Empid = '"
                    + empid + "'", null);

            while (cursor.moveToNext()){
                UserData.add(cursor.getString(0));
                UserData.add(cursor.getString(1));
                UserData.add(cursor.getString(2));
                UserData.add(cursor.getString(3));
                UserData.add(cursor.getString(4));

                String empID = cursor.getString(1);
                String emailID = cursor.getString(2);
                String phoneNo = cursor.getString(3);
                String passWord = cursor.getString(4);

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return UserData;
    }

    public boolean updateTaskStartDetails(StartClockModel startClockModel){
        boolean result = false;

        ContentValues cv =new ContentValues();

        try {
            cv.put(TASK_EMPID,startClockModel.getEmpid());
            cv.put(TASK_PRJ_NAME,startClockModel.getProjectName());
            cv.put(TASK_TASK_NAME,startClockModel.getTaskName());
            cv.put(TASK_EST_TIME,startClockModel.getEstimateTime());
            cv.put(TASK_START_TIME,startClockModel.getStartTime());
            cv.put(TASK_CURRENT_DATE,startClockModel.getCurrentDate());


            sqlDatabase.insert(OldDB.TBL_TASKDETAILS,null,cv);
            result =true;
        } catch (Exception e) {
            result =false;
            e.printStackTrace();
        }


        return result;
    }

    public List<StartClockModel> getStartedTaskList(){
        List<StartClockModel> list = new ArrayList<StartClockModel>();

        StartClockModel startClockModel = null;

        mCursor =sqlDatabase.rawQuery("select * from TakDetails",null);

        try {


            while (mCursor.moveToNext()) {

                startClockModel = new StartClockModel();

                startClockModel.setProjectName(mCursor.getString(1));
                startClockModel.setEstimateTime(mCursor.getString(2));
                startClockModel.setStartTime(mCursor.getString(3));
                startClockModel.setTaskName(mCursor.getString(4));
                startClockModel.setCurrentDate(mCursor.getString(6));

                list.add(startClockModel);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        mCursor.close();

        return list;

    }

    public void closeDatabase() {
        this.sqlDatabase.close();
        Log.i("Db closed", "Db closed.");
    }

    public boolean updateCompletedTast(String prjName, String tskName, String startime, String currentTime, String timetaken) {
        boolean result = false;

        ContentValues cv = new ContentValues();

        try {
            cv.put(COMPL_PRJ_NAME,prjName);
            cv.put(COMPL_TASK_NAME,tskName);
            cv.put(COMPL_START_TIME,startime);
            cv.put(COMPL_END_TIME,currentTime);
            cv.put(COMPL_TIMETAKEN,timetaken);

            sqlDatabase.insert(OldDB.TBL_COMPTASKS,null,cv);
            result = true;

        } catch (Exception e) {
            result =false;
            e.printStackTrace();
        }

        return result;
    }

    public boolean DeleteEndtask(String prjName, String tskName) {

        boolean delete =false;

        try {
            sqlDatabase.delete(OldDB.TBL_TASKDETAILS, "projectName = '" + prjName
                    + "' and taskName = '" + tskName + "'", null);

            delete = true;
        } catch (Exception e) {

            delete =false;
            e.printStackTrace();
        }

        return delete;
    }

    public List<StartClockModel> getCompletedTasks() {
        List<StartClockModel> list = new ArrayList<>();

        StartClockModel startClockModel = null;

        mCursor =sqlDatabase.rawQuery("select * from CompletedTast",null);

        try{



            while (mCursor.moveToNext()) {

                startClockModel = new StartClockModel();

                startClockModel.setProjectName(mCursor.getString(0));
                startClockModel.setTaskName(mCursor.getString(1));
                startClockModel.setStartTime(mCursor.getString(2));
                startClockModel.setEndTime(mCursor.getString(3));
                startClockModel.setTtlTimeTaken(mCursor.getString(4));

                list.add(startClockModel);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }
}

