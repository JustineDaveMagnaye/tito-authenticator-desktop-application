package com.system.demo.appl.facade.timerecord;

import com.system.demo.appl.model.timeRecord.TimeRecord;

import java.sql.Time;

public interface TimeRecordFacade {
    TimeRecord checkTimeInRecord(TimeRecord timeRecord);

    TimeRecord checkTimeOutRecord(TimeRecord timeRecord);

    TimeRecord addTimeInRecord(TimeRecord timeRecord);

    TimeRecord addTimeOutRecord(TimeRecord timeRecord);
}
