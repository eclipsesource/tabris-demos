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
package com.eclipsesource.tabris.demos.entrypoints;

import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_ACTION_SEARCH;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_ACTION_SETTINGS;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_ACTION_SHARE;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_ACTION_THEME;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_PAGE_ALL_BOOKS;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_PAGE_FAVOURITE_BOOKS;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_PAGE_POPULAR_BOOKS;

import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.rap.rwt.application.EntryPointFactory;

import com.eclipsesource.tabris.demos.ui.AllBooksPage;
import com.eclipsesource.tabris.demos.ui.BookDetailsPage;
import com.eclipsesource.tabris.demos.ui.ChangeThemeAction;
import com.eclipsesource.tabris.demos.ui.FavouriteBooksPage;
import com.eclipsesource.tabris.demos.ui.PopularBooksPage;
import com.eclipsesource.tabris.demos.ui.ReadBookPage;
import com.eclipsesource.tabris.demos.ui.SearchAction;
import com.eclipsesource.tabris.demos.ui.SettingsAction;
import com.eclipsesource.tabris.demos.ui.SettingsPage;
import com.eclipsesource.tabris.demos.ui.ShareAction;
import com.eclipsesource.tabris.ui.ActionConfiguration;
import com.eclipsesource.tabris.ui.PageConfiguration;
import com.eclipsesource.tabris.ui.TabrisUIEntryPoint;
import com.eclipsesource.tabris.ui.UIConfiguration;

public class UiDemo implements EntryPointFactory {

  public EntryPoint create() {
    return new TabrisUIEntryPoint( createConfiguration() );
  }

  private UIConfiguration createConfiguration() {
    UIConfiguration configuration = new UIConfiguration();
    createPages( configuration);
    createPageSettings( configuration );
    createGlobalActions( configuration );
    return configuration;
  }

  private void createPages( UIConfiguration configuration ) {
    createAllBooksPage( configuration );
    createPopularBooksPage( configuration );
    createFavouriteBooksPage( configuration );
    createBookDetailsPage( configuration );
    createReadBookPage( configuration );
  }

  private void createAllBooksPage( UIConfiguration configuration ) {
    PageConfiguration page = new PageConfiguration( AllBooksPage.class.getName(), AllBooksPage.class );
    page.setTitle( "All Books" );
    page.setImage( IMAGE_PAGE_ALL_BOOKS );
    page.setTopLevel( true );
    ActionConfiguration action = new ActionConfiguration( SearchAction.class.getName(), SearchAction.class );
    action.setImage( IMAGE_ACTION_SEARCH );
    action.setTitle( "Search" );
    page.addActionConfiguration( action );
    configuration.addPageConfiguration( page );
  }

  private void createPopularBooksPage( UIConfiguration configuration ) {
    PageConfiguration page = new PageConfiguration( PopularBooksPage.class.getName(), PopularBooksPage.class );
    page.setTitle( "Popular" );
    page.setImage( IMAGE_PAGE_POPULAR_BOOKS );
    page.setTopLevel( true );
    configuration.addPageConfiguration( page );
  }

  private void createFavouriteBooksPage( UIConfiguration configuration ) {
    PageConfiguration page = new PageConfiguration( FavouriteBooksPage.class.getName(), FavouriteBooksPage.class );
    page.setTitle( "Favourite" );
    page.setImage( IMAGE_PAGE_FAVOURITE_BOOKS );
    page.setTopLevel( true );
    configuration.addPageConfiguration( page );
  }

  private void createBookDetailsPage( UIConfiguration configuration ) {
    PageConfiguration page = new PageConfiguration( BookDetailsPage.class.getName(), BookDetailsPage.class );
    page.setTitle( "Book" );
    ActionConfiguration action = new ActionConfiguration( ShareAction.class.getName(), ShareAction.class );
    action.setImage( IMAGE_ACTION_SHARE );
    action.setTitle( "Share" );
    page.addActionConfiguration( action );
    configuration.addPageConfiguration( page );
  }

  private void createReadBookPage( UIConfiguration configuration ) {
    PageConfiguration page = new PageConfiguration( ReadBookPage.class.getName(), ReadBookPage.class );
    page.setTitle( "Book" );
    ActionConfiguration action = new ActionConfiguration( ChangeThemeAction.class.getName(),
                                                          ChangeThemeAction.class );
    action.setImage( IMAGE_ACTION_THEME );
    action.setTitle( "Change Theme" );
    page.addActionConfiguration( action );
    configuration.addPageConfiguration( page );
  }

  private void createPageSettings( UIConfiguration configuration ) {
    PageConfiguration page = new PageConfiguration( SettingsPage.class.getName(), SettingsPage.class );
    page.setTitle( "Settings" );
    configuration.addPageConfiguration( page );
  }

  private void createGlobalActions( UIConfiguration configuration ) {
    ActionConfiguration action = new ActionConfiguration( SettingsAction.class.getName(), SettingsAction.class );
    action.setImage( IMAGE_ACTION_SETTINGS );
    action.setTitle( "Settings" );
    configuration.addActionConfiguration( action );
  }

}