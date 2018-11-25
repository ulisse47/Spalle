/*
 * Created on 3-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.model.bean;

import java.util.ArrayList;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Verticale {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    private double Nc;
    private double Nq;

    public double getNc() {
        return Nc;
    }

    public void setNc(double Nc) {
        this.Nc = Nc;
    }

    public double getNq() {
        return Nq;
    }

    public void setNq(double Nq) {
        this.Nq = Nq;
    }

    public ArrayList getStrati() {
        return strati;
    }

    public void setStrati(ArrayList strati) {
        this.strati = strati;
    }
    
    ArrayList strati = new ArrayList();
            
      
    /**
     * 
     */
    public Verticale(double Nc,double Nq, ArrayList strati) {
        this.Nc = Nc;
        this.Nq = Nq;
        this.strati = strati;
        if (strati.size()==0){
            this.strati.add(new Terreno());
        }
 
    }

    /**
     * 
     */
    public Verticale(String name) {
        this.name = name;   
        Nc = 9;
        Nq = 25;
        strati.add(new Terreno());
        
        
        
    }
    
     public Verticale() {
        this.name = "Vericale";   
        Nc = 9;
        Nq = 25;
        strati.add(new Terreno());
      
        
    }
	
    public String toString() {
		return name;
	}
    
}
