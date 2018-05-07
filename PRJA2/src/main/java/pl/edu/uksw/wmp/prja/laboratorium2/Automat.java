/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uksw.wmp.prja.laboratorium2;


import java.util.*;

public class Automat {
    private int amountOfCups;
    private double amountOfMilk;
    private Moneta[] coinsInAutomat = new Moneta[250];
    private Map<RodzajKawy, CoffeeInformation> coffeeMenu = new HashMap<>();

    public Moneta[] getCoinsInAutomat() {
        return coinsInAutomat;
    }

    //konstruktor
    public Automat() {
        this.amountOfCups = 10;
        this.amountOfMilk = 100;
        initCoins();
        initCoffeeMenu();
    }

    private void initCoffeeMenu() {
        coffeeMenu.put(RodzajKawy.KAWA_CZARNA, new CoffeeInformation(1.60, 0.0));
        coffeeMenu.put(RodzajKawy.KAWA_CZARNA_Z_CUKREM, new CoffeeInformation(1.60, 0.0));
        coffeeMenu.put(RodzajKawy.KAWA_Z_MLEKIEM, new CoffeeInformation(1.80, 0.5));
        coffeeMenu.put(RodzajKawy.KAWA_Z_MLEKIEM_I_CUKREM, new CoffeeInformation(1.80, 0.3));
    }

    //wypełnienie Automatu monetami - po 5 monet każdego nominału
    private void initCoins() {
        int nominalIteration = 0;
        for (Moneta nominal : Moneta.values()) {

            for (int i = nominalIteration; i < nominalIteration + 5; i++) {
                coinsInAutomat[i] = nominal;
            }
            nominalIteration += 5;
        }
    }

    //konstruktor
    public Automat(int amountOfCups, double amountOfMilk) {
        if (amountOfCups < 0) {
            amountOfCups = 0;
        }
        if (amountOfMilk < 0) {
            amountOfMilk = 0;
        }

        this.amountOfCups = amountOfCups;
        this.amountOfMilk = amountOfMilk;
        initCoins();
    }

    //dodawanie kubków
    public void addCups(int cupsToAdd) {
        if (cupsToAdd > 0) {
            amountOfCups += cupsToAdd;
        }
        if (amountOfCups > 1000) {
            amountOfCups = 1000;
        }
    }

    //dodawanie mleka
    public void addMilk(double milkToAdd) {
        if (milkToAdd > 0) {
            amountOfMilk += milkToAdd;
        }
        if (amountOfMilk > 200) {
            amountOfMilk = 200;
        }
    }

    //sprawdzenie czy jest wystarczająco kubków i mleka
    public RodzajKawy chooseCoffee(RodzajKawy choice) throws NotEnoughCupsException, NotSuchBeverageException, NotEnoughMilkException {
        if (amountOfCups < 1) {
            throw new NotEnoughCupsException();
        }
        if (!coffeeMenu.containsKey(choice)) {
            throw new NotSuchBeverageException();
        }

        double requiredAmountOfMilk = coffeeMenu.get(choice).getAmountOfMilk();
        if (amountOfMilk < requiredAmountOfMilk) {
            throw new NotEnoughMilkException();
        }
        amountOfCups--;
        amountOfMilk -= requiredAmountOfMilk;
        return choice;
    }

    //spawdzenie czy jest wystarczająco miejsca w automacie na wrzucone przez klienta monety
    private boolean isEnoughSpace(Moneta[] clientCoins) {
        Moneta[] coinsInAutomatCopy = new Moneta[coinsInAutomat.length];
        boolean wasSuccessful = true;
        for (int i = 0; i < coinsInAutomat.length; i++) {
            coinsInAutomatCopy[i] = coinsInAutomat[i];
        }

        for (int m = 0; m < clientCoins.length; m++) {
            Moneta moneta = clientCoins[m];
            if (countCoins(moneta, coinsInAutomatCopy) < 50) {
                for (int k = 0; k < coinsInAutomatCopy.length; k++) {
                    if (coinsInAutomatCopy[k] == null) {
                        coinsInAutomatCopy[k] = moneta;
                        break;
                    }
                }
            } else {
                wasSuccessful = false;
                break;
            }

        }
        if (wasSuccessful) {
            coinsInAutomat = coinsInAutomatCopy;
        } else {
            System.out.println("Przeładowany automat");
        }
        return wasSuccessful;
    }

    //wyszukanie na jakich indeksach tablicy monet automatu znajduje się dany nominał
    private int[] searchForCoins(Moneta nominal) {
        int[] automatIndexesOfCoins = new int[50];
        int coinIndex = 0;
        for (int i = 0; i < coinsInAutomat.length; i++) {
            if (coinsInAutomat[i] == nominal) {
                automatIndexesOfCoins[coinIndex] = i;
                coinIndex++;
            }
        }
        return automatIndexesOfCoins;
    }

    //wydanie reszty (wydawane są monety konkretnego nominału)
    private Moneta[] giveChange(Moneta nominal, int amountOfReturnCoins) {
        if (amountOfReturnCoins == 0) {
            return new Moneta[]{};
        }
        Moneta[] givenChange = new Moneta[amountOfReturnCoins];
        for (int i = 0; i < amountOfReturnCoins; i++) {
            coinsInAutomat[searchForCoins(nominal)[i]] = null;
        }
        Arrays.fill(givenChange, nominal);
        return givenChange;
    }

    // zamówienie kawy, wydanie reszty
    public Moneta[] orderCoffee(RodzajKawy choice, Moneta[] clientCoins) {
        double clientCoinsSum = 0;
        double clientChange;
        List<Moneta> clientChangeCoins = new ArrayList<>();
        double price = coffeeMenu.get(choice).getPrice();

        for (Moneta m : clientCoins) {
            clientCoinsSum += m.getValue();
        }

        try {
            if (chooseCoffee(choice) == choice) {
                clientChange = (clientCoinsSum * 10 - price * 10) / 10;
                if (clientChange < 0) {
                    System.out.println("Wrzuciłeś za mało pieniędzy");
                } else if (isEnoughSpace(clientCoins) && clientChange == 0) {
                    chooseCoffee(choice);

                } else if (isEnoughSpace(clientCoins) && clientChange > 0) {
                    for (Moneta nominal : reverseArrayOrder(Moneta.values())) {
                        int amountOfReturnNominal = countAmountOfCoinsToReturn(clientChange, nominal);
                        clientChange = clientChange - (nominal.getValue() * amountOfReturnNominal);
                        clientChangeCoins.addAll(Arrays.asList(giveChange(nominal, amountOfReturnNominal)));
                    }

                } else {
                    return null;
                }
            }
            return clientChangeCoins.toArray(new Moneta[clientChangeCoins.size()]);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return clientCoins;
        }

    }

    //zamiana kolejności pól emuma Monety
    private Moneta[] reverseArrayOrder(Moneta[] nominals) {
        Moneta[] reversedArray = new Moneta[nominals.length];
        for (int i = 0; i < nominals.length; i++) {
            reversedArray[i] = nominals[nominals.length - i - 1];
        }
        return reversedArray;
    }

    //ile monet konkretnego nominału wydać
    private int countAmountOfCoinsToReturn(double change, Moneta nominal) {
        int maxAmountOfCoinsToReturn = (int) (change / nominal.getValue());
        int amountOfCoinsInAutomat = countCoins(nominal, coinsInAutomat);
        int amountOfCoinsToReturn = Math.min(amountOfCoinsInAutomat, maxAmountOfCoinsToReturn);

        return amountOfCoinsToReturn;
    }


    //ile monet konkretnego nominału jest w tablicy monet
    private int countCoins(Moneta nominal, Moneta[] coinsInAutomat) {
        int amountOfCoins = 0;
        for (int i = 0; i < coinsInAutomat.length; i++) {
            if (coinsInAutomat[i] == nominal) {
                amountOfCoins++;
            }
        }
        return amountOfCoins;
    }

    
   /* public Moneta[] oproznij(Moneta[] coinsInAutomat){
        for(Moneta m: Moneta.values()){

        }
    }
    */

    //wypisz zawartość tablicy monet automatu
    public void printCoinsOfAutomat() {
        for (int i = 0; i < coinsInAutomat.length; i++) {
            System.out.println(coinsInAutomat[i]);
        }
    }
}





