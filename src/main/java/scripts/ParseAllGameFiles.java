package main.java.scripts;
import main.java.interfaces.iTree;
import main.java.parser.Parser;

import java.util.ArrayList;

public class ParseAllGameFiles {
    public static void main(String[] args) {
        ArrayList<String> locations = ReadAllGameFiles.readGameLocations("src/main/resources/locations.txt");
        ArrayList<iTree> allGames = new ArrayList<>();
        int i = 0;
        for(String location : locations) {
            System.out.println("Number: " + i);
            i++;
            System.out.println("Name: " + location);
            System.out.println("------------------");
            try {
                iTree t = Parser.getTree(location);
                allGames.add(t);
            } catch (StringIndexOutOfBoundsException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        double success = (double)(allGames.size()) / (double)(locations.size());
        double failure = 1.0 - success;
        System.out.println("Success rate: " + success);
    }
}
