/**********************************************************************
 *
 * Copyright (c) 2004 Olaf Willuhn
 * All rights reserved.
 * 
 * This software is copyrighted work licensed under the terms of the
 * Jameica License.  Please consult the file "LICENSE" for details. 
 *
 **********************************************************************/
package de.willuhn.jameica.fibu.server;

import java.rmi.RemoteException;

import de.willuhn.datasource.db.AbstractDBObject;
import de.willuhn.jameica.fibu.Fibu;
import de.willuhn.jameica.fibu.Settings;
import de.willuhn.jameica.fibu.rmi.Konto;
import de.willuhn.jameica.fibu.rmi.Transfer;
import de.willuhn.jameica.system.Application;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;
import de.willuhn.util.I18N;

/**
 * @author willuhn
 */
public abstract class AbstractTransferImpl extends AbstractDBObject implements Transfer
{
  final static transient I18N i18n = Application.getPluginLoader().getPlugin(Fibu.class).getResources().getI18N();
  
  /**
   * Erzeugt einen neuen Transfer.
   * @throws RemoteException
   */
  public AbstractTransferImpl() throws RemoteException
  {
    super();
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Transfer#getSollKonto()
   */
  public Konto getSollKonto() throws RemoteException
  {
    return (Konto) getAttribute("sollkonto_id");
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Transfer#getHabenKonto()
   */
  public Konto getHabenKonto() throws RemoteException
  {
    return (Konto) getAttribute("habenkonto_id");
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Transfer#getText()
   */
  public String getText() throws RemoteException
  {
    return (String) getAttribute("buchungstext");
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Transfer#getBetrag()
   */
  public double getBetrag() throws RemoteException
  {
    Double d = (Double) getAttribute("betrag");
    if (d == null)
      return 0;

    return d.doubleValue();
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Transfer#getSteuer()
   */
  public double getSteuer() throws RemoteException
  {
    Double d = (Double) getAttribute("steuer");
    if (d != null)
      return d.doubleValue();

    return 0;
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Transfer#setSollKonto(de.willuhn.jameica.fibu.rmi.Konto)
   */
  public void setSollKonto(Konto k) throws RemoteException
  {
    setAttribute("sollkonto_id",k);
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Transfer#setHabenKonto(de.willuhn.jameica.fibu.rmi.Konto)
   */
  public void setHabenKonto(Konto k) throws RemoteException
  {
    setAttribute("habenkonto_id",k);
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Transfer#setText(java.lang.String)
   */
  public void setText(String text) throws RemoteException
  {
    setAttribute("buchungstext", text);
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Transfer#setBetrag(double)
   */
  public void setBetrag(double betrag) throws RemoteException
  {
    setAttribute("betrag", new Double(betrag));
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Transfer#setSteuer(double)
   */
  public void setSteuer(double steuer) throws RemoteException
  {
    setAttribute("steuer", new Double(steuer));
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#getForeignObject(java.lang.String)
   */
  protected Class getForeignObject(String field) throws RemoteException
  {
    if ("sollkonto_id".equals(field))
      return Konto.class;

    if ("habenkonto_id".equals(field))
      return Konto.class;

    return null;
  }

  /**
   * @see de.willuhn.datasource.rmi.Changeable#store()
   */
  public void store() throws RemoteException, ApplicationException
  {
    super.store();
    
    // Die NULL-Checks sind primaer nur fuer Buchungsvorlagen - dort duerfen sie NULL sein.
    Konto soll = getSollKonto();
    Konto haben = getHabenKonto();
    if (soll != null && haben != null)
      Logger.info(soll.getKontonummer() + " an " + haben.getKontonummer() + ": " + Settings.DECIMALFORMAT.format(getBetrag()) + " (" + getText() + ")");
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Transfer#isGeprueft()
   */
  public boolean isGeprueft() throws RemoteException
  {
    Integer i = (Integer) getAttribute("geprueft");
    return i != null && i.intValue() == 1;
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.Transfer#setGeprueft(boolean)
   */
  public void setGeprueft(boolean b) throws RemoteException
  {
    setAttribute("geprueft",new Integer(b ? 1 : 0));
  }


}

/*********************************************************************
 * $Log: AbstractTransferImpl.java,v $
 * Revision 1.9  2011/05/12 09:10:31  willuhn
 * @R Back-Buttons entfernt
 * @C GUI-Cleanup
 *
 * Revision 1.8  2010-08-30 16:41:01  willuhn
 * @N Klartextbezeichnung bei Import/Export
 *
 * Revision 1.7  2010/06/03 14:26:16  willuhn
 * @N Extension zum Zuordnen von Hibiscus-Kategorien zu SynTAX-Buchungsvorlagen
 * @C Code-Cleanup
 *
 * Revision 1.6  2010/06/01 16:37:22  willuhn
 * @C Konstanten von Fibu zu Settings verschoben
 * @N Systemkontenrahmen nach expliziter Freigabe in den Einstellungen aenderbar
 * @C Unterscheidung zwischen canChange und isUserObject in UserObject
 * @C Code-Cleanup
 * @R alte CVS-Logs entfernt
 *
 * Revision 1.5  2009/07/03 10:52:19  willuhn
 * @N Merged SYNTAX_1_3_BRANCH into HEAD
 *
 * Revision 1.3  2006/05/29 23:05:07  willuhn
 * *** empty log message ***
 *
 * Revision 1.2  2006/05/29 17:30:26  willuhn
 * @N a lot of debugging
 *
 * Revision 1.1  2006/01/02 15:18:29  willuhn
 * @N Buchungs-Vorlagen
 *
 **********************************************************************/