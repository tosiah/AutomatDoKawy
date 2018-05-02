/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uksw.wmp.prja.laboratorium2;

/**
 *
 * @author pkacz_000
 */

public enum Moneta {
    
    GR5(0.05), GR10(0.1), GR50(0.5), ZL1(1), ZL2(2), ZL5(5);
    
    Moneta(double wartosc) {
        this.wartosc = wartosc;
    }
    
    public double getWartosc() {
        return wartosc;
    }

    private double wartosc;
            
}
