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

}
