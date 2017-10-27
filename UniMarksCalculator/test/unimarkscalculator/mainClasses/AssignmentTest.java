package unimarkscalculator.mainClasses;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author Lukasz Bol
 */
public class AssignmentTest 
{
    private Assignment testAssignment;
    public AssignmentTest(){}
    
    @BeforeClass
    public static void setUpClass(){}
    
    @AfterClass
    public static void tearDownClass(){}
    
    @Before
    public void setUp()
    {
        testAssignment = new Assignment("PEN Test", "coursework", 70, 50);
    }
    
    @After
    public void tearDown(){}
    
    
}