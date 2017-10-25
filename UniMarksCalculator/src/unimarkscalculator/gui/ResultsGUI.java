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
    private JFrame myForm = new JFrame();
    private JLabel resultsLabel = new JLabel("Results", JLabel.CENTER);
    private JLabel yearLabel = new JLabel("year: ");
    private JLabel modulesListLabel = new JLabel("modules list: ");
    private JLabel assignmentsListLabel = new JLabel("assignments list: ");
    private JLabel modulesForCalculationLabel = new JLabel("modules chosen for calculation: ");
    private JLabel finalGradeLabel = new JLabel("Final Grade: ");
    private JButton showAllButton = new JButton("Show all results");
    private JButton calculateFinalGradeButton = new JButton("Calculate FINAL GRADE");
    private JButton printResultsButton = new JButton("Print all results");
    
    
    
}
