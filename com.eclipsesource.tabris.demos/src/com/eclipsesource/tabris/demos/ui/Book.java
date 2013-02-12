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
