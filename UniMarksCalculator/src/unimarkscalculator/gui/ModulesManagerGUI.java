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
    String[] levelsList = {"4", "5", "6"};
    String[] semestersList = {"A", "B", "C"};
    String[] creditsList = {"15", "30"};
    private JFrame myFrame = new JFrame("Modules Manager");
    private JLabel modulesManagerLabel = new JLabel("MODULES MANAGER", JLabel.CENTER);
    private JLabel moduleLabel = new JLabel("module: ");
    private JLabel levelLabel = new JLabel("level: ");
    private JLabel semesterLabel = new JLabel("semester: ");
    private JLabel nameLabel = new JLabel("name: ");
    private JLabel creditsLabel = new JLabel("credits: ");
    private JTextField levelInput = new JTextField("");
    private JTextField semesterInput = new JTextField("");
    private JTextField nameInput = new JTextField("");
    private JTextField creditsInput = new JTextField("");
    private JButton deleteModuleButton = new JButton("Delete module");
    private JButton updateModuleButton = new JButton("Update");
    private JButton clearFieldsButton = new JButton("Clear all fields");
    private JComboBox modulesList = new JComboBox();
    private JComboBox setLevel = new JComboBox(levelsList);
    private JComboBox setSemester = new JComboBox(semestersList);
    private JComboBox setCredits = new JComboBox(creditsList);
    
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
        centerPanel.add(levelLabel, gcCenter);

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
        centerPanel.add(setLevel, gcCenter);
        setLevel.setSelectedIndex(-1);
        
        gcCenter.gridx = 1; gcCenter.gridy = 2;
        centerPanel.add(setSemester, gcCenter);
        setSemester.setSelectedIndex(-1);
        
        gcCenter.gridx = 1; gcCenter.gridy = 3;
        centerPanel.add(nameInput, gcCenter);
        nameInput.setPreferredSize(new Dimension(100, 25));
        
        gcCenter.gridx = 1; gcCenter.gridy = 4;
        centerPanel.add(setCredits, gcCenter);        
        setCredits.setSelectedIndex(-1);
        
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
            if(modulesList.getItemCount() == 0 || levelInput.getText().equals("") || semesterInput.getText().equals("") || nameInput.getText().equals("") || creditsInput.getText().equals(""))
            {
                JOptionPane.showMessageDialog(myFrame, "No modules on the list or some fields are empty", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                String level = levelInput.getText();
                String semester = semesterInput.getText();
                String name = nameInput.getText(); 
                String credits = creditsInput.getText();
                int creditsNum = Integer.parseInt(credits);
                String selectedModule = (String) modulesList.getSelectedItem();
                Module m = userModulesManager.getModule(selectedModule);
                m.updateModuleInfo(level, name, semester, creditsNum);
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
                String level = moduleToRetrieveInfo.getLevel();
                String semester = moduleToRetrieveInfo.getSemester();
                nameInput.setText(moduleToRetrieveInfo.getName());
                String credits = String.valueOf(moduleToRetrieveInfo.getCredits());
                
                for(int labelIndex = 0; labelIndex < setLevel.getItemCount(); labelIndex++)
                {
                    if(setLevel.getItemAt(labelIndex).equals(level))
                    {
                        setLevel.setSelectedIndex(labelIndex);
                    }                        
                }
                
                for(int semesterIndex = 0; semesterIndex < setSemester.getItemCount(); semesterIndex++)
                {
                    if(setSemester.getItemAt(semesterIndex).equals(semester))
                    {
                        setSemester.setSelectedIndex(semesterIndex);
                    }
                }
                
                for(int creditsIndex = 0; creditsIndex < setCredits.getItemCount(); creditsIndex++)
                {
                    if(setCredits.getItemAt(creditsIndex).equals(credits))
                    {
                        setCredits.setSelectedIndex(creditsIndex);
                    }
                }                
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
        levelInput.setText("");
        semesterInput.setText("");
        nameInput.setText("");
        creditsInput.setText("");
    }
    
    public void setVisible(boolean visibility)
    {
        myFrame.setVisible(visibility);
    }
}