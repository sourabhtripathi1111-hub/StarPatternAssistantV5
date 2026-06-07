package com.starpattern.assistant;

import java.util.*;

public class AiHelper {
    public static String prompt(List<ResultRecord> history) {
        StringBuilder sb = new StringBuilder();
        sb.append("StarMaker/Star Trigger pattern recheck karo. New pattern, family balance, skip/safe signal aur next chance batao.\n");
        sb.append("Family rules: Orange x5 + Yellow x15 + Purple x25 = Yellow/Big; Purple x5 = Purple/Small; Green/Teal = Green/Small; Pink x10/x50 = Pink/Big.\n");
        int start = Math.max(0, history.size() - 60);
        for (int i = start; i < history.size(); i++) {
            ResultRecord r = history.get(i);
            sb.append(r.round).append(": ").append(r.result).append(" [").append(r.family).append("]\n");
        }
        return sb.toString();
    }
}
