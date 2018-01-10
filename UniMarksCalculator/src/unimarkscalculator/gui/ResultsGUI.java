package unimarkscalculator.gui;

import com.itextpdf.layout.element.Table;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
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
    private JLabel labelSelectModulesToCalculate = new JLabel("select modules");
    private JButton buttonCalculateFinalGrade = new JButton("Calculate FINAL GRADE");
    private JButton buttonPrintResults = new JButton("Print all results");
    private JTextField outputFinalGrade = new JTextField("");
    private JCheckBox checboxSelectModulesToCalculate = new JCheckBox();
    private JTable tableModules;
    private JTable tableAssignments;
    
    private Object[][] dataModules;
    private Object[][] dataAssignments;
    private String[] columnsOfModulesData;
    private String[] columnsOfAssignmentsData;
    private ModulesManager userModulesManager = ModulesManager.getInstance();
    private ArrayList<Module> listModules = new ArrayList<>();
    private ArrayList<Assignment> listAssignments = new ArrayList<>();
    
    private ArrayList<Module> allModulesSelectedForCalculation = new ArrayList<>();
    
    private DefaultTableModel modelModulesTable;
    private DefaultTableModel modelAssignmentsTable;
    
    private int tempModuleRow = -1;
    private boolean multipleRowSelection = false;
    private boolean isTicked = false;
    private double finalGrade = 0;
    
    public ResultsGUI()
    {
        setGUIFrame();
    }
    
    private void setGUIFrame()
    {
        Container contentPane = resultsFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        //Dimension preferredWindowSize = new Dimension(511, 600);
        //resultsFrame.setPreferredSize(preferredWindowSize);
        
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
        buttonPrintResults.addActionListener(new PrintResultsButtonHandler());

        gcCenter.anchor = GridBagConstraints.LINE_END;
        gcCenter.gridx = 0; gcCenter.gridy = 1;
        centerPanel.add(labelSelectModulesToCalculate, gcCenter);

        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 0; gcCenter.gridy = 2;
        gcCenter.weighty = 5;
        centerPanel.add(labelModulesList, gcCenter);

        // ***** MODULES TABLE ---------------------------------------------------------------------------------
        gcCenter.gridx = 0; gcCenter.gridy = 3;
        String[] columnsOfModulesData = {"Selected", "Module Title", "Credits", "Semester", "Grade", "Level"}; 
        modelModulesTable = new DefaultTableModel(dataModules, columnsOfModulesData)
        {
            public Class<?> getColumnClass(int column)
            {
                switch(column)
                {
                    case 0:
                        return Boolean.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    case 3:
                        return String.class;
                    case 4:
                        return String.class;
                    case 5:
                        return String.class;
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
            newModuleToBeAdded = new Object[]{false, tempModule.getName(), tempModule.getCredits(), tempModule.getSemester(), tempModule.getGrade(), tempModule.getLevel()};
            modelModulesTable.addRow(newModuleToBeAdded);
        }
        
        tableModules = new JTable(modelModulesTable)
        {
            @Override
            public boolean isCellEditable(int data, int columns)
            {
                if(isTicked == false)
                {
                    return false;
                }
                else
                {
                    return(columns == 0 ? true : false); // only column 0 will be editable
                }
                
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
        // ---------------------------------------------------------------------------------------------------------
        
        gcCenter.gridx = 0; gcCenter.gridy = 6;
        gcCenter.weighty = 5;
        centerPanel.add(labelAssignmentsList, gcCenter);
        
        // ***** ASSIGNMENTS TABLE ---------------------------------------------------------------------------------
//        gcCenter.gridx = 0; gcCenter.gridy = 7;               
//        String[] columnsOfAssignmentsData = {"Title", "Type", "Weight(%)", "Result"};
//        modelAssignmentsTable = new DefaultTableModel(dataAssignments, columnsOfAssignmentsData)
//        {
//            public Class getColumnClass(int column)
//            {
//                switch(column)
//                {
//                    case 0:
//                        return String.class;
//                    case 1:
//                        return String.class;
//                    case 2:
//                        return String.class;
//                    case 3:
//                        return String.class;
//                    case 4:
//                        return Boolean.class;
//                    default:
//                        return String.class;
//                        
//                }
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
//            }
//        };        
//        tableAssignments = new JTable(modelAssignmentsTable)
//        {
//            @Override
//            public boolean isCellEditable(int data, int columns)
//            {
//                return false;
//            }
//        };
//        tableAssignments.setPreferredScrollableViewportSize(new Dimension(500,100));
//        tableAssignments.setFillsViewportHeight(true);
//        tableAssignments.setAutoCreateRowSorter(true);
//        JScrollPane assignmentsScrollPane = new JScrollPane(tableAssignments);
//        centerPanel.add(assignmentsScrollPane, gcCenter);      
        // -----------------------------------------------------------------------------------------------------
        
        // COLUMN 2:
        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 1; gcCenter.gridy = 1;
        centerPanel.add(checboxSelectModulesToCalculate, gcCenter);
        checboxSelectModulesToCalculate.addActionListener(new CheckboxSelectModulesToCalculateHandler());
        
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
                String selectedModuleName = tableModules.getValueAt(selectedTableRow, 1).toString();
                selectedModule = userModulesManager.getModule(selectedModuleName);
                

//                try
//                {
//                    listAssignments = selectedModule.getAllAssignments();
//                }
//                catch(NullPointerException e)
//                {
//                    System.out.println(e.toString());
//                }
//                
//
//                if(listAssignments.isEmpty() || listAssignments == null)
//                {
//                    try
//                    {
//                        modelAssignmentsTable.getDataVector().removeAllElements();
//                        modelAssignmentsTable.fireTableDataChanged(); // notifies the JTable that the model has changed
//                    }
//                    catch(NullPointerException e)
//                    {
//                        System.out.println(e.toString());
//                    }
//                }
//                else
//                {
//                    Object[] newAssignmentToBeAdded;
//                    for(Assignment tempAssignment : listAssignments)
//                    {
//                        newAssignmentToBeAdded = new Object[]{tempAssignment.getTitle(), tempAssignment.getType(), tempAssignment.getWeightPercent(), tempAssignment.getResult()};
//                        modelAssignmentsTable.addRow(newAssignmentToBeAdded);
//                    }    
//                } 
            }
        }     
    }
            
    private class CheckboxSelectModulesToCalculateHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(checboxSelectModulesToCalculate.isSelected())
            {
                setIsTicked(true);
                //modelAssignmentsTable.getDataVector().removeAllElements();
                //modelAssignmentsTable.fireTableDataChanged(); // notifies the JTable that the model has changed
                // enable first column (change from greyed out to editable)
                
                setMultipleRowSelection(true);
                tableModules.setRowSelectionAllowed(multipleRowSelection);
                tableModules.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                
                System.out.println("Checkbox selected. You can choose your modules to be calculated.");
            }
            else
            {
                setIsTicked(false);
//                modelAssignmentsTable.getDataVector().removeAllElements();
//                modelAssignmentsTable.fireTableDataChanged(); // notifies the JTable that the model has changed
                setMultipleRowSelection(false);
//                tableModules.setRowSelectionAllowed(multipleRowSelection);
//                tableModules.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                System.out.println("Checkbox unselected.");
            }
        }
    }
    
    private class CalculateFinalGradeButtonHandler implements ActionListener // [TO DO]
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(checboxSelectModulesToCalculate.isSelected())
            {
                if(tableModules.getRowCount() > 0)
                {
                    boolean modulesSelectedFromList = checkAnyModulesSelectedForCalculation(tableModules);
                    boolean modulesAboveLevel4Threshold = checkSelectedModulesAboveLevel4(tableModules);
                    boolean modulesAreCompleted = checkSelectedModulesAreCompleted(tableModules);
                    if(modulesSelectedFromList)
                    {
                        if(modulesAboveLevel4Threshold)
                        {
                            if(modulesAreCompleted)
                            {
                                for(int i=0; i<tableModules.getRowCount(); i++)
                                {
                                    boolean isModuleCheckboxTicked = Boolean.valueOf(tableModules.getValueAt(i, 0).toString());
                                    if(isModuleCheckboxTicked)
                                    {
                                        String selectedModuleName = tableModules.getValueAt(i, 1).toString();
                                        Module selectedModuleObject = userModulesManager.getModule(selectedModuleName);
                                        allModulesSelectedForCalculation.add(selectedModuleObject);
                                    }
                                }
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(resultsFrame, "Some of the modules selected are not completed yet.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(resultsFrame, "Only modules from Level 5 or above count towards the Final Degree Classification.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(resultsFrame, "Please select at least 2 modules from the list.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(resultsFrame, "No modules on the list.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(resultsFrame, "Checkbox 'select modules' not ticked.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
            }
            calculateFinalGrade();
            String finalGradeCalculated = String.valueOf(getFinalGrade());
            outputFinalGrade.setText(finalGradeCalculated);
            
        }
    }
    
    private class PrintResultsButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            Document pdfDocument = new Document(PageSize.A4, 14, 14, 14, 14);
            float fontSize = 6.7f;
            float lineSpacing = 10f;
            Module moduleObject = null;
            Font documentHeaderFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);      
            Font tableHeaderFont = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
            Font cellFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
            Paragraph documentHeading = new Paragraph("MODULE RESULTS\n ", documentHeaderFont);
            documentHeading.setAlignment(Element.ALIGN_CENTER);
            
            Paragraph spaceBetweenLines = new Paragraph(" ");
            spaceBetweenLines.setLeading(0, 5);
            
            PdfPTable documentTable = new PdfPTable(5);
            documentTable.setWidthPercentage(100);
            
            
            PdfPCell cell1 = new PdfPCell(new Phrase("Module Title", tableHeaderFont));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setBackgroundColor(new BaseColor(136, 159, 251));
            documentTable.addCell(cell1);
            
            cell1 = new PdfPCell(new Phrase("Credits", tableHeaderFont));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setBackgroundColor(new BaseColor(136, 159, 251));
            documentTable.addCell(cell1);
            
            cell1 = new PdfPCell(new Phrase("Semester", tableHeaderFont));
            cell1.setBackgroundColor(new BaseColor(136, 159, 251));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            documentTable.addCell(cell1);
            
            cell1 = new PdfPCell(new Phrase("Grade/Completed (%)", tableHeaderFont));
            cell1.setBackgroundColor(new BaseColor(136, 159, 251));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            documentTable.addCell(cell1);

            cell1 = new PdfPCell(new Phrase("Level", tableHeaderFont));
            cell1.setBackgroundColor(new BaseColor(136, 159, 251));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            documentTable.addCell(cell1);
            //documentTable.setHeaderRows(1);
            
            try 
            {
                if(modelModulesTable.getRowCount() > 0)
                {
                    PdfWriter writer = PdfWriter.getInstance(pdfDocument, new FileOutputStream("tempDocument.pdf"));
                    PdfPageFooter footer = new PdfPageFooter();
                    writer.setPageEvent(footer);
                    
                    Image uniLogoImage = Image.getInstance("images/uniMarksCalculatorLogo.jpg");
                    uniLogoImage.scaleAbsolute(160f, 60f);
                    pdfDocument.open();
                    pdfDocument.add(uniLogoImage);
                    pdfDocument.add(spaceBetweenLines);
                    pdfDocument.add(documentHeading);
                    for(int tableRow = 0; tableRow < modelModulesTable.getRowCount(); tableRow++)
                    {
                        String moduleName = modelModulesTable.getValueAt(tableRow, 1).toString();
                        moduleObject = userModulesManager.getModule(moduleName);
                        String[] moduleInfo = moduleObject.toStringDataArray();
                        //pdfDocument.add(new Paragraph(new Phrase(lineSpacing, moduleObject.toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, fontSize))));
                        
                        // NAME
                        PdfPCell cell2 = new PdfPCell(new Phrase(moduleInfo[0], cellFont));
                        documentTable.addCell(cell2);

                        // CREDITS
                        cell2 = new PdfPCell(new Phrase(moduleInfo[3], cellFont));
                        documentTable.addCell(cell2);

                        // SEMESTER
                        cell2 = new PdfPCell(new Phrase(moduleInfo[2], cellFont));
                        documentTable.addCell(cell2);

                        // COMPLETED(%) / GRADE(%)
                        cell2 = new PdfPCell(new Phrase(moduleInfo[4], cellFont));
                        documentTable.addCell(cell2);
                        
                        // LEVEL
                        cell2 = new PdfPCell(new Phrase(moduleInfo[1], cellFont));
                        documentTable.addCell(cell2);
                    }
                    pdfDocument.add(documentTable);
                    pdfDocument.close();
                    System.out.println("PDF file saved sucessfully!");
                    
                    if(Desktop.isDesktopSupported())
                    {
                        File createdPDFFile = new File("tempDocument.pdf");
                        Desktop.getDesktop().open(createdPDFFile);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(resultsFrame, "No modules on the list.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
                }
            } 
            catch (IOException ex) 
            {
                System.out.println(ex.toString());
            } 
            catch (DocumentException ex) 
            {
                Logger.getLogger(ResultsGUI.class.getName()).log(Level.SEVERE, null, ex);
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

    public void setMultipleRowSelection(boolean newMultipleRowSelection) 
    {
        this.multipleRowSelection = multipleRowSelection;
    }

    public void setIsTicked(boolean isTicked) 
    {
        this.isTicked = isTicked;
    }
    
    private boolean checkAnyModulesSelectedForCalculation(JTable tableModules)
    {
        boolean selectedModulesExist = false;
        int countModulesSelectedForCalculation = 0;
        for(int i = 0; i < tableModules.getRowCount(); i++)
        {
            if((tableModules.getValueAt(i, 0).toString()).equals("true"))
            {
                countModulesSelectedForCalculation += 1;
            }
        }
        
        if(countModulesSelectedForCalculation > 1)
        {
            selectedModulesExist = true;
        }
        return selectedModulesExist;
    }
    
    private boolean checkSelectedModulesAboveLevel4(JTable tableModules)
    { 
        boolean modulesAboveThreshold = true;
        for(int row = 0; row < tableModules.getRowCount(); row++)
        {
            boolean isModuleCheckboxTicked = Boolean.valueOf(tableModules.getValueAt(row, 0).toString());
            int moduleLevel = Integer.valueOf(tableModules.getValueAt(row, 5).toString());
            if(isModuleCheckboxTicked)
            {
                if(moduleLevel < 5)
                {
                    modulesAboveThreshold = false;
                    return modulesAboveThreshold;
                }
            }
        }
        return modulesAboveThreshold;
    }
    // Added a method to check selected modules have a grade calculated already.
    private boolean checkSelectedModulesAreCompleted(JTable tableModules)
    {
        boolean modulesAreCompleted = true;
        for(int row = 0; row < tableModules.getRowCount(); row++)
        {
            boolean isModuleCheckboxTicked = Boolean.valueOf(tableModules.getValueAt(row, 0).toString());
            double moduleGrade = Double.valueOf(tableModules.getValueAt(row, 4).toString());
            if(isModuleCheckboxTicked)
            {
                if(moduleGrade <= 0)
                {
                    modulesAreCompleted = false;
                    return modulesAreCompleted;
                }
            }
        }
        return modulesAreCompleted;
    }
    
    private void calculateFinalGrade()
    {
        ArrayList<Module> chosenModulesLevel5 = new ArrayList<>();
        ArrayList<Module> chosenModulesLevel6 = new ArrayList<>();
        ArrayList<Module> bestLevel6Modules = new ArrayList<>();
        
        if(allModulesSelectedForCalculation.isEmpty())
        {
            JOptionPane.showMessageDialog(resultsFrame, "No modules to be calculated.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            for(Module tempModule : allModulesSelectedForCalculation)
            {
                if(tempModule.getLevel().equals("5"))
                {
                    chosenModulesLevel5.add(tempModule);
                }
                else if(tempModule.getLevel().equals("6"))
                {
                    chosenModulesLevel6.add(tempModule);
                }
            }
            
            chosenModulesLevel6 = sortModules(chosenModulesLevel6);
            
            System.out.println(chosenModulesLevel6.size());
            
            int arrayListIndex = 0;
            while(arrayListIndex < 5)
            {
                Module tempModule = chosenModulesLevel6.get(arrayListIndex);
                bestLevel6Modules.add(tempModule);
                arrayListIndex++;      
            }
            
            // remove modules moved from chosenModulesLeve6 to bestLevel6Modules
            // move all chosenModulesLeve5 and remaining chosenModulesLevel6 to one collection - mixedLevelModulesLeft
            // sort mixedLevelModulesLeft
            // calculate averageMarkBestLevel6Modules (averageMark * 0.75
            // take best 6 modules from mixedLevelModulesLeft to bestMixedLevelModules
            // calculate averageMarkBestMixedModules (averageMark * 0.25)
            // add averageMarkBestLevel6Modules to averageMarkBestMixedModules
            // display result in the field
            
            //*make sure to count 30 credit modules as 2 x 15 modules
        }       
    }

    public double getFinalGrade() 
    {
        return finalGrade;
    }

    public void setFinalGrade(double finalGrade) 
    {
        this.finalGrade = finalGrade;
    }
    
    private ArrayList<Module> sortModules(ArrayList<Module> arrayListToBeSorted)
    {
        ArrayList<Module> sortedArray = new ArrayList<>();
        ArrayList<Integer> tempArrayList = new ArrayList<>();
        Collections.sort(arrayListToBeSorted);   
        
        return sortedArray;
    }
}