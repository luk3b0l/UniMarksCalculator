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
    private JFrame programMainFrame = new JFrame("Uni Marks Calculator");
    private JButton buttonAddModule = new JButton("Add Module");
    private JButton buttonAddAssignment = new JButton("Add Assignment");
    private JButton buttonModulesManager = new JButton("Modules Manager");
    private JButton buttonAssignmentsManager = new JButton("Assignments Manager");
    private JButton buttonResults = new JButton("View Results");      
    private JComboBox dropdownModules = new JComboBox();
    
    public MarksGUI()
    {
        setGUIFrame();
    }
    
    private void setGUIFrame()
    {
        Container contentPane = programMainFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        Dimension preferredWindowSize = new Dimension(200, 250);
        programMainFrame.setPreferredSize(preferredWindowSize);
        setMenuBar(programMainFrame);
        
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
        centerPanel.add(buttonAddModule, gcCenter);
        buttonAddModule.addActionListener(new AddModuleButtonHandler());
      
        gcCenter.gridx = 0; gcCenter.gridy = 1;
        centerPanel.add(buttonAddAssignment, gcCenter);
        buttonAddAssignment.addActionListener(new AddAssignmentButtonHandler());
        
        gcCenter.gridx = 0; gcCenter.gridy = 2;
        centerPanel.add(buttonModulesManager, gcCenter);
        buttonModulesManager.addActionListener(new ModulesManagerButtonHandler());        
        
        gcCenter.gridx = 0; gcCenter.gridy = 3;
        centerPanel.add(buttonAssignmentsManager, gcCenter);
        buttonAssignmentsManager.addActionListener(new AssignmentsManagerButtonHandler());        
        
        gcCenter.gridx = 0; gcCenter.gridy = 4;
        centerPanel.add(buttonResults, gcCenter);
        buttonResults.addActionListener(new ResultsButtonHandler());  
        
        programMainFrame.pack();
        programMainFrame.setVisible(true);
        programMainFrame.setAlwaysOnTop(false);
        programMainFrame.setResizable(false);
        programMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        programMainFrame.setLocationRelativeTo(null);    // setting the program in the centre of the screen
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
        exitItem.addActionListener(new ExitHandler());                           
        
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
    }
    
    // ***** L I S T E N E R S -------------------------------------------------------------------------------------
    
    // File:
    // - Exit    
    private class ExitHandler implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            int userAnswer = JOptionPane.showConfirmDialog(programMainFrame, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
            if(userAnswer == JOptionPane.YES_OPTION)
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
            
            JOptionPane.showMessageDialog(programMainFrame, message, title, JOptionPane.INFORMATION_MESSAGE);
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
            JOptionPane.showMessageDialog(programMainFrame, message, title, JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private class AddModuleButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            new ModuleGUI().setWindowVisible(true);
        }
    }
    
    private class AddAssignmentButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            new AssignmentGUI().setWindowVisible(true);
        }
    }
   
    private class ModulesManagerButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            new ModulesManagerGUI().setWindowVisible(true);
        }
        
    }
    
    private class AssignmentsManagerButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            new AssignmentsManagerGUI().setWindowVisible(true);
        }
        
    }
    
    private class ResultsButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            new ResultsGUI().setWindowVisible(true);
        }
        
    }
}