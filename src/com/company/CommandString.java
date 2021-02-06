package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandString {

    private String buffer = "";
    private int parentheses = 0;
    private int[] bufferSizeStack;
    private String someExpression;
    private List<String> dataIn = new ArrayList();
    private List<String> dataOut = new ArrayList();
    private List<String> stack = new ArrayList();

    public void setRebuildExpression(String ex) {
        someExpression = ex;
    }

    public void setBufferSize(int count) {
        bufferSizeStack = new int[count + 1];
    }

    public void getNumbers() {
        dataIn = Arrays.asList(someExpression.split(""));
        for (int i = 0; i < dataIn.size(); i++) {
            switch (dataIn.get(i)) {
                case "(":
                    parentheses++;
                    break;
                case ")":
                    if (!buffer.equals("")) {
                        dataOut.add(buffer);
                        buffer = "";
                    }
                    if (bufferSizeStack[parentheses] != 0) {
                        for (int j = 1; j <= bufferSizeStack[parentheses]; j++) {
                            dataOut.add(stack.remove(stack.size() - j));
                            bufferSizeStack[parentheses]--;
                        }
                    }
                    parentheses--;
                    break;
                case "+":
                    if (!buffer.equals("")) {
                        dataOut.add(buffer);
                        buffer = "";
                    }
                    if (bufferSizeStack[parentheses] != 0) {
                        dataOut.add(stack.remove(stack.size() - 1));
                        stack.add(dataIn.get(i));
                    } else {
                        stack.add(dataIn.get(i));
                        bufferSizeStack[parentheses]++;
                    }
                    break;
                case "-":
                    if (!buffer.equals("")) {
                        dataOut.add(buffer);
                        buffer = "";
                    }
                    if (bufferSizeStack[parentheses] == 0) {
                        if (dataIn.get(i - 1).equals("(")) {
                            dataOut.add("0");
                        }
                        stack.add(dataIn.get(i));
                        bufferSizeStack[parentheses]++;
                    } else {
                        dataOut.add(stack.remove(stack.size() - 1));
                        stack.add(dataIn.get(i));
                    }
                    break;
                case "/":
                case "*":
                    if (!buffer.equals("")) {
                        dataOut.add(buffer);
                        buffer = "";
                    }
                    if (bufferSizeStack[parentheses] != 0) {
                        if (!stack.get(stack.size() - 1).equals("-") && !stack.get(stack.size() - 1).equals("+")) {
                            for (int k = 1; k <= bufferSizeStack[parentheses]; k++) {
                                dataOut.add(stack.remove(stack.size() - k));
                                bufferSizeStack[parentheses]--;
                            }
                        }
                    }
                    stack.add(dataIn.get(i));
                    bufferSizeStack[parentheses]++;
                    break;
                default:
                    buffer = buffer.concat(dataIn.get(i));
                    break;
            }
        }
        if (!buffer.equals("")) {
            dataOut.add(buffer);
            buffer = "";
        }
        for (int p = 1; p <= stack.size(); p++) {
            dataOut.add(stack.get(stack.size() - p));
        }
    }

    public List<String> getRebuildExpression() {
        return dataOut;
    }
}
