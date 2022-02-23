package main.java.scripts;

import main.java.Utils.FileUtils;

import java.util.*;

public class ParseLudiiGrammar {

    public static void main(String[] args) {
        parseLudiiGrammar("src\\main\\resources\\ludii-grammar-1.2.11.txt");
    }

    /**
     * This method gets the grammar file,
     * creates a hashtable of possible constructors for each one
     * writes it into a database table
     * returns the location of the database file
     * @param grammarfile
     * @return
     */
    public static String parseLudiiGrammar(String grammarfile) {
        HashMap<String, List<String>> grammar = (HashMap<String, List<String>>) createHashTable(grammarfile);
        String database = createDatabase(grammar);
        return database;
    }

    /**
     * This method reads the grammar file and converts it into a hashtable of
     * key: keyword
     * value: List of possible constructors
     * @param grammarfile
     * @return
     */
    public static Map<String, List<String>> createHashTable(String grammarfile) {
        Scanner sc = FileUtils.readFile(grammarfile);
        Map<String, List<String>> constructors = new HashMap<>();
        String line = "";
        while(sc.hasNextLine()) {
            String next = sc.nextLine();
            if(next.length() > 1){
                //new definition
                if(next.charAt(0) == '<') {
                    if(line == "") {
                        line = next;
                        continue;
                    }
                    //parse into keywords
                    int lastIndexOf = line.lastIndexOf("::=");
                    String keyword = line.substring(1,lastIndexOf - 2);
                    if(keyword.contains(">")) {
                        keyword = keyword.substring(0,keyword.lastIndexOf(">"));
                    }
                    while(keyword.contains(".")) {
                        keyword = keyword.substring(0,keyword.lastIndexOf("."));
                    }
                    String constructor = line.substring(lastIndexOf + 4);
                    System.out.println(keyword+" ==> "+constructor);
                    if(constructors.containsKey(keyword)) {
                        ArrayList<String> v = (ArrayList<String>) constructors.get(keyword);
                        v.add(constructor);
                        constructors.put(keyword,v);
                    } else {
                        constructors.put(keyword,new ArrayList<>(Arrays.asList(new String[]{constructor})));
                    }
                    line = next;
                } else if(next.startsWith("               ")) {
                    line += next.substring("               ".length());
                }
            } else {
                continue;
            }
        }
        sc.close();
        return null;
    }

    /**
     * This method pastes all the information of the hashtable acquired in createHashTable into a database,
     * and returns the location of the database file.
     * @param grammar
     * @return
     */
    public static String createDatabase(Map<String, List<String>> grammar) {
        return null;
    }
}
