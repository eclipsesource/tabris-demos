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
import org.eclipse.rap.rwt.template.ImageCell;
import org.eclipse.rap.rwt.template.Template;
import org.eclipse.rap.rwt.template.TextCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

import com.eclipsesource.tabris.ui.AbstractPage;
import com.eclipsesource.tabris.ui.PageData;
import com.eclipsesource.tabris.ui.UI;

public class BooksListPage extends AbstractPage {

  private BookFilter bookFilter;
  private Composite container;
  private TreeViewer viewer;
  private static UI ui;

  public void setBookFilter( BookFilter bookFilter ) {
    this.bookFilter = bookFilter;
  }

  @Override
  public void createContent( Composite parent, PageData data ) {
    ui = getUI();
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
    tree.setLinesVisible( true );
    tree.setData( RWT.MARKUP_ENABLED, Boolean.TRUE );
    tree.setData( RWT.ROW_TEMPLATE, createRowTemplate() );
    tree.setData( RWT.CUSTOM_ITEM_HEIGHT, new Integer( 68 ) );
    tree.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.FILL ).grab( true, true ).create() );
    viewer.setLabelProvider( new BooksLabelProvider() );
    new TreeColumn( tree, SWT.LEFT );
    new TreeColumn( tree, SWT.LEFT );
    new TreeColumn( tree, SWT.LEFT );
    return viewer;
  }

  private static Template createRowTemplate() {
    Template template = new Template();
    addImageCell( template );
    addTitleCell( template );
    addAuthorCell( template );
    return template;
  }

  private static void addAuthorCell( Template template ) {
    TextCell authorCell = new TextCell( template );
    authorCell.setBindingIndex( 2 );
    authorCell.setLeft( 52 ).setRight( 10 ).setTop( 36 ).setBottom( SWT.DEFAULT );
    authorCell.setForeground( new Color( ui.getDisplay(), new RGB( 123, 123, 123 ) ) );
    authorCell.setFont( new Font( ui.getDisplay(), new FontData( "Verdana", 14, SWT.NONE ) ) );
  }

  private static void addTitleCell( Template template ) {
    TextCell titleCell = new TextCell( template );
    titleCell.setBindingIndex( 1 );
    titleCell.setLeft( 52 ).setRight( 10 ).setTop( 12 ).setBottom( SWT.DEFAULT );
    titleCell.setFont( new Font( ui.getDisplay(), new FontData( "Verdana", 18, SWT.NONE ) ) );
    titleCell.setForeground( new Color( ui.getDisplay(), new RGB( 74, 74, 74 ) ) );
  }

  private static void addImageCell( Template template ) {
    ImageCell imageCell = new ImageCell( template );
    imageCell.setBindingIndex( 0 );
    imageCell.setLeft( 10 ).setWidth( 32 ).setTop( 10 ).setHeight( 48 );
  }

  @SuppressWarnings( "unchecked" )
  private void createViewerInput( TreeViewer viewer ) {
    List books = ( List )RWT.getUISession().getAttribute( BOOKS );
    List<Book> filteredBooks = bookFilter.filter( books );
    viewer.setInput( filteredBooks );
  }

  private static void addBookSelectionListener( final AbstractPage page, TreeViewer viewer ) {
    viewer.addSelectionChangedListener( new ISelectionChangedListener() {
      @Override
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
