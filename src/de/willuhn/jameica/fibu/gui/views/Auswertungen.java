/**********************************************************************
 * $Source: /cvsroot/syntax/syntax/src/de/willuhn/jameica/fibu/gui/views/Auswertungen.java,v $
 * $Revision: 1.8 $
 * $Date: 2010/06/03 14:26:16 $
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
import de.willuhn.jameica.fibu.Settings;
import de.willuhn.jameica.fibu.gui.controller.AuswertungControl;
import de.willuhn.jameica.fibu.rmi.Geschaeftsjahr;
import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.internal.buttons.Back;
import de.willuhn.jameica.gui.util.ButtonArea;
import de.willuhn.jameica.gui.util.Container;
import de.willuhn.jameica.gui.util.SimpleContainer;
import de.willuhn.jameica.system.Application;
import de.willuhn.util.I18N;

/**
 * View fuer die Auswertungen.
 */
public class Auswertungen extends AbstractView
{

  /**
   * @see de.willuhn.jameica.gui.AbstractView#bind()
   */
  public void bind() throws Exception
  {
    I18N i18n = Application.getPluginLoader().getPlugin(Fibu.class).getResources().getI18N();

    GUI.getView().setTitle(i18n.tr("Auswertungen"));

    final AuswertungControl control = new AuswertungControl(this);
    
    Geschaeftsjahr current = Settings.getActiveGeschaeftsjahr();
    if (!current.isClosed())
      GUI.getView().setErrorText(i18n.tr("Das aktuelle Gesch�ftsjahr ist noch nicht abgeschlossen. Abschreibungen wurden noch nicht gebucht."));

    
    Container group = new SimpleContainer(getParent());

    group.addHeadline(i18n.tr("Eigenschaften"));
    group.addLabelPair(i18n.tr("Art der Auswertung"), control.getAuswertungen());
    group.addHeadline(i18n.tr("Zeitraum"));
    group.addLabelPair(i18n.tr("Gesch�ftsjahr"), control.getJahr());
    group.addLabelPair(i18n.tr("Start-Datum"), control.getStart());
    group.addLabelPair(i18n.tr("End-Datum"), control.getEnd());
    
    group.addHeadline(i18n.tr("Konten"));
    group.addLabelPair(i18n.tr("von"), control.getStartKonto());
    group.addLabelPair(i18n.tr("bis"), control.getEndKonto());
    
    group.addSeparator();
    group.addCheckbox(control.getOpenAfterCreation(),i18n.tr("Auswertung nach der Erstellung �ffnen"));
    
    ButtonArea buttonArea = new ButtonArea(getParent(),3);
    buttonArea.addButton(new Back());
    buttonArea.addButton(control.getStartButton());
  }
}


/*********************************************************************
 * $Log: Auswertungen.java,v $
 * Revision 1.8  2010/06/03 14:26:16  willuhn
 * @N Extension zum Zuordnen von Hibiscus-Kategorien zu SynTAX-Buchungsvorlagen
 * @C Code-Cleanup
 *
 * Revision 1.7  2010/06/01 23:51:56  willuhn
 * @N Neue Icons - erster Teil
 *
 * Revision 1.6  2010/06/01 16:37:22  willuhn
 * @C Konstanten von Fibu zu Settings verschoben
 * @N Systemkontenrahmen nach expliziter Freigabe in den Einstellungen aenderbar
 * @C Unterscheidung zwischen canChange und isUserObject in UserObject
 * @C Code-Cleanup
 * @R alte CVS-Logs entfernt
 *
 * Revision 1.5  2009/07/03 10:52:18  willuhn
 * @N Merged SYNTAX_1_3_BRANCH into HEAD
 *
 * Revision 1.4.2.1  2009/06/25 15:21:18  willuhn
 * @N weiterer Code fuer IDEA-Export
 **********************************************************************/