package pl.edu.uksw.wmp.prja.laboratorium2;

/**
 * Created by Antonina on 2018-05-03.
 */
public class CoffeeInformation {
    private double price;
    private double amountOfMilk;

    public CoffeeInformation(double price, double amountOfMilk){
        this.price = price;
        this.amountOfMilk = amountOfMilk;
    }

    public double getPrice() {
        return price;
    }

    public double getAmountOfMilk() {
        return amountOfMilk;
    }
}
