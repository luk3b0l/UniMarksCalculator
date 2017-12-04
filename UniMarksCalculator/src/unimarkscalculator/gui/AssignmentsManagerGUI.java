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
    private JComboBox assignmentTypesLists = new JComboBox(new String[] {"coursework", "exam", "test", "other"});
    private String tempAssignmentTitle = "";
    
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
        assignmentsList.setSelectedIndex(-1);
             
        gcCenter.gridx = 1; gcCenter.gridy = 2;
        centerPanel.add(titleInput, gcCenter);
        titleInput.setPreferredSize(new Dimension(100, 25));
        
        gcCenter.gridx = 1; gcCenter.gridy = 3;
        centerPanel.add(assignmentTypesLists, gcCenter);   
        assignmentTypesLists.setSelectedIndex(-1);

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
            clearFields();
            modulesList.setSelectedIndex(-1);
            assignmentsList.setSelectedIndex(-1);
            assignmentTypesLists.setSelectedIndex(-1);
        }
    }
    
    private class UpdateAssignmentButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            boolean isResultDouble = false;
            boolean isWeightDouble = false;  
            Module m = null;
            Assignment selectedAssignment = null;
            String assignmentType = "";
            double resultNum = -1;
            double weightNum = -1;
            
            if(modulesList.getItemCount() != 0 && modulesList.getSelectedIndex() != -1)
            {
                String selectedModule = (String) modulesList.getSelectedItem();
                m = userModulesManager.getModule(selectedModule); 
                
                if(assignmentsList.getItemCount() != 0 && assignmentsList.getSelectedIndex() != -1)
                {
                    assignmentType = assignmentTypesLists.getSelectedItem().toString();
                    selectedAssignment = m.getAssignment(assignmentsList.getSelectedItem().toString());
                    String result = resultInput.getText();
                    String weight = weightPercentInput.getText();
                    
                    isResultDouble = isDouble(result);
                    isWeightDouble = isDouble(weight);
                    
                    if(isResultDouble == true && isWeightDouble == true)
                    {
                        resultNum = Double.parseDouble(result);
                        weightNum = Double.parseDouble(weight);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(myFrame, "Result and weight data must be numbers.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
                    } 
                }
            }
            
            if(modulesList.getItemCount() == 0)
            {
                JOptionPane.showMessageDialog(myFrame, "No modules on the list.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else if (modulesList.getSelectedIndex() == -1)
            {
                JOptionPane.showMessageDialog(myFrame, "No module selected.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else if(assignmentsList.getItemCount() == 0)
            {
                JOptionPane.showMessageDialog(myFrame, "No assignments on the list.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else if(assignmentsList.getSelectedIndex() == -1)
            {
                JOptionPane.showMessageDialog(myFrame, "No assignment selected.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            
            else if(titleInput.getText().equals("") || assignmentTypesLists.getSelectedIndex() == -1 || resultInput.getText().equals("") || weightPercentInput.getText().equals(""))
            {
                JOptionPane.showMessageDialog(myFrame, "Some fields are empty.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else if((titleInput.getText()).equals(selectedAssignment.getTitle()) &&
                    (assignmentTypesLists.getSelectedItem().toString()).equals(selectedAssignment.getType()) &&
                    resultNum == selectedAssignment.getResult() &&
                    weightNum == selectedAssignment.getWeightPercent()
                    )
            {
                JOptionPane.showMessageDialog(myFrame, "No information has been changed.", "WARNING Info", JOptionPane.WARNING_MESSAGE);
            }
            else
            {             
                String oldTitle = getTempAssignmentTitle();
                String newTitle = titleInput.getText();
                double totalWeight = (m.getTotalAssignmentsWeight() - selectedAssignment.getWeightPercent()) + weightNum;
                
                if(totalWeight > 100)
                {
                    JOptionPane.showMessageDialog(myFrame, "Your total weight of assignments cannot be more than 100. Please check your input data.", "ERROR Info", JOptionPane.ERROR_MESSAGE);    
                }
                else if(totalWeight == 100 && resultNum > 0 && resultNum <= 100 && weightNum > 0 && resultNum <= 100)
                {
                    m.updateAssignment(oldTitle, newTitle, assignmentType, resultNum, weightNum);   
                    m.calculateAndSetGrade();
                    populateAssignmentsList();
                    JOptionPane.showMessageDialog(myFrame, "Assignment has been updated successfully.\nTotal assignments' weight is equal 100. \nYour grade has been calculated and can be seen in 'View Results' tab.", "Success Info", JOptionPane.INFORMATION_MESSAGE);
                }
                else if(totalWeight < 100 && resultNum > 0 && resultNum <= 100 && weightNum > 0 && resultNum <= 100)
                {
                    m.updateAssignment(oldTitle, newTitle, assignmentType, resultNum, weightNum);   
                    m.setGrade(0);
                    populateAssignmentsList();
                    JOptionPane.showMessageDialog(myFrame, "Assignment has been updated successfully", "Success Info", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(myFrame, "Result and weight have to be greater than 0 and less or equal to 100. Please check your data.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
                }
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
            else if(assignmentsList.getSelectedIndex() == -1)
            {
                JOptionPane.showMessageDialog(myFrame, "No assignment  selected.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                Module tempModule = userModulesManager.getModule(modulesList.getSelectedItem().toString());
                String assignmentName = assignmentsList.getSelectedItem().toString();
                //userModulesManager.removeAssignment(assignmentName);   
                                
                tempModule.removeAssignment(assignmentName);
                tempModule.setGrade(0);
                                
                JOptionPane.showMessageDialog(myFrame, "Assignment has been removed successfully", "SUCCESS info", JOptionPane.INFORMATION_MESSAGE);
                populateAssignmentsList();
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
            assignmentsList.removeAllItems();
            if(modulesList.getSelectedItem() != null)
            {
                Module moduleToRetrieveInfo = userModulesManager.getModule(modulesList.getSelectedItem().toString());
                ArrayList<Assignment> tempAssignmentsList = moduleToRetrieveInfo.getAllAssignments();
                
                for(Assignment tempAssignment : tempAssignmentsList)
                {
                    if(tempAssignment != null)
                    {
                        assignmentsList.addItem(tempAssignment.getTitle());
                    }
                } 
                clearFields();
                assignmentsList.setSelectedIndex(-1);
                assignmentTypesLists.setSelectedIndex(-1);
                
            }
        }
    }

    private class AssignmentsListHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(assignmentsList.getItemCount() != 0 && assignmentsList.getSelectedIndex() != -1)
            {
                Module moduleToRetrieveInfo = userModulesManager.getModule(modulesList.getSelectedItem().toString());
                String selectedAssignment = assignmentsList.getSelectedItem().toString();
                ArrayList<Assignment> tempAssignmentsList = new ArrayList<Assignment>();
                tempAssignmentsList = moduleToRetrieveInfo.getAllAssignments();
                for(Assignment tempAssignment : tempAssignmentsList)
                {
                    if(tempAssignment != null && (tempAssignment.getTitle()).equals(selectedAssignment))
                    {
                        setTempAssignmentTitle(tempAssignment.getTitle());
                        titleInput.setText(tempAssignment.getTitle());
                        
                        for(int typeIndex = 0; typeIndex < assignmentTypesLists.getItemCount(); typeIndex++)
                        {
                            if(assignmentTypesLists.getItemAt(typeIndex).equals(tempAssignment.getType()))
                            {
                                assignmentTypesLists.setSelectedIndex(typeIndex);
                            }
                        }

                        String result = String.valueOf(tempAssignment.getResult());
                        resultInput.setText(result);
                        String weight = String.valueOf(tempAssignment.getWeightPercent());
                        weightPercentInput.setText(weight);      
                        
                    }
                }          
            }
        }
    }
    
    public void populateAssignmentsList()
    {
        //Populating updated assignmentsList:
        assignmentsList.removeAllItems();
        
        Module moduleToRetrieveInfo = userModulesManager.getModule(modulesList.getSelectedItem().toString()); 
        ArrayList<Assignment> tempAssignmentsList = new ArrayList<Assignment>();
        tempAssignmentsList = moduleToRetrieveInfo.getAllAssignments();
        for(Assignment  temp : tempAssignmentsList)
        {
            assignmentsList.addItem(temp.getTitle());
        }
        clearFields();
        assignmentsList.setSelectedIndex(-1);
    }
    
    public void clearFields()
    {
        titleInput.setText("");
        assignmentTypesLists.setSelectedIndex(-1);
        resultInput.setText("");
        weightPercentInput.setText("");
    }
    
    public void setVisible(boolean visibility)
    {
        myFrame.setVisible(visibility);
    }

    public String getTempAssignmentTitle() 
    {
        return tempAssignmentTitle;
    }

    public void setTempAssignmentTitle(String tempAssignmentTitle) 
    {
        this.tempAssignmentTitle = tempAssignmentTitle;
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
}