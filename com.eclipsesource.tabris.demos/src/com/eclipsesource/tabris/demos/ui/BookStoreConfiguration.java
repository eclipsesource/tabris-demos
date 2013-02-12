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

import static com.eclipsesource.tabris.demos.ui.Constants.BOOKS;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_ACTION_SEARCH;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_ACTION_SETTINGS;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_ACTION_SHARE;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_ACTION_THEME;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_PAGE_ALL_BOOKS;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_PAGE_FAVOURITE_BOOKS;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_PAGE_POPULAR_BOOKS;
import static com.eclipsesource.tabris.demos.ui.Constants.TITLE_FONT;
import static com.eclipsesource.tabris.ui.ActionConfiguration.newAction;
import static com.eclipsesource.tabris.ui.PageConfiguration.newPage;

import java.util.List;

import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;

import com.eclipsesource.tabris.ui.ActionConfiguration;
import com.eclipsesource.tabris.ui.PageConfiguration;
import com.eclipsesource.tabris.ui.Prominence;
import com.eclipsesource.tabris.ui.UI;
import com.eclipsesource.tabris.ui.UIConfiguration;
import com.eclipsesource.tabris.ui.UIContext;

public class BookStoreConfiguration implements UIConfiguration {

  public void configure( UI ui, UIContext context ) {
    registerResources();
    createBooks( context );
    createPages( ui, context );
    createPageSettings( ui );
    createGlobalActions( ui, context );
  }

  private void registerResources() {
    FontRegistry fontRegistry = JFaceResources.getFontRegistry();
    fontRegistry.put( TITLE_FONT, new FontData[]{
      new FontData( "Verdana", 16, SWT.BOLD )
    } );
  }

  private void createBooks( UIContext context ) {
    List<Book> books = BookProvider.getBooks( context );
    context.getGlobalStore().add( BOOKS, books );
  }

  private void createPages( UI ui, UIContext context ) {
    createAllBooksPage( ui, context );
    createPopularBooksPage( ui, context );
    createFavouriteBooksPage( ui, context );
    createBookDetailsPage( ui, context );
    createReadBookPage( ui, context );
  }

  private void createAllBooksPage( UI ui, UIContext context ) {
    PageConfiguration page = newPage( AllBooksPage.class.getName(), AllBooksPage.class );
    page.setTitle( "All Books" );
    page.setImage( createImage( context, IMAGE_PAGE_ALL_BOOKS ) );
    page.setTopLevel( true );
    ActionConfiguration action = newAction( SearchAction.class.getName(), SearchAction.class );
    action.setImage( createImage( context, IMAGE_ACTION_SEARCH ) );
    action.setTitle( "Search" );
    ui.addPage( page ).addAction( action );
  }

  private void createPopularBooksPage( UI ui, UIContext context ) {
    PageConfiguration page = newPage( PopularBooksPage.class.getName(), PopularBooksPage.class );
    page.setTitle( "Popular" );
    page.setImage( createImage( context, IMAGE_PAGE_POPULAR_BOOKS ) );
    page.setTopLevel( true );
    ui.addPage( page );
  }

  private void createFavouriteBooksPage( UI ui, UIContext context ) {
    PageConfiguration page = newPage( FavouriteBooksPage.class.getName(), FavouriteBooksPage.class );
    page.setTitle( "Favourite" );
    page.setImage( createImage( context, IMAGE_PAGE_FAVOURITE_BOOKS ) );
    page.setTopLevel( true );
    ui.addPage( page );
  }

  private void createBookDetailsPage( UI ui, UIContext context ) {
    PageConfiguration page = newPage( BookDetailsPage.class.getName(), BookDetailsPage.class );
    page.setTitle( "Book" );
    ActionConfiguration action = newAction( ShareAction.class.getName(), ShareAction.class );
    action.setImage( createImage( context, IMAGE_ACTION_SHARE ) );
    action.setTitle( "Share" );
    ui.addPage( page ).addAction( action );
  }

  private void createReadBookPage( UI ui, UIContext context ) {
    PageConfiguration page = newPage( ReadBookPage.class.getName(), ReadBookPage.class );
    page.setTitle( "Book" );
    ActionConfiguration action = newAction( ChangeThemeAction.class.getName(), ChangeThemeAction.class );
    action.setImage( createImage( context, IMAGE_ACTION_THEME ) );
    action.setTitle( "Change Theme" );
    ui.addPage( page ).addAction( action );
  }

  private void createPageSettings( UI ui ) {
    PageConfiguration page = newPage( SettingsPage.class.getName(), SettingsPage.class );
    page.setTitle( "Settings" );
    ui.addPage( page );
  }

  private void createGlobalActions( UI ui, UIContext context ) {
    ActionConfiguration action = newAction( SettingsAction.class.getName(), SettingsAction.class );
    action.setImage( createImage( context, IMAGE_ACTION_SETTINGS ) );
    action.setTitle( "Settings" );
    action.setProminence( Prominence.EDIT );
    ui.addAction( action );
  }

  private Image createImage( UIContext context, String path ) {
    return new Image( context.getDisplay(), BookStoreConfiguration.class.getResourceAsStream( path ) );
  }
}