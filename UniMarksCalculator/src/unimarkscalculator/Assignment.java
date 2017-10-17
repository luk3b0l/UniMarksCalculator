/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unimarkscalculator;

/**
 *
 * @author Lukasz Bol
 */
public class Assignment 
{
    private String title;
    private String type;    // test/exam
    private double result;
    private double weightPercent;
    
    public Assignment(String newTitle, String newType, double newResult, double newWeightPercent)
    {
        this.title = newTitle;
        this.type = newType;
        this.result = newResult;
        this.weightPercent = newWeightPercent;
    }
}
