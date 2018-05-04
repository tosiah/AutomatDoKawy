package pl.edu.uksw.wmp.prja.laboratorium1;

import org.junit.Test;
import pl.edu.uksw.wmp.prja.laboratorium2.Moneta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

/**
 *
 * @author pkacz_000
 */
public class Punkt5Test extends TestBase {

    @Test(timeout=300)
    public void podajKaweIstnieje() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class c = Class.forName("pl.edu.uksw.wmp.prja.laboratorium2.Automat");
        Class rodzajKawy = Class.forName("pl.edu.uksw.wmp.prja.laboratorium2.RodzajKawy");
        try {
            Method orderCoffee = c.getMethod("orderCoffee", rodzajKawy, Moneta[].class);
            assertFalse("Metoda powinna byc publiczna", (orderCoffee.getModifiers() & Modifier.PUBLIC) == 0);
            assertEquals("Zly typ zwracany", orderCoffee.getReturnType(), Moneta[].class);
        } catch (NoSuchMethodException ex) {
            fail("Brak prawidlowej metody orderCoffee");
        } catch (SecurityException ex) {
            fail("" + ex.getCause());
        } catch (IllegalArgumentException ex) {
            fail("Zla metoda");
        }
    }
    
    @Test(timeout=300)
    public void dobraReszta() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException, IllegalArgumentException {
        Class c = Class.forName("pl.edu.uksw.wmp.prja.laboratorium2.Automat");
        Class rodzajKawy = Class.forName("pl.edu.uksw.wmp.prja.laboratorium2.RodzajKawy");
        Object automat = c.newInstance();
        Method orderCoffee = c.getMethod("orderCoffee", rodzajKawy, Moneta[].class);
        try {
            Moneta[] reszta = (Moneta[])orderCoffee.invoke(automat,
                                           Enum.valueOf(rodzajKawy, "KAWA_Z_MLEKIEM_I_CUKREM"),
                                           new Moneta[]{ Moneta.ZL2 });
            assertEquals("Zla reszta!", 0.2, wartoscMonet(reszta), 0.001);
            reszta = (Moneta[])orderCoffee.invoke(automat,
                                           Enum.valueOf(rodzajKawy, "KAWA_Z_MLEKIEM_I_CUKREM"),
                                           new Moneta[]{ Moneta.ZL5 });
            assertEquals("Zla reszta!", 3.2, wartoscMonet(reszta), 0.001);

            reszta = (Moneta[])orderCoffee.invoke(automat,
                                           Enum.valueOf(rodzajKawy, "KAWA_Z_MLEKIEM"),
                                           new Moneta[]{ Moneta.ZL1, Moneta.GR10, Moneta.GR50 });
            assertEquals("Zla reszta!", 0.0, wartoscMonet(reszta), 0.001);

        } catch (InvocationTargetException ex) {
            fail("Blad podczas metody orderCoffee " + ex.getCause());
        }
    }
    
    private double wartoscMonet(Moneta[] monety) {
        double sum = 0;
        for(Moneta m : monety) {
            sum += m.getValue();
        }
        return sum;
    }

}