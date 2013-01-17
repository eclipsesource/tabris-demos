/*******************************************************************************
 * Copyright (c) 2013 EclipseSource and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.swipe;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.eclipsesource.tabris.widgets.swipe.SwipeContext;
import com.eclipsesource.tabris.widgets.swipe.SwipeItem;

public class DictionarySwipeItem implements SwipeItem {

  private final String english;
  private final String klingon;
  private final int index;

  public DictionarySwipeItem( String english, String klingon, int index ) {
    this.english = english;
    this.klingon = klingon;
    this.index = index;
  }

  public boolean isPreloadable() {
    return true;
  }

  public Control load( Composite parent ) {
    Composite result = new Composite( parent, SWT.NONE );
    GridLayoutFactory.fillDefaults().margins( 5, 5 ).applyTo( result );
    Composite page = createPage( result );
    createTitleLabel( page );
    createEnglishLabel( page );
    createKlingonLabel( page );
    parent.layout( true, true );
    return result;
  }

  private Composite createPage( Composite parent ) {
    Composite page = new Composite( parent, SWT.NONE );
    GridDataFactory.fillDefaults().grab( true, true ).align( SWT.FILL, SWT.FILL ).applyTo( page );
    GridLayoutFactory.fillDefaults().margins( 16, 16 ).applyTo( page );
    page.setBackground( parent.getDisplay().getSystemColor( SWT.COLOR_WHITE ) );
    return page;
  }

  private void createTitleLabel( Composite parent ) {
    Label titleLabel = new Label( parent, SWT.WRAP );
    GridDataFactory.fillDefaults()
      .align( SWT.CENTER, SWT.TOP )
      .grab( true, true )
      .applyTo( titleLabel );
    titleLabel.setForeground( parent.getDisplay().getSystemColor( SWT.COLOR_DARK_GRAY ) );
    titleLabel.setText( "Lesson " + ( index + 1 ) );
    titleLabel.setFont( new Font( parent.getDisplay(), new FontData( "Arial", 30, SWT.BOLD ) ) );
  }

  private void createEnglishLabel( Composite parent ) {
    Label englishLabel = new Label( parent, SWT.WRAP );
    GridDataFactory.fillDefaults()
      .align( SWT.LEFT, SWT.CENTER )
      .grab( true, true )
      .applyTo( englishLabel );
    englishLabel.setForeground( parent.getDisplay().getSystemColor( SWT.COLOR_BLACK ) );
    englishLabel.setText( english );
    englishLabel.setFont( new Font( parent.getDisplay(), new FontData( "Arial", 25, SWT.BOLD ) ) );
  }

  private void createKlingonLabel( Composite parent ) {
    Label klingonLabel = new Label( parent, SWT.WRAP | SWT.RIGHT );
    GridDataFactory.fillDefaults()
      .align( SWT.END, SWT.CENTER )
      .grab( true, true )
      .applyTo( klingonLabel );
    klingonLabel.setForeground( parent.getDisplay().getSystemColor( SWT.COLOR_DARK_GRAY ) );
    klingonLabel.setText( klingon );
    klingonLabel.setFont( new Font( parent.getDisplay(), new FontData( "Arial", 25, SWT.BOLD ) ) );
  }

  public void activate( SwipeContext context ) {
    // do nothing
  }

  public void deactivate( SwipeContext context ) {
    // do nothing
  }
}