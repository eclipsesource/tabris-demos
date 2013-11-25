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
package com.eclipsesource.tabris.demos.ui.dynamic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;

import com.eclipsesource.tabris.ui.AbstractPage;
import com.eclipsesource.tabris.ui.ActionConfiguration;
import com.eclipsesource.tabris.ui.PageConfiguration;
import com.eclipsesource.tabris.ui.PageData;


public class DynamicPage extends AbstractPage {

  private final List<String> globalActiondIds;
  private final List<String> pageActiondIds;
  private final List<String> rootPageIds;

  public DynamicPage() {
    globalActiondIds = getSessionList( "globalActions" );
    rootPageIds = getSessionList( "rootPages" );
    pageActiondIds = new ArrayList<String>();
  }

  @SuppressWarnings("unchecked")
  private List<String> getSessionList( String attributeId ) {
    Object attribute = RWT.getUISession().getAttribute( attributeId );
    if( attribute == null ) {
      List<String> result = new ArrayList<String>();
      RWT.getUISession().setAttribute( attributeId, result );
      return result;
    }
    return ( List<String> )attribute;
  }

  @Override
  public void createContent( Composite parent, PageData data ) {
    RowLayout layout = new RowLayout( SWT.VERTICAL );
    layout.fill = true;
    parent.setLayout( layout );
    createButtons( parent );
  }

  private void createButtons( Composite parent ) {
    createAddRootPageButton( parent );
    createRemoveRootPageButton( parent );
    createAddPageButton( parent );
    createAddActionButton( parent );
    createRemoveActionButton( parent );
    createAddGlobalActionButton( parent );
    createRemoveGlobalActionButton( parent );
  }

  private void createAddRootPageButton( Composite parent ) {
    Button addRootPageButton = new Button( parent, SWT.PUSH );
    addRootPageButton.setText( "Add Root Page" );
    addRootPageButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        String id = String.valueOf( new Random().nextInt() );
        InputStream image = DynamicPage.class.getResourceAsStream( "/action_share.png" );
        ActionConfiguration actionConfiguration = new ActionConfiguration( id, DynamicAction.class ).setImage( image );
        PageConfiguration pageConfiguration = new PageConfiguration( id, DynamicPage.class ).setTitle( id ).setTopLevel( true );
        pageConfiguration.addActionConfiguration( actionConfiguration );
        getUIConfiguration().addPageConfiguration( pageConfiguration );
        PageData pageData = new PageData();
        pageData.set( "id", id );
        rootPageIds.add( id );
        openPage( id, pageData );
      }
    } );
  }

  private void createRemoveRootPageButton( final Composite parent ) {
    Button addRootPageButton = new Button( parent, SWT.PUSH );
    addRootPageButton.setText( "Remove Last Root Page" );
    addRootPageButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        if( !rootPageIds.isEmpty() ) {
          String lastId = rootPageIds.get( rootPageIds.size() - 1 );
          try {
            getUIConfiguration().removePageConfiguration( lastId );
            rootPageIds.remove( rootPageIds.size() - 1 );
          } catch( IllegalStateException ise ) {
            MessageBox messageBox = new MessageBox( parent.getShell() );
            messageBox.setMessage( "Can not remove active page with id " + lastId );
            messageBox.setText( "Not Allowed" );
            DialogUtil.open( messageBox, null );
          }
        }
      }
    } );
  }

  private void createAddPageButton( Composite parent ) {
    Button addPageButton = new Button( parent, SWT.PUSH );
    addPageButton.setText( "Add Page" );
    addPageButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        String id = String.valueOf( new Random().nextInt() );
        InputStream image = DynamicPage.class.getResourceAsStream( "/action_share.png" );
        ActionConfiguration actionConfiguration = new ActionConfiguration( id, DynamicAction.class ).setImage( image );
        PageConfiguration pageConfiguration = new PageConfiguration( id, DynamicPage.class ).setTitle( id ).setTopLevel( false );
        pageConfiguration.addActionConfiguration( actionConfiguration );
        getUIConfiguration().addPageConfiguration( pageConfiguration );
        PageData pageData = new PageData();
        pageData.set( "id", id );
        openPage( id, pageData );
      }
    } );
  }

  private void createAddActionButton( Composite parent ) {
    Button addActionButton = new Button( parent, SWT.PUSH );
    addActionButton.setText( "Add Page Action" );
    addActionButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        String id = String.valueOf( new Random().nextInt() );
        pageActiondIds.add( id );
        InputStream image = DynamicPage.class.getResourceAsStream( "/action_theme.png" );
        ActionConfiguration actionConfiguration = new ActionConfiguration( id, DynamicAction.class ).setImage( image );
        getUIConfiguration().getPageConfiguration( getPageId() ).addActionConfiguration( actionConfiguration );
      }
    } );
  }

  private void createRemoveActionButton( Composite parent ) {
    Button removeLastActionButton = new Button( parent, SWT.PUSH );
    removeLastActionButton.setText( "Remove Last Page Action" );
    removeLastActionButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        if( !pageActiondIds.isEmpty() ) {
          String lastId = pageActiondIds.get( pageActiondIds.size() - 1 );
          pageActiondIds.remove( pageActiondIds.size() - 1 );
          getUIConfiguration().getPageConfiguration( getPageId() ).removeActionConfiguration( lastId );
        }
      }
    } );
  }

  private void createAddGlobalActionButton( Composite parent ) {
    Button addGlobalActionButton = new Button( parent, SWT.PUSH );
    addGlobalActionButton.setText( "Add Global Action" );
    addGlobalActionButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        String id = String.valueOf( new Random().nextInt() );
        globalActiondIds.add( id );
        InputStream image = DynamicPage.class.getResourceAsStream( "/action_settings.png" );
        ActionConfiguration actionConfiguration = new ActionConfiguration( id, DynamicAction.class ).setImage( image );
        getUIConfiguration().addActionConfiguration( actionConfiguration );
      }
    } );
  }

  private void createRemoveGlobalActionButton( Composite parent ) {
    Button removeLastGlobalActionButton = new Button( parent, SWT.PUSH );
    removeLastGlobalActionButton.setText( "Remove Last Global Action" );
    removeLastGlobalActionButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        if( !globalActiondIds.isEmpty() ) {
          String lastId = globalActiondIds.get( globalActiondIds.size() - 1 );
          globalActiondIds.remove( globalActiondIds.size() - 1 );
          getUIConfiguration().removeActionConfiguration( lastId );
        }
      }
    } );
  }

  private String getPageId() {
    String id = getData().get( "id", String.class );
    if( id != null ) {
      return id;
    }
    return "root";
  }
}
