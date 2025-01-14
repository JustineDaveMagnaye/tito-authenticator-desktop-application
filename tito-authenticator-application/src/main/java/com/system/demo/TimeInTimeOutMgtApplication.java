package com.system.demo;

import com.system.demo.appl.facade.employee.EmployeeFacade;
import com.system.demo.appl.facade.employee.impl.EmployeeFacadeImpl;
import com.system.demo.appl.facade.timerecord.TimeRecordFacade;
import com.system.demo.appl.facade.timerecord.impl.TimeRecordFacadeImpl;
import com.system.demo.appl.model.timeRecord.TimeRecord;
import com.system.demo.data.employee.dao.EmployeeDao;
import com.system.demo.data.employee.dao.impl.EmployeeDaoImpl;
import com.system.demo.data.timerecord.TimeRecordDao;
import com.system.demo.data.timerecord.impl.TimeRecordDaoImpl;

public class TimeInTimeOutMgtApplication {
    private TimeRecordFacade timeRecordFacade;
    /**
     * This creates a new EmployeeInfoMgtApplication
     * @return the employeeFacade this helps for managing employee data.
     */
    public TimeInTimeOutMgtApplication() {
        TimeRecordDao timeRecordDaoImpl = new TimeRecordDaoImpl();
        this.timeRecordFacade = new TimeRecordFacadeImpl(timeRecordDaoImpl);
    }
    /**
     * This gets the Employee Facade.
     * @return the employee facade.
     */
    public TimeRecordFacade getTimeRecordFacade() {
        return timeRecordFacade;
    }
}