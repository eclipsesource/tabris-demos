/*******************************************************************************
 * Copyright (c) 2012 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.entrypoints;

import org.eclipse.rwt.lifecycle.IEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.eclipsesource.tabris.demos.enron.EnronExample;


public class VirtualTreeDemo implements IEntryPoint {

  public int createUI() {
    Display display = new Display();
    Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized(true);
    shell.setLayout( new FillLayout() );
    createContent( display, shell );
    shell.open();
    shell.setVisible(true);    
    return 0;
  }

  private void createContent( Display display, Shell shell ) {
    Composite comp = new Composite( shell, SWT.NONE );
    comp.setLayout( new FillLayout() );
    EnronExample enronExample = new EnronExample();
    enronExample.createMainArea( comp );
  }

}
