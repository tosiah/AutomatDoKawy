package pl.edu.uksw.wmp.prja.laboratorium1;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 *
 * @author pkacz_000
 */
public class Punkt1Test extends TestBase {
        
    @Test(timeout=300)
    public void iloscKubkowIstnieje() throws ClassNotFoundException {
        try {
            Class c = Class.forName("pl.edu.uksw.wmp.prja.laboratorium2.Automat");
            Field f = c.getDeclaredField("iloscKubkow");
            assertTrue("Zły modyfikator widoczności", 
                       (Modifier.PUBLIC & f.getModifiers()) == 0);
        } catch (NoSuchFieldException ex) {
            fail("Nie ma pola iloscKubkow");
        }
    }
    
    @Test(timeout=300)
    public void iloscMleka() throws ClassNotFoundException {
        try {
            Class c = Class.forName("pl.edu.uksw.wmp.prja.laboratorium2.Automat");
            Field f = c.getDeclaredField("iloscKubkow");
            assertTrue("Zły modyfikator widoczności", 
                       (f.getModifiers() & Modifier.PUBLIC) == 0);

        } catch (NoSuchFieldException ex) {
            fail("Nie ma pola iloscMleka");
        }
    }

}