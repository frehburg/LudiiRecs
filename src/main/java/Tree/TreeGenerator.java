package Tree;

import Parsing.Parser;
import Utils.FileUtils;
import Utils.PrintUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TreeGenerator {
    public static void main(String[] args) {
        try {
            File ticTacToe = new File("C:\\Users\\filre\\OneDrive\\Documents\\IntelliJ\\LudiiRecs\\res\\Tic-Tac-Toe.lud");
            String[] a = Parser.parse(ticTacToe);
            System.out.println("-------------------------------------------------");
            PrintUtils.printCollection(Arrays.asList(a));
            System.out.println("-------------------------------------------------");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    //-------------------------------------------------------------
    private final String ABSOLUTE_LUDEME_PATH;
    private final File[] dotLudFiles;

    public TreeGenerator() {
        File directory = new File("../");
        System.out.println(directory.getAbsolutePath());
        ABSOLUTE_LUDEME_PATH = directory.getAbsolutePath().substring(0,directory.getAbsolutePath().length()-3)+"\\res\\Ludii\\lud\\";

        //creates a list of all directories containing .lud files
        dotLudFiles = searchDotLudFiles();
    }

    /**
     * Creates an array of File where each File is a .lud file
     * @return File Array of .lud files
     */
    public File[] searchDotLudFiles() {
        List<File> files = new ArrayList<>();
        File[] dotLudDirectories = searchDotLudDirectories();
        Arrays.asList(dotLudDirectories).forEach(directory ->{
            File[] subDirs = directory.listFiles();
            Arrays.asList(subDirs).forEach(file -> files.add(file));
        });
        return files.toArray(new File[0]);
    }

    /**
     * Creates an array of Files containing all directories in the res/lud/ directory that
     * directly contain .lud files and returns it
     * @return Array of directories directly containing .lud files
     */
    public File[] searchDotLudDirectories() {
        Queue<File> directories = new PriorityQueue<>();
        File directory = new File(ABSOLUTE_LUDEME_PATH);
        directories.add(directory);
        List<File> ludemeFound = new ArrayList<>();
        while(!directories.isEmpty()) {
            File cur = directories.remove();
            File[] subDirs = cur.listFiles();
            System.out.println(subDirs[0].getName() + " " + subDirs[0].getPath());
            if(FileUtils.isFileDotLud(subDirs[0].getName())) {
                ludemeFound.add(cur);
            } else {
                Arrays.asList(subDirs).forEach(file -> directories.add(file));
            }
        }
        return ludemeFound.toArray(new File[0]);
    }
}
