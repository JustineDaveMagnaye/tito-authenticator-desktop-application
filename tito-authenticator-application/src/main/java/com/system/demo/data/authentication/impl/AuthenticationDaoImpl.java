package com.system.demo.data.authentication.impl;

import com.system.demo.appl.model.authentication.Authentication;
import com.system.demo.data.connection.ConnectionHelper;
import com.system.demo.data.authentication.AuthenticationDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.system.demo.data.utils.authenticator.AuthenticatorQueryConstants.*;

public class AuthenticationDaoImpl implements AuthenticationDao {

    @Override
    public Boolean validateAuthenticatorCode(String employeeNo, String authenticatorCode){
        try(Connection connection = ConnectionHelper.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(VALIDATE_AUTHENTICATOR_CODE);
            preparedStatement.setString(1, employeeNo);
            preparedStatement.setString(2, authenticatorCode);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public Boolean validateSecretPhrase(String employeeNo, String secretPhrase) {
        try(Connection connection = ConnectionHelper.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(VALIDATE_SECRET_PHRASE);
            preparedStatement.setString(1, employeeNo);
            preparedStatement.setString(2, secretPhrase);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return true;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
