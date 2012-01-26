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
package com.eclipsesource.rap.mobile.demos;

import org.eclipse.rwt.branding.AbstractBranding;


public class Branding extends AbstractBranding {
  
  private final String id;

  public Branding( String id ) {
    this.id = id;
  }
  
  @Override
  public String getDefaultEntryPoint() {
    return id;
  }
  
  @Override
  public String getThemeId() {
    return id;
  }
  
  @Override
  public String getServletName() {
    return id;
  }
}
