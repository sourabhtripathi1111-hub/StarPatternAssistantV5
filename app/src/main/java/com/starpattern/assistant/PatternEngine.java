package com.starpattern.assistant;

import java.util.*;

public class PatternEngine {
    private final ArrayList<ResultRecord> history = new ArrayList<>();
    private int wrongStreak = 0;

    public void add(ResultRecord r) { history.add(r); }
    public List<ResultRecord> history() { return history; }
    public void markWrong() { wrongStreak++; }
    public void markCorrect() { wrongStreak = 0; }

    public Prediction predict() {
        if (history.size() < 6) return new Prediction("Add more rounds", "Unknown", 30, "WAIT/SKIP", "कम से कम 6 result add करो");
        Prediction exact = ngramPrediction(false);
        Prediction fam = ngramPrediction(true);
        String signal = exact.confidence >= 68 && wrongStreak < 2 ? "SAFE/MEDIUM" : exact.confidence >= 55 ? "RISK" : "SKIP";
        if (wrongStreak >= 2) signal = "AI RECHECK / SKIP";
        return new Prediction(exact.planet, fam.family, exact.confidence, signal, exact.reason + "\nFamily hint: " + fam.family + "\nWrong streak: " + wrongStreak);
    }

    private Prediction ngramPrediction(boolean familyMode) {
        int n = Math.min(4, history.size() - 1);
        while (n >= 1) {
            List<String> tail = last(n, familyMode);
            HashMap<String, Integer> next = new HashMap<>();
            for (int i = 0; i + n < history.size(); i++) {
                boolean match = true;
                for (int j = 0; j < n; j++) {
                    String a = val(history.get(i + j), familyMode);
                    if (!a.equals(tail.get(j))) { match = false; break; }
                }
                if (match) {
                    String after = val(history.get(i + n), familyMode);
                    next.put(after, next.getOrDefault(after, 0) + 1);
                }
            }
            if (!next.isEmpty()) {
                String best = null; int max = 0; int total = 0;
                for (String k: next.keySet()) { int c = next.get(k); total += c; if (c > max) { max = c; best = k; } }
                int conf = Math.min(86, 38 + (max * 100 / Math.max(1, total)) / 2 + n * 6);
                String reason = n + "-step pattern match मिला. History में next सबसे ज्यादा: " + best + " (" + max + "/" + total + ")";
                return familyMode ? new Prediction("", best, conf, "", reason) : new Prediction(best, FamilyMapper.family(best), conf, "", reason);
            }
            n--;
        }
        String last = history.get(history.size()-1).result;
        return new Prediction(last, FamilyMapper.family(last), 42, "SKIP", "Strong pattern match नहीं मिला");
    }

    private List<String> last(int n, boolean fam) {
        ArrayList<String> out = new ArrayList<>();
        for (int i = history.size() - n; i < history.size(); i++) out.add(val(history.get(i), fam));
        return out;
    }

    private String val(ResultRecord r, boolean fam) { return fam ? r.family : r.result; }

    public String autoPatternNote() {
        if (history.size() < 8) return null;
        int s = history.size();
        String a = history.get(s-1).family, b = history.get(s-2).family, c = history.get(s-3).family;
        String d = history.get(s-4).family, e = history.get(s-5).family, f = history.get(s-6).family;
        if (b.equals(c) && e.equals(f) && a.equals(d)) return "Possible mirror/balance family pattern near round " + history.get(s-1).round;
        return null;
    }
}
