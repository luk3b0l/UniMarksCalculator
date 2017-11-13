package unimarkscalculator.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import unimarkscalculator.mainClasses.ModulesManager;

/**
 *
 * @author Lukasz BOl
 */
public class ModuleGUI 
{
    private JFrame myFrame = new JFrame("Add Module");
    private JLabel addModuleLabel = new JLabel("ADD MODULE", JLabel.CENTER);
    private JLabel levelLabel = new JLabel("level: ");
    private JLabel semesterLabel = new JLabel("semester: ");
    private JLabel nameLabel = new JLabel("name: ");
    private JLabel creditsLabel = new JLabel("credits: ");
    private JTextField levelInput = new JTextField("");
    private JTextField semesterInput = new JTextField("");
    private JTextField nameInput = new JTextField("");
    private JTextField creditsInput = new JTextField("");
    private JButton addModuleButton = new JButton("Add");
    private JButton clearFieldsButton = new JButton("Clear all fields");
    private JComboBox semestersList = new JComboBox(new String[] {"A", "B", "C"});
    private JComboBox levelsList = new JComboBox(new String[] {"4", "5", "6"});
    private JComboBox creditsList = new JComboBox(new String[] {"15", "30"});
    
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
        centerPanel.add(levelLabel, gcCenter);

        gcCenter.gridx = 0; gcCenter.gridy = 1;
        centerPanel.add(semesterLabel, gcCenter);        

        gcCenter.gridx = 0; gcCenter.gridy = 2;
        centerPanel.add(nameLabel, gcCenter);        

        gcCenter.gridx = 0; gcCenter.gridy = 3;
        centerPanel.add(creditsLabel, gcCenter);   
        
        // COLUMN 2:        
        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 1; gcCenter.gridy = 0;
        centerPanel.add(levelsList, gcCenter);
        levelsList.setSelectedIndex(-1);
        
        gcCenter.gridx = 1; gcCenter.gridy = 1;
        centerPanel.add(semestersList, gcCenter);
        semestersList.setSelectedIndex(-1);
        
        gcCenter.gridx = 1; gcCenter.gridy = 2;
        centerPanel.add(nameInput, gcCenter);
        nameInput.setPreferredSize(new Dimension(100, 25));
        
        gcCenter.gridx = 1; gcCenter.gridy = 3;
        centerPanel.add(creditsList, gcCenter);   
        creditsList.setSelectedIndex(-1);
        
        // COLUMN 3:
        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 2; gcCenter.gridy = 3;
        centerPanel.add(clearFieldsButton, gcCenter);
        clearFieldsButton.addActionListener(new ClearFieldsButtonHandler());
                
        gcCenter.gridx = 2; gcCenter.gridy = 4;
        centerPanel.add(addModuleButton, gcCenter);
        addModuleButton.addActionListener(new AddModuleButtonHandler());
        
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
    
    private class AddModuleButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            boolean isCreditsInteger = false;
            if(levelsList.getSelectedIndex() == -1 || semestersList.getSelectedIndex() == -1 || nameInput.getText().equals("") || creditsList.getSelectedIndex() == -1)
            {
                JOptionPane.showMessageDialog(myFrame, "Some fields are empty", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                String level = levelsList.getSelectedItem().toString();
                String semester = semestersList.getSelectedItem().toString();
                String name = nameInput.getText(); 
                String credits = creditsList.getSelectedItem().toString();             
                int creditsNum = Integer.parseInt(credits);
                
                userModulesManager.addModule(level, name, semester, creditsNum);
                clearFields();  //Clearing the input fields for next data input
                JOptionPane.showMessageDialog(myFrame, "Module has been added successfully", "SUCCESS info", JOptionPane.INFORMATION_MESSAGE);
            }           
        }
    }
    
    public void clearFields()
    {
        levelsList.setSelectedIndex(-1);
        semestersList.setSelectedIndex(-1);
        creditsList.setSelectedIndex(-1);
        nameInput.setText("");
    }
    public void setVisible(boolean visibility)
    {
        myFrame.setVisible(visibility);
    }
}