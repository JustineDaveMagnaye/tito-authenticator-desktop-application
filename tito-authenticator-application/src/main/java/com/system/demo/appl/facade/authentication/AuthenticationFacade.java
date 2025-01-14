package com.system.demo.appl.facade.authentication;

import com.system.demo.appl.model.authentication.Authentication;

public interface AuthenticationFacade {
    Boolean validateAuthenticatorCode(String employeeNo, String authenticatorCode) throws RuntimeException;

    Boolean validateSecretPhrase(String EmployeeNo, String secretPhrase) throws RuntimeException;

}
