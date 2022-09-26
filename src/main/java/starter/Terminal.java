package starter;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Terminal {
    private JTextArea console;
    private static String DIR = System.getProperty("user.home");
    public static String output_file;

    public Terminal(JTextArea console){
        this.console = console;
        this.output_file = new String();
    }
    public void clear(){
        console.setText("");
    }

    public void ls(){
        File f = new File(this.DIR);
        ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));

        String name_list = new String();
        for(String name : names){
            name_list += "\n"+name;
        }
        if(!output_file.equals(new String())){
            Utility.write_to_file(name_list, Utility.resolve_path(output_file, this.DIR), this.console);
        }
        else {
            console.insert("\n" + name_list, console.getText().length());
        }
    }

    public void cd(String path) {
        if(path.equals(new String())){
            this.DIR = System.getProperty("user.home");
        }
        else{
            path = Utility.resolve_path(path, this.DIR);
            try {
                Path p = Paths.get(path);
                if(Files.isDirectory(p)){
                    this.DIR = String.valueOf(p.toRealPath());
                }
                else{
                    console.insert("\nDirectory is not exists!", console.getText().length());
                }
            }catch(Exception e){
                console.insert("\nException: "+e, console.getText().length());
            }
        }
    }

    public void cp(String sourcePath, String destinationPath) {
        try {
            Path FROM  = Paths.get(Utility.resolve_path(sourcePath, this.DIR));
            Path TO = Paths.get(Utility.resolve_path(destinationPath, this.DIR));
            //overwrite existing files if exist
            CopyOption[] options = new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING,
            };
            Files.copy(FROM, TO, options);
        } catch (IOException e) {
            console.insert(e.getMessage(), console.getText().length());
        }
    }

    public void mv(String sourcePath, String destinationPath) {
        try {
            Path FROM  = Paths.get(Utility.resolve_path(sourcePath, this.DIR));
            Path TO = Paths.get(Utility.resolve_path(destinationPath, this.DIR));
            //overwrite existing files if exist
            CopyOption[] options = new CopyOption[]{
                    StandardCopyOption.REPLACE_EXISTING,
            };
            Files.move(FROM, TO, options);
        } catch (IOException e) {
            console.insert(e.getMessage(), console.getText().length());
        }
    }

    public void rm(String sourcePath) {
        try{
            Path FROM  = Paths.get(Utility.resolve_path(sourcePath, this.DIR));
            Files.delete(FROM);
        } catch (IOException e) {
            console.insert(e.getMessage(), console.getText().length());
        }
    }

    public void mkdir(String name) {
        try {
            String path = Utility.resolve_path(name, this.DIR);
            boolean dir = new File(path).mkdir();
        }
        catch(Exception e){
            console.insert(e.getMessage(), console.getText().length());
        }
    }
    public void rmdir(String sourcePath) {
        try{
            Path FROM  = Paths.get(Utility.resolve_path(sourcePath, this.DIR));
            Files.delete(FROM);
        } catch (IOException e) {
            console.insert(e.getMessage(), console.getText().length());
        }
    }

    public void cat(String path){
        try{
            Path PATH  = Paths.get(Utility.resolve_path(path, this.DIR));
            String contents = new String(Files.readAllBytes(PATH));
            if(!output_file.equals(new String())){
                Utility.write_to_file(contents, Utility.resolve_path(output_file, this.DIR), this.console);
            }
            else {
                console.insert("\n" + contents, console.getText().length());
            }
        } catch (IOException e) {
            console.insert(e.getMessage(), console.getText().length());
        }
    }

    public void pwd(){
        console.insert("\n"+this.DIR, console.getText().length());
    }

    public void date(){
        String date = LocalDateTime.now().toString();
        console.insert("\n"+date, console.getText().length());
    }

    public void exit(){
        System.exit(0);
    }

    public void help(){
        String help_text = new String();

        help_text += "\nclear\t - No arguments - \t: Clears Terminal window.";
        help_text += "\nls\t - No arguments - \t: Lists all files and folders in current directory.";
        help_text += "\ncd\t - 1:PATH(optional) - \t: Changes current directory. Home user's home directory for no argument.";
        help_text += "\ncp\t - 1:Source Path 2: Destination Path - \t: Copies file.";
        help_text += "\nmv\t - 1:Source Path 2: Destination Path - \t: Moves file";
        help_text += "\nrm\t - 1:File Name - \t: Renames file.";
        help_text += "\nmkdir\t - 1:PATH - \t: Creates folder on specified directory";
        help_text += "\nrmdir\t - 1:Folder Name - \t: Renames folder.";
        help_text += "\ncat\t - 1:File PATH - \t: Outputs file contents in Terminal.";
        help_text += "\nargs\t - 1: Command - \t: Prints argument list for specified command";
        help_text += "\npwd\t - No arguments - \t: Prints current directory.";
        help_text += "\ndate\t - No arguments - \t: Prints current date/time.";
        help_text += "\neixt\t - No arguments - \t: Exits the Terminal.";
        help_text += "\nhelp\t - No arguments - \t: Prints Help text for Terminal user.";

        if(!output_file.equals(new String())){
            Utility.write_to_file(help_text, Utility.resolve_path(output_file, this.DIR), this.console);
        }
        else {
            console.insert("\n" + help_text, console.getText().length());
        }
    }

    public void args(String command){
        switch (command){
            case "clear" : console.insert("\n No arguments", console.getText().length()); break;
            case "ls" : console.insert("\n No arguments", console.getText().length()); break;
            case "cd" : console.insert("\n 1: PATH", console.getText().length()); break;
            case "cp" : console.insert("\n 1: Source PATH, 2: Destination PATH", console.getText().length()); break;
            case "mv" : console.insert("\n 1: Source PATH, 2: Destination PATH", console.getText().length()); break;
            case "rm" : console.insert("\n 1: File Name", console.getText().length()); break;
            case "mkdir" : console.insert("\n 1: Directory Name", console.getText().length()); break;
            case "rmdir" : console.insert("\n 1: Directory Name", console.getText().length()); break;
            case "cat" : console.insert("\n 1: File PATH", console.getText().length()); break;
            case "args" : console.insert("\n 1: Command", console.getText().length()); break;
            case "pwd" : console.insert("\n 1: No arguments", console.getText().length()); break;
            case "date" : console.insert("\n 1: No arguments", console.getText().length()); break;
            case "exit" : console.insert("\n 1: No arguments", console.getText().length()); break;
            case "help" : console.insert("\n 1: No arguments", console.getText().length()); break;
            default: console.insert("\n Unrecognized command!", console.getText().length()); break;
        }
    }
}
