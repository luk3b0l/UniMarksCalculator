/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unimarkscalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



/**
 *
 * @author Lukasz BOl
 */
public class ModuleGUI 
{
    private JFrame myFrame = new JFrame("Add Module");
    private JLabel addModuleLabel = new JLabel("ADD MODULE", JLabel.CENTER);
    private JLabel yearLabel = new JLabel("year: ");
    private JLabel semesterLabel = new JLabel("semester: ");
    private JLabel nameLabel = new JLabel("name: ");
    private JLabel creditsLabel = new JLabel("credits: ");
    private JTextField yearInput = new JTextField("");
    private JTextField semesterInput = new JTextField("");
    private JTextField nameInput = new JTextField("");
    private JTextField creditsInput = new JTextField("");
    private JButton addModuleButton = new JButton("Add");
    private JButton clearFieldsButton = new JButton("Clear all fields");
    
    private ModulesManager userModulesManager = ModulesManager.getInstance();
    
    public ModuleGUI()
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
        northPanel.add(addModuleLabel);

        // ***** C E N T E R
        JPanel centerPanel = new JPanel();
        contentPane.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gcCenter = new GridBagConstraints();
        gcCenter.weightx = 0.5; gcCenter.weighty = 0.5;
        
        // COLUMN 1:
        gcCenter.anchor = GridBagConstraints.LINE_END;
        gcCenter.gridx = 0; gcCenter.gridy = 0;
        centerPanel.add(yearLabel, gcCenter);

        gcCenter.gridx = 0; gcCenter.gridy = 1;
        centerPanel.add(semesterLabel, gcCenter);        

        gcCenter.gridx = 0; gcCenter.gridy = 2;
        centerPanel.add(nameLabel, gcCenter);        

        gcCenter.gridx = 0; gcCenter.gridy = 3;
        centerPanel.add(creditsLabel, gcCenter);   
        
        // COLUMN 2:        
        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 1; gcCenter.gridy = 0;
        centerPanel.add(yearInput, gcCenter);
        yearInput.setPreferredSize(new Dimension(50, 25));
        
        gcCenter.gridx = 1; gcCenter.gridy = 1;
        centerPanel.add(semesterInput, gcCenter);
        semesterInput.setPreferredSize(new Dimension(50, 25));
        
        gcCenter.gridx = 1; gcCenter.gridy = 2;
        centerPanel.add(nameInput, gcCenter);
        nameInput.setPreferredSize(new Dimension(100, 25));
        
        gcCenter.gridx = 1; gcCenter.gridy = 3;
        centerPanel.add(creditsInput, gcCenter);        
        creditsInput.setPreferredSize(new Dimension(100, 25));
        
        // COLUMN 3:
        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 2; gcCenter.gridy = 3;
        centerPanel.add(clearFieldsButton, gcCenter);
        clearFieldsButton.addActionListener(new clearFieldsButtonHandler());
                
        gcCenter.gridx = 2; gcCenter.gridy = 4;
        centerPanel.add(addModuleButton, gcCenter);
        addModuleButton.addActionListener(new addModuleButtonHandler());
        
        myFrame.pack();
        myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myFrame.setAlwaysOnTop(false);
        myFrame.setResizable(false);
        myFrame.setLocationRelativeTo(null);    // setting the program in the centre of the screen
    }
    
    // ---------------------------------------------------------------------------------------------------
    
    // ***** BUTTON HANDLERS:
    private class clearFieldsButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            yearInput.setText("");
            semesterInput.setText("");
            nameInput.setText("");
            creditsInput.setText("");
        }
    }    
    
    private class addModuleButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            String year = yearInput.getText();
            String semester = semesterInput.getText();
            String name = nameInput.getText(); 
            String credits = creditsInput.getText();
            int creditsNum = Integer.parseInt(credits);
            userModulesManager.addModule(year, name, semester, creditsNum);
            System.out.println(userModulesManager.getDescription());
            System.out.println(userModulesManager.getAllModulesString());
            // TODO
        }
    }
    
    public void setVisible(boolean visibility)
    {
        myFrame.setVisible(visibility);
    }
}
