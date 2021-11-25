package main.java.old.Parsing;

public enum PreLudemeType {
        ERR(-1, "rootro", null),
        LUDEME(0, "ludeme", new char[]{'('}), // (
        COLLECTION(1, "collec", new char[]{'{'}), // {
        STRING(2, "string", new char[]{'"'}), // " or '
        DEFINE_PARAMETER(3, "defpar", new char[]{'#'}), // # in define
        LOWERCASE(4, "optarg",
                new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
                        'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'}), // any letter
        UPPERCASE(5, "attri", new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O','P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'}),
        NUMBER(6, "number", new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}),
        OPTION(7, "optarg",  new char[]{'<'});


        private int type;
        private String descr;
        private char[] startChar;

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
        private PreLudemeType(int type, String descr, char[] startChar) {
            this.type = type;
            this.descr = descr;
            this.startChar = startChar;
        }


}
