/*******************************************************************************
 * Copyright (c) 2013 EclipseSource and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
    PageData data = new PageData();
    data.set( SearchResultsPage.SEARCH_QUERY, query );
    getUI().getPageOperator().openPage( SearchResultsPage.class.getName(), data );
  }

  @Override
  public void modified( final String query, final ProposalHandler proposalHandler ) {
    // start potentially long running proposal gathering thread
    new Thread( new Runnable() {

      public void run() {
        getUI().getDisplay().asyncExec( new Runnable() {

          public void run() {
            List<Book> books = BookProvider.getBooks( getUI().getDisplay() );
            final List<String> proposals = new ArrayList<String>();
            for( Book book : books ) {
              if( contains( book.getTitle(), query ) ) {
                proposals.add( book.getTitle() );
              }
            }
            proposalHandler.setProposals( proposals );
          }
        } );
      }
    } ).start();
  }

  private boolean contains( String title, String query ) {
    return Pattern.compile( Pattern.quote( query ), Pattern.CASE_INSENSITIVE )
      .matcher( title )
      .find();
  }
}
