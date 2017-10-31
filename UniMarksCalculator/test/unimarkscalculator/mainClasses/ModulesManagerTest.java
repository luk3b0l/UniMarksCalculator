package unimarkscalculator.mainClasses;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Lukasz Bol
 */
public class ModulesManagerTest 
{
    public ModulesManagerTest(){}
    
    @BeforeClass
    public static void setUpClass(){}
    
    @AfterClass
    public static void tearDownClass(){}
    
    @Before
    public void setUp(){}
    
    @After
    public void tearDown(){} 
    
    @Test
    public void testGetInstance()
    {
        ModulesManager testingModulesManager = ModulesManager.getInstance();
        String description = "NEW ModulesManager";
        testingModulesManager.setDescription(description);
        Assert.assertEquals(description, testingModulesManager.getDescription());
    }
}