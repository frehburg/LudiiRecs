package Parsing;

public enum LudemeType {
    ROOT(-1, "rootro"),
    LUDEME(0, "ludeme"), // (
    COLLECTION(1, "collec"), // {
    STRING(2, "string"), // " or '
    PARAMETER(3, "parame"), // #
    KEYWORD(4, "keywor"), // any letter
    NUMBER(5, "number"); // any number

    private int type;
    private String descr;
    private LudemeType(int type, String descr) {
        this.type = type;
        this.descr = descr;
    }
}
