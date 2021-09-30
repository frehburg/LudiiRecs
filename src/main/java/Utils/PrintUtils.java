package Utils;

import java.util.Arrays;
import java.util.Collection;

public class PrintUtils {
    public static void printCollection(Collection c) {
        c.forEach(i -> System.out.println(i + ","));
    }

    public static <T> void printCollection(T[] a) {
        Collection c = Arrays.asList(a);
        c.forEach(i -> System.out.println(i + ","));
    }

    /**
     * This method inserts a space left of '(' and right of ')' if it doesn´t exist yet
     * @param s
     * @param start
     * @return
     */
    public static String insertSpaceAroundBrackets(String s, int start) {
        if(s.length() > 1) {
            for (int i = 2; i < s.length(); i++) {
                char prev = s.charAt(i - 2);
                char cur = s.charAt(i - 1);
                char next = s.charAt(i);

                if(cur == '(' || cur == '{') {
                    if(prev != ' '){
                        s = s.substring(0, (i-1)) + " " + s.substring(i - 1, s.length());
                        return insertSpaceAroundBrackets(s,i + 1);
                    }
                }
                if(cur == ')' || cur == '}') {
                   if(next != ' ') {
                        s = s.substring(0, (i)) + " " + s.substring(i, s.length());
                        return insertSpaceAroundBrackets(s,i + 2);
                    }
                }
            }
        }
        return s;
    }

    public static String insertSpaceAroundBrackets(String s) {
        return insertSpaceAroundBrackets(s, 0);
    }

    /**
     * Behaves similarily to String.split(String regex) with regex = " ",
     * but does not split ludemes apart
     * @param s
     * @return
     */
    public static String[] splitIntoParameters(String s) {
        return null;
    }
}
