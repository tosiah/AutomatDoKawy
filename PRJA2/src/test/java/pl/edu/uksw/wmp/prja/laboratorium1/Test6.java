package pl.edu.uksw.wmp.prja.laboratorium1;

import org.junit.Test;
import pl.edu.uksw.wmp.prja.laboratorium2.Automat;
import pl.edu.uksw.wmp.prja.laboratorium2.Moneta;

import static org.junit.Assert.assertEquals;

/**
 * Created by Antonina on 2018-05-07.
 */
public class Test6 extends TestBase {

    @Test(timeout = 300)
    public void checkInitCoins() {
        Automat a = new Automat();
        for (Moneta nominal : Moneta.values()) {
            assertEquals("Nieprawidłowe wypełnienie monetami nominału: " + nominal.getValue(),
                    5,
                    countAmountOfNominal(a.getCoinsInAutomat(), nominal)
            );
        }
    }

    private int countAmountOfNominal(Moneta[] coins, Moneta nominal) {
        int numberOfNominal = 0;
        for (int i = 0; i < coins.length; i++) {
            if (coins[i] == nominal) {
                numberOfNominal++;
            }
        }
        return numberOfNominal;
    }
}


