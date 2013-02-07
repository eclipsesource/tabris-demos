
package com.eclipsesource.tabris.demos.ui;

import org.eclipse.rap.rwt.RWT;

import com.eclipsesource.tabris.interaction.AppLauncher;
import com.eclipsesource.tabris.interaction.MailOptions;
import com.eclipsesource.tabris.ui.Action;
import com.eclipsesource.tabris.ui.UIContext;

public class ShareAction implements Action {

  public void execute( UIContext context ) {
    AppLauncher appLauncher = RWT.getClient().getService( AppLauncher.class );
    if( appLauncher != null ) {
      Book book = context.getPageStore().get( BookDetailsPage.BOOK_ITEM, Book.class );
      MailOptions launchOptions = new MailOptions( "user@mail.com", "Check out the book '"
                                                                    + book.getTitle()
                                                                    + "' from "
                                                                    + book.getAuthor()
                                                                    + ". It is really great." );
      appLauncher.open( launchOptions );
    }
  }

}
