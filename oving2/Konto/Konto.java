package Konto;

//
// Leksjon 2, katalog Konto
// Konto.java E.L. 2003-09-03
//
interface Konto {
    long finnKontonr();

    String finnNavn();

    double finnSaldo();

    void gjørTransaksjon(double beløp);
}

