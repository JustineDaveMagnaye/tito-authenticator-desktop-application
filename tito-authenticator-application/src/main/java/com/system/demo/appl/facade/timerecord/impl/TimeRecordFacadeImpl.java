package com.system.demo.appl.facade.timerecord.impl;

import com.system.demo.appl.facade.timerecord.TimeRecordFacade;
import com.system.demo.appl.model.timeRecord.TimeRecord;
import com.system.demo.data.timerecord.TimeRecordDao;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeRecordFacadeImpl implements TimeRecordFacade {

    private TimeRecordDao timeRecordDao;

    public TimeRecordFacadeImpl(TimeRecordDao timeRecordDao) {
        this.timeRecordDao = timeRecordDao;
    }
    @Override
    public TimeRecord checkTimeInRecord(TimeRecord timeRecord) {
        try {
            return timeInData(timeRecord.getEmployeeNumber(), timeRecord.getCreatedAt());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public TimeRecord checkTimeOutRecord(TimeRecord timeRecord) {
        try {
            return timeOutData(timeRecord.getEmployeeNumber(), timeRecord.getCreatedAt());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public TimeRecord addTimeInRecord(TimeRecord timeRecord) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            TimeRecord newRecord = new TimeRecord();
            newRecord.setId(timeRecord.getId());
            newRecord.setEmployeeNumber(timeRecord.getEmployeeNumber());
            newRecord.setTimeIn(timeRecord.getTimeIn());
            Date currentDate = new Date();
            String currentDateString = format.format(currentDate);
            newRecord.setCreatedAt(currentDateString);
            newRecord.setType("Onsite");
            return timeRecordDao.addTimeInRecord(newRecord);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public TimeRecord addTimeOutRecord(TimeRecord timeRecord) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            TimeRecord newRecord = new TimeRecord();
            newRecord.setEmployeeNumber(timeRecord.getEmployeeNumber());
            newRecord.setTimeOut(timeRecord.getTimeOut());

            Date currentDate = new Date();
            String currentDateString = format.format(currentDate);
            newRecord.setCreatedAt(currentDateString);

            TimeRecord existingRecord = timeRecordDao.timeRecordExists(newRecord);

            long durationMillis = timeRecord.getTimeOut().getTime() - existingRecord.getTimeIn().getTime();
            newRecord.setTotalHours(durationMillis / 3600000.0);
            newRecord.setType("Onsite");
            return timeRecordDao.addTimeOutRecord(newRecord);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public TimeRecord timeInData(String employeeNumber, String createdAt) {
        try{
            TimeRecord newRecord = new TimeRecord();
            newRecord.setEmployeeNumber(employeeNumber);
            newRecord.setCreatedAt(createdAt);
            TimeRecord check = timeRecordDao.timeRecordExists(newRecord);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date currentDate = new Date();
            String currentDateString = format.format(currentDate);
            if(check != null && check.getCreatedAt().equals(currentDateString)){
                System.out.println("This employee is already timed in today!");
                return null;
            } else {
                return newRecord;
            }
        } catch (Exception e){
            return null;
        }
    }


    public TimeRecord timeOutData(String employeeNumber, String createdAt) {
        try{
            TimeRecord newRecord = new TimeRecord();
            newRecord.setEmployeeNumber(employeeNumber);
            newRecord.setCreatedAt(createdAt);
            TimeRecord check = timeRecordDao.timeRecordExists(newRecord);
            if(check != null && check.getTimeOut() != null){
                System.out.println("This employee is already timed out today!");
                return null;
            } else if(check == null){
                System.out.println("You haven't timed In yet!");
                return null;
            }
            return check;
        } catch (Exception e){
            return null;
        }
    }

}
