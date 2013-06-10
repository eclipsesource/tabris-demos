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

import org.eclipse.swt.widgets.Composite;

import com.eclipsesource.tabris.ui.PageData;

public class SearchResultsPage extends BooksListPage {

  public static final String SEARCH_QUERY = "searchQuery";

  private static class ContainsBookFilter implements BookFilter {

    private final String query;

    public ContainsBookFilter( String query ) {
      this.query = query;
    }

    public List<Book> filter( List<Book> books ) {
      final List<Book> result = new ArrayList<Book>();
      for( Book book : books ) {
        if( contains( book.getTitle(), query ) ) {
          result.add( book );
        }
      }
      return result;
    }

    private boolean contains( String title, String query ) {
      return Pattern.compile( Pattern.quote( query ), Pattern.CASE_INSENSITIVE ).matcher( title ).find();
    }
  }

  @Override
  public void createContent( Composite parent, PageData data ) {
    String query = getData().get( SEARCH_QUERY, String.class );
    setTitle( query );
    setBookFilter( new ContainsBookFilter( query ) );
    super.createContent( parent, data );
  }

}
