/*******************************************************************************
 * Copyright (c) 2013 EclipseSource and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.ui;

import static com.eclipsesource.tabris.demos.ui.BookDetailsPage.BOOK_ITEM;
import static com.eclipsesource.tabris.demos.ui.BookProvider.findBookTitles;
import static com.eclipsesource.tabris.demos.ui.BookProvider.findBooks;

import java.util.List;

import com.eclipsesource.tabris.ui.PageData;
import com.eclipsesource.tabris.ui.action.ProposalHandler;
import com.eclipsesource.tabris.ui.action.SearchAction;

public class BookSearchAction extends SearchAction {

  @Override
  public void execute() {
    // do nothing
  }

  @Override
  public void search( String query ) {
    List<Book> books = findBooks( getUI().getDisplay(), query );
    if( books.size() == 1 ) {
      showBookDetailsPage( books.get( 0 ) );
    } else {
      showSearchResultsPage( query );
    }
  }

  private void showBookDetailsPage( Book book ) {
    PageData data = new PageData();
    data.set( BOOK_ITEM, book );
    openPage( BookDetailsPage.class.getName(), data );
  }

  private void showSearchResultsPage( String query ) {
    if( getCurrentPage() instanceof SearchResultsPage ) {
      closeCurrentPage();
    }
    PageData data = new PageData();
    data.set( SearchResultsPage.SEARCH_QUERY, query );
    openPage( SearchResultsPage.class.getName(), data );
  }

  @Override
  public void modified( final String query, final ProposalHandler proposalHandler ) {
    // start potentially long running proposal gathering thread
    new Thread( new Runnable() {

      public void run() {
        getUI().getDisplay().asyncExec( new Runnable() {

          public void run() {
            proposalHandler.setProposals( findBookTitles( getUI().getDisplay(), query ) );
          }
        } );
      }
    } ).start();
  }
}
