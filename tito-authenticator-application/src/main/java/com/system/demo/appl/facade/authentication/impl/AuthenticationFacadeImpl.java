package com.system.demo.appl.facade.authentication.impl;

import com.system.demo.appl.facade.authentication.AuthenticationFacade;
import com.system.demo.appl.model.authentication.Authentication;
import com.system.demo.data.authentication.AuthenticationDao;

public class AuthenticationFacadeImpl implements AuthenticationFacade {
    private AuthenticationDao authenticationDao;

    public AuthenticationFacadeImpl(AuthenticationDao authenticationDao) {this.authenticationDao = authenticationDao;}

    @Override
    public Boolean validateAuthenticatorCode(String employeeNo, String authenticatorCode) throws RuntimeException{
        return authenticationDao.validateAuthenticatorCode(employeeNo, authenticatorCode);
    }

    @Override
    public Boolean validateSecretPhrase(String EmployeeNo, String secretPhrase) throws RuntimeException{
        return authenticationDao.validateSecretPhrase(EmployeeNo, secretPhrase);
    }

}
