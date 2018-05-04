package pl.edu.uksw.wmp.prja.laboratorium2;

/**
 * Created by Antonina on 2018-05-03.
 */
public class NotEnoughCupsException extends Exception {
    public NotEnoughCupsException(){
        super("Brak kubków");
    }
}
