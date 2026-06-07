package com.starpattern.assistant;
import java.util.*;
public class PatternEngine {
  private final ArrayList<String> history=new ArrayList<>();
  public void add(String r){ if(r!=null&&!r.trim().isEmpty()) history.add(r.trim()); }
  public List<String> all(){ return history; }
  public Prediction predict(){
    if(history.size()<3) return new Prediction("Data कम",25,"SKIP");
    Map<String,Integer> count=new HashMap<>(); for(String h:history) count.put(h,count.getOrDefault(h,0)+1);
    String best="Purple x5"; int max=0; for(String k:count.keySet()) if(count.get(k)>max){best=k;max=count.get(k);} 
    int conf=Math.min(85,30+(max*100/history.size())/2);
    String sig=conf>=65?"SAFE":conf>=45?"RISK":"SKIP";
    if(history.size()>=3){String a=history.get(history.size()-1),b=history.get(history.size()-2),c=history.get(history.size()-3); if(a.equals(b)&&b.equals(c)){best="Pattern break possible"; conf=45; sig="RISK";}}
    return new Prediction(best,conf,sig);
  }
  public String detectPattern(){
    int n=history.size(); if(n>=6){String a=history.get(n-6),b=history.get(n-5),c=history.get(n-4),d=history.get(n-3),e=history.get(n-2),f=history.get(n-1); if(a.equals(b)&&d.equals(e)&&c.equals(f)) return "AAB-BBA mirror जैसा pattern"; if(a.equals(b)&&b.equals(c)&&d.equals(e)) return "3-2-1 chain possible";}
    return "कोई strong नया pattern अभी नहीं";
  }
}
