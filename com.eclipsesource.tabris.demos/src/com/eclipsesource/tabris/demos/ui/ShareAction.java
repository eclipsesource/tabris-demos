package com.eclipsesource.tabris.demos.ui;

import static java.text.MessageFormat.format;

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
      String body = format( "Check out the book \"{0}\".", book.getTitle() );
      MailOptions launchOptions = new MailOptions( "user@mail.com", body );
      appLauncher.open( launchOptions );
    }
  }
}
