package com.company;

public class Main {

    public static void main(String[] args) throws divNull {
        String mainExpression;

        Console console = new Console();
        CorrectExpression correctExpression = new CorrectExpression();
        CommandString commandString = new CommandString();
        Calculation calc = new Calculation();

        console.startAPI();

        while (correctExpression.haveCorrectExpression()) {
            console.getSomeExpression();
            mainExpression = console.getInExpression();

            correctExpression.setOptions();
            correctExpression.checkedWrongSymbols(mainExpression);
            correctExpression.correctOperations(mainExpression);
            correctExpression.countParentheses(mainExpression);
            correctExpression.correctParentheses(mainExpression);
            correctExpression.correctNumbers(mainExpression);
            correctExpression.correctStartEnd(mainExpression);
            correctExpression.correctNegativeNumbers(mainExpression);
            commandString.setRebuildExpression(mainExpression);
        }
        commandString.setBufferSize(correctExpression.getParentheses());
        commandString.getNumbers();

        calc.setListExpression(commandString.getRebuildExpression());
        console.printExpression(calc.shuffleListExpression());
    }
}
