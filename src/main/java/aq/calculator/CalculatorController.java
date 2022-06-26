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
        calculatorLogic.pushOperator(operator);
    }

    public void pushOperand(String operand) {
        calculatorLogic.pushOperand(operand);
    }

    public void reset() {
        calculatorLogic.reset();
    }

    public String getResult() {
        return calculatorLogic.getResult();
    }
}
