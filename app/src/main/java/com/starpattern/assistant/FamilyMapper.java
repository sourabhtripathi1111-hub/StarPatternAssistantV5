package com.starpattern.assistant;

public class FamilyMapper {
    public static String family(String r) {
        if (r == null) return "Unknown";
        if (r.contains("x25") || r.contains("x15") || r.contains("Orange")) return "Yellow/Big";
        if (r.contains("Purple x5")) return "Purple/Small";
        if (r.contains("Green") || r.contains("Teal")) return "Green/Small";
        if (r.contains("x10") || r.contains("x50") || r.contains("Pink")) return "Pink/Big";
        return "Unknown";
    }
}
