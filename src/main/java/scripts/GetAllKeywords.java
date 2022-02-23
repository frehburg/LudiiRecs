package main.java.scripts;

import main.java.Utils.FileUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class GetAllKeywords {
    public static void main(String[] args) {
        getAllKeywords();
    }
    public static void getAllKeywords() {
        Scanner sc = FileUtils.readFile("src\\main\\resources\\ludii-grammar-1.2.11.txt");
        ArrayList<String> keywords = new ArrayList<>();
        String line = "";
        while(sc.hasNextLine()) {
            String next = sc.nextLine();
            if(next.length() > 1){
                if(next.charAt(0) == '<') {
                    if(line == "") {
                        line = next;
                        continue;
                    }
                    //parse into keywords
                    //System.out.println(line);
                    int lastIndexOf = line.lastIndexOf("::=");
                    line = line.substring(lastIndexOf + 3);
                    System.out.println(line);

                    String[] split = line.substring(1).split(" \\| ");
                    System.out.println(Arrays.toString(split));
                    //go through all implementations
                    for(int i = 0; i < split.length; i++) {
                        String s = split[i];
                        if(s.charAt(0) == '(') {
                            String t = "";
                            int j = 0;
                            while(j < s.length() && s.charAt(j) != ' ' && s.charAt(j) != ')') {
                                j++;
                            }
                            t = s.substring(1, j);
                            if(!keywords.contains(t)) {
                                keywords.add(t);
                            }
                            System.out.println(t);
                        } else if(Character.isLetter(s.charAt(0)) && Character.isUpperCase(s.charAt(0))) {
                            String t = "";
                            int j = 0;
                            while(j < s.length() && s.charAt(j) != ' ' && s.charAt(j) != ')') {
                                j++;
                            }
                            t = s.substring(0, j);
                            if(!keywords.contains(t)) {
                                keywords.add(t);
                            }
                            System.out.println(t);
                        }
                    }
                    //end parse into keywords
                    line = next;
                } else if(next.startsWith("               ")) {
                    line += next.substring("               ".length());
                }
            } else {
                continue;
            }
        }
        sc.close();
        //sort
        keywords.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.charAt(0) - o2.charAt(0);
            }
        });
        System.out.println("-------------------------------------------------------------");
        for (String s : keywords) {
            System.out.println(s);
        }
        //
        FileWriter fw = FileUtils.writeFile("C:\\Users\\filre\\OneDrive\\Documents\\IntelliJ\\LudiiRecs\\src\\main\\resources\\allKeywords.txt");
        for (String s : keywords) {
            try {
                fw.write(s+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
