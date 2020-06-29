package com.abclinic.constant;

public enum Gender {
    MALE,
    FEMALE,
    OTHER;

    public static Gender toGender(int i) {
        switch (i) {
            case 0:
                return Gender.MALE;
            case 1:
                return Gender.FEMALE;
            case 2:
                return Gender.OTHER;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case MALE: return "Nam";
            case FEMALE: return "Nữ";
            case OTHER: return "Khác";
            default: return "";
        }
    }

}
