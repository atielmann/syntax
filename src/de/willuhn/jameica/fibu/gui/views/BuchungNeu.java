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
import de.willuhn.jameica.fibu.Settings;
import de.willuhn.jameica.fibu.gui.action.BuchungDelete;
import de.willuhn.jameica.fibu.gui.controller.BuchungControl;
import de.willuhn.jameica.fibu.rmi.Anlagevermoegen;
import de.willuhn.jameica.fibu.rmi.Buchung;
import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.parts.Button;
import de.willuhn.jameica.gui.parts.ButtonArea;
import de.willuhn.jameica.gui.util.SimpleContainer;
import de.willuhn.jameica.system.Application;
import de.willuhn.util.ApplicationException;
import de.willuhn.util.I18N;

/**
 * Erzeugt eine neue Buchung oder bearbeitet eine existierende.
 * @author willuhn
 */
public class BuchungNeu extends AbstractView
{
  private final static I18N i18n = Application.getPluginLoader().getPlugin(Fibu.class).getResources().getI18N();

  /**
   * @see de.willuhn.jameica.gui.AbstractView#bind()
   */
  public void bind() throws Exception
  {

    // Headline malen
		GUI.getView().setTitle(i18n.tr("Buchung bearbeiten"));

    final BuchungControl control = new BuchungControl(this);
    
    // Gruppe Konto erzeugen
    SimpleContainer group = new SimpleContainer(getParent());
    group.addLabelPair(i18n.tr("Vorlage"),         control.getBuchungstemplate());
    
    group.addHeadline(i18n.tr("Eigenschaften"));
    group.addLabelPair(i18n.tr("Datum"),           control.getDatum());
    group.addLabelPair(i18n.tr("Text"),            control.getText());
    group.addLabelPair(i18n.tr("Soll-Konto"),      control.getSollKontoAuswahl());
    group.addLabelPair(i18n.tr("Haben-Konto"),     control.getHabenKontoAuswahl());
    group.addLabelPair(i18n.tr("Beleg-Nr."),       control.getBelegnummer());
    group.addLabelPair(i18n.tr("Brutto-Betrag"),   control.getBetrag());
    group.addLabelPair(i18n.tr("Steuersatz"),      control.getSteuer());
    group.addLabelPair(i18n.tr("Notiz"),           control.getKommentar());
    
    Buchung b = control.getBuchung();
    Anlagevermoegen av = b.getAnlagevermoegen();
    if (av != null)
      group.addLabelPair(i18n.tr("Zugeh�riges Anlagegut"), control.getAnlageVermoegenLink());
    else if (b.isNewObject())
      group.addCheckbox(control.getAnlageVermoegen(),i18n.tr("In Anlageverm�gen �bernehmen"));

    // wir machen das Datums-Feld zu dem mit dem Focus.
    control.getDatum().focus();

    boolean closed = Settings.getActiveGeschaeftsjahr().isClosed();
    if (closed) GUI.getView().setErrorText(i18n.tr("Buchung kann nicht mehr ge�ndert werden, da das Gesch�ftsjahr abgeschlossen ist"));

    // und noch die Abschicken-Knoepfe
    ButtonArea buttonArea = new ButtonArea();

    Button delete = new Button(i18n.tr("L�schen"), new BuchungDelete(), b ,false,"user-trash-full.png");
    delete.setEnabled(!closed);
    buttonArea.addButton(delete);

    Button store = new Button(i18n.tr("Speichern"),new Action() {
      public void handleAction(Object context) throws ApplicationException
      {
        control.handleStore(false);
      }
    },null,false,"document-save.png");
    store.setEnabled(!closed);
    buttonArea.addButton(store);

    Button storeNew = new Button(i18n.tr("Speichern und n�chste Buchung"),new Action() {
      public void handleAction(Object context) throws ApplicationException
      {
        control.handleStore(true);
      }
    },null,true,"go-next.png");
    storeNew.setEnabled(!closed);
    buttonArea.addButton(storeNew);
    
    buttonArea.paint(getParent());
  }
}

/*********************************************************************
 * $Log: BuchungNeu.java,v $
 * Revision 1.48  2011/05/12 09:10:31  willuhn
 * @R Back-Buttons entfernt
 * @C GUI-Cleanup
 *
 * Revision 1.47  2010-08-02 22:02:19  willuhn
 * *** empty log message ***
 *
 * Revision 1.46  2010/06/04 00:33:56  willuhn
 * @B Debugging
 * @N Mehr Icons
 * @C GUI-Cleanup
 *
 * Revision 1.45  2010/06/03 14:26:16  willuhn
 * @N Extension zum Zuordnen von Hibiscus-Kategorien zu SynTAX-Buchungsvorlagen
 * @C Code-Cleanup
 *
 * Revision 1.44  2010/06/02 00:02:59  willuhn
 * @N Mehr Icons
 *
 * Revision 1.43  2010/06/01 23:51:56  willuhn
 * @N Neue Icons - erster Teil
 *
 * Revision 1.42  2010/06/01 16:37:22  willuhn
 * @C Konstanten von Fibu zu Settings verschoben
 * @N Systemkontenrahmen nach expliziter Freigabe in den Einstellungen aenderbar
 * @C Unterscheidung zwischen canChange und isUserObject in UserObject
 * @C Code-Cleanup
 * @R alte CVS-Logs entfernt
 *
 **********************************************************************/