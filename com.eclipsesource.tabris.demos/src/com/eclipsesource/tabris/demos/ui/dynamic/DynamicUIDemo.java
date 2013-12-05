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

import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.rap.rwt.application.EntryPointFactory;

import com.eclipsesource.tabris.ui.PageConfiguration;
import com.eclipsesource.tabris.ui.TabrisUIEntryPoint;
import com.eclipsesource.tabris.ui.UIConfiguration;


public class DynamicUIDemo implements EntryPointFactory {

  @Override
  public EntryPoint create() {
    return new TabrisUIEntryPoint( createConfig() );
  }

  private UIConfiguration createConfig() {
    UIConfiguration configuration = new UIConfiguration();
    configuration.addPageConfiguration( new PageConfiguration( "root", DynamicPage.class )
                                          .setTopLevel( true )
                                          .setTitle( "Dynamic UI Start" ) );
    return configuration;
  }
}
