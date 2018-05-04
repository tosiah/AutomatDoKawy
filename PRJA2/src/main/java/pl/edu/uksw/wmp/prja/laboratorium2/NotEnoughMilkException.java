package pl.edu.uksw.wmp.prja.laboratorium2;

/**
 * Created by Antonina on 2018-05-03.
 */
public class NotEnoughMilkException extends Exception {
    public NotEnoughMilkException(){
        super("Nie ma wystarczająco mleka");
    }
}
