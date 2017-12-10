package unimarkscalculator.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import unimarkscalculator.mainClasses.Assignment;
import unimarkscalculator.mainClasses.Module;
import unimarkscalculator.mainClasses.ModulesManager;

/**
* This class provides functionality on managing existing assignments and their data.
 * @author Lukasz Bol
 */
public class AssignmentsManagerGUI 
{
    private JFrame assignmentsManagerFrame = new JFrame("Assignments Manager GUI");
    private JLabel labelAssignmentsManager = new JLabel("ASSIGNMENTS MANAGER", JLabel.CENTER);
    private JLabel labelModules = new JLabel("modules: ");
    private JLabel labelAssignments = new JLabel("assignments: ");
    private JLabel labelTitle = new JLabel("title: ");
    private JLabel labelType = new JLabel("type: ");
    private JLabel labelResult = new JLabel("result: ");
    private JLabel labelWeightPercents = new JLabel("weight(%): ");
    private JTextField inputTitle = new JTextField("");
    private JTextField inputType = new JTextField("");
    private JTextField inputResult = new JTextField("");
    private JTextField inputWeightPercents = new JTextField("");
    private JButton buttonDeleteAssignment = new JButton("Delete assignment");
    private JButton buttonUpdateModule = new JButton("Update");
    private JButton buttonClearFields = new JButton("Clear all fields");
    private JComboBox dropdownModules = new JComboBox();
    private JComboBox dropdownAssignments = new JComboBox();
    private JComboBox dropdownAssignmentTypes = new JComboBox(new String[] {"coursework", "exam", "test", "other"});
    private String tempAssignmentTitle = "";
    
    private ModulesManager userModulesManager = ModulesManager.getInstance();       // very important, gets instance of the class instead of a new object - SINGLETON PATTERN
    
    public AssignmentsManagerGUI()
    {
        setGUIFrame();
    }
    
    public void setGUIFrame()
    {
        Container contentPane = assignmentsManagerFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        Dimension preferredWindowSize = new Dimension(400,300);
        contentPane.setPreferredSize(preferredWindowSize);
        
        // ***** N O R T H
        JPanel northPanel = new JPanel();
        contentPane.add(northPanel, BorderLayout.NORTH);
        northPanel.setLayout(new FlowLayout());
        northPanel.add(labelAssignmentsManager);
        
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
        centerPanel.add(labelAssignments, gcCenter); 
        
        gcCenter.gridx = 0; gcCenter.gridy = 2;
        centerPanel.add(labelTitle, gcCenter);

        gcCenter.gridx = 0; gcCenter.gridy = 3;
        centerPanel.add(labelType, gcCenter);
        
        gcCenter.gridx = 0; gcCenter.gridy = 4;
        centerPanel.add(labelResult, gcCenter);

        gcCenter.gridx = 0; gcCenter.gridy = 5;
        centerPanel.add(labelWeightPercents, gcCenter);

        // COLUMN 2:        
        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 1; gcCenter.gridy = 0;
        centerPanel.add(dropdownModules, gcCenter);                

        dropdownModules.removeAllItems();
        ArrayList<Module> tempModulesList = userModulesManager.getAllModules();
        for(Module temp : tempModulesList)
        {
            dropdownModules.addItem(temp.getName());
        }
        dropdownModules.setSelectedIndex(-1);
        dropdownModules.addActionListener(new ModulesListHandler());
        
        gcCenter.gridx = 1; gcCenter.gridy = 1;
        centerPanel.add(dropdownAssignments, gcCenter);
        dropdownAssignments.addActionListener(new AssignmentsListHandler());
        dropdownAssignments.setSelectedIndex(-1);
             
        gcCenter.gridx = 1; gcCenter.gridy = 2;
        centerPanel.add(inputTitle, gcCenter);
        inputTitle.setPreferredSize(new Dimension(100, 25));
        
        gcCenter.gridx = 1; gcCenter.gridy = 3;
        centerPanel.add(dropdownAssignmentTypes, gcCenter);   
        dropdownAssignmentTypes.setSelectedIndex(-1);

        gcCenter.gridx = 1; gcCenter.gridy = 4;
        centerPanel.add(inputResult, gcCenter);   
        inputResult.setPreferredSize(new Dimension(50, 25));
        
        gcCenter.gridx = 1; gcCenter.gridy = 5;
        centerPanel.add(inputWeightPercents, gcCenter);
        inputWeightPercents.setPreferredSize(new Dimension(50, 25));

        // COLUMN 3:     
        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 2; gcCenter.gridy = 1; 
        centerPanel.add(buttonDeleteAssignment, gcCenter);
        buttonDeleteAssignment.addActionListener(new DeleteAssignmentButtonHandler());

        gcCenter.gridx = 2; gcCenter.gridy = 4;
        centerPanel.add(buttonClearFields, gcCenter);
        buttonClearFields.addActionListener(new ClearFieldsButtonHandler());
                
        gcCenter.gridx = 2; gcCenter.gridy = 5;
        centerPanel.add(buttonUpdateModule, gcCenter);
        buttonUpdateModule.addActionListener(new UpdateAssignmentButtonHandler());
        
        assignmentsManagerFrame.pack();
        assignmentsManagerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        assignmentsManagerFrame.setAlwaysOnTop(false);
        assignmentsManagerFrame.setResizable(false);
        assignmentsManagerFrame.setLocationRelativeTo(null);    // setting the program in the centre of the screen 
    }
    
    // ***** H A N D L E R S -------------------------------------------------------------------------------------
    private class ClearFieldsButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            clearFields();
            dropdownModules.setSelectedIndex(-1);
            dropdownAssignments.setSelectedIndex(-1);
            dropdownAssignmentTypes.setSelectedIndex(-1);
        }
    }
    
    private class UpdateAssignmentButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            boolean isResultDouble = false;
            boolean isWeightDouble = false;  
            Module selectedModule = null;
            Assignment selectedAssignment = null;
            String assignmentType = "";
            double resultNum = -1;
            double weightNum = -1;
            
            if(dropdownModules.getItemCount() != 0 && dropdownModules.getSelectedIndex() != -1)
            {
                selectedModule = userModulesManager.getModule(dropdownModules.getSelectedItem().toString()); 
                
                if(dropdownAssignments.getItemCount() != 0 && dropdownAssignments.getSelectedIndex() != -1)
                {
                    assignmentType = dropdownAssignmentTypes.getSelectedItem().toString();
                    selectedAssignment = selectedModule.getAssignment(dropdownAssignments.getSelectedItem().toString());
                    String result = inputResult.getText();
                    String weight = inputWeightPercents.getText();
                    
                    isResultDouble = isDouble(result);
                    isWeightDouble = isDouble(weight);
                    
                    if(isResultDouble && isWeightDouble)
                    {
                        resultNum = Double.parseDouble(result);
                        weightNum = Double.parseDouble(weight);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(assignmentsManagerFrame, "Result and weight data must be numbers.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
                    } 
                }
            }
            
            if(dropdownModules.getItemCount() == 0)
            {
                JOptionPane.showMessageDialog(assignmentsManagerFrame, "No modules on the list.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else if (dropdownModules.getSelectedIndex() == -1)
            {
                JOptionPane.showMessageDialog(assignmentsManagerFrame, "No module selected.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else if(dropdownAssignments.getItemCount() == 0)
            {
                JOptionPane.showMessageDialog(assignmentsManagerFrame, "No assignments on the list.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else if(dropdownAssignments.getSelectedIndex() == -1)
            {
                JOptionPane.showMessageDialog(assignmentsManagerFrame, "No assignment selected.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            
            else if(inputTitle.getText().equals("") || dropdownAssignmentTypes.getSelectedIndex() == -1 || inputResult.getText().equals("") || inputWeightPercents.getText().equals(""))
            {
                JOptionPane.showMessageDialog(assignmentsManagerFrame, "Some fields are empty.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else if((inputTitle.getText()).equals(selectedAssignment.getTitle()) &&
                    (dropdownAssignmentTypes.getSelectedItem().toString()).equals(selectedAssignment.getType()) &&
                    resultNum == selectedAssignment.getResult() &&
                    weightNum == selectedAssignment.getWeightPercent())
            {
                JOptionPane.showMessageDialog(assignmentsManagerFrame, "No information has been changed.", "WARNING Info", JOptionPane.WARNING_MESSAGE);
            }
            else
            {             
                String oldTitle = getTempAssignmentTitle();
                String newTitle = inputTitle.getText();
                double totalWeight = (selectedModule.getTotalAssignmentsWeight() - selectedAssignment.getWeightPercent()) + weightNum;
                
                if(totalWeight > 100)
                {
                    JOptionPane.showMessageDialog(assignmentsManagerFrame, "Your total weight of assignments cannot be more than 100. Please check your input data.", "ERROR Info", JOptionPane.ERROR_MESSAGE);    
                }
                else if(totalWeight == 100 && resultNum > 0 && resultNum <= 100 && weightNum > 0 && resultNum <= 100)
                {
                    selectedModule.updateAssignment(oldTitle, newTitle, assignmentType, resultNum, weightNum);   
                    selectedModule.calculateAndSetGrade();
                    reloadUpdatedAssignmentsListForSelectedModule();
                    JOptionPane.showMessageDialog(assignmentsManagerFrame, "Assignment has been updated successfully.\nTotal assignments' weight is equal 100. \nYour grade has been calculated and can be seen in 'View Results' tab.", "Success Info", JOptionPane.INFORMATION_MESSAGE);
                }
                else if(totalWeight < 100 && resultNum > 0 && resultNum <= 100 && weightNum > 0 && resultNum <= 100)
                {
                    selectedModule.updateAssignment(oldTitle, newTitle, assignmentType, resultNum, weightNum);   
                    selectedModule.setGrade(0);
                    reloadUpdatedAssignmentsListForSelectedModule();
                    JOptionPane.showMessageDialog(assignmentsManagerFrame, "Assignment has been updated successfully.", "Success Info", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(assignmentsManagerFrame, "Result and weight have to be greater than 0 and less or equal to 100. Please check your data.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }    
    
    private class DeleteAssignmentButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(dropdownAssignments.getItemCount()== 0)
            {
                JOptionPane.showMessageDialog(assignmentsManagerFrame, "No assignments on the list.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else if(dropdownAssignments.getSelectedIndex() == -1)
            {
                JOptionPane.showMessageDialog(assignmentsManagerFrame, "No assignment  selected.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                Module tempModule = userModulesManager.getModule(dropdownModules.getSelectedItem().toString());
                String assignmentName = dropdownAssignments.getSelectedItem().toString();
                                
                tempModule.removeAssignment(assignmentName);
                tempModule.setGrade(0);
                                
                JOptionPane.showMessageDialog(assignmentsManagerFrame, "Assignment has been removed successfully.", "SUCCESS info", JOptionPane.INFORMATION_MESSAGE);
                reloadUpdatedAssignmentsListForSelectedModule();
            }
        }
    }

    private class ModulesListHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            dropdownAssignments.removeAllItems();
            if(dropdownModules.getSelectedItem() != null)
            {
                Module tempModule = userModulesManager.getModule(dropdownModules.getSelectedItem().toString());
                ArrayList<Assignment> tempAssignmentsList = tempModule.getAllAssignments();
                
                for(Assignment tempAssignment : tempAssignmentsList)
                {
                    if(tempAssignment != null)
                    {
                        dropdownAssignments.addItem(tempAssignment.getTitle());
                    }
                } 
                clearFields();
                dropdownAssignments.setSelectedIndex(-1);
                dropdownAssignmentTypes.setSelectedIndex(-1);
                
            }
        }
    }

    private class AssignmentsListHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(dropdownAssignments.getItemCount() != 0 && dropdownAssignments.getSelectedIndex() != -1)
            {
                Module tempModule = userModulesManager.getModule(dropdownModules.getSelectedItem().toString());
                String selectedAssignment = dropdownAssignments.getSelectedItem().toString();
                ArrayList<Assignment> tempAssignmentsList = new ArrayList<Assignment>();
                tempAssignmentsList = tempModule.getAllAssignments();
                for(Assignment tempAssignment : tempAssignmentsList)
                {
                    if(tempAssignment != null && ((tempAssignment.getTitle()).equals(selectedAssignment)))
                    {
                        setTempAssignmentTitle(tempAssignment.getTitle());
                        inputTitle.setText(tempAssignment.getTitle());
                        
                        for(int assignmentsIndex = 0; assignmentsIndex < dropdownAssignmentTypes.getItemCount(); assignmentsIndex++)
                        {
                            if(dropdownAssignmentTypes.getItemAt(assignmentsIndex).equals(tempAssignment.getType()))
                            {
                                dropdownAssignmentTypes.setSelectedIndex(assignmentsIndex);
                            }
                        }

                        String result = String.valueOf(tempAssignment.getResult());
                        inputResult.setText(result);
                        String weight = String.valueOf(tempAssignment.getWeightPercent());
                        inputWeightPercents.setText(weight);      
                        
                    }
                }          
            }
        }
    }

    // ***** M E T H O D S ---------------------------------------------------------------------------------------  
    public void reloadUpdatedAssignmentsListForSelectedModule()
    {
        dropdownAssignments.removeAllItems();   
        
        Module tempModule = userModulesManager.getModule(dropdownModules.getSelectedItem().toString()); 
        ArrayList<Assignment> tempAssignmentsList = new ArrayList<Assignment>();
        tempAssignmentsList = tempModule.getAllAssignments();
        for(Assignment  tempAssignment : tempAssignmentsList)
        {
            dropdownAssignments.addItem(tempAssignment.getTitle());
        }
        clearFields();
        dropdownAssignments.setSelectedIndex(-1);
    }
    
    public void clearFields()
    {
        inputTitle.setText("");
        dropdownAssignmentTypes.setSelectedIndex(-1);
        inputResult.setText("");
        inputWeightPercents.setText("");
    }

    public String getTempAssignmentTitle() 
    {
        return tempAssignmentTitle;
    }

    public void setTempAssignmentTitle(String newtempAssignmentTitle) 
    {
        this.tempAssignmentTitle = newtempAssignmentTitle;
    }

    private boolean isDouble(String numberToCheckIfDouble)
    {
        boolean isValidInteger = false;
        
        try
        {
            Double.parseDouble(numberToCheckIfDouble);
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
        assignmentsManagerFrame.setVisible(visibility);
    }
}