package main.java.parser;

import main.java.Utils.FileUtils;
import main.java.Utils.PrintUtils;
import main.java.VisualEditor.EditorView.EditorFrame;
import main.java.domain.NodeType;
import main.java.domain.RecNode;
import main.java.domain.Tree;
import main.java.domain.Tuple;
import main.java.interfaces.iRecNode;
import main.java.interfaces.iTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is responsible for parsing a .lud file into an iTree made up of iRecNodes.
 * @author Jan-Filip Rehburg
 */
public class Parser {
    public static boolean verbose = false;

    /**
     * This is the main method of this class. It receives the name of a file. If the file is a
     * .lud file, it tries to read it in. If this does not succeed an Exception is thrown.
     * If it succeeds, the file gets read in and preprocessed by FileUtils.
     * Then its contents are parsed into an iTree object.
     * To this object postprocessing is applied before it is returned.
     * @param fileLocation
     * @return iTree parsed game tree
     */
    public static iTree getTree(String fileLocation) throws Exception {
        File f = new File(fileLocation);
        String gameDescription;
        System.out.println(fileLocation + " " + FileUtils.isFileDotLud(fileLocation));
        if(FileUtils.isFileDotLud(fileLocation)) {
            gameDescription = FileUtils.getContents(f); //read in and preprocessing
            gameDescription = preprocessing(gameDescription);
            iTree t = parse(gameDescription); //parsing
            t = postProcessing(t); //postprocessing
            return t;
        } else {
            throw new Exception("The file could not be parsed. Make sure that you only try to parse .lud files!");
        }
    }

    private static String preprocessing(String gameDescription) {
        gameDescription = gameDescription.replaceAll("\\*",""); //stars for preferred options are removed
        gameDescription = gameDescription.replaceAll(":", ": "); //optional argument labes are spaced out
        gameDescription = gameDescription.replaceAll("  ", " "); //double spaces removed
        gameDescription = gameDescription.replaceAll("  ", " ");
        gameDescription = gameDescription.replaceAll("\\\\\"", "'"); //replaces \" with single quotation marks (only for metadata
        gameDescription = "(root "+ gameDescription + ")"; //does not parse otherwise
        return gameDescription;
    }

    /**
     * This method takes in a String gameDescription and parses it into an iTree object. It calls
     * recursiveParse(), which contains further parsing logic and splitIntoSubLudemes with the
     * core of the logic.
     * @param gameDescription
     * @return iTree parsed game tree
     */
    private static iTree parse(String gameDescription) {
        //create root of the tree, to which all other nodes are children
        iRecNode root = new RecNode("root");
        //list of necessary information to create children of the node
        ArrayList<Tuple<String,ArrayList<String>, LudemeType>> subLudemes = splitIntoSubLudemes(gameDescription);
        for(Tuple<String,ArrayList<String>, LudemeType> t : subLudemes) {
            iRecNode c = root.addChild(t.getR());
            recursiveParse(c, t.getS());
        }
        iTree t = new Tree(root);
        return t;
    }

    /**
     * This method is recursively called to assemble the parsed game tree in all of its height.
     * It takes in a node n and adds all of its children to it. Then it is recursively called for
     * all of their children. The anchor of recursion is implicit: once a ludeme does not have any
     * children anymore the corresponding subludemes list will be empty. The recursive call is
     * called on all items of the list. If the list is empty there are no calls.
     * Thus, it always stops.
     *
     * @param n
     * @param subludemes
     */
    private static void recursiveParse(iRecNode n, ArrayList<String> subludemes) {
        if(verbose)System.out.println("-------------------");
        if(verbose)System.out.println(n.getKeyword());
        //goes through all subludemes and splits them into their subludemes respectively
        for(String s : subludemes) {
            ArrayList<Tuple<String,ArrayList<String>, LudemeType>> tupleList = splitIntoSubLudemes(s);
            if(verbose)System.out.println(s);
            if(verbose)System.out.println(tupleList);
            //for each of the initial subludemes a new node is created,
            //and as its children the subsubludemes are passed on into a recursive call
            for(Tuple<String,ArrayList<String>, LudemeType> t : tupleList) {
                //R is the first parameter in the Tuple
                //S is the second parameter in the Tuple
                iRecNode c = n.addChild(t.getR());
                recursiveParse(c, t.getS());
            }
        }

    }


    /**
     * This method holds the core of the parsing logic. It gets used by parse() and recursiveParse()
     * and uses findItemsAtEqualLevel(), findAtSameLevel(), findEndOfWord(), as well as preClassify().
     * This method loops over the contents String. At all times it has a character cur, that corresponds
     * to the character at the currently considered position in the string. (contents.charAt(i) )
     * In the beginning, this is the first character of the contents string.
     * It gets classified using the method preClassify(). Depending on the classification, different courses of action
     * are taken.
     * 1. If the classification is on of the following:
     *  a) PRE_OPTION,
     *  b) PRE_UPPERCASE,
     *  c) PRE_LOWERCASE,
     *  d) PRE_DEFINE_PARAMETER or
     *  e) PRE_NUMBER,
     * then we just need to find the end of this ludeme/ word. We get the index of its last character from the method
     * findEndOfWord(). The ludeme/ its keyword is simply the string in between our cur character and the character at
     * this position. It gets added to the subludemes list.
     *
     * 2. PRE_STRING
     * We first find the closing " using the method findAtSameLevel with parameter searched = '"'. It is assumed that
     * Strings do not contain Strings. If the second quotation mark is followed by two points "..", then it is a range
     * and we also include the uper limit of the range. e.g.: "A1".."B12". Again, this is the whole ludeme and we add it
     * to the subludemes list.
     * 3.
     *  a) PRE_COLLECTION
     *  b) PRE_LUDEME
     * These two classifications lead down a very similar road, bu differences will be pointed out.
     * The keyword of the collection is "{}". The keyword of the ludeme is the word/ sign that follows directly after the
     * opening bracket "(". The keyword is set.
     * Then, using findAtSameLevel() the end of the ludeme/ collection is determined. Everything in between is passed on
     * to findItemsAtEqualLevel(), which determines all items of the collection/ parameters of the ludeme.
     * It returns a list. All items of this list are added to the subludemes list.
     *
     * Note that the necessary information to create a node, its keyword, subludemes and ludeme type, are stored in a
     * specially created Tuple class. These tuples are accumulated as a list and returned to be assembled into nodes.
     *
     * After one ludeme has ended, it will parse on, until it reaches the end of string contents.
     *
     * For a visualization of the described process, please visit:
     * https://github.com/frehburg/LudiiRecs/blob/main/src/main/resources/pictures/Parsing/parsing_abstract_machine.png
     * @param contents
     * @return
     */
    public static ArrayList<Tuple<String,ArrayList<String>, LudemeType>> splitIntoSubLudemes(String contents) {
        if(contents.charAt(contents.length()-1) == ' ')
            contents = contents.substring(0,contents.length()-1);
        if(contents == "")
            return new ArrayList<>();
        int i = 0;
        int previous = i;
        ArrayList<Tuple<String,ArrayList<String>, LudemeType>> tupleArrayList = new ArrayList<>();
        int l = contents.length();
        //do while to accommodate ludemes of length 1
        do {
            String tmp = contents.substring(i);
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
                    keyword = contents.substring(previous,end + 1); // to show closing quotation mark
                    break;
                case PRE_COLLECTION:
                    end = findAtSameLevel(contents,'}', previous);
                    keyword = "{}";
                    subludemes = findItemsAtEqualLevel(contents.substring(previous + 1,end));
                    break;
                case PRE_LUDEME:
                    end = findAtSameLevel(contents,')', previous);
                    String tmp1 = contents.substring(i);
                    System.out.println(contents.substring(i));
                    keyword = contents.substring(i + 1,previous + findEndOfWord(contents.substring(previous)));
                    keyword = keyword.replaceAll("\\(","");
                    subludemes = findItemsAtEqualLevel(contents.substring(previous + 1,end));
                    subludemes.remove(0);
                    break;

            }
            i = end;
            previous = end + 1;

            //add all, if it is not ERR
            if(foundType != LudemeType.ERR) {
                Tuple<String, ArrayList<String>, LudemeType> t = new Tuple(keyword, subludemes, foundType);
                tupleArrayList.add(t);
            }
        }while(i < l - 1);
        return tupleArrayList;
    }

    /**
     * TODO write comment
     * @param contents
     * @return
     */
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
            } //if we have a string, then we need to jump ahead, to the closing quotationmark of the string
            //because there can be spaces in strings and they don´t affect the split
            else if(level == 0 && cur == '"') {
                if(i > 0 && contents.charAt(i) == '\\') {
                    //skip this one, because it is in another string
                } else {
                    int delta = findAtSameLevel(contents.substring(i), '"', 0);
                    i += delta;
                    cur = contents.charAt(i);
                    String tmp = contents.substring(previous, i + 1);
                    list.add(contents.substring(previous, i + 1));
                    previous = i + 1;
                }
            }

        }
        return list;
    }

    /**
     * TODO write comment
     * @param contents
     * @param searched
     * @param posOfFirst
     * @return
     */
    private static int findAtSameLevel(String contents, char searched, int posOfFirst) {
        char first = contents.charAt(posOfFirst);
        int level = 0;
        for(int i = posOfFirst + 1; i < contents.length(); i++) {
            char cur = contents.charAt(i);
            if(cur == searched) {
                if(level == 0) {
                    //had to put this extra case in to identify ranges
                    if((i+1) < contents.length()
                            && (i+2) < contents.length()
                            && searched == '"'
                            && contents.charAt(i+1) == '.'
                            && contents.charAt(i+2) == '.') {
                        i += 3;
                    } else {
                        return i;
                    }
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
     *
     * this end can be signaled by either a ' ', indicating another ludeme is following, or by the closing sign of a
     * ludeme (')') or collection('}').
     * @param contents
     * @return
     */
    private static int findEndOfWord(String contents) {
        //goes through contents until a signal of the word/ ludeme ending is found
        int level = 0;
        for(int i = 0; i < contents.length(); i++) {
            char cur = contents.charAt(i);
            if(cur == ' '
                    || (level == 0 && cur == ')')
                    || (level == 0 && cur == '}')
            ) {//these can be around all of the types
                return i;
            }
            if(cur == '(' || cur == '{')
                level++;
            if(cur == ')' || cur == '}')
                level--;
        }
        return contents.length();
    }

    /**
     * Classifies the ludeme into one of 8 pretypes, which requires only the first character. The classes are designed in
     * a way as to cover all possible ludemes. Later they can be specified into final classifications.
     *
     * @param first
     * @return
     */
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
        // TODO make it into char values, but right now the following doesn´t work
        /*
        if(first == 28) { // (
            return LudemeType.PRE_LUDEME;
        } else if(first == 123) { // {
            return LudemeType.PRE_COLLECTION;
        } else if(first == 22) { // "
            return LudemeType.PRE_STRING;
        } else if(first == 23) { // #
            return LudemeType.PRE_DEFINE_PARAMETER;
        }  else if(first >= 97 && first <= 122) { // lowercase letters
            return LudemeType.PRE_LOWERCASE;
        }  else if((first >= 48 && first <= 57) || first == 46) { // digits or .
            return LudemeType.PRE_NUMBER;
        } else if(first >= 65 && first <= 90) { // uppercase letters
            return LudemeType.PRE_UPPERCASE;
        } else if(first == 60) { // <
            return LudemeType.PRE_OPTION;
        } else {
            return LudemeType.ERR;
        }
        */
    }

    /**
     * TODO write comment
     * @param pre
     * @param ludeme
     * @return
     */
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