package com.starpattern.assistant;
import android.app.*; import android.content.*; import android.os.*; import android.view.*; import android.widget.*;
public class OverlayService extends Service { public IBinder onBind(Intent i){return null;} public int onStartCommand(Intent i,int f,int id){ Toast.makeText(this,"Overlay base ready",Toast.LENGTH_SHORT).show(); return START_NOT_STICKY; } }
