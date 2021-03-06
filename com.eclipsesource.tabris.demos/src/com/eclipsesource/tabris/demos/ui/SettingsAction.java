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
package com.eclipsesource.tabris.demos.ui;

import com.eclipsesource.tabris.ui.AbstractAction;

public class SettingsAction extends AbstractAction {

  @Override
  public void execute() {
    openPage( SettingsPage.class.getName() );
  }
}
