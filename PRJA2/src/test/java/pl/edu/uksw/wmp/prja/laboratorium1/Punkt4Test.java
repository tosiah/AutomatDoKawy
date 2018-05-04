package pl.edu.uksw.wmp.prja.laboratorium1;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

/**
 *
 * @author pkacz_000
 */
public class Punkt4Test extends TestBase {

    @Test(timeout=300)
    public void rodzajKawy() {
        try {
            Class rodzajKawy = Class.forName("pl.edu.uksw.wmp.prja.laboratorium2.RodzajKawy");
            assertTrue("RodzajKawy powinien byc enumem", rodzajKawy.isEnum());
            checkIfEnumFieldExists(rodzajKawy, "KAWA_CZARNA_Z_CUKREM");
            checkIfEnumFieldExists(rodzajKawy, "KAWA_CZARNA");
            checkIfEnumFieldExists(rodzajKawy, "KAWA_Z_MLEKIEM");
            checkIfEnumFieldExists(rodzajKawy, "KAWA_Z_MLEKIEM_I_CUKREM");
        } catch (ClassNotFoundException ex) {
            fail("Rodzaj kawy nie istnieje");
        }
    }

    @Test(timeout=300)
    public void podajKaweIstnieje() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class c = Class.forName("pl.edu.uksw.wmp.prja.laboratorium2.Automat");
        Class rodzajKawy = Class.forName("pl.edu.uksw.wmp.prja.laboratorium2.RodzajKawy");
        try {
            Method chooseCoffee = c.getMethod("chooseCoffee", rodzajKawy);
            assertFalse("Metoda powinna byc publiczna", (chooseCoffee.getModifiers() & Modifier.PUBLIC) == 0);
            assertEquals("Zly typ zwracany", chooseCoffee.getReturnType(), rodzajKawy);
        } catch (NoSuchMethodException ex) {
            fail("Brak prawidlowej metody chooseCoffee");
        } catch (SecurityException ex) {
            fail("" + ex.getCause());
        } catch (IllegalArgumentException ex) {
            fail("Zla metoda");
        }
    }
    
    @Test(timeout=300)
    public void podajKaweZuzywaKubkiIMleko() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException, IllegalArgumentException {
        Class c = Class.forName("pl.edu.uksw.wmp.prja.laboratorium2.Automat");
        Class rodzajKawy = Class.forName("pl.edu.uksw.wmp.prja.laboratorium2.RodzajKawy");
        Object automat = c.newInstance();
        Method chooseCoffee = c.getMethod("chooseCoffee", rodzajKawy);
        try {
            Object kawa = chooseCoffee.invoke(automat, Enum.valueOf(rodzajKawy, "KAWA_Z_MLEKIEM_I_CUKREM"));
            assertEquals("Nie ta kawa! Prosilem o kawe z mlekiem i cukrem!", kawa, Enum.valueOf(rodzajKawy, "KAWA_Z_MLEKIEM_I_CUKREM"));
            assertEquals(9, getFieldValue(automat, "amountOfCups"));
            assertEquals(99.7, getFieldValue(automat, "amountOfMilk"));
            kawa = chooseCoffee.invoke(automat, Enum.valueOf(rodzajKawy, "KAWA_Z_MLEKIEM"));
            assertEquals("Nie ta kawa! Prosilem o kawe z mlekiem!", kawa, Enum.valueOf(rodzajKawy, "KAWA_Z_MLEKIEM"));
            assertEquals(8, getFieldValue(automat, "amountOfCups"));
            assertEquals(99.2, getFieldValue(automat, "amountOfMilk"));
            
            kawa = chooseCoffee.invoke(automat, Enum.valueOf(rodzajKawy, "KAWA_CZARNA"));
            assertEquals("Nie ta kawa! Prosilem o kawe czarna!", kawa, Enum.valueOf(rodzajKawy, "KAWA_CZARNA"));
            assertEquals(7, getFieldValue(automat, "amountOfCups"));
            assertEquals(99.2, getFieldValue(automat, "amountOfMilk"));
            
            kawa = chooseCoffee.invoke(automat, Enum.valueOf(rodzajKawy, "KAWA_CZARNA_Z_CUKREM"));
            assertEquals("Nie ta kawa! Prosilem o kawe czarna z cukrem!", kawa, Enum.valueOf(rodzajKawy, "KAWA_CZARNA_Z_CUKREM"));
            assertEquals(6, getFieldValue(automat, "amountOfCups"));
            assertEquals(99.2, getFieldValue(automat, "amountOfMilk"));
            
            for(int i=0; i<6; i++) {
                chooseCoffee.invoke(automat, Enum.valueOf(rodzajKawy, "KAWA_CZARNA_Z_CUKREM"));
                assertEquals(6-i-1, getFieldValue(automat, "amountOfCups"));
                assertEquals(99.2, getFieldValue(automat, "amountOfMilk"));
            }
            kawa = chooseCoffee.invoke(automat, Enum.valueOf(rodzajKawy, "KAWA_CZARNA_Z_CUKREM"));
            assertNull("Oj, kubki cudownie rozmnozyly sie!", kawa);
            assertEquals(0, getFieldValue(automat, "amountOfCups"));
            automat.getClass().getMethod("addCups", int.class).invoke(automat, 400);
            for(int i=0; i<198; i++) {
                chooseCoffee.invoke(automat, Enum.valueOf(rodzajKawy, "KAWA_Z_MLEKIEM"));
                assertEquals(99.2 - (i+1)*0.5, getFieldValue(automat, "amountOfMilk"));
            }
            System.out.println(getFieldValue(automat, "amountOfMilk"));
            kawa = chooseCoffee.invoke(automat, Enum.valueOf(rodzajKawy, "KAWA_Z_MLEKIEM"));
            assertNull("Oj, mleko cudownie rozmnozylo sie!", kawa);
            assertEquals(0.2, (Double)getFieldValue(automat, "amountOfMilk"), .00001);
            kawa = chooseCoffee.invoke(automat, Enum.valueOf(rodzajKawy, "KAWA_Z_MLEKIEM_I_CUKREM"));
            assertNull("Oj, mleko cudownie rozmnozylo sie!", kawa);
            assertEquals(0.2, (Double)getFieldValue(automat, "amountOfMilk"), .00001);
            
            kawa = chooseCoffee.invoke(automat, Enum.valueOf(rodzajKawy, "KAWA_CZARNA"));
            assertEquals("Nie ta kawa! Prosilem o kawe czarna!", kawa, Enum.valueOf(rodzajKawy, "KAWA_CZARNA"));            
            kawa = chooseCoffee.invoke(automat, Enum.valueOf(rodzajKawy, "KAWA_CZARNA_Z_CUKREM"));
            assertEquals("Nie ta kawa! Prosilem o kawe czarna z cukrem!", kawa, Enum.valueOf(rodzajKawy, "KAWA_CZARNA_Z_CUKREM"));
            
        } catch (InvocationTargetException ex) {
            System.out.println("Blad podczas metody chooseCoffee " + ex.getCause());
        }
    }

}