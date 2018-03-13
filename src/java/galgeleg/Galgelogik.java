package galgeleg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;
import java.rmi.server.UnicastRemoteObject;
import javax.jws.WebService;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;


@WebService(endpointInterface = "galgeleg.GalgeInterface")

public class Galgelogik extends UnicastRemoteObject implements GalgeInterface{
       
    //bruger login
    private Brugeradmin BI;

    // Påkrævet constructor
    public Galgelogik() throws java.rmi.RemoteException, Exception
    {
      hentOrdFraDr();

      try {
          BI = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
      } 
      catch(Exception E){
          E.printStackTrace();
      }
      nulstil();
    }
  /** AHT afprøvning er muligeOrd synlig på pakkeniveau */
  ArrayList<String> muligeOrd = new ArrayList<String>();
  private String ordet;
  private ArrayList<String> OutputClient = new ArrayList<String>();
  private ArrayList<String> brugteBogstaver = new ArrayList<String>();
  private String synligtOrd;
  private int antalForkerteBogstaver;
  private boolean sidsteBogstavVarKorrekt;
  private boolean spilletErVundet;
  private boolean spilletErTabt;


  public ArrayList<String> getBrugteBogstaver() {
    return brugteBogstaver;
  }

  public String getSynligtOrd() {
    return synligtOrd;
  }

  public String getOrdet() {
    return ordet;
  }

  public int getAntalForkerteBogstaver() {
    return antalForkerteBogstaver;
  }

  public boolean erSidsteBogstavKorrekt() {
    return sidsteBogstavVarKorrekt;
  }

  public boolean erSpilletVundet() {
    return spilletErVundet;
  }

  public boolean erSpilletTabt() {
    return spilletErTabt;
  }

  public boolean erSpilletSlut() {
    return spilletErTabt || spilletErVundet;
  }

/*
  public Galgelogik() {
    muligeOrd.add("bil");
    muligeOrd.add("computer");
    muligeOrd.add("programmering");
    muligeOrd.add("motorvej");
    muligeOrd.add("busrute");
    muligeOrd.add("gangsti");
    muligeOrd.add("skovsnegl");
    muligeOrd.add("solsort");
    muligeOrd.add("seksten");
    muligeOrd.add("sytten");
    nulstil();
  }
*/
  public void nulstil() {
    brugteBogstaver.clear();
    antalForkerteBogstaver = 0;
    spilletErVundet = false;
    spilletErTabt = false;
    ordet = muligeOrd.get(new Random().nextInt(muligeOrd.size()-1));
    opdaterSynligtOrd();
  }


  private void opdaterSynligtOrd() {
    synligtOrd = "";
    spilletErVundet = true;
    for (int n = 0; n < ordet.length(); n++) {
      String bogstav = ordet.substring(n, n + 1);
      if (brugteBogstaver.contains(bogstav)) {
        synligtOrd = synligtOrd + bogstav;
      } else {
        synligtOrd = synligtOrd + "*";
        spilletErVundet = false;
      }
    }
  }

  public void gætBogstav(String bogstav) {
      OutputClient.clear();
      
    if (bogstav.length() != 1) return;
    System.out.println("Der gættes på bogstavet: " + bogstav);
    // Levere en meddelse til client
    OutputClient.add("Der gættes på bogstavet: " + bogstav + "\n"); 
    if (brugteBogstaver.contains(bogstav)) return;
    if (spilletErVundet || spilletErTabt) return;

    brugteBogstaver.add(bogstav);

    if (ordet.contains(bogstav)) {
      sidsteBogstavVarKorrekt = true;
      System.out.println("Bogstavet var korrekt: " + bogstav);
      //Meddelse til client
    OutputClient.add("Bogstav korrekt: " + bogstav + "\n"); 
    } else {
      // Vi gættede på et bogstav der ikke var i ordet.
      sidsteBogstavVarKorrekt = false;
      System.out.println("Bogstavet var IKKE korrekt: " + bogstav);
   // meddelse til client
      OutputClient.add("Forkert bogstav: " + bogstav + "\n"); 
      antalForkerteBogstaver = antalForkerteBogstaver + 1;
      if (antalForkerteBogstaver > 6) {
        spilletErTabt = true;
      }
    }
    opdaterSynligtOrd();
  }

  public void logStatus() {
    System.out.println("---------- ");
    System.out.println("- ordet (skult) = " + ordet);
    System.out.println("- synligtOrd = " + synligtOrd);
    System.out.println("- forkerteBogstaver = " + antalForkerteBogstaver);
    System.out.println("- brugeBogstaver = " + brugteBogstaver);
    if (spilletErTabt) System.out.println("- SPILLET ER TABT");
    if (spilletErVundet) System.out.println("- SPILLET ER VUNDET");
    System.out.println("---------- ");
  }


  public static String hentUrl(String url) throws IOException {
    System.out.println("Henter data fra " + url);
    BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
    StringBuilder sb = new StringBuilder();
    String linje = br.readLine();
    while (linje != null) {
      sb.append(linje + "\n");
      linje = br.readLine();
    }
    return sb.toString();
  }

  public void hentOrdFraDr() throws Exception {
      //https://www.dr.dk/mu-online/Help/1.3/Api/GET-api-1.3-page-tv-front
    Client client = ClientBuilder.newClient();
    Response res = client.target("https://www.dr.dk/mu-online/api/1.3/page/tv/live/dr1")
            .request(MediaType.APPLICATION_JSON).get();
    String svar = res.readEntity(String.class);
    //System.out.println(svar);
    try {

//Object ole = res.getEntity();
    //Parse svar som et JSON-objekt
    JSONObject json = new JSONObject(svar);
    //System.out.println("Mulige ord: " json.getJSONObject("Type").getString("Slug"));
    String data = "";
    //data += json.getJSONArray("Live").getJSONObject(0).getJSONObject("Now").getString("Description");
    data += json.getJSONObject("NowNext").getJSONObject("Now").getString("Description");
    System.out.println("data = " + data);
    muligeOrd.clear();
    muligeOrd.addAll(new HashSet<String>(Arrays.asList(data.split(" "))));
    
    } catch (Exception e) {
    e.printStackTrace();
    }
      /*    String data = hentUrl("https://dr.dk");
    //System.out.println("data = " + data);

    data = data.substring(data.indexOf("<body")). // fjern headere
            replaceAll("<.+?>", " ").toLowerCase(). // fjern tags
            replaceAll("&#198;", "æ"). // erstat HTML-tegn
            replaceAll("&#230;", "æ"). // erstat HTML-tegn
            replaceAll("&#216;", "ø"). // erstat HTML-tegn
            replaceAll("&#248;", "ø"). // erstat HTML-tegn
            replaceAll("&oslash;", "ø"). // erstat HTML-tegn
            replaceAll("&#229;", "å"). // erstat HTML-tegn
            replaceAll("[^a-zæøå]", " "). // fjern tegn der ikke er bogstaver
            replaceAll(" [a-zæøå] "," "). // fjern 1-bogstavsord
            replaceAll(" [a-zæøå][a-zæøå] "," "); // fjern 2-bogstavsord

    System.out.println("data = " + data);
    System.out.println("data = " + Arrays.asL    ist(data.split("\\s+")));
    muligeOrd.clear();
    muligeOrd.addAll(new HashSet<String>(Arrays.asList(data.split(" "))));

    System.out.println("muligeOrd = " + muligeOrd);
    nulstil();
  }*/
  }
 
    public String outputTilKlient()
  {
      return OutputClient.toString();
  }
    class Type {
        
    }
  
  //@Override
    public boolean hentBruger(String brugernavn, String password) {
        
        try {
            Bruger b = BI.hentBruger(brugernavn, password);
            System.out.println("GalgelegImpl.java : Objekt modtaget");
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("GalgelegImpl.java : IllegalArgumentException");
            e.printStackTrace();
            return false;
        } catch (Exception p) {
            System.out.println("GalgelegImpl.java : Exception");
            p.printStackTrace();
        }
        
        return false;
    }
}
