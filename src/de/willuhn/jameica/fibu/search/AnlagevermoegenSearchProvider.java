/**********************************************************************
 *
 * Copyright (c) 2004 Olaf Willuhn
 * All rights reserved.
 * 
 * This software is copyrighted work licensed under the terms of the
 * Jameica License.  Please consult the file "LICENSE" for details. 
 *
 **********************************************************************/

package de.willuhn.jameica.fibu.search;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import de.willuhn.datasource.rmi.DBIterator;
import de.willuhn.jameica.fibu.Fibu;
import de.willuhn.jameica.fibu.Settings;
import de.willuhn.jameica.fibu.gui.action.AnlagevermoegenNeu;
import de.willuhn.jameica.fibu.rmi.Anlagevermoegen;
import de.willuhn.jameica.fibu.rmi.Geschaeftsjahr;
import de.willuhn.jameica.gui.formatter.CurrencyFormatter;
import de.willuhn.jameica.search.Result;
import de.willuhn.jameica.search.SearchProvider;
import de.willuhn.jameica.system.Application;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;
import de.willuhn.util.I18N;

/**
 * Suchprovider fuer Anlagegegenstaende.
 */
public class AnlagevermoegenSearchProvider implements SearchProvider
{
  private Geschaeftsjahr jahr = null;

  /**
   * @see de.willuhn.jameica.search.SearchProvider#getName()
   */
  public String getName()
  {
    return Application.getPluginLoader().getPlugin(Fibu.class).getResources().getI18N().tr("Anlagevermögen");
  }

  /**
   * @see de.willuhn.jameica.search.SearchProvider#search(java.lang.String)
   */
  public List search(String search) throws RemoteException,
      ApplicationException
  {
    if (search == null || search.length() == 0)
      return null;

    this.jahr = Settings.getActiveGeschaeftsjahr();
    if (this.jahr == null)
      return null; // Kein Geschaeftsjahr aktiv

    String text = "%" + search.toLowerCase() + "%";
    DBIterator list = this.jahr.getMandant().getAnlagevermoegen();
    list.setOrder("order by anschaffungsdatum desc");
    list.addFilter("LOWER(name) LIKE ?",text);

    ArrayList results = new ArrayList();
    while (list.hasNext())
    {
      results.add(new MyResult((Anlagevermoegen)list.next()));
    }
    return results;
  }
  
  /**
   * Hilfsklasse fuer die formatierte Anzeige der Ergebnisse.
   */
  private class MyResult implements Result
  {
    private Anlagevermoegen av = null;
    
    /**
     * ct.
     * @param av
     */
    private MyResult(Anlagevermoegen av)
    {
      this.av = av;
    }

    /**
     * @see de.willuhn.jameica.search.Result#execute()
     */
    public void execute() throws RemoteException, ApplicationException
    {
      new AnlagevermoegenNeu().handleAction(this.av);
    }

    /**
     * @see de.willuhn.jameica.search.Result#getName()
     */
    public String getName()
    {
      try
      {
        I18N i18n = Application.getPluginLoader().getPlugin(Fibu.class).getResources().getI18N();

        return i18n.tr("{0}: {1} - Restwert {2}", new String[]{Settings.DATEFORMAT.format(av.getAnschaffungsdatum()),
                                                               av.getName(),
                                                               new CurrencyFormatter(jahr.getMandant().getWaehrung(),Settings.DECIMALFORMAT).format(new Double(av.getRestwert(jahr)))});
      }
      catch (RemoteException re)
      {
        Logger.error("unable to determin result name",re);
        return null;
      }
    }
    
  }
}


/*********************************************************************
 * $Log: AnlagevermoegenSearchProvider.java,v $
 * Revision 1.4  2011/08/08 10:44:35  willuhn
 * @C compiler warnings
 *
 * Revision 1.3  2010-06-01 16:37:22  willuhn
 * @C Konstanten von Fibu zu Settings verschoben
 * @N Systemkontenrahmen nach expliziter Freigabe in den Einstellungen aenderbar
 * @C Unterscheidung zwischen canChange und isUserObject in UserObject
 * @C Code-Cleanup
 * @R alte CVS-Logs entfernt
 *
 * Revision 1.2  2009/07/03 10:52:19  willuhn
 * @N Merged SYNTAX_1_3_BRANCH into HEAD
 *
 * Revision 1.1.2.1  2008/09/04 10:02:13  willuhn
 * @N Suchprovider
 *
 **********************************************************************/