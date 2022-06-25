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

    public CalculatorVisual() {
        frame = new JFrame(TITLE);
        displayPanel = new JPanel();
        buttonPanel = new JPanel();
        mainPanel = new JPanel();
        BUTTONS = new JButton[CalculatorSchema.getRowCount()][CalculatorSchema.getColumnCount()];
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

    private class CalculatorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            JButton button = (JButton) ae.getSource();
            String textButton = button.getText();
            buttonActionHandle(textButton);
        }

        private void buttonActionHandle(String textButton) {
            switch (textButton) {
                case CalculatorSchema.ADD_COMMAND:
                    break;
                case CalculatorSchema.SUB_COMMAND:
                    break;
                case CalculatorSchema.MUL_COMMAND:
                    break;
                case CalculatorSchema.DIV_COMMAND:
                    break;
                case CalculatorSchema.EQU_COMMAND:
                    break;
                case CalculatorSchema.C_COMMAND:
                    cleanDisplay();
                    break;
                case CalculatorSchema.CE_COMMAND:
                    cleanDisplay();
                    break;
                case CalculatorSchema.B_COMMAND:
                    backspaceDisplay();
                    break;
                case CalculatorSchema.POINT_COMMAND:
                    break;
                case CalculatorSchema.PERCENT_COMMAND:
                    break;
                default:
                    setNumberToDisplay(textButton);
//                    cleanDisplay();
                    break;
            }
        }

        private void setNumberToDisplay(String textButton) {
            String textDisplay = textFieldDisplay.getText();
            setDisplayText(textButton, textDisplay);
        }
        private void setDisplayText(String textButton, String additionText) {
            String operand = additionText + textButton;
            textFieldDisplay.setText(operand);
        }

        private void backspaceDisplay() {
            String textDisplay = textFieldDisplay.getText();
            if(!textDisplay.equals(DisplayMessages.EMPTY_DISPLAY_MESSAGE.getStringMessage())) {
                if(textDisplay.length() > 1) {
                    String operand = textDisplay.substring(0, textDisplay.length() - 1);
                    setDisplayText(operand, DisplayMessages.EMPTY_DISPLAY_MESSAGE.getStringMessage());
//                    Pattern patternPoint = Pattern.compile("\\.");
//                    Matcher matcherPoint = patternPoint.matcher(operand);
//                    if(!matcherPoint.find()) {
//                        if(isDisableButton(BTN_POINT_TITLE)) {
//                            enableButton(BTN_POINT_TITLE);
//                        }
//                    }
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
