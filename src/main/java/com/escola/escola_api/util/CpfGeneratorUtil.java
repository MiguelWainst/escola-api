package com.escola.escola_api.util;

import java.util.Arrays;
import java.util.Random;

public class CpfGeneratorUtil {

    public static String gerar() {
        int[] nums = new int[9];
        Random random = new Random();
        for (int i = 0; i < 9; i++) nums[i] = random.nextInt(10);

        int d1 = calcularDigito(nums, new int[]{10,9,8,7,6,5,4,3,2});
        int[] comD1 = Arrays.copyOf(nums, 10);
        comD1[9] = d1;
        int d2 = calcularDigito(comD1, new int[]{11,10,9,8,7,6,5,4,3,2});

        StringBuilder sb = new StringBuilder();
        for (int n : nums) sb.append(n);
        return sb.append(d1).append(d2).toString();
    }

    private static int calcularDigito(int[] digitos, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < pesos.length; i++) soma += digitos[i] * pesos[i];
        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }
}