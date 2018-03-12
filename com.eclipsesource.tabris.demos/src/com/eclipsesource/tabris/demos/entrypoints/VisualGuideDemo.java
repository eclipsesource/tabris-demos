/*******************************************************************************
 * Copyright (c) 2012, 2018 EclipseSource and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.entrypoints;

import static com.eclipsesource.tabris.widgets.enhancement.Widgets.onToolItem;

import java.io.InputStream;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.eclipsesource.tabris.widgets.BarcodeScanner;
import com.eclipsesource.tabris.widgets.ClientDialog;
import com.eclipsesource.tabris.widgets.ClientDialogListener;
import com.eclipsesource.tabris.widgets.ImageView;
import com.eclipsesource.tabris.widgets.RefreshComposite;
import com.eclipsesource.tabris.widgets.RefreshListener;
import com.eclipsesource.tabris.widgets.ScanListener;
import com.eclipsesource.tabris.widgets.enhancement.Widgets;


@SuppressWarnings("unused")
public class VisualGuideDemo implements EntryPoint {

  private Display display;

  @Override
  public int createUI() {
    display = new Display();
    /**
     * Call method to create controls like in the guide (http://developer.eclipsesource.com/tabris/docs/ui-controls/).
     * Only one method can be called.
     */
    createLabel();
    return 0;
  }

  private void createDialog() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    shell.setLayout( new GridLayout() );
    Button button = new Button( shell, SWT.PUSH );
    button.setText( "Open ClientDialog" );
    button.addListener( SWT.Selection, new Listener() {
      @Override
      public void handleEvent( Event event ) {
        ClientDialog clientDialog = new ClientDialog();
        clientDialog.setTitle( "Tabris.js Remote" );
        clientDialog.setMessage( "Tabris rocks!" );
        clientDialog.setButton( ClientDialog.ButtonType.OK, "OK", new Listener() {
          @Override
          public void handleEvent( Event event ) {
            System.out.println( "OK button selected" );
          }
        });
//        clientDialog.setButton( ClientDialog.ButtonType.NEUTRAL, "Retry", new Listener() {
//          @Override
//          public void handleEvent( Event event ) {
//            System.out.println( "NEUTRAL button selected" );
//          }
//        } );
        clientDialog.setButton( ClientDialog.ButtonType.CANCEL, "Cancel", new Listener() {
          @Override
          public void handleEvent( Event event ) {
            System.out.println( "CANCEL button selected" );
          }
        } );
        clientDialog.addClientDialogListener( new ClientDialogListener() {
          @Override
          public void open() {
            System.out.println( "Dialog opened" );
          }
          @Override
          public void close() {
            System.out.println( "Dialog closed" );
          }
        } );
        clientDialog.open();
      }
    } );
    shell.open();
  }

  private void createBrowser() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    shell.setLayout( new FillLayout() );
    final Browser browser = new Browser( shell, SWT.NONE );
    browser.setUrl( "http://www.eclipse.org" );
    shell.open();
  }

  private void createSlider() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    shell.setLayout( new FillLayout() );
    final Scale slider = new Scale( shell, SWT.NONE );
    slider.setMinimum( 25 );
    slider.setMaximum( 75 );
    slider.setSelection( 45 );
    slider.addListener( SWT.Selection, new Listener() {
      @Override
      public void handleEvent( Event event ) {
        System.out.println( "Selection changed: " + slider.getSelection() );
      }
    } );
    shell.open();
  }

  private void createButtonCheck() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    RowLayout layout = new RowLayout( SWT.HORIZONTAL );
    layout.marginTop = 23;
    shell.setLayout( layout );
    final Button buttonCheck = new Button( shell, SWT.CHECK );
    buttonCheck.setBackground( display.getSystemColor( SWT.COLOR_RED ) );
    buttonCheck.setText( "One Two Three Four Five Six Seven" );
    final Button buttonCheck2 = new Button( shell, SWT.CHECK );
    buttonCheck2.setBackground( display.getSystemColor( SWT.COLOR_GREEN ) );
    buttonCheck2.setText( "Two" );
    shell.open();
  }

  private void createButtonPush() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    RowLayout layout = new RowLayout();
    layout.marginTop = 23;
    shell.setLayout( layout );
    final Button buttonPush = new Button( shell, SWT.PUSH );
    buttonPush.setText( "One Two Three Four Five Six Seven" );
    final Button buttonPush2 = new Button( shell, SWT.PUSH );
    buttonPush2.setText( "Two" );
    shell.open();
  }

  private void createButtonRadio() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    RowLayout layout = new RowLayout( SWT.HORIZONTAL );
    layout.marginTop = 23;
    shell.setLayout( layout );
    final Button buttonRadio1 = new Button( shell, SWT.RADIO );
    buttonRadio1.setBackground( display.getSystemColor( SWT.COLOR_RED ) );
    buttonRadio1.setText( "One Two Three Four Five Six Seven" );
    final Button buttonRadio2 = new Button( shell, SWT.RADIO );
    buttonRadio2.setBackground( display.getSystemColor( SWT.COLOR_GREEN ) );
    buttonRadio2.setText( "Two" );
    final Button buttonRadio3 = new Button( shell, SWT.RADIO );
    buttonRadio3.setBackground( display.getSystemColor( SWT.COLOR_BLUE ) );
    buttonRadio3.setText( "Three" );
    shell.open();
  }

  private void createButtonToggle() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    RowLayout layout = new RowLayout( SWT.HORIZONTAL );
    layout.marginTop = 23;
    shell.setLayout( layout );
    final Button buttonToggle1 = new Button( shell, SWT.TOGGLE );
    buttonToggle1.setText( "One" );
    final Button buttonToggle2 = new Button( shell, SWT.TOGGLE );
    buttonToggle2.setText( "Two" );
    final Button buttonToggle3 = new Button( shell, SWT.TOGGLE );
    buttonToggle3.setText( "Three" );
    shell.open();
  }

  private void createCanvas() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    shell.setLayout( new FillLayout() );
    final Canvas canvas = new Canvas( shell, SWT.NONE );
    canvas.addPaintListener( new PaintListener() {

      @Override
      public void paintControl( final PaintEvent event ) {
        event.gc.setLineWidth( 3 );
        event.gc.drawLine( 20, 120, 120, 20 );
        event.gc.drawLine( 20, 20, 120, 120 );
        event.gc.drawRectangle( 20, 20, 100, 100 );
        event.gc.drawOval( 20, 20, 100, 100 );
      }
    } );
    canvas.redraw();
    shell.open();
  }

  private void createCombo() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    RowLayout layout = new RowLayout();
    layout.marginTop = 120;
    shell.setLayout( layout );
    final Combo combo = new Combo( shell, SWT.NONE );
    combo.setItems( new String[]{
      "Cat", "House", "Sky", "Sun"
    } );
    combo.select( 1 );
    shell.open();
  }

  private void createComposite() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    //shell.setLayout( new FillLayout() );
    final Composite compo = new Composite( shell, SWT.NONE );
    RowLayout layout = new RowLayout( SWT.VERTICAL );
    layout.pack = false;
    layout.marginTop = 20;
    layout.marginLeft = 20;
    compo.setLayout( layout );
    compo.setBackground( display.getSystemColor( SWT.COLOR_YELLOW ) );
    final Button buttonPush = new Button( compo, SWT.PUSH );
    buttonPush.setText( "A Composite ..." );
    buttonPush.addListener( SWT.Selection, new Listener() {
      @Override
      public void handleEvent( Event event ) {
        compo.setBounds( 60, 60, 250, 250 );
      }
    } );
    final Text text = new Text( compo, SWT.NONE );
    text.setText( "is a widget container." );
    compo.setBounds( 40, 40, 250, 250 );
    shell.open();
  }

  private void createRefreshComposite() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    //shell.setLayout( new FillLayout() );
    final RefreshComposite compo = new RefreshComposite( shell, SWT.NONE );
    RowLayout layout = new RowLayout( SWT.VERTICAL );
    layout.pack = false;
    layout.marginTop = 20;
    layout.marginLeft = 20;
    compo.setLayout( layout );
    compo.setMessage( "Refreshing..." );
    compo.setBackground( display.getSystemColor( SWT.COLOR_YELLOW ) );
    compo.addRefreshListener( new RefreshListener() {
      @Override
      public void refresh() {
        System.out.println( "Refreshing..." );
        compo.done();
      }
    } );
    final Button buttonPush = new Button( compo, SWT.PUSH );
    buttonPush.setText( "A Composite ..." );
    buttonPush.addListener( SWT.Selection, new Listener() {
      @Override
      public void handleEvent( Event event ) {
        compo.setBounds( 60, 60, 250, 250 );
      }
    } );
    compo.setBounds( 40, 40, 250, 250 );
    final Text text = new Text( compo, SWT.NONE );
    text.setText( "is a widget container." );
    shell.open();
  }

  private void createDateTime() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    shell.setLayout( new GridLayout() );
    final DateTime date = new DateTime( shell, SWT.BORDER | SWT.DATE | SWT.LONG );
    date.addListener( SWT.Selection, new Listener() {
      @Override
      public void handleEvent( Event event ) {
        System.out.println( "DateTime date selected: -> " + event );
        System.out.println( "year: -> " + date.getYear() );
        System.out.println( "month: -> " + date.getMonth() );
        System.out.println( "day: -> " + date.getDay() );
      }
    } );
    final DateTime time = new DateTime( shell, SWT.BORDER | SWT.TIME | SWT.LONG );
    time.addListener( SWT.Selection, new Listener() {
      @Override
      public void handleEvent( Event event ) {
        System.out.println( "DateTime time selected: -> " + event );
        System.out.println( "hours: -> " + time.getHours() );
        System.out.println( "minutes: -> " + time.getMinutes() );
        System.out.println( "seconds: -> " + time.getSeconds() );
      }
    } );
    shell.open();
  }

  private void createGroup() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    RowLayout layout = new RowLayout();
    layout.marginHeight = 20;
    layout.marginWidth = 20;
    shell.setLayout( null );
//    shell.setLayout( new FillLayout() );
    Group group = new Group( shell, SWT.SHADOW_ETCHED_IN );
    group.setBounds( 20, 20, 300, 300 );
    group.setBackground( display.getSystemColor( SWT.COLOR_GREEN ) );
    group.setText( "Group label" );
    group.setLayout( new RowLayout( SWT.VERTICAL ) );
    final Label label = new Label( group, SWT.NONE );
    label.setText( "Label inside a group." );
    shell.open();
  }

  private void createLabel() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    RowLayout layout = new RowLayout();
    layout.marginHeight = 20;
    layout.marginWidth = 20;
    shell.setLayout( layout );
    final Label label = new Label( shell, SWT.WRAP );
    label.setBackground( display.getSystemColor( SWT.COLOR_GREEN ) );
    label.setFont( new Font( display, new FontData( "Helvetica", 18, SWT.NORMAL ) ) );
    label.setText( "Jack and Jill went up\nthe hill to fetch a pail\nof water, Jack fell\ndown and broke his\ncrown and Jill came\ntumbling after!" );
    shell.open();
  }

  private InputStream openResourceStream( String name ) {
    return getClass().getClassLoader().getResourceAsStream( name );
  }

  private void createScrolledComposite() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setLayout( new FillLayout() );
    ScrolledComposite scroll = new ScrolledComposite( shell, SWT.BORDER | SWT.V_SCROLL );
    Composite content = new Composite( scroll, SWT.NONE );
    content.setLayout( new RowLayout( SWT.VERTICAL ) );
    for( int i = 0; i < 10; i++ ) {
      Button button = new Button( content, SWT.PUSH );
      button.setText( "Button" );
    }
    content.pack();
    scroll.setContent( content );
    shell.setSize( 100, 300 );
    shell.open();
  }

  private void createShell() {
    final Shell background = new Shell( display, SWT.NONE );
    background.setMaximized( true );
    background.open();
    background.setBackground( display.getSystemColor( SWT.COLOR_WIDGET_LIGHT_SHADOW ) );
    final Shell shell = new Shell( display, SWT.BORDER
                                            | SWT.APPLICATION_MODAL
                                            | SWT.TITLE
                                            | SWT.CLOSE );
    Widgets.onShell( shell ).setOverlayColor( display.getSystemColor( SWT.COLOR_DARK_BLUE ), 128 );
    shell.setLayout( new GridLayout() );
    shell.setText( "Shell With Title" );
    shell.setSize( 230, 100 );
    shell.setLocation( 30, 30 );
    final Label label = new Label( shell, SWT.NONE );
    label.setText( "Label inside a shell." );
    shell.open();
  }

  private void createSpinner() {
    final Shell shell = new Shell( display, SWT.NONE );
    Spinner spinner = new Spinner( shell, SWT.BORDER );
    spinner.setMinimum( 0 );
    spinner.setMaximum( 1000 );
    spinner.setSelection( 500 );
    spinner.setIncrement( 1 );
    spinner.setPageIncrement( 100 );
    spinner.pack();
    shell.pack();
    shell.open();
  }

  private void createTabFolder() {
    final Shell shell = new Shell( display, SWT.NONE );
    shell.setMaximized( true );
    shell.setLayout( new FillLayout() );
    final TabFolder tabFolder = new TabFolder( shell, SWT.NONE );
    Widgets.onTabFolder( tabFolder ).usePaging();
    final TabItem tab0 = new TabItem( tabFolder, SWT.NONE );
    tab0.setText( "Tab0" );
    Composite content0 =  new Composite( tabFolder, SWT.NONE );
    content0.setBackground( tabFolder.getDisplay().getSystemColor( SWT.COLOR_RED ) );
    tab0.setControl( content0 );
    final TabItem tab1 = new TabItem( tabFolder, SWT.NONE );
    tab1.setText( "Tab1" );
    Composite content1 =  new Composite( tabFolder, SWT.NONE );
    content1.setBackground( tabFolder.getDisplay().getSystemColor( SWT.COLOR_GREEN ) );
    tab1.setControl( content1 );
    final TabItem tab2 = new TabItem( tabFolder, SWT.NONE, 0 );
    tab2.setText( "Tab2" );
    Composite content2 =  new Composite( tabFolder, SWT.NONE );
    content2.setBackground( tabFolder.getDisplay().getSystemColor( SWT.COLOR_BLUE ) );
    tab2.setControl( content2 );
    tabFolder.setSelection( tab1 );
    shell.open();
  }

  private void createTable() {
    final Shell shell = new Shell( display, SWT.NONE );
    shell.setMaximized( true );
    shell.setLayout( new FillLayout() );
    Table table = new Table( shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION );
    table.setToolTipText( "A Table" );
    table.setLinesVisible( true );
    table.setHeaderVisible( true );
    String[] titles = {
      "Column0", "Column1", "Column2"
    };
    for( int i = 0; i < titles.length; i++ ) {
      TableColumn column = new TableColumn( table, SWT.NONE );
      column.setText( titles[ i ] );
    }
    int count = 128;
    for( int i = 0; i < count; i++ ) {
      TableItem item = new TableItem( table, SWT.NONE );
      item.setText( 0, "row " + i + " item 0" );
      item.setText( 1, "row " + i + " item 1" );
      item.setText( 2, "row " + i + " item 2" );
    }
    for( int i = 0; i < titles.length; i++ ) {
      table.getColumn( i ).pack();
    }
    shell.open();
  }

  private void createText() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    RowLayout layout = new RowLayout();
    layout.marginHeight = 20;
    layout.marginWidth = 20;
    shell.setLayout( layout );
    final Text textField = new Text( shell, SWT.BORDER );
    textField.setBackground( display.getSystemColor( SWT.COLOR_GREEN ) );
    textField.setText( "The quick brown fox jumps..." );
    Widgets.onText( textField ).useLocalClipboard();
    textField.addListener( SWT.DefaultSelection, new Listener() {
      @Override
      public void handleEvent( Event event ) {
        System.out.println( event );
      }
    } );
    shell.open();
  }

  private void createTextMulti() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    RowLayout layout = new RowLayout();
    layout.marginHeight = 20;
    layout.marginWidth = 20;
    shell.setLayout( layout );
    final Text multiLineTextField = new Text( shell, SWT.MULTI | SWT.BORDER );
    multiLineTextField.setText( "The quick brown fox\njumps over the lazy dog." );
    shell.open();
  }

  private void createToolBar() {
    final Shell shell = new Shell( display, SWT.NONE );
    GridLayout layout = new GridLayout();
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    shell.setLayout( layout );
    ToolBar toolBar = new ToolBar( shell, SWT.HORIZONTAL | SWT.RIGHT );
    toolBar.setLayoutData( new GridData( SWT.FILL, SWT.TOP, true, false ) );
    ToolItem item = new ToolItem( toolBar, SWT.PUSH );
    item.setText( "Item" );
    item = new ToolItem( toolBar, SWT.PUSH );
    item.setText( "Toolbar" );
    onToolItem( item ).useAsTitle();
    new ToolItem( toolBar, SWT.SEPARATOR );
    item = new ToolItem( toolBar, SWT.PUSH );
    item.setImage( new Image( display,
                              VisualGuideDemo.class.getResourceAsStream( "/images/envelope.png" ) ) );
    item.setText( "Send" );
    Composite comp = new Composite( shell, SWT.NONE );
    comp.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    shell.setSize( 400, 100 );
    shell.setLocation( 10, 30 );
    shell.setBackground( display.getSystemColor( SWT.COLOR_WIDGET_LIGHT_SHADOW ) );
    shell.open();
  }

  private void createTree() {
    final Shell shell = new Shell( display, SWT.NONE );
    shell.setLayout( new FillLayout() );
    shell.setMaximized( true );
    final Tree tree = new Tree( shell, SWT.BORDER );
    tree.setToolTipText( "A Tree" );
    TreeItem topItem = new TreeItem( tree, 0 );
    topItem.setText( "a branch" );
    TreeItem childItem = new TreeItem( topItem, 0 );
    childItem.setText( "a little branch" );
    TreeItem subChildItem = new TreeItem( childItem, 0 );
    subChildItem.setText( "a brown leaf" );
    childItem = new TreeItem( topItem, 0 );
    childItem.setText( "a yellow leaf" );
    topItem = new TreeItem( tree, 0 );
    topItem.setText( "another branch" );
    childItem = new TreeItem( topItem, 0 );
    childItem.setText( "a red leaf" );
    topItem = new TreeItem( tree, 0 );
    topItem.setText( "a green leaf" );
    shell.open();
  }

  private void createList() {
    final Shell shell = new Shell( display, SWT.NONE );
    shell.setLayout( new FillLayout() );
    shell.setMaximized( true );
    final List list = new List( shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL );
    list.setItems( new String[]{
      "House", "<b>Mouse</b>", "Tree", "<i>Sun</i>", "Flower"
    } );
    list.setData( RWT.MARKUP_ENABLED, Boolean.TRUE );
    list.addListener( SWT.Selection, new Listener() {
      @Override
      public void handleEvent( Event event ) {
        System.out.println( "Item selected: " + list.getItem( list.getSelectionIndex() ) );
      }
    } );
    shell.open();
  }

  private void createProgress() {
    final Shell shell = new Shell( display, SWT.NONE );
    shell.setMaximized( true );
    GridLayout layout = new GridLayout();
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    shell.setLayout( layout );
    ProgressBar progressBar = new ProgressBar( shell, SWT.HORIZONTAL | SWT.INDETERMINATE );
    Widgets.onProgressBar( progressBar ).useSpinningIndicator( true );
    progressBar.setMinimum( 50 );
    progressBar.setMaximum( 150 );
    progressBar.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, true ) );
    shell.open();
  }

  private void createImageView() {
    final Shell shell = new Shell( display, SWT.NONE );
    shell.setMaximized( true );
    GridLayout layout = new GridLayout();
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    shell.setLayout( layout );
    final ImageView view = new ImageView( shell );
    view.setLayoutData( new GridData( 100, 200 ) );
    view.setZoomEnabled( true );
    view.setMinZoomLevel( 2 );
    view.setMaxZoomLevel( 10 );
    view.setZoomLevel( 5 );
    view.setScaleMode( ImageView.ScaleMode.STRETCH );
    view.setScale( 4 );
    Image image = new Image( display, VisualGuideDemo.class.getResourceAsStream( "/images/hal.png" ) );
    view.setImage( image );
    view.addListener( SWT.MouseUp, new Listener() {
      @Override
      public void handleEvent( Event event ) {
        System.out.println( event );
        System.out.println( "zoomLevel -> " + view.getZoomLevel() );
      }
    } );
    shell.open();
  }

  private void createMenu() {
    final Shell shell = new Shell( display, SWT.NONE );
    shell.setMaximized( true );
    GridLayout layout = new GridLayout();
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    shell.setLayout( layout );
    final Image image = new Image( display, VisualGuideDemo.class.getResourceAsStream( "/images/inbox.png" ) );
    final Menu menu = new Menu( shell );
    for( int i = 0; i < 3; i++ ) {
      addMenuItem( menu, "Item " + i, image, i );
    }
    shell.setMenu( menu );
    Button showButton = new Button( shell, SWT.PUSH );
    showButton.setLayoutData( new GridData( SWT.BEGINNING, SWT.CENTER, false, false ) );
    showButton.setText( "Open menu" );
    showButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        menu.setVisible( true );
      }
    } );
    Button addButton = new Button( shell, SWT.PUSH );
    addButton.setLayoutData( new GridData( SWT.BEGINNING, SWT.CENTER, false, false ) );
    addButton.setText( "Add item at index 1" );
    addButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        addMenuItem( menu, "New Item", image, 1 );
      }
    } );
    Button removeButton = new Button( shell, SWT.PUSH );
    removeButton.setLayoutData( new GridData( SWT.BEGINNING, SWT.CENTER, false, false ) );
    removeButton.setText( "Dispose item at index 1" );
    removeButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        menu.getItem( 1 ).dispose();
      }
    } );
    shell.open();
  }

  private void createBarcodScanner() {
    final Shell shell = new Shell( display, SWT.NONE );
    shell.setLayout( new GridLayout( 2, true ) );
    shell.setMaximized( true );
    final BarcodeScanner barcodeScanner = new BarcodeScanner( shell );
    barcodeScanner.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 2, 1 ) );
    final Label status = new Label( shell, SWT.NONE );
    status.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 2, 1 ) );
    barcodeScanner.addScanListener( new ScanListener() {
      @Override
      public void scanSucceeded( String format, String data ) {
        status.setForeground( display.getSystemColor( SWT.COLOR_DARK_GREEN ) );
        status.setText( "Detected \"" + format + "\" code with data: " + data );
      }
      @Override
      public void scanFailed( String error ) {
        status.setForeground( display.getSystemColor( SWT.COLOR_DARK_RED ) );
        status.setText( "Scan failed with error: " + error );
      }
    } );
    Button startButton = new Button( shell, SWT.PUSH );
    startButton.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
    startButton.setText( "Start" );
    startButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        barcodeScanner.start( new BarcodeScanner.Formats[] {
          BarcodeScanner.Formats.qr,
          BarcodeScanner.Formats.code128,
          BarcodeScanner.Formats.code39
        } );
      }
    } );
    Button stopButton = new Button( shell, SWT.PUSH );
    stopButton.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
    stopButton.setText( "Stop" );
    stopButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        barcodeScanner.stop();
      }
    } );
    shell.open();
  }

  private void addMenuItem( Menu menu, String text, Image image, int index ) {
    final MenuItem item = new MenuItem( menu, SWT.PUSH, index );
    item.setText( text );
    item.setImage( image );
    item.addListener( SWT.Selection, new Listener() {
      @Override
      public void handleEvent( Event event ) {
        System.out.println( item.getText() + " selected" );
      }
    } );
  }

}
