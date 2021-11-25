package main.java.Parsing;


import main.java.Tree.Tree;
import main.java.Utils.FileUtils;
import main.java.Utils.PrintUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    /**
     * // TODO: add comment
     * @param f
     * @return
     * @throws FileNotFoundException
     */
    public static Tree parse(File f) throws FileNotFoundException {
        Tree root = new Tree(null, "root", false, /*TODO: Change back to LudemeType when ready*/PreLudemeType.ERR);
        String contents = FileUtils.getContents(f);
        String[] ludemes = firstSplit(contents);
        System.out.println(Arrays.toString(ludemes));
        // run through the whole string, need to make tree
        for (String cur : ludemes) {
            //each ludeme becomes a tree, that is a child to the current tree
            if(cur != "" && cur != null)
                root.addChild(buildTree(cur));
        }
        return root;
    }

    /**
     * This method takes a string contents, which is expected to be a compilable ludii expression.
     * It then splits it into it´s sub-parts and creates a tree, how depends on the type of the ludeme.
     * If it is a ..., the root is a node ...
     * Ludeme -> with the ludeme keyword as root and all its parameters as children (not terminal),
     * Collection -> "collection" with all elements of the collection as children (not terminal),
     * Keyword, Number, Parameter, String -> a node containing the affore mentioned without children (terminal)
     *
     * To achieve having Tree objects as children, the method first recursively calls itself on the relevant sub-parts
     * and then passes the resulting tree objects.
     * @param contents
     * @return the ludii expression in contents parsed as a Tree
     */
    public static Tree buildTree(String contents) {
        Tree t = null;
        if(contents.equals(""))
            throw new NullPointerException("The contents String was null");
        PreLudemeType type = preclassify(contents.charAt(0), true);
        if(type == PreLudemeType.LUDEME || type == PreLudemeType.OPTION) {
            //removes () around ludeme
            contents = contents.substring(1);

            String[] sub = splitIntoSubLudemes(contents);
            System.out.println("-----------------------------------------");
            System.out.println(contents);
            System.out.println(" ");
            PrintUtils.printCollection(Arrays.asList(sub));
            System.out.println("---");
            List<Tree> children = new ArrayList<>();
            for(int i = 1; i < sub.length; i++) {
                if(sub[i] != null && sub[i] != "")
                    children.add(buildTree(sub[i]));
            }
            // TODO: Make this work with LudemeType instead of pre
            t = new Tree(children, sub[0], false, type);

        } else if(type == PreLudemeType.COLLECTION) {
            //removes {} around collection
            contents = contents.substring(1,contents.length() - 1);

            String[] sub = splitIntoSubLudemes(contents);

            List<Tree> children = new ArrayList<>();
            for (String s : sub) {
                if(s != "" && s != null)
                    children.add(buildTree(s));
            }
            t = new Tree(children, "collection", false, type);
        }
        else if(type == PreLudemeType.UPPERCASE || type == PreLudemeType.NUMBER
                || type == PreLudemeType.DEFINE_PARAMETER || type == PreLudemeType.STRING
                || type == PreLudemeType.LOWERCASE) {
            //no subdivision necessary
            t = new Tree(null, contents, true, type);
        }
        return t;
    }

    /**
     * Only works if all subs are ludemes with () around them
     * Takes the contents of a .lud file, applies preprocessing and then splits it into ludemes, while correcting spacing
     * and removing comments.
     * Results in a String array with each String being either
     * - the game ludeme,
     * - the metadata ludeme or
     * - a define ludeme.
     *
     * @param contents
     * @return String array of ludemes in .lud file contents string
     */
    public static String[] firstSplit(String contents) {
        contents = PrintUtils.insertSpaceAroundBrackets(contents);
        //step 1: search for first ')'
        int nestingLevel = -1; // root is not actually part of the tree
        int startNestingLevel = 0;
        List<String> ludemes = new ArrayList<>();
        int start = -1;
        int end;
        for(int i = 0; i < contents.length(); i++) {
            char cur = contents.charAt(i);
            //we have opened a ludeme
            if(start > -1) {
                if(cur == '(')
                    nestingLevel++;
                if(cur == ')') {
                    nestingLevel--;
                    if(nestingLevel == (startNestingLevel - 1)) {
                        //System.out.println("end");
                        //HOORAY we have closed the ludeme
                        end = i + 1;
                        String ludeme = contents.substring(start, end);
                        ludemes.add(ludeme);
                        start = -1;
                    }
                }

            } else {
                //we have not yet opened a ludeme
                if(cur == '(') { // we have a new ludeme
                    // new '(' so nested
                    nestingLevel++;
                    startNestingLevel = nestingLevel;
                    // to select ludeme later
                    start = i;
                }

            }
            //System.out.println(cur + " " + nestingLevel);
        }
        PrintUtils.printCollection(ludemes);
        return ludemes.toArray(String[]::new);
    }

    /**
     * Expects removed () around the ludeme
     * Must work for subs that are
     * - ludemes with () around
     * - collections with {} around
     * - Strings with " " around
     * - parameters starting with #
     * - keywords starting and ending with any upper or lowercase letter
     * - booleans
     * -
     * @param contents
     * @return
     */
    public static String[] splitIntoSubLudemes(String contents) {
        boolean debug = true;

        //step 1: search for first ')'
        List<String> ludemes = new ArrayList<>();
        int i = 0;
        int start = 0;
        int end = -1;
        int nestingLevel = -1, startNestingLevel = 0;
        boolean foundFirst = true;
        boolean foundSecond = false;
        PreLudemeType foundType = preclassify(contents.charAt(0), false); // because the brackets are removed around the ludeme: (game ...) -> game ..., so needs to be keyword, so we look for " "
        while(i < contents.length()) {
            char cur = contents.charAt(i);
            //need to find fist space
            if(!foundFirst) {
                //from first character we can classify it into a type
                foundType = preclassify(cur, true);
                if(foundType == PreLudemeType.ERR) {
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
                    case LUDEME:
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
                    case COLLECTION:
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
                    case STRING:
                        //need to find "
                        if(cur == '"') {
                            //found a complete string
                            foundSecond = true;
                            end= i + 1;
                        }
                        break;
                    case DEFINE_PARAMETER:
                    case UPPERCASE:
                    case NUMBER:
                        //need to find space
                        if(cur == ' ') {
                            //found a complete string
                            foundSecond = true;
                            end =  i;
                        }
                        break;

                    case LOWERCASE:
                        //need to find colon for optional argument name
                        if(cur == ':') {
                            //found a complete optional name
                            //because we also want to parse the value
                            if(contents.charAt(i + 1) == ' ') {
                                foundType = preclassify(contents.charAt(i + 2), false);
                            } else {
                                foundType = preclassify(contents.charAt(i + 1), false);
                            }
                        }
                        //need to find space
                        else if(cur == ' ') {
                            //found a complete string
                            foundSecond = true;
                            end =  i;
                        }
                        break;
                    case OPTION:
                        //need to find '>'
                        if(cur == '>') {
                            //found a complete options tag
                            foundSecond = true;
                            end= i + 1;
                        }
                }
                if(foundSecond) {
                    ludemes.add(contents.substring(start,end));
                    if(debug)System.out.println(i + " " + cur + " 1.:" + foundFirst + " 2.:" + foundSecond);
                    if(debug)PrintUtils.printCollection(ludemes);
                    if(debug)System.out.println("FOUND: " + contents.substring(start,end));
                    start = -1;
                    end = -1;
                    foundFirst = false;
                    foundSecond = false;
                } else {
                    if(debug)System.out.println(i + " " + cur + " 1.:" + foundFirst + " 2.:" + foundSecond);
                    if(debug)PrintUtils.printCollection(ludemes);
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
            ludemes.add(contents.substring(start,contents.length()-1));//REMOVE LAST CHAR

        PrintUtils.printCollection(ludemes);
        return ludemes.toArray(String[]::new);
    }

    /**
     * This method classifies a ludeme into one of the six following categories:
     * - Ludeme,
     * - Collection,
     * - String,
     * - Parameter or
     * - Number
     * according to its first character.
     * @param c first character of an expression
     * @param b
     * @return The type of the ludeme
     */
    public static PreLudemeType preclassify(char c, boolean b) {
        // ludeme : '(' = 40
        // lowercase range:  a - z = 97 - 122
        int i = (int) c;
        if(b)System.out.println("CLASSIFICATION "+ c + " "+i);
        if(i == 40) { // Ludeme: '('
            if(b)System.out.println("lud");
            return PreLudemeType.LUDEME;
        } else if(i == 123) { // Collection: '{'
            if(b)System.out.println("coll");
            return PreLudemeType.COLLECTION;
        } else if(i == 34){ // String: ' " '
            if(b)System.out.println("str");
            return PreLudemeType.STRING;
        } else if(i == 35){ // Define parameter: ' # '
            if(b)System.out.println("def #");
            return PreLudemeType.DEFINE_PARAMETER;
        } else if(97 <= i && i <= 122){ // any lowercase letter
            if(b)System.out.println("low");
            return PreLudemeType.LOWERCASE;
        } else if(i >= 65 && i <= 90){ // any uppercase letter
            if(b)System.out.println("upp");
            return PreLudemeType.UPPERCASE;
        } else if(i >= 48 && i <= 57){ // any numeral
            if(b)System.out.println("num");
            return PreLudemeType.NUMBER;
        }else if(i == 60){ // option: '<'
            if(b)System.out.println("opt");
            return PreLudemeType.OPTION;
        }else { //DEFAULT
            return PreLudemeType.ERR;
        }
    }

    public static LudemeType reclassify(String s, PreLudemeType preType) {
        // TODO: Implement
        // ludeme : '(' = 40
        // lowercase range:  a - z = 97 - 122
        switch (preType) {
            case LUDEME:
                return LudemeType.ERR;
            case COLLECTION:
                return LudemeType.ERR;
            case STRING:
                return LudemeType.ERR;
            case DEFINE_PARAMETER:
                return LudemeType.ERR;
            case LOWERCASE:
                return LudemeType.ERR;
            case UPPERCASE:
                return LudemeType.ERR;
            case NUMBER:
                return LudemeType.ERR;
            case OPTION:
                return LudemeType.ERR;
            default:
                return LudemeType.ERR;
        }
    }
}
