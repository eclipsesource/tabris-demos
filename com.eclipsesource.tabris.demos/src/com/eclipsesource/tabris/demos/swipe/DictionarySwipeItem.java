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
package com.eclipsesource.tabris.demos.swipe;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.eclipsesource.tabris.widgets.swipe.SwipeContext;
import com.eclipsesource.tabris.widgets.swipe.SwipeItem;


public class DictionarySwipeItem implements SwipeItem {

  private final String english;
  private final String klingon;

  public DictionarySwipeItem( String english, String klingon ) {
    this.english = english;
    this.klingon = klingon;
  }

  public boolean isPreloadable() {
    return true;
  }

  public Control load( Composite parent ) {
    Composite result = new Composite( parent, SWT.NONE);
    GridLayoutFactory.fillDefaults().margins( 32, 32 ).applyTo( result );
    Composite page = new Composite( result, SWT.NONE);
    GridDataFactory.fillDefaults().grab( true, true ).align( SWT.FILL, SWT.FILL ).applyTo( page );
    GridLayoutFactory.fillDefaults().margins( 16, 16 ).applyTo( page );
    page.setBackground( parent.getDisplay().getSystemColor( SWT.COLOR_WHITE ) );
    Label titleLabel = new Label(page, SWT.WRAP);
    GridDataFactory.fillDefaults().align( SWT.BEGINNING, SWT.CENTER ).grab( true, true ).applyTo( titleLabel );
    titleLabel.setForeground( parent.getDisplay().getSystemColor( SWT.COLOR_BLACK ) );
    titleLabel.setText(english);
    Label contentLabel = new Label(page, SWT.WRAP);
    GridDataFactory.fillDefaults().align( SWT.END, SWT.CENTER ).grab( true, true ).applyTo( contentLabel );
    contentLabel.setForeground( parent.getDisplay().getSystemColor( SWT.COLOR_DARK_GRAY ) );
    contentLabel.setText(klingon);
    return result;
  }

  public void activate( SwipeContext context ) {
  }

  public void deactivate( SwipeContext context ) {
  }
}
