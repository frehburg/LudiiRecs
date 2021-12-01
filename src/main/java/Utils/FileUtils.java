package main.java.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileUtils {
    public static boolean isFileDotLud(String fileName) {
        if(!fileName.contains("."))
            return false;
        if(".lud".equals(fileName.substring(fileName.length() - 4, fileName.length())))
            return true;
        return false;
    }

    /**
     * returns the contents of the file as a string where
     * @param f
     * @return
     */
    public static String getContents(File f) throws FileNotFoundException {
        boolean verbose = false;
        String content = "";
        Scanner sc = new Scanner(f);
        while(sc.hasNextLine()) {
            String l = sc.nextLine();
            if(l.contains("//"))
                l = l.substring(0,l.indexOf("//"));
            if(!l.equals("")) {
                //System.out.println((l.length() > 1) + " " + l);
                while (l.length() > 1 && (l.charAt(0) == ' ' && l.charAt(1) == ' '))
                    l = l.substring(1, l.length());
                while (l.length() > 0 && (l.charAt(l.length() - 1) == ' '))
                    l = l.substring(0, l.length() - 1);

                content = content + l;
            }
        }
        int index = content.length();
        int i = 0;
        ArrayList<Integer> occ = new ArrayList<>();
        while (i < content.length()) {
            index = content.indexOf(")(", i);
            i++;
            if(index != -1) {
                i = index + 1;
                occ.add(index);
            }
        }
        for(int j : occ) {
            if(verbose)System.out.println(content.substring(j, j + 2));
            if(verbose)System.out.println(content.substring(0,j + 1));
            if(verbose)System.out.println(content.substring(j + 1));
            content = content.substring(0,j + 1) + " " + content.substring(j + 1);
        }
        return content;
    }
}
