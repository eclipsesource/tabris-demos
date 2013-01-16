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
package com.eclipsesource.tabris.demos.entrypoints;

import static com.eclipsesource.tabris.widgets.enhancement.Widgets.onToolItem;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.rap.rwt.widgets.DialogUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.eclipsesource.tabris.widgets.swipe.Swipe;
import com.eclipsesource.tabris.widgets.swipe.SwipeContext;
import com.eclipsesource.tabris.widgets.swipe.SwipeItem;
import com.eclipsesource.tabris.widgets.swipe.SwipeItemProvider;
import com.eclipsesource.tabris.widgets.swipe.SwipeListener;

public class SwipeDemo implements EntryPoint {

  private Swipe swipe;
  private DynamicSwipeItemProvider itemProvider;
  private Scale scale;
  private Shell shell;
  private Font numberFont;
  private Label label;
  private int totalItems;
  private ToolItem titleToolItem;
  private int selectionIndex;
  private final KlingonDictionary dictionary = new KlingonDictionary();
  private Button removeItem;

  private final class DynamicSwipeItemProvider implements SwipeItemProvider {

    private final class NumberSwipeItem implements SwipeItem {

      private final int number;

      public NumberSwipeItem( int number ) {
        this.number = number;
      }

      public Control load( Composite parent ) {
        Composite page = new Composite( parent, SWT.NONE );
        page.setLayout( UiUtil.createGridLayout( 1, false ) );
        Composite bg = new Composite( page, SWT.NONE );
        GridLayout layout = UiUtil.createGridLayout( 1, false );
        layout.marginWidth = 16;
        layout.marginBottom = 16;
        bg.setLayout( layout );
        bg.setLayoutData( UiUtil.createFill() );
        Composite composite = new Composite( bg, SWT.NONE );
        composite.setBackground( parent.getDisplay().getSystemColor( SWT.COLOR_GRAY ) );
        composite.setLayout( UiUtil.createGridLayout( 1, false ) );
        composite.setLayoutData( UiUtil.createFill() );
        Label label = new Label( composite, SWT.NONE );
        label.setText( "Lesson [ " + number + " ]\n\n" + loadKlingonLesson() );
        label.setFont( numberFont );
        label.setLayoutData( UiUtil.createCenteredGrab() );
        composite.layout( true );
        return page;
      }

      private String loadKlingonLesson() {
        String[] entry = dictionary.getRandomEntry();
        StringBuilder builder = new StringBuilder();
        builder.append( "English: " + entry[ 0 ] );
        builder.append( "\n" );
        builder.append( "Klingon: " + entry[ 1 ] );
        return builder.toString();
      }

      public boolean isPreloadable() {
        return true;
      }

      public void deactivate( SwipeContext context ) {

      }

      public void activate( SwipeContext context ) {

      }
    }

    private final List<Integer> content;
    private int index;

    public DynamicSwipeItemProvider() {
      content = new ArrayList<Integer>();
      index = 0;
      for( int i = 0; i < 5; i++ ) {
        index = i;
        content.add( Integer.valueOf( i ) );
      }
    }

    public int getItemCount() {
      return content.size();
    }

    public SwipeItem getItem( int index ) {
      return new NumberSwipeItem( content.get( index ).intValue() );
    }

    public void addItem() {
      content.add( Integer.valueOf( ++index ) );
    }

    public void removeItem() {
      content.remove( index-- );
    }
  }

  public int createUI() {
    final Display display = new Display();
    numberFont = new Font( display, new FontData( "Arial", 48, SWT.BOLD ) );
    shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    shell.setLayout( new FillLayout() );
    Composite mainComp = new Composite( shell, SWT.NONE );
    mainComp.setLayout( UiUtil.createGridLayout( 1, false ) );
    createToolbar( mainComp );
    itemProvider = new DynamicSwipeItemProvider();
    totalItems = itemProvider.getItemCount();
    updateTitle();
    Composite parent = createContentComposite( mainComp );
    Composite groupConfiguration = createGroup( parent );
    createAddRemoveButtons( groupConfiguration );
    createCacheSize( groupConfiguration );
    createLocks( groupConfiguration );
    Composite groupControl = createGroup( parent );
    createScale( groupControl );
    createSwipe( parent );
    shell.open();
    return 0;
  }

  private void createCacheSize( Composite parent ) {
    label = new Label( parent, SWT.NONE );
    label.setText( "Cache size:" );
    GridData layoutData = new GridData( SWT.BEGINNING, SWT.CENTER, true, false );
    layoutData.horizontalIndent = 8;
    label.setLayoutData( layoutData );
    final Combo cacheSizeCombo = new Combo( parent, SWT.NONE );
    cacheSizeCombo.setLayoutData( UiUtil.createFillHori() );
    for( int i = 1; i < 10; i++ ) {
      cacheSizeCombo.add( String.valueOf( i ) );
    }
    cacheSizeCombo.select( 0 );
    cacheSizeCombo.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e ) {
        swipe.setCacheSize( cacheSizeCombo.getSelectionIndex() );
      }
    } );
  }

  private Composite createContentComposite( Composite parent ) {
    Composite contentComp = new Composite( parent, SWT.NONE );
    contentComp.setLayoutData( UiUtil.createFill() );
    GridLayout layout = UiUtil.createGridLayout( 1, true );
    layout.marginLeft = 16;
    layout.marginRight = 16;
    contentComp.setLayout( layout );
    return contentComp;
  }

  private Composite createGroup( Composite parent ) {
    Composite contentComp = new Composite( parent, SWT.NONE );
    contentComp.setLayoutData( UiUtil.createFillHori() );
    GridLayout layout = UiUtil.createGridLayout( 2, true );
    layout.horizontalSpacing = 16;
    layout.verticalSpacing = 8;
    contentComp.setLayout( layout );
    return contentComp;
  }

  private void createAddRemoveButtons( Composite parent ) {
    Button addItem = new Button( parent, SWT.PUSH );
    addItem.setText( "Add Lesson" );
    addItem.setLayoutData( UiUtil.createFillHori() );
    addItem.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( org.eclipse.swt.events.SelectionEvent e ) {
        itemProvider.addItem();
        notifyItemProviderChanged();
      }

    } );
    removeItem = new Button( parent, SWT.PUSH );
    removeItem.setLayoutData( UiUtil.createFillHori() );
    removeItem.setText( "Remove Lesson" );
    removeItem.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( org.eclipse.swt.events.SelectionEvent e ) {
        itemProvider.removeItem();
        notifyItemProviderChanged();
      }

    } );
  }

  private void createLocks( final Composite parent ) {
    final Button buttonLockLeft = new Button( parent, SWT.CHECK );
    buttonLockLeft.setText( "Lock Item left" );
    buttonLockLeft.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e ) {
        if( buttonLockLeft.getSelection() ) {
          buttonLockLeft.setText( "Lock left (" + selectionIndex + ")" );
          parent.layout( true, true );
          swipe.lock( SWT.LEFT );
        } else {
          swipe.unlock( SWT.LEFT );
          buttonLockLeft.setText( "Lock left" );
        }
      }

    } );

    final Button buttonLockRight = new Button( parent, SWT.CHECK );
    buttonLockRight.setText( "Lock Item right" );
    buttonLockRight.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e ) {
        if( buttonLockRight.getSelection() ) {
          buttonLockRight.setText( "Lock right (" + selectionIndex + ")" );
          parent.layout( true, true );
          swipe.lock( SWT.RIGHT );
        } else {
          swipe.unlock( SWT.RIGHT );
          buttonLockRight.setText( "Lock right" );
        }
      }

    } );
  }

  private void createSwipe( Composite parent ) {
    swipe = new Swipe( parent, itemProvider );
    Control control = swipe.getControl();
    control.setLayoutData( UiUtil.createFill() );

    swipe.addSwipeListener( new SwipeListener() {

      public void itemLoaded( SwipeItem item, int index ) {
        // do nothing
      }

      public void itemDeactivated( SwipeItem item, int index, SwipeContext context ) {

      }

      public void itemActivated( SwipeItem item, int index, SwipeContext context ) {
        scale.setSelection( index );
        selectionIndex = index;
        updateTitle();
        if( index == ( totalItems -1 ) ) {
          removeItem.setEnabled( false );
        } else {
          removeItem.setEnabled( true );
        }
      }

      public void disposed( SwipeContext context ) {
        // do nothing
      }
    } );

  }

  private void createScale( Composite parent ) {
    scale = new Scale( parent, SWT.NONE );
    GridData layoutData = UiUtil.createFillHori();
    layoutData.horizontalSpan = 2;
    scale.setLayoutData( layoutData );
    scale.setMinimum( 0 );
    scale.setMaximum( itemProvider.getItemCount() - 1 );
    scale.setIncrement( 1 );
    scale.setSelection( 0 );
    scale.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e ) {
        swipe.show( scale.getSelection() );
      }
    } );
  }

  private ToolBar createToolbar( Composite parent ) {
    ToolBar toolBar = new ToolBar( parent, SWT.HORIZONTAL );
    toolBar.setLayoutData( UiUtil.createFillHori() );
    titleToolItem = new ToolItem( toolBar, SWT.PUSH );
    onToolItem( titleToolItem ).useAsTitle();
    titleToolItem.setText( "Klingon Lessons" );
    return toolBar;
  }

  private void notifyItemProviderChanged() {
    try {
      swipe.refresh();
      scale.setMaximum( itemProvider.getItemCount() - 1 );
      totalItems = itemProvider.getItemCount();
      updateTitle();
      shell.layout( true, true );
    } catch( IllegalStateException e ) {
      MessageBox messageBox = new MessageBox( shell, SWT.ICON_WARNING );
      messageBox.setMessage( "Can not remove last item" );
      DialogUtil.open( messageBox, null );
    }
  }

  private void updateTitle() {
    titleToolItem.setText( "Klingon Lessons (" + ( selectionIndex + 1 ) + " of " + totalItems + ")" );
  }

}
