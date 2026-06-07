package com.starpattern.assistant;

import android.app.*; import android.content.*; import android.graphics.PixelFormat; import android.os.*; import android.view.*; import android.widget.*;

public class OverlayService extends Service {
    WindowManager wm; View view;
    public int onStartCommand(Intent i,int flags,int startId){ show(); return START_STICKY; }
    void show(){
        if(view!=null) return; wm=(WindowManager)getSystemService(WINDOW_SERVICE);
        TextView tv=new TextView(this); tv.setText("Star V5 AI\nOpen app → Predict\nSafe/Risk/Skip"); tv.setTextSize(14); tv.setPadding(18,12,18,12); tv.setBackgroundColor(0xDD111111); tv.setTextColor(0xFFFFFFFF); view=tv;
        int type = Build.VERSION.SDK_INT>=26 ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_PHONE;
        WindowManager.LayoutParams lp=new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT,type,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        lp.gravity=Gravity.TOP|Gravity.START; lp.x=20; lp.y=120; wm.addView(view,lp);
    }
    public void onDestroy(){ if(view!=null) wm.removeView(view); }
    public IBinder onBind(Intent i){ return null; }
}
