/*******************************************************************************
 * Copyright (c) 2013 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.entrypoints;

import static com.eclipsesource.tabris.widgets.enhancement.Widgets.onToolItem;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.eclipsesource.tabris.widgets.ScrollingComposite;
import com.eclipsesource.tabris.widgets.enhancement.TextDecorator;
import com.eclipsesource.tabris.widgets.enhancement.Widgets;

public class KeyboardDemo implements EntryPoint {

  Display display;

  public int createUI() {
    display = new Display();
    final Shell shell = createShell();
    createToolBar( shell );
    Composite comp = createParentComposite( shell );
    createTextFields( comp );
    createNoteLabel( comp );
    shell.open();
    return 0;
  }

  private Shell createShell() {
    final Shell shell = new Shell( display, SWT.NONE );
    shell.setMaximized( true );
    GridLayout shellLayout = new GridLayout( 1, false );
    shellLayout.marginHeight = 0;
    shellLayout.marginWidth = 0;
    shell.setLayout( shellLayout );
    return shell;
  }

  private void createToolBar( final Composite parent ) {
    ToolBar toolBar = new ToolBar( parent, SWT.NONE );
    toolBar.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.TOP ).grab( true, false ).create() );
    ToolItem toolItem = new ToolItem( toolBar, SWT.NONE );
    toolItem.setText( "Keyboard Types" );
    onToolItem( toolItem ).useAsTitle();
  }

  private Composite createParentComposite( final Shell shell ) {
    ScrollingComposite comp = new ScrollingComposite( shell, SWT.VERTICAL );
    GridLayout compLayout = new GridLayout( 2, false );
    compLayout.marginWidth = 16;
    compLayout.horizontalSpacing = 16;
    comp.setLayout( compLayout );
    comp.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.FILL ).grab( true, true ).create() );
    return comp;
  }

  private void createTextFields( Composite comp ) {
    createLabelText( comp, "Captialize On" ).setAutoCapitalizationEnabled( true );
    createLabelText( comp, "Captialize Off" ).setAutoCapitalizationEnabled( false );
    createLabelText( comp, "AutoCorrect On" ).setAutoCorrectionEnabled( true );
    createLabelText( comp, "AutoCorrect Off" ).setAutoCorrectionEnabled( false );
    createLabelText( comp, "ASCII" ).useAsciiKeyboard();
    createLabelText( comp, "Decimal" ).useDecimalKeyboard();
    createLabelText( comp, "E-Mail" ).useEmailKeyboard();
    createLabelText( comp, "Numbers" ).useNumberKeyboard();
    createLabelText( comp, "Numbers &\nPunctuation" ).useNumbersAndPunctuationKeyboard();
    createLabelText( comp, "Phone" ).usePhoneKeyboard();
    createLabelText( comp, "URL" ).useUrlKeyboard();
  }

  private TextDecorator createLabelText( Composite parent, String title ) {
    Label titleLabel = new Label( parent, SWT.NONE );
    titleLabel.setText( title );
    Text text = new Text( parent, SWT.BORDER );
    text.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.TOP ).grab( true, false ).create() );
    return Widgets.onText( text );
  }

  private void createNoteLabel( Composite comp ) {
    Label labelNote = new Label( comp, SWT.CENTER | SWT.WRAP );
    labelNote.setText( "Tab on the text input fields to show different keyboards." );
    GridData gridData = new GridData( SWT.CENTER, SWT.CENTER, true, false );
    gridData.verticalIndent = 24;
    gridData.horizontalSpan = 2;
    labelNote.setLayoutData( gridData );
  }
}
