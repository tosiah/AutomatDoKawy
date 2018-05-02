/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uksw.wmp.prja.laboratorium2;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Automat {
    private int iloscKubkow;
    private double iloscMleka;
    private Moneta[] kasaAutomatu = new Moneta[250];


    public Automat() {
        this.iloscKubkow = 10;
        this.iloscMleka = 100;
        arsenal();
    }

    private void arsenal() {
        int licznik = 0;
        for (Moneta nominal : Moneta.values()) {

            for (int i = licznik; i < licznik + 5; i++) {
                kasaAutomatu[i] = nominal;
            }
            licznik += 5;
        }


    }

    public Automat(int iloscKubkow, double iloscMleka) {
        if (iloscKubkow < 0) {
            iloscKubkow = 0;
        }
        if (iloscMleka < 0) {
            iloscMleka = 0;
        }

        this.iloscKubkow = iloscKubkow;
        this.iloscMleka = iloscMleka;
        arsenal();
    }

    public void dodajKubki(int noweKubki) {
        if (noweKubki > 0) {
            iloscKubkow += noweKubki;
        }
        if (iloscKubkow > 1000) {
            iloscKubkow = 1000;
        }
    }

    public void dodajMleko(double noweMleko) {
        if (noweMleko > 0) {
            iloscMleka += noweMleko;
        }
        if (iloscMleka > 200) {
            iloscMleka = 200;
        }
    }


    public RodzajKawy podajKawe(RodzajKawy wybor) {
        RodzajKawy wydanaKawa = null;
        if (iloscKubkow >= 1) {
            if (wybor == RodzajKawy.KAWA_Z_MLEKIEM && iloscMleka >= 0.5) {
                iloscMleka -= 0.5;
                iloscKubkow--;
                wydanaKawa = RodzajKawy.KAWA_Z_MLEKIEM;
            }
            if (wybor == RodzajKawy.KAWA_Z_MLEKIEM_I_CUKREM && iloscMleka >= 0.3) {
                iloscMleka -= 0.3;
                iloscKubkow--;
                wydanaKawa = RodzajKawy.KAWA_Z_MLEKIEM_I_CUKREM;
            } else if (wybor != RodzajKawy.KAWA_Z_MLEKIEM && wybor != RodzajKawy.KAWA_Z_MLEKIEM_I_CUKREM) {
                wydanaKawa = wybor;
                iloscKubkow--;
            }
            System.out.println("Wydano: " + wydanaKawa);
        } else {
            System.out.println("Brak kubków");
        }
        return wydanaKawa;

    }

    public Moneta[] podajKawe(RodzajKawy wybor, Moneta[] wrzuconeMonety) {
        double sumaMonet = 0;
        double reszta = 0;
        List<Moneta> monetyReszty = new ArrayList<>();
        double koszt = 0;
        int ile5 = 0;
        int ile2 = 0;
        int ile1 = 0;
        int ile50gr = 0;
        int ile10gr = 0;
        int ile05gr = 0;

        for (Moneta m : wrzuconeMonety) {
            sumaMonet += m.getWartosc();
        }
        if (wybor == RodzajKawy.KAWA_CZARNA || wybor == RodzajKawy.KAWA_CZARNA_Z_CUKREM) {
            koszt = 1.60;
        }
        if (wybor == RodzajKawy.KAWA_Z_MLEKIEM || wybor == RodzajKawy.KAWA_Z_MLEKIEM_I_CUKREM) {
            koszt = 1.80;
        }

        if (podajKawe(wybor) == wybor) {

            reszta = (sumaMonet*10 - koszt*10)/10;
            if (reszta < 0) {
                System.out.println("Wrzuciłeś za mało pieniędzy");
            } else if (dodajMonety(wrzuconeMonety) && reszta == 0) {
                podajKawe(wybor);

            } else if (dodajMonety(wrzuconeMonety) && reszta > 0) {
                for (Moneta nominal : zamienKolejnosc(Moneta.values())) {
                    int ileMonetWydano = iloscWydanychMonet(reszta, nominal);
                    reszta = reszta - (nominal.getWartosc() * ileMonetWydano);
                    monetyReszty.addAll(Arrays.asList(wydajMonety(nominal, ileMonetWydano)));
                }

            } else {
                return null;
            }
        }
        return monetyReszty.toArray(new Moneta[monetyReszty.size()]);
    }

    public Moneta[] zamienKolejnosc(Moneta[] monety) {
        Moneta[] odwrocone = new Moneta[monety.length];
        for (int i = 0; i < monety.length; i++) {
            odwrocone[i] = monety[monety.length - i - 1];
        }
        return odwrocone;
    }

    public int iloscWydanychMonet(double reszta, Moneta nominal) {
        int maksymalnaIloscMonetDoWydania = (int) (reszta / nominal.getWartosc());
        int iloscMonetWAutomacie = ileMonet(nominal, kasaAutomatu);
        int ileMonetWydac = Math.min(iloscMonetWAutomacie, maksymalnaIloscMonetDoWydania);

        return ileMonetWydac;
    }

    public Moneta[] wydajMonety(Moneta nominal, int ileMonetWydac) {
        if (ileMonetWydac == 0) {
            return new Moneta[]{};
        }
        Moneta[] wydaneMonety = new Moneta[ileMonetWydac];
        for (int i = 0; i < ileMonetWydac; i++) {
            kasaAutomatu[znajdzMonety(nominal, kasaAutomatu)[i]] = null;
        }
        Arrays.fill(wydaneMonety, nominal);
        return wydaneMonety;
    }

    public int[] znajdzMonety(Moneta szukanyNominal, Moneta[] tablica) {
        // Moneta nominal = null;
        int[] tablicaIndeksow = new int[50];
        int licznik = 0;
        for (int i = 0; i < tablica.length; i++) {
            if (tablica[i] == szukanyNominal) {//nominal zamiast tablica[i]
                tablicaIndeksow[licznik] = i;
                licznik++;
            }
        }
        return tablicaIndeksow;
    }

    public int ileMonet(Moneta szukanyNominal, Moneta[] tablica) {
        int ilosc = 0;
        for (int i = 0; i < tablica.length; i++) {
            if (tablica[i] == szukanyNominal) {
                ilosc++;
            }
        }
        return ilosc;
    }

    public boolean dodajMonety(Moneta[] wrzuconeMonety) {
        Moneta[] kopia = new Moneta[kasaAutomatu.length];
        boolean wasSuccessful = true;
        double suma = 0.0;
        for (int i = 0; i < kasaAutomatu.length; i++) {
            kopia[i] = kasaAutomatu[i];
        }

        for (int m = 0; m < wrzuconeMonety.length; m++) {
            Moneta moneta = wrzuconeMonety[m];
            if (ileMonet(moneta, kopia) < 50) {
                for (int k = 0; k < kopia.length; k++) {
                    if (kopia[k] == null) {
                        kopia[k] = moneta;
                        break;
                    }
                }
            } else {
                wasSuccessful = false;
                break;
            }

        }
        if (wasSuccessful) {
            kasaAutomatu = kopia;
        } else {
            System.out.println("Przeładowany automat");
        }
        return wasSuccessful;
    }

   /* public Moneta[] oproznij(Moneta[] kasaAutomatu){
        for(Moneta m: Moneta.values()){

        }
    }
    */

    public void wypiszZawartoscKasyAutomatu() {
        for (int i = 0; i < kasaAutomatu.length; i++) {
            System.out.println(kasaAutomatu[i]);
        }
    }
}





