package com.wekex.gpsreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.tuenti.smsradar.Sms;
import com.tuenti.smsradar.SmsListener;
import com.tuenti.smsradar.SmsRadar;

import de.adorsys.android.smsparser.SmsReceiver;
import de.adorsys.android.smsparser.SmsTool;

public class MainActivity extends AppCompatActivity {
    private  LocalBroadcastManager localBroadcastManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            SmsTool.requestSMSPermission(this);
        }
        SmsRadar.initializeSmsRadarService(this, new SmsListener() {
            @Override
            public void onSmsSent(Sms sms) {
                showSmsToast(sms);
            }

            @Override
            public void onSmsReceived(Sms sms) {
                showSmsToast(sms);
            }
        });
    }

    private void showSmsToast(Sms sms) {
        Log.d("TAG", "showSmsToast: "+sms);
    }





        @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SmsTool.REQUEST_CODE_ASK_PERMISSIONS && (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED)) {
            Toast.makeText(this, getString(R.string.warning_permission_not_granted),
                    Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + this)));
        }
    }

}