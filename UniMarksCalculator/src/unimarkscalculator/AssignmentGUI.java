/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unimarkscalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.Event;


/**
 *
 * @author Lukasz Bol
 */
public class AssignmentGUI 
{
    private JFrame myFrame = new JFrame("Add Assignment");
    private JLabel addAssignmentLabel = new JLabel("ADD ASSIGNMENT", JLabel.CENTER);
    private JLabel moduleLabel = new JLabel("module: ");
    private JLabel titleLabel = new JLabel("title: ");
    private JLabel typeLabel = new JLabel("type: ");
    private JLabel resultLabel = new JLabel("result: ");
    private JLabel weightPercentLabel = new JLabel("weight(%): ");
    private JTextField titleInput = new JTextField("");
    private JTextField typeInput = new JTextField("");
    private JTextField resultInput = new JTextField("");
    private JTextField weightPercentInput = new JTextField("");
    private JButton addAssignmentButton = new JButton("Add");
    private JButton clearDataButton = new JButton("Clear data");
    private JComboBox modulesList = new JComboBox();
    
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
        centerPanel.add(moduleLabel, gcCenter);
        
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
        centerPanel.add(clearDataButton, gcCenter);        
        
        gcCenter.gridx = 2; gcCenter.gridy = 4;
        centerPanel.add(addAssignmentButton, gcCenter);                
        
        myFrame.pack();
        myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myFrame.setLocationRelativeTo(null);    // setting the program in the centre of the screen        
    }
    
    public void setVisible(boolean visibility)
    {
        myFrame.setVisible(visibility);
    }
}
