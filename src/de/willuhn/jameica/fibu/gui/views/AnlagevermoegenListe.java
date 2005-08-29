/**********************************************************************
 * $Source: /cvsroot/syntax/syntax/src/de/willuhn/jameica/fibu/gui/views/AnlagevermoegenListe.java,v $
 * $Revision: 1.2 $
 * $Date: 2005/08/29 22:44:05 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.fibu.gui.views;

import de.willuhn.jameica.fibu.Fibu;
import de.willuhn.jameica.fibu.gui.action.AnlagevermoegenExport;
import de.willuhn.jameica.fibu.gui.action.AnlagevermoegenNeu;
import de.willuhn.jameica.fibu.gui.part.AnlagevermoegenList;
import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.Part;
import de.willuhn.jameica.gui.internal.action.Back;
import de.willuhn.jameica.gui.util.ButtonArea;
import de.willuhn.jameica.system.Application;
import de.willuhn.util.ApplicationException;
import de.willuhn.util.I18N;

/**
 * View fuer das Anlagevermoegen.
 */
public class AnlagevermoegenListe extends AbstractView
{

  /**
   * @see de.willuhn.jameica.gui.AbstractView#bind()
   */
  public void bind() throws Exception
  {
    I18N i18n = Application.getPluginLoader().getPlugin(Fibu.class).getResources().getI18N();

    GUI.getView().setTitle(i18n.tr("Anlageverm�gen"));

    Part p = new AnlagevermoegenList(new AnlagevermoegenNeu());
    p.paint(getParent());

    ButtonArea buttons = new ButtonArea(getParent(),4);
    buttons.addButton(i18n.tr("Zur�ck"), new Back());
    buttons.addButton(i18n.tr("�bersicht exportieren"), new AnlagevermoegenExport());
    buttons.addButton(i18n.tr("Anlageverm�gen hinzuf�gen"), new AnlagevermoegenNeu(),null,true);
  }

  /**
   * @see de.willuhn.jameica.gui.AbstractView#unbind()
   */
  public void unbind() throws ApplicationException
  {
  }

}


/*********************************************************************
 * $Log: AnlagevermoegenListe.java,v $
 * Revision 1.2  2005/08/29 22:44:05  willuhn
 * @N added templates
 *
 * Revision 1.1  2005/08/29 14:26:56  willuhn
 * @N Anlagevermoegen, Abschreibungen
 *
 **********************************************************************/