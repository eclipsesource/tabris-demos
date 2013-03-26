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
import static com.eclipsesource.tabris.demos.ui.Constants.BOOK_ITEM;
import static com.eclipsesource.tabris.demos.ui.Constants.RELATED_BOOKS_FONT;
import static com.eclipsesource.tabris.demos.ui.Constants.TITLE_FONT;

import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

import com.eclipsesource.tabris.ui.Page;
import com.eclipsesource.tabris.ui.PageData;
import com.eclipsesource.tabris.ui.UI;

public class BooksListPage implements Page {

  private final BookFilter bookFilter;
  private Composite container;

  public BooksListPage( BookFilter bookFilter ) {
    this.bookFilter = bookFilter;
  }

  public void createContents( Composite parent, final UI ui ) {
    registerResources();
    createBooks( ui );
    container = new Composite( parent, SWT.NONE );
    container.setLayout( GridLayoutFactory.fillDefaults().spacing( 0, 0 ).numColumns( 1 ).equalWidth( false ).create() );
    TreeViewer viewer = createTreeViewer( ui, container );
    createViewerInput( ui, viewer );
  }

  private void registerResources() {
    FontRegistry fontRegistry = JFaceResources.getFontRegistry();
    fontRegistry.put( TITLE_FONT, createFontData( 16, SWT.BOLD ) );
    fontRegistry.put( RELATED_BOOKS_FONT, createFontData( 16, SWT.BOLD ) );
  }

  private FontData[] createFontData( int height, int style ) {
    return new FontData[]{ new FontData( "Verdana", height, style ) };
  }

  private void createBooks( final UI ui ) {
    Object books = RWT.getUISession().getAttribute( BOOKS );
    if( books == null ) {
      books = BookProvider.getBooks( ui );
      RWT.getUISession().setAttribute( BOOKS, books );
    }
  }

  public Composite getContainer() {
    return container;
  }

  public static TreeViewer createTreeViewer( final UI ui, Composite container ) {
    TreeViewer viewer = new TreeViewer( container, SWT.V_SCROLL );
    viewer.setContentProvider( new BooksContentProvider() );
    viewer.setLabelProvider( new BooksLabelProvider() );
    addBookSelectionListener( ui, viewer );
    Tree tree = viewer.getTree();
    tree.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.FILL ).grab( true, true ).create() );
    new TreeColumn( tree, SWT.LEFT );
    new TreeColumn( tree, SWT.LEFT );
    return viewer;
  }

  @SuppressWarnings("unchecked")
  private void createViewerInput( final UI ui, TreeViewer viewer ) {
    List books = ( List )RWT.getUISession().getAttribute( BOOKS );
    List<Book> filteredBooks = bookFilter.filter( books );
    viewer.setInput( filteredBooks );
  }

  private static void addBookSelectionListener( final UI ui, TreeViewer viewer ) {
    viewer.addSelectionChangedListener( new ISelectionChangedListener() {
      public void selectionChanged( SelectionChangedEvent event ) {
        Object book = ( ( IStructuredSelection )event.getSelection() ).getFirstElement();
        if( book != null ) {
          PageData data = new PageData();
          data.set( BOOK_ITEM, book );
          ui.getPageOperator().openPage( BookDetailsPage.class.getName(), data );
        }
      }
    } );
  }

  public void activate() {
    // nothing to do here
  }

  public void deactivate() {
    // nothing to do here
  }
}
