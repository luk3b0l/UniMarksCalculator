/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unimarkscalculator.gui;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 *
 * @author Lukasz Bol
 */
public class ResultsGUI 
{
    private JFrame myFrame = new JFrame("View Results");
    private JLabel resultsLabel = new JLabel("RESULTS", JLabel.CENTER);
    private JLabel levelLabel = new JLabel("level: ");
    private JLabel modulesListLabel = new JLabel("modules list: ");
    private JLabel assignmentsListLabel = new JLabel("assignments list: ");
    private JLabel modulesForCalculationLabel = new JLabel("modules chosen for calculation: ");
    private JLabel finalGradeLabel = new JLabel("Final Grade: ");
    private JButton showAllButton = new JButton("Show all results");
    private JButton calculateFinalGradeButton = new JButton("Calculate FINAL GRADE");
    private JButton printResultsButton = new JButton("Print all results");
    private JTextField finalGradeOutput = new JTextField("");
    private JComboBox levelsList = new JComboBox();

    
    public ResultsGUI()
    {
        setFrame();
    }
    
    private void setFrame()
    {
        Container contentPane = myFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        Dimension preferredSize = new Dimension(600, 600);
        myFrame.setPreferredSize(preferredSize);
        
        // ***** N O R T H
        JPanel northPanel = new JPanel();
        contentPane.add(northPanel, BorderLayout.NORTH);
        northPanel.setLayout(new FlowLayout());
        northPanel.add(resultsLabel);
        
        // ***** C E N T E R
        JPanel centerPanel = new JPanel();
        contentPane.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gcCenter = new GridBagConstraints();
        gcCenter.weightx = 0.5; gcCenter.weighty = 0.5;
        
        // COLUMN 1:
        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 0; gcCenter.gridy = 0;
        
        // Creating MODULES table:
        String[] modulesColumnNames = {"name", "semester", "credits", "completed(%)", "grade"};
        Object[][] modulesData = {
            {"Artificial Intelligence", "A", new Integer(15), new Integer(100), new Integer(85)},
            {"Cybersecurity", "B", new Integer(15), new Integer (85), "---"}
        };
        JTable table = new JTable(modulesData, modulesColumnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        centerPanel.add(table, gcCenter);
        
        
        gcCenter.gridx = 0; gcCenter.gridy = 1;      
        //centerPanel.add(..., gcCenter);

        
        
        // ***** W E S T
        JPanel westPanel = new JPanel();
        contentPane.add(westPanel, BorderLayout.WEST);
        westPanel.setLayout(new GridBagLayout());
        GridBagConstraints gcWest = new GridBagConstraints();
        gcWest.weightx = 0.5; gcWest.weighty = 0.5;
        
        // COLUMN 1:
        gcWest.anchor = GridBagConstraints.CENTER;
        gcWest.gridx = 0; gcWest.gridy = 0;
        westPanel.add(showAllButton, gcWest);
        
        gcWest.gridx = 0; gcWest.gridy = 1;
        westPanel.add(printResultsButton, gcWest);
        
        gcWest.gridx = 0; gcWest.gridy = 2;
        westPanel.add(levelLabel, gcWest);
        
        // COLUMN 2:
        gcWest.anchor = GridBagConstraints.LINE_START;
        gcWest.gridx = 1; gcWest.gridy = 2;
        westPanel.add(levelsList, gcWest);

        // ***** S O U T H
        JPanel southPanel = new JPanel();
        contentPane.add(southPanel, BorderLayout.SOUTH);
        southPanel.setLayout(new GridBagLayout());
        GridBagConstraints gcSouth = new GridBagConstraints();
        gcSouth.weightx = 0.5; gcSouth.weighty = 0.5;
        
        // COLUMN 1:
        gcSouth.anchor = GridBagConstraints.LINE_START;
        gcSouth.gridx = 0; gcSouth.gridy = 0;
        southPanel.add(calculateFinalGradeButton, gcSouth);
 
        gcSouth.gridx = 0; gcSouth.gridy = 1;
        southPanel.add(finalGradeLabel, gcSouth);
        
        gcSouth.gridx = 1; gcSouth.gridy = 1;
        southPanel.add(finalGradeOutput, gcSouth);
        finalGradeOutput.setPreferredSize(new Dimension(50, 25));
        
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

    public static void main (String[] args)
    {
        new ResultsGUI().setVisible(true);
    }
}
