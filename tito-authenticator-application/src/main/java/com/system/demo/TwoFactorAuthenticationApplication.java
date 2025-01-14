package com.system.demo;

import com.system.demo.appl.facade.authentication.AuthenticationFacade;
import com.system.demo.appl.facade.authentication.impl.AuthenticationFacadeImpl;
import com.system.demo.appl.facade.employee.EmployeeFacade;
import com.system.demo.appl.facade.employee.impl.EmployeeFacadeImpl;
import com.system.demo.appl.facade.user.UserFacade;
import com.system.demo.appl.facade.user.impl.UserFacadeImpl;
import com.system.demo.appl.model.employee.Employee;
import com.system.demo.appl.model.randomQuestion.randomQuestion;
import com.system.demo.appl.model.user.User;
import com.system.demo.data.authentication.AuthenticationDao;
import com.system.demo.data.authentication.impl.AuthenticationDaoImpl;
import com.system.demo.data.employee.dao.EmployeeDao;
import com.system.demo.data.employee.dao.impl.EmployeeDaoImpl;
import com.system.demo.data.user.dao.UserDao;
import com.system.demo.data.user.dao.impl.UserDaoImpl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class TwoFactorAuthenticationApplication {

    private AuthenticationFacade authenticationFacade;
    private EmployeeFacade employeeFacade;
    private UserFacade userFacade;

    public TwoFactorAuthenticationApplication() {
        AuthenticationDao authenticationDaoImpl = new AuthenticationDaoImpl();
        this.authenticationFacade = new AuthenticationFacadeImpl(authenticationDaoImpl);

        EmployeeDao employeeDaoImpl = new EmployeeDaoImpl();
        this.employeeFacade = new EmployeeFacadeImpl(employeeDaoImpl);

        UserDao userDaoImpl = new UserDaoImpl();
        this.userFacade = new UserFacadeImpl(userDaoImpl);
    }

    public AuthenticationFacade getAuthenticationFacade() {
        return authenticationFacade;
    }

    public EmployeeFacade getEmployeeFacade() {
        return employeeFacade;
    }

    public randomQuestion executeRandomChallenge(String employeeId) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Employee employee = employeeFacade.getEmployeeById(employeeId);
        List<Function<Employee, randomQuestion>> challengeFunctions = Arrays.asList(
                e -> new randomQuestion("Where is your Birthplace?", e.getBirthplace()),
                e -> new randomQuestion("When is your Birthdate?", format.format(e.getBirthdate()).toString()),
                e -> new randomQuestion("What is your Middle Name?", e.getMiddleName())
        );
        int challengeId = (int)(Math.random() * challengeFunctions.size());
        return challengeFunctions.get(challengeId).apply(employee);
    }

}
