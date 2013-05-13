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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.rwt.widgets.DialogUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import com.eclipsesource.tabris.ui.action.ProposalHandler;
import com.eclipsesource.tabris.ui.action.SearchAction;

public class BookSearchAction extends SearchAction {

  @Override
  public void execute() {
    // do nothing
  }

  @Override
  public void search( String query ) {
    BooksListPage page = ( BooksListPage )getCurrentPage();
    MessageBox messageBox = new MessageBox( page.getContainer().getShell(), SWT.ICON_WARNING );
    messageBox.setText( "Search" );
    messageBox.setMessage( "Search for books with query: " + query );
    DialogUtil.open( messageBox, null );
  }

  @Override
  public void modified( String query, ProposalHandler propoalHandler ) {
    List<Book> books = BookProvider.getBooks( getUI().getDisplay() );
    List<String> proposals = new ArrayList<String>();
    for( Book book : books ) {
      if( book.getTitle().contains( query ) ) {
        proposals.add( book.getTitle() );
      }
    }
    propoalHandler.setProposals( proposals );
  }
}
