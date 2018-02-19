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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
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
    private ImageIcon printerIcon = new ImageIcon("images/if_printer_39263.png");
    private JButton buttonCalculateFinalGrade = new JButton("Calculate FINAL GRADE");
    private JButton buttonPrintModules = new JButton("  Print", printerIcon);
    //private JButton buttonPrintModulesAndAssignments = new JButton("Print modules with assignments");
    private JTextField outputFinalGrade = new JTextField("");
    private JTextField outputDegreeClassificationName = new JTextField("");
    private JCheckBox checkboxSelectModulesToCalculate = new JCheckBox();
    private JCheckBox checkboxShowAssignments = new JCheckBox("show assignments");
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
    private ListSelectionModel assignmentsSelectionModel;
    
    private int tempModuleRow = -1;
    private boolean multipleRowSelection = false;
    private boolean isTicked = false;
    private double finalGrade = 0;  
    
    JScrollPane assignmentsScrollPane;
    
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
        
        
        centerPanel.add(buttonPrintModules, gcCenter);
        buttonPrintModules.addActionListener(new PrintModulesButtonHandler());

        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 0; gcCenter.gridy = 1;
        //centerPanel.add(buttonPrintModulesAndAssignments, gcCenter);
        //buttonPrintModulesAndAssignments.addActionListener(new PrintModulesButtonHandler());        
        
        gcCenter.anchor = GridBagConstraints.LINE_END;
        gcCenter.gridx = 0; gcCenter.gridy = 2;
        centerPanel.add(labelSelectModulesToCalculate, gcCenter);

        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 0; gcCenter.gridy = 3;
        gcCenter.weighty = 5;
        centerPanel.add(labelModulesList, gcCenter);
        
        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 0; gcCenter.gridy = 4;
        gcCenter.weighty = 6;
        centerPanel.add(checkboxShowAssignments, gcCenter);        
        checkboxShowAssignments.addActionListener(new CheckboxShowAssignmentsHandler());


                
        //labelAssignmentsList.setVisible(true);
        //assignmentsScrollPane.setVisible(true);
                
        // ***** MODULES TABLE ---------------------------------------------------------------------------------
        gcCenter.gridx = 0; gcCenter.gridy = 5;
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
        tableModules.getSelectionModel().addListSelectionListener(new ModuleAssignmentsListSelectionHandler());
        tableModules.setPreferredScrollableViewportSize(new Dimension(600,300));
        tableModules.setFillsViewportHeight(true);
        tableModules.setAutoCreateRowSorter(true);      
        tableModules.setShowGrid(false);
        tableModules.getColumnModel().getColumn(1).setPreferredWidth(250);      // sets Modules' titles more visible
        
        // center data in cells:
        DefaultTableCellRenderer centerCellDataRenderer = new DefaultTableCellRenderer();
        centerCellDataRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        for(int tableColumn = 2; tableColumn < tableModules.getColumnCount(); tableColumn++)
        {
            tableModules.getColumnModel().getColumn(tableColumn).setCellRenderer(centerCellDataRenderer);
        }
        
        JScrollPane modulesScrollPane = new JScrollPane(tableModules);
        centerPanel.add(modulesScrollPane, gcCenter);     
        // -----------------------------------------------------------------------------------------------------
        
        // added two empty JLabels to make space between two JTables (for better clarity) [TO IMPROVE]
        gcCenter.gridx = 0; gcCenter.gridy = 6;
        gcCenter.weighty = 5;
        centerPanel.add(new JLabel(""), gcCenter);

        gcCenter.gridx = 0; gcCenter.gridy = 7;
        gcCenter.weighty = 5;
        centerPanel.add(new JLabel(""), gcCenter);
        // ---------------------------------------------------------------------------------------------------------
        
        gcCenter.gridx = 0; gcCenter.gridy = 6;
        gcCenter.weighty = 5;
        centerPanel.add(labelAssignmentsList, gcCenter);
        labelAssignmentsList.setVisible(false);
        
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
        assignmentsScrollPane = new JScrollPane(tableAssignments);
        centerPanel.add(assignmentsScrollPane, gcCenter);      
        assignmentsScrollPane.setVisible(false);
        // -----------------------------------------------------------------------------------------------------
        
        // COLUMN 2:
        gcCenter.anchor = GridBagConstraints.LINE_START;
        gcCenter.gridx = 1; gcCenter.gridy = 2;
        centerPanel.add(checkboxSelectModulesToCalculate, gcCenter);
        checkboxSelectModulesToCalculate.addActionListener(new CheckboxSelectModulesToCalculateHandler());
        
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
        southPanel.add(new JLabel("         "));    // to make space between button and JTextField [TO IMPROVE]
        southPanel.add(outputDegreeClassificationName);
        outputFinalGrade.setPreferredSize(new Dimension(50, 25));
        outputDegreeClassificationName.setPreferredSize(new Dimension(150, 25));

        resultsFrame.pack();
        resultsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultsFrame.setAlwaysOnTop(false);
        resultsFrame.setResizable(true);
        resultsFrame.setLocationRelativeTo(null);    // setting the program in the centre of the screen
    }
    
    // ***** L I S T E N E R S -------------------------------------------------------------------------------------   
        
    private class ModuleAssignmentsListSelectionHandler implements ListSelectionListener
    {
        @Override
        public void valueChanged(ListSelectionEvent event) 
        {
            if(checkboxShowAssignments.isSelected() && event.getValueIsAdjusting())  // event.getValueIsAdjusting() is 'true' if this is one in a series of multiple events, where changes are still being made 
                                                // (very important for showing ONLY 1 instance of each assignment)
            {                
                Module selectedModule = null;
                int selectedTableRow = tableModules.getSelectedRow();
                String selectedModuleName = tableModules.getValueAt(selectedTableRow, 1).toString();
                selectedModule = userModulesManager.getModule(selectedModuleName);                
                
                try
                {
                    listAssignments = selectedModule.getAllAssignments();
                }
                catch(NullPointerException e)
                {
                    System.out.println(e.toString());
                }
                
                if(!listAssignments.isEmpty() || listAssignments != null)
                {
                    try
                    {
                        modelAssignmentsTable.getDataVector().removeAllElements();
                        modelAssignmentsTable.fireTableDataChanged(); // notifies the JTable that the model has changed
                        
                        Object[] newAssignmentToBeAdded;
                        for(Assignment tempAssignment : listAssignments)
                        {
                            newAssignmentToBeAdded = new Object[]{tempAssignment.getTitle(), tempAssignment.getType(), tempAssignment.getWeightPercent(), tempAssignment.getResult()};
                            modelAssignmentsTable.addRow(newAssignmentToBeAdded);
                        }    
                    }
                    catch(NullPointerException e)
                    {
                        System.out.println(e.toString());
                    }
                }        
            }
        }        
    }
            
    private class CheckboxSelectModulesToCalculateHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(checkboxSelectModulesToCalculate.isSelected())
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
    
    private class CheckboxShowAssignmentsHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(checkboxShowAssignments.isSelected())
            {
                labelAssignmentsList.setVisible(true);
                assignmentsScrollPane.setVisible(true);
            }
            else if (!checkboxShowAssignments.isSelected())
            {
                labelAssignmentsList.setVisible(false);
                assignmentsScrollPane.setVisible(false);
            }
            resultsFrame.revalidate();
            resultsFrame.pack();
            resultsFrame.repaint();    
        }   
    }
    
    private class CalculateFinalGradeButtonHandler implements ActionListener // [TO DO]
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(checkboxSelectModulesToCalculate.isSelected())
            {
                if(tableModules.getRowCount() > 0)  // check if table not empty
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
                                        String selectedModuleCredits = tableModules.getValueAt(i, 2).toString();
                                        Module selectedModuleObject = userModulesManager.getModule(selectedModuleName);
                                        
                                        if(selectedModuleCredits.equals("30"))      // if module has 30 credits, it is added twice (but with different name) as it was 2 x 15 credits modules, as per calculation requirements
                                        {
                                            allModulesSelectedForCalculation.add(selectedModuleObject); 
                                            Module module30CreditsPart2 = new Module(selectedModuleObject.getLevel(), selectedModuleObject.getName() +"(2)", selectedModuleObject.getSemester(), selectedModuleObject.getCredits());
                                            module30CreditsPart2.setGrade(selectedModuleObject.getGrade());
                                            allModulesSelectedForCalculation.add(module30CreditsPart2);
                                        }
                                        else
                                        {
                                            allModulesSelectedForCalculation.add(selectedModuleObject); 
                                        } 
                                    }
                                    else
                                    {
                                        if(Integer.valueOf(tableModules.getValueAt(i, 5).toString()) < 5)
                                        {
                                            System.out.println("Module level below Final Degree Classification threshold.");
                                        }
                                        else
                                        {
                                            System.out.println("-----ERROR Module: " + tableModules.getValueAt(i, 1).toString() + " not chosen.");
                                        }
                                    }
                                }
                                calculateFinalGrade();
                                displayFinalDegreeClassification();
                                // add text field with Degree Classification
                                // make final grade fields setEditable(false)
                                
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
                        JOptionPane.showMessageDialog(resultsFrame, "Please select all modules on Level 5 and Level 6 from the list.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
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
        }
    }
        
    private class PrintModulesButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            String[] printOptions = new String[] {"Modules", "Modules and Assignments"};
            int optionChosen = JOptionPane.showOptionDialog(null, "What would you like to print out?", "Print options", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, printOptions, printOptions[0]);
            
            Document pdfDocument = new Document(PageSize.A4, 14, 14, 14, 14);
            float fontSize = 6.7f;
            float lineSpacing = 10f;
            Module moduleObject = null;
            Assignment assignmentObject = null;
            Font documentHeaderFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);      
            Font tableHeaderFont = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
            Font cellFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
            Paragraph documentHeading;        
            File createdPDFFile;
            PdfWriter writer;
            
            if(optionChosen == 0)
            {
                documentHeading = new Paragraph("MODULES\n ", documentHeaderFont);
                documentHeading.setAlignment(Element.ALIGN_CENTER);             
                
                Paragraph spaceBetweenLines = new Paragraph(" ");
                spaceBetweenLines.setLeading(0, 5);

                PdfPTable documentTable = new PdfPTable(6);
                try 
                {
                    documentTable.setWidths(new float[] {1,8,2,2,3,2});
                } 
                catch (DocumentException ex) 
                {
                    Logger.getLogger(ResultsGUI.class.getName()).log(Level.SEVERE, null, ex);
                }

                PdfPCell cell1 = new PdfPCell(new Phrase("No.", tableHeaderFont));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setBackgroundColor(new BaseColor(136, 159, 251));
                documentTable.addCell(cell1);

                cell1 = new PdfPCell(new Phrase("Module Title", tableHeaderFont));
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

                cell1 = new PdfPCell(new Phrase("Grade(%)", tableHeaderFont));
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
                        writer = PdfWriter.getInstance(pdfDocument, new FileOutputStream("Uni Marks Calculator - Modules Results.pdf"));
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

                            // NUMBER
                            PdfPCell cell2 = new PdfPCell(new Phrase(String.valueOf(tableRow+1), cellFont));
                            documentTable.addCell(cell2);

                            // NAME
                            cell2 = new PdfPCell(new Phrase(moduleInfo[0], cellFont));
                            documentTable.addCell(cell2);

                            // CREDITS
                            cell2 = new PdfPCell(new Phrase(moduleInfo[3], cellFont));
                            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                            documentTable.addCell(cell2);

                            // SEMESTER
                            cell2 = new PdfPCell(new Phrase(moduleInfo[2], cellFont));
                            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                            documentTable.addCell(cell2);

                            // GRADE(%)
                            cell2 = new PdfPCell(new Phrase(moduleInfo[4], cellFont));
                            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                            documentTable.addCell(cell2);

                            // LEVEL
                            cell2 = new PdfPCell(new Phrase(moduleInfo[1], cellFont));
                            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                            documentTable.addCell(cell2);
                        }
                        pdfDocument.add(documentTable);

                        pdfDocument.close();
                        System.out.println("PDF file saved successfully!");

                        if(Desktop.isDesktopSupported())
                        {
                            createdPDFFile = new File("Uni Marks Calculator - Modules Results.pdf");
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
            
            else if(optionChosen == 1)
            {
                documentHeading = new Paragraph("MODULES AND ASSIGNMENTS\n ", documentHeaderFont);
                try {
                    writer = PdfWriter.getInstance(pdfDocument, new FileOutputStream("Uni Marks Calculator - Modules Results.pdf"));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ResultsGUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DocumentException ex) {
                    Logger.getLogger(ResultsGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                pdfDocument.open();
                try {
                    pdfDocument.add(documentHeading);
                } catch (DocumentException ex) {
                    Logger.getLogger(ResultsGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                for(int i = 0; i < 5; i++)
                {
                    PdfPTable table1 = new PdfPTable(2);
                    try 
                    {
                        table1.setWidths(new float[] {8,2});
                    } 
                    catch (DocumentException ex) 
                    {
                        Logger.getLogger(ResultsGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    PdfPCell cell3 = new PdfPCell(new Phrase("Title", tableHeaderFont));
                    cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell3.setBackgroundColor(new BaseColor(136, 159, 251));
                    table1.addCell(cell3);
                    
                    cell3 = new PdfPCell(new Phrase("Type", tableHeaderFont));
                    cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell3.setBackgroundColor(new BaseColor(136, 159, 251));
                    table1.addCell(cell3);
                    
                    PdfPCell cell4 = new PdfPCell(new Phrase("Table 1 Data - " + i, cellFont));
                    cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell4.setBackgroundColor(new BaseColor(136, 159, 251));
                    table1.addCell(cell4);

                    cell4 = new PdfPCell(new Phrase("Table 1 Data - " + i, cellFont));
                    cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell4.setBackgroundColor(new BaseColor(136, 159, 251));
                    table1.addCell(cell4);              
                    
                    try {
                        pdfDocument.add(table1);
                    } catch (DocumentException ex) {
                        Logger.getLogger(ResultsGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    

                        
                        PdfPTable table2 = new PdfPTable(1);
                        try 
                        {
                            table2.setWidths(new float[] {8});
                        } 
                        catch (DocumentException ex) 
                        {
                            Logger.getLogger(ResultsGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        PdfPCell cell5 = new PdfPCell(new Phrase("Extra Info", tableHeaderFont));
                        cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell5.setBackgroundColor(new BaseColor(136, 159, 251));
                        table2.addCell(cell5);
                        
                    try {
                        pdfDocument.add(table2);
                    } catch (DocumentException ex) {
                        Logger.getLogger(ResultsGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }


                    for(int x = 0; x < 3; x++)
                    {       
                        PdfPTable table3 = new PdfPTable(1);
                        try 
                        {
                            table3.setWidths(new float[] {8});
                        } 
                        catch (DocumentException ex) 
                        {
                            Logger.getLogger(ResultsGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        
                        
                        PdfPCell cell6 = new PdfPCell(new Phrase("Table 2 Data - " + x, cellFont));
                        cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell6.setBackgroundColor(new BaseColor(136, 159, 251));
                        table3.addCell(cell6);
                        
                        try {
                            pdfDocument.add(table3);
                        } catch (DocumentException ex) {
                            Logger.getLogger(ResultsGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } 
                }
                pdfDocument.close();
                System.out.println("PDF file saved successfully!");

                if(Desktop.isDesktopSupported())
                {
                    createdPDFFile = new File("Uni Marks Calculator - Modules Results.pdf");
                    try {
                        Desktop.getDesktop().open(createdPDFFile);
                    } catch (IOException ex) {
                        Logger.getLogger(ResultsGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                
                
                
             
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
//                documentHeading = new Paragraph("MODULES WITH ASSIGNMENTS\n ", documentHeaderFont);
//                documentHeading.setAlignment(Element.ALIGN_CENTER);
//
//                Paragraph spaceBetweenLines = new Paragraph(" ");
//                spaceBetweenLines.setLeading(0, 5);
//
//                PdfPTable documentTable = new PdfPTable(2);
//                try 
//                {
//                    documentTable.setWidths(new float[] {5,5});
//                } 
//                catch (DocumentException ex) 
//                {
//                    Logger.getLogger(ResultsGUI.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                
//                PdfPTable moduleAssignmentsTable;
//                moduleAssignmentsTable = new PdfPTable(4);
//                try 
//                {
//                    moduleAssignmentsTable.setWidths(new float[] {8,2,2,2});
//                } 
//                catch (DocumentException ex) 
//                {
//                    Logger.getLogger(ResultsGUI.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                
//                
//                String[] columnNames = {"No.", "Module Title", "Credits", "Semester", "Grade(%)", "Level"};
//                PdfPCell cell1 = null;
//                for(int i= 0; i < columnNames.length; i++)
//                {
//                    cell1 = new PdfPCell(new Phrase(columnNames[i], tableHeaderFont));
//                    cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
//                    cell1.setBackgroundColor(new BaseColor(136, 159, 251));
//                    documentTable.addCell(cell1);
//                }
//                //documentTable.setHeaderRows(1);
//
//                try 
//                {
//                    if(modelModulesTable.getRowCount() > 0)
//                    {
//                        writer = PdfWriter.getInstance(pdfDocument, new FileOutputStream("Uni Marks Calculator - Modules with Assignments Results.pdf"));
//                        PdfPageFooter footer = new PdfPageFooter();
//                        writer.setPageEvent(footer);
//
//                        Image uniLogoImage = Image.getInstance("images/uniMarksCalculatorLogo.jpg");
//                        uniLogoImage.scaleAbsolute(160f, 60f);
//                        pdfDocument.open();
//                        pdfDocument.add(uniLogoImage);
//                        pdfDocument.add(spaceBetweenLines);
//                        pdfDocument.add(documentHeading);
//                        
//                        for(int tableRow = 0; tableRow < modelModulesTable.getRowCount(); tableRow++)
//                        {
//                            String moduleName = modelModulesTable.getValueAt(tableRow, 1).toString();
//                            moduleObject = userModulesManager.getModule(moduleName);
//                            String[] moduleInfo = moduleObject.toStringDataArray();
//                            ArrayList<Assignment> assignmentsListOfSelectedModule = moduleObject.getAllAssignments();
//                            //pdfDocument.add(new Paragraph(new Phrase(lineSpacing, moduleObject.toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, fontSize))));
//
//                            // NUMBER
//                            PdfPCell cell2 = new PdfPCell(new Phrase(String.valueOf(tableRow+1), cellFont));
//                            documentTable.addCell(cell2);
//
//                            // NAME
//                            cell2 = new PdfPCell(new Phrase(moduleInfo[0], cellFont));
//                            documentTable.addCell(cell2);
//
//                            // CREDITS
//                            cell2 = new PdfPCell(new Phrase(moduleInfo[3], cellFont));
//                            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
//                            documentTable.addCell(cell2);
//
//                            // SEMESTER
//                            cell2 = new PdfPCell(new Phrase(moduleInfo[2], cellFont));
//                            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
//                            documentTable.addCell(cell2);
//
//                            // GRADE(%)
//                            cell2 = new PdfPCell(new Phrase(moduleInfo[4], cellFont));
//                            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
//                            documentTable.addCell(cell2);
//
//                            // LEVEL
//                            cell2 = new PdfPCell(new Phrase(moduleInfo[1], cellFont));
//                            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
//                            documentTable.addCell(cell2);
//                            
//                            // SPACE BETWEEN MODULES
//                            //cell2 = new PdfPCell(new Phrase("", cellFont));
//                            //cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
//                            //documentTable.addCell(cell2);
//                            
//                            if(assignmentsListOfSelectedModule.size() > 0)
//                            {
//                                // make 1 line space
//                                // add all assignments
//                                // make 1 line space                                
//                                
//                                PdfPCell cell3 = new PdfPCell(new Phrase("Assignment Title", tableHeaderFont));
//                                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
//                                cell3.setBackgroundColor(new BaseColor(136, 159, 251));
//                                moduleAssignmentsTable.addCell(cell3);
//
//                                cell3 = new PdfPCell(new Phrase("Type", tableHeaderFont));
//                                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
//                                cell3.setBackgroundColor(new BaseColor(136, 159, 251));
//                                moduleAssignmentsTable.addCell(cell3);
//
//                                cell3 = new PdfPCell(new Phrase("Result(%)", tableHeaderFont));
//                                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
//                                cell3.setBackgroundColor(new BaseColor(136, 159, 251));
//                                moduleAssignmentsTable.addCell(cell3);
//
//                                cell3 = new PdfPCell(new Phrase("Weight(%)", tableHeaderFont));
//                                cell3.setBackgroundColor(new BaseColor(136, 159, 251));
//                                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
//                                moduleAssignmentsTable.addCell(cell3);
//                                
//                                
//                                for (Assignment tempAssignment : assignmentsListOfSelectedModule) 
//                                {
//                                    String[] assignmentInfo = tempAssignment.toStringDataArray();
//                                    
//                                    // TITLE
//                                    cell2 = new PdfPCell(new Phrase(assignmentInfo[0], cellFont));
//                                    moduleAssignmentsTable.addCell(cell2);
//
//                                    // TYPE
//                                    cell2 = new PdfPCell(new Phrase(assignmentInfo[1], cellFont));
//                                    cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
//                                    moduleAssignmentsTable.addCell(cell2);
//
//                                    // RESULT
//                                    cell2 = new PdfPCell(new Phrase(assignmentInfo[2], cellFont));
//                                    cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
//                                    moduleAssignmentsTable.addCell(cell2);
//
//                                    // WEIGHT
//                                    cell2 = new PdfPCell(new Phrase(assignmentInfo[3], cellFont));
//                                    cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
//                                    moduleAssignmentsTable.addCell(cell2);
//                                    
//                                }
//                                pdfDocument.add(documentTable);
//                                pdfDocument.add(moduleAssignmentsTable);
//                                
//
//                                
//                                
//                                
//                                
//                                
//                                
//                                
//                            }
//                            
//                        }
//                        
//                        
//                        
//                        
//                        pdfDocument.close();
//                        System.out.println("PDF file saved successfully!");
//
//                        if(Desktop.isDesktopSupported())
//                        {
//                            createdPDFFile = new File("Uni Marks Calculator - Modules with Assignments Results.pdf");
//                            Desktop.getDesktop().open(createdPDFFile);
//                        }
//                    }
//                    else
//                    {
//                        JOptionPane.showMessageDialog(resultsFrame, "No modules on the list.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
//                    }
//                } 
//                catch (IOException ex) 
//                {
//                    System.out.println(ex.toString());
//                } 
//                catch (DocumentException ex) 
//                {
//                    Logger.getLogger(ResultsGUI.class.getName()).log(Level.SEVERE, null, ex);
//                }                
//                
//                
//                
//                
//                
//                
//                
//                
//                
//                
////                documentHeading = new Paragraph("MODULES WITH ASSIGNMENTS\n ", documentHeaderFont);
////                documentHeading.setAlignment(Element.ALIGN_CENTER);
////
////                Paragraph spaceBetweenLines = new Paragraph(" ");
////                spaceBetweenLines.setLeading(0, 5);
////
////                PdfPTable documentTable = new PdfPTable(6);
////                try 
////                {
////                    documentTable.setWidths(new float[] {1,8,2,2,3,2});
////                } 
////                catch (DocumentException ex) 
////                {
////                    Logger.getLogger(ResultsGUI.class.getName()).log(Level.SEVERE, null, ex);
////                }
////                
////                PdfPTable moduleAssignmentsTable;
////                moduleAssignmentsTable = new PdfPTable(4);
////                try 
////                {
////                    moduleAssignmentsTable.setWidths(new float[] {8,2,2,2});
////                } 
////                catch (DocumentException ex) 
////                {
////                    Logger.getLogger(ResultsGUI.class.getName()).log(Level.SEVERE, null, ex);
////                }
////
////                PdfPCell cell1 = new PdfPCell(new Phrase("No.", tableHeaderFont));
////                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
////                cell1.setBackgroundColor(new BaseColor(136, 159, 251));
////                documentTable.addCell(cell1);
////
////                cell1 = new PdfPCell(new Phrase("Module Title", tableHeaderFont));
////                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
////                cell1.setBackgroundColor(new BaseColor(136, 159, 251));
////                documentTable.addCell(cell1);
////
////                cell1 = new PdfPCell(new Phrase("Credits", tableHeaderFont));
////                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
////                cell1.setBackgroundColor(new BaseColor(136, 159, 251));
////                documentTable.addCell(cell1);
////
////                cell1 = new PdfPCell(new Phrase("Semester", tableHeaderFont));
////                cell1.setBackgroundColor(new BaseColor(136, 159, 251));
////                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
////                documentTable.addCell(cell1);
////
////                cell1 = new PdfPCell(new Phrase("Grade(%)", tableHeaderFont));
////                cell1.setBackgroundColor(new BaseColor(136, 159, 251));
////                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
////                documentTable.addCell(cell1);
////
////                cell1 = new PdfPCell(new Phrase("Level", tableHeaderFont));
////                cell1.setBackgroundColor(new BaseColor(136, 159, 251));
////                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
////                documentTable.addCell(cell1);
////                //documentTable.setHeaderRows(1);
////
////                try 
////                {
////                    if(modelModulesTable.getRowCount() > 0)
////                    {
////                        writer = PdfWriter.getInstance(pdfDocument, new FileOutputStream("Uni Marks Calculator - Modules with Assignments Results.pdf"));
////                        PdfPageFooter footer = new PdfPageFooter();
////                        writer.setPageEvent(footer);
////
////                        Image uniLogoImage = Image.getInstance("images/uniMarksCalculatorLogo.jpg");
////                        uniLogoImage.scaleAbsolute(160f, 60f);
////                        pdfDocument.open();
////                        pdfDocument.add(uniLogoImage);
////                        pdfDocument.add(spaceBetweenLines);
////                        pdfDocument.add(documentHeading);
////                        
////                        for(int tableRow = 0; tableRow < modelModulesTable.getRowCount(); tableRow++)
////                        {
////                            String moduleName = modelModulesTable.getValueAt(tableRow, 1).toString();
////                            moduleObject = userModulesManager.getModule(moduleName);
////                            String[] moduleInfo = moduleObject.toStringDataArray();
////                            ArrayList<Assignment> assignmentsListOfSelectedModule = moduleObject.getAllAssignments();
////                            //pdfDocument.add(new Paragraph(new Phrase(lineSpacing, moduleObject.toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, fontSize))));
////
////                            // NUMBER
////                            PdfPCell cell2 = new PdfPCell(new Phrase(String.valueOf(tableRow+1), cellFont));
////                            documentTable.addCell(cell2);
////
////                            // NAME
////                            cell2 = new PdfPCell(new Phrase(moduleInfo[0], cellFont));
////                            documentTable.addCell(cell2);
////
////                            // CREDITS
////                            cell2 = new PdfPCell(new Phrase(moduleInfo[3], cellFont));
////                            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
////                            documentTable.addCell(cell2);
////
////                            // SEMESTER
////                            cell2 = new PdfPCell(new Phrase(moduleInfo[2], cellFont));
////                            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
////                            documentTable.addCell(cell2);
////
////                            // GRADE(%)
////                            cell2 = new PdfPCell(new Phrase(moduleInfo[4], cellFont));
////                            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
////                            documentTable.addCell(cell2);
////
////                            // LEVEL
////                            cell2 = new PdfPCell(new Phrase(moduleInfo[1], cellFont));
////                            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
////                            documentTable.addCell(cell2);
////                            
////                            // SPACE BETWEEN MODULES
////                            //cell2 = new PdfPCell(new Phrase("", cellFont));
////                            //cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
////                            //documentTable.addCell(cell2);
////                            
////                            if(assignmentsListOfSelectedModule.size() > 0)
////                            {
////                                // make 1 line space
////                                // add all assignments
////                                // make 1 line space                                
////                                
////                                PdfPCell cell3 = new PdfPCell(new Phrase("Assignment Title", tableHeaderFont));
////                                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
////                                cell3.setBackgroundColor(new BaseColor(136, 159, 251));
////                                moduleAssignmentsTable.addCell(cell3);
////
////                                cell3 = new PdfPCell(new Phrase("Type", tableHeaderFont));
////                                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
////                                cell3.setBackgroundColor(new BaseColor(136, 159, 251));
////                                moduleAssignmentsTable.addCell(cell3);
////
////                                cell3 = new PdfPCell(new Phrase("Result(%)", tableHeaderFont));
////                                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
////                                cell3.setBackgroundColor(new BaseColor(136, 159, 251));
////                                moduleAssignmentsTable.addCell(cell3);
////
////                                cell3 = new PdfPCell(new Phrase("Weight(%)", tableHeaderFont));
////                                cell3.setBackgroundColor(new BaseColor(136, 159, 251));
////                                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
////                                moduleAssignmentsTable.addCell(cell3);
////                                
////                                
////                                for (Assignment tempAssignment : assignmentsListOfSelectedModule) 
////                                {
////                                    String[] assignmentInfo = tempAssignment.toStringDataArray();
////                                    
////                                    // TITLE
////                                    cell2 = new PdfPCell(new Phrase(assignmentInfo[0], cellFont));
////                                    moduleAssignmentsTable.addCell(cell2);
////
////                                    // TYPE
////                                    cell2 = new PdfPCell(new Phrase(assignmentInfo[1], cellFont));
////                                    cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
////                                    moduleAssignmentsTable.addCell(cell2);
////
////                                    // RESULT
////                                    cell2 = new PdfPCell(new Phrase(assignmentInfo[2], cellFont));
////                                    cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
////                                    moduleAssignmentsTable.addCell(cell2);
////
////                                    // WEIGHT
////                                    cell2 = new PdfPCell(new Phrase(assignmentInfo[3], cellFont));
////                                    cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
////                                    moduleAssignmentsTable.addCell(cell2);
////                                    
////                                }
////                                pdfDocument.add(documentTable);
////                                pdfDocument.add(moduleAssignmentsTable);
////                                
////
////                                
////                                
////                                
////                                
////                                
////                                
////                                
////                            }
////                            
////                        }
////                        
////                        
////                        
////                        
////                        pdfDocument.close();
////                        System.out.println("PDF file saved successfully!");
////
////                        if(Desktop.isDesktopSupported())
////                        {
////                            createdPDFFile = new File("Uni Marks Calculator - Modules with Assignments Results.pdf");
////                            Desktop.getDesktop().open(createdPDFFile);
////                        }
////                    }
////                    else
////                    {
////                        JOptionPane.showMessageDialog(resultsFrame, "No modules on the list.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
////                    }
////                } 
////                catch (IOException ex) 
////                {
////                    System.out.println(ex.toString());
////                } 
////                catch (DocumentException ex) 
////                {
////                    Logger.getLogger(ResultsGUI.class.getName()).log(Level.SEVERE, null, ex);
////                }



            }                 
        }
    }
        
    // ***** M E T H O D S -------------------------------------------------------------------------------------   
    public void setWindowVisible(boolean visibility)
    {
        resultsFrame.setVisible(visibility);
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
        boolean modulesAvailableForCalculation = false;
        int countModulesSelectedForCalculation = 0;
        int allModulesOnLevel5and6 = 0;
        
        for(int row = 0; row < tableModules.getRowCount(); row++)
        {
            String moduleLevel = tableModules.getValueAt(row, 5).toString();
            if(moduleLevel.equals("5") || moduleLevel.equals("6"))
            {
                allModulesOnLevel5and6 += 1;
            }
        }
        System.out.println("Modules on level5/level6: " + allModulesOnLevel5and6);
        
        for(int row = 0; row < tableModules.getRowCount(); row++)
        {
            if((tableModules.getValueAt(row, 0).toString()).equals("true"))
            {
                countModulesSelectedForCalculation += 1;
            }
        }
        
        if((countModulesSelectedForCalculation == allModulesOnLevel5and6) && (countModulesSelectedForCalculation >= 12))
        {
            modulesAvailableForCalculation = true;
        }
        return modulesAvailableForCalculation;
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
        ArrayList<Module> mixedLevelModulesLeft = new ArrayList<>();
        ArrayList<Module> bestLevel6Modules = new ArrayList<>();
        ArrayList<Module> bestMixedLevelModules = new ArrayList<>();
        double averageMarkBestLevel6Modules;
        double averageMarkBestMixedModules;
        double totalModulesGrades = 0;
        double finalDegreeClassification = 0;
        
        System.out.println("\n-----All modules selected for calculation:");
        for(Module temp : allModulesSelectedForCalculation)
        {
            System.out.println("Module: " + temp.getName() + "|  Level: " + temp.getLevel() + "| Credits: " + temp.getCredits());
        }
        
        if(allModulesSelectedForCalculation.isEmpty())
        {
            JOptionPane.showMessageDialog(resultsFrame, "No modules to be calculated.", "ERROR Info", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            // segregate modules into Level5 and Level6 modules
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
                else
                {
                    System.out.println("Ambiguous LEVEL:" + tempModule.getLevel());
                }
            }
  
            // sort chosenModulesLevel6
            System.out.println("\n----- LEVEL 6 before sorting:");
            for(Module tempModule : chosenModulesLevel6)
            {
                System.out.println(tempModule.getName() + "| Level: " + tempModule.getLevel() + " | Grade: " + tempModule.getGrade());
            }
            
            chosenModulesLevel6 = sortModules(chosenModulesLevel6);

            System.out.println("\n----- LEVEL 6 after sorting:");
            for(Module tempModule : chosenModulesLevel6)
            {
                System.out.println(tempModule.getName() + " | Level: " + tempModule.getLevel() + " | Grade: " + tempModule.getGrade());
            }           
            
            int arrayListIndex = 0;
            while(arrayListIndex <= 5)
            {
                Module tempModule = chosenModulesLevel6.get(arrayListIndex);

                bestLevel6Modules.add(tempModule);
                arrayListIndex++;      
            }
            
            // remove modules moved from chosenModulesLevel6 to bestLevel6Modules
            for(Module tempModule : bestLevel6Modules)
            {
                if(chosenModulesLevel6.contains(tempModule))
                {
                    chosenModulesLevel6.remove(tempModule);
                    System.out.println("Removed: " + tempModule.getName());
                }
                
            }      
            System.out.println("chosenModulesLevel6 list has been cleared...");
            
            // move all chosenModulesLevel5 and remaining chosenModulesLevel6 to one collection - mixedLevelModulesLeft
            for(Module tempModule : chosenModulesLevel5)
            {
                mixedLevelModulesLeft.add(tempModule);
            }
            
            for(Module tempModule : chosenModulesLevel6)
            {
                mixedLevelModulesLeft.add(tempModule);
            }
            
            System.out.println("-----Checking mixedLevelModulesLeft contents [before sort]:");
            for(Module tempModule : mixedLevelModulesLeft)
            {
                System.out.println(tempModule.getName() + " | Level: " + tempModule.getLevel() + " | Grade: " + tempModule.getGrade());
            }
            System.out.println("----------------------------------------------------");
            // sort mixedLevelModulesLeft
            mixedLevelModulesLeft = sortModules(mixedLevelModulesLeft);
            
            System.out.println("-----Checking mixedLevelModulesLeft contents [after sort]:");
            for(Module tempModule : mixedLevelModulesLeft)
            {
                System.out.println(tempModule.getName() + " | Level: " + tempModule.getLevel() + " | Grade: " + tempModule.getGrade());
            }
            
            // calculate averageMarkBestLevel6Modules (averageMark * 0.75)
            for(Module tempModule : bestLevel6Modules)
            {
                totalModulesGrades = totalModulesGrades + tempModule.getGrade();                
            }
            averageMarkBestLevel6Modules = (totalModulesGrades / 6) * 0.75;
            System.out.println("-----averageMarkBestLevel6Modules: " + averageMarkBestLevel6Modules);
            
            // take best 6 modules from mixedLevelModulesLeft to bestMixedLevelModules
            arrayListIndex = 0;
            while(arrayListIndex < 5)
            {
                Module tempModule = mixedLevelModulesLeft.get(arrayListIndex);
                bestMixedLevelModules.add(tempModule);
                arrayListIndex++;      
            }
            
            // calculate averageMarkBestMixedModules (averageMark * 0.25)
            totalModulesGrades = 0;
            for(Module tempModule : bestMixedLevelModules)
            {
                totalModulesGrades = totalModulesGrades + tempModule.getGrade();
            }
            averageMarkBestMixedModules = (totalModulesGrades / 6) * 0.25;
            System.out.println("-----averageMarkBestMixedModules: " + averageMarkBestMixedModules);
            
            // add averageMarkBestLevel6Modules to averageMarkBestMixedModules
            finalDegreeClassification = averageMarkBestLevel6Modules + averageMarkBestMixedModules;
            
            // round and set final grade:
            finalDegreeClassification = roundFinalGrade(finalDegreeClassification);
            setFinalGrade(finalDegreeClassification);
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
        Collections.sort(arrayListToBeSorted);   
        
        System.out.println("*INFO: sorted");

        return arrayListToBeSorted;
    }
    
    private double roundFinalGrade(double newGrade) 
    {
        BigDecimal tempGrade = new BigDecimal(newGrade);
        BigDecimal roundOff = tempGrade.setScale(2, RoundingMode.HALF_UP);
        return roundOff.doubleValue();
    }
    
    private void displayFinalDegreeClassification()
    {
        double finalGrade = getFinalGrade();
        String finalGradeCalculated = String.valueOf(finalGrade);
        outputFinalGrade.setText(finalGradeCalculated);
        
        String degreeClassificationName = "";
        if(finalGrade >= 70)
        {
            degreeClassificationName = "First Class";
        }
        else if(finalGrade >= 60)
        {
            degreeClassificationName = "Upper Second Class";
        }
        else if(finalGrade >= 50)
        {
            degreeClassificationName = "Lower Second Class";
        }
        else if(finalGrade >= 40)
        {
            degreeClassificationName = "Third Class";
        }
        else
        {
            degreeClassificationName = "Fail";
        }              
        outputDegreeClassificationName.setText(degreeClassificationName);
    }
}