/**********************************************************************
 *
 * Copyright (c) 2004 Olaf Willuhn
 * All rights reserved.
 * 
 * This software is copyrighted work licensed under the terms of the
 * Jameica License.  Please consult the file "LICENSE" for details. 
 *
 **********************************************************************/

package de.willuhn.jameica.fibu.gui.views;

import de.willuhn.jameica.fibu.Fibu;
import de.willuhn.jameica.fibu.gui.action.AnlagevermoegenNeu;
import de.willuhn.jameica.fibu.gui.part.AnlagevermoegenList;
import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.Part;
import de.willuhn.jameica.gui.parts.ButtonArea;
import de.willuhn.jameica.system.Application;
import de.willuhn.util.I18N;

/**
 * View fuer das Anlagevermoegen.
 */
public class AnlagevermoegenListe extends AbstractView
{
  private final static I18N i18n = Application.getPluginLoader().getPlugin(Fibu.class).getResources().getI18N();

  /**
   * @see de.willuhn.jameica.gui.AbstractView#bind()
   */
  public void bind() throws Exception
  {
    GUI.getView().setTitle(i18n.tr("Anlagevermögen"));

    Part p = new AnlagevermoegenList(new AnlagevermoegenNeu());
    p.paint(getParent());

    ButtonArea buttons = new ButtonArea();
    buttons.addButton(i18n.tr("Neues Anlagevermögen"), new AnlagevermoegenNeu(),null,true,"list-add.png");
    buttons.paint(getParent());
  }
}


/*********************************************************************
 * $Log: AnlagevermoegenListe.java,v $
 * Revision 1.9  2011/05/12 09:10:31  willuhn
 * @R Back-Buttons entfernt
 * @C GUI-Cleanup
 *
 * Revision 1.8  2010-06-04 00:33:56  willuhn
 * @B Debugging
 * @N Mehr Icons
 * @C GUI-Cleanup
 *
 * Revision 1.7  2010/06/02 00:02:59  willuhn
 * @N Mehr Icons
 *
 * Revision 1.6  2010/06/01 23:51:56  willuhn
 * @N Neue Icons - erster Teil
 *
 * Revision 1.5  2010/06/01 16:37:22  willuhn
 * @C Konstanten von Fibu zu Settings verschoben
 * @N Systemkontenrahmen nach expliziter Freigabe in den Einstellungen aenderbar
 * @C Unterscheidung zwischen canChange und isUserObject in UserObject
 * @C Code-Cleanup
 * @R alte CVS-Logs entfernt
 *
 **********************************************************************/