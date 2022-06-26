package aq.calculator;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorVisual {
    private final String TITLE = "Калькулятор v.1.0";
    private final Font DISPLAY_FONT = new Font("arial", Font.PLAIN, 15);
    private final int WIDTH_CALCULATOR = 265;
    private final int HEIGHT_DISPLAY_PANEL = 80;
    private final int HEIGHT_BUTTON_PANEL = 320;
    public static final int MAX_COUNT_NUMBER_DISPLAY = 15;

    private JFrame frame;
    private JPanel displayPanel;
    private JPanel buttonPanel;
    private JPanel mainPanel;
    private JTextField textFieldDisplay;
    private final JButton[][] BUTTONS;

    private CalculatorController calculatorController;

    public CalculatorVisual(CalculatorController calculatorController) {
        frame = new JFrame(TITLE);
        displayPanel = new JPanel();
        buttonPanel = new JPanel();
        mainPanel = new JPanel();
        BUTTONS = new JButton[CalculatorSchema.getRowCount()][CalculatorSchema.getColumnCount()];
        this.calculatorController = calculatorController;
    }

    public void getView() {
        packGraphicObjects();
    }

    private void packGraphicObjects() {
        configMainPanel();
        configDisplayPanel();
        configButtonPanel();
        configTextFieldDisplay();
        configButtons();
        setButtonsLocation();
        addContent();
        configFrame();
        addListenerToButtons(new CalculatorListener());
    }

    private void configMainPanel() {
        mainPanel.setLayout(new BorderLayout());
    }

    private void configDisplayPanel() {
        displayPanel.setPreferredSize(new Dimension(WIDTH_CALCULATOR, HEIGHT_DISPLAY_PANEL));
        displayPanel.setLayout(new BorderLayout());
    }

    private void configButtonPanel() {
        buttonPanel.setLayout(new SpringLayout());
        buttonPanel.setPreferredSize(new Dimension(WIDTH_CALCULATOR, HEIGHT_BUTTON_PANEL));
    }

    private void configTextFieldDisplay() {
        textFieldDisplay = new JTextField() {
            private static final long serialVersionUID = 1L;
            @Override
            protected Document createDefaultModel() {
                return new LimitField();
            }
        };
        textFieldDisplay.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        textFieldDisplay.setFont(new Font("Arial", Font.PLAIN, 30));
        textFieldDisplay.setEditable(false);
        textFieldDisplay.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        textFieldDisplay.setText(DisplayMessages.EMPTY_DISPLAY_MESSAGE.getStringMessage());
    }

    private void configButtons() {
        int initialIndex = 0;

        for(int i = 0; i < CalculatorSchema.getRowCount(); i++) {
            for(int j = 0; j < CalculatorSchema.getColumnCount(); j++) {
                JButton button = new JButton(CalculatorSchema.getSchema()[initialIndex]);
                button.setName(CalculatorSchema.getSchema()[initialIndex]);
                button.setPreferredSize(new Dimension(55, 55));
                button.setFont(DISPLAY_FONT);
                BUTTONS[i][j] = button;
                initialIndex++;
            }
        }
    }

    private void setButtonsLocation() {
        int marginNorthSide = 10;
        int marginWestSide = 15;
        int marginBetweenButton = 5;

        for(int i = 0; i < CalculatorSchema.getRowCount(); i++) {
            for(int j = 0; j < CalculatorSchema.getColumnCount(); j++) {
                if(i == 0) {
                    ((SpringLayout) buttonPanel.getLayout()).putConstraint(SpringLayout.NORTH, BUTTONS[i][j], marginNorthSide,
                            SpringLayout.NORTH, buttonPanel);
                } else {
                    ((SpringLayout) buttonPanel.getLayout()).putConstraint(SpringLayout.NORTH, BUTTONS[i][j], marginBetweenButton,
                            SpringLayout.SOUTH, BUTTONS[i - 1][j]);
                }
                if(j == 0) {
                    ((SpringLayout) buttonPanel.getLayout()).putConstraint(SpringLayout.WEST, BUTTONS[i][j], marginWestSide,
                            SpringLayout.WEST, buttonPanel);
                } else {
                    ((SpringLayout) buttonPanel.getLayout()).putConstraint(SpringLayout.WEST, BUTTONS[i][j], marginBetweenButton,
                            SpringLayout.EAST,  BUTTONS[i][j - 1]);
                }
            }
        }
    }

    private void addContent() {
        addButtonsOnButtonPanel();
        addTextFieldDisplayToDisplayPanel();
        addDisplayPanelOnBasePanel();
        addButtonPanelOnBasePanel();
        addBasePanelOnFrame();
    }

    private void addButtonsOnButtonPanel() {
        for(int i = 0; i < CalculatorSchema.getRowCount(); i++) {
            for(int j = 0; j < CalculatorSchema.getColumnCount(); j++) {
                buttonPanel.add(BUTTONS[i][j]);
            }
        }
    }

    private void addTextFieldDisplayToDisplayPanel() {
        displayPanel.add(textFieldDisplay);
    }

    private void addDisplayPanelOnBasePanel() {
        mainPanel.add(displayPanel, BorderLayout.NORTH);
    }

    private void addButtonPanelOnBasePanel() {
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
    }

    private void addBasePanelOnFrame() {
        frame.add(mainPanel);
    }

    private void configFrame() {
        frame.setResizable(false);
        frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - (WIDTH_CALCULATOR / 2), 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void addListenerToButtons(ActionListener listener) {
        for(JButton[] buttonRow : BUTTONS) {
            for(JButton button : buttonRow) {
                button.addActionListener(listener);
            }
        }
    }

    private void enableAllButtons() {
        for(int i = 0; i < CalculatorSchema.getRowCount(); i++) {
            for(int j = 0; j < CalculatorSchema.getColumnCount(); j++) {
                if(!BUTTONS[i][j].isEnabled()) {
                    BUTTONS[i][j].setEnabled(true);
                }
            }
        }
    }

    private void enableOnlyC_Button() {
        for(int i = 0; i < CalculatorSchema.getRowCount(); i++) {
            for(int j = 0; j < CalculatorSchema.getColumnCount(); j++) {
                if(!BUTTONS[i][j].getText().equals(CalculatorSchema.C_COMMAND)) {
                    BUTTONS[i][j].setEnabled(false);
                }
            }
        }
    }

    public void enableButton(String buttonTitle) {
        for(int i = 0; i < CalculatorSchema.getRowCount(); i++) {
            for(int j = 0; j < CalculatorSchema.getColumnCount(); j++) {
                if(BUTTONS[i][j].getText().equals(buttonTitle)) {
                    BUTTONS[i][j].setEnabled(true);
                }
            }
        }
    }

    public void disableButton(String buttonTitle) {
        for(int i = 0; i < CalculatorSchema.getRowCount(); i++) {
            for(int j = 0; j < CalculatorSchema.getColumnCount(); j++) {
                if(BUTTONS[i][j].getText().equals(buttonTitle)) {
                    BUTTONS[i][j].setEnabled(false);
                }
            }
        }
    }

    public boolean isEnableButton(String buttonTitle) {
        for(int i = 0; i < CalculatorSchema.getRowCount(); i++) {
            for(int j = 0; j < CalculatorSchema.getColumnCount(); j++) {
                if(BUTTONS[i][j].getText().equals(buttonTitle)) {
                    return BUTTONS[i][j].isEnabled();
                }
            }
        }
        return false;
    }

    public boolean isDisableButton(String buttonTitle) {
        for(int i = 0; i < CalculatorSchema.getRowCount(); i++) {
            for(int j = 0; j < CalculatorSchema.getColumnCount(); j++) {
                if(BUTTONS[i][j].getText().equals(buttonTitle)) {
                    return !BUTTONS[i][j].isEnabled();
                }
            }
        }
        return false;
    }

    private class CalculatorListener implements ActionListener {

        private String lastUsedButtonTitle;
        @Override
        public void actionPerformed(ActionEvent ae) {
            JButton button = (JButton) ae.getSource();
            String textButton = button.getText();
            buttonActionHandle(textButton);
        }

        private void buttonActionHandle(String textButton) {
            switch (textButton) {
                case CalculatorSchema.ADD_COMMAND:
                    doAdditionCommand();
                    writeLastButtonTitle(textButton);
                    break;
                case CalculatorSchema.SUB_COMMAND:
                    doSubtractCommand();
                    writeLastButtonTitle(textButton);
                    break;
                case CalculatorSchema.MUL_COMMAND:
                    doMultiplyCommand();
                    writeLastButtonTitle(textButton);
                    break;
                case CalculatorSchema.DIV_COMMAND:
                    doDivideCommand();
                    writeLastButtonTitle(textButton);
                    break;
                case CalculatorSchema.EQU_COMMAND:
                    doEqualsCommand();
                    writeLastButtonTitle(textButton);
                    break;
                case CalculatorSchema.C_COMMAND:
                    cleanDisplay();
                    calculatorController.reset();
                    enableAllButtons();
                    break;
                case CalculatorSchema.CE_COMMAND:
                    cleanDisplay();
                    break;
                case CalculatorSchema.B_COMMAND:
                    backspaceCommand();
                    break;
                case CalculatorSchema.POINT_COMMAND:
                    doPointCommand();
                    break;
                case CalculatorSchema.PERCENT_COMMAND:
                    doPercentCommand();
                    writeLastButtonTitle(textButton);
                    break;
                default:
                    doNumberCommand(textButton);
                    writeLastButtonTitle(textButton);
                    break;
            }
        }

        private void writeLastButtonTitle(String textButton) {
            lastUsedButtonTitle = textButton;
        }

        private void doAdditionCommand() {
            if(!isDisplayEmpty()) {
                if(textFieldDisplay.getText().matches(CalculatorSchema.SUB_COMMAND)) {
                    cleanDisplay();
                } else {
                    pushData(textFieldDisplay.getText(), CalculatorSchema.ADD_COMMAND);
                    getResult();
                }
            }
        }

        private void doSubtractCommand() {
            if(!isDisplayEmpty()) {
                pushData(textFieldDisplay.getText(), CalculatorSchema.SUB_COMMAND);
                getResult();
            } else {
                setMinusToDisplay();
            }
        }

        private void setMinusToDisplay() {
            setDisplayText(CalculatorSchema.SUB_COMMAND, "");
        }

        private void doMultiplyCommand() {
            if(!isDisplayEmpty()) {
                pushData(textFieldDisplay.getText(), CalculatorSchema.MUL_COMMAND);
                getResult();
            }
        }

        private void doDivideCommand() {
            if(!isDisplayEmpty()) {
                pushData(textFieldDisplay.getText(), CalculatorSchema.DIV_COMMAND);
                getResult();
            }
        }

        private void doPercentCommand() {
            if(!isDisplayEmpty()) {
                pushData(textFieldDisplay.getText(), CalculatorSchema.PERCENT_COMMAND);
                getResult();
            }
        }

        private void doPointCommand() {
            if(!isDisplayEmpty()) {
                if(!isNumberContainsPoint()) {
                    setDisplayText(CalculatorSchema.POINT_COMMAND, textFieldDisplay.getText());
                }
            } else {
                setDisplayText(CalculatorSchema.POINT_COMMAND, "0");
            }
        }

        private boolean isNumberContainsPoint() {
            return textFieldDisplay.getText().matches("\\d+\\.|\\d+\\.\\d+");
        }

        private void doEqualsCommand() {
            if(!isDisplayEmpty()) {
                pushData(textFieldDisplay.getText(), CalculatorSchema.EQU_COMMAND);
                getResult();
                disableButton(CalculatorSchema.B_COMMAND);
                disableButton(CalculatorSchema.CE_COMMAND);
            }
        }

        private void doNumberCommand(String number) {
            if(!isDisplayEmpty()) {
                if(!lastUsedButtonTitle.matches("\\d")) {
                    cleanDisplay();
                    if(isDisableButton(CalculatorSchema.B_COMMAND)) {
                        enableButton(CalculatorSchema.B_COMMAND);
                    }
                    if(isDisableButton(CalculatorSchema.CE_COMMAND)) {
                        enableButton(CalculatorSchema.CE_COMMAND);
                    }
                }
            }
            setNumberToDisplay(number);
        }

        private void getResult() {
            String result = calculatorController.getResult();
            System.out.println(result);
            if(isNoNumberResult(result)) {
                enableOnlyC_Button();
            }
            setResultToDisplay(result);
        }

        private boolean isNoNumberResult(String result) {
            return result.equals(DisplayMessages.TO_LONG_NUMBER_MESSAGE.getStringMessage()) ||
                    result.equals(DisplayMessages.DIVIDE_BY_ZERO_MESSAGE.getStringMessage());
        }

        private void setResultToDisplay(String result) {
            setDisplayText("", result);
        }

        private boolean isDisplayEmpty() {
            return textFieldDisplay.getText().equals("");
        }

        private void setNumberToDisplay(String textButton) {
            String textDisplay = textFieldDisplay.getText();
            setDisplayText(textButton, textDisplay);
        }

        private void setDisplayText(String textButton, String additionText) {
            String operand = additionText + textButton;
            textFieldDisplay.setText(operand);
        }

        private void pushData(String operand, String operator) {
            if(!textFieldDisplay.getText().matches(CalculatorSchema.SUB_COMMAND)) {
                calculatorController.pushOperand(operand);
                calculatorController.pushOperator(operator);
            }
        }

        private void backspaceCommand() {
            String textDisplay = textFieldDisplay.getText();
            if(!textDisplay.equals(DisplayMessages.EMPTY_DISPLAY_MESSAGE.getStringMessage())) {
                if(textDisplay.length() > 1) {
                    String operand = textDisplay.substring(0, textDisplay.length() - 1);
                    setDisplayText(operand, DisplayMessages.EMPTY_DISPLAY_MESSAGE.getStringMessage());
                } else {
                    cleanDisplay();
                }
            }
        }

        private void cleanDisplay() {
            textFieldDisplay.setText(DisplayMessages.EMPTY_DISPLAY_MESSAGE.getStringMessage());
        }
    }

    private class LimitField extends PlainDocument {
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if(str == null) {
                return;
            }
            if((getLength() + str.length()) <= MAX_COUNT_NUMBER_DISPLAY) {
                super.insertString(offs, str, a);
            } else {
                textFieldDisplay.setText(str.substring(0, str.length() -  1));
            }
        }
    }
}
