/*******************************************************************************
 * Copyright (c) 2013 EclipseSource and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.ui;

import org.eclipse.swt.graphics.Image;

import com.eclipsesource.tabris.ui.UI;
import com.eclipsesource.tabris.ui.UIConfiguration;
import com.eclipsesource.tabris.ui.UIContext;

public class DemoUIConfiguration implements UIConfiguration {

  public void configure( UI ui, UIContext context ) {
    ui.addPage( "top1", new DemoPage( "Top 1" ), "Top 1", true );
    ui.addAction( "topAction", "TopAction", createImage( context ), new DemoAction() );
    ui.addPage( "top2", new DemoPage( "Top 2" ), "Top 2", true );
    ui.addPage( "sub1", new DemoPage( "Sub 1" ), "Sub 1", false );
    ui.addPage( "sub2", new DemoPage( "Sub 2" ), "Sub 2", false );
    ui.addPage( "sub3", new DemoPage( "Sub 3" ), "Sub 3", false )
      .addAction( "subAction", "SubAction", createImage( context ), new DemoAction() );
  }

  private Image createImage( UIContext context ) {
    return new Image( context.getDisplay(),
                      DemoUIConfiguration.class.getResourceAsStream( "/images/phone.png" ) );
  }
}
