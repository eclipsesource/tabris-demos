/*******************************************************************************
 * Copyright (c) 2012 EclipseSource and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.entrypoints;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.eclipsesource.tabris.widgets.ClientCanvas;
import com.eclipsesource.tabris.widgets.ClientDrawListener;

public class DrawDemo implements EntryPoint {

  private static final List<int[]> ERNIE = new ArrayList<int[]>();
  /**
   * includes labels for already connected dots
   */
  private static final boolean LABEL_ALL_DOTS = false;
  /**
   * ignore labels in the dot info
   */
  private static final boolean RENUMBER_LABELS = true;
  /**
   * original Ernie!
   */
  private static final boolean SHOW_BLUE_PRINT = false;
  private static final int[] HAIR = new int[]{
    22, 93
  };
  private static final int[] LEFT_EYE = new int[]{
    163, 168
  };
  private static final int[] RIGHT_EYE = new int[]{
    158, 162
  };
  private static final int[] NECK = new int[]{
    169, 178
  };
  private static final int[] MOUTH = new int[]{
    108, 118
  };
  private static final List<int[]> ALLREADY_DRAWN_SUBSETS = new ArrayList<int[]>();
  static {
    ALLREADY_DRAWN_SUBSETS.add( HAIR );
    ALLREADY_DRAWN_SUBSETS.add( LEFT_EYE );
    ALLREADY_DRAWN_SUBSETS.add( RIGHT_EYE );
    ALLREADY_DRAWN_SUBSETS.add( NECK );
    ALLREADY_DRAWN_SUBSETS.add( MOUTH );
    addToErnie( 0, 95, 125 );
    addToErnie( 1, 81, 166 );
    addToErnie( 2, 61, 194 );
    addToErnie( 3, 55, 233 );
    addToErnie( 4, 82, 286 );
    addToErnie( 5, 108, 315 );
    addToErnie( 6, 123, 332 );
    addToErnie( 7, 139, 342 );
    addToErnie( 8, 158, 354 );
    addToErnie( 9, 184, 363 );
    addToErnie( 10, 218, 372 );
    addToErnie( 11, 249, 369 );
    addToErnie( 12, 280, 361 );
    addToErnie( 13, 310, 350 );
    addToErnie( 14, 326, 333 );
    addToErnie( 15, 342, 324 );
    addToErnie( 16, 368, 298 );
    addToErnie( 17, 381, 282 );
    addToErnie( 18, 391, 267 );
    addToErnie( 19, 401, 242 );
    addToErnie( 20, 399, 221 );
    addToErnie( 21, 387, 198 );
    addToErnie( 22, 362, 150 );
    addToErnie( 179, 362, 150 );
    addToErnie( 23, 332, 135 );
    addToErnie( 24, 305, 121 );
    addToErnie( 25, 281, 116 );
    addToErnie( 26, 258, 115 );
    addToErnie( 27, 244, 121 );
    addToErnie( 28, 221, 114 );
    addToErnie( 29, 191, 113 );
    addToErnie( 30, 163, 121 );
    addToErnie( 31, 131, 127 );
    addToErnie( 32, 95, 125 );
    addToErnie( 33, 76, 109 );
    addToErnie( 34, 96, 108 );
    addToErnie( 35, 82, 94 );
    addToErnie( 36, 79, 73 );
    addToErnie( 37, 106, 85 );
    addToErnie( 38, 99, 74 );
    addToErnie( 39, 98, 66 );
    addToErnie( 40, 110, 71 );
    addToErnie( 41, 129, 71 );
    addToErnie( 42, 123, 61 );
    addToErnie( 43, 115, 56 );
    addToErnie( 44, 132, 51 );
    addToErnie( 45, 151, 50 );
    addToErnie( 46, 141, 38 );
    addToErnie( 47, 156, 38 );
    addToErnie( 48, 174, 43 );
    addToErnie( 49, 162, 30 );
    addToErnie( 50, 149, 29 );
    addToErnie( 51, 165, 22 );
    addToErnie( 52, 185, 27 );
    addToErnie( 53, 183, 14 );
    addToErnie( 54, 186, 2 );
    addToErnie( 55, 197, 13 );
    addToErnie( 56, 211, 24 );
    addToErnie( 57, 215, 15 );
    addToErnie( 58, 209, 8 );
    addToErnie( 59, 224, 11 );
    addToErnie( 60, 233, 18 );
    addToErnie( 61, 248, 16 );
    addToErnie( 62, 266, 6 );
    addToErnie( 63, 276, 1 );
    addToErnie( 64, 275, 9 );
    addToErnie( 65, 272, 20 );
    addToErnie( 66, 294, 12 );
    addToErnie( 67, 322, 8 );
    addToErnie( 68, 307, 15 );
    addToErnie( 69, 299, 26 );
    addToErnie( 70, 315, 26 );
    addToErnie( 71, 326, 26 );
    addToErnie( 72, 311, 35 );
    addToErnie( 73, 340, 35 );
    addToErnie( 74, 331, 50 );
    addToErnie( 75, 353, 50 );
    addToErnie( 76, 368, 54 );
    addToErnie( 77, 350, 58 );
    addToErnie( 78, 379, 62 );
    addToErnie( 79, 396, 70 );
    addToErnie( 80, 378, 72 );
    addToErnie( 81, 368, 81 );
    addToErnie( 82, 388, 84 );
    addToErnie( 83, 369, 93 );
    addToErnie( 84, 383, 102 );
    addToErnie( 85, 410, 101 );
    addToErnie( 86, 394, 109 );
    addToErnie( 87, 379, 113 );
    addToErnie( 88, 394, 125 );
    addToErnie( 89, 410, 122 );
    addToErnie( 90, 405, 133 );
    addToErnie( 91, 386, 133 );
    addToErnie( 92, 395, 143 );
    addToErnie( 93, 365, 155 );
    addToErnie( 100, 58, 157 );
    addToErnie( 101, 31, 170 );
    addToErnie( 102, 22, 207 );
    addToErnie( 103, 35, 227 );
    addToErnie( 104, 407, 194 );
    addToErnie( 105, 433, 211 );
    addToErnie( 106, 436, 241 );
    addToErnie( 107, 417, 267 );
    addToErnie( 108, 74, 217 );
    addToErnie( 109, 85, 229 );
    addToErnie( 110, 102, 239 );
    addToErnie( 111, 152, 255 );
    addToErnie( 112, 178, 261 );
    addToErnie( 113, 226, 269 );
    addToErnie( 114, 276, 271 );
    addToErnie( 115, 315, 268 );
    addToErnie( 116, 352, 257 );
    addToErnie( 117, 375, 250 );
    addToErnie( 118, 387, 240 );
    addToErnie( 119, 340, 278 );
    addToErnie( 120, 317, 297 );
    addToErnie( 121, 276, 322 );
    addToErnie( 122, 230, 332 );
    addToErnie( 123, 182, 323 );
    addToErnie( 124, 154, 306 );
    addToErnie( 125, 132, 285 );
    addToErnie( 126, 112, 260 );
    addToErnie( 127, 103, 244 );
    addToErnie( 128, 178, 265 );
    addToErnie( 129, 195, 287 );
    addToErnie( 130, 228, 301 );
    addToErnie( 131, 253, 295 );
    addToErnie( 132, 270, 282 );
    addToErnie( 133, 226, 281 );
    addToErnie( 134, 188, 228 );
    addToErnie( 135, 191, 201 );
    addToErnie( 136, 209, 184 );
    addToErnie( 137, 234, 180 );
    addToErnie( 138, 257, 189 );
    addToErnie( 139, 269, 217 );
    addToErnie( 140, 258, 248 );
    addToErnie( 141, 242, 259 );
    addToErnie( 142, 170, 198 );
    addToErnie( 143, 152, 186 );
    addToErnie( 144, 152, 156 );
    addToErnie( 145, 167, 144 );
    addToErnie( 146, 191, 131 );
    addToErnie( 147, 215, 136 );
    addToErnie( 148, 225, 154 );
    addToErnie( 149, 221, 176 );
    addToErnie( 150, 243, 166 );
    addToErnie( 151, 254, 148 );
    addToErnie( 152, 271, 135 );
    addToErnie( 153, 294, 138 );
    addToErnie( 154, 317, 165 );
    addToErnie( 155, 316, 191 );
    addToErnie( 156, 300, 204 );
    addToErnie( 157, 282, 207 );
    addToErnie( 158, 273, 184 );
    addToErnie( 159, 289, 181 );
    addToErnie( 160, 295, 169 );
    addToErnie( 161, 281, 158 );
    addToErnie( 162, 265, 168 );
    addToErnie( 163, 189, 181 );
    addToErnie( 164, 176, 172 );
    addToErnie( 165, 180, 155 );
    addToErnie( 166, 195, 153 );
    addToErnie( 167, 203, 167 );
    addToErnie( 168, 202, 178 );
    addToErnie( 169, 120, 346 );
    addToErnie( 170, 131, 366 );
    addToErnie( 171, 150, 380 );
    addToErnie( 172, 182, 397 );
    addToErnie( 173, 217, 404 );
    addToErnie( 174, 249, 405 );
    addToErnie( 175, 285, 396 );
    addToErnie( 176, 309, 382 );
    addToErnie( 177, 332, 363 );
    addToErnie( 178, 344, 343 );
    determineErnieDimensions();
  }
  private static float scaleFactor = 1f;
  private static Point ernieDimensions;
  private static Point ernieMinima;
  private Image bgPatternImage;
  protected int currentColor = SWT.COLOR_DARK_RED;
  protected int currentLineWidth = 8;
  protected int alpha = 255;
  private ClientCanvas canvas;
  private ToolItem redoToolItem;
  private ToolItem undoToolItem;
  private ToolItem clearToolItem;

  public int createUI() {
    final Display display = new Display();
    if( SHOW_BLUE_PRINT ) {
      bgPatternImage = new Image( display, DrawDemo.class.getResourceAsStream( "/ernie.png" ) );
    }
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    shell.setLayout( new FillLayout() );
    Composite mainComp = new Composite( shell, SWT.NONE );
    mainComp.setLayout( createGridLayout( 1, false ) );
    createToolbar( mainComp );
    createClientCanvas( display, mainComp );
    shell.open();
    fitErnieToCanvas();
    return 0;
  }

  private static void determineErnieDimensions() {
    int minX = -1;
    int minY = -1;
    int maxX = 0;
    int maxY = 0;
    for( int[] dotInfo : ERNIE ) {
      int x = dotInfo[ 1 ];
      int y = dotInfo[ 2 ];
      if( x > maxX ) {
        maxX = x;
      }
      if( y > maxY ) {
        maxY = y;
      }
      if( minX == -1 || x < minX ) {
        minX = x;
      }
      if( minY == -1 || y < minY ) {
        minY = y;
      }
    }
    ernieDimensions = new Point( maxX - minX, maxY - minY );
    ernieMinima = new Point( minX, minY );
  }

  private static void addToErnie( int index, int x, int y ) {
    ERNIE.add( new int[]{
      index, x, y
    } );
  }

  private void createClientCanvas( final Display display, Composite mainComp ) {
    canvas = new ClientCanvas( mainComp, SWT.NONE );
    canvas.setLayoutData( createFill() );
    if( bgPatternImage != null ) {
      canvas.setBackgroundImage( bgPatternImage );
    } else {
      canvas.setBackground( display.getSystemColor( SWT.COLOR_WHITE ) );
    }
    canvas.addPaintListener( new PaintListener() {

      public void paintControl( PaintEvent e ) {
        e.gc.setLineWidth( currentLineWidth );
        e.gc.setLineCap( SWT.CAP_ROUND );
        e.gc.setLineJoin( SWT.JOIN_ROUND );
        e.gc.setForeground( display.getSystemColor( currentColor ) );
        e.gc.setAlpha( alpha );
      }
    } );
    canvas.addClientDrawListener( new ClientDrawListener() {

      public void receivedDrawing() {
        redoToolItem.setEnabled( canvas.hasRedo() );
        undoToolItem.setEnabled( canvas.hasUndo() );
        clearToolItem.setEnabled( canvas.hasUndo() );
      }
    } );
    canvas.redraw();
    canvas.addPaintListener( new PaintListener() {

      public void paintControl( PaintEvent event ) {
        GC gc = event.gc;
        Color oldForeground = gc.getForeground();
        Color oldBackground = gc.getBackground();
        gc.setForeground( event.display.getSystemColor( SWT.COLOR_BLACK ) );
        gc.setBackground( event.display.getSystemColor( SWT.COLOR_BLACK ) );
        drawErnie( gc );
        gc.setForeground( oldForeground );
        gc.setBackground( oldBackground );
      }

      private void drawErnie( GC gc ) {
        int oldLineWidth = currentLineWidth;
        gc.setLineWidth( 8 );
        gc.fillPolygon( transform( getHair( ERNIE ) ) );
        gc.drawPolyline( transform( getNeck( ERNIE ) ) );
        gc.fillPolygon( transform( getRightEye( ERNIE ) ) );
        gc.fillPolygon( transform( getLeftEye( ERNIE ) ) );
        gc.drawPolyline( transform( getMouth( ERNIE ) ) );
        drawDots( gc, ERNIE );
        gc.setLineWidth( oldLineWidth );
      }

      private int[] getLeftEye( List<int[]> dotInfos ) {
        return getDotsAsPoly( dotInfos, LEFT_EYE[ 0 ], LEFT_EYE[ 1 ] );
      }

      private int[] getRightEye( List<int[]> dotInfos ) {
        return getDotsAsPoly( dotInfos, RIGHT_EYE[ 0 ], RIGHT_EYE[ 1 ] );
      }

      private int[] getNeck( List<int[]> dotInfos ) {
        return getDotsAsPoly( dotInfos, NECK[ 0 ], NECK[ 1 ] );
      }

      private int[] getHair( List<int[]> dotInfos ) {
        return getDotsAsPoly( dotInfos, HAIR[ 0 ], HAIR[ 1 ] );
      }

      private int[] getMouth( List<int[]> dotInfos ) {
        return getDotsAsPoly( dotInfos, MOUTH[ 0 ], MOUTH[ 1 ] );
      }

      private boolean isAlreadyDrawn( int dotLable ) {
        for( int[] interval : ALLREADY_DRAWN_SUBSETS ) {
          if( dotLable >= interval[ 0 ] && dotLable <= interval[ 1 ] ) {
            return true;
          }
        }
        return false;
      }

      private int[] getDotsAsPoly( List<int[]> dotInfos, int startLable, int endLable ) {
        List<int[]> dotInfoSubset = getDots( dotInfos, startLable, endLable );
        int[] coordSubset = new int[ dotInfoSubset.size() * 2 ];
        int coordIndex = 0;
        for( int[] dotInfo : dotInfoSubset ) {
          coordSubset[ coordIndex ] = dotInfo[ 1 ];
          coordSubset[ coordIndex + 1 ] = dotInfo[ 2 ];
          coordIndex += 2;
        }
        return coordSubset;
      }

      private List<int[]> getDots( List<int[]> dotInfos, int startLable, int endLable ) {
        List<int[]> result = new ArrayList<int[]>();
        int currentLable = startLable;
        for( int[] dotInfo : dotInfos ) {
          int dotLable = dotInfo[ 0 ];
          if( dotLable == currentLable ) {
            result.add( dotInfo );
            currentLable++;
          }
          if( currentLable > endLable ) {
            break;
          }
        }
        return result;
      }

      private void drawDots( GC gc, List<int[]> dotInfos ) {
        int dotLable = 0;
        for( int[] dotInfo : dotInfos ) {
          if( LABEL_ALL_DOTS || !isAlreadyDrawn( dotInfo[ 0 ] ) ) {
            if( RENUMBER_LABELS ) {
              dotLable++;
            } else {
              dotLable = dotInfo[ 0 ];
            }
            drawDot( gc, dotLable, dotInfo[ 1 ], dotInfo[ 2 ] );
          }
        }
      }

      private void drawDot( GC gc, int dotLabel, int x, int y ) {
        Point dot = transform( new Point( x, y ) );
        int lableInterval = 1;
        if( dotLabel % lableInterval == 0 ) {
          Color oldBackground = gc.getBackground();
          gc.setBackground( gc.getDevice().getSystemColor( SWT.COLOR_WHITE ) );
          gc.drawString( Integer.toString( dotLabel ), dot.x + 5, dot.y + 5 );
          gc.setBackground( oldBackground );
        }
        gc.fillOval( dot.x, dot.y, 5, 5 );
      }

      Point transform( Point coord ) {
        Point centerDelta = calculateCenterDelta();
        Point inTopLeft = translate( coord, -ernieMinima.x, -ernieMinima.y );
        Point scaledInTopLeft = scale( inTopLeft, scaleFactor );
        translate( scaledInTopLeft, centerDelta.x, centerDelta.y );
        return scaledInTopLeft;
      }

      Point scale( Point coord, float factor ) {
        coord.x = ( int )( coord.x * factor );
        coord.y = ( int )( coord.y * factor );
        return coord;
      }

      Point translate( Point coord, int deltaX, int deltaY ) {
        coord.x = coord.x + deltaX;
        coord.y = coord.y + deltaY;
        return coord;
      }

      int[] transform( int[] coords ) {
        Point centerDelta = calculateCenterDelta();
        int[] inTopLeft = translate( coords, -ernieMinima.x, -ernieMinima.y );
        int[] scaledInTopLeft = scale( inTopLeft, scaleFactor );
        translate( scaledInTopLeft, centerDelta.x, centerDelta.y );
        return scaledInTopLeft;
      }

      Point calculateCenterDelta() {
        int canvasCenterX = canvas.getBounds().width / 2;
        int canvasCenterY = canvas.getBounds().height / 2;
        int ernieCenterX = ( ( int )( ( ernieDimensions.x / 2 ) * scaleFactor ) );
        int ernieCenterY = ( ( int )( ( ernieDimensions.y / 2 ) * scaleFactor ) );
        Point result = new Point( canvasCenterX - ernieCenterX, canvasCenterY - ernieCenterY );
        return result;
      }

      int[] scale( int[] coords, float factor ) {
        for( int i = 0; i < coords.length; i++ ) {
          coords[ i ] = ( int )( coords[ i ] * factor );
        }
        return coords;
      }

      int[] translate( int[] coords, int deltaX, int deltaY ) {
        for( int i = 0; i < coords.length; i += 2 ) {
          coords[ i ] += deltaX;
          coords[ i + 1 ] += deltaY;
        }
        return coords;
      }
    } );
  }

  private void fitErnieToCanvas() {
    Rectangle bounds = canvas.getBounds();
    if( bounds.width >= bounds.height ) {
      scaleFactor = ( float )bounds.height / ernieDimensions.y;
    } else {
      scaleFactor = ( float )bounds.width / ernieDimensions.x;
    }
    canvas.redraw();
  }

  private GridData createFill() {
    return new GridData( SWT.FILL, SWT.FILL, true, true );
  }

  private GridData createFillHori() {
    return new GridData( SWT.FILL, SWT.TOP, true, false );
  }

  private GridLayout createGridLayout( int cols, boolean equalWidth ) {
    GridLayout layout = new GridLayout( cols, equalWidth );
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    layout.verticalSpacing = 0;
    layout.horizontalSpacing = 0;
    return layout;
  }

  private void createToolbar( Composite parent ) {
    final ToolBar toolBar = new ToolBar( parent, SWT.HORIZONTAL );
    toolBar.setLayoutData( createFillHori() );
    toolBar.setBackground( toolBar.getDisplay().getSystemColor( currentColor ) );
    ToolItem widthThinToolItem = new ToolItem( toolBar, SWT.PUSH );
    widthThinToolItem.setImage( new Image( parent.getDisplay(),
                                           DrawDemo.class.getResourceAsStream( "/line-width-thin.png" ) ) );
    widthThinToolItem.addListener( SWT.Selection, new Listener() {

      public void handleEvent( Event event ) {
        currentLineWidth = 1;
        canvas.redraw();
      }
    } );
    ToolItem widthMediumToolItem = new ToolItem( toolBar, SWT.PUSH );
    widthMediumToolItem.setImage( new Image( parent.getDisplay(),
                                             DrawDemo.class.getResourceAsStream( "/line-width-medium.png" ) ) );
    widthMediumToolItem.addListener( SWT.Selection, new Listener() {

      public void handleEvent( Event event ) {
        currentLineWidth = 8;
        canvas.redraw();
      }
    } );
    ToolItem widthThickToolItem = new ToolItem( toolBar, SWT.PUSH );
    widthThickToolItem.setImage( new Image( parent.getDisplay(),
                                            DrawDemo.class.getResourceAsStream( "/line-width-thick.png" ) ) );
    widthThickToolItem.addListener( SWT.Selection, new Listener() {

      public void handleEvent( Event event ) {
        currentLineWidth = 16;
        canvas.redraw();
      }
    } );
    new ToolItem( toolBar, SWT.SEPARATOR );
    final ToolItem opacityToolItem = new ToolItem( toolBar, SWT.PUSH );
    opacityToolItem.setText( "Opaque" );
    opacityToolItem.addListener( SWT.Selection, new Listener() {

      public void handleEvent( Event event ) {
        if( alpha == 255 ) {
          alpha = 128;
          opacityToolItem.setText( "Transparent" );
        } else {
          alpha = 255;
          opacityToolItem.setText( "Opaque" );
        }
        canvas.redraw();
      }
    } );
    new ToolItem( toolBar, SWT.SEPARATOR );
    ToolItem colorRedToolItem = new ToolItem( toolBar, SWT.PUSH );
    colorRedToolItem.setText( "Red" );
    colorRedToolItem.addListener( SWT.Selection, new Listener() {

      public void handleEvent( Event event ) {
        currentColor = SWT.COLOR_DARK_RED;
        toolBar.setBackground( toolBar.getDisplay().getSystemColor( currentColor ) );
        canvas.redraw();
      }
    } );
    ToolItem colorGreenToolItem = new ToolItem( toolBar, SWT.PUSH );
    colorGreenToolItem.setText( "Green" );
    colorGreenToolItem.addListener( SWT.Selection, new Listener() {

      public void handleEvent( Event event ) {
        currentColor = SWT.COLOR_DARK_GREEN;
        toolBar.setBackground( toolBar.getDisplay().getSystemColor( currentColor ) );
        canvas.redraw();
      }
    } );
    ToolItem colorBlueToolItem = new ToolItem( toolBar, SWT.PUSH );
    colorBlueToolItem.setText( "Blue" );
    colorBlueToolItem.addListener( SWT.Selection, new Listener() {

      public void handleEvent( Event event ) {
        currentColor = SWT.COLOR_DARK_BLUE;
        toolBar.setBackground( toolBar.getDisplay().getSystemColor( currentColor ) );
        canvas.redraw();
      }
    } );
    new ToolItem( toolBar, SWT.SEPARATOR );
    undoToolItem = new ToolItem( toolBar, SWT.PUSH );
    undoToolItem.setImage( new Image( parent.getDisplay(),
                                      DrawDemo.class.getResourceAsStream( "/undo.png" ) ) );
    undoToolItem.setEnabled( false );
    undoToolItem.addListener( SWT.Selection, new Listener() {

      public void handleEvent( Event event ) {
        canvas.undo();
        undoToolItem.setEnabled( canvas.hasUndo() );
        clearToolItem.setEnabled( canvas.hasUndo() );
      }
    } );
    redoToolItem = new ToolItem( toolBar, SWT.PUSH );
    redoToolItem.setImage( new Image( parent.getDisplay(),
                                      DrawDemo.class.getResourceAsStream( "/redo.png" ) ) );
    redoToolItem.setEnabled( false );
    redoToolItem.addListener( SWT.Selection, new Listener() {

      public void handleEvent( Event event ) {
        canvas.redo();
        redoToolItem.setEnabled( canvas.hasRedo() );
      }
    } );
    new ToolItem( toolBar, SWT.SEPARATOR );
    clearToolItem = new ToolItem( toolBar, SWT.PUSH );
    clearToolItem.setText( "Clear" );
    clearToolItem.addListener( SWT.Selection, new Listener() {

      public void handleEvent( Event event ) {
        canvas.clear();
      }
    } );
  }
}
