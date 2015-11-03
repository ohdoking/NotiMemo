package com.ohdoking.kimdongmemo.service;

import android.app.Notification;
import android.app.Notification.InboxStyle;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.ohdoking.kimdongmemo.R;

public class DeviceBootReceiver extends BroadcastReceiver {

	
	String memo = "none";
	Context context;
	
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            /* Setting the alarm here */
        	
        	this.context = context;
        	String tempMemo = getPreferences();
        	
//        	 Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();
//             
//           Vibrator vibe = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);         
//         vibe.vibrate(1000); 
        	
        	if(!tempMemo.equals("")){
        		memo = tempMemo;
        		makeNotification(context);
    		}
        	
        	
        }
    }
    
 // 값 불러오기
    private String getPreferences(){
        SharedPreferences pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        return pref.getString("memo", "");
    }
     
    // 값 저장하기
    private void savePreferences(String memo){
        SharedPreferences pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("memo", memo);
        editor.commit();
    }
	
	void makeNotification(Context context){
		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				new Intent(),
				PendingIntent.FLAG_UPDATE_CURRENT);

		Notification.Builder mBuilder = new Notification.Builder(context);
		
		mBuilder.setSmallIcon(R.drawable.ic_launcher);
		mBuilder.setTicker("오 메모장");
		mBuilder.setWhen(System.currentTimeMillis());
//		mBuilder.setNumber(list.size());
		mBuilder.setContentTitle("오 메모장");
		mBuilder.setContentText(memo);
		mBuilder.setDefaults(Notification.DEFAULT_SOUND
				| Notification.DEFAULT_VIBRATE);
		mBuilder.setContentIntent(pendingIntent);
		mBuilder.setAutoCancel(true);
		mBuilder.setOngoing(true) ;
		

		mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);

		Notification.InboxStyle style = new InboxStyle(mBuilder);
		
			
			style.addLine(memo);
//		style.setSummaryText(" More");
		 
		mBuilder.setStyle(style);
		
		nm.notify(111, mBuilder.build());
	}
}