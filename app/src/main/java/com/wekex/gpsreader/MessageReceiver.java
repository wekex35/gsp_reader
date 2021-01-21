package com.wekex.gpsreader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MessageReceiver extends BroadcastReceiver {

    private static MessageListener mListener;



    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();
    String mobile,body;

    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();
                    mobile=senderNum.replaceAll("\\s","");
                    body=message.replaceAll("\\s","+");


                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + body);


                    // Show Alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,
                            "senderNum: "+ mobile+ ", message: " + message, duration);
                    toast.show();

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        Log.d("==========> TAG", "onReceive: ");
//        Bundle data = intent.getExtras();
//        Object[] pdus = (Object[]) data.get("pdus");
//        for(int i=0; i<pdus.length; i++){
//            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
////            String message = "Sender : " + smsMessage.getDisplayOriginatingAddress()
////                    + "Email From: " + smsMessage.getEmailFrom()
////                    + "Emal Body: " + smsMessage.getEmailBody()
////                    + "Display message body: " + smsMessage.getDisplayMessageBody()
////                    + "Time in millisecond: " + smsMessage.getTimestampMillis()
////                    + "Message: " + smsMessage.getMessageBody();
//            mListener.messageReceived("message test");
//        }
//    }

    public static void bindListener(MessageListener listener){
        mListener = listener;
    }
}