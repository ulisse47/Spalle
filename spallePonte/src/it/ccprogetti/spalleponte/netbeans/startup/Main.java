/*
 * Main.java
 *
 * Created on 30 dicembre 2007, 17.12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package it.ccprogetti.spalleponte.netbeans.startup;

/**
 *
 * @author andrea.cavalieri
 */
public class Main {
    
  public static void main (String[] argv) throws Exception {
    System.out.println( argv );
    for ( int i = 0; i < argv.length; i++ ){
        
        System.out.println( argv[i] );
    
    }
      /*TopThreadGroup tg = new TopThreadGroup ("IDE Main", argv); // NOI18N - programatic name
    StartLog.logStart ("Forwarding to topThreadGroup"); // NOI18N
    tg.start ();
    StartLog.logProgress ("Main.main finished"); // NOI18N*/
  }
    
}
