package com.eclipsesource.tabris.demos.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.eclipsesource.tabris.demos.entrypoints.UiUtil;
import com.eclipsesource.tabris.ui.Page;
import com.eclipsesource.tabris.ui.UIContext;

public class TempPage implements Page {

  public static final String BOOK_ITEM = "bookItem";
  private Label textLabel;
  private Composite container;

  public void create( Composite parent, UIContext context ) {
    container = new Composite( parent, SWT.NONE );
    GridLayout layout = UiUtil.createGridLayout( 1, false );
    layout.marginWidth = 16;
    layout.marginHeight = 16;
    container.setLayout( layout );
    textLabel = new Label( container, SWT.NONE );
    textLabel.setLayoutData( UiUtil.createFill() );
    textLabel.setText( "Temp page." );
  }

  public void setTheme( Color foreground, Color background ) {
    textLabel.setForeground( foreground );
    textLabel.setBackground( background );
    container.setBackground( background );
  }

  public void activate( UIContext context ) {
    setSettingsActionVisibility( context, false );
  }

  public void deactivate( UIContext context ) {
    setSettingsActionVisibility( context, true );
  }

  private void setSettingsActionVisibility( UIContext context, boolean visible ) {
    context.getActionManager().setActionVisible( SettingsAction.class.getName(), true );
  }
}
