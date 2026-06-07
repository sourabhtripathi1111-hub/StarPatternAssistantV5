package com.starpattern.assistant;
import android.content.*; import java.io.*; import java.text.*; import java.util.*;
public class CsvStore {
  public static void append(Context ctx,int round,String planet,String prediction,int confidence,String signal){
    try{ File f=new File(ctx.getExternalFilesDir(null),"star_pattern_data.csv"); boolean fresh=!f.exists(); FileWriter w=new FileWriter(f,true); if(fresh) w.write("time,round,planet,prediction,confidence,signal\n"); String t=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US).format(new Date()); w.write(t+","+round+","+planet+","+prediction+","+confidence+","+signal+"\n"); w.close(); }catch(Exception ignored){}
  }
  public static String path(Context ctx){ File f=new File(ctx.getExternalFilesDir(null),"star_pattern_data.csv"); return f.getAbsolutePath(); }
}
