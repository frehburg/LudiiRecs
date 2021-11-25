package main.java.old.Parsing;

public enum LudemeType {
    ERR(-1, "rootro", null, null,null),
    LUDEME(0, "ludeme", new char[]{'('}, null, new char[]{')'}), // (
    COLLECTION(1, "collec", new char[]{'{'}, null, new char[]{'}'}), // {
    STRING(2, "string", new char[]{'"'}, null, new char[]{'"'}), // " or '
    DEFINE_PARAMETER(3, "defpar", new char[]{'#'}, null,
            new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}), // # in define
    OPTIONAL_ARGUMENT_NAME(4, "optarg",
            new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'}, ":",
            new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}), // any letter
    INTEGER(5, "intege", new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'},
            null, new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}),
    FLOAT(6, "floatno", new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'},
            ".", new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}),
    OPTION_LABEL(8, "optarg",  new char[]{'<'}, null, new char[]{'>'}),//uppercase
    OPTION_ARGUMENT_LABEL(9, "optarg",  new char[]{'<'}, null, new char[]{'>'}),//lowercase
    ATTRIBUTE(10, "attri", new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O','P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'},
            null,
            new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                    'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}),
    RANGE_NUMBER(11,"rangno", new char[]{'0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9'},
            "..",
            new char[]{'0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9'}),
    RANGE_FIELDS(12, "rangfi", new char[]{'"'}, "..", new char[]{'"'}),
    BOOLEAN(13, "boolea", new char[]{'T', 'F', 't'}, null, new char[]{'e'});


    private final String identifiyingSubsequence;
    private int type;
    private String descr;
    private char[] startChar, endChar;

    public static final char[] LOWERCASE_ALPHABET = {'a', 'b', 'c', 'd', 'e',
            'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'};
    public static final char[] UPPERCASE_ALPHABET = {'A', 'B', 'C', 'D', 'E',
            'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'};
    public static final char[] NUMBERS = {'0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9'};
    public static final char PLACEHOLDER = '*';
    private LudemeType(int type, String descr, char[] startChar, String identifyingSubsequence, char[] endChar) {
        this.type = type;
        this.descr = descr;
        this.startChar = startChar;
        this.identifiyingSubsequence = identifyingSubsequence;
        this.endChar = endChar;
    }
}
