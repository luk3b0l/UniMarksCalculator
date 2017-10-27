package unimarkscalculator.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import unimarkscalculator.mainClasses.Assignment;
import unimarkscalculator.mainClasses.Module;
import unimarkscalculator.mainClasses.ModulesManager;

/**
 *
 * @author Lukasz Bol
 */
public class AssignmentsManagerGUI 
{
    private JFrame myFrame = new JFrame("Assignments Manager GUI");
    private JLabel assignmentsManagerLabel = new JLabel("ASSIGNMENTS MANAGER", JLabel.CENTER);
    private JLabel moduleLabel = new JLabel("modules: ");
    private JLabel assignmentsLabel = new JLabel("assignments: ");
    private JLabel titleLabel = new JLabel("title: ");
    private JLabel typeLabel = new JLabel("type: ");
    private JLabel resultLabel = new JLabel("result: ");
    private JLabel weightPercent = new JLabel("weight(%): ");
    private JTextField titleInput = new JTextField("");
    private JTextField typeInput = new JTextField("");
    private JTextField resultInput = new JTextField("");
    private JTextField weightPercentInput = new JTextField("");
    private JButton deleteAssignmentButton = new JButton("Delete assignment");
    private JButton updateModuleButton = new JButton("Update");
    private JButton clearFieldsButton = new JButton("Clear all fields");
    private JComboBox modulesList = new JComboBox();
    private JComboBox assignmentsList = new JComboBox();
    
    private ModulesManager userModulesManager = ModulesManager.getInstance();
    //private ArrayList<Assignment> tempAssignmentsList = new ArrayList<Assignment>();
    
    public AssignmentsManagerGUI()
    {
        setFrame();
    }
    
    public void setFrame()
    {
        Container contentPane = myFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        Dimension preferredSize = new Dimension(400,300);
        contentPane.setPreferredSize(preferredSize);
        
        // ***** N O R T H
        JPanel northPanel = new JPanel();
        contentPane.add(northPanel, BorderLayout.NORTH);
        northPanel.setLayout(new FlowLayout());
        northPanel.add(assignmentsManagerLabel);
        
        // ***** C E N T E R
        JPanel centerPanel = new JPanel();
        contentPane.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gcCenter = new GridBagConstraints();
        gcCenter.weightx = 0.5; gcCenter.weighty = 0.5;
        
        // COLUMN 1:
        gcCenter.anchor = GridBagConstraints.LINE_END;
        gcCenter.gridx = 0; gcCenter.gridy = 0;
        centerPanel.add(moduleLabel, gcCenter);
        
        gcCenter.gridx = 0; gcCenter.gridy = 1;
        centerPanel.add(assignmentsLabel, gcCenter); 
        
        gcCenter.gridx = 0; gcCenter.gridy = 2;
        centerPanel.add(titleLabel, gcCenter);

        gcCenter.gridx = 0; gcCenter.gridy = 3;
        centerPanel.add(typeLabel, gcCenter);
        
        gcCenter.gridx = 0; gcCenter.gridy = 4;
        centerPanel.add(resultLabel, gcCenter);

        gcCenter.gridx = 0; gcCenter.gridy = 5;
        centerPanel.add(weightPercent, gcCenter);

        // COLUMN 2:        
        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 1; gcCenter.gridy = 0;
        centerPanel.add(modulesList, gcCenter);                

        //Populating updated modulesList:
        modulesList.removeAllItems();
        ArrayList<Module> tempModulesList = userModulesManager.getAllModules();
        for(Module temp : tempModulesList)
        {
            modulesList.addItem(temp.getName());
        }
        modulesList.setSelectedIndex(-1);
        modulesList.addActionListener(new ModulesListHandler());
        
        gcCenter.gridx = 1; gcCenter.gridy = 1;
        centerPanel.add(assignmentsList, gcCenter);
        assignmentsList.addActionListener(new AssignmentsListHandler());
             
        gcCenter.gridx = 1; gcCenter.gridy = 2;
        centerPanel.add(titleInput, gcCenter);
        titleInput.setPreferredSize(new Dimension(100, 25));
        
        gcCenter.gridx = 1; gcCenter.gridy = 3;
        centerPanel.add(typeInput, gcCenter);   
        typeInput.setPreferredSize(new Dimension(100, 25));

        gcCenter.gridx = 1; gcCenter.gridy = 4;
        centerPanel.add(resultInput, gcCenter);   
        resultInput.setPreferredSize(new Dimension(50, 25));
        
        gcCenter.gridx = 1; gcCenter.gridy = 5;
        centerPanel.add(weightPercentInput, gcCenter);
        weightPercentInput.setPreferredSize(new Dimension(50, 25));

        // COLUMN 3:     
        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 2; gcCenter.gridy = 1; 
        centerPanel.add(deleteAssignmentButton, gcCenter);
        deleteAssignmentButton.addActionListener(new DeleteAssignmentButtonHandler());

        gcCenter.gridx = 2; gcCenter.gridy = 4;
        centerPanel.add(clearFieldsButton, gcCenter);
        clearFieldsButton.addActionListener(new ClearFieldsButtonHandler());
                
        gcCenter.gridx = 2; gcCenter.gridy = 5;
        centerPanel.add(updateModuleButton, gcCenter);
        updateModuleButton.addActionListener(new UpdateAssignmentButtonHandler());
        
        myFrame.pack();
        myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myFrame.setAlwaysOnTop(false);
        myFrame.setResizable(false);
        myFrame.setLocationRelativeTo(null);    // setting the program in the centre of the screen 
    }
    
    // ---------------------------------------------------------------------------------------------------
    
    // ***** BUTTON HANDLERS:
    private class ClearFieldsButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            titleInput.setText("");
            typeInput.setText("");
            resultInput.setText("");
            weightPercentInput.setText("");
        }
    }
    
    private class UpdateAssignmentButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(modulesList.getItemCount() == 0 || assignmentsList.getItemCount()== 0 || titleInput.getText().equals("") || typeInput.getText().equals("") || resultInput.getText().equals("") || weightPercentInput.getText().equals(""))
            {
                JOptionPane.showMessageDialog(myFrame, "No modules/assignments on the list or some fields are empty", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                String selectedModule = (String) modulesList.getSelectedItem();
                Module m = userModulesManager.getModule(selectedModule);
                String title = titleInput.getText();
                String type = typeInput.getText();
                double result = Double.valueOf(resultInput.getText());
                double weight = Double.valueOf(weightPercentInput.getText());
                m.updateAssignment(title, type, result, weight);     
                JOptionPane.showMessageDialog(myFrame, "Assignment has been updated successfully", "SUCCESS info", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }    
    
    private class DeleteAssignmentButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(assignmentsList.getItemCount()== 0)
            {
                JOptionPane.showMessageDialog(myFrame, "No assignments on the list", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                String assignmentName = assignmentsList.getSelectedItem().toString();
                userModulesManager.removeAssignment(assignmentName);   
                JOptionPane.showMessageDialog(myFrame, "Assignment has been removed successfully", "SUCCESS info", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // ---------------------------------------------------------------------------------------------------
    // ***** OTHER HANDLERS:
    
    private class ModulesListHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(modulesList.getSelectedItem() != null)
            {
                Module moduleToRetrieveInfo = userModulesManager.getModule(modulesList.getSelectedItem().toString());
                ArrayList<Assignment> tempAssignmentsList = moduleToRetrieveInfo.getAllAssignments();
                assignmentsList.removeAllItems();
                for(Assignment tempAssignment : tempAssignmentsList)
                {
                    if(tempAssignment != null)
                    {
                        assignmentsList.addItem(tempAssignment.getTitle());
                    }
                } 
                clearFields();
            }
        }
    }

    private class AssignmentsListHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(assignmentsList.getItemCount() != 0)
            {
                Module moduleToRetrieveInfo = userModulesManager.getModule(modulesList.getSelectedItem().toString());
                String selectedAssignment = assignmentsList.getSelectedItem().toString();
                ArrayList<Assignment> tempAssignmentsList = new ArrayList<Assignment>();
                tempAssignmentsList = moduleToRetrieveInfo.getAllAssignments();
                for(Assignment tempAssignment : tempAssignmentsList)
                {
                    if(tempAssignment != null && (tempAssignment.getTitle()).equals(selectedAssignment))
                    {
                        titleInput.setText(tempAssignment.getTitle());
                        typeInput.setText(tempAssignment.getType());
                        String result = String.valueOf(tempAssignment.getResult());
                        resultInput.setText(result);
                        String weight = String.valueOf(tempAssignment.getWeightPercent());
                        weightPercentInput.setText(weight);               
                    }
                } 
            }

            
        }
    }
    
    public void clearFields()
    {
        titleInput.setText("");
        typeInput.setText("");
        resultInput.setText("");
        weightPercentInput.setText("");
    }
    
    public void setVisible(boolean visibility)
    {
        myFrame.setVisible(visibility);
    }
}