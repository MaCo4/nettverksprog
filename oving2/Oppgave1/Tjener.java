import java.rmi.Naming;

public class Tjener {
    public static void main(String[] args) throws Exception {
        Register register = new RegisterImpl();
        register.regNyttUtstyr(1, "Kjøkkenmaskin", "Kenwood", 5, 3);
        register.regNyttUtstyr(2, "Stavmikser", "Kenwood", 5, 3);
        register.regNyttUtstyr(3, "Juicemaskin", "Kenwood", 5, 3);
        register.regNyttUtstyr(4, "Riskoker", "Philips", 5, 3);
        register.regNyttUtstyr(5, "Eggkoker", "Philips", 5, 3);

        Naming.rebind("Register", register);
        System.out.println("Register er registrert i rmi-registeret");
        //javax.swing.JOptionPane.showMessageDialog(null, "Trykk OK for å stoppe tjeneren.");
        //Naming.unbind("Register");
    }
}
