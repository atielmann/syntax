/**********************************************************************
 * $Source: /cvsroot/syntax/syntax/src/de/willuhn/jameica/fibu/gui/menus/GeschaeftsjahrListMenu.java,v $
 * $Revision: 1.3 $
 * $Date: 2005/08/29 21:37:02 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.fibu.gui.menus;

import de.willuhn.jameica.fibu.Fibu;
import de.willuhn.jameica.fibu.Settings;
import de.willuhn.jameica.fibu.gui.action.GeschaeftsjahrClose;
import de.willuhn.jameica.fibu.gui.action.GeschaeftsjahrDelete;
import de.willuhn.jameica.fibu.gui.action.GeschaeftsjahrNeu;
import de.willuhn.jameica.fibu.rmi.Geschaeftsjahr;
import de.willuhn.jameica.fibu.rmi.Mandant;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.parts.CheckedContextMenuItem;
import de.willuhn.jameica.gui.parts.ContextMenu;
import de.willuhn.jameica.gui.parts.ContextMenuItem;
import de.willuhn.jameica.system.Application;
import de.willuhn.util.ApplicationException;
import de.willuhn.util.I18N;

/**
 * Vorkonfiguriertes Kontext-Menu fuer Geschaeftsjahres-Listen.
 */
public class GeschaeftsjahrListMenu extends ContextMenu
{
  private Mandant mandant = null;
  
  /**
   * ct.
   * @param m Mandant.
   */
  public GeschaeftsjahrListMenu(Mandant m)
  {
    this.mandant = m;
    I18N i18n = Application.getPluginLoader().getPlugin(Fibu.class).getResources().getI18N();
    this.addItem(new CheckedContextMenuItem(i18n.tr("Bearbeiten"), new GeschaeftsjahrNeu()));
    this.addItem(new CheckedContextMenuItem(i18n.tr("Löschen"), new GeschaeftsjahrDelete()));
    this.addItem(ContextMenuItem.SEPARATOR);
    this.addItem(new ContextMenuItem(i18n.tr("Neues Geschäftsjahr"), new GNeu()));
    this.addItem(ContextMenuItem.SEPARATOR);
    this.addItem(new ContextMenuItem(i18n.tr("Als aktives Geschäftsjahr festlegen"), new Action() {
      public void handleAction(Object context) throws ApplicationException
      {
        if (context == null || !(context instanceof Geschaeftsjahr))
          return;
        Settings.setActiveGeschaeftsjahr((Geschaeftsjahr)context);
        // Seite aktualisieren
        GUI.startView(GUI.getCurrentView().getClass(),GUI.getCurrentView().getCurrentObject());
      }
    }));
    this.addItem(new ContextMenuItem(i18n.tr("Geschäftsjahr abschliessen"), new GeschaeftsjahrClose()));
  }
  
  /**
   * Erzeugt immer ein neues Geschaeftsjahr - unabhaengig vom Kontext.
   */
  private class GNeu extends GeschaeftsjahrNeu
  {
    /**
     * @see de.willuhn.jameica.gui.Action#handleAction(java.lang.Object)
     */
    public void handleAction(Object context) throws ApplicationException
    {
      super.handleAction(mandant);
    }
    
  }
}


/*********************************************************************
 * $Log: GeschaeftsjahrListMenu.java,v $
 * Revision 1.3  2005/08/29 21:37:02  willuhn
 * *** empty log message ***
 *
 * Revision 1.2  2005/08/29 14:54:28  willuhn
 * @B bugfixing
 *
 * Revision 1.1  2005/08/29 12:17:29  willuhn
 * @N Geschaeftsjahr
 *
 * Revision 1.2  2005/08/25 21:58:57  willuhn
 * @N SKR04
 *
 * Revision 1.1  2005/08/16 23:14:36  willuhn
 * @N velocity export
 * @N context menus
 * @B bugfixes
 *
 **********************************************************************/