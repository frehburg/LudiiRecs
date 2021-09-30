package Parsing;

import Tree.Tree;
import Utils.FileUtils;
import Utils.PrintUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    public static Tree parse(File f) throws FileNotFoundException {
        Tree root = new Tree(null, "root", false, LudemeType.ROOT);
        String contents = FileUtils.getContents(f);
        System.out.println(contents);
        contents = PrintUtils.insertSpaceAroundBrackets(contents);
        System.out.println("HI"+contents);
        String[] ludemes = splitIntoSubLudemes(contents);
        Tree[] t = null;
        // run through the whole string, need to make tree
        for(int i = 0; i < ludemes.length; i++) {
            //each ludeme becomes a tree, that is a child to the current tree
            String cur = ludemes[i];
            root.addChild(buildTree(cur));
        }
        return root;
    }

    public static Tree buildTree(String contents) {
        Tree t = null;
        if(contents == "")
            return null;
        LudemeType type = classify(contents.charAt(0));
        if(type == LudemeType.LUDEME) {
            //removes () around ludeme
            contents = contents.substring(1,contents.length());

            String[] sub = splitIntoSubLudemes2(contents);
            System.out.println("-----------------------------------------");
            System.out.println(contents);
            System.out.println(" ");
            PrintUtils.printCollection(Arrays.asList(sub));
            System.out.println("---");
            List<Tree> children = new ArrayList<Tree>();
            for(int i = 1; i < sub.length; i++) {
                children.add(buildTree(sub[i]));
            }
            t = new Tree(children, sub[0], false, type);

        } else if(type == LudemeType.COLLECTION) {
            //removes {} around collection
            contents = contents.substring(1,contents.length() - 1);

            String[] sub = splitIntoSubLudemes2(contents);

            List<Tree> children = new ArrayList<Tree>();
            for(int i = 0; i < sub.length; i++) {
                children.add(buildTree(sub[i]));
            }
            t = new Tree(children, "collection", false, type);
        }
        else if(type == LudemeType.KEYWORD || type == LudemeType.NUMBER
                || type == LudemeType.PARAMETER || type == LudemeType.STRING) {
            //no subdivision necessary
            t = new Tree(null, contents, true, type);
        }
        return t;
    }

    /**
     * Only works if all subs are ludemes with () around them
     * @param contents
     * @return
     */
    public static String[] splitIntoSubLudemes(String contents) {
        //step 1: search for first ')'
        int nestingLevel = -1; // root is not actually part of the tree
        int startNestingLevel = 0;
        List<String> ludemes = new ArrayList<>();
        int start = -1;
        int end = -1;
        boolean searching = false;
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
                        end = -1;
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
    public static String[] splitIntoSubLudemes2(String contents) {
        boolean debug = false;

        //step 1: search for first ')'
        List<String> ludemes = new ArrayList<>();
        int i = 0;
        int start = 0;
        int end = -1;
        int nestingLevel = -1, startNestingLevel = 0;
        boolean foundFirst = true;
        boolean foundSecond = false;
        LudemeType foundType = LudemeType.KEYWORD;
        while(i < contents.length()) {
            char cur = contents.charAt(i);
            //need to find fist space
            if(!foundFirst) {
                //from first character we can classify it into a type
                foundType = classify(cur);
                if(foundType == LudemeType.ROOT) {
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
                    case PARAMETER:
                    case KEYWORD:
                    case NUMBER:
                        //need to find space
                        if(cur == ' ') {
                            //found a complete string
                            foundSecond = true;
                            end =  i;
                        }
                        break;

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

    public static LudemeType classify(char c) {
        switch (c) {
            case '(': return LudemeType.LUDEME;
            case '{': return LudemeType.COLLECTION;
            case '"': return LudemeType.STRING;
            case '#': return LudemeType.PARAMETER;
            case '0':
            case '1':
            case '2':
            case '3':
            case '5':
            case '6':
            case '9':
            case '8':
            case '7':
            case '4':
                return LudemeType.NUMBER;
            case 'a':
            case 'b':
            case 'd':
            case 'e':
            case 'g':
            case 'j':
            case 'k':
            case 'm':
            case 'n':
            case 'p':
            case 'r':
            case 's':
            case 'c':
            case 'f':
            case 'h':
            case 'i':
            case 'l':
            case 'o':
            case 'q':
            case 't':
            case 'u':
            case 'w':
            case 'x':
            case 'y':
            case 'z':
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
            case 'Z':
                return LudemeType.KEYWORD;
            default: return  LudemeType.ROOT;
        }
    }
}
