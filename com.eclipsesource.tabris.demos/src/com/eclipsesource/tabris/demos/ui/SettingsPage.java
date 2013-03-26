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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.eclipsesource.tabris.ui.Page;
import com.eclipsesource.tabris.ui.UI;

public class SettingsPage implements Page {

  private UI ui;

  public void createContents( Composite parent, UI ui ) {
    this.ui = ui;
    Composite container = new Composite( parent, SWT.NONE );
    container.setLayout( GridLayoutFactory.fillDefaults().spacing( 0, 0 ).numColumns( 1 ).equalWidth( false ).create() );
    Label textLabel = new Label( container, SWT.CENTER );
    GridData layoutData = GridDataFactory.fillDefaults().align( SWT.CENTER, SWT.CENTER ).grab( true, true ).create();
    textLabel.setLayoutData( layoutData );
    textLabel.setText( "Settings" );
  }

  public void activate() {
    ui.getActionOperator().setActionVisible( SettingsAction.class.getName(), false );
  }

  public void deactivate() {
    ui.getActionOperator().setActionVisible( SettingsAction.class.getName(), true );
  }
}
