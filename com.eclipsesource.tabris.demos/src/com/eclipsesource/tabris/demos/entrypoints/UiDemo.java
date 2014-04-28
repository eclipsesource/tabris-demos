/*******************************************************************************
 * Copyright (c) 2013 EclipseSource and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.entrypoints;

import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_ACTION_SEARCH;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_ACTION_SETTINGS;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_ACTION_SHARE;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_ACTION_THEME;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_PAGE_ALL_BOOKS;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_PAGE_FAVORITE_BOOKS;
import static com.eclipsesource.tabris.demos.ui.Constants.IMAGE_PAGE_POPULAR_BOOKS;

import java.io.InputStream;

import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.rap.rwt.application.EntryPointFactory;

import com.eclipsesource.tabris.demos.ui.AllBooksPage;
import com.eclipsesource.tabris.demos.ui.BookDetailsPage;
import com.eclipsesource.tabris.demos.ui.BookSearchAction;
import com.eclipsesource.tabris.demos.ui.ChangeThemeAction;
import com.eclipsesource.tabris.demos.ui.FavoriteBooksPage;
import com.eclipsesource.tabris.demos.ui.PDFAnnotatePage;
import com.eclipsesource.tabris.demos.ui.PDFSignPage;
import com.eclipsesource.tabris.demos.ui.PopularBooksPage;
import com.eclipsesource.tabris.demos.ui.ReadBookPage;
import com.eclipsesource.tabris.demos.ui.SaveAnnotatedPDFAction;
import com.eclipsesource.tabris.demos.ui.SaveSignedPDFAction;
import com.eclipsesource.tabris.demos.ui.ScanPage;
import com.eclipsesource.tabris.demos.ui.SearchResultsPage;
import com.eclipsesource.tabris.demos.ui.SettingsAction;
import com.eclipsesource.tabris.demos.ui.SettingsPage;
import com.eclipsesource.tabris.demos.ui.ShareAction;
import com.eclipsesource.tabris.ui.ActionConfiguration;
import com.eclipsesource.tabris.ui.PageConfiguration;
import com.eclipsesource.tabris.ui.PlacementPriority;
import com.eclipsesource.tabris.ui.TabrisUIEntryPoint;
import com.eclipsesource.tabris.ui.UIConfiguration;

public class UiDemo implements EntryPointFactory {

  @Override
  public EntryPoint create() {
    return new TabrisUIEntryPoint( createConfiguration() );
  }

  private UIConfiguration createConfiguration() {
    UIConfiguration configuration = new UIConfiguration();
    // configuration.setImage( getImage( IMAGE_BOOK_STORE_UI ) );
    createPages( configuration );
    createPageSettings( configuration );
    createGlobalActions( configuration );
    return configuration;
  }

  private void createPages( UIConfiguration configuration ) {
    createAllBooksPage( configuration );
    createPopularBooksPage( configuration );
    createFavoriteBooksPage( configuration );
    createBookDetailsPage( configuration );
    createReadBookPage( configuration );
    createSearchResultsPage( configuration );
    createAnnotatePDFPage( configuration );
    createSignPDFPage( configuration );
    createScannerPage( configuration );
  }

  private void createSignPDFPage( UIConfiguration configuration ) {
    PageConfiguration page = new PageConfiguration( PDFSignPage.class.getName(),
                                                    PDFSignPage.class );
    page.setTitle( "Sign a Contract" );
    page.setImage( getImage( "/contract.png" ) );
    page.setTopLevel( true );

    ActionConfiguration action = new ActionConfiguration( SaveSignedPDFAction.class.getName(), SaveSignedPDFAction.class );
    action.setTitle( "Save" );
    action.setImage( getImage( "/save.png" ) );
    page.addActionConfiguration( action );
    configuration.addPageConfiguration( page );
  }

  private void createAnnotatePDFPage( UIConfiguration configuration ) {
    PageConfiguration page = new PageConfiguration( PDFAnnotatePage.class.getName(),
                                                    PDFAnnotatePage.class );
    page.setTitle( "Look Inside" );
    page.setImage( getImage( "/look.png" ) );
    page.setTopLevel( true );

    ActionConfiguration action = new ActionConfiguration( SaveAnnotatedPDFAction.class.getName(), SaveAnnotatedPDFAction.class );
    action.setTitle( "Save" );
    action.setImage( getImage( "/save.png" ) );
    page.addActionConfiguration( action );
    configuration.addPageConfiguration( page );
  }

  private void createScannerPage( UIConfiguration configuration ) {
    PageConfiguration page = new PageConfiguration( ScanPage.class.getName(),
                                                    ScanPage.class );
    page.setTitle( "Scan a Book Code" );
    page.setImage( getImage( "/camera.png" ) );
    page.setTopLevel( true );
    configuration.addPageConfiguration( page );
  }

  private void createAllBooksPage( UIConfiguration configuration ) {
    PageConfiguration page = new PageConfiguration( AllBooksPage.class.getName(),
                                                    AllBooksPage.class );
    page.setTitle( "All Books" );
    page.setImage( getImage( IMAGE_PAGE_ALL_BOOKS ) );
    page.setTopLevel( true );
    configuration.addPageConfiguration( page );
  }

  private void createPopularBooksPage( UIConfiguration configuration ) {
    PageConfiguration page = new PageConfiguration( PopularBooksPage.class.getName(),
                                                    PopularBooksPage.class );
    page.setTitle( "Popular" );
    page.setImage( getImage( IMAGE_PAGE_POPULAR_BOOKS ) );
    page.setTopLevel( true );
    configuration.addPageConfiguration( page );
  }

  private void createFavoriteBooksPage( UIConfiguration configuration ) {
    PageConfiguration page = new PageConfiguration( FavoriteBooksPage.class.getName(),
                                                    FavoriteBooksPage.class );
    page.setTitle( "Favorite" );
    page.setImage( getImage( IMAGE_PAGE_FAVORITE_BOOKS ) );
    page.setTopLevel( true );
    configuration.addPageConfiguration( page );
  }

  private void createBookDetailsPage( UIConfiguration configuration ) {
    PageConfiguration page = new PageConfiguration( BookDetailsPage.class.getName(),
                                                    BookDetailsPage.class );
    page.setTitle( "Book" );
    ActionConfiguration action = new ActionConfiguration( ShareAction.class.getName(),
                                                          ShareAction.class );
    action.setImage( getImage( IMAGE_ACTION_SHARE ) );
    action.setTitle( "Share" );
    page.addActionConfiguration( action );
    configuration.addPageConfiguration( page );
  }

  private void createReadBookPage( UIConfiguration configuration ) {
    PageConfiguration page = new PageConfiguration( ReadBookPage.class.getName(),
                                                    ReadBookPage.class );
    page.setTitle( "Book" );
    ActionConfiguration action = new ActionConfiguration( ChangeThemeAction.class.getName(),
                                                          ChangeThemeAction.class );
    action.setImage( getImage( IMAGE_ACTION_THEME ) );
    action.setTitle( "Change Theme" );
    page.addActionConfiguration( action );
    configuration.addPageConfiguration( page );
  }

  private void createPageSettings( UIConfiguration configuration ) {
    PageConfiguration page = new PageConfiguration( SettingsPage.class.getName(),
                                                    SettingsPage.class );
    page.setTitle( "Settings" );
    configuration.addPageConfiguration( page );
  }

  private void createSearchResultsPage( UIConfiguration configuration ) {
    PageConfiguration page = new PageConfiguration( SearchResultsPage.class.getName(),
                                                    SearchResultsPage.class );
    configuration.addPageConfiguration( page );
  }

  private void createGlobalActions( UIConfiguration configuration ) {
    ActionConfiguration action = new ActionConfiguration( BookSearchAction.class.getName(),
                                                          BookSearchAction.class );
    action.setImage( getImage( IMAGE_ACTION_SEARCH ) );
    action.setTitle( "Search" );
    action.setPlacementPriority( PlacementPriority.HIGH );
    configuration.addActionConfiguration( action );
    action = new ActionConfiguration( SettingsAction.class.getName(), SettingsAction.class );
    action.setPlacementPriority( PlacementPriority.LOW );
    action.setImage( getImage( IMAGE_ACTION_SETTINGS ) );
    action.setTitle( "Settings" );
    configuration.addActionConfiguration( action );
  }

  private InputStream getImage( String path ) {
    return UiDemo.class.getResourceAsStream( path );
  }
}