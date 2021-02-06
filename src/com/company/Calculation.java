package com.company;

import java.util.ArrayList;
import java.util.List;

public class Calculation {

    List<String> listExpression;
    List<String> listCalculate = new ArrayList<>();
    int indexCalculate = 0;
    int firstExpression = 0;

    public void setListExpression(List<String> ex) {
        listExpression = ex;
    }

    public String shuffleListExpression() throws divNull {
        do {
            firstExpression = listExpression.size();
            while (indexCalculate < listExpression.size()) {
                try {
                    listCalculate.add(countOperation(listExpression.get(indexCalculate + 2)));
                    indexCalculate++;
                } catch (IndexOutOfBoundsException indexOut) {
                    listCalculate.add(listExpression.get(indexCalculate));
                    indexCalculate++;
                }
            }
            indexCalculate = 0;
            listExpression.clear();
            listExpression.addAll(listCalculate);
            listCalculate.clear();
        } while (firstExpression > 3);
        return listExpression.get(0);
    }

    public String countOperation(String operation) throws divNull {
        switch (operation) {
            case "+":
                try {
                    float sum =
                            Float.parseFloat(listExpression.get(indexCalculate)) +
                                    Float.parseFloat(listExpression.get(indexCalculate + 1));
                    indexCalculate = indexCalculate + 2;
                    return String.valueOf(sum);
                } catch (NumberFormatException nfe) {
                    return listExpression.get(indexCalculate);
                }
            case "-":
                try {
                    float sub =
                            Float.parseFloat(listExpression.get(indexCalculate)) -
                                    Float.parseFloat(listExpression.get(indexCalculate + 1));
                    indexCalculate = indexCalculate + 2;
                    return String.valueOf(sub);
                } catch (NumberFormatException nfe) {
                    return listExpression.get(indexCalculate);
                }
            case "*":
                try {
                    float mul =
                            Float.parseFloat(listExpression.get(indexCalculate)) *
                                    Float.parseFloat(listExpression.get(indexCalculate + 1));
                    indexCalculate = indexCalculate + 2;
                    return String.valueOf(mul);
                } catch (NumberFormatException nfe) {
                    return listExpression.get(indexCalculate);
                }
            case "/":
                if (listExpression.get(indexCalculate + 1).equals("0.0")) {
                    throw new divNull("Делить на ноль нельзя!");
                }
                try {
                    float div =
                            Float.parseFloat(listExpression.get(indexCalculate)) /
                                    Float.parseFloat(listExpression.get(indexCalculate + 1));
                    indexCalculate = indexCalculate + 2;
                    return String.valueOf(div);
                } catch (NumberFormatException nfExpr) {
                    return listExpression.get(indexCalculate);
                }
            default: return listExpression.get(indexCalculate);
        }
    }
}

class divNull extends Exception {
    public divNull (String message) {
        super(message);
    }
}