package main.java;

public enum PrimitiveType {
    STRING("String"),
    INT("int"),
    DOUBLE("double"),
    BOOLEAN("boolean"),
    CHAR("char"),
    OBJECT("Object"),
    VOID("void");

    private String name;

    PrimitiveType(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
