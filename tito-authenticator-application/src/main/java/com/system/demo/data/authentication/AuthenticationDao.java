package com.system.demo.data.authentication;

import com.system.demo.appl.model.authentication.Authentication;

public interface AuthenticationDao {
    Boolean validateAuthenticatorCode(String employeeNo, String authenticatorCode);

    Boolean validateSecretPhrase(String employeeNo, String secretPhrase);
}
