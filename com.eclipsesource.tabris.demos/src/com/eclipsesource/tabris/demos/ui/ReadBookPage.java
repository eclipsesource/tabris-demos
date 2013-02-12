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
package com.eclipsesource.tabris.demos.ui;

import static com.eclipsesource.tabris.demos.ui.Constants.DUMMY_TEXT;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.eclipsesource.tabris.ui.Page;
import com.eclipsesource.tabris.ui.UIContext;

public class ReadBookPage implements Page {

  public static final String BOOK_ITEM = "bookItem";
  private Label textLabel;
  private Composite container;

  public void create( Composite parent, UIContext context ) {
    container = new Composite( parent, SWT.NONE );
    GridLayout layout = GridLayoutFactory.fillDefaults().spacing( 0, 0 ).numColumns( 1 ).equalWidth( false ).create();
    layout.marginWidth = 16;
    layout.marginHeight = 16;
    container.setLayout( layout );
    createText();
    setPageTitle( context );
  }

  private void createText() {
    textLabel = new Label( container, SWT.NONE );
    textLabel.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.FILL ).grab( true, true ).create() );
    textLabel.setText( DUMMY_TEXT );
  }

  private void setPageTitle( UIContext context ) {
    Book book = context.getPageStore().get( BOOK_ITEM, Book.class );
    context.setTitle( book.getTitle() );
  }

  public void setTheme( Color foreground, Color background ) {
    textLabel.setForeground( foreground );
    textLabel.setBackground( background );
    container.setBackground( background );
  }

  public void activate( UIContext context ) {
    setSettingsActionVisibility( context, false );
  }

  public void deactivate( UIContext context ) {
    setSettingsActionVisibility( context, true );
  }

  private void setSettingsActionVisibility( UIContext context, boolean visible ) {
    context.getActionManager().setActionVisible( SettingsAction.class.getName(), visible );
  }
}
