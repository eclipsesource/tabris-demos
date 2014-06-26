/*******************************************************************************
 * Copyright (c) 2012 EclipseSource and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.entrypoints;

import static com.eclipsesource.tabris.widgets.enhancement.Widgets.onTree;

import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.eclipsesource.tabris.widgets.RefreshListener;
import com.eclipsesource.tabris.widgets.enhancement.RefreshHandler;

public class SimpleTreeDemo implements EntryPoint {

  @Override
  public int createUI() {
    Display display = new Display();
    Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    shell.setLayout( new FillLayout() );
    createContent( display, shell );
    shell.open();
    shell.setVisible( true );
    return 0;
  }

  private void createContent( Display display, Shell shell ) {
    Tree tree = new Tree( shell, SWT.BORDER );
    tree.setToolTipText( "Winchester Evangelium" );
    tree.setLinesVisible( true );
    onTree( tree ).enableMarkup( true );
    onTree( tree ).setBackButtonNavigationEnabled( true );
    setupPullToRefresh( tree );
    addTreeItems( tree );
    tree.addListener( SWT.Expand, new Listener() {

      @Override
      public void handleEvent( Event event ) {
        TreeItem item = ( TreeItem )event.item;
        System.out.println( "Item selected: " + item.getText() );
      }
    } );
  }

  public void addTreeItems( Tree tree ) {
    for( int i = 0; i < 4; i++ ) {
      TreeItem iItem = new TreeItem( tree, 0 );
      iItem.setText( getMainCharacterName( i ) );
      for( int j = 0; j < 8; j++ ) {
        TreeItem jItem = new TreeItem( iItem, 0 );
        jItem.setText( getSideCharacterName( j ) );
        for( int k = 0; k < 30; k++ ) {
          TreeItem kItem = new TreeItem( jItem, 0 );
          kItem.setText( "Season <b>5</b>, Episode <b>" + k + "</b>" );
        }
      }
    }
  }

  private void setupPullToRefresh( final Tree tree ) {
    final RefreshHandler handler = new RefreshHandler();
    handler.setMessage( "Spoiler Alert!" );
    handler.addRefreshListener( new RefreshListener() {
      @Override
      public void refresh() {
        markDead( tree.getItems() );
        tree.update();
        handler.done();
      }
    } );
    onTree( tree ).setRefreshHandler( handler  );
  }

  private void markDead( TreeItem[] items ) {
    for( TreeItem item : items ) {
      markDead( item.getItems() );
      String name = item.getText();
      if( !survives( name ) && !name.contains( "Deceased" ) ) {
        item.setText( name + " <b>[Deceased]</b>" );
      }
    }
  }

  private String getSideCharacterName( int j ) {
    String result = "";
    switch( j ) {
      case 0:
        result = "Bobby Singer";
      break;
      case 1:
        result = "Ellen Harvelle";
      break;
      case 2:
        result = "Johanna Harvelle";
      break;
      case 3:
        result = "Gordon Walker";
      break;
      case 4:
        result = "Samuel Campbell";
      break;
      case 5:
        result = "Rufus Turner";
      break;
      case 6:
        result = "Gwen Campbell";
      break;
      case 7:
        result = "Castiel";
      break;
    }
    return result;
  }

  private String getMainCharacterName( int i ) {
    String result = "";
    switch( i ) {
      case 0:
        result = "John Winchester";
      break;
      case 1:
        result = "Mary Winchester";
      break;
      case 2:
        result = "Dean Winchester";
      break;
      case 3:
        result = "Sam Winchester";
      break;
      default:
        result = "Not known";
      break;
    }
    return result;
  }
  
  private boolean survives( String name ) {
    boolean survivor = false;
    if( (contains( name, "Castiel" ) || contains( name, "Sam" ) || contains( name, "Dean" )) && !contains( name, "Samuel" ) ) {
      survivor = true;
    }
    return survivor;
  }

  private boolean contains( String string, String substring ) {
    return string.indexOf( substring ) >= 0;
  }

}
