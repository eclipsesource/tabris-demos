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

import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

import com.eclipsesource.tabris.Store;
import com.eclipsesource.tabris.ui.Page;
import com.eclipsesource.tabris.ui.UIContext;

public class BooksListPage implements Page {

  private final BookFilter bookFilter;
  private Composite container;

  public BooksListPage( BookFilter bookFilter ) {
    this.bookFilter = bookFilter;
  }

  public void create( Composite parent, final UIContext context ) {
    container = new Composite( parent, SWT.NONE );
    container.setLayout( GridLayoutFactory.fillDefaults().spacing( 0, 0 ).numColumns( 1 ).equalWidth( false ).create() );
    TreeViewer viewer = createTreeViewer( context, container );
    createViewerInput( context, viewer );
  }
  
  public Composite getContainer() {
    return container;
  }

  public static TreeViewer createTreeViewer( final UIContext context, Composite container ) {
    TreeViewer viewer = new TreeViewer( container, SWT.V_SCROLL );
    viewer.setContentProvider( new BooksContentProvider() );
    viewer.setLabelProvider( new BooksLabelProvider() );
    addBookSelectionListener( context, viewer );
    Tree tree = viewer.getTree();
    tree.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.FILL ).grab( true, true ).create() );
    new TreeColumn( tree, SWT.LEFT );
    new TreeColumn( tree, SWT.LEFT );
    return viewer;
  }

  @SuppressWarnings("unchecked")
  private void createViewerInput( final UIContext context, TreeViewer viewer ) {
    List books = context.getGlobalStore().get( BOOKS, List.class );
    List<Book> filteredBooks = bookFilter.filter( books );
    viewer.setInput( filteredBooks );
  }

  private static void addBookSelectionListener( final UIContext context, TreeViewer viewer ) {
    viewer.addSelectionChangedListener( new ISelectionChangedListener() {
      public void selectionChanged( SelectionChangedEvent event ) {
        Object book = ( ( IStructuredSelection )event.getSelection() ).getFirstElement();
        if( book != null ) {
          Store store = new Store();
          store.add( BOOK_ITEM, book );
          context.getPageManager().showPage( BookDetailsPage.class.getName(), store );
        }
      }
    } );
  }

  public void activate( UIContext context ) {
    // nothing to do here
  }

  public void deactivate( UIContext context ) {
    // nothing to do here
  }
}
