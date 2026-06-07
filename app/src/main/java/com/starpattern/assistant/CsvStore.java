package com.starpattern.assistant;

import android.content.Context;
import java.io.*; import java.text.SimpleDateFormat; import java.util.*;

public class CsvStore {
    private final Context ctx;
    public CsvStore(Context c){ ctx = c; }
    public File file(){ return new File(ctx.getExternalFilesDir(null), "star_pattern_v5_data.csv"); }
    public void append(ResultRecord rec) throws IOException {
        File f = file(); boolean fresh = !f.exists(); FileWriter w = new FileWriter(f, true);
        if (fresh) w.write("Round,Result,Family,Time\n");
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date(rec.time));
        w.write(rec.round + "," + safe(rec.result) + "," + safe(rec.family) + "," + time + "\n");
        w.close();
    }
    private String safe(String s){ return s == null ? "" : s.replace(",", " "); }
}
