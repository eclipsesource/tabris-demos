/*******************************************************************************
 * Copyright (c) 2013, 2018 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.entrypoints;

import static com.eclipsesource.tabris.widgets.enhancement.Widgets.onToolItem;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.eclipsesource.tabris.TabrisClient;
import com.eclipsesource.tabris.demos.swipe.DictionarySwipeItemProvider;
import com.eclipsesource.tabris.widgets.PagingIndicator;
import com.eclipsesource.tabris.widgets.swipe.Swipe;
import com.eclipsesource.tabris.widgets.swipe.SwipeAdapter;
import com.eclipsesource.tabris.widgets.swipe.SwipeContext;
import com.eclipsesource.tabris.widgets.swipe.SwipeItem;

public class SwipeDemo implements EntryPoint {

  @Override
  public int createUI() {
    Display display = new Display();
    final Shell shell = createShell( display );
    if( RWT.getClient() instanceof TabrisClient ) {
      createToolBar( shell );
      Composite container = createParentComposite( shell );
      createSwipeWidget( container );
    } else {
      createWebClientContent( shell );
    }
    shell.open();
    return 0;
  }

  private Composite createParentComposite( final Shell shell ) {
    Composite comp = new Composite( shell, SWT.NONE );
    GridLayoutFactory.fillDefaults().margins( 0, 5 ).applyTo( comp );
    GridDataFactory.fillDefaults().align( SWT.FILL, SWT.FILL ).grab( true, true ).applyTo( comp );
    return comp;
  }

  private Shell createShell( Display display ) {
    final Shell shell = new Shell( display, SWT.NONE );
    shell.setMaximized( true );
    GridLayoutFactory.fillDefaults().applyTo( shell );
    return shell;
  }

  private void createToolBar( final Composite parent ) {
    ToolBar toolBar = new ToolBar( parent, SWT.NONE );
    GridDataFactory.fillDefaults().align( SWT.FILL, SWT.TOP ).grab( true, false ).applyTo( toolBar );
    ToolItem toolItem = new ToolItem( toolBar, SWT.NONE );
    toolItem.setText( "Klingon Lessons" );
    onToolItem( toolItem ).useAsTitle();
  }

  private void createSwipeWidget( Composite parent ) {
    DictionarySwipeItemProvider itemProvider = new DictionarySwipeItemProvider();
    Swipe result = new Swipe( parent, itemProvider );
    result.setCacheSize( 2 );
    GridDataFactory.fillDefaults()
      .grab( true, true )
      .align( SWT.FILL, SWT.FILL )
      .applyTo( result.getControl() );
    result.show( 0 );
    createPagingIndicator( parent, result, itemProvider.getItemCount() );
  }

  private void createPagingIndicator( Composite parent, Swipe swipe, int itemCount ) {
    final PagingIndicator pagingIndicator = new PagingIndicator( parent );
    pagingIndicator.setSpacing( 5 );
    pagingIndicator.setCount( itemCount );
    swipe.addSwipeListener( new SwipeAdapter() {
      @Override
      public void itemActivated(SwipeItem item, int index, SwipeContext context) {
        pagingIndicator.setActive( index );
      }
    } );
    GridDataFactory.fillDefaults()
      .grab( true, false )
      .align( SWT.FILL, SWT.FILL )
      .applyTo( pagingIndicator );
  }

  private void createWebClientContent( final Shell shell ) {
    MessageBox messageBox = new MessageBox( shell, SWT.ICON_WARNING );
    messageBox.setMessage( "This demo is availaible on mobile devices only." );
    messageBox.setText( "Tabris Demo" );
    shell.open();
    messageBox.open( null );
  }

}