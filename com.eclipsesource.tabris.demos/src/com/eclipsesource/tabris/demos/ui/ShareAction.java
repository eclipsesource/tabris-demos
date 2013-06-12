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

import static com.eclipsesource.tabris.demos.ui.BookDetailsPage.BOOK_ITEM;
import static java.text.MessageFormat.format;

import org.eclipse.rap.rwt.RWT;

import com.eclipsesource.tabris.interaction.AppLauncher;
import com.eclipsesource.tabris.interaction.MailOptions;
import com.eclipsesource.tabris.ui.AbstractAction;

public class ShareAction extends AbstractAction {

  @Override
  public void execute() {
    AppLauncher appLauncher = RWT.getClient().getService( AppLauncher.class );
    if( appLauncher != null ) {
      Book book = getCurrentPageData().get( BOOK_ITEM, Book.class );
      String body = format( "Check out the book \"{0}\".", book.getTitle() );
      MailOptions launchOptions = new MailOptions( "user@mail.com", body );
      appLauncher.open( launchOptions );
    }
  }
}
