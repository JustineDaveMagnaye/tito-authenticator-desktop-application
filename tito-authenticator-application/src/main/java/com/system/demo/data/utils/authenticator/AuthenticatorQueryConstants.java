package com.system.demo.data.utils.authenticator;

public class AuthenticatorQueryConstants {

    public static final String VALIDATE_AUTHENTICATOR_CODE = "SELECT * FROM AUTHENTICATOR WHERE EMPLOYEE_NUMBER = ? AND AUTHENTICATOR_CODE = ?";

    public static final String VALIDATE_SECRET_PHRASE = "SELECT * FROM SECRET_PHRASE WHERE EMPLOYEE_NUMBER = ? AND SECRET_PHRASE = ?";

}
