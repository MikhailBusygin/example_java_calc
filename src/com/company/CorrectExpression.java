package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class CorrectExpression {

    private final String[] operations = {"\\.", "-", "\\+", "/", "\\*"}; //перечень операций с защитой в виде "\\"
    private List<String> cases = new ArrayList<>(); //варианты последовательностей символов, которые должны отсутствовать
    private String regexExpression; //допустимые символы ввода
    private boolean correctFormula; //проверка выражения на корректность в main()
    private int parentheses;

    public void setOptions() {
        String allOperations = "";
        for (String operation : operations) {
            allOperations = allOperations.concat("|" + operation);
        }
        regexExpression = "\\d|\\)|\\(" + allOperations;
        for (int i = 0; i < operations.length; i++) {
            for (int j = 0; j < operations.length; j++) {
                cases.add(operations[i].replace("\\", "")
                        + operations[j].replace("\\", ""));
            }
        }
    }

    public void checkedWrongSymbols(String ex) {
        List<String> symbols = Arrays.asList(ex.split(""));
        if (symbols.stream().allMatch(n -> n.matches(regexExpression))) {
            correctFormula = true;
        } else {
            System.out.println("Содержит посторонние символы или не содержит ничего, введите заного");
            correctFormula = false;
        }
    }

    public void correctNegativeNumbers(String ex) {
        if (correctFormula) {
            String[] exSymbols = ex.split("");
            for (int i = 0; i < exSymbols.length - 1; i ++) {
                String doubleExSymbols = exSymbols[i] + exSymbols[i + 1];
                if (doubleExSymbols.matches("\\(-")) {
                    if (ex.substring(i + 1).length() > 1) {
                        String endOfExSymbols = ex.substring(i + 2);
                        if (
                                endOfExSymbols.startsWith("+")||
                                        endOfExSymbols.startsWith("-")||
                                        endOfExSymbols.startsWith(".")||
                                        endOfExSymbols.startsWith("*")||
                                        endOfExSymbols.startsWith("/")||
                                        endOfExSymbols.startsWith(")")) {
                            System.out.println("Минус в неположенном месте");
                            i = exSymbols.length - 1;
                            correctFormula = false;
                            break;
                        }
                        if (endOfExSymbols.startsWith("(")) {
                            for (int j = 0; j < endOfExSymbols.length(); j++) {
                                int opensParentheses = 2;
                                int closesParentheses = 0;
                                if (endOfExSymbols.charAt(j) == '(') {
                                    opensParentheses++;
                                }
                                if (endOfExSymbols.charAt(j) == ')') {
                                    closesParentheses++;
                                }
                                if (opensParentheses == closesParentheses) {
                                    if (!endOfExSymbols.substring(j - 2, j).equals("))")) {
                                        System.out.println("Минус в неположенном месте");
                                        correctFormula = false;
                                        i = exSymbols.length - 1;
                                        break;
                                    } else {
                                        j = endOfExSymbols.length();
                                    }
                                }
                            }
                        }
                    } else {
                        System.out.println("Минус в неположенном месте");
                        correctFormula = false;
                        break;
                    }
                }
            }
        }
    }

    public void correctStartEnd(String ex) {
        if (correctFormula) {
            if (ex.startsWith(".")||ex.startsWith("/")||ex.startsWith("+")||ex.startsWith("*")||ex.startsWith("-")) {
                correctFormula = false;
                System.out.println("Некорректное местоположение знаков операций или дробной части числа");
            }
            if (ex.endsWith(".")||ex.endsWith("/")||ex.endsWith("+")||ex.endsWith("*")||ex.endsWith("-")) {
                System.out.println("Некорректное местоположение знаков операций или дробной части числа");
                correctFormula = false;
            }
        }
    }

    public void correctNumbers(String ex) {
        if (correctFormula) {
            String[] wrongNumbers = ex.split("\\d+");
            if (wrongNumbers.length != 1) {
                for (int j = 0; j < wrongNumbers.length; j++) {
                    if (j < wrongNumbers.length - 1) {
                        if (wrongNumbers[j].endsWith(".")&&wrongNumbers[j + 1].startsWith(".")) {
                            System.out.println("Некорректные числа в выражении, или их местоположение");
                            correctFormula = false;
                            break;
                        }
                    }
                    String[] wrongPartsOfNumber = wrongNumbers[j].split("");
                    if (wrongPartsOfNumber.length != 1) {
                        List<String> addOperations = new ArrayList<>();
                        addOperations.add(").");
                        addOperations.add(".(");
                        addOperations.add("..");
                        addOperations.add(")(");
                        addOperations.add("()");
                        for (int i = 0; i < wrongPartsOfNumber.length - 1; i++) {
                            String symbolsDoubleNumber = (wrongPartsOfNumber[i] + wrongPartsOfNumber[i + 1]);
                            for (String operationsCases : addOperations) {
                                if (!(symbolsDoubleNumber).equals(operationsCases)) {
                                    correctFormula = true;
                                } else {
                                    System.out.println("Некорректные числа в выражении, или их местоположение");
                                    correctFormula = false;
                                    i = wrongPartsOfNumber.length - 1;
                                    j = wrongNumbers.length - 1;
                                    break;
                                }
                            }
                        }
                    }
                }
            } else {
                System.out.println("Некорректные числа в выражении, или их местоположение3");
                correctFormula = false;
            }
        }
    }

    public void correctParentheses(String ex) {
        if (correctFormula) {
            List<String> addParentheses = new ArrayList<>();
            for (String operation : operations) {
                addParentheses.add("(" + operation.replace("\\", ""));
                addParentheses.add(operation.replace("\\", "") + ")");
                addParentheses.remove("(-");
            }
            String[] symbolsIn = ex.split("");
            for (int i = 0; i < symbolsIn.length - 1; i++) {
                String symbolsDouble = (symbolsIn[i] + symbolsIn[i + 1]);
                for (String parenthesesCases : addParentheses) {
                    if (!(symbolsDouble).equals(parenthesesCases)) {
                        correctFormula = true;
                    } else {
                        System.out.println("Недопустимое расположение скобок");
                        correctFormula = false;
                        i = symbolsIn.length - 1;
                        break;
                    }
                }
            }
        }
    }

    public void countParentheses(String ex) {
        if (correctFormula) {
            int openParentheses = 0;
            int closeParentheses = 0;
            String[] symbols = ex.split("");
            for (String symbol : symbols) {
                if (symbol.equals("(")) {
                    openParentheses++;
                }
                if (symbol.equals(")")) {
                    closeParentheses++;
                }
                if (openParentheses < closeParentheses) {
                    System.out.println("Нарушен порядок скобок");
                    correctFormula = false;
                    break;
                }
            }
            if (openParentheses == closeParentheses) {
                parentheses = openParentheses;
            } else {
                System.out.println("Не все скобки закрыты");
                correctFormula = false;
            }
        }
    }

    public void correctOperations(String ex) {
        if (correctFormula) {
            String[] symbolsIn = ex.split("");
            for (int i = 0; i < symbolsIn.length - 1; i++) {
                String symbolsDouble = (symbolsIn[i] + symbolsIn[i + 1]);
                for (String symbolsEx : cases) {
                    if (!(symbolsDouble).equals(symbolsEx)) {
                        correctFormula = true;
                    } else {
                        System.out.println("Между знаками операций нет чисел");
                        correctFormula = false;
                        i = symbolsIn.length - 1;
                        break;
                    }
                }
            }
        }
    }

    public boolean haveCorrectExpression() {
        return !correctFormula;
    }

    public int getParentheses() {
        return parentheses;
    }
}
