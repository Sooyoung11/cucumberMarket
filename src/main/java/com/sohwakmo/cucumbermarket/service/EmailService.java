package com.sohwakmo.cucumbermarket.service;

public interface EmailService {
    String sendEmail(String to)throws Exception;
    String sendLink(String to)throws Exception;
}

