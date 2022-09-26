package starter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    private String cmd;
    private ArrayList<String> args;
    private JTextArea console;
    private String output_file;

    public Parser(JTextArea console, String command){
        cmd = new String();
        args = new ArrayList<String>();
        this.console = console;
        this.output_file = new String();

        parse_command(command);
    }

    private void parse_command(String commands){
        String[] cmds = commands.split(" \\| ");

        for(String cmd_string : cmds){
            String[] command_rdr = cmd_string.split(">>");
            if(command_rdr.length > 1){
                output_file = command_rdr[1];
            }
            if(command_validator(command_rdr[0])){
                command_detector();
            }
        }
    }

    private boolean command_validator(String command){
        ArrayList<String> cmd_args = new ArrayList<String>(Arrays.asList(command.split(" ")));

        //Interpreting quoted arguments.
        boolean unstable = false;
        String arg = new String();
        for(int i=0; i<cmd_args.size(); i++){
            if(cmd_args.get(i).startsWith("\"") && cmd_args.get(i).endsWith("\"")){
                cmd_args.set(i, cmd_args.get(i).substring(1, cmd_args.get(i).length()-1));
            }
            else if(cmd_args.get(i).startsWith("\"") && !unstable){
                arg = cmd_args.get(i).substring(1, cmd_args.get(i).length());
                cmd_args.set(i, new String());
                unstable = !unstable;
            }
            else if(cmd_args.get(i).endsWith("\"") && unstable){
                arg += " " + cmd_args.get(i).substring(0, cmd_args.get(i).length()-1);
                cmd_args.set(i, arg);
                unstable = !unstable;
            }
            else if(unstable){
                arg += " " + cmd_args.get(i);
                cmd_args.set(i, new String());
            }
        }

        ArrayList<String> validated = Utility.remove_empty(cmd_args.toArray(new String[0]));

        if(unstable || validated.isEmpty()){
            console.insert("\nCommand syntax error!", console.getText().length());
            return false;
        }
        else{
            this.cmd = validated.get(0);
            for(int i=1; i<validated.size(); i++){
                this.args.add(validated.get(i));
            }
            return true;
        }
    }

    private void command_detector(){
        Terminal terminal = new Terminal(this.console);
        terminal.output_file = this.output_file;
        if(this.cmd.equals("clear") && this.args.isEmpty()){
            terminal.clear();
            clear_cmd_queue();
        }
        else if(this.cmd.equals("ls") && this.args.isEmpty()){
            terminal.ls();
            clear_cmd_queue();
        }
        else if(this.cmd.equals("cd") && this.args.isEmpty()){
            terminal.cd("");
            clear_cmd_queue();
        }
        else if(this.cmd.equals("cd") && this.args.size() == 1){
            terminal.cd(this.args.get(0));
            clear_cmd_queue();
        }
        else if(this.cmd.equals("cp") && this.args.size() == 2){
            terminal.cp(this.args.get(0), this.args.get(1));
            clear_cmd_queue();
        }
        else if(this.cmd.equals("mv") && this.args.size() == 2){
            terminal.mv(this.args.get(0), this.args.get(1));
            clear_cmd_queue();
        }
        else if(this.cmd.equals("rm") && this.args.size() == 1){
            terminal.rm(this.args.get(0));
            clear_cmd_queue();
        }
        else if(this.cmd.equals("mkdir") && this.args.size() == 1){
            terminal.mkdir(this.args.get(0));
            clear_cmd_queue();
        }
        else if(this.cmd.equals("rmdir") && this.args.size() == 1){
            terminal.rmdir(this.args.get(0));
            clear_cmd_queue();
        }
        else if(this.cmd.equals("cat") && this.args.size() == 1){
            terminal.cat(this.args.get(0));
            clear_cmd_queue();
        }
        else if(this.cmd.equals("pwd") && this.args.size() == 0){
            terminal.pwd();
            clear_cmd_queue();
        }
        else if(this.cmd.equals("args") && this.args.size() == 1){
            terminal.args(this.args.get(0));
            clear_cmd_queue();
        }
        else if(this.cmd.equals("date") && this.args.size() == 0){
            terminal.date();
            clear_cmd_queue();
        }
        else if(this.cmd.equals("exit") && this.args.size() == 0){
            terminal.exit();
            clear_cmd_queue();
        }
        else if(this.cmd.equals("help") && this.args.size() == 0){
            terminal.help();
            clear_cmd_queue();
        }
        else{
            console.insert("\nUnrecognized command!", console.getText().length());
            clear_cmd_queue();
        }
    }

    private void clear_cmd_queue(){
        this.cmd = new String();
        this.args.clear();
    }
}
