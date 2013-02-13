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

import static com.eclipsesource.tabris.demos.ui.Constants.*;
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

import com.eclipsesource.tabris.Store;
import com.eclipsesource.tabris.ui.Page;
import com.eclipsesource.tabris.ui.UIContext;

@SuppressWarnings("serial")
public class BookDetailsPage implements Page {

  private Label titleLabel;
  private Label authorLabel;
  private Label imageLabel;
  private TreeViewer relatedTreeViewer;
  private Composite bookDetailsComposite;

  public void create( Composite parent, UIContext context ) {
    Composite container = new Composite( parent, SWT.NONE );
    container.setLayout( GridLayoutFactory.fillDefaults().spacing( 0, 0 ).numColumns( 1 ).equalWidth( false ).create() );
    createBookDetailsComposite( container );
    createRelatedBooks( container, context );
    createRelatedList( container, context );
    populatePage( context );
  }

  private void populatePage( final UIContext context ) {
    Book book = context.getPageManager().getPageStore().get( BOOK_ITEM, Book.class );
    context.setTitle( book.getTitle() );
    titleLabel.setText( book.getTitle() );
    authorLabel.setText( book.getAuthor() );
    imageLabel.setImage( book.getImage() );
    relatedTreeViewer.setInput( book.getRelated() );
    addGroupedEventsListener( context, book );
  }

  private void addGroupedEventsListener( final UIContext context, final Book book ) {
    onComposite( bookDetailsComposite ).addGroupedListener( SWT.MouseUp, new Listener() {

      public void handleEvent( Event event ) {
        Store readStore = new Store();
        readStore.add( ReadBookPage.BOOK_ITEM, book );
        context.getPageManager().showPage( ReadBookPage.class.getName(), readStore );
      }
    } );
  }

  private void createBookDetailsComposite( Composite parent ) {
    createBookComposite( parent );
    createBooImage();
    createBookTitle();
    createBookAuthor();
  }

  private void createBookComposite( Composite parent ) {
    bookDetailsComposite = new Composite( parent, SWT.NONE );
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

  private void createBooImage() {
    imageLabel = new Label( bookDetailsComposite, SWT.WRAP );
    GridData gridData = new GridData( SWT.FILL, SWT.FILL, false, false );
    gridData.verticalSpan = 2;
    imageLabel.setLayoutData( gridData );
  }

  private void createBookTitle() {
    titleLabel = new Label( bookDetailsComposite, SWT.WRAP );
    titleLabel.setFont( getFontRegistry().get( TITLE_FONT ) );
    titleLabel.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.TOP ).grab( true, false ).create() );
  }

  private void createBookAuthor() {
    authorLabel = new Label( bookDetailsComposite, SWT.WRAP );
    authorLabel.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.TOP ).grab( true, false ).create() );
  }

  private void createRelatedBooks( Composite parent, UIContext context ) {
    Composite relatedBooksComposite = createRelatedTitleComposite( parent );
    createRelatedBooksTitle( context, relatedBooksComposite );
    createLine( context, relatedBooksComposite );
  }

  private Composite createRelatedTitleComposite( Composite parent ) {
    Composite composite = new Composite( parent, SWT.NONE );
    composite.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.TOP ).grab( true, false).create() );
    GridLayout layout = GridLayoutFactory.fillDefaults().spacing( 0, 0 ).numColumns( 1 ).equalWidth( false ).create();
    layout.marginWidth = 6;
    layout.marginTop = 12;
    layout.marginBottom = 6;
    composite.setLayout( layout );
    return composite;
  }

  private void createRelatedBooksTitle( UIContext context, Composite composite ) {
    Label relatedBooksLabel = new Label( composite, SWT.NONE );
    relatedBooksLabel.setText( "Related Books" );
    relatedBooksLabel.setFont( getFontRegistry().get( RELATED_BOOKS_FONT ) );
    relatedBooksLabel.setForeground( context.getDisplay().getSystemColor( SWT.COLOR_DARK_GRAY ) );
    GridData layoutData = new GridData( SWT.FILL, SWT.TOP, true, false );
    layoutData.horizontalIndent = 6;
    relatedBooksLabel.setLayoutData( layoutData );
  }

  private void createLine( UIContext context, Composite composite ) {
    Label line = new Label( composite, SWT.NONE );
    line.setBackground( context.getDisplay().getSystemColor( SWT.COLOR_GRAY ) );
    GridData layoutData = new GridData( SWT.FILL, SWT.TOP, true, false );
    layoutData.heightHint = 1;
    line.setLayoutData( layoutData );
  }
  
  private void createRelatedList( Composite parent, UIContext context ) {
    relatedTreeViewer = BooksListPage.createTreeViewer( context, parent );
  }

  public void activate( final UIContext context ) {
    // nothing to do here
  }

  public void deactivate( UIContext context ) {
    // nothing to do here
  }
}
