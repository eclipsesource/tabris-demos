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

public class FavouriteBooksPage extends BooksListPage {

  private static class FavouriteBookFilter implements BookFilter {

    public List<Book> filter( List<Book> books ) {
      List<Book> result = new ArrayList<Book>();
      for( Book book : books ) {
        if( book.isPopular() ) {
          result.add( book );
        }
      }
      return result;
    }
  }

  public FavouriteBooksPage() {
    super( new FavouriteBookFilter() );
  }

  @Override
  public void activate() {
    super.activate();
    getViewer().setSelection( null );
  }
}
