/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package progetto.model.bean;

import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class FondazioneAllaWinkler {

    public double[][] getFORZM() {
        return FORZM;
    }

    public double[][][] getMEXT() {
        return MEXT;
    }

    public double[][] getPRESS() {
        return PRESS;
    }

    public double[][][] getROTAZ() {
        return ROTAZ;
    }

    //DATI DI INPUT
    int NP; //numero variabili modali (rotazione e spostamento per ogni nodo)
    int NM; // numero di elmenti
    int NNZP, NCYC, NRC;
    int ITYPE = 0;
    int JTSOIL = 0;
    boolean NONLIN; //1 per analisi non lineare (se non lin porre NLC=1)
    double E; //modulo elastico kpa
    double UNITWT; //peso specifico palo kN/m3
    double XMAX; //massimo cedimeno lineare del terreno (m)
    double REDFAC; //fattore di riduzione costante molla a quota terreno
    //usato come REDFAC*SSK(I,1)
    double PWIDT; //diametro del palo (m)
    //calori per calcolo coefficiente sottofondo
    double AS, BS, EXPO; //ks = AS + BS*Z*EXPO

    //carichi
//    int K; //numero variabile nodale (notare ciclo si NLC
    double[][] P; //termine della matrice P; K si riferisce alle NP var. modali, 
    // l'incice L alle NLC condizioni di carico;
    int M; //numero del nodo in cui si assegna una molla
    double SPRNG[]; // costante dekke molla (kN/m)
    //solo se NZX (rotazioni o spostamenti noti) >0
    int NZX; //numero di spostamenti o rotazioni note
    int[] NXZERO; //numero spostamento nodale NP assegnato
    double[] XSPEC; //spostamento assegnato a al nodo NXZERO[]
    //variabili fisse
    double[] STIFF;
    double[] STIFS;
    int IINPUT = 0; //CALCOLA IN AUTOMATICO MOM. INERZ
    int LISTB = 0; //STAMPA LA MATRICE 0=NO
    int IMET = 1; //sistema di unità intern (SI) = 1
    int NLC; //numero condizioni di carico
    double LPalo;
    //VARIABILI DI INTERNE
    //   int nMaxElementi = 200;
    int[][] NPE;
    double[] H; //lunghezza elementi
    double[] B; //larghezza elemeti
    double[] T; //altezza elementi
    double[] XINER; //inerzia elementi
    double[] SUMP; //calcola la somma delle forze applicate per ogni condizione
    double[][] FtestaPalo;
    double[][] PSAVE; // vettore forze esterne intermedio analisi non lin
    double[] SK;
    double[][] SSK;
    int ISIZE;
    double XI;
    double[][] ESATS;
    public double[][][] MEXT; //momenti alle estremità delle travi
    public double[][] FORZM; //reazioni molle
    public double[][][] ROTAZ; //rotazioni nodi
    public double[][] PRESS; //pressioni terreno

    /*
     * FtestaPalo[0][numero condizione di carico] Momento flettente in testa
     * FtestaPalo[1][numero condizione di carico] Forza orizzontale in testa palo
     * 
     */
    public FondazioneAllaWinkler(int nElementi, double[][] FtestaPalo,
            double LPalo, double DPalo, boolean rotazioneInTesta) {

        NCYC = 20;
        NM = nElementi;
        NP = (nElementi + 1) * 2;
        ISIZE = NP * 4;
        NPE = new int[NM][4];
        H = new double[NM]; //lunghezza elementi
        B = new double[NM]; //larghezza elemeti
        T = new double[NM]; //altezza elementi
        XINER = new double[NM]; //inerzia elementi

        STIFF = new double[ISIZE];
        STIFS = new double[ISIZE];

        P = new double[NP][1];

        this.FtestaPalo = FtestaPalo;
        this.NLC = FtestaPalo[0].length;
        SUMP = new double[NLC];

        PSAVE = new double[NP][NLC];
        SPRNG = new double[NM + 1];
        SK = new double[NM + 1];
        SSK = new double[NM + 1][2];
        ESATS = new double[NM][8];

        this.LPalo = LPalo;
        PWIDT = DPalo;

        //spostamento noto solo rotazione in testa
        if (!rotazioneInTesta) {
            NZX = 1;
            NXZERO = new int[NZX];
            XSPEC = new double[NZX];
            NXZERO[0] = 1;
            XSPEC[0] = 0;

        } else {
            NZX = 0;
            NXZERO = new int[NZX];
            XSPEC = new double[NZX];

        }


        MEXT = new double[NM][2][NLC]; //momenti alle estremità nodi
        FORZM = new double[NM + 1][NLC]; //forze molle
        ROTAZ = new double[NM + 1][2][NLC]; //rotazioni nodi
        PRESS = new double[NM + 1][NLC]; //pressioni terreno

        AS = 5000;
        BS = 0.0;
        EXPO = 1;

        REDFAC = 1;

        E = 30000000;

        elabora();


    }

    /*    public void elabora(int Np, int NM,int NNZP,int NLC,int NCYC,
    int NRC,int NONLIN,int NZX, double E, double UNITWT,
    double XMAX,double REDFAC, double PWIDT, double As, double BS,
    double EXPO){
     */
    public void elabora() {

//        int IHOLD = JTSOIL;
        //      if (IHOLD <= 0) {
        //        JTSOIL = 0;
        //  }

        int LCOUN = 0;
        int ICOUN = 1;

        input();
        load();
        spring();
        bstif();

        //memorizza la matrice iniziale se nonlin>0
        //caso non lineare
        if (NONLIN) {
            for (int i = 0; i < ISIZE; ++i) {
                STIFS[i] = STIFF[i];
            }
        }

        //il loop è per il caso non lineare
        do {
            modif();
            solv1(4);
            //caso non lineare
            if (NONLIN) {
                check(ICOUN, LCOUN);
                if (ICOUN > 0) {
                    if (ICOUN <= NCYC) {
                        //ricalcola stifff per il ciclo successivo
                        for (int i = 0; i < ISIZE; ++i) {
                            STIFF[i] = STIFS[i];
                        }
                    }
                }
            } else {
                ICOUN = 0;
            }
        } while (ICOUN > 0 && ICOUN <= NCYC);


        if (ICOUN > NCYC) {
            JOptionPane.showMessageDialog(null, "Superato numeo di iterazioni massimo", "warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        force();

    }

    private void bandm(double[][] EASAT, int i) {

        for (int k = 0; k < 4; ++k) {
            int NS1 = (NPE[i][k] - 1) * 4;
            for (int j = 0; j < 4; ++j) {
                if (NPE[i][j] >= NPE[i][k]) {
                    int NS2 = NPE[i][j] - NPE[i][k] + 1;
                    STIFF[NS1 + NS2 - 1] += EASAT[k][j];
                }
            }
        }


    }

    private void bstif() {
        double[][] EA = new double[4][2];
        double[][] ES = new double[2][2];
        double[][] ESAT = new double[2][4];
        double[][] EASAT = new double[4][4];

        //azzera STIFF(i) ad ogni ciclo
        for (int i = 0; i < ISIZE; ++i) {
            STIFF[i] = 0;
        }

        //calcola le matrici degli elementi e quella globale
        for (int ii = 0; ii < NM; ++ii) {
            //EA
            EA[0][0] = 1.0;
            EA[0][1] = 0.0;

            EA[1][0] = 1.0 / H[ii];
            EA[1][1] = 1.0 / H[ii];

            EA[2][0] = 0.0;
            EA[2][1] = 1.0;

            EA[3][0] = -1.0 / H[ii];
            EA[3][1] = -1.0 / H[ii];

            //ES
            ES[0][0] = 4.0 * E * XI / H[ii];
            ES[0][1] = 0.5 * ES[0][0];

            ES[1][0] = ES[0][1];
            ES[1][1] = ES[0][0];

            //matrice SAT
            for (int I = 0; I < 2; ++I) {
                for (int J = 0; J < 4; ++J) {
                    ESAT[I][J] = 0;
                    for (int k = 0; k < 2; ++k) {
                        ESAT[I][J] += ES[I][k] * EA[J][k];
                    }
                }
            }

            //memorizza ESAT
            int L = -1;
            for (int I = 0; I < 2; ++I) {
                for (int J = 0; J < 4; ++J) {
                    L = L + 1;
                    ESATS[ii][L] = ESAT[I][J];
                }
            }

            //calcola la matrice ASAT
            for (int k = 0; k < 4; ++k) {
                for (int J = 0; J < 4; ++J) {
                    EASAT[k][J] = 0;
                    for (int l = 0; l < 2; ++l) {
                        EASAT[k][J] += EA[k][l] * ESAT[l][J];
                    }
                }
            }
            bandm(EASAT, ii);

        }
    }

    private void check(int ICOUN, int LCOUN) {
        int IHOLD = 0;
        double FORC;
        if (ICOUN == 1) {
            IHOLD = LCOUN;
        }
        int K = JTSOIL;
        int NMP1 = NM + 1;
        double Z2 = 2.0 * XMAX;

        for (int I = K; I <= NMP1; ++I) {
            double CON = -1.0;
            //controllo pali caricati trasversalmente
            double Z1 = P[2 * I - 1][1 - 1] + XMAX;
            if (Z1 <= 0) {
                CON = 1.0;
                FORC = CON * XMAX * SPRNG[I - 1];
                PSAVE[2 * I - 1][1 - 1] += FORC;
                SUMP[0] += FORC;
                SPRNG[I - 1] = 0.0;
                LCOUN++;
            } else if (Z1 > Z2) {
                FORC = CON * XMAX * SPRNG[I - 1];
                PSAVE[2 * I - 1][1 - 1] += FORC;
                SUMP[0] += FORC;
                SPRNG[I - 1] = 0.0;
                LCOUN++;
            }
        }//chiuso ciclo I

        //se LCOUN > IHOLD non si ha convergenza
        if (LCOUN > IHOLD) {
            IHOLD = ICOUN;
            ICOUN = ICOUN + 1;
            LCOUN = 0;
            //ricalcola la matrice P in base alla matrice memorizzata

            for (int I = 1; I <= NP; ++I) {
                P[I - 1][1 - 2] = PSAVE[I - 1][1 - 1];
            }
        } else {
            ICOUN = 0;
        }


    }

    private void force() {
        double CON = 1000;
        double L1, LL, X1, X2, FORC1, SOILP1;
        int NS1;
        double[] F = new double[2];

        //per ogni caso di carico
        for (int LC = 1; LC <= NLC; ++LC) {
            int INODE = 0;
            double SUM = 0.0;
            for (int JJ = 1; JJ <= NM; ++JJ) {
                INODE++;
                LL = 2 * INODE;
                L1 = LL - 1;
                int L = 0;
                for (int K = 1; K <= 2; ++K) {
                    F[K - 1] = 0.0;
                    for (int J = 1; J <= 4; ++J) {
                        NS1 = NPE[JJ - 1][J - 1];
                        L++;
                        F[K - 1] += ESATS[JJ - 1][L - 1] * P[NS1 - 1][LC - 1];
                    }
                }
                //determina gli spostamenti
                X1 = P[NPE[JJ - 1][0] - 1][LC - 1];
                X2 = P[NPE[JJ - 1][1] - 1][LC - 1];
                FORC1 = X2 * SPRNG[INODE - 1];
                SUM += FORC1;
                SOILP1 = X2 * SK[INODE - 1];
                MEXT[JJ - 1][0][LC - 1] = F[0];
                MEXT[JJ - 1][1][LC - 1] = F[1];
                FORZM[JJ - 1][LC - 1] = FORC1;
                ROTAZ[JJ - 1][0][LC - 1] = X1;
                ROTAZ[JJ - 1][1][LC - 1] = X2;
                PRESS[JJ - 1][LC - 1] = SOILP1;

            }
            INODE++;
            LL = 2 * INODE;
            L1 = LL - 1;
            X1 = P[NPE[NM - 1][2] - 1][LC - 1];
            X2 = P[NPE[NM - 1][3] - 1][LC - 1];
            FORC1 = X2 * SPRNG[INODE - 1];
            SOILP1 = X2 * SK[INODE - 1];
            FORZM[NM][LC - 1] = FORC1;
            ROTAZ[NM][0][LC - 1] = X1;
            ROTAZ[NM][1][LC - 1] = X2;
            PRESS[NM][LC - 1] = SOILP1;

        }

    }

    private void modif() {
        int NMP1 = NM + 1;
        /*        if(ICOUN<=1 || JCOUN<=1 || NZX >0){
        //legge le posizioni dnel vettore P degli spostamenti assegnati
        //e poi il loro valore
        }
         */
        int NS1;
        int k = 1;
        int L = 2 * k;
        for (int I = k; I <= NMP1; ++I) {
            NS1 = (L - 1) * 4 + 1;
            STIFF[NS1 - 1] += SPRNG[I - 1];
            L += 2;
        }

//introduce condizioni al contorno alla matrice
        if (NZX > 0) {
            for (int IZ = 1; IZ <= NZX; ++IZ) {
                int NPZI = NXZERO[IZ - 1];
                int LL = (NPZI - 1) * 4 + 1;
                STIFF[LL - 1] = 1.0;
                for (int IM = 1; IM <= NLC; ++IM) {
                    P[NPZI - 1][IM - 1] = XSPEC[IZ - 1];
                    for (int IL = 2; IL <= 4; ++IL) {
                        int NPZP = NPZI + IL - 1;
                        if (NPZP <= NP) {
                            P[NPZP - 1][IM - 1] += -STIFF[LL + IL - 1 - 1] * XSPEC[IZ - 1];
                        }

                    }
                    for (int IN = 2; IN < 4 + 1; ++IN) {
                        int NPZM = NPZI - IN + 1;
                        int NPZ1 = (NPZI - IN) * 4 + IN;
                        if (NPZM > 0) {
                            P[NPZM - 1][IM - 1] += -STIFF[NPZ1 - 1] * XSPEC[IZ - 1];
                        }

                    }

                }
                for (k = 2; k <= 4; ++k) {
                    STIFF[LL + k - 1 - 1] = 0.0;
                    int K1 = NPZI - k;
                    if (K1 >= 0) {
                        int KZ = K1 * 4 + k;
                        STIFF[KZ - 1] = 0;

                    }
                }
            }
        }
    }

    private void solv1(int NBAND) {
        int N1 = 1;
        for (int N = 1; N <= NP; ++N) {
            int I = N;
            for (int L = 2; L <= NBAND; ++L) {
                int NL = (N - 1) * NBAND + L;
                I += 1;
                if (STIFF[NL - 1] != 0) {
                    double B = STIFF[NL - 1] / STIFF[N1 - 1];
                    int J = 0;
                    for (int k = L; k <= NBAND; ++k) {
                        J += 1;
                        int IJ = (I - 1) * NBAND + J;
                        int NK = (N - 1) * NBAND + k;
                        if (STIFF[NK - 1] != 0) {
                            STIFF[IJ - 1] += -B * STIFF[NK - 1];
                        }

                    }
                    STIFF[NL - 1] = B;
                    for (int M = 1; M <=
                            NLC; ++M) {
                        P[I - 1][M - 1] += -B * P[N - 1][M - 1];
                    }

                }
            }
            for (int m = 1; m <= NLC; ++m) {
                P[N - 1][m - 1] = P[N - 1][m - 1] / STIFF[N1 - 1];
            }

            N1 += NBAND;
        }

        int N = NP;
        N = N - 1;
        while (N > 0) {
            int L = N - 1;
            for (int k = 2; k <= NBAND; ++k) {
                int NK = (N - 1) * NBAND + k;
                for (int m = 1; m <= NLC; ++m) {
                    if (STIFF[NK - 1] != 0) {
                        P[N - 1][m - 1] += -STIFF[NK - 1] * P[L + k - 1][m - 1];
                    }

                }

            }
            N--;
        }

    }

    private void input() {

        //momenti di inerzia
        XI = Math.PI / 4 * ((PWIDT / 2) * (PWIDT / 2) * (PWIDT / 2) * (PWIDT / 2));
        for (int i = 0; i < NM; ++i) {
            //    B[i] = PWIDT;
            XINER[i] = XI;
            H[i] = LPalo / NM;
            B[i] = PWIDT;
        }

        int K = 1;
        for (int i = 0; i < NM; ++i) {
            NPE[i][0] = K;
            NPE[i][1] = K + 1;
            NPE[i][2] = K + 2;
            NPE[i][3] = K + 3;
            K = K + 2;
        }

    }

    private void load() {

        //azzera P e SUMP
        for (int j = 0; j < NLC; ++j) {
            SUMP[j] = 0;
            for (int i = 0; i < NP; i++) {
                P[i][j] = 0;
            }

        }
        //forza in testa palo
        for (int j = 0; j < NLC; ++j) {
            P[0][j] = FtestaPalo[0][j]; //momento flettente in testa condizione j
            P[1][j] = FtestaPalo[1][j];//forza orizzontale in testa condizione j
            SUMP[j] = FtestaPalo[1][j];//sommo le forze orizzontali per controllo
        }

//memorizza matrice PSAVE
        for (int j = 0; j < NLC; ++j) {
            for (int i = 0; i < NP; i++) {
                PSAVE[i][j] = P[i][j];
            }

        }

    }

    private void spring() {
        int NMP1 = NM + 1; //numero nodi

        //azzero array spring
        for (int i = 0; i < NMP1; i++) {
            SPRNG[i] = 0;
        }

        //k molle nodi interrati
        double Z = 0;
        for (int k = 0; k < NMP1; k++) {
            if (k >= JTSOIL) {
                SK[k] = AS + BS * Math.pow(Z, EXPO);
                if (k != NMP1 - 1) {
                    Z += H[k];
                }

            } else {
                SK[k] = 0;
            }

        }

        //k molle estremi igni elemento
        for (int k = 0; k < NM; k++) {
            //elementi non estremi
            if (k >= JTSOIL && k != NM - 1) {
                SSK[k][0] = H[k] * B[k] * (7.0 * SK[k] + 6.0 * SK[k + 1] - SK[k + 2]) / 24;
                SSK[k][1] = H[k] * B[k] * (3.0 * SK[k] + 10.0 * SK[k + 1] - SK[k + 2]) / 24;
                //riduce la prima molla di REDFAC
                if (k == JTSOIL) {
                    SSK[k][0] *= REDFAC;
                }

            } else if (k == NM - 1) {
                SSK[k][0] = H[k] * B[k] * (3.0 * SK[k + 1] + 10.0 * SK[k] - SK[k - 1]) / 24;
                SSK[k][1] = H[k] * B[k] * (7.0 * SK[k + 1] + 6.0 * SK[k] - SK[k - 1]) / 24;
            } else if (k < JTSOIL) {
                SSK[k][0] = 0;
                SSK[k][1] = 0;
            }

        }

        //calcola le molle modali partendo dalle molle degli elementi
        int k = JTSOIL;
        for (int i = k; i < NMP1; i++) {
            //per nodi interni...
            if (i != k && i != NMP1 - 1) {
                SPRNG[i] = SSK[i - 1][1] + SSK[i][0];
            } else if (i == k) {
                SPRNG[i] = SSK[i][0];
            } else if (i == NMP1 - 1) {
                SPRNG[i] = SSK[i - 1][1];
            }
        }

    }
    }
    
