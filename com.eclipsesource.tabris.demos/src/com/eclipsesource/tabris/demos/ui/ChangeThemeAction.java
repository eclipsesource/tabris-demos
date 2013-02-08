
package com.eclipsesource.tabris.demos.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import com.eclipsesource.tabris.ui.Action;
import com.eclipsesource.tabris.ui.UIContext;

public class ChangeThemeAction implements Action {

  public void execute( UIContext context ) {
    Color foreground = context.getDisplay().getSystemColor( SWT.COLOR_WHITE );
    Color background = context.getDisplay().getSystemColor( SWT.COLOR_BLACK );
    ReadBookPage page = ( ReadBookPage )context.getPage();
    page.setTheme( foreground, background );
  }

}
