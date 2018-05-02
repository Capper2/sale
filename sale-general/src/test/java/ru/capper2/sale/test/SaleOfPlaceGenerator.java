package ru.capper2.sale.test;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

public class SaleOfPlaceGenerator extends Generator<String> {

    private static final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_CHARS_RU = "абвгдежзийклмнопрстуфхцчшщъыьэюя";
    private static final String UPPERCASE_CHARS_RU = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL_CHARS = ".-\\:_@[]^/|}{";
    private static final String ALL_MY_CHARS =
            LOWERCASE_CHARS + UPPERCASE_CHARS +
                    LOWERCASE_CHARS_RU + UPPERCASE_CHARS_RU +
                    NUMBERS + SPECIAL_CHARS;
    public static final int CAPACITY = 40;

    public SaleOfPlaceGenerator() {
        super(String.class);
    }

    @Override
    public String generate(SourceOfRandomness random, GenerationStatus status) {
        StringBuilder sb = new StringBuilder(CAPACITY);
        for (int i = 0; i < CAPACITY; i++) {
            int randomIndex = random.nextInt(ALL_MY_CHARS.length());
            sb.append(ALL_MY_CHARS.charAt(randomIndex));
        }
        return sb.toString();
    }
}