package com.starpattern.assistant;
import android.app.*; import android.os.*; import android.content.*; import android.provider.Settings; import android.net.Uri; import android.view.*; import android.widget.*; import java.util.*;
public class MainActivity extends Activity{
 PatternEngine engine=new PatternEngine(); int round=1; TextView output;
 String[] planets={"Purple x5","Orange x5","Light Green x5","Teal/Green x5","Pink x10","Yellow x15","Purple x25","Pink/Purple x50"};
 public void onCreate(Bundle b){super.onCreate(b); buildUi();}
 void buildUi(){ ScrollView sv=new ScrollView(this); LinearLayout root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(24,24,24,24); sv.addView(root);
  TextView title=new TextView(this); title.setText("Star Pattern Assistant V5\nManual + Direct Reading Base"); title.setTextSize(22); root.addView(title);
  output=new TextView(this); output.setText("Result add करो. CSV auto save होगा. Direct screen reading/OCR अगला module है."); output.setTextSize(16); root.addView(output);
  for(String p:planets){ Button btn=new Button(this); btn.setText("Add: "+p); btn.setOnClickListener(v->addResult(p)); root.addView(btn); }
  Button pred=new Button(this); pred.setText("Predict / Pattern Check"); pred.setOnClickListener(v->showPrediction()); root.addView(pred);
  Button export=new Button(this); export.setText("CSV/Excel Path दिखाओ"); export.setOnClickListener(v->output.setText("CSV saved at:\n"+CsvStore.path(this)+"\nइसे File Manager से share/download कर सकते हो.")); root.addView(export);
  Button ai=new Button(this); ai.setText("AI Recheck Prompt"); ai.setOnClickListener(v->output.setText(AIHelper.prompt()+"\nCSV export करके ChatGPT में भेजो.")); root.addView(ai);
  Button overlay=new Button(this); overlay.setText("Overlay Permission / Direct Reading Base"); overlay.setOnClickListener(v->{ if(Build.VERSION.SDK_INT>=23 && !Settings.canDrawOverlays(this)){startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:"+getPackageName())));} else startService(new Intent(this,OverlayService.class));}); root.addView(overlay);
  setContentView(sv); }
 void addResult(String p){ engine.add(p); Prediction pr=engine.predict(); CsvStore.append(this,round,p,pr.planet,pr.confidence,pr.signal); output.setText("Round "+round+" saved: "+p+"\nNext: "+pr.planet+"\nConfidence: "+pr.confidence+"%\nSignal: "+pr.signal+"\nPattern: "+engine.detectPattern()); round++; }
 void showPrediction(){ Prediction pr=engine.predict(); output.setText("Prediction: "+pr.planet+"\nConfidence: "+pr.confidence+"%\nSignal: "+pr.signal+"\nPattern: "+engine.detectPattern()); }
}
