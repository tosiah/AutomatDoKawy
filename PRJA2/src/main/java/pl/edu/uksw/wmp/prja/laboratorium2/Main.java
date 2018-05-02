package pl.edu.uksw.wmp.prja.laboratorium2;

/**
 * Created by Antonina on 2017-06-11.
 */
public class Main {
    public static void main(String[] args){
    Automat automat = new Automat();
    Automat tchibo = new Automat(0, 200);
    //automat.wypiszZawartoscKasyAutomatu();
    //tchibo.podajKawe(RodzajKawy.KAWA_CZARNA);
    Moneta[] reszta = automat.podajKawe(RodzajKawy.KAWA_Z_MLEKIEM_I_CUKREM, new Moneta[] {Moneta.ZL2});
    automat.wypiszZawartoscKasyAutomatu();

        System.out.println(wartoscMonet(reszta));
    }

    static double wartoscMonet(Moneta[] monety) {
        double sum = 0;
        for(Moneta m : monety) {
            sum += m.getWartosc();
        }
        return sum;
    }
}
