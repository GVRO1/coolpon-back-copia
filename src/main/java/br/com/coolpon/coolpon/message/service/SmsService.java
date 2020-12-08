package br.com.coolpon.coolpon.message.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsService {
    private final String ACCOUNT_SID = "AC8afc4e7fa369915ff7c0f676bc830453";
    private final String AUTH_TOKEN = "569e7ed9f829216f3f2a0dcc2a5912ff";
    private final String SENDER = "+16592014220";
    private Message message;


    public SmsService() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public String sendSms(String recipient, String message) {

        this.message = Message.creator(
                new PhoneNumber("+5511986353713"),
                new PhoneNumber(SENDER),
                message
        ).create();

        return this.message.getSid();
    }
}
