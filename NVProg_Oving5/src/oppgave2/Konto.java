/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oppgave2;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Magnus
 */

@Entity
public class Konto {
    @Id
    private long kontonr;
    private double saldo;
    private String eier;
    
    public Konto() {
        
    }

    public long getKontonr() {
        return kontonr;
    }

    public void setKontonr(long kontonr) {
        this.kontonr = kontonr;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getEier() {
        return eier;
    }

    public void setEier(String eier) {
        this.eier = eier;
    }

    public void trekk(double belop) {

    }
}

