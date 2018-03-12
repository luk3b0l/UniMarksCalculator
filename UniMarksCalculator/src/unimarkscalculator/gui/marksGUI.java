package unimarkscalculator.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import javax.swing.*;
import unimarkscalculator.mainClasses.Module;
import unimarkscalculator.mainClasses.ModulesManager;

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
    private ModulesManager userModulesManager = ModulesManager.getInstance();
    
    public MarksGUI()
    {
        setGUIFrame();
        setTestData();      //temporary TEST DATA METHOD
        
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
        
        JMenuItem clearAllDataItems = new JMenuItem("Clear all data");
        fileMenu.add(clearAllDataItems);
        clearAllDataItems.addActionListener(new ClearAllDataItems());
        
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);
        exitItem.addActionListener(new ExitHandler());                           

//        [FUTURE DEVELOPMENT]
//        // *** PROFILE menu:
//        JMenu profileMenu = new JMenu("Profile");
//        menubar.add(profileMenu);
//        
//        JMenuItem newItem = new JMenuItem("New");
//        profileMenu.add(newItem);
//        // TODO - add Action Listener
//
//        JMenuItem loadItem = new JMenuItem("Load");
//        profileMenu.add(loadItem);
//        // TODO - add Action Listener
//
//        JMenuItem editItem = new JMenuItem("Edit");
//        profileMenu.add(editItem);
//        // TODO - add Action Listener  
// 
//        JMenuItem deleteItem = new JMenuItem("Delete");
//        profileMenu.add(deleteItem);
//        // TODO - add Action Listener
        
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
    // - Clear all data  
    // - Exit
    
    private class ClearAllDataItems implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            int userAnswer = JOptionPane.showConfirmDialog(programMainFrame, "Are you sure you want to clear all existing data?", "Clear All Data Confirmation", JOptionPane.YES_NO_OPTION);
            if(userAnswer == JOptionPane.YES_OPTION)
            {
                userModulesManager.removeAllModulesList();
            }
        }
    }
  
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
                             + "GENERAL INFO:\n"
                             + "For the Uni Marks Calculator application to work, please follow these simple steps: \n"
                             + "1. Click on 'File' -> 'Clear all data' to make sure you start with no previous data added.\n"
                             + "2. Add all relevant modules.\n"
                             + "3. Add all assignments to each individual module stored earlier.\n"
                             + "4. Go to 'View Results' to see all your modules and assignments.\n"
                             + "5. Tick 'select modules' checkbox and select modules that you want to add to the final mark grade (not applicable for Level 4 modules) and press 'Calculate FINAL GRADE' button.\n"
                             + "\n"
                             + "NOTE:\n"
                             + "<html><body><ul><li>Module grades are calculated once total percentage weight of module assignments is 100%."
                             + "<li>Otherwise, the Final Grade will be 0 in the 'View Results' tab.</ul><br>"
                    
                             + "SHOW ASSIGNMENTS:\n"
                             + "To see assignments of each module tick 'show assignments' checkbox in 'View Results' tab.\n"
                             + "Now, whenever you click on a module, you will be shown with related assignments in the Assignments table.\n\n"
                    
                             + "PRINTING:\n"
                             + "1. On the 'View Results' tab click on 'Print' button.\n"
                             + "2. Choose 'Modules' or 'Modules and assignments'.\n"
                             + "3. The program will generate a PDF file in the program destination and open it straight after creating it.";
            
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
    
    private void setTestData()
    {
        Module tempModule;
        
        //ADD MODULES:
        userModulesManager.addModule("4", "Human Dimensions of Computing", "AB", 30);
        userModulesManager.addModule("4", "Models and Methods in Computing", "AB", 30);
        userModulesManager.addModule("4", "Platforms for Computing", "AB", 30);
        userModulesManager.addModule("4", "Programming", "AB", 30);
        
        userModulesManager.addModule("5", "Algorithms and Data Structures", "A", 15);
        userModulesManager.addModule("5", "Artificial Intelligence", "A", 15);
        userModulesManager.addModule("5", "Computer Science Development Exercise", "AB", 30);
        userModulesManager.addModule("5", "Contemporary Issues", "A", 15);
        userModulesManager.addModule("5", "Database Concepts", "B", 15);
        userModulesManager.addModule("5", "Operating Systems and Networks", "B", 15);
        userModulesManager.addModule("5", "The C Family", "B", 15);
        
        userModulesManager.addModule("6", "Computer Systems Security", "A", 15);
        userModulesManager.addModule("6", "Machine Learning and Neural Computing", "B", 15);
        userModulesManager.addModule("6", "Object Oriented Development", "A", 15);
        userModulesManager.addModule("6", "Programming Paradigms", "A", 15);
        userModulesManager.addModule("6", "Project Planning", "A", 15);
        userModulesManager.addModule("6", "Software Engineering Practice", "B", 15);
        userModulesManager.addModule("6", "Software Engineering Project", "B", 30);
        
        //ADD ASSIGNMENTS:
        //Level 4
        tempModule = userModulesManager.getModule("Human Dimensions of Computing");
        tempModule.addAssignment("Test 1", "test", 58, 10);
        tempModule.addAssignment("Test 2", "test", 69, 20);
        tempModule.addAssignment("Test 3", "test", 80, 30);
        tempModule.addAssignment("Presentation", "other", 65.63, 40);
        tempModule.calculateAndSetGrade();
        
        tempModule = userModulesManager.getModule("Models and Methods in Computing");
        tempModule.addAssignment("Coursework 1", "coursework", 92, 100);
        tempModule.calculateAndSetGrade();
        
        tempModule = userModulesManager.getModule("Platforms for Computing");
        tempModule.addAssignment("Coursework 1", "coursework", 84, 100);
        tempModule.calculateAndSetGrade();
        
        tempModule = userModulesManager.getModule("Programming");
        tempModule.addAssignment("Coursework 1", "coursework", 91, 60);
        tempModule.addAssignment("Exam 1", "exam", 89, 40);
        tempModule.calculateAndSetGrade();
        
        //Level 5
        tempModule = userModulesManager.getModule("Algorithms and Data Structures");
        tempModule.addAssignment("Exam 1", "exam", 97, 100);
        tempModule.calculateAndSetGrade();
        
        tempModule = userModulesManager.getModule("Artificial Intelligence");
        tempModule.addAssignment("Practical", "coursework", 90, 20);
        tempModule.addAssignment("Coursework 1", "coursework", 100, 30);
        tempModule.addAssignment("Exam 1", "exam", 73, 50);
        tempModule.calculateAndSetGrade();
        
        tempModule = userModulesManager.getModule("Computer Science Development Exercise");
        tempModule.addAssignment("Coursework 1", "coursework", 75, 100);
        tempModule.calculateAndSetGrade();
        
        tempModule = userModulesManager.getModule("Contemporary Issues");
        tempModule.addAssignment("Coursework 1", "coursework", 75, 100);
        tempModule.calculateAndSetGrade();        
        
        tempModule = userModulesManager.getModule("Database Concepts");
        tempModule.addAssignment("Coursework 1", "coursework", 75, 50);
        tempModule.addAssignment("Exam 1", "exam", 62, 50);
        tempModule.calculateAndSetGrade();          
        
        tempModule = userModulesManager.getModule("Operating Systems and Networks");
        tempModule.addAssignment("Coursework 1", "coursework", 90, 10);
        tempModule.addAssignment("Exam 1", "exam", 96, 90);
        tempModule.calculateAndSetGrade();            

        tempModule = userModulesManager.getModule("The C Family");
        tempModule.addAssignment("Practical", "coursework", 93, 15);
        tempModule.addAssignment("Exam 1", "exam", 84, 85);
        tempModule.calculateAndSetGrade();         
        
        //Level 6
        tempModule = userModulesManager.getModule("Computer Systems Security");
        tempModule.addAssignment("Coursework 1", "coursework", 90, 100);
        tempModule.calculateAndSetGrade();   
        
        tempModule = userModulesManager.getModule("Machine Learning and Neural Computing");
        tempModule.addAssignment("Coursework 1", "coursework", 83, 100);
        tempModule.calculateAndSetGrade();          

        tempModule = userModulesManager.getModule("Object Oriented Development");
        tempModule.addAssignment("Coursework 1", "coursework", 84, 33);
        tempModule.addAssignment("Exam 1", "exam", 63, 67);
        tempModule.calculateAndSetGrade();   
        
        tempModule = userModulesManager.getModule("Programming Paradigms");
        tempModule.addAssignment("Coursework 1", "coursework", 87, 40);
        tempModule.addAssignment("Exam 1", "exam", 56, 60);
        tempModule.calculateAndSetGrade();   
        
        tempModule = userModulesManager.getModule("Project Planning");
        tempModule.addAssignment("Coursework 1", "coursework", 84, 50);
        tempModule.addAssignment("Coursework 2", "coursework", 83, 50);
        tempModule.calculateAndSetGrade();  
        
        tempModule = userModulesManager.getModule("Software Engineering Practice");
        tempModule.addAssignment("Coursework 1", "coursework", 61, 33);
        tempModule.addAssignment("Coursework 2", "coursework", 46, 67);
        tempModule.calculateAndSetGrade();          
        
        tempModule = userModulesManager.getModule("Software Engineering Project");
        tempModule.addAssignment("Practical", "coursework", 93, 20);
        tempModule.addAssignment("Coursework 1", "coursework", 80, 80);
        tempModule.calculateAndSetGrade();     
    }
}