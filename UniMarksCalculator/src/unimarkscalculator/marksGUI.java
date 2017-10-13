/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unimarkscalculator;

import java.awt.*;
import java.awt.Event.*;
import javax.swing.*;

/**
 *
 * @author xxx
 */
public class marksGUI 
{
    private JFrame myFrame = new JFrame("Uni Marks Calculator");
    private JLabel assignment = new JLabel("Assignment");
    private JLabel module = new JLabel("Module");
    private JTextField moduleTitle = new JTextField("Module title");
    private JTextField assignmentTitle = new JTextField("Assignment title");
    
    
    public marksGUI()
    {
        setFrame();
    }
    
    private void setFrame()
    {
        Container contentPane = myFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        Dimension preferredSize = new Dimension(600, 400);
        myFrame.setPreferredSize(preferredSize);
        setMenuBar(myFrame);
        
        myFrame.pack();
        myFrame.setVisible(true);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLocationRelativeTo(null);    // setting the program in the centre of the screen
    }

    private void setMenuBar(JFrame frame)
    {
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        
        // *** FILE menu:
        JMenu fileMenu = new JMenu("File");
        menubar.add(fileMenu);
        
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);
        //exitItem.addActionListener(new ExitHandler());                           // TESTING!
        // -----------------------------------------------
        
        // *** PROFILE menu:
        JMenu profileMenu = new JMenu("Profile");
        menubar.add(profileMenu);
        
        JMenuItem newItem = new JMenuItem("New");
        profileMenu.add(newItem);
        // TODO - add Action Listener

        JMenuItem loadItem = new JMenuItem("Load");
        profileMenu.add(loadItem);
        // TODO - add Action Listener

        JMenuItem editItem = new JMenuItem("Edit");
        profileMenu.add(editItem);
        // TODO - add Action Listener  
 
        JMenuItem deleteItem = new JMenuItem("Delete");
        profileMenu.add(deleteItem);
        // TODO - add Action Listener
        // -----------------------------------------------
        

        
        // *** HELP menu:
        JMenu helpMenu = new JMenu("Help");
        menubar.add(helpMenu);
        
        JMenuItem howToItem = new JMenuItem("How to use");
        helpMenu.add(howToItem);
        //howToItem.addActionListener(new howToUseHandler());
        
        JMenuItem aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);
        //aboutItem.addActionListener(new aboutHandler());
        // -----------------------------------------------
    }
    
    public static void main(String[] args)
    {
        new marksGUI();
    }
}
