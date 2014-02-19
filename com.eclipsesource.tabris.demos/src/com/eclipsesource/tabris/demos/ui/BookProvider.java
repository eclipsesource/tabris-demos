/*******************************************************************************
 * Copyright (c) 2013 EclipseSource and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.ui;

import static com.eclipsesource.tabris.demos.ui.Constants.BOOK_BROOK;
import static com.eclipsesource.tabris.demos.ui.Constants.BOOK_GAIGE;
import static com.eclipsesource.tabris.demos.ui.Constants.BOOK_HAINY;
import static com.eclipsesource.tabris.demos.ui.Constants.BOOK_KOCH;
import static com.eclipsesource.tabris.demos.ui.Constants.BOOK_RUSSEL;
import static com.eclipsesource.tabris.demos.ui.Constants.BOOK_SHIELDS;
import static com.eclipsesource.tabris.demos.ui.Constants.BOOK_SLOSS;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_AFTER_VISITING;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_AUTOBIOGRAFY;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_HISTORY;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_HOW_LITERATUR;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_SCHRODER;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_THE_DINNER;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_VAMPIRES;
import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;
import static java.util.regex.Pattern.quote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.eclipsesource.tabris.ui.action.Proposal;

public class BookProvider {

  private static class BookData {

    private final String title;
    private final String author;
    private final String imagePath;

    public BookData( String title, String author, String imagePath ) {
      this.title = title;
      this.author = author;
      this.imagePath = imagePath;
    }
  }
  private static Map<String, BookData> bookData = createBookData();

  private static Map<String, BookData> createBookData() {
    Map<String, BookData> data = new HashMap<String, BookData>();
    data.put( BOOK_GAIGE, new BookData( "Schroder: A Novel", "Amity Gaige", IMAGE_SCHRODER ) );
    data.put( BOOK_HAINY, new BookData( "After Visiting Friends: A Son's Story",
                                        "Michael Hainey",
                                        IMAGE_AFTER_VISITING ) );
    data.put( BOOK_RUSSEL, new BookData( "Vampires in the Lemon Grove: Stories",
                                         "Karen Russell",
                                         IMAGE_VAMPIRES ) );
    data.put( BOOK_BROOK,
              new BookData( "A History of Future Cities", "Daniel Brook", IMAGE_HISTORY ) );
    data.put( BOOK_SLOSS, new BookData( "Autobiography of Us: A Novel",
                                        "Aria Beth Sloss",
                                        IMAGE_AUTOBIOGRAFY ) );
    data.put( BOOK_SHIELDS, new BookData( "How Literature Saved My Life",
                                          "David Shields",
                                          IMAGE_HOW_LITERATUR ) );
    data.put( BOOK_KOCH, new BookData( "The Dinner", "Herman Koch", IMAGE_THE_DINNER ) );
    return data;
  }

  public static List<Book> getBooks( Display display ) {
    List<Book> books = new ArrayList<Book>();
    Book bookSchroder = createBook( display, bookData.get( BOOK_GAIGE ) );
    Book bookAfterVisiting = createBook( display, bookData.get( BOOK_HAINY ) ).setFavorite( true );
    Book bookVampires = createBook( display, bookData.get( BOOK_RUSSEL ) ).setFavorite( true );
    Book bookHistory = createBook( display, bookData.get( BOOK_BROOK ) ).setFavorite( true )
      .setPopular( true );
    Book bookAutobiography = createBook( display, bookData.get( BOOK_SLOSS ) ).setPopular( true );
    Book bookLiteratur = createBook( display, bookData.get( BOOK_SHIELDS ) ).setPopular( true );
    Book bookDinner = createBook( display, bookData.get( BOOK_KOCH ) ).setPopular( true );
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
    return books;
  }

  private static Book createBook( Display display, BookData data ) {
    Image image = new Image( display, BookProvider.class.getResourceAsStream( data.imagePath ) );
    return new Book( data.title, data.author, image );
  }

  private static void relate( Book book1, Book book2 ) {
    book1.getRelated().add( book2 );
    book2.getRelated().add( book1 );
  }

  public static List<Proposal> findBookTitles( Display display, final String query ) {
    List<Book> books = BookProvider.getBooks( display );
    final List<Proposal> matches = new ArrayList<Proposal>();
    for( Book book : books ) {
      if( contains( book.getTitle(), query ) ) {
        matches.add( new Proposal( book.getTitle() ) );
      }
    }
    return matches;
  }

  public static List<Book> findBooks( Display display, final String query ) {
    List<Book> books = BookProvider.getBooks( display );
    final List<Book> matches = new ArrayList<Book>();
    for( Book book : books ) {
      if( contains( book.getTitle(), query ) ) {
        matches.add( book );
      }
    }
    return matches;
  }

  private static boolean contains( String title, String query ) {
    return compile( quote( query ), CASE_INSENSITIVE ).matcher( title ).find();
  }

  private BookProvider() {
    // prevent instantiation
  }
}
