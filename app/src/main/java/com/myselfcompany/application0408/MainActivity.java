package com.myselfcompany.application0408;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Telephony;
import android.telecom.ConnectionService;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyMainActivity";
    private static final String TODO = "没有权限";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
////        功能一  资源转bitmap
//        Resources resources = this.getResources();
//        Bitmap bitmap1 = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_background);
////        功能一  资源转bitmap
//        InputStream inputStream = this.getResources().openRawResource(R.drawable.ic_launcher_background);
//        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        Log.d(TAG, "网络状态 + " + getNetState());
        Log.d(TAG, "当前时间 + " + nowTime());
        Log.d(TAG, "当前电话号码情况 + " + myTelNum() );
        startSystemWeb();
    }

    //    功能二 确认联网状态
    private boolean getNetState() {
        ConnectivityManager service = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network activeNetwork = service.getActiveNetwork();
        if (activeNetwork != null) {
            return true;
        }
        return false;
    }

    //功能三  获取当前时间
    private String nowTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar instance = Calendar.getInstance();
        String format = simpleDateFormat.format(instance.getTime());
        return format;
    }

    //    功能四  只有连续点退出间隔10秒以内才能退出
    @Override
    public void onBackPressed() {
        long oldTime = 0;
        long time = System.currentTimeMillis();
        if ((time - oldTime) <= 10000) {
            super.onBackPressed();
        }
        Toast.makeText(this, "拒绝推出", Toast.LENGTH_SHORT).show();
        oldTime = time;
    }

    //    功能五  获取当前手机号
    private String myTelNum() {
        TelephonyManager telecomManager = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return TODO;
        }
        String networkCountryIso = telecomManager.getLine1Number();
        return networkCountryIso;
    }

//    功能六  调用系统浏览器
    private void startSystemWeb(){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri parse = Uri.parse("https://www.google.com.hk");
        intent.setData(parse);
        startActivity(intent);
    }

//    功能七  判断当前卡运营商
//    private String myTelCompany(){
//        TelephonyManager telManager = (TelephonyManager)this.getSystemService(TELEPHONY_SERVICE);
//        String subscriberId = telManager.getSubscriberId();
//        if(subscriberId == null){
//            return "啥也没打印";
//        }
//        if(subscriberId.startsWith("46000" )|| subscriberId.startsWith("46002")){
//            Log.d(TAG, "myTelCompany: ");
//            return "这是移动号码";
//        } else if (subscriberId.startsWith("46001")) {
//            Log.d(TAG, "myTelCompany: ");
//            return "这是联通号码";
//        }else if(subscriberId.startsWith("46003")){
//            Log.d(TAG, "myTelCompany: ");
//            return "这是电信号码";
//        }else
//            return "奇怪的号码";
//    }
}