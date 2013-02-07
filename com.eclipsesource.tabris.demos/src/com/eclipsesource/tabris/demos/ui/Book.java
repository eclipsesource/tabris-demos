
package com.eclipsesource.tabris.demos.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

public class Book {

  private final String title;
  private final String author;
  private final Image image;
  private final List<Book> related;
  private boolean favourite;
  private boolean popular;

  public Book( String title, String author, Image image ) {
    this.title = title;
    this.author = author;
    this.image = image;
    this.related = new ArrayList<Book>();
  }

  public boolean isFavourite() {
    return favourite;
  }

  public Book setFavourite( boolean favourite ) {
    this.favourite = favourite;
    return this;
  }

  public boolean isPopular() {
    return popular;
  }

  public Book setPopular( boolean popular ) {
    this.popular = popular;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public Image getImage() {
    return image;
  }

  public List<Book> getRelated() {
    return related;
  }

}
