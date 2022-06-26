package aq.calculator;

public class CalculatorController {

    private CalculatorVisual calculatorVisual;
    private CalculatorLogic calculatorLogic;

    public CalculatorController() {
        calculatorLogic = new CalculatorLogic();
        calculatorVisual = new CalculatorVisual(this);
    }


}
