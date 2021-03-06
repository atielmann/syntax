/**********************************************************************
 *
 * Copyright (c) 2004 Olaf Willuhn
 * All rights reserved.
 * 
 * This software is copyrighted work licensed under the terms of the
 * Jameica License.  Please consult the file "LICENSE" for details. 
 *
 **********************************************************************/

package de.willuhn.jameica.fibu.rmi;

import java.rmi.RemoteException;

/**
 * Interface fuer Buchungs-Templates.
 */
public interface Buchungstemplate extends Transfer
{
  
  /**
   * Liefert den Namen des Templates.
   * @return Name des Templates.
   * @throws RemoteException
   */
  public String getName() throws RemoteException;
  
  /**
   * Speichert den Namen des Templates.
   * @param name Name des Templates.
   * @throws RemoteException
   */
  public void setName(String name) throws RemoteException;
  
  /**
   * Liefert den Mandanten, fuer den das Buchungstemplate gilt. Kann <code>null</code> sein.
   * @return Mandant.
   * @throws RemoteException
   */
  public Mandant getMandant() throws RemoteException;

  /**
   * Speichert den Mandanten, fuer den das Template gilt.
   * @param m Mandant.
   * @throws RemoteException
   */
  public void setMandant(Mandant m) throws RemoteException;

  /**
   * Liefert den Kontenrahmen. Kann <code>null</code> sein.
   * @return Kontenrahmen.
   * @throws RemoteException
   */
  public Kontenrahmen getKontenrahmen() throws RemoteException;
  
  /**
   * Speichert den Kontenrahmen.
   * @param kr Kontenrahmen.
   * @throws RemoteException
   */
  public void setKontenrahmen(Kontenrahmen kr) throws RemoteException;
  
  /**
   * Liefert die optionale ID der zugeordneten Umsatzkategorie aus Hibiscus.
   * @return ID der zugeordneten Umsatzkategorie aus Hibiscus.
   * @throws RemoteException
   */
  public String getHibiscusUmsatzTypID() throws RemoteException;
  
  /**
   * Speichert die optionale ID der zugeordneten Umsatzkategorie aus Hibiscus.
   * @param id die ID der zugeordneten Umsatzkategorie aus Hibiscus.
   * @throws RemoteException
   */
  public void setHibiscusUmsatzTypID(String id) throws RemoteException;
  
}


/*********************************************************************
 * $Log: Buchungstemplate.java,v $
 * Revision 1.5  2010/06/03 14:26:16  willuhn
 * @N Extension zum Zuordnen von Hibiscus-Kategorien zu SynTAX-Buchungsvorlagen
 * @C Code-Cleanup
 *
 * Revision 1.4  2009/07/03 10:52:19  willuhn
 * @N Merged SYNTAX_1_3_BRANCH into HEAD
 *
 * Revision 1.2  2006/01/03 17:55:53  willuhn
 * @N a lot more checks
 * @B NPEs
 * @N BuchungsTemplates pro Mandant/Kontenrahmen
 * @N Default-Geschaeftsjahr in init.sql verschoben
 * @N Handling von Eingabe von Altbestaenden im AV
 *
 * Revision 1.1  2006/01/02 15:18:29  willuhn
 * @N Buchungs-Vorlagen
 *
 **********************************************************************/