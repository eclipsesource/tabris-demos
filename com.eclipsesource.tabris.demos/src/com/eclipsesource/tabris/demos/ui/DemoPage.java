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

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.eclipsesource.tabris.ui.Page;
import com.eclipsesource.tabris.ui.Transition;
import com.eclipsesource.tabris.ui.UIContext;


public class DemoPage implements Page {

  private final String label;

  public DemoPage( String label ) {
    this.label = label;
  }

  public Control create( Composite parent, UIContext context ) {
    Composite container = new Composite( parent, SWT.NONE );
    container.setLayout( GridLayoutFactory.fillDefaults().create() );
    Label labelControl = new Label( container, SWT.NONE );
    labelControl.setText( label );
    labelControl.setLayoutData( GridDataFactory.fillDefaults().grab( true, true ).align( SWT.CENTER, SWT.CENTER ).create() );
    return container;
  }

  public void activate( UIContext context, Transition transition ) {
    context.setTitle( label + " activated" );
  }

  public void deactivate( UIContext context ) {
  }
}
