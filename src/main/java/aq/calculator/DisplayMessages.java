package aq.calculator;

public enum DisplayMessages {

    EMPTY_DISPLAY_MESSAGE(""), DIVIDE_BY_ZERO_MESSAGE("Divide by zero"), TO_LONG_NUMBER_MESSAGE("To long number");
    private String message;

    DisplayMessages(String message) {
        this.message = message;
    }

    public String getStringMessage() {
        return message;
    }
}
