import java.rmi.Naming;

import static javax.swing.JOptionPane.showInputDialog;

public class Klient {
    public static void main(String[] args) throws Exception {

        Register register = (Register) Naming.lookup("rmi://localhost/Register");

        System.out.println("Register: " + register.lagDatabeskrivelse());

        String inn = showInputDialog("Skriv inn varenummer");
        while (inn != null && !inn.equals("")) {
            int varenr = Integer.parseInt(inn);
            int mengde = Integer.parseInt(showInputDialog("Skriv inn endring i beholdning"));
            register.endreLagerbeholdning(varenr, mengde);
            System.out.println("Register: " + register.lagDatabeskrivelse());

            inn = showInputDialog("Skriv inn varrenummer");
        }

        System.out.println(register.lagBestillingsliste());
    }
}
