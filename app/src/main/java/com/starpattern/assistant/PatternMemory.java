package com.starpattern.assistant;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.*;

public class PatternMemory {
    private final SharedPreferences sp;
    public PatternMemory(Context ctx) { sp = ctx.getSharedPreferences("patterns", Context.MODE_PRIVATE); seedDefaults(); }

    private void seedDefaults() {
        if (sp.getBoolean("seeded", false)) return;
        SharedPreferences.Editor e = sp.edit();
        e.putString("P1", "3-2-1 repeat: 3 same, then 2 same, then 1 substitute");
        e.putString("P2", "2-2-2-1 chain: pairs complete then one break/substitute");
        e.putString("P3", "AAB-BBA mirror: example Green Green Purple then Purple Purple Green");
        e.putString("P4", "Yellow family vs Purple: Orange x5 + Yellow x15 + Purple x25 as Big/Yellow family compared with Purple x5");
        e.putString("P5", "2 x25 then 2 Purple balance block");
        e.putString("seeded", "true");
        e.putBoolean("seeded", true);
        e.apply();
    }

    public void saveAutoPattern(String key, String text) { sp.edit().putString(key, text).apply(); }

    public String allPatternsText() {
        Map<String, ?> all = sp.getAll();
        ArrayList<String> lines = new ArrayList<>();
        for (String k: all.keySet()) if (!k.equals("seeded")) lines.add("• " + all.get(k));
        Collections.sort(lines);
        StringBuilder sb = new StringBuilder();
        for (String l: lines) sb.append(l).append("\n");
        return sb.toString();
    }
}
