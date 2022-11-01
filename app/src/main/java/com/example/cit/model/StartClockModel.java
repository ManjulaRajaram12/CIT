package com.example.cit.model;

public class StartClockModel {

    private String empid;
    private String projectName;
    private String taskName;
    private String estimateTime;
    private String startTime;
    private String endTime;

    public String getChronometer() {
        return chronometer;
    }

    public void setChronometer(String chronometer) {
        this.chronometer = chronometer;
    }

    private String chronometer;

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    private String currentDate;

    public String getTtlTimeTaken() {
        return ttlTimeTaken;
    }

    public void setTtlTimeTaken(String ttlTimeTaken) {
        this.ttlTimeTaken = ttlTimeTaken;
    }

    private String ttlTimeTaken;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }



    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(String estimateTime) {
        this.estimateTime = estimateTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
