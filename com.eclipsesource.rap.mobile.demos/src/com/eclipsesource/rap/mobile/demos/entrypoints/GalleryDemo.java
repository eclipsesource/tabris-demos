/*******************************************************************************
 * Copyright (c) 2012 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.rap.mobile.demos.entrypoints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.rwt.lifecycle.IEntryPoint;
import org.eclipse.rwt.lifecycle.WidgetUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;


public class GalleryDemo implements IEntryPoint {

  private ScrolledComposite scrolledComposite;
  private Label zoomImageLabel;
  private Composite thumbnailsComposite;
  private Shell thumbnailsArea;
  private boolean thumbnailsVisible;
  private ToolItem overviewItem;

  public int createUI() {
    Display display = new Display();
    Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    shell.setLayout( new FillLayout() );
    shell.setBackground( display.getSystemColor( SWT.COLOR_BLACK ) );
    createContent( shell );
    shell.open();
    thumbnailsArea.open();
    showThumbnails();
    shell.addControlListener( new ControlAdapter() {
      @Override
      public void controlResized( ControlEvent e ) {
        if( thumbnailsVisible ) {
          showThumbnails();
        } else {
          hideThumbnails();
        }
      }
    } );
    return 0;
  }

  private void createContent(Shell shell ) {
    Composite parent = new Composite( shell, SWT.NONE) ;
    GridLayoutFactory.fillDefaults().spacing( 0, 0 ).applyTo( parent );
    createToolbar( parent );
    createZoomImageArea( parent );
    createThumbnailsArea( shell );  
  }

  private void createToolbar(final Composite parent) {
    final ToolBar toolBar = new ToolBar( parent, SWT.NONE );
    GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(toolBar);

    overviewItem = new ToolItem(toolBar, SWT.PUSH);
    overviewItem.setText("Fullscreen");
    overviewItem.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        toggleThubnails();
      }
    } );
    
    new ToolItem(toolBar, SWT.SEPARATOR);
    
    ToolItem titleItem = new ToolItem(toolBar, SWT.NONE);
    titleItem.setData(WidgetUtil.CUSTOM_VARIANT, "TITLE");
    titleItem.setText("The Big Bang Theory");
    
    new ToolItem(toolBar, SWT.SEPARATOR);
  }
  
  protected void toggleThubnails() {
    if( thumbnailsVisible ) {
      hideThumbnails();
    } else {
      showThumbnails();
    }
  }

  private void hideThumbnails() {
    int displayHeight = thumbnailsArea.getDisplay().getBounds().height;
    int y =  displayHeight;
    int x = 0;
    int width = thumbnailsArea.getDisplay().getBounds().width;
    int height = 164;
    thumbnailsArea.setBounds( x, y, width, height );
    thumbnailsVisible = false;
    overviewItem.setText( "Thumbnails" );
  }

  private void showThumbnails() {
    int displayHeight = thumbnailsArea.getDisplay().getBounds().height;
    int shellHeight = thumbnailsArea.getBounds().height;
    int y = displayHeight - shellHeight;
    int x = 0;
    int width = thumbnailsArea.getDisplay().getBounds().width;
    int height = 164;
    thumbnailsArea.setBounds( x, y, width, height );
    thumbnailsVisible = true;
    overviewItem.setText( "Fullscreen" );
  }

  private void createThumbnailsArea( Shell parentShell ) {
    thumbnailsArea = new Shell(parentShell, SWT.NO_TRIM);
    thumbnailsArea.setData(WidgetUtil.CUSTOM_VARIANT, "ANIMATED");
    thumbnailsArea.setBounds( 0, thumbnailsArea.getDisplay().getBounds().height, thumbnailsArea.getDisplay().getBounds().width, 164 );
    FillLayout thumbnailsAreaLayout = new FillLayout();
    thumbnailsAreaLayout.marginHeight = 7;
    thumbnailsAreaLayout.marginWidth = 7;

    Color bgColor = new Color( thumbnailsArea.getDisplay(), new RGB(32,32,32));
    thumbnailsArea.setLayout(thumbnailsAreaLayout);
    thumbnailsArea.setBackground(bgColor );
  
    scrolledComposite = new ScrolledComposite(thumbnailsArea, SWT.H_SCROLL);
    scrolledComposite.setData(WidgetUtil.CUSTOM_VARIANT, "PAGINGENABLED");
  
    thumbnailsComposite = new Composite(scrolledComposite, SWT.NONE);
    GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER).grab(true, false).applyTo(thumbnailsComposite);
    GridLayoutFactory.fillDefaults().spacing(5, 0).numColumns(19).applyTo(thumbnailsComposite);
    thumbnailsComposite.setBackground(bgColor );
  
    scrolledComposite.setExpandHorizontal(true);
    scrolledComposite.setExpandVertical(true);
    scrolledComposite.addControlListener(new ControlAdapter() {
      private static final long serialVersionUID = 3338040035822519746L;
      @Override
      public void controlResized(final ControlEvent e) {
        computeScrollAreaSize(scrolledComposite);
      }
    });
    scrolledComposite.setContent( thumbnailsComposite );
    populateWithImages();
  }

  private void createZoomImageArea( Composite parent ) {
    Composite imageArea = new Composite(parent, SWT.NONE);
    GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo( imageArea );
    imageArea.setBackground(parent.getDisplay().getSystemColor( SWT.COLOR_BLACK ) );
    imageArea.setLayout( new FillLayout() );
  
    zoomImageLabel = new Label( imageArea, SWT.NONE );
    zoomImageLabel.setData( WidgetUtil.CUSTOM_VARIANT, "ZOOM" );
    zoomImageLabel.addMouseListener( new MouseAdapter() {
      @Override
      public void mouseDown( MouseEvent e ) {
        hideThumbnails();
      }
    } );
  }
  
  private void populateWithImages() {
    List<String> images = new ArrayList<String>();
    images.add("catseye");
    images.add("heic0305a");
    images.add("heic0401a");
    images.add("heic0405a");
    images.add("heic0407a");
    images.add("heic0407b");
    images.add("heic0409a");
    images.add("heic0414a");
    images.add("heic0502a");
    images.add("heic0514a");
    images.add("heic0515a");
    images.add("heic0604a");
    images.add("heic0910e");
    images.add("IRS46_nasa");
    images.add("ngc4414");
    images.add("opo0110a");
    images.add("opo0505a");
    images.add("opo9901a");
    images.add("orion-nebula");
    final Map<String, Image> imageCache = new HashMap<String, Image>();
    for (final String imageName : images) {
      Image thumbImage = new Image(thumbnailsComposite.getDisplay(),
          GalleryDemo.class.getResourceAsStream("/gallery/"
              + imageName + "_thumb.jpg"));
      final Label thumbLabel = new Label(thumbnailsComposite, SWT.NONE);
      thumbLabel.setImage(thumbImage);
      thumbLabel.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseUp(MouseEvent e) {
//          hideThumbnails();
          Image fullImage = imageCache.get(imageName);
          if (fullImage == null) {
            fullImage = new Image(thumbLabel.getDisplay(),
                                  GalleryDemo.class
                    .getResourceAsStream("/gallery/"
                        + imageName + ".jpg"));
            imageCache.put(imageName, fullImage);
          }
          scrolledComposite.showControl(thumbLabel);
          Point size = zoomImageLabel.getSize();
          zoomImageLabel.setImage(fullImage);
          zoomImageLabel.setSize(size);
        }
      });
    }
  }

  private void computeScrollAreaSize(final ScrolledComposite scrolledComp) {
    Rectangle clientAreaSize = scrolledComp.getClientArea();
    Point clientSize = scrolledComp.getContent().computeSize(SWT.DEFAULT,
        clientAreaSize.height);
    scrolledComp.setMinSize(clientSize.x, clientAreaSize.height);
  }
}
