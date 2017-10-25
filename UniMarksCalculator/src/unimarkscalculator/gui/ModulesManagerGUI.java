/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class ModulesManagerGUI 
{
    private JFrame myFrame = new JFrame("Modules Manager");
    private JLabel modulesManagerLabel = new JLabel("MODULES MANAGER", JLabel.CENTER);
    private JLabel moduleLabel = new JLabel("module: ");
    private JLabel yearLabel = new JLabel("year: ");
    private JLabel semesterLabel = new JLabel("semester: ");
    private JLabel nameLabel = new JLabel("name: ");
    private JLabel creditsLabel = new JLabel("credits: ");
    private JTextField yearInput = new JTextField("");
    private JTextField semesterInput = new JTextField("");
    private JTextField nameInput = new JTextField("");
    private JTextField creditsInput = new JTextField("");
    private JButton deleteModuleButton = new JButton("Delete module");
    private JButton updateModuleButton = new JButton("Update");
    private JButton clearFieldsButton = new JButton("Clear all fields");
    private JComboBox modulesList = new JComboBox();
    
    private ModulesManager userModulesManager = ModulesManager.getInstance();
    
    public ModulesManagerGUI()
    {
        setFrame();
    }
    
    private void setFrame()
    {
        Container contentPane = myFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        Dimension preferredSize = new Dimension(400,300);
        contentPane.setPreferredSize(preferredSize);
        
        // ***** N O R T H
        JPanel northPanel = new JPanel();
        contentPane.add(northPanel, BorderLayout.NORTH);
        northPanel.setLayout(new FlowLayout());
        northPanel.add(modulesManagerLabel);    
        
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
        centerPanel.add(yearLabel, gcCenter);

        gcCenter.gridx = 0; gcCenter.gridy = 2;
        centerPanel.add(semesterLabel, gcCenter);        

        gcCenter.gridx = 0; gcCenter.gridy = 3;
        centerPanel.add(nameLabel, gcCenter);        

        gcCenter.gridx = 0; gcCenter.gridy = 4;
        centerPanel.add(creditsLabel, gcCenter);   
        
        // COLUMN 2:        
        
        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 1; gcCenter.gridy = 0;
        centerPanel.add(modulesList, gcCenter);
        populateModulesList();      
        modulesList.setSelectedIndex(-1);
        modulesList.addActionListener(new ModulesListHandler());
        
        gcCenter.gridx = 1; gcCenter.gridy = 1;
        centerPanel.add(yearInput, gcCenter);
        yearInput.setPreferredSize(new Dimension(50, 25));
        
        gcCenter.gridx = 1; gcCenter.gridy = 2;
        centerPanel.add(semesterInput, gcCenter);
        semesterInput.setPreferredSize(new Dimension(50, 25));
        
        gcCenter.gridx = 1; gcCenter.gridy = 3;
        centerPanel.add(nameInput, gcCenter);
        nameInput.setPreferredSize(new Dimension(100, 25));
        
        gcCenter.gridx = 1; gcCenter.gridy = 4;
        centerPanel.add(creditsInput, gcCenter);        
        creditsInput.setPreferredSize(new Dimension(100, 25));
        
        // COLUMN 3:
        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 2; gcCenter.gridy = 0;
        centerPanel.add(deleteModuleButton, gcCenter);
        deleteModuleButton.addActionListener(new DeleteModuleButtonHandler());
        
        gcCenter.gridx = 2; gcCenter.gridy = 3;
        centerPanel.add(clearFieldsButton, gcCenter);
        clearFieldsButton.addActionListener(new ClearFieldsButtonHandler());
                
        gcCenter.gridx = 2; gcCenter.gridy = 4;
        centerPanel.add(updateModuleButton, gcCenter);
        updateModuleButton.addActionListener(new UpdateModuleButtonHandler());
        
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
        }
    }    
    
    private class UpdateModuleButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(modulesList.getItemCount() == 0 || yearInput.getText().equals("") || semesterInput.getText().equals("") || nameInput.getText().equals("") || creditsInput.getText().equals(""))
            {
                JOptionPane.showMessageDialog(myFrame, "No modules on the list or some fields are empty", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                String year = yearInput.getText();
                String semester = semesterInput.getText();
                String name = nameInput.getText(); 
                String credits = creditsInput.getText();
                int creditsNum = Integer.parseInt(credits);
                String selectedModule = (String) modulesList.getSelectedItem();
                Module m = userModulesManager.getModule(selectedModule);
                m.updateModuleInfo(year, name, semester, creditsNum);
                JOptionPane.showMessageDialog(myFrame, "Module has been updated successfully", "SUCCESS info", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }   
    
    private class DeleteModuleButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(modulesList.getItemCount() == 0)
            {
                JOptionPane.showMessageDialog(myFrame, "No modules on the list.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                String selectedModule = (String) modulesList.getSelectedItem();
                userModulesManager.removeModule(selectedModule);     
                populateModulesList();
                clearFields();
                JOptionPane.showMessageDialog(myFrame, "Module has been removed successfully", "SUCCESS info", JOptionPane.INFORMATION_MESSAGE);
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
                yearInput.setText(moduleToRetrieveInfo.getYear());
                semesterInput.setText(moduleToRetrieveInfo.getSemester());
                nameInput.setText(moduleToRetrieveInfo.getName());
                creditsInput.setText(String.valueOf(moduleToRetrieveInfo.getCredits()));
            }
        }  
    }
    
    public void populateModulesList()
    {
        //Populating updated modulesList:
        modulesList.removeAllItems();
        ArrayList<Module> tempModulesList = userModulesManager.getAllModules();
        for(Module temp : tempModulesList)
        {
            modulesList.addItem(temp.getName());
        }   
    }
    
    public void clearFields()
    {
        yearInput.setText("");
        semesterInput.setText("");
        nameInput.setText("");
        creditsInput.setText("");
    }
    
    public void setVisible(boolean visibility)
    {
        myFrame.setVisible(visibility);
    }
}
