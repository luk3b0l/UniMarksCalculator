package unimarkscalculator.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import unimarkscalculator.mainClasses.Module;
import unimarkscalculator.mainClasses.ModulesManager;

/**
 * This class provides functionality on managing existing modules and their data.
 * @author Lukasz Bol
 */
public class ModulesManagerGUI 
{
    private JFrame modulesManagerFrame = new JFrame("Modules Manager");
    private JLabel labelModulesManager = new JLabel("MODULES MANAGER", JLabel.CENTER);
    private JLabel labelModule = new JLabel("module: ");
    private JLabel labelLevel = new JLabel("level: ");
    private JLabel labelSemester = new JLabel("semester: ");
    private JLabel labelName = new JLabel("name: ");
    private JLabel labelCredits = new JLabel("credits: ");
    private JTextField inputLevel = new JTextField("");
    private JTextField inputSemester = new JTextField("");
    private JTextField inputName = new JTextField("");
    private JTextField inputCredits = new JTextField("");
    private JButton buttonDeleteModule = new JButton("Delete module");
    private JButton buttonUpdateModule = new JButton("Update");
    private JButton buttonClearFields = new JButton("Clear all fields");
    private JComboBox dropdownModules = new JComboBox();
    private JComboBox dropdownLevels = new JComboBox(new String[] {"4", "5", "6"});
    private JComboBox dropdownSemester = new JComboBox(new String[] {"A", "B", "AB"});
    private JComboBox dropdownCredits = new JComboBox(new String[] {"15", "30"});
    
    private ModulesManager userModulesManager = ModulesManager.getInstance();
    
    public ModulesManagerGUI()
    {
        setGUIFrame();
    }
    
    private void setGUIFrame()
    {
        Container contentPane = modulesManagerFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        Dimension preferredWindowSize = new Dimension(400,300);
        contentPane.setPreferredSize(preferredWindowSize);
        
        // ***** N O R T H
        JPanel northPanel = new JPanel();
        contentPane.add(northPanel, BorderLayout.NORTH);
        northPanel.setLayout(new FlowLayout());
        northPanel.add(labelModulesManager);    
        
        // ***** C E N T E R
        JPanel centerPanel = new JPanel();
        contentPane.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gcCenter = new GridBagConstraints();
        gcCenter.weightx = 0.5; gcCenter.weighty = 0.5;
        
        // COLUMN 1:
        gcCenter.anchor = GridBagConstraints.LINE_END;
        gcCenter.gridx = 0; gcCenter.gridy = 0;
        centerPanel.add(labelModule, gcCenter);
        
        gcCenter.gridx = 0; gcCenter.gridy = 1;
        centerPanel.add(labelLevel, gcCenter);

        gcCenter.gridx = 0; gcCenter.gridy = 2;
        centerPanel.add(labelSemester, gcCenter);        

        gcCenter.gridx = 0; gcCenter.gridy = 3;
        centerPanel.add(labelName, gcCenter);        

        gcCenter.gridx = 0; gcCenter.gridy = 4;
        centerPanel.add(labelCredits, gcCenter);   
        
        // COLUMN 2:        
        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 1; gcCenter.gridy = 0;
        centerPanel.add(dropdownModules, gcCenter);
        populateModulesList();      
        dropdownModules.addActionListener(new ModulesListHandler());
        
        gcCenter.gridx = 1; gcCenter.gridy = 1;
        centerPanel.add(dropdownLevels, gcCenter);
        dropdownLevels.setSelectedIndex(-1);
        
        gcCenter.gridx = 1; gcCenter.gridy = 2;
        centerPanel.add(dropdownSemester, gcCenter);
        dropdownSemester.setSelectedIndex(-1);
        
        gcCenter.gridx = 1; gcCenter.gridy = 3;
        centerPanel.add(inputName, gcCenter);
        inputName.setPreferredSize(new Dimension(100, 25));
        
        gcCenter.gridx = 1; gcCenter.gridy = 4;
        centerPanel.add(dropdownCredits, gcCenter);        
        dropdownCredits.setSelectedIndex(-1);
        
        // COLUMN 3:
        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 2; gcCenter.gridy = 0;
        centerPanel.add(buttonDeleteModule, gcCenter);
        buttonDeleteModule.addActionListener(new DeleteModuleButtonHandler());
        
        gcCenter.gridx = 2; gcCenter.gridy = 3;
        centerPanel.add(buttonClearFields, gcCenter);
        buttonClearFields.addActionListener(new ClearFieldsButtonHandler());
                
        gcCenter.gridx = 2; gcCenter.gridy = 4;
        centerPanel.add(buttonUpdateModule, gcCenter);
        buttonUpdateModule.addActionListener(new UpdateModuleButtonHandler());
        
        modulesManagerFrame.pack();
        modulesManagerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        modulesManagerFrame.setAlwaysOnTop(false);
        modulesManagerFrame.setResizable(false);
        modulesManagerFrame.setLocationRelativeTo(null);    // setting the program in the centre of the screen        
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
    
    private class UpdateModuleButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            Module tempModule = null;
            int creditsNum = -1;
            if(dropdownModules.getItemCount() != 0 && dropdownModules.getSelectedIndex() != -1)
            {
                String selectedModule = dropdownModules.getSelectedItem().toString();
                tempModule = userModulesManager.getModule(selectedModule);
                
                if(dropdownCredits.getSelectedIndex() != -1)
                {
                    String credits = dropdownCredits.getSelectedItem().toString();
                    creditsNum = Integer.parseInt(credits);
                }
            }
            
            if(dropdownModules.getItemCount() == 0 )
            {
                JOptionPane.showMessageDialog(modulesManagerFrame, "No modules on the list", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else if(dropdownModules.getSelectedIndex() == -1 || dropdownLevels.getSelectedIndex() == -1 || dropdownSemester.getSelectedIndex() == -1 || inputName.getText().equals("") || dropdownCredits.getSelectedIndex() == -1)
            {
                JOptionPane.showMessageDialog(modulesManagerFrame, "Some fields are empty.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else if(
                    dropdownLevels.getSelectedItem().toString().equals(tempModule.getLevel()) && 
                    dropdownSemester.getSelectedItem().toString().equals(tempModule.getSemester()) && 
                    inputName.getText().equals(tempModule.getName()) &&
                    (creditsNum == tempModule.getCredits())
                    ) 
            {
                JOptionPane.showMessageDialog(modulesManagerFrame, "No information has been changed.", "WARNING Info", JOptionPane.WARNING_MESSAGE);
            }        
            else
            {
                String level = dropdownLevels.getSelectedItem().toString();
                String semester = dropdownSemester.getSelectedItem().toString();
                String name = inputName.getText();
                tempModule.updateModuleInfo(level, name, semester, creditsNum);
                populateModulesList();
                JOptionPane.showMessageDialog(modulesManagerFrame, "Module has been updated successfully", "SUCCESS info", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }   
    
    private class DeleteModuleButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(dropdownModules.getItemCount() == 0)
            {
                JOptionPane.showMessageDialog(modulesManagerFrame, "No modules on the list.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else if(dropdownModules.getSelectedIndex() == -1)
            {
                JOptionPane.showMessageDialog(modulesManagerFrame, "No module selected.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                String selectedModule = dropdownModules.getSelectedItem().toString();
                userModulesManager.removeModule(selectedModule);     
                populateModulesList();
                JOptionPane.showMessageDialog(modulesManagerFrame, "Module has been removed successfully", "SUCCESS info", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private class ModulesListHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(dropdownModules.getSelectedItem() != null)
            {
                Module selectedModule = userModulesManager.getModule(dropdownModules.getSelectedItem().toString());
                String level = selectedModule.getLevel();
                String semester = selectedModule.getSemester();
                inputName.setText(selectedModule.getName());
                String credits = String.valueOf(selectedModule.getCredits());
                
                for(int labelIndex = 0; labelIndex < dropdownLevels.getItemCount(); labelIndex++)
                {
                    if(dropdownLevels.getItemAt(labelIndex).equals(level))
                    {
                        dropdownLevels.setSelectedIndex(labelIndex);
                    }                        
                }
                
                for(int semesterIndex = 0; semesterIndex < dropdownSemester.getItemCount(); semesterIndex++)
                {
                    if(dropdownSemester.getItemAt(semesterIndex).equals(semester))
                    {
                        dropdownSemester.setSelectedIndex(semesterIndex);
                    }
                }
                
                for(int creditsIndex = 0; creditsIndex < dropdownCredits.getItemCount(); creditsIndex++)
                {
                    if(dropdownCredits.getItemAt(creditsIndex).equals(credits))
                    {
                        dropdownCredits.setSelectedIndex(creditsIndex);
                    }
                }                
            }
        }  
    }
    
    // ***** M E T H O D S ---------------------------------------------------------------------------------------       
    public void populateModulesList()
    {
        dropdownModules.removeAllItems();
        ArrayList<Module> tempModulesList = userModulesManager.getAllModules();
        for(Module tempModule : tempModulesList)
        {
            dropdownModules.addItem(tempModule.getName());
        }   
        clearFields();
    }
    
    public void clearFields()
    {
        dropdownModules.setSelectedIndex(-1);
        dropdownLevels.setSelectedIndex(-1);
        dropdownSemester.setSelectedIndex(-1);
        inputName.setText("");
        dropdownCredits.setSelectedIndex(-1);
    }
    
    public void setWindowVisible(boolean visibility)
    {
        modulesManagerFrame.setVisible(visibility);
    }
}