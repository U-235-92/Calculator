package aq.calculator;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.LinkedList;

public class CalculatorLogic {

    private Deque<BigDecimal> operandQueue;
    private Deque<String> operatorQueue;
    private BigDecimal lastOperand;
    private String lastOperator;

    public CalculatorLogic() {
        operandQueue = new LinkedList<>();
        operatorQueue = new LinkedList<>();
        lastOperand = null;
        lastOperator = null;
    }

    public String getResult() {
        return calculate();
    }

    private String calculate() {
        String result = null;
        if(operandQueue.size() > 1) {
            BigDecimal secondOperand = popOperand();
            BigDecimal firstOperand = popOperand();

        } else if(operandQueue.size() == 1) {

        } else {
            result = DisplayMessages.EMPTY_DISPLAY_MESSAGE.getStringMessage();
        }
        return result;
    }

    private String calculate(BigDecimal firstOperand, BigDecimal secondOperand, String operator) {
        BigDecimal result = null;
        switch (operator) {
            case CalculatorSchema.ADD_COMMAND:
                result = firstOperand.add(secondOperand);
                break;
            case CalculatorSchema.SUB_COMMAND:
                result = firstOperand.subtract(secondOperand);
                break;
            case CalculatorSchema.MUL_COMMAND:
                result = firstOperand.multiply(secondOperand);
                break;
            case CalculatorSchema.DIV_COMMAND:
                if(isDivideByZero(secondOperand)) {
                    return DisplayMessages.DIVIDE_BY_ZERO_MESSAGE.getStringMessage();
                } else {
                    result = firstOperand.divide(secondOperand);
                }
                break;
            case CalculatorSchema.PERCENT_COMMAND:
                result = secondOperand.divide(new BigDecimal(100)).multiply(firstOperand);
                break;
        }
        result = formatResult(result);
        return checkLengthResult(result.toString());
    }

    private boolean isDivideByZero(BigDecimal divider) {
        return divider.toString().matches("0|0\\.0+");
    }

    private BigDecimal formatResult(BigDecimal result) {
        result = (result.scale() > 0) ? result.stripTrailingZeros() : result;
        result = (result.scale() < 0) ? result.setScale(0) : result;
        return result;
    }
    private String checkLengthResult(String result) {
        if(result.length() > CalculatorVisual.MAX_COUNT_NUMBER_DISPLAY) {
            return DisplayMessages.TO_LONG_NUMBER_MESSAGE.getStringMessage();
        } else {
            return result;
        }
    }

    private boolean isNoNumberResult(String result) {
       return result.equals(DisplayMessages.DIVIDE_BY_ZERO_MESSAGE.getStringMessage()) ||
                result.equals(DisplayMessages.TO_LONG_NUMBER_MESSAGE.getStringMessage());
    }

    public void pushOperand(BigDecimal operand) {
        operandQueue.add(operand);
    }

    public void pushOperator(String operator) {
        operatorQueue.push(operator);
    }

    private String popOperator() {
        return operatorQueue.pop();
    }

    private BigDecimal popOperand() {
        return operandQueue.pop();
    }

    private void pushOperand(String operand) {
        operandQueue.push(new BigDecimal(operand));
    }

    private String peekOperator() {
        return operatorQueue.peek();
    }

    private BigDecimal peekOperand() {
        return operandQueue.peek();
    }
}
