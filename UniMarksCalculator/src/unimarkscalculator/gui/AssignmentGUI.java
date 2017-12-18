package unimarkscalculator.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import unimarkscalculator.mainClasses.Module;
import unimarkscalculator.mainClasses.ModulesManager;


/**
 * This class provides functionality on adding assignments to existing modules.
 * @author Lukasz Bol
 */
public class AssignmentGUI 
{
    private JFrame assignmentsFrame = new JFrame("Add Assignment");
    private JLabel labelAddAssignment = new JLabel("ADD ASSIGNMENT", JLabel.CENTER);
    private JLabel labelModules = new JLabel("module: ");
    private JLabel labelTitle = new JLabel("title: ");
    private JLabel labelType = new JLabel("type: ");
    private JLabel labelResult = new JLabel("result: ");
    private JLabel labelWeightPercents = new JLabel("weight(%): ");
    private JTextField inputTitle = new JTextField("");
    private JTextField inputType = new JTextField("");
    private JTextField inputResult = new JTextField("");
    private JTextField inputWeightPercents = new JTextField("");
    private JButton buttonAddAssignment = new JButton("Add");
    private JButton buttonClearFields = new JButton("Clear all fields");
    private JComboBox dropdownModules = new JComboBox();
    private JComboBox dropdownAssignmentTypes = new JComboBox(new String[] {"coursework", "exam", "test", "other"});

    private ModulesManager userModulesManager = ModulesManager.getInstance();   // very important, getting instance of ModulesManager (instead of creating new object) - SINGLETON PATTERN
    
    public AssignmentGUI()
    {
        setGUIFrame();
    }
    

    private void setGUIFrame()
    {
        Container contentPane = assignmentsFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        Dimension preferredWindowSize = new Dimension(400, 300);
        assignmentsFrame.setPreferredSize(preferredWindowSize);
        
        // ***** N O R T H
        JPanel northPanel = new JPanel();
        contentPane.add(northPanel, BorderLayout.NORTH);
        northPanel.setLayout(new FlowLayout());
        northPanel.add(labelAddAssignment);

        // ***** C E N T E R
        JPanel centerPanel = new JPanel();
        contentPane.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gcCenter = new GridBagConstraints();
        gcCenter.weightx = 0.5; gcCenter.weighty = 0.5;
        
        // COLUMN 1:
        gcCenter.anchor = GridBagConstraints.LINE_END;
        gcCenter.gridx = 0; gcCenter.gridy = 0;
        centerPanel.add(labelModules, gcCenter);

        gcCenter.gridx = 0; gcCenter.gridy = 1;
        centerPanel.add(labelTitle, gcCenter);        

        gcCenter.gridx = 0; gcCenter.gridy = 2;
        centerPanel.add(labelType, gcCenter);        

        gcCenter.gridx = 0; gcCenter.gridy = 3;
        centerPanel.add(labelResult, gcCenter);           

        gcCenter.gridx = 0; gcCenter.gridy = 4;
        centerPanel.add(labelWeightPercents, gcCenter); 
        
        // COLUMN 2:    
        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 1; gcCenter.gridy = 0;
        centerPanel.add(dropdownModules, gcCenter);
        
        dropdownModules.removeAllItems();
        ArrayList<Module> tempModulesList = userModulesManager.getAllModules();
        for(Module tempModule : tempModulesList)
        {
            dropdownModules.addItem(tempModule.getName());
        }
        dropdownModules.setSelectedIndex(-1);
        
        gcCenter.gridx = 1; gcCenter.gridy = 1;
        centerPanel.add(inputTitle, gcCenter);
        inputTitle.setPreferredSize(new Dimension(100, 25));
        
        gcCenter.gridx = 1; gcCenter.gridy = 2;
        centerPanel.add(dropdownAssignmentTypes, gcCenter);        
        dropdownAssignmentTypes.setSelectedIndex(-1);
        
        gcCenter.gridx = 1; gcCenter.gridy = 3;
        centerPanel.add(inputResult, gcCenter);        
        inputResult.setPreferredSize(new Dimension(50, 25));
        
        gcCenter.gridx = 1; gcCenter.gridy = 4;
        centerPanel.add(inputWeightPercents, gcCenter);        
        inputWeightPercents.setPreferredSize(new Dimension(50, 25));
        
        // COLUMN 3:    
        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 2; gcCenter.gridy = 3;
        centerPanel.add(buttonClearFields, gcCenter);       
        buttonClearFields.addActionListener(new ClearFieldsButtonHandler());
        
        gcCenter.gridx = 2; gcCenter.gridy = 4;
        centerPanel.add(buttonAddAssignment, gcCenter);                
        buttonAddAssignment.addActionListener(new AddAssignmentButtonHandler());
        
        assignmentsFrame.pack();
        assignmentsFrame.setAlwaysOnTop(false);
        assignmentsFrame.setResizable(false);
        assignmentsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        assignmentsFrame.setLocationRelativeTo(null);    // setting the program in the centre of the screen        
    }
    
    // ***** L I S T E N E R S -------------------------------------------------------------------------------------
     private class ClearFieldsButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            clearFields();
        }
    }    
    
    private class AddAssignmentButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            boolean isResultDouble = false;
            boolean isWeightDouble = false;            
            String selectedModule = (String) dropdownModules.getSelectedItem();
            String title = inputTitle.getText();
            Module m = userModulesManager.getModule(selectedModule);
            
            if(dropdownModules.getItemCount() == 0 || inputTitle.getText().equals("") || dropdownAssignmentTypes.getSelectedIndex() == -1 || inputResult.getText().equals("") || inputWeightPercents.getText().equals(""))
            {
                JOptionPane.showMessageDialog(assignmentsFrame, "No modules on the list or some fields are empty", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else if(m.checkAssignmentExists(title) == true)
            {
                JOptionPane.showMessageDialog(assignmentsFrame, "Assignment already exists.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                String type = dropdownAssignmentTypes.getSelectedItem().toString();
                String result = inputResult.getText(); 
                String weightPercent = inputWeightPercents.getText();
                
                isResultDouble = isDouble(result);
                isWeightDouble = isDouble(weightPercent);
                
                if(isResultDouble == true && isWeightDouble == true)
                {
                    double resultNum = Double.parseDouble(result);
                    double weightPercentNum = Double.parseDouble(weightPercent);
                    double totalWeight = m.getTotalAssignmentsWeight() + weightPercentNum;
                    
                    if(totalWeight > 100)
                    {               
                        JOptionPane.showMessageDialog(assignmentsFrame, "Total weights are more than 100. Please check your assignments' dates for mistakes.", "ERROR Info", JOptionPane.ERROR_MESSAGE);    
                        
                    }
                    else if(totalWeight == 100 && resultNum > 0 && resultNum <= 100 && weightPercentNum > 0 && resultNum <= 100)
                    {
                        double calculatedGrade = 0;
                        m.addAssignment(title, type, resultNum, weightPercentNum);
                        m.calculateAndSetGrade();
                        clearFields();  //Clearing the input fields for next data input
                        JOptionPane.showMessageDialog(assignmentsFrame, "Assignment has been added successfully.\nTotal assignments' weight is equal 100. \nYour grade has been calculated and can be seen in 'View Results' tab.", "Success Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if (totalWeight < 100 && resultNum > 0 && resultNum <= 100 && weightPercentNum > 0 && resultNum <= 100)
                    {
                        m.addAssignment(title, type, resultNum, weightPercentNum);
                        clearFields();  //Clearing the input fields for next data input
                        JOptionPane.showMessageDialog(assignmentsFrame, "Assignment has been added successfully", "Success Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(assignmentsFrame, "Result and weight have to be greater than 0 and less or equal to 100. Please check your data.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(assignmentsFrame, "Result and weight data must be numbers.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    // ***** M E T H O D S -------------------------------------------------------------------------------------
    public void clearFields()
    {
        inputTitle.setText("");
        dropdownAssignmentTypes.setSelectedIndex(-1);
        inputResult.setText("");
        inputWeightPercents.setText("");
    }

    private boolean isDouble(String numberToCheck)
    {
        boolean isValidInteger = false;
        
        try
        {
            Double.parseDouble(numberToCheck);
            isValidInteger = true;
        }
        catch (NumberFormatException e)
        {
            System.out.println(e);
        }
        return isValidInteger;
    }
    
    public void setWindowVisible(boolean visibility)
    {
        assignmentsFrame.setVisible(visibility);
    }
}