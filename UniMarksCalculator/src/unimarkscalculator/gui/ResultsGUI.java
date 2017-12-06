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
 *
 * @author Lukasz Bol
 */
public class ResultsGUI 
{
    private JFrame myFrame = new JFrame("View Results");
    private JLabel resultsLabel = new JLabel("RESULTS", JLabel.CENTER);
    private JLabel modulesListLabel = new JLabel("MODULES");
    private JLabel assignmentsListLabel = new JLabel("ASSIGNMENTS");
    private JLabel modulesForCalculationLabel = new JLabel("modules chosen for calculation: ");
    private JLabel finalGradeLabel = new JLabel("Final Grade: ");
    private JButton calculateFinalGradeButton = new JButton("Calculate FINAL GRADE");
    private JButton printResultsButton = new JButton("Print all results");
    private JTextField finalGradeOutput = new JTextField("");
    private JTable modulesTable;
    private JTable assignmentsTable;
    private JCheckBox selectModuleCheckBox;
    
    private Object[][] modulesData;
    private Object[][] assignmentsData;
    private String[] modulesColumnNames;
    private String[] assignmentsColumnNames;
    private ModulesManager modulesCollectionInstance = ModulesManager.getInstance();
    private ArrayList<Module> modulesList = new ArrayList<>();
    private ArrayList<Assignment> assignmentsList = new ArrayList<>();
    
    private DefaultTableModel modulesTableModel;
    private DefaultTableModel assignmentsTableModel;
    
    private int tempModuleRow = -1;
    
    public ResultsGUI()
    {
        setFrame();
    }
    

    //Setting frame to create 'View Results' tab
    private void setFrame()
    {
        Container contentPane = myFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        Dimension preferredSize = new Dimension(511, 600);
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
        gcCenter.weightx = 0.5; gcCenter.weighty = 50;
        
        // COLUMN 1:
        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 0; gcCenter.gridy = 0;
        centerPanel.add(printResultsButton, gcCenter);

        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 0; gcCenter.gridy = 2;
        gcCenter.weighty = 5;
        centerPanel.add(modulesListLabel, gcCenter);

        /**
         * Creating MODULES table
         */
        gcCenter.gridx = 0; gcCenter.gridy = 3;
        String[] modulesColumnNames = {"Module title", "Credits", "Semester", "Grade", "Level", "Selected"}; 
        modulesTableModel = new DefaultTableModel(modulesData, modulesColumnNames)
        {
            public Class<?> getColumnClass(int column)
            {
                switch(column)  //added switch to paste different types of values into a JTable row
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
        Object[] newModule;
        modulesList = modulesCollectionInstance.getAllModules();
        for(Module tempModule : modulesList)
        {
            newModule = new Object[]{tempModule.getName(), tempModule.getCredits(), tempModule.getSemester(), tempModule.getGrade(), tempModule.getLevel(), false};
            modulesTableModel.addRow(newModule);
        }
        
        modulesTable = new JTable(modulesTableModel)
        {
            @Override
            public boolean isCellEditable(int data, int columns)
            {
                return false;
            }            
        };
        modulesTable.setPreferredScrollableViewportSize(new Dimension(500,150));
        modulesTable.setFillsViewportHeight(true);
        modulesTable.setAutoCreateRowSorter(true);      //allows to sort through the information
        JScrollPane modulesScrollPane = new JScrollPane(modulesTable);
        centerPanel.add(modulesScrollPane, gcCenter);     
        modulesTable.getSelectionModel().addListSelectionListener(new ModulesListSelectionListener());

        /**
         * --------------------------------------------------------------------------------------------
         */
        
        // added two empty JLabels to make space between two JTables (for better clarity) [TO IMPROVE]
        gcCenter.gridx = 0; gcCenter.gridy = 4;
        gcCenter.weighty = 5;
        centerPanel.add(new JLabel(""), gcCenter);

        gcCenter.gridx = 0; gcCenter.gridy = 5;
        gcCenter.weighty = 5;
        centerPanel.add(new JLabel(""), gcCenter);
        
        gcCenter.gridx = 0; gcCenter.gridy = 6;
        gcCenter.weighty = 5;
        centerPanel.add(assignmentsListLabel, gcCenter);
        
        /**
         * Creating ASSIGNMENTS table
         */
        gcCenter.gridx = 0; gcCenter.gridy = 7;               
        String[] assignmentsColumnNames = {"Title", "Type", "Weight(%)", "Result"};
        assignmentsTableModel = new DefaultTableModel(assignmentsData, assignmentsColumnNames)
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
//                if((column >= 0) && (column < getColumnCount()) && !assignmentsList.isEmpty())
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
        assignmentsTable = new JTable(assignmentsTableModel)
        {
            @Override
            public boolean isCellEditable(int data, int columns)
            {
                return false;
            }
        };

        assignmentsTable.setPreferredScrollableViewportSize(new Dimension(500,100));
        assignmentsTable.setFillsViewportHeight(true);
        assignmentsTable.setAutoCreateRowSorter(true);
        JScrollPane assignmentsScrollPane = new JScrollPane(assignmentsTable);
        centerPanel.add(assignmentsScrollPane, gcCenter);      
        
        /**
         * -----------------------------------------------------------------------------
         */
        
        // ***** S O U T H
        JPanel southPanel = new JPanel();
        contentPane.add(southPanel, BorderLayout.SOUTH);
        southPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        southPanel.add(calculateFinalGradeButton);
        calculateFinalGradeButton.addActionListener(new CalculateFinalGradeButtonHandler());
        southPanel.add(new JLabel("         "));    // to make space between button and JTextField [TO IMPROVE]
        southPanel.add(new JLabel("         "));    // to make space between button and JTextField [TO IMPROVE]
        southPanel.add(finalGradeLabel);
        southPanel.add(finalGradeOutput);
        finalGradeOutput.setPreferredSize(new Dimension(50, 25));

        
        myFrame.pack();
        myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myFrame.setAlwaysOnTop(false);
        //myFrame.setResizable(false);
        myFrame.setLocationRelativeTo(null);    // setting the program in the centre of the screen
    }
    
    private class ModulesListSelectionListener implements ListSelectionListener
    {
        @Override
        public void valueChanged(ListSelectionEvent event) 
        {   
            if(event.getValueIsAdjusting())     // 'true' if this is one in a series of multiple events, where changes are still being made 
                                                // (important for showing ONLY 1 instance of each assignment)
            {
                Module selectedModule = null;
                int viewRow = modulesTable.getSelectedRow();
                setTempModuleRow(viewRow);
                String selectedModuleName = modulesTable.getValueAt(viewRow, 0).toString();
                selectedModule = modulesCollectionInstance.getModule(selectedModuleName);
                assignmentsList = selectedModule.getAllAssignments();

                if(assignmentsList.isEmpty())
                {
                    assignmentsTableModel.getDataVector().removeAllElements();
                    assignmentsTableModel.fireTableDataChanged(); // notifies the JTable that the model has changed
                }
                else
                {
                    Object[] newAssignment;
                    for(Assignment tempAssignment : assignmentsList)
                    {
                        newAssignment = new Object[]{tempAssignment.getTitle(), tempAssignment.getType(), tempAssignment.getWeightPercent(), tempAssignment.getResult()};
                        assignmentsTableModel.addRow(newAssignment);
                    }    
                } 
            }
        }     
    }
    
    private class CalculateFinalGradeButtonHandler implements ActionListener // [TO DO]
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            for(int i=0; i<modulesTable.getRowCount(); i++)
            {
                Boolean checked = Boolean.valueOf(modulesTable.getValueAt(i, 5).toString());
                String column = modulesTable.getValueAt(i, 0).toString();
                
                if(checked)
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
//                String selectedModuleName = modulesTable.getValueAt(viewRow, 0).toString();
//                selectedModule = modulesCollectionInstance.getModule(selectedModuleName);
//                JOptionPane.showMessageDialog(myFrame, "Your Final Grade is ...", "Success Info", JOptionPane.INFORMATION_MESSAGE);
//            }
//            else
//            {
//                JOptionPane.showMessageDialog(myFrame, "No modules selected.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
//            }
        }
    }
    
    public void setVisible(boolean visibility)
    {
        myFrame.setVisible(visibility);
    }

    public int getTempModuleRow() 
    {
        return tempModuleRow;
    }

    public void setTempModuleRow(int tempModuleRow) 
    {
        this.tempModuleRow = tempModuleRow;
    } 
}