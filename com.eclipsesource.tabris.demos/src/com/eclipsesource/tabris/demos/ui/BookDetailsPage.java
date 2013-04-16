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

import static com.eclipsesource.tabris.demos.ui.Constants.BOOK_ITEM;
import static com.eclipsesource.tabris.demos.ui.Constants.RELATED_BOOKS_FONT;
import static com.eclipsesource.tabris.demos.ui.Constants.TITLE_FONT;
import static com.eclipsesource.tabris.widgets.enhancement.Widgets.onComposite;
import static org.eclipse.jface.resource.JFaceResources.getFontRegistry;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import com.eclipsesource.tabris.ui.AbstractPage;
import com.eclipsesource.tabris.ui.PageData;


public class BookDetailsPage extends AbstractPage {

  private Label titleLabel;
  private Label authorLabel;
  private Label imageLabel;
  private TreeViewer relatedTreeViewer;
  private Composite bookDetailsComposite;

  @Override
  public void createContent( Composite parent, PageData data ) {
    Composite container = new Composite( parent, SWT.NONE );
    container.setBackground( parent.getDisplay().getSystemColor( SWT.COLOR_WHITE ) );
    container.setLayout( GridLayoutFactory.fillDefaults().spacing( 0, 0 ).numColumns( 1 ).equalWidth( false ).create() );
    createBookDetailsComposite( container );
    createRelatedBooks( container );
    createRelatedList( container );
    populatePage( data );
  }

  private void populatePage( PageData data ) {
    Book book = data.get( BOOK_ITEM, Book.class );
    setTitle( book.getTitle() );
    titleLabel.setText( book.getTitle() );
    authorLabel.setText( book.getAuthor() );
    imageLabel.setImage( book.getImage() );
    relatedTreeViewer.setInput( book.getRelated() );
    addGroupedEventsListener( book );
  }

  private void addGroupedEventsListener( final Book book ) {
    onComposite( bookDetailsComposite ).addGroupedListener( SWT.MouseUp, new Listener() {

      public void handleEvent( Event event ) {
        PageData readData = new PageData();
        readData.set( ReadBookPage.BOOK_ITEM, book );
        openPage( ReadBookPage.class.getName(), readData );
      }
    } );
  }

  private void createBookDetailsComposite( Composite parent ) {
    createBookComposite( parent );
    createBookImage();
    createBookTitle();
    createBookAuthor();
  }

  private void createBookComposite( Composite parent ) {
    bookDetailsComposite = new Composite( parent, SWT.NONE );
    bookDetailsComposite.setBackground( parent.getDisplay().getSystemColor( SWT.COLOR_WHITE ) );
    GridData layoutData = GridDataFactory.fillDefaults().align( SWT.FILL, SWT.TOP ).grab( true, false ).create();
    bookDetailsComposite.setLayoutData( layoutData );
    onComposite( bookDetailsComposite ).showLocalTouch();
    GridLayout layout = GridLayoutFactory.fillDefaults().spacing( 0, 0 ).numColumns( 2 ).equalWidth( false ).create();
    layout.verticalSpacing = 12;
    layout.horizontalSpacing = 12;
    layout.marginWidth = 12;
    layout.marginHeight = 12;
    bookDetailsComposite.setLayout( layout );
  }

  private void createBookImage() {
    imageLabel = new Label( bookDetailsComposite, SWT.WRAP );
    imageLabel.setForeground( bookDetailsComposite.getDisplay().getSystemColor( SWT.COLOR_BLACK ) );
    GridData gridData = new GridData( SWT.FILL, SWT.FILL, false, false );
    gridData.verticalSpan = 2;
    imageLabel.setLayoutData( gridData );
  }

  private void createBookTitle() {
    titleLabel = new Label( bookDetailsComposite, SWT.WRAP );
    titleLabel.setForeground( bookDetailsComposite.getDisplay().getSystemColor( SWT.COLOR_BLACK ) );
    titleLabel.setFont( getFontRegistry().get( TITLE_FONT ) );
    titleLabel.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.TOP ).grab( true, false ).create() );
  }

  private void createBookAuthor() {
    authorLabel = new Label( bookDetailsComposite, SWT.WRAP );
    authorLabel.setForeground( bookDetailsComposite.getDisplay().getSystemColor( SWT.COLOR_BLACK ) );
    authorLabel.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.TOP ).grab( true, false ).create() );
  }

  private void createRelatedBooks( Composite parent ) {
    Composite relatedBooksComposite = createRelatedTitleComposite( parent );
    createRelatedBooksTitle( relatedBooksComposite );
    createLine( relatedBooksComposite );
  }

  private Composite createRelatedTitleComposite( Composite parent ) {
    Composite composite = new Composite( parent, SWT.NONE );
    composite.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.TOP ).grab( true, false).create() );
    GridLayout layout = GridLayoutFactory.fillDefaults().spacing( 0, 0 ).numColumns( 1 ).equalWidth( false ).create();
    layout.marginWidth = 6;
    layout.marginTop = 12;
    layout.marginBottom = 6;
    composite.setLayout( layout );
    composite.setBackground( composite.getDisplay().getSystemColor( SWT.COLOR_WHITE ) );
    return composite;
  }

  private void createRelatedBooksTitle( Composite composite ) {
    Label relatedBooksLabel = new Label( composite, SWT.NONE );
    relatedBooksLabel.setText( "Related Books" );
    relatedBooksLabel.setFont( getFontRegistry().get( RELATED_BOOKS_FONT ) );
    relatedBooksLabel.setForeground( composite.getDisplay().getSystemColor( SWT.COLOR_DARK_GRAY ) );
    GridData layoutData = new GridData( SWT.FILL, SWT.TOP, true, false );
    layoutData.horizontalIndent = 6;
    relatedBooksLabel.setLayoutData( layoutData );
  }

  private void createLine( Composite composite ) {
    Label line = new Label( composite, SWT.NONE );
    line.setBackground( composite.getDisplay().getSystemColor( SWT.COLOR_GRAY ) );
    GridData layoutData = new GridData( SWT.FILL, SWT.TOP, true, false );
    layoutData.heightHint = 1;
    line.setLayoutData( layoutData );
  }

  private void createRelatedList( Composite parent ) {
    relatedTreeViewer = BooksListPage.createTreeViewer( this, parent );
  }

  @Override
  public void activate() {
    // nothing to do here
  }

  @Override
  public void deactivate() {
    // nothing to do here
  }
}
