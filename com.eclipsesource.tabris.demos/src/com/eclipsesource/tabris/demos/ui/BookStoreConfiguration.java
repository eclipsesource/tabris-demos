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

import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;

import com.eclipsesource.tabris.ui.PageStyle;
import com.eclipsesource.tabris.ui.UI;
import com.eclipsesource.tabris.ui.UIConfiguration;
import com.eclipsesource.tabris.ui.UIContext;

public class BookStoreConfiguration implements UIConfiguration {

  private static final String IMAGE_SCHRODER = "/images/book_schroder.jpg";
  private static final String IMAGE_HISTORY = "/images/book_a_history.jpg";
  private static final String IMAGE_AFTER_VISITING = "/images/book_after_visiting.jpg";
  private static final String IMAGE_AUTOBIOGRAFY = "/images/book_autobiografy.jpg";
  private static final String IMAGE_HOW_LITERATUR = "/images/book_how_literature.jpg";
  private static final String IMAGE_THE_DINNER = "/images/book_the_dinner.jpg";
  private static final String IMAGE_VAMPIRES = "/images/book_vampires.jpg";
  private static final String IMAGE_ACTION_SETTINGS = "/images/action_settings.png";
  private static final String IMAGE_ACTION_SEARCH = "/images/action_search.png";
  private static final String IMAGE_ACTION_SHARE = "/images/action_share.png";
  private static final String IMAGE_ACTION_THEME = "/images/action_theme.png";

  private List<Book> books;

  IBookFilter allBookFilter = new IBookFilter() {

    public List<Book> filter( List<Book> books ) {
      return books;
    }
  };

  IBookFilter favouriteBookFilter = new IBookFilter() {

    public List<Book> filter( List<Book> books ) {
      List<Book> result = new ArrayList<Book>();
      for( Book book : books ) {
        if( book.isFavourite() ) {
          result.add( book );
        }
      }
      return result;
    }
  };

  IBookFilter popularBookFilter = new IBookFilter() {

    public List<Book> filter( List<Book> books ) {
      List<Book> result = new ArrayList<Book>();
      for( Book book : books ) {
        if( book.isPopular() ) {
          result.add( book );
        }
      }
      return result;
    }
  };

  public void configure( UI ui, UIContext context ) {
    registerResources();
    createBooks( context );
    ui.addPage( "allBooksPage", new BooksListPage( books, allBookFilter ), "All Books", true );
    ui.addPage( "popularBooksPage", new BooksListPage( books, popularBookFilter ), "Popular", true );
    ui.addPage( "favouriteBooksPage",
                new BooksListPage( books, favouriteBookFilter ),
                "Favourite",
                true );
    ui.addPage( BookDetailsPage.ID, new BookDetailsPage(), "Book", false )
      .addAction( ShareAction.class.getName(),
                  "Share",
                  createImage( context, IMAGE_ACTION_SHARE ),
                  new ShareAction() );
    ui.addPage( ReadBookPage.ID, new ReadBookPage(), "Book", false, PageStyle.FULLSCREEN )
      .addAction( SelectThemeAction.class.getName(),
                  "Change Theme",
                  createImage( context, IMAGE_ACTION_THEME ),
                  new SelectThemeAction() );
    ui.addAction( SearchAction.class.getName(),
                  "Search",
                  createImage( context, IMAGE_ACTION_SEARCH ),
                  new SearchAction() );
    ui.addAction( SettingsAction.class.getName(),
                  "Settings",
                  createImage( context, IMAGE_ACTION_SETTINGS ),
                  new SettingsAction() );
  }

  private void registerResources() {
    FontRegistry fontRegistry = JFaceResources.getFontRegistry();
    fontRegistry.put( SharedFont.ITEM_TITLE,
                      new FontData[]{ new FontData( "Verdana", 16, SWT.BOLD ) } );
  }

  private void createBooks( UIContext context ) {
    books = new ArrayList<Book>();
    Book bookSchroder = new Book( "Schroder: A Novel", "Amity Gaige", createImage( context,
                                                                                   IMAGE_SCHRODER ) );
    Book bookAfterVisiting = new Book( "After Visiting Friends: A Son's Story",
                                       "Michael Hainey",
                                       createImage( context, IMAGE_AFTER_VISITING ) ).setFavourite( true );
    Book bookVampires = new Book( "Vampires in the Lemon Grove: Stories",
                                  "Karen Russell",
                                  createImage( context, IMAGE_VAMPIRES ) ).setFavourite( true );
    Book bookHistory = new Book( "A History of Future Cities",
                                 "Daniel Brook",
                                 createImage( context, IMAGE_HISTORY ) ).setFavourite( true )
      .setPopular( true );
    Book bookAutobiography = new Book( "Autobiography of Us: A Novel",
                                       "Aria Beth Sloss",
                                       createImage( context, IMAGE_AUTOBIOGRAFY ) ).setPopular( true );
    Book bookLiteratur = new Book( "How Literature Saved My Life",
                                   "David Shields",
                                   createImage( context, IMAGE_HOW_LITERATUR ) ).setPopular( true );
    Book bookDinner = new Book( "The Dinner",
                                "Herman Koch",
                                createImage( context, IMAGE_THE_DINNER ) ).setPopular( true );

    relate( bookVampires, bookAfterVisiting );
    relate( bookVampires, bookAutobiography );
    relate( bookSchroder, bookAfterVisiting );
    relate( bookHistory, bookLiteratur );
    relate( bookHistory, bookAutobiography );
    relate( bookHistory, bookAfterVisiting );

    books.add( bookSchroder );
    books.add( bookAfterVisiting );
    books.add( bookVampires );
    books.add( bookHistory );
    books.add( bookAutobiography );
    books.add( bookLiteratur );
    books.add( bookDinner );
  }

  private void relate( Book book1, Book book2 ) {
    book1.getRelated().add( book2 );
    book2.getRelated().add( book1 );
  }

  private Image createImage( UIContext context, String path ) {
    return new Image( context.getDisplay(), BookStoreConfiguration.class.getResourceAsStream( path ) );
  }

}