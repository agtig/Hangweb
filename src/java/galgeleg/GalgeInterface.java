/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galgeleg;
import javax.jws.WebService;
import javax.jws.WebMethod;
import java.rmi.RemoteException;
import java.util.ArrayList;
/**
 *
 * @author mikkel
 */
//Definere en grænseflade imellem klienten og servern
//Fortæller hvilke metoder fra galgelogikken der kan benyttes
@WebService
public interface GalgeInterface {
     @WebMethod void nulstil() throws java.rmi.RemoteException;
     @WebMethod String getOrdet() throws java.rmi.RemoteException;
     @WebMethod String getSynligtOrd();
     @WebMethod ArrayList<String> getBrugteBogstaver(); 
     @WebMethod int getAntalForkerteBogstaver(); 
     @WebMethod boolean erSidsteBogstavKorrekt();
     @WebMethod boolean erSpilletVundet();
     @WebMethod boolean erSpilletTabt();
     @WebMethod boolean erSpilletSlut();
     //@WebMethod void galgelogik() throws java.rmi.RemoteException;
     @WebMethod void logStatus() throws java.rmi.RemoteException;
     //@WebMethod void opdaterSynligtOrd() throws java.rmi.RemoteException;
     @WebMethod boolean hentBruger(String brugernavn, String password) throws java.rmi.RemoteException;  
     @WebMethod void gætBogstav(String bogstav)  throws java.rmi.RemoteException;
     @WebMethod String outputTilKlient()    throws java.rmi.RemoteException;
    
}
