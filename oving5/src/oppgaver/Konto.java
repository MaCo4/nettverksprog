/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oppgaver;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Magnus
 */

@Entity
public class Konto {
    @Id
    private int kontonr;
    private double saldo;
    private String eier;
    
    public Konto() {
        
    }

    public int getKontonr() {
        return kontonr;
    }

    public void setKontonr(int kontonr) {
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
        this.saldo -= belop;
    }

    @Override
    public String toString() {
        return "Konto[kontonr=" + kontonr + ", eier=" + eier + ", saldo=" + saldo + "]";
    }
}

