package com.elearning.studyvocabulary.common;

import java.util.Random;

public class RandomString {
	private static final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private static final String alphaUpperCase = alpha.toUpperCase(); // A-Z
    private static final String digits = "0123456789";
    private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
    private static Random generator = new Random();
    
    public String randomAlphaNumeric(int numberOfCharactor) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfCharactor; i++) {
            int number = randomNumber(0, ALPHA_NUMERIC.length() - 1);
            char ch = ALPHA_NUMERIC.charAt(number);
            sb.append(ch);
        }
        return sb.toString();
    }
    public static int randomNumber(int min, int max) {
        return generator.nextInt((max - min) + 1) + min;
    }
}
