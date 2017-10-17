/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unimarkscalculator;

import java.awt.*;
import java.awt.Event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Lukasz Bol
 */
public class marksGUI 
{
    private JFrame myFrame = new JFrame("Uni Marks Calculator");
    private JLabel assignmentLabel = new JLabel("Assignment");
    private JLabel moduleLabel = new JLabel("Module");
    private JTextField moduleTitle = new JTextField(" ");
    private JTextField assignmentTitle = new JTextField(" ");
    
    private JButton newModule = new JButton("New module");
    private JButton addModule = new JButton("Add");
    
    
    private JComboBox modulesList = new JComboBox();
    
    
    
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
        
        // ***** N O R T H
        JPanel northPanel = new JPanel();
        contentPane.add(northPanel, BorderLayout.NORTH);
        northPanel.setLayout(new FlowLayout());
        northPanel.add(newModule);
        
        // ***** C E N T E R
        JPanel centerPanel = new JPanel();
        contentPane.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gcCenter = new GridBagConstraints();
        gcCenter.weightx = 0.5; gcCenter.weighty = 0.5;
        
        // COLUMN 1:
        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 0; gcCenter.gridy = 0;
        centerPanel.add(moduleLabel, gcCenter);
      
        gcCenter.gridx = 0; gcCenter.gridy = 1;
        centerPanel.add(assignmentLabel, gcCenter);
         
        // COLUMN 2:
        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 1; gcCenter.gridy = 0;
        centerPanel.add(moduleTitle, gcCenter);
        moduleTitle.setPreferredSize(new Dimension(100,25));
        
        gcCenter.gridx = 1; gcCenter.gridy = 1;
        centerPanel.add(assignmentTitle, gcCenter);
        assignmentTitle.setPreferredSize(new Dimension(100,25));
        
        
        
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
        exitItem.addActionListener(new ExitHandler());                           // TESTING!
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
        howToItem.addActionListener(new howToUseHandler());
        
        JMenuItem aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);
        aboutItem.addActionListener(new AboutHandler());
        //aboutItem.addActionListener(new aboutHandler());
        // -----------------------------------------------
    }
    
    // ***** MENU HANDLERS:

    // File:
    // - Exit    
    private class ExitHandler implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    
    // Help:
    // - How to use
    // - About
    private class howToUseHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
         // TODO!
            String title = "How to use";
            String message = "\n<html><font size='5'>How to use Uni Marks Calculator ?</font></html>\n\n"
                             +"For the Uni Marks Calculator application to work, please follow these simple steps: \n"
                             + "1. ...\n"
                             + "2. ...\n"
                             + "3. ...\n";
            JOptionPane.showMessageDialog(myFrame, message, title, JOptionPane.INFORMATION_MESSAGE);
        }
    }    
    private class AboutHandler implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            String title = "About";
            String message = "\n<html><font size='5'>Uni Marks Calculator</font></html>\n\n"
                            + "This application will help you to calculate your final marks and keep track of all your grades."
                            + "\n\nAuthor: Lukasz Bol"
                            + "\nContact: lukaszbol@yahoo.co.uk"
                            + "\nVersion: 1.1  [October 2017]";
            JOptionPane.showMessageDialog(myFrame, message, title, JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public static void main(String[] args)
    {
        new marksGUI();
    }
}