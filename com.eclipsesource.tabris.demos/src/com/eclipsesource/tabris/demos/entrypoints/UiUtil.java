/*
 * Copyright(c) 2012 EclipseSource. All Rights Reserved.
 */

package com.eclipsesource.tabris.demos.entrypoints;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

public class UiUtil {

  public static GridData createFill() {
    return new GridData( SWT.FILL, SWT.FILL, true, true );
  }

  public static GridData createFillHori() {
    return new GridData( SWT.FILL, SWT.TOP, true, false );
  }

  public static GridData createCenteredGrab() {
    return new GridData( SWT.CENTER, SWT.CENTER, true, true );
  }

  public static GridLayout createGridLayout( int cols, boolean equalWidth ) {
    GridLayout layout = new GridLayout( cols, equalWidth );
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    layout.verticalSpacing = 0;
    layout.horizontalSpacing = 0;
    return layout;
  }

}
