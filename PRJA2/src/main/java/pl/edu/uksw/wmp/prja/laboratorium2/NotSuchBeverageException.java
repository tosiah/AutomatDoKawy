package pl.edu.uksw.wmp.prja.laboratorium2;

/**
 * Created by Antonina on 2018-05-03.
 */
public class NotSuchBeverageException extends Exception {
    public NotSuchBeverageException(){
        super("Nie ma takiego napoju");
    }
}
