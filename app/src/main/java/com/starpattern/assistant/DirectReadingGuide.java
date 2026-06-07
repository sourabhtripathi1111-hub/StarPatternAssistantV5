package com.starpattern.assistant;

public class DirectReadingGuide {
    public static String text() {
        return "Direct App Reading Mode:\n\n" +
                "1) Start Direct Reading दबाओ.\n" +
                "2) Android screen capture permission allow करो.\n" +
                "3) Settings/Crop Area में result strip crop set होगा.\n" +
                "4) App OCR + icon matching से planet/result पढ़ेगा.\n\n" +
                "Note: पहली build में direct reading base/menu है. Real accuracy के लिए game के actual icon templates और crop calibration add करनी होगी. Manual correction button हमेशा रहेगा.";
    }
}
