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
import org.eclipse.swt.widgets.Group;
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
    createRelatedComposite( container, context );
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
    layout.marginWidth = 16;
    layout.marginHeight = 16;
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

  private void createRelatedComposite( Composite parent, UIContext context ) {
    Group group = new Group( parent, SWT.NONE );
    group.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.FILL ).grab( true, true ).create() );
    group.setText( "Related Books" );
    group.setLayout( GridLayoutFactory.fillDefaults().spacing( 0, 0 ).numColumns( 1 ).equalWidth( false ).create() );
    relatedTreeViewer = BooksListPage.createTreeViewer( context, group );
  }

  public void activate( final UIContext context ) {
    // nothing to do here
  }

  public void deactivate( UIContext context ) {
    // nothing to do here
  }
}
