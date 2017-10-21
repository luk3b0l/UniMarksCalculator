/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unimarkscalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.Event;
import java.util.ArrayList;

/**
 *
 * @author xxx
 */
public class AssignmentsManagerGUI 
{
    private JFrame myFrame = new JFrame("Assignments Manager GUI");
    private JLabel assignmentsManagerLabel = new JLabel("ASSIGNMENTS MANAGER", JLabel.CENTER);
    private JLabel moduleLabel = new JLabel("module: ");
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
    
    private ModulesManager userModulesManager = ModulesManager.getInstance();
    
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
        centerPanel.add(titleLabel, gcCenter);

        gcCenter.gridx = 0; gcCenter.gridy = 2;
        centerPanel.add(typeLabel, gcCenter);
        
        gcCenter.gridx = 0; gcCenter.gridy = 3;
        centerPanel.add(resultLabel, gcCenter);

        gcCenter.gridx = 0; gcCenter.gridy = 4;
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
        
        // TODO here dropdown menu for assignments need to be added
        gcCenter.gridx = 1; gcCenter.gridy = 1;
        centerPanel.add(titleInput, gcCenter);      // TODO here dropdown menu for assignments need to be added  
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
        gcCenter.gridx = 2; gcCenter.gridy = 1; 
        centerPanel.add(deleteAssignmentButton);
        //deleteAssignmentButton.addActionListener(new DeleteAssignmentButtonHandler());

        gcCenter.gridx = 2; gcCenter.gridy = 3;
        centerPanel.add(clearFieldsButton, gcCenter);
        //clearFieldsButton.addActionListener(new ClearFieldsButtonHandler());
                
        gcCenter.gridx = 2; gcCenter.gridy = 4;
        centerPanel.add(updateModuleButton, gcCenter);
        //updateModuleButton.addActionListener(new UpdateModuleButtonHandler());
        
        myFrame.pack();
        myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myFrame.setAlwaysOnTop(false);
        myFrame.setResizable(false);
        myFrame.setLocationRelativeTo(null);    // setting the program in the centre of the screen 
    }
    
    public void setVisible(boolean visibility)
    {
        myFrame.setVisible(visibility);
    }
}
