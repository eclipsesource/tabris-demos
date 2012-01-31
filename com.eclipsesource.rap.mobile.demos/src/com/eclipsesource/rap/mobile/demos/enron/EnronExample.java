/*******************************************************************************
 * Copyright (c) 2009, 2011 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.rap.mobile.demos.enron;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ILazyTreeContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TreeColumn;

import com.eclipsesource.rap.mobile.demos.enron.EnronDataset.Folder;
import com.eclipsesource.rap.mobile.demos.enron.EnronDataset.Node;

public class EnronExample {

  private static final String DEFAULT_DATASET_DIR = "YOUR_ENRON_DIRECTORY/enron_mail_20110402/maildir";
  private static final String DATASET_DIR_PROP = "org.eclipse.rap.demo.enronDatasetDirectory";
  private TreeViewer viewer;
  private Composite parent;

  public void createMainArea( Composite parent ) {
    this.parent = parent;
    createTreeArea( parent );
  }

  private void createTreeArea( Composite parent ) {
    Composite composite = new Composite( parent, SWT.NONE );
    GridLayout layout = ExampleUtil.createGridLayout();
    composite.setLayout( layout );

    viewer = new TreeViewer( composite, SWT.SINGLE | SWT.VIRTUAL );
    viewer.setUseHashlookup( true );
    TreeColumn treeColumn = new TreeColumn( viewer.getTree(), SWT.NONE );
    treeColumn.setWidth( 200 );
    TreeColumn treeColumn2 = new TreeColumn( viewer.getTree(), SWT.NONE );
    treeColumn2.setWidth( 200 );
    viewer.getTree().setToolTipText( "Enron Mailbox" );
    viewer.getControl().setLayoutData( ExampleUtil.createFillData() );
    viewer.setLabelProvider( new EnronLabelProvider( parent.getDisplay() ) );
    viewer.setContentProvider( new EnronLazyContentProvider( viewer ) );
    viewer.setInput( getDataSet() );
    viewer.addSelectionChangedListener( new ISelectionChangedListener() {

      public void selectionChanged( SelectionChangedEvent event ) {
        IStructuredSelection selection = ( IStructuredSelection )event.getSelection();
        Object firstElement = selection.getFirstElement();
        if( firstElement instanceof Node ) {
          nodeSelected( ( Node )firstElement );
        }
      }
    } );
  }

  private void nodeSelected( Node selectedNode ) {
    if( selectedNode != null ) {
      if( selectedNode.getFile().isFile() ) {
        try {
          Mail mail = new Mail( selectedNode.readContents() );
          Shell mailShell = new Shell( parent.getDisplay(), SWT.NO_TRIM );
          mailShell.setBackground( mailShell.getDisplay().getSystemColor( SWT.COLOR_DARK_GRAY ) );
          mailShell.setMaximized( true );
          mailShell.setLayout( new FillLayout() );
          createContentArea( mailShell, mail );
          mailShell.open();
        } catch( IOException exception ) {
          throw new RuntimeException( "Failed to read contents from node", exception );
        }
      }
    }
  }

  private void createContentArea( Composite parent, Mail mail ) {
    final Composite composite = new Composite( parent, SWT.NONE );
    GridLayout layout = ExampleUtil.createGridLayout();
    composite.setLayout( layout );
    createCloseButtonToolbar(composite);
    createMailHeaderArea( composite, mail );
    createMailContentArea( composite, mail );
  }

  private void createCloseButtonToolbar(final Composite parent) {
    final ToolBar toolBar = new ToolBar(parent, SWT.NONE);
    GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(toolBar);
    new ToolItem(toolBar, SWT.SEPARATOR);
    ToolItem closeToolItem = new ToolItem(toolBar, SWT.PUSH);
    closeToolItem.setText("Close");
    closeToolItem.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        parent.getShell().close();
      }
    });
  }
  
  private void createMailHeaderArea( Composite parent, Mail mail ) {
    Composite header = new Composite( parent, SWT.NONE );
    header.setLayoutData( ExampleUtil.createHorzFillData() );
    GridLayout headerLayout = new GridLayout( 2, false );
    headerLayout.horizontalSpacing = 5;
    headerLayout.verticalSpacing = 5;
    header.setLayout( headerLayout );
    Color foreground = parent.getDisplay().getSystemColor(SWT.COLOR_WHITE);
    Label senderLabel = new Label( header, SWT.NONE );
    senderLabel.setForeground(foreground);
    senderLabel.setText( "From:" );
    senderLabel.setLayoutData( new GridData( SWT.BEGINNING, SWT.CENTER, false, false ) );
    Text senderText = new Text( header, SWT.SINGLE | SWT.READ_ONLY );
    senderText.setText( mail.sender );
    senderText.setForeground(foreground);
    GridData senderTextData = ExampleUtil.createHorzFillData();
    senderTextData.horizontalIndent = 2;
    senderText.setLayoutData( senderTextData );
    Label subjectLabel = new Label( header, SWT.NONE );
    subjectLabel.setForeground(foreground);
    subjectLabel.setText( "Subject:" );
    subjectLabel.setLayoutData( new GridData( SWT.BEGINNING, SWT.CENTER, false, false ) );
    Text subjectText = new Text( header, SWT.SINGLE | SWT.READ_ONLY );
    subjectText.setText( mail.subject );
    subjectText.setForeground(foreground);
    subjectText.setLayoutData( senderTextData );
  }
  
  private void createMailContentArea( Composite parent, Mail mail ) {
    Text messageText = new Text( parent, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY );
    GridData messageTextData = ExampleUtil.createFillData();
    messageText.setLayoutData( messageTextData );
    messageText.setText( mail.content );
  }

  private static Node getDataSet() {
    try {
      File root = getRootDirectory();
      return new EnronDataset( root ).getRootNode();
    } catch( IOException exception ) {
      throw new IllegalStateException( "Could not access data model", exception );
    }
  }

  private static File getRootDirectory() {
    String path = getRootDirectoryPath();
    File root = new File( path );
    if( !root.isDirectory() ) {
      throw new RuntimeException( "Enron dataset directory missing: " + root );
    }
    return root;
  }

  private static String getRootDirectoryPath() {
    String path = System.getProperty( DATASET_DIR_PROP );
    if( path == null ) {
      path = DEFAULT_DATASET_DIR;
    }
    return path;
  }

  private static class Mail {
    
    private String sender;
    private String subject;
    private String content;

    public Mail( String text ) {
      String[] lines = text.split( "\n" );
      StringBuilder buffer = new StringBuilder();
      boolean headerFinished = false;
      for( String line : lines ) {
        if( headerFinished ) {
          buffer.append( line );
          buffer.append( "\n" );
        } else if( line.startsWith( "From:" ) ) {
          sender = line.substring( "From:".length() ).trim();
        } else if( line.startsWith( "Subject:" ) ) {
          subject = line.substring( "Subject:".length() ).trim();
        } else if( "".equals( line.trim() ) ) {
          headerFinished = true;
        }
      }
      content = buffer.toString();
    }
    
  }

  private static final class EnronLabelProvider extends CellLabelProvider {

    private static final String ICON_FILE = "/envelope.png";
    private static final String ICON_FOLDER = "/inbox.png";

    private static final int COLUMN_TITLE = 0;
    private static final int COLUMN_SUB_TITLE = 1;

    private final Image fileImage;
    private final Image folderImage;

    EnronLabelProvider( final Device device ) {
      fileImage = createImage( device, ICON_FILE );
      folderImage = createImage( device, ICON_FOLDER );
    }

    @Override
    public void update( final ViewerCell cell ) {
      Object element = cell.getElement();
      if( element instanceof Node ) {
        Node node = ( Node )element;
        int columnIndex = cell.getColumnIndex();
        switch( columnIndex ) {
          case COLUMN_TITLE:
            updateTitle( cell, node );
          break;
          case COLUMN_SUB_TITLE:
            updateSubTitle( cell, node );
          break;
        }
      }
    }

    @Override
    public String getToolTipText( final Object element ) {
      String result = "";
      if( element instanceof File ) {
        File file = ( File )element;
        result = file.getName();
      }
      return result;
    }

    private void updateTitle( ViewerCell cell, Node node ) {
      if( node instanceof Folder ) {
        cell.setText( node.getTitle() );
        cell.setImage( folderImage );
      } else {
        cell.setText( node.getFrom() );
        cell.setImage( fileImage );
      }
    }
    
    private void updateSubTitle( ViewerCell cell, Node node ) {
      if( !( node instanceof Folder ) ) {
        cell.setText( node.getTitle() );
      }
    }

    private static Image createImage( Device device, String name ) {
      ClassLoader classLoader = EnronLabelProvider.class.getClassLoader();
      InputStream inputStream = classLoader.getResourceAsStream( name );
      Image result = null;
      if( inputStream != null ) {
        result = new Image( device, inputStream );
      }
      return result;
    }
  }
  private static class EnronLazyContentProvider implements ILazyTreeContentProvider {

    private final TreeViewer viewer;

    public EnronLazyContentProvider( TreeViewer viewer ) {
      this.viewer = viewer;
    }

    public Object getParent( Object element ) {
      Object result = null;
      if( element instanceof Node ) {
        result = ( ( Node )element ).getParent();
      }
      return result;
    }

    public void updateElement( Object parent, int index ) {
      if( parent instanceof Folder ) {
        Folder folder = ( Folder )parent;
        Node node = folder.getChild( index );
        if( node != null ) {
          viewer.replace( parent, index, node );
          viewer.setChildCount( node, node.getChildCount() );
        }
      }
    }

    public void updateChildCount( Object element, int currentChildCount ) {
      if( element instanceof Node ) {
        Node node = ( Node )element;
        int childCount = node.getChildCount();
        if( childCount != currentChildCount ) {
          viewer.setChildCount( element, childCount );
        }
      }
    }

    public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {
      // nothing
    }

    public void dispose() {
      // nothing
    }
  }
}
