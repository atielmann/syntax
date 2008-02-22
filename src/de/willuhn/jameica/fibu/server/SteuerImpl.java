/**********************************************************************
 * $Source: /cvsroot/syntax/syntax/src/de/willuhn/jameica/fibu/server/SteuerImpl.java,v $
 * $Revision: 1.15 $
 * $Date: 2008/02/22 10:41:41 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/
package de.willuhn.jameica.fibu.server;

import java.rmi.RemoteException;

import de.willuhn.datasource.rmi.DBIterator;
import de.willuhn.jameica.fibu.Settings;
import de.willuhn.jameica.fibu.rmi.Konto;
import de.willuhn.jameica.fibu.rmi.Steuer;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;

/**
 * @author willuhn
 */
public class SteuerImpl extends AbstractKontenrahmenObjectImpl implements Steuer
{
  /**
   * Erzeugt einen neuen Steuersatz.
   * @throws RemoteException
   */
  public SteuerImpl() throws RemoteException
  {
    super();
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#getTableName()
   */
  protected String getTableName()
  {
    return "steuer";
  }

  /**
   * @see de.willuhn.datasource.GenericObject#getPrimaryAttribute()
   */
  public String getPrimaryAttribute() throws RemoteException
  {
    return "name";
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Steuer#getName()
   */
  public String getName() throws RemoteException
  {
    return (String) getAttribute("name");
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Steuer#getSatz()
   */
  public double getSatz() throws RemoteException
  {
    Double d = (Double) getAttribute("satz");
    if (d != null)
      return d.doubleValue();

    return 0;
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Steuer#setName(java.lang.String)
   */
  public void setName(String name) throws RemoteException
  {
    setAttribute("name", name);
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Steuer#setSatz(double)
   */
  public void setSatz(double satz) throws RemoteException
  {
    setAttribute("satz", new Double(satz));
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#deleteCheck()
   */
  protected void deleteCheck() throws ApplicationException
  {
    super.deleteCheck();
    try {

      // wir checken ob vielleicht ein Konto diesen Steuersatz besitzt.
      DBIterator list = Settings.getDBService().createList(Konto.class);
      list.addFilter("steuer_id = " + this.getID());
      if (list.hasNext())
        throw new ApplicationException(i18n.tr("Der Steuersatz ist einem Konto zugewiesen.\n" +
          "Bitte �ndern oder l�schen zu Sie zun�chst das Konto."));
    }
    catch (RemoteException e)
    {
			Logger.error("error while checking dependencies",e);
      throw new ApplicationException(i18n.tr("Fehler beim Pr�fen der Abh�ngigkeiten."));
    }
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#insertCheck()
   */
  protected void insertCheck() throws ApplicationException
  {
    super.insertCheck();
    try {
      if (getName() == null || "".equals(getName()))
        throw new ApplicationException(i18n.tr("Bitte geben Sie eine Bezeichnung f�r den Steuersatz ein."));
      
      if (getSatz() < 0)
        throw new ApplicationException(i18n.tr("Steuersatz darf nicht kleiner als 0 sein."));
    }
    catch (RemoteException e)
    {
      Logger.error("error while checking dependencies",e);
      throw new ApplicationException(i18n.tr("Fehler bei der Pr�fung der Pflichtfelder."),e);
    }
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Steuer#getKonto()
   */
  public Konto getKonto() throws RemoteException
  {
    return findKonto((String) getAttribute("konto"));
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Steuer#setKonto(de.willuhn.jameica.fibu.rmi.Konto)
   */
  public void setKonto(Konto k) throws RemoteException
  {
    setAttribute("konto",k == null ? null : k.getKontonummer());
  }
}

/*********************************************************************
 * $Log: SteuerImpl.java,v $
 * Revision 1.15  2008/02/22 10:41:41  willuhn
 * @N Erweiterte Mandantenfaehigkeit (IN PROGRESS!)
 *
 * Revision 1.14  2006/01/02 15:18:29  willuhn
 * @N Buchungs-Vorlagen
 *
 * Revision 1.13  2005/10/18 23:28:55  willuhn
 * @N client/server tauglichkeit
 *
 * Revision 1.12  2005/10/05 17:52:33  willuhn
 * @N steuer behaviour
 *
 * Revision 1.11  2005/08/22 16:37:22  willuhn
 * @N Anfangsbestaende
 *
 * Revision 1.10  2005/08/10 17:48:02  willuhn
 * @C refactoring
 *
 * Revision 1.9  2005/08/08 22:54:16  willuhn
 * @N massive refactoring
 *
 * Revision 1.8  2005/08/08 21:35:46  willuhn
 * @N massive refactoring
 *
 * Revision 1.7  2004/01/25 19:44:03  willuhn
 * *** empty log message ***
 *
 * Revision 1.6  2004/01/03 18:07:22  willuhn
 * @N Exception logging
 *
 * Revision 1.5  2003/12/15 19:08:04  willuhn
 * *** empty log message ***
 *
 * Revision 1.4  2003/12/12 21:11:27  willuhn
 * @N ObjectMetaCache
 *
 * Revision 1.3  2003/12/11 21:00:34  willuhn
 * @C refactoring
 *
 * Revision 1.2  2003/12/10 23:51:52  willuhn
 * *** empty log message ***
 *
 * Revision 1.1  2003/12/01 20:29:00  willuhn
 * @B filter in DBIteratorImpl
 * @N InputFelder generalisiert
 *
 **********************************************************************/