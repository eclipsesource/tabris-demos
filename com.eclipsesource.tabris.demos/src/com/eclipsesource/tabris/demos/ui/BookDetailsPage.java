package com.eclipsesource.tabris.demos.ui;

import static com.eclipsesource.tabris.demos.ui.SharedFont.ITEM_TITLE;
import static com.eclipsesource.tabris.widgets.enhancement.Widgets.onComposite;
import static org.eclipse.jface.resource.JFaceResources.getFontRegistry;

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
import com.eclipsesource.tabris.demos.entrypoints.UiUtil;
import com.eclipsesource.tabris.ui.Page;
import com.eclipsesource.tabris.ui.UIContext;

@SuppressWarnings("serial")
public class BookDetailsPage implements Page {

  public static final String ID = BookDetailsPage.class.getName();
  protected static final String BOOK_ITEM = "bookItem";
  private Label titleLabel;
  private Label authorLabel;
  private Label imageLabel;
  private TreeViewer relatedTreeViewer;
  private Composite bookDetailsComposite;

  public void create( Composite parent, UIContext context ) {
    Composite container = new Composite( parent, SWT.NONE );
    container.setLayout( UiUtil.createGridLayout( 1, false ) );
    createBookDetailsComposite( container );
    createRelatedComposite( container, context );
    populatePage( context );
  }

  private void populatePage( final UIContext context ) {
    final Book book = context.getPageStore().get( BOOK_ITEM, Book.class );
    context.setTitle( book.getTitle() );
    titleLabel.setText( book.getTitle() );
    authorLabel.setText( book.getAuthor() );
    imageLabel.setImage( book.getImage() );
    relatedTreeViewer.setInput( book.getRelated() );
    onComposite( bookDetailsComposite ).addGroupedListener( SWT.MouseUp, new Listener() {

      public void handleEvent( Event event ) {
        Store readStore = new Store();
        readStore.add( ReadBookPage.BOOK_ITEM, book );
        context.showPage( ReadBookPage.ID, readStore );
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
    bookDetailsComposite.setLayoutData( UiUtil.createFillHori() );
    onComposite( bookDetailsComposite ).showLocalTouch();
    GridLayout layout = UiUtil.createGridLayout( 2, false );
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
    titleLabel.setFont( getFontRegistry().get( ITEM_TITLE ) );
    titleLabel.setLayoutData( UiUtil.createFillHori() );
  }

  private void createBookAuthor() {
    authorLabel = new Label( bookDetailsComposite, SWT.WRAP );
    authorLabel.setLayoutData( UiUtil.createFillHori() );
  }

  private void createRelatedComposite( Composite parent, UIContext context ) {
    Group group = new Group( parent, SWT.NONE );
    group.setLayoutData( UiUtil.createFill() );
    group.setText( "Related Books" );
    group.setLayout( UiUtil.createGridLayout( 1, false ) );
    relatedTreeViewer = BooksListPage.createTreeViewer( context, group );
  }

  public void activate( final UIContext context ) {
    // nothing to do here
  }

  public void deactivate( UIContext context ) {
    // nothing to do here
  }
}
