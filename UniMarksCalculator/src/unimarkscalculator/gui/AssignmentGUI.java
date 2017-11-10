package unimarkscalculator.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import unimarkscalculator.mainClasses.Module;
import unimarkscalculator.mainClasses.ModulesManager;


/**
 *
 * @author Lukasz Bol
 */
public class AssignmentGUI 
{
    private JFrame myFrame = new JFrame("Add Assignment");
    private JLabel addAssignmentLabel = new JLabel("ADD ASSIGNMENT", JLabel.CENTER);
    private JLabel modulesLabel = new JLabel("module: ");
    private JLabel titleLabel = new JLabel("title: ");
    private JLabel typeLabel = new JLabel("type: ");
    private JLabel resultLabel = new JLabel("result: ");
    private JLabel weightPercentLabel = new JLabel("weight(%): ");
    private JTextField titleInput = new JTextField("");
    private JTextField typeInput = new JTextField("");
    private JTextField resultInput = new JTextField("");
    private JTextField weightPercentInput = new JTextField("");
    private JButton addAssignmentButton = new JButton("Add");
    private JButton clearFieldsButton = new JButton("Clear all fields");
    private JComboBox modulesList = new JComboBox();

    private ModulesManager userModulesManager = ModulesManager.getInstance();
    
    public AssignmentGUI()
    {
        setFrame();
    }
    
    private void setFrame()
    {
        Container contentPane = myFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        Dimension preferredSize = new Dimension(400, 300);
        myFrame.setPreferredSize(preferredSize);
        
        // ***** N O R T H
        JPanel northPanel = new JPanel();
        contentPane.add(northPanel, BorderLayout.NORTH);
        northPanel.setLayout(new FlowLayout());
        northPanel.add(addAssignmentLabel);

        // ***** C E N T E R
        JPanel centerPanel = new JPanel();
        contentPane.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gcCenter = new GridBagConstraints();
        gcCenter.weightx = 0.5; gcCenter.weighty = 0.5;
        
        // COLUMN 1:
        gcCenter.anchor = GridBagConstraints.LINE_END;
        gcCenter.gridx = 0; gcCenter.gridy = 0;
        centerPanel.add(modulesLabel, gcCenter);

        gcCenter.gridx = 0; gcCenter.gridy = 1;
        centerPanel.add(titleLabel, gcCenter);        

        gcCenter.gridx = 0; gcCenter.gridy = 2;
        centerPanel.add(typeLabel, gcCenter);        

        gcCenter.gridx = 0; gcCenter.gridy = 3;
        centerPanel.add(resultLabel, gcCenter);           

        gcCenter.gridx = 0; gcCenter.gridy = 4;
        centerPanel.add(weightPercentLabel, gcCenter); 
        
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
        
        gcCenter.gridx = 1; gcCenter.gridy = 1;
        centerPanel.add(titleInput, gcCenter);
        titleInput.setPreferredSize(new Dimension(100, 25));
        
        gcCenter.gridx = 1; gcCenter.gridy = 2;
        centerPanel.add(typeInput, gcCenter);        
        typeInput.setPreferredSize(new Dimension(100, 25));
        
        gcCenter.gridx = 1; gcCenter.gridy = 3;
        centerPanel.add(resultInput, gcCenter);        
        resultInput.setPreferredSize(new Dimension(50, 25));
        
        gcCenter.gridx = 1; gcCenter.gridy = 4;
        centerPanel.add(weightPercentInput, gcCenter);        
        weightPercentInput.setPreferredSize(new Dimension(50, 25));
        
        // COLUMN 3:    
        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 2; gcCenter.gridy = 3;
        centerPanel.add(clearFieldsButton, gcCenter);       
        clearFieldsButton.addActionListener(new ClearFieldsButtonHandler());
        
        gcCenter.gridx = 2; gcCenter.gridy = 4;
        centerPanel.add(addAssignmentButton, gcCenter);                
        addAssignmentButton.addActionListener(new AddAssignmentButtonHandler());
        
        myFrame.pack();
        myFrame.setAlwaysOnTop(false);
        myFrame.setResizable(false);
        myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        }
    }    
    
    private class AddAssignmentButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            boolean isResultDouble = false;
            boolean isWeightDouble = false;            
            
            if(modulesList.getItemCount() == 0 || titleInput.getText().equals("") || typeInput.getText().equals("") || resultInput.getText().equals("") || weightPercentInput.getText().equals(""))
            {
                JOptionPane.showMessageDialog(myFrame, "No modules on the list or some fields are empty", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                String title = titleInput.getText();
                String type = typeInput.getText();
                String result = resultInput.getText(); 
                String weightPercent = weightPercentInput.getText();
                String selectedModule = (String) modulesList.getSelectedItem();
                
                isResultDouble = isDouble(result);
                isWeightDouble = isDouble(weightPercent);
                
                if(isResultDouble == true && isWeightDouble == true)
                {
                    double resultNum = Double.parseDouble(result);
                    double weightPercentNum = Double.parseDouble(weightPercent);
                    
                    Module m = userModulesManager.getModule(selectedModule);
                    m.addAssignment(title, type, resultNum, weightPercentNum);
                    clearFields();  //Clearing the input fields for next data input
                    JOptionPane.showMessageDialog(myFrame, "Assignment has been added successfully", "Success Info", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(myFrame, "Some of the data input are incorrect", "ERROR Info", JOptionPane.ERROR_MESSAGE);
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