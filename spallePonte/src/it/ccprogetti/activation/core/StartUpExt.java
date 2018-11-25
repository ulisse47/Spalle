/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ccprogetti.activation.core;

import it.ccprogetti.spalleponte.netbeans.installer.dlgAttivaSoftware;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.UUID;
import javax.swing.DefaultListModel;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;


/*
 * @author andrea.cavalieri
 */
public class StartUpExt {

    //private static int code;
    private String program;
    public static final int NOT_REGIGESTERED = 0;
    public static final int REGIGESTERED = 1;
    public static final int DEMO = 2;
    public static final int BLOCCATO = 3;
    public static final int LOCAL_REGIGESTERED = 4;
    public static final int NOT_VALID_CODE = 5;
    public static final int HTTP_ERROR = 6;
    public static final int HTTP_CREATED_NEW = 200;

    private int mode;
    private static StartUpExt startUp;

    private static String URL_BASE = "http://www.ccprogetti.it/ponti/index.php/api/spalle/activations/";
    private static String URL_GET_CODE = URL_BASE.concat("id/");
    private static String URL_POST_CODE = URL_BASE;
    private static String URL_DELETE_CODE = URL_BASE;
    private static String X_API_KEY_GET = "40g48sws8ckwwk0480gwcwog440wkogg880gc004";
    private static String X_API_KEY_ACTIVATE = "40g48sws8ckwwk0480gwcwog440wkogg880gc004";
    private static String X_API_KEY_DE_ACTIVATE = "40g48sws8ckwwk0480gwcwog440wkogg880gc004";
    private static String Maya_Path ="/AppData/Roaming/.spalle/dev/Maya.data";

    private String activationCode;
    //   private String user = "user1";

    public String getUser() {
        InetAddress ip;
        try {

            ip = InetAddress.getLocalHost();
            //	System.out.println("Current IP address : " + ip.getHostAddress());

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            byte[] mac = network.getHardwareAddress();

//		System.out.print("Current MAC address : ");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
//		System.out.println(sb.toString());
            return sb.toString();

        } catch (UnknownHostException e) {

            e.printStackTrace();

        } catch (SocketException e) {

            e.printStackTrace();

        }

        return "user 1";

    }

//    public void setUser(String user) {
//        this.user = user;
//    }
    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode.trim();
    }

    private StartUpExt() {

        // user = "cava";
        //activationCode = UUID.randomUUID().toString();
    }

    public static synchronized StartUpExt getInstance() {
        if (startUp == null) {
            startUp = new StartUpExt();
        }

        return startUp;
    }

    public void togliChiaveSW() throws Exception {

        doDeActivateRemotely(readActivationCodeFromMaya(), getUser());
        deActivateLocally();
        SpalleBusinessDelegateImpl.getInstance().setMode(StartUpExt.DEMO);

    }

    public void CCProgettiWEB() throws Exception {

    }

    public void updateWeb() throws Exception {

    }

    public void about() throws Exception {
        
    }

    //legge valore locale della variabile installazione
    public int verifyLocalActivation() throws IOException {
        String regis = "";
        int ch;
        File fMaya = null;

//        File f =  Location.class.getProtectionDomain().getCodeSource().getLocation();
        String dir = System.getProperty("user.home");
        fMaya = new File(dir + Maya_Path);

        if (!fMaya.exists()) {
            fMaya = new File(dir + Maya_Path);
            fMaya.getParentFile().mkdir();
            try {
                fMaya.createNewFile();
            } catch (Exception e) {

            }
        }

        FileInputStream fin = null;  // file to read
        // open file to read
        try {
            fin = new FileInputStream(fMaya);
            // read the listModel, or, the entire list
            int i = 0;
            while ((ch = fin.read()) != -1) {
                if (i >= 10 && i <= 12) {
                    regis += ((char) ch);
                }
                i++;
            }

        } catch (IOException ioe) {
            System.out.println("load Maia.data not found.");
            System.exit(0);
        }

        try {
            fin.close();
        } catch (IOException ioe2) {
            System.out.println("IOexception fin.close");
            System.exit(0);
        }
        System.out.println("Maia.data read");

        if (regis.equals("MSY")) {
            return StartUpExt.LOCAL_REGIGESTERED;
        } else {
            return StartUpExt.NOT_REGIGESTERED;
        }

    }

    public String readActivationCodeFromMaya() {
        String regis = "";
        int ch;
        File fMaya = null;

//        File f =  Location.class.getProtectionDomain().getCodeSource().getLocation();
        String dir = System.getProperty("user.home");
        fMaya = new File(dir + Maya_Path);

        if (!fMaya.exists()) {
            return null;
        }

        FileInputStream fin = null;  // file to read
        // open file to read
        try {
            fin = new FileInputStream(fMaya);
            // read the listModel, or, the entire list
            int i = 0;
            while ((ch = fin.read()) != -1) {
                if (i >= 23) {
                    regis += ((char) ch);
                }
                i++;
            }

        } catch (IOException ioe) {
            System.out.println("load Maia.data not found.");
            return null;
        }

        try {
            fin.close();
        } catch (IOException ioe2) {
            System.out.println("IOexception fin.close");
            return null;
        }
        System.out.println("Maia.data read");

        return regis;
    }

    private void doDeActivateRemotely(String activationCode, String user) throws MalformedURLException, IOException {
        
        String urlParameters = "?id=" + activationCode;
        URL url = new URL(URL_DELETE_CODE + urlParameters);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("DELETE");
        //conn.setDoOutput(true);
        conn.addRequestProperty("X-API-KEY", X_API_KEY_DE_ACTIVATE);
        conn.setRequestProperty("Accept", "application/json");
        conn.addRequestProperty("X-API-USER", user);

        int responseCode = conn.getResponseCode();

        System.out.println("\nSending 'DELETE' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        switch (responseCode) {
            //OK, NO_CONTENT (204)
            case 204:
                return;
            default:
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
        }

    }

    private int doActivateRemotely(String activationCode, String user) throws MalformedURLException, IOException {

        try {
            URL url = new URL(URL_POST_CODE);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.addRequestProperty("X-API-KEY", X_API_KEY_ACTIVATE);
            conn.setRequestProperty("Accept", "application/json");

            String urlParameters = "id=" + activationCode + "&user=" + user;

            // Send post request
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = conn.getResponseCode();

            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
            
            //int xop = HttpURLConnection.HTTP_CREATED - 1;
            if (responseCode != HTTP_CREATED_NEW ){
                return HTTP_ERROR;
                //throw new RuntimeException("Failed : HTTP error code : "
                //      + conn.getResponseCode());
            }
        } catch (Exception ex) {
            return HTTP_ERROR;
        }
        return REGIGESTERED;

    }

    private int doVerifyValidCode(String activationCode) throws MalformedURLException, IOException {

        URL url = new URL(URL_GET_CODE.concat(activationCode));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.addRequestProperty("X-API-KEY", X_API_KEY_GET);
        conn.setRequestProperty("Accept", "application/json");

        int responseCode = conn.getResponseCode();

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        switch (responseCode) {
            //FOUND (200)
            case 302:
                return NOT_REGIGESTERED;
            case 404:
                return NOT_VALID_CODE;
            case 401:
                return REGIGESTERED;

//                throw new RuntimeException("Failed : codice attivazione non disponibile");
            //return CODICE NON DISPONIBILE;
            default:
                return HTTP_ERROR;
//                throw new RuntimeException("Failed : HTTP error code : "
//                        + conn.getResponseCode());
        }
    }

    //attiva localmente il software
    public void deActivateLocally() throws Exception {

        File fMaya = null;

        String dir = System.getProperty("user.home");
        fMaya = new File(dir + Maya_Path);
        
        try {
            if(fMaya.exists()) fMaya.delete();
            
        } catch (Exception e) {
    
        }
        
    }

    //attiva localmente il software
    public void activateLocally() throws Exception {
        File fMaya = null;

        String dir = System.getProperty("user.home");
        fMaya = new File(dir + Maya_Path);
        if (!fMaya.exists()) {
            fMaya = new File(dir + Maya_Path);
            fMaya.getParentFile().mkdir();
            try {
                fMaya.createNewFile();
            } catch (Exception e) {

            }
        }

        FileOutputStream fin = null;  // file to read
        // open file to read
        try {
            fin = new FileOutputStream(fMaya);
            PrintStream scrivi = new PrintStream(fin);
            String code = "";
            Random r = new Random();
            for (int i = 0; i < 20; i++) {
                char c = (char) (r.nextInt(26) + 'a');
                if (i == 10) {
                    code += "MSY";
                }
                code += c;
                if (i == 19) {
                    code += activationCode;
                }
            }
            scrivi.print(code);

        } catch (IOException ioe) {
            System.out.println("load Maia.data not found.");
            System.exit(0);
        }
        try {
            fin.close();
        } catch (IOException ioe2) {
            System.out.println("IOexception fin.close");
            System.exit(0);
        }
        System.out.println("Maia.data read");

        System.out.println(
                "it.ccprogetti.activation.core.StartUpExt.activateLocally() - attivato localmente");
    }

    public int verifyValidCode(String code) throws IOException {
        setActivationCode(code);
        return doVerifyValidCode(getActivationCode());
    }

    public int activateRemotely() throws IOException {
        return doActivateRemotely(getActivationCode(), getUser());
        //System.out.println("it.ccprogetti.activation.core.StartUpExt.activateRemotey() - attivazione remota");
    }

    public int getLocalActivated() throws Exception {
        //VERIFICA LOCALE
        mode = verifyLocalActivation();//VERIFICO REGISTRAZIONE LOCALE
        return mode;
    }

    /*    public int activate() throws Exception {

        mode = verifyValidCode();//VERIFICA REGISTRAZIONE REMOTA
        if (mode ==) //CASO NON REGITRATO
        {
            if (mode == NOT_REGIGESTERED) {
                //REGISTRAZIONE REMOTA
                activateRemotely();//ATTIVO IN REMOTO

                //REGISTRO LOCALMENTE
                activateLocally();//ATTIVO ANCHE LOCALMENTE
                mode = REGIGESTERED;
                return mode;

                //CASO NON REGITRATO, BLOCCATO, DEMO
            } else {
                //NON ATTIVO LOCALMENTE
                return mode;
            }
        }

    

default:
                return mode;
        }
     */
    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

}
