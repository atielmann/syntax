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
import de.willuhn.jameica.fibu.rmi.DBProperty;

/**
 * Speichert ein einzelnes Property in der Datenbank.
 */
public class DBPropertyImpl extends AbstractDBObject implements DBProperty
{

  /**
   * ct
   * @throws RemoteException
   */
  public DBPropertyImpl() throws RemoteException
  {
    super();
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#getPrimaryAttribute()
   */
  public String getPrimaryAttribute() throws RemoteException
  {
    return "name";
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#getTableName()
   */
  protected String getTableName()
  {
    return "property";
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.DBProperty#getName()
   */
  public String getName() throws RemoteException
  {
    return (String) getAttribute("name");
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.DBProperty#getValue()
   */
  public String getValue() throws RemoteException
  {
    return (String) getAttribute("content");
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.DBProperty#setName(java.lang.String)
   */
  public void setName(String name) throws RemoteException
  {
    setAttribute("name",name);
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.DBProperty#setValue(java.lang.String)
   */
  public void setValue(String value) throws RemoteException
  {
    setAttribute("content",value);
  }

}


/*********************************************************************
 * $Log: DBPropertyImpl.java,v $
 * Revision 1.3  2010/06/03 10:25:47  willuhn
 * *** empty log message ***
 *
 * Revision 1.2  2010/06/03 10:25:09  willuhn
 * @B wrong import
 *
 * Revision 1.1  2010/06/02 15:52:34  willuhn
 * @N DBProperties jetzt auch in SynTAX
 *
 **********************************************************************/