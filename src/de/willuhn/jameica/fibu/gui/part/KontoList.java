/**********************************************************************
 * $Source: /cvsroot/syntax/syntax/src/de/willuhn/jameica/fibu/gui/part/KontoList.java,v $
 * $Revision: 1.6 $
 * $Date: 2005/09/01 16:34:45 $
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
import java.util.ArrayList;

import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import de.willuhn.datasource.GenericIterator;
import de.willuhn.datasource.pseudo.PseudoIterator;
import de.willuhn.datasource.rmi.DBIterator;
import de.willuhn.jameica.fibu.Fibu;
import de.willuhn.jameica.fibu.Settings;
import de.willuhn.jameica.fibu.gui.menus.KontoListMenu;
import de.willuhn.jameica.fibu.rmi.Konto;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.formatter.CurrencyFormatter;
import de.willuhn.jameica.gui.input.CheckboxInput;
import de.willuhn.jameica.gui.input.TextInput;
import de.willuhn.jameica.gui.parts.TablePart;
import de.willuhn.jameica.gui.util.LabelGroup;
import de.willuhn.jameica.system.Application;
import de.willuhn.logging.Logger;
import de.willuhn.util.I18N;

/**
 * Vorkonfigurierte Tabelle mit Konten.
 */
public class KontoList extends TablePart
{
  
  private I18N i18n = Application.getPluginLoader().getPlugin(Fibu.class).getResources().getI18N();
  
  private TextInput search      = null;
  private CheckboxInput filter  = null;
  
  private GenericIterator list  = null;
  private ArrayList konten      = null;

  /**
   * ct.
   * @param action
   * @throws RemoteException
   */
  public KontoList(Action action) throws RemoteException
  {
    this(null,action);
  }
  
  /**
   * @param list
   * @param action
   * @throws RemoteException
   */
  public KontoList(GenericIterator list, Action action) throws RemoteException
  {
    // Wir laden die Konten erst wenn wirklich gezeichnet wird. Und dann
    // in einem separaten Thread.
    super(PseudoIterator.fromArray(new Konto[0]), action);

    this.list = list;

    addColumn(i18n.tr("Kontonummer"),"kontonummer");
    addColumn(i18n.tr("Name"),"name");
    addColumn(i18n.tr("Kontoart"),"kontoart_id");
    addColumn(i18n.tr("Steuer"),"steuer_id");
    addColumn(i18n.tr("Saldo"),"saldo", new CurrencyFormatter(Settings.getActiveGeschaeftsjahr().getMandant().getWaehrung(),Fibu.DECIMALFORMAT));
    setContextMenu(new KontoListMenu());
    setMulti(true);
  }
  
  /**
   * initialisiert eine Default-Liste mit Konten.
   * @throws RemoteException
   */
  private synchronized void init() throws RemoteException
  {
    if (this.list == null)
    {
      this.list = Settings.getDBService().createList(Konto.class);
      ((DBIterator)this.list).setOrder("order by kontonummer");
    }
    
    // Wir kopieren den ganzen Kram in eine ArrayList, damit die
    // Objekte beim Filter geladen bleiben
    konten = new ArrayList();
    list.begin();
    while (list.hasNext())
    {
      Konto k = (Konto) list.next();
      konten.add(k);
      addItem(k);
    }
  }

  /**
   * Ueberschrieben, um noch weitere Details anzuzeigen.
   * @see de.willuhn.jameica.gui.Part#paint(org.eclipse.swt.widgets.Composite)
   */
  public void paint(Composite parent) throws RemoteException
  {
    LabelGroup group = new LabelGroup(parent,i18n.tr("Filter"));
    
    this.filter = new CheckboxInput(false);
    this.search = new TextInput("");
    
    group.addLabelPair(i18n.tr("Bezeichnung enth�lt"), this.search);
    group.addCheckbox(this.filter,i18n.tr("Nur Konten mit Saldo anzeigen"));
    
    super.paint(parent);
    init();

    KL kl = new KL();
    this.search.getControl().addKeyListener(kl);
    ((Button)this.filter.getControl()).addSelectionListener(kl);
  }
  
  private class KL extends KeyAdapter implements SelectionListener
  {
    private Thread timeout = null;
    private long count = 900l;
    
    /**
     * @see org.eclipse.swt.events.KeyListener#keyReleased(org.eclipse.swt.events.KeyEvent)
     */
    public void keyReleased(KeyEvent e)
    {
      // Mal schauen, ob schon ein Thread laeuft. Wenn ja, muessen wir den
      // erst killen
      if (timeout != null)
      {
        timeout.interrupt();
        timeout = null;
      }
      
      // Ein neuer Timer
      timeout = new Thread()
      {
        public void run()
        {
          try
          {
            // Wir warten 900ms. Vielleicht gibt der User inzwischen weitere
            // Sachen ein.
            sleep(count);
            
            // Ne, wir wurden nicht gekillt. Also machen wir uns ans Werk

            GUI.getDisplay().syncExec(new Runnable()
            {
              public void run()
              {
                try
                {
                  // Erstmal alle rausschmeissen
                  removeAll();

                  // Wir holen uns den aktuellen Text
                  String text = (String) search.getValue();
                  if (text.length() == 0) text = null;

                  boolean checkSaldo = ((Boolean)filter.getValue()).booleanValue();

                  Konto k     = null;
                  String name = null;

                  for (int i=0;i<konten.size();++i)
                  {
                    k = (Konto) konten.get(i);
                    
                    if (checkSaldo && k.getSaldo() == 0.0d)
                      continue;

                    name = k.getName();
                    // Was zum Filtern da?
                    if (text == null || name == null || name.length() == 0)
                    {
                      // ne
                      addItem(k);
                      continue;
                    }
                    
                    if (name.indexOf(text) != -1)
                      addItem(k);
                    
                  }
                }
                catch (Exception e)
                {
                  Logger.error("error while loading konto",e);
                }
              }
            });
          }
          catch (InterruptedException e)
          {
            return;
          }
          finally
          {
            timeout = null;
            count = 900l;
          }
        }
      };
      timeout.start();
    }

    /**
     * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetSelected(SelectionEvent e)
    {
      // Beim Klick auf die Checkbox muessen wir nichts warten
      count = 0l;
      keyReleased(null);
    }

    /**
     * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetDefaultSelected(SelectionEvent e)
    {
    }
  }

}


/*********************************************************************
 * $Log: KontoList.java,v $
 * Revision 1.6  2005/09/01 16:34:45  willuhn
 * *** empty log message ***
 *
 * Revision 1.5  2005/08/29 12:17:29  willuhn
 * @N Geschaeftsjahr
 *
 * Revision 1.4  2005/08/25 23:00:02  willuhn
 * *** empty log message ***
 *
 * Revision 1.3  2005/08/16 23:14:35  willuhn
 * @N velocity export
 * @N context menus
 * @B bugfixes
 *
 * Revision 1.2  2005/08/15 23:38:27  willuhn
 * *** empty log message ***
 *
 * Revision 1.1  2005/08/08 21:35:46  willuhn
 * @N massive refactoring
 *
 **********************************************************************/