package progetto.model.util;

import java.awt.Polygon;
import java.util.ArrayList;

import progetto.model.bean.Appoggio;
import progetto.model.bean.Carico;
import progetto.model.bean.CaricoForze;
import progetto.model.bean.Combinazione;
import progetto.model.bean.Palo;
import progetto.model.bean.Spalla;
import progetto.model.bean.SpallaManager;
import progetto.model.bean.Terreno;
import progetto.model.bean.Verticale;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class Calcoli {

    // materiali
    double gammaCls;

    // Fonda
    double BxCiabatta, ByCiabatta, Spf;

    // muri d'ala
    double BmuriAla, SpMuriAla, HmuriAla;

    // paraghiaie
    double SpParaghiaia, Hparaghiaia, dyParaghiaia;

    // spalla
    double BxSpalla, BySpalla, Hspalla;
    double dySpalla, dxSpalla;

    // terreno
    double deltaTerreno;
    boolean spintaVerticale;
    double Hterreno;
    double HterrenoValle;
    double gammaTerreno;
    double qSovraccarico;
    double Kas, Kad, Kass;

    // Appoggi
    double[][][] Fappoggi;
    double[][] xAppoggi;
    private int nCarichi;
    private int nAppoggi;
    private double kAtrito;
    ArrayList carichi;

    // palificata
    private int nPali;
    private double kpali;
    private double[][] pali;
    private double Lpalo;
    private double Zpalo;
    private double Dpalo;
    private double Zfalda;
    // sezioni di verifica
    double xVerifica;
    double yVerifica;

    // combinazioni
    double[][] combinazioni;

    // inclinazione impalcato
    double alfa;

    // forze di inerzia sismica
    double agS;

    public Calcoli() {
        getVariabiliLocali();
    }

    public double getFiMedioPuntaPalo(Verticale verticale) {
       ArrayList strati = verticale.getStrati();
        double zTesta = Zpalo + Lpalo;
        double DZ = 0.001;
        double zmin = zTesta-0.5*Dpalo;
        double zmax = zTesta + 3*Dpalo;
        double z1 = zmin;
        double z2 = zmin +DZ;
        double zm = z1 / 2 + z2 / 2;
        double FiM = 0;
        Terreno t;
        while (z2 < zmax) {
            int ns = getNstrato(zm, strati);
            t = (Terreno) strati.get(ns);
            double Fi = t.getFi();
            FiM += Fi*DZ;
            z1 += DZ;
            z2 += DZ;
            zm = z1 / 2 + z2 / 2;
        }
        FiM = FiM/(zmax-zmin);
        
        return FiM;
    }

    public double getQbaseStarti(Verticale verticale) {
        ArrayList strati = verticale.getStrati();
        double zmax = Zpalo + Lpalo;
        double AreaB = Math.PI * Dpalo * Dpalo / 4;
    
        //condizioni drenate o non drenate?
        int ns = getNstrato(zmax, strati);
        Terreno t = (Terreno) strati.get(ns);
        double fi = t.getFi();

        if (fi != 0) {
            double Nq = verticale.getNq();
            double sigP = getSigmazEff(zmax, strati);
            return sigP * AreaB * Nq;
        } else {
            //tensione efficace verticale
            double Nc = verticale.getNc();
            double sigT = getSigmazTot(zmax, strati);
            double Cu = getCuMediaPuntaPalo(verticale);
            return Nc*Cu+sigT;
        }

    }

    public double getCuMediaPuntaPalo(Verticale verticale) {

       ArrayList strati = verticale.getStrati();
        double zTesta = Zpalo + Lpalo;
        double DZ = 0.001;
        double zmin = zTesta-0.5*Dpalo;
        double zmax = zTesta + 3*Dpalo;
        double z1 = zmin;
        double z2 = zmin +DZ;
        double zm = z1 / 2 + z2 / 2;
        double CuM = 0;
        Terreno t;
        while (z2 < zmax) {
            int ns = getNstrato(zm, strati);
            t = (Terreno) strati.get(ns);
            double Cu = t.getC();
            CuM += Cu*DZ;
            z1 += DZ;
            z2 += DZ;
            zm = z1 / 2 + z2 / 2;
        }
        CuM = CuM/(zmax-zmin);
        
        return CuM;
    }

    public double getQl1Starti(Verticale verticale) {

        ArrayList strati = verticale.getStrati();
        double zmin = Zpalo;
        double zmax = Zpalo + Lpalo;
        double DZ = 0.0005;
        double z1 = zmin;
        double z2 = zmin + DZ;
        double zm = z1 / 2 + z2 / 2;
        double Slat = 0;
        Terreno t;
        while (z2 < zmax) {
            int ns = getNstrato(zm, strati);
            t = (Terreno) strati.get(ns);
            double fi = t.getFi();
            double sup = Math.PI * Dpalo;
            if (fi != 0) {
                double K = t.getKa();
                double Tanfi = Math.tan(fi / 180 * Math.PI);
                Slat += getSigmazEff(zm, strati) * K * Tanfi * DZ * sup;
            } else {
                double alfaLoc = t.getAlfa();
                double cu = t.getC();
                Slat += alfaLoc * cu * DZ * sup;
            }

            z1 += DZ;
            z2 += DZ;
            zm = z1 / 2 + z2 / 2;
        }

        return Slat;
    }

    private double[] getGammi(ArrayList strati) {
        int ns = strati.size();
        double[] z = new double[ns];
        for (int i = 0; i < ns; ++i) {
            Terreno t = (Terreno) strati.get(i);
            z[i] = t.getGamma();
        }
        return z;

    }

    private double getSigmazEff(double Z, ArrayList strati) {
        int ns = strati.size();
        double[] Zi = getZstrati(strati);
        double[] Gammi = getGammi(strati);

        double sigz = 0;
        double z0 = 0;
        double z1;
        int i = 0;

        //sigma totale
        while (i < ns) {
            z1 = Zi[i];
            if (Z < Zi[i]) {
                sigz += Gammi[i] * (Z - z0);
                break;
            }
            sigz += Gammi[i] * (z1 - z0);
            ++i;
            z0 = z1;
        }

        //sigma efficace
        if (Z < Zfalda) {
            return sigz;
        } else {
            return sigz - (Z - Zfalda) * 9.8;
        }
    }
    private double getSigmazTot(double Z, ArrayList strati) {
        int ns = strati.size();
        double[] Zi = getZstrati(strati);
        double[] Gammi = getGammi(strati);

        double sigz = 0;
        double z0 = 0;
        double z1;
        int i = 0;

        //sigma totale
        while (i < ns) {
            z1 = Zi[i];
            if (Z < Zi[i]) {
                sigz += Gammi[i] * (Z - z0);
                break;
            }
            sigz += Gammi[i] * (z1 - z0);
            ++i;
            z0 = z1;
        }
        
        return sigz;

    }

    private double[] getZstrati(ArrayList strati) {
        int ns = strati.size();
        double[] z = new double[ns];
        for (int i = 0; i < ns; ++i) {
            Terreno t = (Terreno) strati.get(i);
            z[i] = t.getH();
        }
        return z;

    }

    private int getNstrato(double Z, ArrayList strati) {
        int ns = strati.size();
        double[] Zi = getZstrati(strati);

        double z0 = 0;
        double z1;

        for (int i = 0; i < ns; ++i) {
            z1 = Zi[i];
            if (Z >= z0 && Z < z1) {
                return i;
            }
            z0 = z1;
        }
        return 0;
}

        public double getFiBase(double z, ArrayList strati) {
        int stratoBase = getNstrato(z, strati);
        Terreno s = (Terreno) strati.get(stratoBase);

        return s.getFi();
}

    
    private void getVariabiliLocali() {
        // NB tutto in Newton e millimetri
        Spalla spalla = SpallaManager.getInstance().getCurrentSpalla();
        double prop = 1;

        carichi = spalla.getCarichi();

        //pali
        Zpalo = spalla.getZpalo();
        Lpalo = spalla.getLpalo();
        Dpalo = spalla.getDpalo();
        Zfalda = spalla.getZfalda();



        // fonda
        BxCiabatta = spalla.getBxFonda() * prop;
        ByCiabatta = spalla.getByFonda() * prop;
        Spf = spalla.getSpFonda() * prop;
        alfa = (spalla.getAlfa() / 180.0000) * Math.PI;

        // muri d'ala
        BmuriAla = spalla.getLMuri() * prop;
        SpMuriAla = spalla.getSpMuri() * prop;

        // paraghiaie
        SpParaghiaia = spalla.getSpPara() * prop;
        Hparaghiaia = spalla.getHPara() * prop;
        dyParaghiaia = spalla.getDyPara() * prop;

        // spalla
        BxSpalla = spalla.getBxElevazione() * prop;
        BySpalla = spalla.getByElevazione() * prop;
        Hspalla = spalla.getHsElevazione() * prop;
        dySpalla = spalla.getYgElevazione() * prop;
        dxSpalla = spalla.getXgElevazione() * prop;

        // Materiali
        gammaCls = spalla.getGammaCLS() / 1;// 000000;

        // terreno
        Hterreno = spalla.getH_terreno() * prop;
        HterrenoValle = spalla.getH_terrenoValle() * prop;
        gammaTerreno = spalla.getGammaTerreno() / 1;// 000000;
        qSovraccarico = spalla.getQ_carico() / 1;// 000;
        Kas = spalla.getK_static();
        Kad = spalla.getK_dinamic();
        Kass = spalla.getK_static_dinamic() - spalla.getK_dinamic();// k solo
        // statico
        // surante
        // sisma
        deltaTerreno = (spalla.getDeltaTerreno() / 180.0000) * Math.PI;
        spintaVerticale = spalla.isSpintaVerticale();
        agS = spalla.getAgS();

        // grandezze derivate
        HmuriAla = Hspalla + Hparaghiaia;

        // Fappoggi
        kAtrito = 0.03;
        ArrayList carichi = spalla.getCarichi();
        ArrayList appoggi = spalla.getAppoggi();
        nCarichi = carichi.size();
        nAppoggi = appoggi.size();
        Carico carico;
        ArrayList forze;
        CaricoForze forza;
        Fappoggi = new double[5][nAppoggi][nCarichi];
        for (int i = 0; i < carichi.size(); i++) {
            carico = (Carico) carichi.get(i);
            forze = carico.getForzeAppoggi();
            for (int z = 0; z < forze.size(); z++) {
                forza = (CaricoForze) forze.get(z);
                Fappoggi[0][z][i] = forza.getFx();
                Fappoggi[1][z][i] = forza.getFy();
                Fappoggi[2][z][i] = forza.getFz();
                Fappoggi[3][z][i] = forza.getMx();
                Fappoggi[4][z][i] = forza.getMy();
            }
        }

        // xAppoggi;
        Appoggio appoggio;
        xAppoggi = new double[3][nAppoggi];
        for (int k = 0; k < nAppoggi; k++) {
            appoggio = (Appoggio) appoggi.get(k);
            xAppoggi[0][k] = appoggio.getX() * prop;
            xAppoggi[1][k] = appoggio.getY() * prop;
            xAppoggi[2][k] = appoggio.getZ() * prop;
        }

        // combinazioni
        ArrayList combinazioniList = spalla.getCombinazioni();
        Combinazione combinazione;
        ArrayList combos;
        combinazioni = new double[6 + nCarichi][combinazioniList.size()];
        for (int q = 0; q < combinazioniList.size(); q++) {
            combinazione = (Combinazione) combinazioniList.get(q);
            combos = combinazione.getCombo();
            for (int r = 0; r < combos.size(); r++) {
                combinazioni[r][q] = ((Double) combos.get(r)).doubleValue();
            }
        }

        xVerifica = 0;
        yVerifica = 0;

        // pali
        ArrayList listaPali = spalla.getPalificata();
        nPali = listaPali.size();
        Palo palo;
        pali = new double[3][nPali];
        for (int i = 0; i < nPali; i++) {
            palo = (Palo) listaPali.get(i);
            pali[0][i] = palo.getX() * prop;
            pali[1][i] = palo.getY() * prop;
            pali[2][i] = palo.getDiametro() * prop;
        }
        kpali = spalla.getKpali();

    }

    // calcola momenti M1 ciabatta
    public double[] getM1CiabattaFondazione() {
        double[] M1 = {0, 0, 0, 0, 0};
        return M1;
    }

    // calcola momenti M2 ciabatta
    public double[] getM2CiabattaFondazione() {
        double[] M = {0, 0, 0, 0, 0};
        // Forza assiale
        M[2] = BxCiabatta * ByCiabatta * Spf * gammaCls;
        return M;
    }

    // calcola momenti M1 muri d'ala
    public double[] getM1MuriAla() {
        double[] M = {0, 0, 0, 0, 0};
        return M;
    }

    // calcola momenti M2 muri d'ala
    public double[] getM2MuriAla() {
        double[] M = {0, 0, 0, 0, 0};

        double dgy = -BySpalla / 2 - BmuriAla / 2 + dySpalla;

        // Forza assiale
        M[2] = 2 * BmuriAla * SpMuriAla * HmuriAla * gammaCls;
        // Momenti Mx
        M[3] = -M[2] * dgy;
        // Momenti My
        M[4] = M[2] * dxSpalla;
        return M;
    }

    // calcola momenti M1 spalla
    public double[] getM1Spalla() {
        double[] M = {0, 0, 0, 0, 0};
        // Forza assiale
        M[2] = Hspalla * BxSpalla * BySpalla * gammaCls;
        return M;
    }

    // calcola momenti M2 spalla
    public double[] getM2Spalla() {
        double[] M = {0, 0, 0, 0, 0};
        // Forza assiale
        M[2] = Hspalla * BxSpalla * BySpalla * gammaCls;
        // momento Mx
        M[3] = -M[2] * dySpalla;
        // momento My
        M[4] = M[2] * dxSpalla;
        return M;
    }

    // calcola momenti M1 paraghiaia
    public double[] getM1Paraghiaia() {
        double[] M = {0, 0, 0, 0, 0};
        double dgy = -BySpalla / 2 + SpParaghiaia / 2 + dyParaghiaia;

        // Forza assiale
        M[2] = Hparaghiaia * BxSpalla * SpParaghiaia * gammaCls;
        // momento Mx
        M[3] = -M[2] * dgy;
        return M;
    }

    // calcola momenti M2 paraghiaia
    public double[] getM2Paraghiaia() {
        double[] M = {0, 0, 0, 0, 0};
        double dgy = -BySpalla / 2 + SpParaghiaia / 2 + dySpalla + dyParaghiaia;
        double dgx = dxSpalla;

        // Forza assiale
        M[2] = Hparaghiaia * BxSpalla * SpParaghiaia * gammaCls;
        // momento Mx
        M[3] = -M[2] * dgy;
        // momento My
        M[4] = M[2] * dgx;

        return M;
    }

    // calcola momenti M1 terreno monte (solo carico verticale)
    public double[] getM1TerrenoMonteV() {
        double[] M = {0, 0, 0, 0, 0};
        return M;
    }

    // calcola momenti M2 terreno monte (solo carico verticale)
    public double[] getM2TerrenoMonteV() {
        double[] M = {0, 0, 0, 0, 0};
        double L = ByCiabatta / 2 + dySpalla - BySpalla / 2;
        double B;

        if (SpMuriAla != 0) {
            B = BxSpalla - 2 * SpMuriAla;
        } else {
            B = BxCiabatta;
        }

        double dgy = -(ByCiabatta / 2 - L / 2);

        // Forza assiale
        M[2] = Hterreno * (L * B) * gammaTerreno;
        // momento Mx
        M[3] = -M[2] * dgy;

        // momento My
        M[4] = M[2] * dxSpalla;

        return M;
    }

    // calcola momenti M1 sovraccarico (carico orizzontale)
    private double[] getM1SovraccaricoH(boolean sismica) {
        double[] M = {0, 0, 0, 0, 0};

        double k = 0;
        if (sismica == false) {
            k = Kas;
        } else {
            k = Kass;
        }

        double L = BxSpalla - 2 * SpMuriAla;
        double sigTer = qSovraccarico * k;
        double F = L * Hterreno * sigTer;
        double Fh = F * Math.cos(deltaTerreno);
        double Fv = F * Math.sin(deltaTerreno);

        if (spintaVerticale == false) {
            Fv = 0;
        }

        // forza Fy
        M[1] = Fh;
        // forza Fz
        M[2] = Fv;
        // momento Mx
        M[3] = -Fh * Hterreno / 2 + Fv * BySpalla / 2;

        return M;
    }

    // calcola momenti M2 sovraccarico (carico verticale)
    public double[] getM2SovraccaricoH(boolean sismica) {

        double k = 0;
        if (sismica == false) {
            k = Kas;
        } else {
            k = Kass;
        }

        double[] M = {0, 0, 0, 0, 0};

        double L = BxSpalla;
        double sigTer = qSovraccarico * k;
        double H = Hterreno + Spf;
        double F = L * H * sigTer;
        double Fh = F * Math.cos(deltaTerreno);
        double Fv = F * Math.sin(deltaTerreno);

        if (spintaVerticale == false) {
            Fv = 0;
        }

        // spinta orizzontale
        // forza Fy
        M[1] = Fh;
        // forza Fz
        M[2] = Fv;
        // momento Mx
        M[3] = -Fh * H / 2 + Fv * (ByCiabatta / 2);

        return M;

    }

    // calcola momenti M1 sovraccarico (carico verticale)
    private double[] getM1SovraccaricoV() {
        double[] M = {0, 0, 0, 0, 0};

        return M;
    }

    // calcola momenti M2 sovraccarico (carico verticale)
    public double[] getM2SovraccaricoV() {

        double[] M = {0, 0, 0, 0, 0};

        // peso proprio del terreno sulla fondazione
        double Lt = ByCiabatta / 2 + dySpalla - BySpalla / 2;
        double dgy = -ByCiabatta / 2 + Lt / 2;
        double B = BxSpalla - 2 * SpMuriAla;

        // Forza assiale
        double N = (Lt * B) * qSovraccarico;
        M[2] = N;
        // momento Mx
        M[3] = -N * dgy;
        // momento My
        M[4] = N * dxSpalla;

        return M;
    }

    // calcola momenti M1 terreno valle
    public double[] getM1TerrenoValle() {
        double[] M = {0, 0, 0, 0, 0};
        return M;
    }

    // calcola momenti M2 terreno valle
    public double[] getM2TerrenoValle() {
        double[] M = {0, 0, 0, 0, 0};

        double L = ByCiabatta / 2 - dySpalla - BySpalla / 2;
        double dgy = (ByCiabatta / 2 - L / 2);

        // Forza assiale
        M[2] = HterrenoValle * (L * BxCiabatta) * gammaTerreno;
        // momento Mx
        M[3] = -M[2] * dgy;

        return M;
    }

    // calcola momenti M1 spinta statica orizzontale
    public double[] getM1TerrenoMonteH(boolean sismica) {

        double k = 0;
        if (sismica == false) {
            k = Kas;
        } else {
            k = Kass;
        }

        double[] M = {0, 0, 0, 0, 0};
        double L = BxSpalla - 2 * SpMuriAla;
        double sigTer = (Hterreno * gammaTerreno) * k;
        double F = 0.5 * L * Hterreno * sigTer;
        double Fh = F * Math.cos(deltaTerreno);
        double Fv = F * Math.sin(deltaTerreno);

        if (spintaVerticale == false) {
            Fv = 0;
        }

        // forza Fy
        M[1] = Fh;
        // forza Fz
        M[2] = Fv;
        // momento Mx
        M[3] = -Fh * Hterreno / 3 + Fv * BySpalla / 2;

        if (sismica == false) {
            return M;
        }

        double[] Msovra = getM1SovraSpintaDianamica();
        M[0] += Msovra[0];
        M[1] += Msovra[1];
        M[2] += Msovra[2];
        M[3] += Msovra[3];
        M[4] += Msovra[4];

        return M;

    }

    // calcola momenti M2 spinta statica orizzontale
    public double[] getM2TerrenoMonteH(boolean sismica) {

        double k = 0;
        if (sismica == false) {
            k = Kas;
        } else {
            k = Kass;
        }

        double[] M = {0, 0, 0, 0, 0};
        double L = BxSpalla;
        double H = Hterreno + Spf;
        double sigTer = (H * gammaTerreno) * k;
        double F = 0.5 * L * H * sigTer;
        double Fh = F * Math.cos(deltaTerreno);
        double Fv = F * Math.sin(deltaTerreno);

        if (spintaVerticale == false) {
            Fv = 0;
        }

        // forza Fy
        M[1] = Fh;
        // forza Fz
        M[2] = Fv;
        // momento Mx
        M[3] = -Fh * (H / 3) + Fv * (ByCiabatta / 2);

        if (sismica == false) {
            return M;
        }

        double[] Msovra = getM2SovraSpintaDianamica();
        M[0] += Msovra[0];
        M[1] += Msovra[1];
        M[2] += Msovra[2];
        M[3] += Msovra[3];
        M[4] += Msovra[4];

        return M;

    }

    // calcola momenti M1 "sovra" spinta dinamica
    public double[] getM1SovraSpintaDianamica() {
        double[] M = {0, 0, 0, 0, 0};
        double L = BxSpalla - 2 * SpMuriAla;
        double sigTer = (Hterreno * gammaTerreno) * Kad;
        double F = 0.5 * L * Hterreno * sigTer;
        double Fh = F * Math.cos(deltaTerreno);
        double Fv = F * Math.sin(deltaTerreno);

        if (spintaVerticale == false) {
            Fv = 0;
        }


        // forza Fy
        M[1] = Fh;
        // forza Fz
        M[2] = Fv;
        // momento Mx
        M[3] = -Fh * Hterreno / 2 + Fv * BySpalla / 2;

        return M;
    }

    // calcola momenti M2 "sovra" spinta dinamica
    public double[] getM2SovraSpintaDianamica() {
        double[] M = {0, 0, 0, 0, 0};
        double L = BxSpalla;
        double H = Hterreno + Spf;
        double sigTer = (H * gammaTerreno) * Kad;
        double F = 0.5 * L * H * sigTer;
        double Fh = F * Math.cos(deltaTerreno);
        double Fv = F * Math.sin(deltaTerreno);

        if (spintaVerticale == false) {
            Fv = 0;
        }

        // forza Fy
        M[1] = Fh;
        // forza Fz
        M[2] = Fv;
        // momento Mx
        M[3] = -Fh * (H / 2) + Fv * (ByCiabatta / 2);

        return M;
    }

    // calcola momenti M1 inerzia paraghiaia
    public double[] getM1InerziaParaghiaia() {
        double[] M = {0, 0, 0, 0, 0};

        double N = getM2Paraghiaia()[2];
        // NB sisma sia x sia y (in sede di combianzione separo i contributi)
        M[0] = N * agS;
        M[1] = N * agS;
        // altezza spinta
        double z = Hspalla + Hparaghiaia / 2;
        // moment0 Mx
        M[3] = -N * agS * z;
        // momento My
        M[4] = N * agS * z;
        return M;
    }

    // calcola momenti M2 inerzia paraghiaia
    public double[] getM2InerziaParaghiaia() {
        double[] M = {0, 0, 0, 0, 0};

        double N = getM2Paraghiaia()[2];
        // NB sisma sia x sia y (in sede di combianzione separo i contributi)
        M[0] = N * agS;
        M[1] = N * agS;
        // altezza spinta
        double z = Hspalla + Hparaghiaia / 2 + Spf;
        // moment0 Mx
        M[3] = -N * agS * z;
        // momento My
        M[4] = N * agS * z;
        return M;
    }

    // calcola momenti M1 inerzia spalla in elevazioni
    public double[] getM1InerziaSpalla() {
        double[] M = {0, 0, 0, 0, 0};

        double N = getM2Spalla()[2];
        // NB sisma sia x sia y (in sede di combianzione separo i contributi)
        M[0] = N * agS;
        M[1] = N * agS;
        // altezza spinta
        double z = Hspalla / 2;
        // moment0 Mx
        M[3] = -N * agS * z;
        // momento My
        M[4] = N * agS * z;
        return M;
    }

    // calcola momenti M2 inerzia paraghiaia
    public double[] getM2Inerziaspalla() {
        double[] M = {0, 0, 0, 0, 0};

        double N = getM2Spalla()[2];
        // NB sisma sia x sia y (in sede di combianzione separo i contributi)
        M[0] = N * agS;
        M[1] = N * agS;
        // altezza spinta
        double z = Hspalla / 2 + Spf;
        // moment0 Mx
        M[3] = -N * agS * z;
        // momento My
        M[4] = N * agS * z;
        return M;
    }

    // calcola momenti M1 inerzia ciabatta
    public double[] getM1InerziaCiabatta() {
        double[] M = {0, 0, 0, 0, 0};

        return M;
    }

    // calcola momenti M2 inerzia paraghiaia
    public double[] getM2InerziaCiabatta() {
        double[] M = {0, 0, 0, 0, 0};

        double N = getM2CiabattaFondazione()[2];
        // NB sisma sia x sia y (in sede di combianzione separo i contributi)
        M[0] = N * agS;
        M[1] = N * agS;
        // altezza spinta
        double z = Spf / 2;
        // moment0 Mx
        M[3] = -N * agS * z;
        // momento My
        M[4] = N * agS * z;
        return M;
    }

    // calcola momenti M1 inerzia ciabatta
    public double[] getM1InerziaMuriAla() {
        double[] M = {0, 0, 0, 0, 0};
        return M;
    }

    // calcola momenti M2 inerzia paraghiaia
    public double[] getM2InerziaMuriAla() {
        double[] M = {0, 0, 0, 0, 0};

        double N = getM2MuriAla()[2];
        // NB sisma sia x sia y (in sede di combianzione separo i contributi)
        M[0] = N * agS;
        M[1] = N * agS;
        // altezza spinta
        double z = HmuriAla / 2 + Spf;
        // moment0 Mx
        M[3] = -N * agS * z;
        // momento My
        M[4] = N * agS * z;
        return M;
    }

    // calcola momenti M1 inerzia terreno monte
    public double[] getM1InerziaTerrenoMonte() {
        double[] M = {0, 0, 0, 0, 0};

        double N = getM2TerrenoMonteV()[2];
        // NB sisma sia x sia y (in sede di combianzione separo i contributi)
        M[0] = N * agS;
        M[1] = N * agS;
        // altezza spinta
        double z = Hterreno / 2;
        // moment0 Mx
        M[3] = -N * agS * z;
        // momento My
        M[4] = N * agS * z;
        return M;
    }

    // calcola momenti M2 inerzia paraghiaia
    public double[] getM2InerziaTerrenoMonte() {
        double[] M = {0, 0, 0, 0, 0};

        double N = getM2TerrenoMonteV()[2];
        // NB sisma sia x sia y (in sede di combianzione separo i contributi)
        M[0] = N * agS;
        M[1] = N * agS;
        // altezza spinta
        double z = Hterreno / 2 + Spf;
        // moment0 Mx
        M[3] = -N * agS * z;
        // momento My
        M[4] = N * agS * z;
        return M;
    }

    public double[] getFyCaricoAppoggiAtrito() {
        double[] M = new double[nAppoggi];
        double Fzperm;

        for (int z = 0; z < nAppoggi; ++z) {
            Fzperm = getFzPermanentiAppoggioI(z);
            M[z] = Fzperm * kAtrito;
        }
        return M;
    }

    // calcola momenti M1 caichi appoggi
    public double[] getM1CarichiAppoggio(int i) {
        double[] M = {0, 0, 0, 0, 0};


        Carico c = (Carico) carichi.get(i);
        // caso carico normale
        if (!c.isAtrito()) {
            // forza Fx,Fy,Fz
            for (int z = 0; z < nAppoggi; ++z) {
                M[0] += Fappoggi[0][z][i];
                M[1] += Fappoggi[1][z][i];
                M[2] += Fappoggi[2][z][i];
                // momento Mx
                M[3] += Fappoggi[3][z][i] - Fappoggi[1][z][i] * xAppoggi[2][z] - Fappoggi[2][z][i] * xAppoggi[1][z];

                // momento My
                M[4] += Fappoggi[4][z][i] + Fappoggi[0][z][i] * xAppoggi[2][z] + Fappoggi[2][z][i] * xAppoggi[0][z];
            }
        } else {
            double Fzperm = 0;
            M[0] = 0;
            M[2] = 0;
            M[4] = 0;
            for (int z = 0; z < nAppoggi; ++z) {
                Fzperm = getFzPermanentiAppoggioI(z);
                M[1] += Fzperm * kAtrito;
                // momento Mx
                M[3] += -Fzperm * kAtrito * (xAppoggi[2][z]);
            }

        }

        return M;
    }

    // calcola momenti M2 caichi appoggi
    public double[] getM2CarichiAppoggio(int i) {
        double[] M = {0, 0, 0, 0, 0};

        Carico c = (Carico) carichi.get(i);
        // caso carico normale
        if (!c.isAtrito()) {
            // forza Fx,Fy,Fz
            for (int z = 0; z < nAppoggi; ++z) {
                M[0] += Fappoggi[0][z][i];
                M[1] += Fappoggi[1][z][i];
                M[2] += Fappoggi[2][z][i];
                // momento Mx
                M[3] += Fappoggi[3][z][i] - Fappoggi[1][z][i] * (xAppoggi[2][z] + Spf) - Fappoggi[2][z][i] * (xAppoggi[1][z] + dySpalla);

                // momento My
                M[4] += Fappoggi[4][z][i] + Fappoggi[0][z][i] * (xAppoggi[2][z] + Spf) + Fappoggi[2][z][i] * (xAppoggi[0][z] + dxSpalla);
            }
        } else {
            double Fzperm = 0;
            M[0] = 0;
            M[2] = 0;
            M[4] = 0;
            for (int z = 0; z < nAppoggi; ++z) {
                Fzperm = getFzPermanentiAppoggioI(z);
                M[1] += Fzperm * kAtrito;
                // momento Mx
                M[3] += -Fzperm * kAtrito * (xAppoggi[2][z] + Spf);
            }

        }
        return M;
    }

    // calcola momenti M2 carichi appoggi
    public double[] getM2CarichiAppoggi() {
        double[] M = {0, 0, 0, 0, 0};
        double[] Mi;

        for (int i = 0; i < nCarichi; ++i) {
            Mi = getM2CarichiAppoggio(i);
            for (int j = 0; j < 5; ++j) {
                M[j] += Mi[j];
            }
        }
        return M;
    }

    // calcola momenti M1 caichi appoggi
    public double[] getM1CarichiAppoggi() {
        double[] M = {0, 0, 0, 0, 0};
        double[] Mi;

        for (int i = 0; i < nCarichi; ++i) {
            Mi = getM1CarichiAppoggio(i);
            for (int j = 0; j < 5; ++j) {
                M[j] += Mi[j];
            }
        }
        return M;
    }

    // calcola momenti M1 caichi permanenti
    public double[] getM1Permanenti() {
        double[] M = {0, 0, 0, 0, 0};
        double[] Mi;

        Mi = getM1CiabattaFondazione();
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j];
        }

        Mi = getM1MuriAla();
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j];
        }

        Mi = getM1Spalla();
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j];
        }

        Mi = getM1Paraghiaia();
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j];
        }

        Mi = getM1TerrenoMonteV();
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j];
        }

        Mi = getM1TerrenoValle();
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j];
        }

        /*
         * Mi = getM1TerrenoMonteH(sismica); for (int j = 0; j < 5; ++j) { M[j] +=
         * Mi[j]; }
         */
        return M;
    }

    // calcola momenti M2 carichi permanenti
    public double[] getM2Permanenti() {
        double[] M = {0, 0, 0, 0, 0};
        double[] Mi;

        Mi = getM2CiabattaFondazione();
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j];
        }

        Mi = getM2MuriAla();
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j];
        }

        Mi = getM2Spalla();
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j];
        }

        Mi = getM2Paraghiaia();
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j];
        }

        Mi = getM2TerrenoMonteV();
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j];
        }

        Mi = getM2TerrenoValle();
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j];
        }

        /*
         * Mi = getM2TerrenoMonteH(sismica); for (int j = 0; j < 5; ++j) { M[j] +=
         * Mi[j]; }
         */
        return M;
    }

    // calcola momenti M2 delle spinte orizzontali del terreno
    public double[] getM2SpinteOrizzontaliTerreno(boolean sismica, int combo) {
        double[] M = {0, 0, 0, 0, 0};
        double[] Mi;

        Mi = getM2SovraccaricoH(sismica);
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j] * combinazioni[1][combo];// combinato anche secondo
        // il coeffiente
        // combinazione del
        // covraccarico
        }
        Mi = getM2TerrenoMonteH(sismica);
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j];
        }

        return M;
    }

    /*	// calcola momenti M1 delle spinte orizzontali del terreno
    public double[] getM1SpinteOrizzontaliTerreno(boolean sismica, int combo) {
    double[] M = { 0, 0, 0, 0, 0 };
    double[] Mi;
    Mi = getM1SovraccaricoH(sismica);
    for (int j = 0; j < 5; ++j) {
    M[j] += Mi[j] * combinazioni[1][combo];// combinato anche secondo
    // il coeffiente
    // combinazione del
    // covraccarico
    }
    Mi = getM1TerrenoMonteH(sismica);
    for (int j = 0; j < 5; ++j) {
    M[j] += Mi[j];
    }
    return M;
    }
     */
    // calcola il totale delle azioni simiche (inerzie)
    public double[] getM1SpinteDianamicheSismaY() {
        double[] M = {0, 0, 0, 0, 0};
        double[] Mi;


        Mi = getM1InerziaCiabatta();
        for (int j = 0; j < 5; ++j) {
            if (j == 1 || j == 3) {
                M[j] += Mi[j];
            }
        }

        Mi = getM1InerziaMuriAla();
        for (int j = 0; j < 5; ++j) {
            if (j == 1 || j == 3) {
                M[j] += Mi[j];
            }
        }
        Mi = getM1InerziaParaghiaia();
        for (int j = 0; j < 5; ++j) {
            if (j == 1 || j == 3) {
                M[j] += Mi[j];
            }
        }
        Mi = getM1InerziaSpalla();
        for (int j = 0; j < 5; ++j) {
            if (j == 1 || j == 3) {
                M[j] += Mi[j];
            }
        }
        Mi = getM1InerziaTerrenoMonte();
        for (int j = 0; j < 5; ++j) {
            if (j == 1 || j == 3) {
                M[j] += Mi[j];
            }
        }

        return M;
    }

    // calcola il totale delle azioni simiche (inerzie + sovraspinte)
    public double[] getM2SpinteDianamicheSismaY() {
        double[] M = {0, 0, 0, 0, 0};
        double[] Mi;


        Mi = getM2InerziaCiabatta();
        for (int j = 0; j < 5; ++j) {
            if (j == 1 || j == 3) {
                M[j] += Mi[j];
            }
        }

        Mi = getM2InerziaMuriAla();
        for (int j = 0; j < 5; ++j) {
            if (j == 1 || j == 3) {
                M[j] += Mi[j];
            }
        }
        Mi = getM2InerziaParaghiaia();
        for (int j = 0; j < 5; ++j) {
            if (j == 1 || j == 3) {
                M[j] += Mi[j];
            }
        }
        Mi = getM2Inerziaspalla();
        for (int j = 0; j < 5; ++j) {
            if (j == 1 || j == 3) {
                M[j] += Mi[j];
            }
        }
        Mi = getM2InerziaTerrenoMonte();
        for (int j = 0; j < 5; ++j) {
            if (j == 1 || j == 3) {
                M[j] += Mi[j];
            }
        }

        return M;
    }

    // calcola il totale delle azioni simiche (inerzie + sovraspinte)
    public double[] getM1SpinteDianamicheSismaX() {
        double[] M = {0, 0, 0, 0, 0};
        double[] Mi;

        Mi = getM1SovraSpintaDianamica();
        for (int j = 0; j < 5; ++j) {
            // nb: solo azione sismica x
            if (j == 0 || j == 4) {
                M[j] += Mi[j];
            }
        }

        Mi = getM1InerziaCiabatta();
        for (int j = 0; j < 5; ++j) {
            if (j == 0 || j == 4) {
                M[j] += Mi[j];
            }
        }

        Mi = getM1InerziaMuriAla();
        for (int j = 0; j < 5; ++j) {
            if (j == 0 || j == 4) {
                M[j] += Mi[j];
            }
        }
        Mi = getM1InerziaParaghiaia();
        for (int j = 0; j < 5; ++j) {
            if (j == 0 || j == 4) {
                M[j] += Mi[j];
            }
        }
        Mi = getM1InerziaSpalla();
        for (int j = 0; j < 5; ++j) {
            if (j == 0 || j == 4) {
                M[j] += Mi[j];
            }
        }
        Mi = getM1InerziaTerrenoMonte();
        for (int j = 0; j < 5; ++j) {
            if (j == 0 || j == 4) {
                M[j] += Mi[j];
            }
        }

        return M;
    }

    // calcola il totale delle azioni simiche (inerzie + sovraspinte)
    public double[] getM2SpinteDianamicheSismaX() {
        double[] M = {0, 0, 0, 0, 0};
        double[] Mi;

        Mi = getM2InerziaCiabatta();
        for (int j = 0; j < 5; ++j) {
            if (j == 0 || j == 4) {
                M[j] += Mi[j];
            }
        }

        Mi = getM2InerziaMuriAla();
        for (int j = 0; j < 5; ++j) {
            if (j == 0 || j == 4) {
                M[j] += Mi[j];
            }
        }
        Mi = getM2InerziaParaghiaia();
        for (int j = 0; j < 5; ++j) {
            if (j == 0 || j == 4) {
                M[j] += Mi[j];
            }
        }
        Mi = getM2Inerziaspalla();
        for (int j = 0; j < 5; ++j) {
            if (j == 0 || j == 4) {
                M[j] += Mi[j];
            }
        }
        Mi = getM2InerziaTerrenoMonte();
        for (int j = 0; j < 5; ++j) {
            if (j == 0 || j == 4) {
                M[j] += Mi[j];
            }
        }

        return M;
    }

    public double[] getM1sovraccarico(boolean sismica) {
        double[] M = {0, 0, 0, 0, 0};
        double[] Mi;
        // sovraccarico
        Mi = getM1SovraccaricoV();
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j];

        }

        // sovraccarico
        Mi = getM1SovraccaricoH(sismica);
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j];

        }
        return M;
    }

    // calcola momenti M1 della combinazione i-esima
    public double[] getM1Combo(int i) {
        double[] M = {0, 0, 0, 0, 0};
        double[] Mi;

        // se il coefficente di combinazione della sovraspinta sismica � diversa
        // da zero
        // si utilizza il coefficiente di spinta statica in condizioni sismiche.
        //	boolean sismica;
//		if (combinazioni[4][i] != 0)
//			sismica = true;
//		else
//			sismica = false;

        // permamenti verticali
        Mi = getM1Permanenti();
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j] * combinazioni[0][i];
        }

        // sovraccarico
        Mi = getM1SovraccaricoV();
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j] * combinazioni[1][i];

        }

        // sovraccarico H statico
        Mi = getM1SovraccaricoH(false);
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j] * combinazioni[1][i] * combinazioni[2][i];

        }

        // spinte orizzontali terreno (statiche)
        Mi = getM1TerrenoMonteH(false);
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j] * combinazioni[2][i];
        }

        // spinte orizzontali terreno (sismiche)
        Mi = getM1TerrenoMonteH(true);
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j] * combinazioni[3][i];
        }

        //  forze inerzia x
        Mi = getM1SpinteDianamicheSismaX();
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j] * combinazioni[4][i];
        }

        // forze inerzia y
        Mi = getM1SpinteDianamicheSismaY();
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j] * combinazioni[5][i];
        }

        // carichi appoggi
        for (int k = 0; k < nCarichi; ++k) {
            // carica la spalla?
            Carico c = (Carico) carichi.get(k);
            if (!c.isAgenteSuElevazioni()) {
                continue;
            }

            Mi = getM1CarichiAppoggio(k);
            for (int j = 0; j < 5; ++j) {
                M[j] += Mi[j] * combinazioni[6 + k][i];
            }
        }

        // azioni su assi principali di inerzia
        double Fx = M[0];
        double Fy = M[1];
        double Mx = M[3];
        double My = M[4];
        M[0] = Fx * Math.cos(alfa) + Fy * Math.sin(alfa);
        M[1] = Fx * Math.sin(alfa) + Fy * Math.cos(alfa);
        M[3] = Mx * Math.cos(alfa) + My * Math.sin(alfa);
        M[4] = Mx * Math.sin(alfa) + My * Math.cos(alfa);

        return M;

    }

    // calcola momenti M2 della combinazione i-esima
    public double[] getM2Combo(int i) {
        double[] M = {0, 0, 0, 0, 0};
        double[] Mi;

        // se il coefficente di combinazione della sovraspinta sismica � diversa
        // da zero
        // si utilizza il coefficiente di spinta statica in condizioni sismiche.
//        boolean sismica;
        //      if (combinazioni[4][i] != 0) {
        //        sismica = true;
        //   } else {
        //      sismica = false;
        //  }

        // permamenti verticali
        Mi = getM2Permanenti();
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j] * combinazioni[0][i];
        }

        // sovraccarico verticale
        Mi = getM2SovraccaricoV();
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j] * combinazioni[1][i];

        }

        // sovraccarico orizzontale statica
        Mi = getM2SovraccaricoH(false);
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j] * combinazioni[1][i] * combinazioni[2][i];
        }

        // spinta terreno statica
        Mi = getM2TerrenoMonteH(false);
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j] * combinazioni[2][i];
        }

        // sovraccarico orizzontale sismica
        Mi = getM2TerrenoMonteH(true);
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j] * combinazioni[3][i];
        }


        // forze inerzia x
        Mi = getM2SpinteDianamicheSismaX();
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j] * combinazioni[4][i];
        }

        // forze inerzia y
        Mi = getM2SpinteDianamicheSismaY();
        for (int j = 0; j < 5; ++j) {
            M[j] += Mi[j] * combinazioni[5][i];
        }

        // carichi appoggi
        for (int k = 0; k < nCarichi; ++k) {
            Mi = getM2CarichiAppoggio(k);
            for (int j = 0; j < 5; ++j) {
                M[j] += Mi[j] * combinazioni[6 + k][i];
            }
        }

        return M;
    }

    // calcola le azioni della combinazione i-esima sugli appoggi
    public double[][] getMappoggiCombo(int i) {
        double[][] M = new double[nAppoggi][3];
        double Fx, Fy, Fz;

        // carichi appoggi
        for (int z = 0; z < nAppoggi; ++z) {
            Fx = 0;
            Fy = 0;
            Fz = 0;
            for (int k = 0; k < nCarichi; ++k) {
                Carico c = (Carico) carichi.get(k);
                if (!c.isAgenteSuAppoggi()) {
                    continue;
                }
                if (c.isAtrito()) {
                    continue;
                }
                // calcola i momenti totali
                Fx += Fappoggi[0][z][k] * combinazioni[6 + k][i];
                Fy += Fappoggi[1][z][k] * combinazioni[6 + k][i];
                Fz += Fappoggi[2][z][k] * combinazioni[6 + k][i];

            }
            // caso carichi attrio
            double Fzperm = getFzPermanentiAppoggioI(z);
            for (int k = 0; k < nCarichi; ++k) {
                Carico c = (Carico) carichi.get(k);
                if (!c.isAtrito()) {
                    continue;
                }
                if (!c.isAgenteSuAppoggi()) {
                    continue;
                }

                Fy += Fzperm * kAtrito * combinazioni[6 + k][i];
            }

            M[z][0] = Fx;
            M[z][1] = Fy;
            M[z][2] = Fz;
        }
        return M;
    }

    private double getFzPermanentiAppoggioI(int z) {

        double Fzperm = 0;
        for (int k = 0; k < nCarichi; ++k) {
            Carico c = (Carico) carichi.get(k);
            if (!c.isAgenteSuAppoggi()) {
                continue;
            }
            if (c.isAtrito()) {
                continue;
            }
            if (!c.isPermanente()) {
                continue;
            }
            // calcola i momenti totali
            Fzperm += Fappoggi[2][z][k];
        }

        return Fzperm;

    }

    // calcola le azioni della combinazione i-esima sugli appoggi
    public double[][] getMappoggiPemanentiCombo(int i) {
        double[][] M = new double[nAppoggi][3];
        double Fx, Fy, Fz;

        // carichi appoggi
        for (int z = 0; z < nAppoggi; ++z) {
            Fx = 0;
            Fy = 0;
            Fz = 0;
            for (int k = 0; k < nCarichi; ++k) {
                Carico c = (Carico) carichi.get(k);
                if (!c.isAgenteSuAppoggi()) {
                    continue;
                }
                if (!c.isPermanente()) {
                    continue;
                }
                if (c.isAtrito()) {
                    continue;
                }
                // calcola i momenti totali
                Fx += Fappoggi[0][z][k] * combinazioni[6 + k][i];
                Fy += Fappoggi[1][z][k] * combinazioni[6 + k][i];
                Fz += Fappoggi[2][z][k] * combinazioni[6 + k][i];
            }
            // caso carichi attrio
            double Fzperm = getFzPermanentiAppoggioI(z);
            for (int k = 0; k < nCarichi; ++k) {
                Carico c = (Carico) carichi.get(k);
                if (!c.isAtrito()) {
                    continue;
                }
                if (!c.isAgenteSuAppoggi()) {
                    continue;
                }
                if (!c.isPermanente()) {
                    continue;
                }

                Fy += Fzperm * kAtrito * combinazioni[6 + k][i];
            }

            M[z][0] = Fx;
            M[z][1] = Fy;
            M[z][2] = Fz;
        }
        return M;

    }

    public double[] getNpalificata(int i) {
        double[] N = new double[nPali];
        double[] M2;
        double x, y, d, A;
        double Jx = 0;
        double Jy = 0;

        double xg, yg;
        double Atot = 0;
        double Ax = 0;
        double Ay = 0;

        // calcola il baricentro della palificata
        for (int j = 0; j < nPali; ++j) {
            x = pali[0][j];
            y = pali[1][j];
            d = pali[2][j];
            A = d * d / 4 * Math.PI;
            double ax = A * x;
            double ay = A * y;
            Ax += ax;
            Ay += ay;
            Atot += A;
        }
        xg = Ax / Atot;
        yg = Ay / Atot;

        // calcola le sollecitazioni sul baricentro palificata
        M2 = getM2Combo(i);
        M2[3] = M2[3] + yg * M2[2];
        M2[4] = M2[4] - xg * M2[2];

        // calcola i momenti sui pali
        double mxp = -M2[1] * kpali;
        double myp = M2[0] * kpali;

        // calcola Jx,Jy
        for (int j = 0; j < nPali; ++j) {
            x = pali[0][j] - xg;
            y = pali[1][j] - yg;
            d = pali[2][j];
            A = d * d / 4 * Math.PI;
            double Jiy = A * x * x + Math.PI * d * d * d * d / 64;
            double Jix = A * y * y + Math.PI * d * d * d * d / 64;
            Jx += Jix;
            Jy += Jiy;
        }

        // calcola le sollecitazioni
        for (int j = 0; j < nPali; ++j) {
            x = pali[0][j] - xg;
            y = pali[1][j] - yg;
            d = pali[2][j];
            A = d * d / 4 * Math.PI;
            N[j] = M2[2] / nPali - A * y * (M2[3] + mxp) / Jx + A * x * (M2[4] + myp) / Jy;
        }
        return N;
    }

    // calcola i momenti per la verifica della ciabatta
    public double[][] getMverifica(int i) {

        if (nPali != 0) {
            return getMverificaPalificata(i);
        } else {
            return getMverificaFondaSuperficiale(i);
        }

    }

    private double[][] getMverificaFondaSuperficiale(int i) {

        double[] var = get_sigma_wx_wy(i);
        double s1 = var[0];
        double w = var[1];
        double v = var[2];
        double[] M2 = getM2Combo(i);
        double Mx = M2[3];
        double My = M2[4];
        double[][] M = new double[4][2];

        double dx, dy;

        if (Mx < 0) {
            dy = ByCiabatta / 2 - dySpalla - yVerifica;
        } else {
            dy = ByCiabatta / 2 + dySpalla - yVerifica;
        }

        if (My > 0) {
            dx = BxCiabatta / 2 - dxSpalla - xVerifica;
        } else {
            dx = BxCiabatta / 2 + dxSpalla - xVerifica;
        }

        if (dy > v) {
            M[0][0] = 1 / 2 * v * s1 * (dy - v / 3);
            M[1][0] = 1 / 2 * v * s1;
        } else {
            double s2 = s1 * (1 - dy / v);
            M[0][0] = s1 * dy * dy / 2 + (s1 - s2) * dy * dy / 3;
            M[1][0] = s1 * dy + 1 / 2 * (s1 - s2) * dy;
        }

        if (dx > w) {
            M[0][1] = 1 / 2 * w * s1 * (dx - w / 3);
            M[1][1] = 1 / 2 * w * s1;
        } else {
            double s2 = s1 * (1 - dx / w);
            M[0][0] = s1 * dx * dx / 2 + (s1 - s2) * dx * dx / 3;
            M[1][0] = s1 * dx + 1 / 2 * (s1 - s2) * dx;
        }

        M[2][0] = 0;
        M[3][0] = 0;
        M[2][1] = 0;
        M[3][1] = 0;
        return M;
    }

    public double[] getM_V_SingoloPalo(int i) {
        double[] M = {0, 0};

        double[] M2 = getM2Combo(i);
        double Vxp = M2[0] / nPali;
        double Vyp = M2[1] / nPali;
        double Vp = Math.pow(Vxp * Vxp + Vyp * Vyp, 0.5);
        double Mp = Vp * kpali;
        M[1] = Vp;
        M[0] = Mp;
        return M;

    }

    // calcola i momenti per la verifica della ciabatta
    // per palificate
    private double[][] getMverificaPalificata(int i) {

        double[] Npali = getNpalificata(i);
        double bincastro;
        double M[][] = new double[4][2];

        double[] M2 = getM2Combo(i);
        // momento Mx sul singolo palo
        double Mxp = -M2[1] / nPali * kpali;

        // Sollecitazione sezione di incastro monte
        // momento provocato dai pali

        double yv = (dySpalla - BySpalla / 2);

        for (int j = 0; j < nPali; ++j) {
            double d = pali[1][j] - (yv + Math.tan(alfa) * pali[0][j]);
            bincastro = -d * Math.cos(alfa);

            if (bincastro > 0) {
                M[0][0] += Npali[j] * (bincastro) - Mxp;
                M[1][0] += Npali[j];
            }
        }

        double L = ByCiabatta / 2 + dySpalla - BySpalla / 2;

        // momento provocato dal terreno sopra
        double Mt[] = getM2TerrenoMonteV();
        M[0][0] += -Mt[2] * L / 2 * Math.cos(alfa) * combinazioni[0][i];
        M[1][0] += -Mt[2] * combinazioni[0][i];

        // momento provocato dal peso della ciabatta
        double q = Spf * gammaCls;
        M[0][0] += -q * L * L / 2 * BxCiabatta * Math.cos(alfa) * combinazioni[0][i];
        M[1][0] += -q * L * BxCiabatta * combinazioni[0][i];

        // momento provocato dal sovraccarico
        double Mq[] = getM2SovraccaricoV();
        M[0][0] += -Mq[2] * L / 2 * Math.cos(alfa) * combinazioni[1][i];
        M[1][0] += -Mq[2] * combinazioni[0][i];

        // momento provocato dai muri d'ala
        double Ma[] = getM2MuriAla();
        M[0][0] += -Ma[2] * BmuriAla / 2 * Math.cos(alfa) * combinazioni[0][i];
        M[1][0] += -Ma[2] * combinazioni[0][i];

        M[0][1] = M[0][0] / (BxCiabatta / Math.cos(alfa));
        M[1][1] = M[1][0] / (BxCiabatta / Math.cos(alfa));

        // SOLLECITAZIONI SEZIONE D'INCASTRO A VALLE
        // momento provocato dai pali
        yv = (dySpalla + BySpalla / 2);

        for (int j = 0; j < nPali; ++j) {
            double d = -pali[1][j] + (yv + Math.tan(alfa) * pali[0][j]);
            bincastro = -d * Math.cos(alfa);

            if (bincastro > 0) {
                M[2][0] += Npali[j] * (bincastro) + Mxp;
                M[3][0] += Npali[j];
            }
        }

        L = ByCiabatta / 2 - dySpalla - BySpalla / 2;

        // momento provocato dal terreno sopra a valle
        double Mtv[] = getM2TerrenoValle();
        M[2][0] += -Mtv[2] * L / 2 * Math.cos(alfa) * combinazioni[0][i];
        M[3][0] += -Mtv[2] * combinazioni[0][i];

        // momento provocato dal peso della ciabatta
        M[2][0] += -q * L * L / 2 * BxCiabatta * Math.cos(alfa) * combinazioni[0][i];
        M[3][0] += -q * L * BxCiabatta * combinazioni[0][i];

        M[2][1] = M[2][0] / (BxCiabatta / Math.cos(alfa));
        M[3][1] = M[3][0] / (BxCiabatta / Math.cos(alfa));
        return M;
    }

    // calcola pressione terreno
    public double[] get_sigma_wx_wy(int i) {
        double v[] = {0, 0, 0};

        double[] Mi = getM2Combo(i);
        double N = Mi[2];
        double Mx = Mi[3];
        double My = Mi[4];
        double wx, wy;

        double ex = Math.abs(My / N);
        double ey = Math.abs(Mx / N);

        wx = BxCiabatta / 2 - ex;
        wy = ByCiabatta / 2 - ey;

        if (ex == 0) {
            if (ey > ByCiabatta / 6) {
                v[1] = Double.MAX_VALUE;
                v[2] = (ByCiabatta / 2 - ey) * 3;
                v[0] = 2 * N / (3 * v[2] * BxCiabatta);
            } else {
                v[0] = N / (ByCiabatta * BxCiabatta) * (1 + 6 * ey / ByCiabatta);
                v[1] = Double.MAX_VALUE;
                v[2] = ByCiabatta * ByCiabatta / (12 * ey);
            }

            return v;
        }

        if (ey == 0) {
            if (ex > BxCiabatta / 6) {
                v[2] = Double.MAX_VALUE;
                v[1] = (BxCiabatta / 2 - ex) * 3;
                v[0] = 2 * N / (3 * v[1] * ByCiabatta);
            } else {
                v[0] = N / (BxCiabatta * ByCiabatta) * (1 + 6 * ex / BxCiabatta);
                v[2] = Double.MAX_VALUE;
                v[1] = BxCiabatta * BxCiabatta / (12 * ex);
            }
            return v;

        }
        if (ex / BxCiabatta + ey / ByCiabatta <= .16666667) {
            double s1 = N / (BxCiabatta * ByCiabatta) * (1 + 6 * ey / ByCiabatta + 6 * ex / BxCiabatta);
            double s2 = N / (BxCiabatta * ByCiabatta) * (1 + 6 * ey / ByCiabatta - 6 * ex / BxCiabatta);
            double s3 = N / (BxCiabatta * ByCiabatta) * (1 - 6 * ey / ByCiabatta + 6 * ex / BxCiabatta);

            v[0] = s1;
            v[1] = s1 / (s1 - s2) * BxCiabatta;
            v[2] = s1 / (s1 - s3) * ByCiabatta;

            return v;
        }

        if (ex / BxCiabatta >= .5 || ey / ByCiabatta >= .5) {
            return (new double[]{0, 0, 0});
        } else {

            FondazioneSuperficiale F = new FondazioneSuperficiale(BxCiabatta,
                    BxCiabatta, new double[]{wx, wy});
            try {
                double[] v2 = new double[2];

                v2 = Matematica.ZeriFunzioni_MetodoNewton(F, new double[]{1,
                    1
                }, 2, 10000, 0.001);

                double V = F.getV(v2);
                v[0] = N / V;
                v[1] = v2[0];
                v[2] = v2[1];

                return v;

            } catch (Exception ecc) {

            }

            return v;
        }

    }

    // restituisce il poligono di pressioni sul terreno
    public Polygon getPoligonoPressioni(int combo) {

        Polygon p = new Polygon();
        double[] var = get_sigma_wx_wy(combo);
        int w = (int) var[1] / 10;
        int v = (int) var[2] / 10;
        double[] M = getM2Combo(combo);
        int a = (int) BxCiabatta / 10;
        int b = (int) ByCiabatta / 10;
        int[] x1 = new int[2];
        int[] x2 = new int[2];
        int[] x3 = new int[2];
        int[] x4 = new int[2];
        int[] x5 = new int[2];

        x1[0] = a / 2;
        x1[1] = -b / 2;
        if (w > a) {
            x2[0] = -a / 2;
            x2[1] = -b / 2;
            int vv;
            if (w == Double.MAX_VALUE) {
                vv = (int) v;
            } else {
                vv = (int) Math.min(v - a * v / w, b);
            }
            x3[0] = -a / 2;
            x3[1] = -b / 2 + vv;
        } else {
            x2[0] = x3[0] = a / 2 - w;
            x2[1] = x3[1] = -b / 2;
        }
        if (v > b) {
            x5[0] = a / 2;
            x5[1] = b / 2;
            int vv;
            if (v == Double.MAX_VALUE) {
                vv = (int) w;
            } else {
                vv = (int) Math.min(w - b * w / v, a);
            }
            x4[0] = a / 2 - vv;
            x4[1] = b / 2;
        } else {
            x4[0] = x5[0] = a / 2;
            x4[1] = x5[1] = -b / 2 + v;
        }

        if (M[3] <= 0 & M[4] >= 0) {
            p.addPoint(x1[0], x1[1]);
            p.addPoint(x2[0], x2[1]);
            if (x3[0] != x2[0] || x3[1] != x2[1]) {
                p.addPoint(x3[0], x3[1]);
            }
            p.addPoint(x4[0], x4[1]);
            if (x5[0] != x4[0] || x5[1] != x4[1]) {
                p.addPoint(x5[0], x5[1]);
            }
            return p;
        }
        if (M[3] >= 0 & M[4] >= 0) {
            p.addPoint(x1[0], -x1[1]);
            p.addPoint(x2[0], -x2[1]);
            if (x3[0] != x2[0] || x3[1] != x2[1]) {
                p.addPoint(x3[0], -x3[1]);
            }
            p.addPoint(x4[0], -x4[1]);
            if (x5[0] != x4[0] || x5[1] != x4[1]) {
                p.addPoint(x5[0], -x5[1]);
            }
            return p;
        }
        if (M[3] >= 0 & M[4] <= 0) {
            p.addPoint(-x1[0], -x1[1]);
            p.addPoint(-x2[0], -x2[1]);
            if (x3[0] != x2[0] || x3[1] != x2[1]) {
                p.addPoint(-x3[0], -x3[1]);
            }
            p.addPoint(-x4[0], -x4[1]);
            if (x5[0] != x4[0] || x5[1] != x4[1]) {
                p.addPoint(-x5[0], -x5[1]);
            }
            return p;
        }
        if (M[3] <= 0 & M[4] <= 0) {
            p.addPoint(-x1[0], x1[1]);
            p.addPoint(-x2[0], x2[1]);
            if (x3[0] != x2[0] || x3[1] != x2[1]) {
                p.addPoint(-x3[0], x3[1]);
            }
            p.addPoint(-x4[0], x4[1]);
            if (x5[0] != x4[0] || x5[1] != x4[1]) {
                p.addPoint(-x5[0], x5[1]);
            }
            return p;
        }

        return p;
    }
    
    public double getInterpolazion(double x1, double x2, double y1, double y2, double xVal){
        
        double Dx = (x2-x1);
        double Dy = (y2-y1);
        double dy = (Dy/Dx)*(xVal-x1);
        
    return dy+y1;
    } 
}
