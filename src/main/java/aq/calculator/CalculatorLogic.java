package aq.calculator;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.LinkedList;

public class CalculatorLogic {

    private Deque<BigDecimal> operandQueue;
    private Deque<String> operatorQueue;
    private BigDecimal firstOperand;
    private BigDecimal secondOperand;
    private String lastOperator;
    private String previousOperator;

    public CalculatorLogic() {
        operandQueue = new LinkedList<>();
        operatorQueue = new LinkedList<>();
        firstOperand = null;
        secondOperand = null;
        lastOperator = null;
        previousOperator = null;
    }

    public void reset() {
        operandQueue.removeAll(operandQueue);
        operatorQueue.removeAll(operatorQueue);
        firstOperand = null;
        secondOperand = null;
        lastOperator = null;
        previousOperator = null;
    }

    public String getResult() {
        return calculate();
    }

    private String calculate() {
        String result = null;
        if(operandQueue.size() > 1) {
            if(operandQueue.size() > 2) {
                while(operandQueue.size() > 2) {
                    popOperand();
                }
            }
            secondOperand = popOperand();
            firstOperand = popOperand();
            lastOperator = popOperator();
            if(lastOperator.equals(CalculatorSchema.EQU_COMMAND)) {
                previousOperator = popOperator();
                result = calculate(firstOperand, secondOperand, previousOperator);
                if(isNoNumberResult(result)) {
                    return result;
                } else {
                    pushOperand(result);
                    pushOperand(secondOperand.toString());
                    pushOperator(previousOperator);
                    previousOperator = lastOperator;
                }
            } else if(lastOperator.equals(CalculatorSchema.PERCENT_COMMAND)) {
                String resultPercent = calculate(firstOperand, secondOperand, lastOperator);
                if(isNoNumberResult(resultPercent)) {
                    return resultPercent;
                }
                previousOperator = popOperator();
                result = calculate(firstOperand, new BigDecimal(resultPercent), previousOperator);
                if(isNoNumberResult(result)) {
                    return result;
                } else {
                    pushOperand(result);
                    pushOperand(resultPercent);
                    pushOperator(previousOperator);
                    previousOperator = lastOperator;
                }
            } else {
                if(previousOperator == null) {
                    previousOperator = lastOperator;
                }
                if(previousOperator.equals(CalculatorSchema.EQU_COMMAND) || previousOperator.equals(CalculatorSchema.PERCENT_COMMAND)) {
                    result = firstOperand.toString();
                    pushOperand(result);
                    pushOperator(lastOperator);
                } else {
                    previousOperator = popOperator();
                    result = calculate(firstOperand, secondOperand, previousOperator);
                    if(isNoNumberResult(result)) {
                        return result;
                    } else {
                        pushOperand(result);
                        pushOperator(lastOperator);
                    }
                }
            }
        } else if(operandQueue.size() == 1) {
            lastOperator = peekOperator();
            if(lastOperator.equals(CalculatorSchema.PERCENT_COMMAND) || lastOperator.equals(CalculatorSchema.EQU_COMMAND)) {
                popOperator();
                result = popOperand().toString();
            } else {
                return operandQueue.peek().toString();
            }
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

    public void pushOperator(String operator) {
        operatorQueue.push(operator);
    }

    public void pushOperand(String operand) {
        operandQueue.push(new BigDecimal(operand));
    }

    private String popOperator() {
        return operatorQueue.pop();
    }

    private BigDecimal popOperand() {
        return operandQueue.pop();
    }

    private String peekOperator() {
        return operatorQueue.peek();
    }

    private BigDecimal peekOperand() {
        return operandQueue.peek();
    }
}
