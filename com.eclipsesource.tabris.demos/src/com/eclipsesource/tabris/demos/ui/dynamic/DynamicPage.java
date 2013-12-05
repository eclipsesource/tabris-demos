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
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridLayout;
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
  private Color COLOR_RED;
  private Color COLOR_GREEN;
  private Color COLOR_WHITE;

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
    initColors();
    GridLayout layout = new GridLayout();
    parent.setLayout( layout );
    createButtons( parent );
  }

  private void initColors() {
    COLOR_RED = new Color( getUI().getDisplay(), new RGB( 210, 50, 20 ) );
    COLOR_WHITE = new Color( getUI().getDisplay(), new RGB( 255, 255, 255 ) );
    COLOR_GREEN = getUI().getDisplay().getSystemColor( SWT.COLOR_DARK_GREEN );
  }

  private void createButtons( Composite parent ) {
    createAddRootPageButton( parent );
    createAddPageButton( parent );
    createAddGlobalActionButton( parent );
    createAddActionButton( parent );
    createSeparator( parent );
    createRemoveRootPageButton( parent );
    createRemoveGlobalActionButton( parent );
    createRemoveActionButton( parent );
  }

  private void createSeparator( Composite parent ) {
    Composite separator = new Composite( parent, SWT.NONE );
    separator.setLayoutData( GridDataFactory.fillDefaults().grab( true, true ).create() );
  }

  private void createAddRootPageButton( Composite parent ) {
    Button addRootPageButton = new Button( parent, SWT.PUSH );
    addRootPageButton.setText( "Add Root Page" );
    colorAddButton( addRootPageButton );
    addRootPageButton.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.TOP ).grab( true, false ).create() );
    applyImage( "/add_root_page.png", addRootPageButton );
    addRootPageButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        String id = createId();
        InputStream image = DynamicPage.class.getResourceAsStream( "/action_share.png" );
        ActionConfiguration actionConfiguration = new ActionConfiguration( id, DynamicAction.class ).setImage( image );
        PageConfiguration pageConfiguration = new PageConfiguration( id, DynamicPage.class )
                                                .setTitle( "Root Page: " + id )
                                                .setTopLevel( true );
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
    Button removeRootPageButton = new Button( parent, SWT.PUSH );
    removeRootPageButton.setText( "Remove Last Root Page" );
    removeRootPageButton.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.TOP ).grab( true, false ).create() );
    colorRemoveButton( removeRootPageButton );
    applyImage( "/remove_root_page.png", removeRootPageButton );
    removeRootPageButton.addSelectionListener( new SelectionAdapter() {
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
    colorAddButton( addPageButton );
    addPageButton.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.TOP ).grab( true, false ).create() );
    applyImage( "/add_page.png", addPageButton );
    addPageButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        String id = createId();
        PageConfiguration pageConfiguration = new PageConfiguration( id, DynamicPage.class )
                                              .setTitle( "Page: " + id )
                                              .setTopLevel( false );
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
    addActionButton.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.TOP ).grab( true, false ).create() );
    colorAddButton( addActionButton );
    applyImage( "/action.png", addActionButton );
    addActionButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        String id = createId();
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
    colorRemoveButton( removeLastActionButton );
    removeLastActionButton.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.TOP ).grab( true, false ).create() );
    applyImage( "/action.png", removeLastActionButton );
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
    addGlobalActionButton.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.TOP ).grab( true, false ).create() );
    colorAddButton( addGlobalActionButton );
    applyImage( "/global_action.png", addGlobalActionButton );
    addGlobalActionButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        String id = createId();
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
    colorRemoveButton( removeLastGlobalActionButton );
    removeLastGlobalActionButton.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.TOP ).grab( true, false ).create() );
    applyImage( "/global_action.png", removeLastGlobalActionButton );
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

  private void colorAddButton( Button button ) {
    button.setBackground( COLOR_GREEN );
    button.setForeground( COLOR_WHITE );
  }

  private void colorRemoveButton( Button button ) {
    button.setBackground( COLOR_RED );
    button.setForeground( COLOR_WHITE );
  }

  private String getPageId() {
    return getUI().getPageOperator().getCurrentPageId();
  }

  private String createId() {
    SecureRandom random = new SecureRandom();
    return new BigInteger( 12, random ).toString( 6 );
  }

  private void applyImage( String file, Button button ) {
    button.setImage( new Image( button.getDisplay(), DynamicPage.class.getResourceAsStream( file ) ) );
  }
}
