package main.java;

public class TypeValuePair {
    private PrimitiveType type;
    private String value;

    public TypeValuePair(PrimitiveType type, String value) {
        this.type = type;
        this.value = value;
    }

    public PrimitiveType getType() {
        return type;
    }

    public void setType(PrimitiveType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
