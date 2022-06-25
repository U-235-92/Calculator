package aq.calculator;

public class CalculatorSchema {

    private CalculatorSchema() {}

    private static final int ROW_COUNT = 5;
    private static final int COLUMN_COUNT = 4;
    private static String[] CALCULATOR_SCHEMA;

    public static final String ADD_COMMAND = "+";
    public static final String SUB_COMMAND = "-";
    public static final String MUL_COMMAND = "*";
    public static final String DIV_COMMAND = "/";
    public static final String EQU_COMMAND = "=";
    public static final String PERCENT_COMMAND = "%";
    public static final String POINT_COMMAND = ".";
    public static final String C_COMMAND = "C";
    public static final String CE_COMMAND = "CE";
    public static final String B_COMMAND = "B";

    public static final String[] getSchema() {
        if(CALCULATOR_SCHEMA == null) {
            return CALCULATOR_SCHEMA = new String[]
                    { B_COMMAND,      C_COMMAND,         CE_COMMAND, PERCENT_COMMAND,
                            "7", 			"8", 				"9",     ADD_COMMAND,
                            "4", 			"5", 				"6",     SUB_COMMAND,
                            "1", 			"2", 				"3",     MUL_COMMAND,
                            "0",  POINT_COMMAND,        EQU_COMMAND,     DIV_COMMAND
                    };
        } else {
            return CALCULATOR_SCHEMA;
        }
    }

    public static final int getRowCount() {
        return ROW_COUNT;
    }

    public static final int getColumnCount() {
        return COLUMN_COUNT;
    }
}
