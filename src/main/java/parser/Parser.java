package main.java.parser;

import main.java.Utils.PrintUtils;
import main.java.domain.NodeType;
import main.java.domain.RecNode;
import main.java.domain.Tree;
import main.java.domain.Tuple;
import main.java.interfaces.iRecNode;
import main.java.interfaces.iTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    public static iTree getTree(String gameDescription) {
        gameDescription = preProcessing(gameDescription);
        iTree t = parse(gameDescription);
        t = postProcessing(t);
        return t;
    }

    private static String preProcessing(String gameDescription) {
        //TODO
        return gameDescription;
    }

    private static iTree parse(String gameDescription) {
        //1. Create a node with the whole gameDescription as the keyword
        iRecNode root = new RecNode("root");
        ArrayList<Tuple<String,ArrayList<String>, LudemeType>> subLudemes = splitIntoSubLudemes(gameDescription);
        for(Tuple<String,ArrayList<String>, LudemeType> t : subLudemes) {
            iRecNode c = root.addChild(t.getR());
            recursiveParse(c, t.getS());
        }
        iTree t = new Tree(root);
        return t;
    }

    private static void recursiveParse(iRecNode n, ArrayList<String> subludemes) {
        System.out.println("-------------------");
        System.out.println(n.getKeyword());
        for(String s : subludemes) {
            ArrayList<Tuple<String,ArrayList<String>, LudemeType>> tupleList = splitIntoSubLudemes(s);
            System.out.println(s);
            System.out.println(tupleList);
            for(Tuple<String,ArrayList<String>, LudemeType> t : tupleList) {
                iRecNode c = n.addChild(t.getR());
                recursiveParse(c, t.getS());
            }
        }

    }


    public static ArrayList<Tuple<String,ArrayList<String>, LudemeType>> splitIntoSubLudemes(String contents) {
        int i = 0;
        int previous = i;
        ArrayList<Tuple<String,ArrayList<String>, LudemeType>> list = new ArrayList<>();
        int l = contents.length();
        do {
            ArrayList<String> subludemes = new ArrayList<>();
            LudemeType foundType = preClassify(contents.charAt(previous));
            String keyword = "";
            int end = 0;
            switch(foundType){
                case PRE_OPTION:
                case PRE_LOWERCASE:
                case PRE_DEFINE_PARAMETER:
                case PRE_NUMBER:
                case PRE_UPPERCASE:
                    end = previous + findEndOfWord(contents.substring(previous));
                    keyword = contents.substring(previous,end);
                    break;
                case PRE_STRING:
                    end = findAtSameLevel(contents,'"', previous);
                    keyword = contents.substring(previous,end);
                    break;
                case PRE_COLLECTION:
                    end = findAtSameLevel(contents,'}', previous);
                    keyword = "{}";
                    subludemes = findItemsAtEqualLevel(contents.substring(previous + 1,end - 1));
                    break;
                case PRE_LUDEME:
                    end = findAtSameLevel(contents,')', previous);
                    keyword = contents.substring(1,findEndOfWord(contents));
                    subludemes = findItemsAtEqualLevel(contents.substring(previous + 1,end - 1));
                    subludemes.remove(0);
                    break;

            }
            i = end;
            previous = end + 1;

            //add all
            Tuple<String, ArrayList<String>, LudemeType> t = new Tuple(keyword,subludemes, foundType);
            list.add(t);
        }while(i < l - 1);
        return list;
    }

    private static ArrayList<String> findItemsAtEqualLevel(String contents) {
        ArrayList<String> list = new ArrayList<>();
        int level = 0;
        int previous = 0;
        for(int i = 0; i < contents.length(); i++) {
            char cur = contents.charAt(i);
            if(cur == '(' || cur == '{')
                level++;
            if(cur == ')' || cur == '}')
                level--;

            if(cur == ' ' || i == contents.length() - 1) {
                if(level == 0) {
                    list.add(contents.substring(previous, i + 1));
                    previous = i + 1;
                }
            }

        }
        return list;
    }

    private static int findAtSameLevel(String contents, char searched, int posOfFirst) {
        char first = contents.charAt(posOfFirst);
        int level = 0;
        for(int i = posOfFirst + 1; i < contents.length(); i++) {
            char cur = contents.charAt(i);
            if(cur == searched) {
                if(level == 0) {
                    return i + 1;
                }
            }
            if(cur == '(' || cur == '{')
                level++;
            if(cur == ')' || cur == '}')
                level--;

        }
        String exc = "No closing char: " + searched + " could be found in: " + contents;
        throw new IllegalStateException(exc);
    }

    /**
     * Used to find the end of the word of a
     * - PRE_OPTION
     * - PRE_UPPERCASE
     * - PRE_LOWERCASE
     * - PRE_DEFINE_PARAMETER
     * - PRE_NUMBER
     * @param contents
     * @return
     */
    private static int findEndOfWord(String contents) {
        for(int i = 0; i < contents.length(); i++) {
            char cur = contents.charAt(i);
            if(cur == ' ' || cur == ')' || cur == '}') //these can be around all of the types
                return i;
        }
        return contents.length();
    }

    public static LudemeType preClassify(char first) {
        switch(first) {
            case '(': return LudemeType.PRE_LUDEME;
            case '{': return LudemeType.PRE_COLLECTION;
            case '"': return LudemeType.PRE_STRING;
            case '#': return LudemeType.PRE_DEFINE_PARAMETER;
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'i':
            case 'j':
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            case 's':
            case 't':
            case 'u':
            case 'v':
            case 'w':
            case 'x':
            case 'y':
            case 'z': return LudemeType.PRE_LOWERCASE;
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case '.': return LudemeType.PRE_NUMBER;
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z': return LudemeType.PRE_UPPERCASE;
            case '<': return LudemeType.PRE_OPTION;

            default: return LudemeType.ERR;
        }
    }

    public static LudemeType reClassify(LudemeType pre, String ludeme) {
        switch(pre) {
            case PRE_LUDEME:
                return LudemeType.LUDEME;
            case PRE_COLLECTION:
                return LudemeType.COLLECTION;
            case PRE_STRING:
                int occ = 0;
                //count the number of " in th ludeme
                for(int i = 0; i < ludeme.length(); i++) {
                    if(ludeme.charAt(i) == '"')
                        occ++;
                }
                if(occ == 4) { // a la "A1".."B3"
                    if(ludeme.contains(".."))
                        return LudemeType.RANGE_FIELDS;
                }
                return LudemeType.STRING;
            case PRE_DEFINE_PARAMETER:
                return LudemeType.DEFINE_PARAMETER;
            case PRE_LOWERCASE:
                if(ludeme.equals("true") || ludeme.equals("false"))
                    return LudemeType.BOOLEAN;
                if(ludeme.contains(":"))
                    return LudemeType.OPTIONAL_ARGUMENT_NAME;
                return LudemeType.ERR;
            case PRE_UPPERCASE:
                if(ludeme.equals("True") || ludeme.equals("False"))
                    return LudemeType.BOOLEAN;
                return LudemeType.ATTRIBUTE;
            case PRE_NUMBER:
                if(Character.isDigit(ludeme.charAt(0)) && ludeme.contains("..") && Character.isDigit(ludeme.charAt(ludeme.length() - 1)))
                    return LudemeType.RANGE_NUMBER;
                if(ludeme.contains("."))
                    return LudemeType.FLOAT;
                return LudemeType.INTEGER;
            case PRE_OPTION:
                if(Character.isLowerCase(ludeme.charAt(1))) {
                    return LudemeType.OPTION_ARGUMENT_LABEL;
                } else {
                    return LudemeType.OPTION_LABEL;
                }
            default:
                return LudemeType.ERR;
        }
    }

    private static iTree postProcessing(iTree t) {
        //TODO
        return t;
    }

}
