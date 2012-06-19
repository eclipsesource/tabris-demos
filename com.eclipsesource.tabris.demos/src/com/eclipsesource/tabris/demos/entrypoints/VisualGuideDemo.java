/*******************************************************************************
 * Copyright (c) 2012 EclipseSource and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.entrypoints;

import org.eclipse.rwt.lifecycle.IEntryPoint;
import org.eclipse.rwt.lifecycle.WidgetUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
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

public class VisualGuideDemo implements IEntryPoint {

  private Display display;

  public int createUI() {
    display = new Display();
    createShell();
    return 0;
  }

  private void createBrowser() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    shell.setLayout( new FillLayout() );
    final Browser browser = new Browser( shell, SWT.NONE );
    browser.setUrl( "http://www.eclipse.org" );
    shell.open();
  }

  private void createButtonCheck() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    shell.setLayout( new FillLayout() );
    final Button buttonCheck = new Button( shell, SWT.CHECK );
    buttonCheck.setText( "One" );
    shell.open();
  }

  private void createButtonPush() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    RowLayout layout = new RowLayout();
    layout.marginTop = 23;
    shell.setLayout( layout );
    final Button buttonPush = new Button( shell, SWT.PUSH );
    buttonPush.setText( "One" );
    shell.open();
  }

  private void createButtonRadio() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    RowLayout layout = new RowLayout( SWT.HORIZONTAL );
    layout.marginTop = 23;
    shell.setLayout( layout );
    final Button buttonRadio1 = new Button( shell, SWT.RADIO );
    buttonRadio1.setText( "One" );
    final Button buttonRadio2 = new Button( shell, SWT.RADIO );
    buttonRadio2.setText( "Two" );
    final Button buttonRadio3 = new Button( shell, SWT.RADIO );
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
    shell.setLayout( new FillLayout() );
    final Composite compo = new Composite( shell, SWT.NONE );
    RowLayout layout = new RowLayout( SWT.VERTICAL );
    layout.pack = false;
    layout.marginTop = 20;
    compo.setLayout( layout );
    final Button buttonPush = new Button( compo, SWT.PUSH );
    buttonPush.setText( "A Composite ..." );
    final Text text = new Text( compo, SWT.NONE );
    text.setText( "is a widget container." );
    shell.pack();
    shell.open();
  }

  private void createDateTime() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    shell.setLayout( new RowLayout( SWT.HORIZONTAL ) );
    new DateTime( shell, SWT.BORDER | SWT.DATE | SWT.LONG );
    new DateTime( shell, SWT.BORDER | SWT.TIME | SWT.LONG );
    shell.open();
  }

  private void createGroup() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    RowLayout layout = new RowLayout();
    layout.marginHeight = 20;
    layout.marginWidth = 20;
    shell.setLayout( layout );
    Group group = new Group( shell, SWT.SHADOW_ETCHED_IN );
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
    final Label label = new Label( shell, SWT.NONE );
    label.setText( "Jack and Jill went up\nthe hill to fetch a pail\nof water, Jack fell\ndown and broke his\ncrown and Jill came\ntumbling after!" );
    shell.open();
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
    shell.setText( "Shell With Title" );
    shell.setSize( 230, 100 );
    shell.setLocation( 30, 30 );
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
    shell.setLayout( new FillLayout() );
    final TabFolder tabFolder = new TabFolder( shell, SWT.NONE );
    final TabItem tab0 = new TabItem( tabFolder, SWT.NONE );
    tab0.setText( "Tab0" );
    final TabItem tab1 = new TabItem( tabFolder, SWT.NONE );
    tab1.setText( "Tab1" );
    final TabItem tab2 = new TabItem( tabFolder, SWT.NONE );
    tab2.setText( "Tab2" );
    shell.setSize( 300, 100 );
    shell.open();
  }

  private void createTable() {
    final Shell shell = new Shell( display, SWT.NONE );
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
    shell.pack();
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
    textField.setText( "The quick brown fox jumps over the lazy dog." );
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
    ToolBar toolBar = new ToolBar( shell, SWT.HORIZONTAL );
    toolBar.setLayoutData( new GridData( SWT.FILL, SWT.TOP, true, false ) );
    ToolItem item = new ToolItem( toolBar, SWT.PUSH );
    item.setText( "Item" );
    item = new ToolItem( toolBar, SWT.PUSH );
    item.setText( "Toolbar" );
    item.setData( WidgetUtil.CUSTOM_VARIANT, "TITLE" );
    new ToolItem( toolBar, SWT.SEPARATOR );
    item = new ToolItem( toolBar, SWT.PUSH );
    item.setImage( new Image( display,
                              VisualGuideDemo.class.getResourceAsStream( "/images/envelope.png" ) ) );
    item.setText( "Send" );
    Composite comp = new Composite( shell, SWT.NONE );
    comp.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    shell.setSize( 300, 100 );
    shell.setLocation( 10, 30 );
    shell.setBackground( display.getSystemColor( SWT.COLOR_WIDGET_LIGHT_SHADOW ) );
    shell.open();
  }

  private void createTree() {
  }
}
