package com.starpattern.assistant;

import android.app.*; import android.content.*; import android.media.projection.MediaProjectionManager; import android.net.*; import android.os.*; import android.provider.Settings; import android.view.*; import android.widget.*; import java.util.*;

public class MainActivity extends Activity {
    private final String[] planets = {"Purple x5", "Orange x5", "Light Green x5", "Teal/Green x5", "Pink x10", "Yellow x15", "Purple x25", "Pink/Purple x50"};
    private PatternEngine engine; private PatternMemory memory; private CsvStore store; private LinearLayout log; private int round = 1;
    private static final int CAPTURE_REQ = 501;

    public void onCreate(Bundle b) { super.onCreate(b); engine = new PatternEngine(); memory = new PatternMemory(this); store = new CsvStore(this); buildUi(); }

    private void buildUi() {
        ScrollView sv = new ScrollView(this); LinearLayout root = new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(26,24,26,24); sv.addView(root);
        TextView title = text("Star Pattern Assistant V5 AI\nManual + Direct Reading + Pattern Learning", 21, true); root.addView(title);
        root.addView(text("Target: Screen → Read → CSV/Excel → Learn Pattern → Predict + Confidence + Skip Signal", 15, false));

        Button overlay = btn("Start Live Overlay"); overlay.setOnClickListener(v -> startOverlay()); root.addView(overlay);
        Button direct = btn("Start Direct App Reading / Screen Capture Permission"); direct.setOnClickListener(v -> startDirectReading()); root.addView(direct);
        Button guide = btn("Direct Reading Guide / Crop Setting Info"); guide.setOnClickListener(v -> dialog("Direct Reading", DirectReadingGuide.text())); root.addView(guide);

        root.addView(text("Manual Reading Mode: अगर app गलत पढ़े या direct mode अभी calibrated नहीं है, result यहाँ tap करके save करो.", 15, false));
        for (String p: planets) { Button b = btn("Add Result: " + p); b.setOnClickListener(v -> addResult(((Button)v).getText().toString().replace("Add Result: ", ""))); root.addView(b); }

        Button predict = btn("Predict Next Chance + Confidence"); predict.setOnClickListener(v -> showPrediction()); root.addView(predict);
        Button wrong = btn("Mark Last Prediction Wrong"); wrong.setOnClickListener(v -> { engine.markWrong(); toast("Wrong count added. Low confidence par AI Recheck suggest hoga."); }); root.addView(wrong);
        Button correct = btn("Mark Prediction Correct"); correct.setOnClickListener(v -> { engine.markCorrect(); toast("Accuracy reset/improved note saved."); }); root.addView(correct);
        Button patterns = btn("Pattern Library / Learned Patterns"); patterns.setOnClickListener(v -> dialog("Pattern Library", memory.allPatternsText())); root.addView(patterns);
        Button export = btn("Export CSV / Excel Path"); export.setOnClickListener(v -> dialog("Excel/CSV Export", "File saved here:\n" + store.file().getAbsolutePath() + "\n\nPhone file manager से share/download कर सकते हो. CSV Excel/WPS में खुलेगी.")); root.addView(export);
        Button ai = btn("AI Pattern Help / Copy Prompt"); ai.setOnClickListener(v -> dialog("AI Recheck Prompt", AiHelper.prompt(engine.history()))); root.addView(ai);

        log = new LinearLayout(this); log.setOrientation(LinearLayout.VERTICAL); root.addView(log); setContentView(sv);
    }

    private Button btn(String s) { Button b = new Button(this); b.setText(s); b.setAllCaps(false); return b; }
    private TextView text(String s, int size, boolean bold) { TextView t = new TextView(this); t.setText(s); t.setTextSize(size); t.setPadding(0,8,0,8); if (bold) t.setTypeface(android.graphics.Typeface.DEFAULT_BOLD); return t; }

    private void startOverlay() {
        if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(this)) {
            startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())));
        } else { startService(new Intent(this, OverlayService.class)); toast("Overlay started"); }
    }

    private void startDirectReading() {
        MediaProjectionManager mpm = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
        if (mpm != null) startActivityForResult(mpm.createScreenCaptureIntent(), CAPTURE_REQ);
        else toast("Screen capture service available nahi hai");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_REQ) {
            if (resultCode == RESULT_OK) dialog("Direct Reading Ready", "Screen capture permission मिल गई.\n\nNext upgrade में crop calibration + icon templates जोड़कर automatic planet reading active होगी. अभी Manual + CSV + Prediction + AI Help पूरी तरह use करो.");
            else toast("Screen capture permission cancel hui");
        }
    }

    private void addResult(String result) {
        try {
            ResultRecord rec = new ResultRecord(round++, result, FamilyMapper.family(result), System.currentTimeMillis());
            engine.add(rec); store.append(rec);
            String note = engine.autoPatternNote(); if (note != null) memory.saveAutoPattern("AUTO_" + rec.round, note);
            TextView row = text("Saved R" + rec.round + ": " + rec.result + " | " + rec.family, 14, false); log.addView(row, 0);
        } catch(Exception e) { toast("Save error: " + e.getMessage()); }
    }

    private void showPrediction() {
        Prediction p = engine.predict();
        dialog("Next Chance", "Planet: " + p.planet + "\nFamily: " + p.family + "\nConfidence: " + p.confidence + "%\nSignal: " + p.signal + "\n\nReason:\n" + p.reason);
    }

    private void dialog(String title, String msg) { new AlertDialog.Builder(this).setTitle(title).setMessage(msg).setPositiveButton("OK", null).show(); }
    private void toast(String s) { Toast.makeText(this, s, Toast.LENGTH_LONG).show(); }
}
