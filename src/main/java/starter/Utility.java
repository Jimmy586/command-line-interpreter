package starter;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Utility {
    public static ArrayList<String> remove_empty(String[] arr){
        ArrayList<String> tmp = new ArrayList<String>();
        for(int i=0; i<arr.length; i++){
            if(arr[i] == new String() || arr[i] == " " || arr[i].isEmpty()){
                continue;
            }
            tmp.add(arr[i]);
        }
        return tmp;
    }

    public static String resolve_path(String path, String DIR){
        Pattern pattern1 = Pattern.compile("^[a-zA-Z]+://"); //matching with drive letter

        if(!pattern1.matcher(path).lookingAt()){
            path = DIR+"/"+path;
        }
        return path;
    }

    public static void write_to_file(String text, String output_file, JTextArea console){
        try {
            Files.write(Paths.get(output_file), text.getBytes());
        } catch (IOException e) {
            console.insert("\n"+e.getMessage(), console.getText().length());
        }
    }
}
