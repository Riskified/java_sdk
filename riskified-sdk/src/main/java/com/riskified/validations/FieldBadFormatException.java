package com.riskified.validations;

public class FieldBadFormatException extends Exception {

    private Object source;

    public FieldBadFormatException(String message) {
        super(message);
    }

    public FieldBadFormatException(Object source, String message) {
        super(message);
        this.source = source;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

}
