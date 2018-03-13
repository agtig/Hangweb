/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galgeleg;

import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
/**
 *
 * @author mikkel
 */
public class GalgeKlientTilServer {
 public static void main(String[] args) throws Exception 
    {
        // GalgeInterface spil = (GalgeInterface) Naming.lookup("rmi://ubuntu4.saluton.dk:9919/GalgeServer?wsdl"); // rmi
        //Galgelogik spil = new Galgelogik(); // ændr såledegs at logik udføres fra server
        
        //URL url = new URL("http://ubuntu4.saluton.dk:9919/GalgeServer?wsdl"); // soap
        //QName qname = new QName("http://galgeleg/", "KontoImplService");
        
        URL url = new URL("http://ubuntu4.saluton.dk:9919/GalgeServer?wsdl"); // soap - Forbinder til det navn serveren udgiver sig på "GalgeServer"
        QName qname = new QName("http://galgeleg/", "GalgelogikService");
        
        Service service = Service.create(url, qname);
        GalgeInterface spil = service.getPort(GalgeInterface.class);
        
        
        boolean truee = true;
        String guess;
        Scanner input = new Scanner(System.in);

        
        // login
        while (true) {
          System.out.println("Brugernavn: ");
          String brugernavn = input.nextLine();

          System.out.println("Password: ");
          String kodeord = input.nextLine();

          if (spil.hentBruger(brugernavn, kodeord))
              break;
          else
              System.out.println("DET ER FORKERT! :-D");

        }
        System.out.println("Tillykke!");
        System.out.println("Dit adgangskode var rigtigt.");
        System.out.println("Spillet starter nu. Du må max gætte forkert 7 gange");
        
        while (truee)
        {
            System.out.println("Indtast ét bogstav");
            guess = input.next();
            spil.gætBogstav(guess);   
            System.out.println(spil.outputTilKlient());
            spil.logStatus();       
            System.out.println(spil.outputTilKlient());    
            if (spil.erSpilletSlut()) truee = false;
        }
      
        //System.out.println("Spillet afsluttes/Forbindelse til server lukkes");
       
        
        
        
    }   
}
