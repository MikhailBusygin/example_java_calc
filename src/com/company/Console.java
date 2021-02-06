package com.company;

import java.util.Scanner;

public class Console {

    private String inExpression;

    public void startAPI() {
        System.out.println("Приложение Калькулятор." +
                "\nПрограмма рассчитывает выражение, используя операции сложения," +
                "\nвычитания, умножения, деления, допускатеся так же использовать" +
                "\nотрицательные и дробные числа, выражения в скобках.");
    }

    public void getSomeExpression() {
        System.out.println("Введите выражение");
        Scanner sc = new Scanner(System.in);
        inExpression = sc.nextLine().replaceAll(" ", "");
    }

    public String getInExpression() {
        return inExpression;
    }

    public void printExpression(String ex) {
        System.out.println(ex);
    }
}