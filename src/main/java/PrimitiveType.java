package main.java;

public enum PrimitiveType {
    STRING("String"),
    INT("int"),
    FLOAT("float"),
    BOOLEAN("boolean"),
    CHAR("char"),
    OBJECT("object"),
    VOID("void");

    private String name;

    PrimitiveType(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
