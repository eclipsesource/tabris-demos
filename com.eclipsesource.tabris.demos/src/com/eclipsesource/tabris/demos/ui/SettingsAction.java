package com.eclipsesource.tabris.demos.ui;

import com.eclipsesource.tabris.ui.Action;
import com.eclipsesource.tabris.ui.UIContext;

public class SettingsAction implements Action {

  public void execute( UIContext context ) {
    context.showPreviousPage();
    context.showPage( TempPage.class.getName() );
    // Shell shell = new Shell( context.getDisplay() );
    // MessageBox dialog = new MessageBox( shell );
    // dialog.setText( "Settings" );
    // dialog.setText( "Change the app settings." );
    // DialogUtil.open( dialog, null );
  }
}
