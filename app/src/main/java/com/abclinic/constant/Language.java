package com.abclinic.constant;

public enum Language {
    VIE,
    ENG;

    public static Language toLanguage(int i) {
        switch (i) {
            case 1:
                return Language.VIE;
            case 2:
                return Language.ENG;
            default:
                return null;
        }
    }
}
