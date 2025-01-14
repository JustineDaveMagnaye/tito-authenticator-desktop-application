package com.system.demo.data.timerecord;

import com.system.demo.appl.model.timeRecord.TimeRecord;

public interface TimeRecordDao {
    TimeRecord addTimeInRecord(TimeRecord timeRecord);

    TimeRecord addTimeOutRecord(TimeRecord timeRecord);

    TimeRecord timeRecordExists(TimeRecord timeRecord);
}
