package com.system.demo.data.timerecord.impl;

import com.system.demo.appl.model.timeRecord.TimeRecord;
import com.system.demo.data.connection.ConnectionHelper;
import com.system.demo.data.timerecord.TimeRecordDao;

import java.sql.*;
import java.text.SimpleDateFormat;

public class TimeRecordDaoImpl implements TimeRecordDao {

    private TimeRecord mapResultSetToUser(ResultSet rs) throws SQLException {
        TimeRecord timeRecord = new TimeRecord();

        timeRecord.setEmployeeNumber(rs.getString("EMPLOYEE_NUMBER"));
        timeRecord.setTimeIn(rs.getTimestamp("TIME_IN"));
        timeRecord.setId(rs.getInt("ID"));
        timeRecord.setTimeOut(rs.getTimestamp("TIME_OUT"));
        timeRecord.setCreatedAt(rs.getString("CREATED_AT"));

        return timeRecord;
    }

    @Override
    public TimeRecord addTimeInRecord(TimeRecord timeRecord) {
        boolean result = false;
        try (Connection connection = ConnectionHelper.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO TIME_RECORD (EMPLOYEE_NUMBER, TIME_IN, TOTAL_HOURS, CREATED_AT, TYPE) VALUES (?, ?, ?, ?, ?)"
            );

            preparedStatement.setString(1, timeRecord.getEmployeeNumber());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
            Timestamp timeInTimestamp = timeRecord.getTimeIn();
            preparedStatement.setTimestamp(2, timeInTimestamp); // Set timeIn as Timestamp

            preparedStatement.setDouble(3, timeRecord.getTotalHours());
            preparedStatement.setString(4, timeRecord.getCreatedAt()); // Set createdAt
            preparedStatement.setString(5,timeRecord.getType());

            int rowsAffected = preparedStatement.executeUpdate();
            result = rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Error adding time record: " + e.getMessage());
        }

        return timeRecord;
    }

    @Override
    public TimeRecord addTimeOutRecord(TimeRecord timeRecord) {
        boolean result = false;
        try (Connection connection = ConnectionHelper.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE TIME_RECORD SET TIME_OUT= ?, TOTAL_HOURS = ? WHERE EMPLOYEE_NUMBER = ? AND CREATED_AT = ?"
            );
            preparedStatement.setTimestamp(1, timeRecord.getTimeOut());
            preparedStatement.setDouble(2, timeRecord.getTotalHours());
            preparedStatement.setString(3, timeRecord.getEmployeeNumber());
            preparedStatement.setString(4, timeRecord.getCreatedAt());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return timeRecord;
                }
            }
        } catch (Exception e) {
            System.out.println("Error adding time record: " + e.getMessage());
        }

        return timeRecord;
    }

    @Override
    public TimeRecord timeRecordExists(TimeRecord timeRecord) {
        try (Connection connection = ConnectionHelper.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM TIME_RECORD WHERE EMPLOYEE_NUMBER = ? AND CREATED_AT = ?"
            );
            preparedStatement.setString(1, timeRecord.getEmployeeNumber());
            preparedStatement.setString(2, timeRecord.getCreatedAt());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToUser(resultSet);
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

}
