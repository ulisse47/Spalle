package progetto.model.util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class SistemaDiFunzioni {
  public SistemaDiFunzioni() {
  }

  public abstract double[] getValori (double[] x) throws Exception;

  public abstract double getNorma(double[] x) throws Exception;

  public abstract double getFi(double[] x,int n) throws Exception;

}
