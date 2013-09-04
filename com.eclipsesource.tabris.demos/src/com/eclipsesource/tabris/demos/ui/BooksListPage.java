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

import static com.eclipsesource.tabris.demos.ui.BookDetailsPage.BOOK_ITEM;
import static com.eclipsesource.tabris.demos.ui.Constants.BOOKS;
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

import com.eclipsesource.tabris.device.ClientDevice;
import com.eclipsesource.tabris.ui.AbstractPage;
import com.eclipsesource.tabris.ui.PageData;

public class BooksListPage extends AbstractPage {

  private BookFilter bookFilter;
  private Composite container;
  private TreeViewer viewer;

  public void setBookFilter( BookFilter bookFilter ) {
    this.bookFilter = bookFilter;
  }

  @Override
  public void createContent( Composite parent, PageData data ) {
    registerResources();
    createBooks();
    container = new Composite( parent, SWT.NONE );
    container.setLayout( GridLayoutFactory.fillDefaults().spacing( 0, 0 ).numColumns( 1 ).equalWidth( false ).create() );
    viewer = createTreeViewer( this, container );
    createViewerInput( viewer );
  }

  private void registerResources() {
    FontRegistry fontRegistry = JFaceResources.getFontRegistry();
    fontRegistry.put( TITLE_FONT, createFontData( 16, SWT.BOLD ) );
    fontRegistry.put( RELATED_BOOKS_FONT, createFontData( 16, SWT.BOLD ) );
  }

  private FontData[] createFontData( int height, int style ) {
    return new FontData[]{ new FontData( "Verdana", height, style ) };
  }

  private void createBooks() {
    Object books = RWT.getUISession().getAttribute( BOOKS );
    if( books == null ) {
      books = BookProvider.getBooks( getUI().getDisplay() );
      RWT.getUISession().setAttribute( BOOKS, books );
    }
  }

  public Composite getContainer() {
    return container;
  }

  public static TreeViewer createTreeViewer( AbstractPage page, Composite container ) {
    TreeViewer viewer = new TreeViewer( container, SWT.V_SCROLL | SWT.FULL_SELECTION );
    viewer.setContentProvider( new BooksContentProvider() );
    addBookSelectionListener( page, viewer );
    Tree tree = viewer.getTree();
    tree.setData( RWT.MARKUP_ENABLED, Boolean.TRUE );
    tree.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.FILL ).grab( true, true ).create() );
    if( RWT.getClient().getService( ClientDevice.class ) != null ) {
      viewer.setLabelProvider( new MobileBooksLabelProvider() );
      new TreeColumn( tree, SWT.LEFT ).setWidth( 500 );
      new TreeColumn( tree, SWT.LEFT ).setWidth( 200 );
    } else {
      viewer.setLabelProvider( new WebBookLabelProvider() );
    }
    return viewer;
  }

  @SuppressWarnings("unchecked")
  private void createViewerInput( TreeViewer viewer ) {
    List books = ( List )RWT.getUISession().getAttribute( BOOKS );
    List<Book> filteredBooks = bookFilter.filter( books );
    viewer.setInput( filteredBooks );
  }

  private static void addBookSelectionListener( final AbstractPage page, TreeViewer viewer ) {
    viewer.addSelectionChangedListener( new ISelectionChangedListener() {
      public void selectionChanged( SelectionChangedEvent event ) {
        Object book = ( ( IStructuredSelection )event.getSelection() ).getFirstElement();
        if( book != null ) {
          PageData data = new PageData();
          data.set( BOOK_ITEM, book );
          page.openPage( BookDetailsPage.class.getName(), data );
        }
      }
    } );
  }

  @Override
  public void activate() {
    super.activate();
    viewer.setSelection( null );
  }
}
