/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package progetto.model.bean;

import javax.swing.JOptionPane;
import progetto.model.util.Calcoli;

/**
 *
 * @author Administrator
 */
public class Berezantzev {

    public double[][] getRow() {
        return row;
    }

    public void setRow(double[][] row) {
        this.row = row;
    }

    //[fi] L/D=5	L/D=10	L/D=20	L/D=50
    double[][] row = {
        {22, 8.3176377110267, 6.3095734448019, 5.2480746024977, 2.9723104638994},
        {23, 10, 7.5857757502918, 6.3095734448019, 3.6540986600956},
        {24, 12.022644346174, 9.1201083935591, 7.5857757502918, 4.4922753460266},
        {25, 14.454397707459, 10.964781961432, 9.1201083935591, 5.5227128935787},
        {26, 17.378008287494, 13.182567385564, 10.964781961432, 6.7895120747391},
        {27, 20.89296130854, 15.848931924611, 13.182567385564, 8.3468894909649},
        {28, 25.118864315096, 19.054607179632, 15.848931924611, 10.26149794086},
        {29, 30.19951720402, 22.908676527678, 19.054607179632, 12.615279033496},
        {30, 36.30780547701, 27.542287033382, 22.908676527678, 15.50897013381},
        {31, 43.651583224017, 33.113112148259, 27.542287033382, 19.066415730701},
        {32, 52.480746024977, 39.81071705535, 33.113112148259, 23.439867746177},
        {33, 63.095734448019, 47.863009232264, 39.81071705535, 28.816501628755},
        {34, 75.857757502918, 57.543993733716, 47.863009232264, 35.426427107527},
        {35, 91.201083935591, 69.183097091894, 57.543993733716, 43.552536451983},
        {36, 109.64781961432, 83.176377110267, 69.183097091894, 53.542611724406},
        {37, 131.82567385564, 100, 83.176377110267, 65.824209192297},
        {38, 158.48931924611, 120.22644346174, 100, 80.922957925421},
        {39, 190.54607179632, 144.54397707459, 120.22644346174, 99.485055722718},
        {40, 229.08676527678, 173.78008287494, 144.54397707459, 122.30492515207},
        {41, 275.42287033382, 208.9296130854, 173.78008287494, 150.35921332893},
        {42, 331.13112148259, 251.18864315096, 208.9296130854, 184.8485905599},

          
           };


public double getNq (double fi, double L_D){
    
    if (fi<22 || fi > 42) {
            JOptionPane.showMessageDialog(null, "θ deve essere compreso tra 22° e 42°",
                    "Calcolo Nq - metodo Berezantzev", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    
   if (L_D<5 || L_D > 50) {
            JOptionPane.showMessageDialog(null, "L/D deve essere compreso tra 5 e 50",
                    "Calcolo Nq - metodo Berezantzev", JOptionPane.ERROR_MESSAGE);
            return 0;
        }

        double Finf = 0;
        double Fsup = 0;
        int iterInf=0;
        int iterSup=0;
        double  NqSup1=0;
        double  NqSup2=0;
        double  NqInf1=0;
        double  NqInf2=0;
        double  L_Dinf=0;
        double  L_Dsup=0;
        
        
        //trova range di fi
        for (int i = 0; i < 20; ++i) {
            if (fi >= row[i][0] & fi <= row[i + 1][0]) {
                Finf = row[i][0];
                Fsup = row[i + 1][0];
                iterSup = i+1;
                iterInf = i;
                continue;
            }
        }

        if (L_D >= 5.0 & L_D < 10.0) {
            NqSup1 = row[iterSup][1];
            NqSup2 = row[iterSup][2];
            NqInf1 = row[iterInf][1];
            NqInf2 = row[iterInf][2];
            L_Dinf = 5.0;
            L_Dsup = 10.0;
        }
        
       if (L_D >= 10.0 & L_D < 20.0) {
            NqSup1 = row[iterSup][2];
            NqSup2 = row[iterSup][3];
            NqInf1 = row[iterInf][2];
            NqInf2 = row[iterInf][3];
            L_Dinf = 10.0;
            L_Dsup = 20.0;
        }
      if (L_D >= 20.0 & L_D <= 50.0) {
            NqSup1 = row[iterSup][3];
            NqSup2 = row[iterSup][4];
            NqInf1 = row[iterInf][3];
            NqInf2 = row[iterInf][4];
            L_Dinf = 20.0;
            L_Dsup = 50.0;
        }

        //interpola Nq per fi1
        Calcoli c = new Calcoli();
        double Nqmed1 = c.getInterpolazion(L_Dinf,L_Dsup, NqSup1, NqSup2, L_D);
        double Nqmed2 = c.getInterpolazion(L_Dinf,L_Dsup, NqInf1, NqInf2, L_D);
        double Nqmed3= c.getInterpolazion(Finf,Fsup, Nqmed2, Nqmed1, fi);
        
        return Nqmed3;
    }
}
