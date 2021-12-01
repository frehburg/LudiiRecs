package main.java.domain;

import main.java.Utils.FileUtils;
import main.java.VisualEditor.EditorView.EditorFrame;
import main.java.interfaces.iTree;
import main.java.parser.LudemeType;
import main.java.parser.Parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        String fileName = "src/main/resources/testAll.lud";
        File f = new File(fileName);
        String contents;
        if(FileUtils.isFileDotLud(fileName)) {
            try {
               contents = FileUtils.getContents(f);

                iTree tree = Parser.getTree(contents);
                new EditorFrame(tree);
            } catch (Exception e) {
                e.printStackTrace();
           }
        }
        //System.out.println(Parser.splitIntoSubLudemes("2"));
        /*String test  = "src/main/resources/test.lud";
        String fileName = test;
        File f = new File(fileName);
        String contents;
        if(FileUtils.isFileDotLud(fileName)) {
            try {
                contents = FileUtils.getContents(f);
                System.out.println(contents);
                ArrayList<Tuple<String,ArrayList<String>, LudemeType>> al = Parser.splitIntoSubLudemes(contents);
                for(Tuple<String,ArrayList<String>, LudemeType> t : al) {
                    System.out.println(t);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }*/

    }
}
