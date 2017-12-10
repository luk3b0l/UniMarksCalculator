package unimarkscalculator.gui;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import unimarkscalculator.mainClasses.Assignment;
import unimarkscalculator.mainClasses.Module;
import unimarkscalculator.mainClasses.ModulesManager;

/**
 * This class provides functionality on showing all added results (modules, assignments),
 * allows to see separate modules' grades, and calculate Final Grade.
 * @author Lukasz Bol
 */
public class ResultsGUI 
{
    private JFrame resultsFrame = new JFrame("View Results");
    private JLabel labelResults = new JLabel("RESULTS", JLabel.CENTER);
    private JLabel labelModulesList = new JLabel("MODULES");
    private JLabel labelAssignmentsList = new JLabel("ASSIGNMENTS");
    private JLabel labelFinalGrade = new JLabel("Final Grade: ");
    private JButton buttonCalculateFinalGrade = new JButton("Calculate FINAL GRADE");
    private JButton buttonPrintResults = new JButton("Print all results");
    private JTextField outputFinalGrade = new JTextField("");
    private JTable tableModules;
    private JTable tableAssignments;
    
    private Object[][] dataModules;
    private Object[][] dataAssignments;
    private String[] columnsOfModulesData;
    private String[] columnsOfAssignmentsData;
    private ModulesManager userModulesManager = ModulesManager.getInstance();
    private ArrayList<Module> listModules = new ArrayList<>();
    private ArrayList<Assignment> listAssignments = new ArrayList<>();
    
    private DefaultTableModel modelModulesTable;
    private DefaultTableModel modelAssignmentsTable;
    
    private int tempModuleRow = -1;
    
    public ResultsGUI()
    {
        setGUIFrame();
    }
    
    private void setGUIFrame()
    {
        Container contentPane = resultsFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        Dimension preferredWindowSize = new Dimension(511, 600);
        resultsFrame.setPreferredSize(preferredWindowSize);
        
        // ***** N O R T H
        JPanel northPanel = new JPanel();
        contentPane.add(northPanel, BorderLayout.NORTH);
        northPanel.setLayout(new FlowLayout());
        northPanel.add(labelResults);
 
        // ***** C E N T E R
        JPanel centerPanel = new JPanel();
        contentPane.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gcCenter = new GridBagConstraints();
        gcCenter.weightx = 0.5; gcCenter.weighty = 50;
        
        // COLUMN 1:
        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 0; gcCenter.gridy = 0;
        centerPanel.add(buttonPrintResults, gcCenter);

        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 0; gcCenter.gridy = 2;
        gcCenter.weighty = 5;
        centerPanel.add(labelModulesList, gcCenter);

        // ***** MODULES TABLE ---------------------------------------------------------------------------------
        gcCenter.gridx = 0; gcCenter.gridy = 3;
        String[] columnsOfModulesData = {"Module Title", "Credits", "Semester", "Grade", "Level", "Selected"}; 
        modelModulesTable = new DefaultTableModel(dataModules, columnsOfModulesData)
        {
            public Class<?> getColumnClass(int column)
            {
                switch(column)
                {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    case 3:
                        return String.class;
                    case 4:
                        return String.class;
                    case 5:
                        return Boolean.class;
                    default:
                        return String.class;
                }
//            public Class getColumnClass(int column)
//            {
//                Class returnValue;
//
//                if((column >= 0) && (column < getColumnCount()))
//                {
//                    returnValue = getValueAt(0, column).getClass();
//                }
//                else
//                {
//                    returnValue = Object.class;
//                }
//                return returnValue;
            }
        };
        Object[] newModuleToBeAdded;
        listModules = userModulesManager.getAllModules();
        for(Module tempModule : listModules)
        {
            newModuleToBeAdded = new Object[]{tempModule.getName(), tempModule.getCredits(), tempModule.getSemester(), tempModule.getGrade(), tempModule.getLevel(), false};
            modelModulesTable.addRow(newModuleToBeAdded);
        }
        
        tableModules = new JTable(modelModulesTable)
        {
            @Override
            public boolean isCellEditable(int data, int columns)
            {
                return false;
            }            
        };
        tableModules.setPreferredScrollableViewportSize(new Dimension(500,150));
        tableModules.setFillsViewportHeight(true);
        tableModules.setAutoCreateRowSorter(true);      
        JScrollPane modulesScrollPane = new JScrollPane(tableModules);
        centerPanel.add(modulesScrollPane, gcCenter);     
        tableModules.getSelectionModel().addListSelectionListener(new ModulesListSelectionListener());
        // -----------------------------------------------------------------------------------------------------
        
        // added two empty JLabels to make space between two JTables (for better clarity) [TO IMPROVE]
        gcCenter.gridx = 0; gcCenter.gridy = 4;
        gcCenter.weighty = 5;
        centerPanel.add(new JLabel(""), gcCenter);

        gcCenter.gridx = 0; gcCenter.gridy = 5;
        gcCenter.weighty = 5;
        centerPanel.add(new JLabel(""), gcCenter);
        // -----------------------------------------------------------------------------------------------------
        
        gcCenter.gridx = 0; gcCenter.gridy = 6;
        gcCenter.weighty = 5;
        centerPanel.add(labelAssignmentsList, gcCenter);
        
        // ***** ASSIGNMENTS TABLE ---------------------------------------------------------------------------------
        gcCenter.gridx = 0; gcCenter.gridy = 7;               
        String[] columnsOfAssignmentsData = {"Title", "Type", "Weight(%)", "Result"};
        modelAssignmentsTable = new DefaultTableModel(dataAssignments, columnsOfAssignmentsData)
        {
            public Class getColumnClass(int column)
            {
                switch(column)
                {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    case 3:
                        return String.class;
                    case 4:
                        return Boolean.class;
                    default:
                        return String.class;
                        
                }
//                Class returnValue= null;
//                
//                if((column >= 0) && (column < getColumnCount()) && !listAssignments.isEmpty())
//                {
//                    returnValue = getValueAt(0, column).getClass();
//                }
//                else
//                {
//                    returnValue = Object.class;
//                }
//                return returnValue;                    
            }
        };        
        tableAssignments = new JTable(modelAssignmentsTable)
        {
            @Override
            public boolean isCellEditable(int data, int columns)
            {
                return false;
            }
        };
        tableAssignments.setPreferredScrollableViewportSize(new Dimension(500,100));
        tableAssignments.setFillsViewportHeight(true);
        tableAssignments.setAutoCreateRowSorter(true);
        JScrollPane assignmentsScrollPane = new JScrollPane(tableAssignments);
        centerPanel.add(assignmentsScrollPane, gcCenter);      
        // -----------------------------------------------------------------------------------------------------
        
        // ***** S O U T H
        JPanel southPanel = new JPanel();
        contentPane.add(southPanel, BorderLayout.SOUTH);
        southPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        southPanel.add(buttonCalculateFinalGrade);
        buttonCalculateFinalGrade.addActionListener(new CalculateFinalGradeButtonHandler());
        southPanel.add(new JLabel("         "));    // to make space between button and JTextField [TO IMPROVE]
        southPanel.add(new JLabel("         "));    // to make space between button and JTextField [TO IMPROVE]
        southPanel.add(labelFinalGrade);
        southPanel.add(outputFinalGrade);
        outputFinalGrade.setPreferredSize(new Dimension(50, 25));

        resultsFrame.pack();
        resultsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultsFrame.setAlwaysOnTop(false);
        //resultsFrame.setResizable(false);
        resultsFrame.setLocationRelativeTo(null);    // setting the program in the centre of the screen
    }
    
    // ***** H A N D L E R S -------------------------------------------------------------------------------------   
    private class CalculateFinalGradeButtonHandler implements ActionListener // [TO DO]
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            for(int i=0; i<tableModules.getRowCount(); i++)
            {
                Boolean isCheckboxTicked = Boolean.valueOf(tableModules.getValueAt(i, 5).toString());
                String column = tableModules.getValueAt(i, 0).toString();
                
                if(isCheckboxTicked)
                {
                    JOptionPane.showMessageDialog(null, column);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, column + "false value");
                }
            }
            
//            int viewRow = getTempModuleRow();
//            
//            if(viewRow > -1)
//            {
//                Module selectedModule = null;
//                String selectedModuleName = tableModules.getValueAt(viewRow, 0).toString();
//                selectedModule = userModulesManager.getModule(selectedModuleName);
//                JOptionPane.showMessageDialog(resultsFrame, "Your Final Grade is ...", "Success Info", JOptionPane.INFORMATION_MESSAGE);
//            }
//            else
//            {
//                JOptionPane.showMessageDialog(resultsFrame, "No modules selected.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
//            }
        }
    }
    
    // ***** L I S T E N E R S -------------------------------------------------------------------------------------   
    private class ModulesListSelectionListener implements ListSelectionListener
    {
        @Override
        public void valueChanged(ListSelectionEvent event) 
        {   
            if(event.getValueIsAdjusting())     // 'true' if this is one in a series of multiple events, where changes are still being made 
                                                // (very important for showing ONLY 1 instance of each assignment)
            {
                Module selectedModule = null;
                int selectedTableRow = tableModules.getSelectedRow();
                setTempModuleRow(selectedTableRow);
                String selectedModuleName = tableModules.getValueAt(selectedTableRow, 0).toString();
                selectedModule = userModulesManager.getModule(selectedModuleName);
                listAssignments = selectedModule.getAllAssignments();

                if(listAssignments.isEmpty())
                {
                    modelAssignmentsTable.getDataVector().removeAllElements();
                    modelAssignmentsTable.fireTableDataChanged(); // notifies the JTable that the model has changed
                }
                else
                {
                    Object[] newAssignmentToBeAdded;
                    for(Assignment tempAssignment : listAssignments)
                    {
                        newAssignmentToBeAdded = new Object[]{tempAssignment.getTitle(), tempAssignment.getType(), tempAssignment.getWeightPercent(), tempAssignment.getResult()};
                        modelAssignmentsTable.addRow(newAssignmentToBeAdded);
                    }    
                } 
            }
        }     
    }
    
    // ***** M E T H O D S -------------------------------------------------------------------------------------   
    public void setWindowVisible(boolean visibility)
    {
        resultsFrame.setVisible(visibility);
    }

    public int getTempModuleRow() 
    {
        return tempModuleRow;
    }

    public void setTempModuleRow(int newTempModuleRow) 
    {
        this.tempModuleRow = newTempModuleRow;
    } 
}