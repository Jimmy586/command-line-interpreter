package starter;

import javafx.scene.input.KeyCode;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ConsoleEvents implements KeyListener {
    private JTextArea console;

    private int start_pos = 0;
    private int end_pos = 0;

    public ConsoleEvents(JTextArea console){
        this.console = console;
        console.insert("This console application is Developed based on Assignment: 1 for Operating System 1 subject in Faculty of Computers and Information, Cairo University\n\n", console.getText().length());
        console.insert("Developers: \tMd Maruf Billah\tID: 20150399\n", console.getText().length());
        console.insert("\t\t\t\tRIVOARISON Heritiana\tID: 20160354\n", console.getText().length());
        console.insert("\t\t\t\tVATOSOA Mananjara Jimmy Clariel\tID: 20160355\n\n", console.getText().length());
        console.insert("For available command, just type help\nType Command: ", console.getText().length());

        start_pos = console.getText().length();
        end_pos = console.getText().length();
        console.setCaretPosition(console.getText().length());
    }

    @Override
    public void keyTyped(java.awt.event.KeyEvent e) {
        end_pos = console.getText().length();
    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        console.setCaretPosition(console.getText().length());

        //Enter Key
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            String command = console.getText().substring(start_pos, end_pos);
            new Parser(console, command);

            console.insert("\n", console.getText().length());
            console.insert("Type Command: ", console.getText().length());
            start_pos = console.getText().length();
            end_pos = console.getText().length();
            e.consume();
        }
        //Back Space Key
        if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
            if(end_pos <= start_pos){
                e.consume();
            }
        }
    }

    @Override
    public void keyReleased(java.awt.event.KeyEvent e) {
        end_pos = console.getText().length();
    }
}
