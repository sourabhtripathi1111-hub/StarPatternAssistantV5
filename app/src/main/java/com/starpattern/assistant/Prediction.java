package com.starpattern.assistant;

public class Prediction {
    public final String planet;
    public final String family;
    public final int confidence;
    public final String signal;
    public final String reason;
    public Prediction(String planet, String family, int confidence, String signal, String reason) {
        this.planet = planet; this.family = family; this.confidence = confidence; this.signal = signal; this.reason = reason;
    }
}
