package main.java.parser;

import main.java.Utils.PrintUtils;
import main.java.domain.RecNode;
import main.java.domain.Tree;
import main.java.interfaces.iRecNode;

import java.util.ArrayList;

public class Parser {
    public static Tree getTree(String gameDescription) {
        gameDescription = preProcessing(gameDescription);
        Tree t = parse(gameDescription);
        t = postProcessing(t);
        return t;
    }

    private static String preProcessing(String gameDescription) {
        //TODO
        return gameDescription;
    }

    private static Tree parse(String gameDescription) {
        //1. Create a node with the whole gameDescription as the keyword
        iRecNode root = new RecNode(gameDescription);
        Tree t = recursiveParse(root);
        return t;
    }

    private static Tree recursiveParse(iRecNode n) {
        String ludeme = n.getKeyword();
        char first = ludeme.charAt(0);
        LudemeType type = preClassify(first);
        n.setLudemeType(type);
        // Go through the ludeme
        String[] subLudemes = splitIntoSubLudemes(ludeme);
        for(String s : subLudemes) {
            RecNode c = (RecNode) n.addChild(s);
            recursiveParse(c);
        }

        return new Tree(n);
    }

    public static String[] splitIntoSubLudemes(String contents) {
        boolean debug = true;

        //step 1: search for first ')'
        ArrayList<String> subludemes = new ArrayList<>();
        int i = 0;
        int start = 0;
        int end = -1;
        int nestingLevel = -1, startNestingLevel = 0;
        boolean foundFirst = false;
        boolean foundSecond = false;
        LudemeType foundType = preClassify(contents.charAt(0)); // because the brackets are removed around the ludeme: (game ...) -> game ..., so needs to be keyword, so we look for " "
        while(i < contents.length()) {
            char cur = contents.charAt(i);
            //need to find fist space
            if(!foundFirst) {
                //from first character we can classify it into a type
                foundType = preClassify(cur);
                if(foundType == LudemeType.ERR) {
                    foundFirst = false;
                } else {
                    if (cur == '(' || cur == '{') {
                        nestingLevel = 0;
                        startNestingLevel = 0;
                    }
                    foundFirst = true;
                    start = i;
                }
            }
            else if(foundFirst){
                if(debug) System.out.println(foundType);
                switch (foundType) {
                    case PRE_LUDEME:
                        //need to find ')'
                        //keep track of nesting
                        if(debug)System.out.println("nesting "+nestingLevel);
                        if(cur == '(')
                            nestingLevel++;
                        if(cur == ')') {
                            nestingLevel--;
                            if(nestingLevel == (startNestingLevel - 1)) {
                                //System.out.println("end");
                                //HOORAY we have closed the ludeme
                                foundSecond = true;
                                end = i + 1;
                            }
                        }
                        break;
                    case PRE_COLLECTION:
                        //need to find '}'
                        //keep track of nesting
                        if(cur == '{')
                            nestingLevel++;
                        if(cur == '}') {
                            nestingLevel--;
                            if(nestingLevel == (startNestingLevel - 1)) {
                                //HOORAY we have closed the ludeme
                                foundSecond = true;
                                end = i + 1;
                            }
                        }
                        break;
                    case PRE_STRING:
                        //need to find "
                        if(cur == '"') {
                            //found a complete string
                            foundSecond = true;
                            end= i + 1;
                        }
                        break;
                    case PRE_DEFINE_PARAMETER:
                    case PRE_UPPERCASE:
                    case PRE_NUMBER:
                        //need to find space
                        if(cur == ' ') {
                            //found a complete string
                            foundSecond = true;
                            end =  i;
                        }
                        break;

                    case PRE_LOWERCASE:
                        //need to find colon for optional argument name
                        if(cur == ':') {
                            //found a complete optional name
                            //because we also want to parse the value
                            if(contents.charAt(i + 1) == ' ') {
                                foundType = preClassify(contents.charAt(i + 2));
                            } else {
                                foundType = preClassify(contents.charAt(i + 1));
                            }
                        }
                        //need to find space
                        else if(cur == ' ') {
                            //found a complete string
                            foundSecond = true;
                            end =  i;
                        }
                        break;
                    case PRE_OPTION:
                        //need to find '>'
                        if(cur == '>') {
                            //found a complete options tag
                            foundSecond = true;
                            end= i + 1;
                        }
                }
                if(foundSecond) {
                    subludemes.add(contents.substring(start,end));
                    if(debug)System.out.println(i + " " + cur + " 1.:" + foundFirst + " 2.:" + foundSecond);
                    if(debug)PrintUtils.printCollection(subludemes);
                    if(debug)System.out.println("FOUND: " + contents.substring(start,end));
                    start = -1;
                    end = -1;
                    foundFirst = false;
                    foundSecond = false;
                } else {
                    if(debug)System.out.println(i + " " + cur + " 1.:" + foundFirst + " 2.:" + foundSecond);
                    if(debug)PrintUtils.printCollection(subludemes);
                    if(foundFirst) {
                        if(debug)System.out.println("CURRENT SELECTION: " + contents.substring(start, i + 1));
                    }  else {
                        if(debug)System.out.println("SEARCHING");
                    }
                }

                if(debug)System.out.println(" ");
            }
            i++;
        }
        //check if there is still an open ludeme
        if(foundFirst)
            subludemes.add(contents.substring(start,contents.length()-1));//REMOVE LAST CHAR

        PrintUtils.printCollection(subludemes);
        return subludemes.toArray(String[]::new);
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

    private static Tree postProcessing(Tree t) {
        //TODO
        return null;
    }

}
