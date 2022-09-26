package starter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI {
    private JTextArea console;
    private JPanel main_panel;
    private JFrame mainFrame;

    public GUI(){
        mainFrame = new JFrame("Operating System 1 - Assignment: 1");
        mainFrame.setSize(800, 800);
        mainFrame.setContentPane(this.main_panel);

        console.addKeyListener(new ConsoleEvents(console));

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        mainFrame.setVisible(true);
    }
}
