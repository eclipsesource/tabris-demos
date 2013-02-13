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

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import com.eclipsesource.tabris.ui.Action;
import com.eclipsesource.tabris.ui.UIContext;

public class ChangeThemeAction implements Action {

  public void execute( UIContext context ) {
    Color foreground = context.getDisplay().getSystemColor( SWT.COLOR_WHITE );
    Color background = context.getDisplay().getSystemColor( SWT.COLOR_BLACK );
    ReadBookPage page = ( ReadBookPage )context.getPageManager().getPage();
    page.setTheme( foreground, background );
  }

}
