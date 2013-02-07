/*******************************************************************************
 * Copyright (c) 2013 EclipseSource and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.ui;

import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

import com.eclipsesource.tabris.Store;
import com.eclipsesource.tabris.demos.entrypoints.UiUtil;
import com.eclipsesource.tabris.ui.Page;
import com.eclipsesource.tabris.ui.UIContext;

public class BooksListPage implements Page {

  private final List<Book> books;
  private final IBookFilter bookFilter;

  public BooksListPage( List<Book> books, IBookFilter bookFilter ) {
    this.books = books;
    this.bookFilter = bookFilter;
  }

  public void create( Composite parent, final UIContext context ) {
    Composite container = new Composite( parent, SWT.NONE );
    container.setLayout( UiUtil.createGridLayout( 1, false ) );
    TreeViewer viewer = createTreeViewer( context, container );
    List<Book> filteredBooks = bookFilter.filter( books );
    viewer.setInput( filteredBooks );
  }

  public static TreeViewer createTreeViewer( final UIContext context, Composite container ) {
    TreeViewer viewer = new TreeViewer( container, SWT.V_SCROLL );
    viewer.setContentProvider( new BooksContentProvider() );
    viewer.setLabelProvider( new BooksLabelProvider() );
    viewer.addSelectionChangedListener( new ISelectionChangedListener() {

      public void selectionChanged( SelectionChangedEvent event ) {
        Object book = ( ( IStructuredSelection )event.getSelection() ).getFirstElement();
        if( book != null ) {
          Store store = new Store();
          store.add( BookDetailsPage.BOOK_ITEM, book );
          context.showPage( BookDetailsPage.class.getName(), store );
        }
      }
    } );
    Tree tree = viewer.getTree();
    tree.setLayoutData( UiUtil.createFill() );
    new TreeColumn( tree, SWT.LEFT );
    new TreeColumn( tree, SWT.LEFT );
    return viewer;
  }

  public void activate( UIContext context ) {
    // nothing to do here
  }

  public void deactivate( UIContext context ) {
    // nothing to do here
  }
}
