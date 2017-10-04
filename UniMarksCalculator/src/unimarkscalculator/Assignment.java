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
    private String name;
    private String type;    // test/exam
    private double resultPercentage;
    
    public Assignment(String newName, String newType, double newResult)
    {
        this.name = newName;
        this.type = newType;
        this.resultPercentage = newResult;
    }
}
