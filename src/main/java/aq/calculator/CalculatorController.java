package aq.calculator;

import java.math.BigDecimal;

public class CalculatorController {

    private CalculatorVisual calculatorVisual;
    private CalculatorLogic calculatorLogic;

    public CalculatorController() {
        calculatorLogic = new CalculatorLogic();
        calculatorVisual = new CalculatorVisual(this);
    }

    public void runCalculator() {
        calculatorVisual.getView();
    }

    public void pushOperator(String operator) {

    }

    public void pushOperand(String operand) {

    }

    public void reset() {

    }

    public String getResult() {
        return null;
    }
}
