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

import org.eclipse.rap.rwt.widgets.DialogUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import com.eclipsesource.tabris.ui.Action;
import com.eclipsesource.tabris.ui.UIContext;

public class SearchAction implements Action {

  public void execute( UIContext context ) {
    BooksListPage page = ( BooksListPage )context.getPageManager().getPage();
    MessageBox messageBox = new MessageBox( page.getContainer().getShell(), SWT.ICON_WARNING );
    messageBox.setText( "Search" );
    messageBox.setMessage( "Search for books." );
    DialogUtil.open( messageBox, null );
  }
}
