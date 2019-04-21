package com.abclinic.enums;

public enum Gender {
    MALE,
    FEMALE,
    OTHER;

    public static Gender toGender(int i) {
        switch (i) {
            case 1:
                return Gender.MALE;
            case 2:
                return Gender.FEMALE;
            case 3:
                return Gender.OTHER;
            default:
                return null;
        }
    }

    public static String toString(Gender gender) {
        switch (gender) {
            case MALE: return "Nam";
            case FEMALE: return "Nữ";
            case OTHER: return "Khác";
            default: return "";
        }
    }

}
