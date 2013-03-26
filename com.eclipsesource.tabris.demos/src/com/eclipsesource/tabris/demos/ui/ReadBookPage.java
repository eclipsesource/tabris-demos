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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.eclipsesource.tabris.ui.AbstractPage;
import com.eclipsesource.tabris.ui.PageData;

public class ReadBookPage extends AbstractPage {

  public static final String BOOK_ITEM = "bookItem";
  private Label textLabel;
  private Composite container;

  @Override
  public void createContent( Composite parent, PageData data ) {
    container = new Composite( parent, SWT.NONE );
    container.setBackground( parent.getDisplay().getSystemColor( SWT.COLOR_WHITE ) );
    GridLayout layout = GridLayoutFactory.fillDefaults().spacing( 0, 0 ).numColumns( 1 ).equalWidth( false ).create();
    layout.marginWidth = 16;
    layout.marginHeight = 16;
    container.setLayout( layout );
    createText();
    setPageTitle( data );
  }

  private void createText() {
    textLabel = new Label( container, SWT.WRAP );
    textLabel.setForeground( container.getDisplay().getSystemColor( SWT.COLOR_BLACK ) );
    textLabel.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.FILL ).grab( true, true ).create() );
    textLabel.setText( DUMMY_TEXT );
  }

  private void setPageTitle( PageData data ) {
    Book book = data.get( BOOK_ITEM, Book.class );
    setTitle( book.getTitle() );
  }

  public void toggleTheme() {
    if( container.getBackground().equals( container.getDisplay().getSystemColor( SWT.COLOR_BLACK ) ) ) {
      textLabel.setForeground( container.getDisplay().getSystemColor( SWT.COLOR_BLACK ) );
      textLabel.setBackground( container.getDisplay().getSystemColor( SWT.COLOR_WHITE ) );
      container.setBackground( container.getDisplay().getSystemColor( SWT.COLOR_WHITE ) );
    } else {
      textLabel.setForeground( container.getDisplay().getSystemColor( SWT.COLOR_WHITE ) );
      textLabel.setBackground( container.getDisplay().getSystemColor( SWT.COLOR_BLACK ) );
      container.setBackground( container.getDisplay().getSystemColor( SWT.COLOR_BLACK ) );
    }
  }

  @Override
  public void activate() {
    setSettingsActionVisibility( false );
  }

  @Override
  public void deactivate() {
    setSettingsActionVisibility( true );
  }

  private void setSettingsActionVisibility( boolean visible ) {
    setActionVisible( SettingsAction.class.getName(), visible );
  }
}
