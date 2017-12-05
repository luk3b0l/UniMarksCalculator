package unimarkscalculator.gui;

import java.awt.*;
import java.awt.Event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import javax.swing.*;

/**
 *
 * @author Lukasz Bol
 */
public class MarksGUI 
{
    private JFrame myFrame = new JFrame("Uni Marks Calculator");
    private JButton addModuleButton = new JButton("Add module");
    private JButton addAssignmentButton = new JButton("Add assignment");
    private JButton modulesManagerButton = new JButton("Modules manager");
    private JButton assignmentsManagerButton = new JButton("Assignments manager");
    private JButton resultsButton = new JButton("View Results");
        
    private JComboBox modulesList = new JComboBox();
    
    
    
    public MarksGUI()
    {
        setFrame();
    }
    
    private void setFrame()
    {
        Container contentPane = myFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        Dimension preferredSize = new Dimension(200, 250);
        myFrame.setPreferredSize(preferredSize);
        setMenuBar(myFrame);
        
        // ***** N O R T H
        JPanel northPanel = new JPanel();
        contentPane.add(northPanel, BorderLayout.NORTH);
        northPanel.setLayout(new FlowLayout());
        //northPanel.add(usernameLabel);
        //northPanel.add(usernameInput);
        //northPanel.add(passwordLabel);
        //northPanel.add(passwordInput);
        
        // ***** C E N T E R
        JPanel centerPanel = new JPanel();
        contentPane.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gcCenter = new GridBagConstraints();
        gcCenter.weightx = 0.5; gcCenter.weighty = 0.5;
        
        // COLUMN 1:
        gcCenter.anchor = GridBagConstraints.CENTER;
        gcCenter.gridx = 0; gcCenter.gridy = 0;
        centerPanel.add(addModuleButton, gcCenter);
        addModuleButton.addActionListener(new AddModuleButtonHandler());
      
        gcCenter.gridx = 0; gcCenter.gridy = 1;
        centerPanel.add(addAssignmentButton, gcCenter);
        addAssignmentButton.addActionListener(new AddAssignmentButtonHandler());
        
        gcCenter.gridx = 0; gcCenter.gridy = 2;
        centerPanel.add(modulesManagerButton, gcCenter);
        modulesManagerButton.addActionListener(new ModulesManagerButtonHandler());        
        
        gcCenter.gridx = 0; gcCenter.gridy = 3;
        centerPanel.add(assignmentsManagerButton, gcCenter);
        assignmentsManagerButton.addActionListener(new AssignmentsManagerButtonHandler());        
        
        gcCenter.gridx = 0; gcCenter.gridy = 4;
        centerPanel.add(resultsButton, gcCenter);
        resultsButton.addActionListener(new ResultsButtonHandler());  
        
        myFrame.pack();
        myFrame.setVisible(true);
        myFrame.setAlwaysOnTop(false);
        myFrame.setResizable(false);
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
        public void actionPerformed(ActionEvent e) 
        {
            int answer = JOptionPane.showConfirmDialog(myFrame, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
            if(answer == JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
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
                             + "1. Add all relevant modules.\n"
                             + "2. Add all assignments to each individual module stored earlier.\n"
                             + "3. Go to 'View Results' to see all your modules and assignments.\n"
                             + "4. Tick modules that you want to add to the final mark grade (not applicable for Level 4 modules) and press 'Calculate FINAL GRADE' button.\n"
                             + "\n"
                             + "NOTE:\n"
                    + "<html><ul><li>Module grades are calculated once total percentage weight of module assignments will be equal to 100%."
                             + "<li>Otherwise, the Grade will be 0 in the 'View Results' tab.</ul></html>";
            
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
                            + "This application helps keeping track of your university grades (Level 4,5,6) and helps to calculate your Final Grade."
                            + "\n\nAuthor: Lukasz Bol"
                            + "\nContact: lukaszbol@yahoo.co.uk"
                            + "\nVersion: 1.1  [October 2017]";
            JOptionPane.showMessageDialog(myFrame, message, title, JOptionPane.INFORMATION_MESSAGE);
        }
    }
    // -----------------------------------------------
    
    // ***** EVENT HANDLERS:    
    
    private class AddModuleButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            new ModuleGUI().setVisible(true);
        }
    }
    
    private class AddAssignmentButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            new AssignmentGUI().setVisible(true);
        }
    }
   
    private class ModulesManagerButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            new ModulesManagerGUI().setVisible(true);
        }
        
    }
    
    private class AssignmentsManagerButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            new AssignmentsManagerGUI().setVisible(true);
        }
        
    }
    
    private class ResultsButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            new ResultsGUI().setVisible(true);
        }
        
    }

    public static void main(String[] args)
    {
        new MarksGUI();
    }
}