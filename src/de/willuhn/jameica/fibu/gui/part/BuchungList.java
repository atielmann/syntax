/**********************************************************************
 * $Source: /cvsroot/syntax/syntax/src/de/willuhn/jameica/fibu/gui/part/BuchungList.java,v $
 * $Revision: 1.2 $
 * $Date: 2005/08/09 23:53:34 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.fibu.gui.part;

import java.rmi.RemoteException;

import de.willuhn.datasource.GenericIterator;
import de.willuhn.datasource.rmi.DBIterator;
import de.willuhn.jameica.fibu.Fibu;
import de.willuhn.jameica.fibu.Settings;
import de.willuhn.jameica.fibu.rmi.Buchung;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.formatter.CurrencyFormatter;
import de.willuhn.jameica.gui.formatter.DateFormatter;
import de.willuhn.jameica.gui.parts.TablePart;
import de.willuhn.jameica.system.Application;
import de.willuhn.util.I18N;

/**
 */
public class BuchungList extends TablePart
{

  /**
   * @param action
   * @throws RemoteException
   */
  public BuchungList(Action action) throws RemoteException
  {
    super(init(), action);
    I18N i18n = Application.getPluginLoader().getPlugin(Fibu.class).getResources().getI18N();
    addColumn(i18n.tr("Datum"),"datum", new DateFormatter(Fibu.DATEFORMAT));
    addColumn(i18n.tr("Konto"),"konto_id");
    addColumn(i18n.tr("Geldkonto"),"geldkonto_id");
    addColumn(i18n.tr("Text"),"buchungstext");
    addColumn(i18n.tr("Beleg"),"belegnummer");
    addColumn(i18n.tr("Netto-Betrag"),"betrag",new CurrencyFormatter(Settings.getActiveMandant().getWaehrung(), Fibu.DECIMALFORMAT));
  }

  private static GenericIterator init() throws RemoteException
  {
    DBIterator list = Settings.getDBService().createList(Buchung.class);
    list.setOrder("order by id desc");
    return list;
  }
}


/*********************************************************************
 * $Log: BuchungList.java,v $
 * Revision 1.2  2005/08/09 23:53:34  willuhn
 * @N massive refactoring
 *
 * Revision 1.1  2005/08/08 22:54:15  willuhn
 * @N massive refactoring
 *
 **********************************************************************/