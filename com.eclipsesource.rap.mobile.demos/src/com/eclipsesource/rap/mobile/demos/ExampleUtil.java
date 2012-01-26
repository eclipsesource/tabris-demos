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
package com.eclipsesource.rap.mobile.demos;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;


public final class ExampleUtil {

  public static GridLayout createMainLayout( int numColumns ) {
    GridLayout result = createGridLayout();
    result.makeColumnsEqualWidth = true;
    result.numColumns = numColumns;
    result.marginTop = 10;
    result.marginBottom = 10;
    result.verticalSpacing = 20;
    result.horizontalSpacing = 20;
    return result;
  }

  public static GridLayout createColumnLayout() {
    GridLayout result = createGridLayout();
    result.verticalSpacing = 20;
    return result;
  }

  public static GridLayout createGridLayout( int numColumns, boolean makeColumnsEqual ) {
    return createGridLayout( numColumns, makeColumnsEqual, 0, 0 );
  }

  public static GridLayout createGridLayout( int numColumns,
                                             boolean makeColumnsEqual,
                                             int margin,
                                             int spacing )
  {
    GridLayout result = new GridLayout( numColumns, makeColumnsEqual );
    result.horizontalSpacing = spacing;
    result.verticalSpacing = spacing;
    result.marginWidth = margin;
    result.marginHeight = margin;
    return result;
  }

  public static GridLayout createGridLayout() {
    GridLayout result = new GridLayout();
    result.horizontalSpacing = 0;
    result.verticalSpacing = 0;
    result.marginWidth = 0;
    result.marginHeight = 0;
    result.marginHeight = 0;
    return result;
  }

  public static GridData createHorzFillData() {
    return new GridData( SWT.FILL, SWT.TOP, true, false );
  }

  public static GridData createFillData() {
    return new GridData( SWT.FILL, SWT.FILL, true, true );
  }

  private ExampleUtil() {
    // prevent instantiation
  }
}
