package unimarkscalculator.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import unimarkscalculator.mainClasses.ModulesManager;

/**
 *
 * @author Lukasz Bol
 */
public class ModuleGUI 
{
    private JFrame modulesFrame = new JFrame("Add Module");
    private JLabel labelAddModule = new JLabel("ADD MODULE", JLabel.CENTER);
    private JLabel labelLabel = new JLabel("level: ");
    private JLabel labelSemester = new JLabel("semester: ");
    private JLabel labelName = new JLabel("name: ");
    private JLabel labelCredits = new JLabel("credits: ");
    private JTextField inputLevel = new JTextField("");
    private JTextField inputSemester = new JTextField("");
    private JTextField inputName = new JTextField("");
    private JTextField inputCredits = new JTextField("");
    private JButton buttonAddModule = new JButton("Add");
    private JButton buttonClearFields = new JButton("Clear all fields");
    private JComboBox dropdownSemesters = new JComboBox(new String[] {"A", "B", "AB"});
    private JComboBox dropdownLevels = new JComboBox(new String[] {"4", "5", "6"});
    private JComboBox dropdownCredits = new JComboBox(new String[] {"15", "30"});
    
    private ModulesManager userModulesManager = ModulesManager.getInstance();
    
    public ModuleGUI()
    {
        setGUIFrame();
    }
    
    private void setGUIFrame()
    {
        Container contentPane = modulesFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        Dimension preferredSize = new Dimension(400, 300);
        modulesFrame.setPreferredSize(preferredSize);
        
        // ***** N O R T H
        JPanel northPanel = new JPanel();
        contentPane.add(northPanel, BorderLayout.NORTH);
        northPanel.setLayout(new FlowLayout());
        northPanel.add(labelAddModule);

        // ***** C E N T E R
        JPanel centerPanel = new JPanel();
        contentPane.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gcCenter = new GridBagConstraints();
        gcCenter.weightx = 0.5; gcCenter.weighty = 0.5;
        
        // COLUMN 1:
        gcCenter.anchor = GridBagConstraints.LINE_END;
        gcCenter.gridx = 0; gcCenter.gridy = 0;
        centerPanel.add(labelLabel, gcCenter);

        gcCenter.gridx = 0; gcCenter.gridy = 1;
        centerPanel.add(labelSemester, gcCenter);        

        gcCenter.gridx = 0; gcCenter.gridy = 2;
        centerPanel.add(labelName, gcCenter);        

        gcCenter.gridx = 0; gcCenter.gridy = 3;
        centerPanel.add(labelCredits, gcCenter);   
        
        // COLUMN 2:        
        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 1; gcCenter.gridy = 0;
        centerPanel.add(dropdownLevels, gcCenter);
        dropdownLevels.setSelectedIndex(-1);
        
        gcCenter.gridx = 1; gcCenter.gridy = 1;
        centerPanel.add(dropdownSemesters, gcCenter);
        dropdownSemesters.setSelectedIndex(-1);
        
        gcCenter.gridx = 1; gcCenter.gridy = 2;
        centerPanel.add(inputName, gcCenter);
        inputName.setPreferredSize(new Dimension(100, 25));
        
        gcCenter.gridx = 1; gcCenter.gridy = 3;
        centerPanel.add(dropdownCredits, gcCenter);   
        dropdownCredits.setSelectedIndex(-1);
        
        // COLUMN 3:
        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 2; gcCenter.gridy = 3;
        centerPanel.add(buttonClearFields, gcCenter);
        buttonClearFields.addActionListener(new ClearFieldsButtonHandler());
                
        gcCenter.gridx = 2; gcCenter.gridy = 4;
        centerPanel.add(buttonAddModule, gcCenter);
        buttonAddModule.addActionListener(new AddModuleButtonHandler());
        
        modulesFrame.pack();
        modulesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        modulesFrame.setAlwaysOnTop(false);
        modulesFrame.setResizable(false);
        modulesFrame.setLocationRelativeTo(null);    // setting the program in the centre of the screen
    }
    
    // ***** H A N D L E R S -------------------------------------------------------------------------------------   
    private class ClearFieldsButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            clearFields();
        }
    }    
    
    private class AddModuleButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            boolean isCreditsInteger = false;
            if(dropdownLevels.getSelectedIndex() == -1 || dropdownSemesters.getSelectedIndex() == -1 || inputName.getText().equals("") || dropdownCredits.getSelectedIndex() == -1)
            {
                JOptionPane.showMessageDialog(modulesFrame, "Some fields are empty", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else if(userModulesManager.checkModuleExists(inputName.getText()) == true)
            {
                JOptionPane.showMessageDialog(modulesFrame, "Module already exists", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                String level = dropdownLevels.getSelectedItem().toString();
                String semester = dropdownSemesters.getSelectedItem().toString();
                String name = inputName.getText(); 
                String credits = dropdownCredits.getSelectedItem().toString();             
                int creditsNum = Integer.parseInt(credits);
                
                userModulesManager.addModule(level, name, semester, creditsNum);
                clearFields();  //Clearing the input fields for next data input
                JOptionPane.showMessageDialog(modulesFrame, "Module has been added successfully", "SUCCESS info", JOptionPane.INFORMATION_MESSAGE);
            }           
        }
    }
 
    // ***** M E T H O D S ---------------------------------------------------------------------------------------      
    public void clearFields()
    {
        dropdownLevels.setSelectedIndex(-1);
        dropdownSemesters.setSelectedIndex(-1);
        dropdownCredits.setSelectedIndex(-1);
        inputName.setText("");
    }
    public void setWindowVisible(boolean visibility)
    {
        modulesFrame.setVisible(visibility);
    }
}