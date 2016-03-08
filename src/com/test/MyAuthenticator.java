package com.test;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyAuthenticator extends Authenticator {
    String userName=null;
    String password=null;
       
    public MyAuthenticator(){
    }
    public MyAuthenticator(String username, String password) { 
        this.userName = username; 
        this.password = password; 
    }
    
    /**
     * ����У��
     */
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    } 
}