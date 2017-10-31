package unimarkscalculator.gui;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
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
    private JTable modulesTable;
    private JTable assignmentsTable;
    
    private Object[][] modulesData;
    private Object[][] assignmentsData = {{"","","",""}};
    private String[] modulesColumnNames;
    private String[] assignmentsColumnNames;
    private ModulesManager modulesCollectionInstance = ModulesManager.getInstance();
    private ArrayList<Module> modulesList = new ArrayList<>();
    
    private DefaultTableModel modulesTableModel;
    private DefaultTableModel assignmentsTableModel;
    
    public ResultsGUI()
    {
        setFrame();
    }
    
    private void setFrame()
    {
        Container contentPane = myFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        Dimension preferredSize = new Dimension(700, 600);
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
        centerPanel.add(modulesListLabel, gcCenter);
        
        gcCenter.gridx = 0; gcCenter.gridy = 1;
        // Creating MODULES table:
        String[] modulesColumnNames = {"Name", "Semester", "Credits", "Grade"}; 
        modulesTableModel = new DefaultTableModel(modulesData, modulesColumnNames)
        {
            public Class getColumnClass(int column)
            {
                Class returnValue;

                if((column >= 0) && (column < getColumnCount()))
                {
                    returnValue = getValueAt(0, column).getClass();
                }
                else
                {
                    returnValue = Object.class;
                }
                return returnValue;
            }
        };
        Object[] newModule;
        modulesList = modulesCollectionInstance.getAllModules();
        int index = 0;
        for(Module tempModule : modulesList)
        {
            System.out.println(tempModule.getName());
            newModule = new Object[]{tempModule.getName(), tempModule.getSemester(), tempModule.getCredits(), tempModule.getGrade()};
            modulesTableModel.addRow(newModule);
            index++;
        }
        
        modulesTable = new JTable(modulesTableModel)
        {
            @Override
            public boolean isCellEditable(int data, int columns)
            {
                return false;
            }            
        };
        modulesTable.setPreferredScrollableViewportSize(new Dimension(500,100));
        modulesTable.setFillsViewportHeight(true);
        modulesTable.setAutoCreateRowSorter(true);      //allows to sort through the information
        JScrollPane modulesScrollPane = new JScrollPane(modulesTable);
        centerPanel.add(modulesScrollPane, gcCenter);     
        modulesTable.getSelectionModel().addListSelectionListener(new ModulesListSelectionListener());

        gcCenter.gridx = 0; gcCenter.gridy = 2;
        centerPanel.add(assignmentsListLabel, gcCenter);
        
        gcCenter.gridx = 0; gcCenter.gridy = 3;
        // Creating ASSIGNMENTS table:
        String[] assignmentsColumnNames = {"title", "type", "result", "weight(%)"};
        DefaultTableModel assignmentsTableModel = new DefaultTableModel(assignmentsData, assignmentsColumnNames)
        {
            public Class getColumnClass(int column)
            {
                Class returnValue;
                if((column >= 0) && (column < getColumnCount()))
                {
                    returnValue = getValueAt(0, column).getClass();
                }
                else
                {
                    returnValue = Object.class;
                }
                return returnValue;                    
            }
        };        
        
//        Object[] newAssignment;
//        modulesList = modulesCollectionInstance.getAllModules();
//        int index = 0;
//        for(Module tempModule : modulesList)
//        {
//            System.out.println(tempModule.getName());
//            newModule = new Object[]{tempModule.getName(), tempModule.getSemester(), tempModule.getCredits(), tempModule.getGrade()};
//            modulesTableModel.addRow(newModule);
//            index++;
//        }
        
        

        assignmentsTable = new JTable(assignmentsData, assignmentsColumnNames)
        {
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
        //myFrame.setResizable(false);
        myFrame.setLocationRelativeTo(null);    // setting the program in the centre of the screen
    }
    
    private class ModulesListSelectionListener implements ListSelectionListener
    {

        @Override
        public void valueChanged(ListSelectionEvent event) 
        {
            int viewRow = modulesTable.getSelectedRow();
            String value = modulesTable.getValueAt(viewRow, 0).toString();
            System.out.println("ROW number: " + viewRow);
            System.out.println("VALUE: " + value);
            
            
        }
                
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
