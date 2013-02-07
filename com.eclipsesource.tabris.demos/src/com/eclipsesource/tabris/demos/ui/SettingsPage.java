package com.eclipsesource.tabris.demos.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.eclipsesource.tabris.demos.entrypoints.UiUtil;
import com.eclipsesource.tabris.ui.Page;
import com.eclipsesource.tabris.ui.UIContext;

public class SettingsPage implements Page {

  public void create( Composite parent, UIContext context ) {
    Composite container = new Composite( parent, SWT.NONE );
    container.setLayout( UiUtil.createGridLayout( 1, false ) );
    Label textLabel = new Label( container, SWT.CENTER );
    textLabel.setLayoutData( UiUtil.createCenteredGrab() );
    textLabel.setText( "Settings" );
  }

  public void activate( UIContext context ) {
    context.getActionManager().setActionVisible( SettingsAction.class.getName(), false );
  }

  public void deactivate( UIContext context ) {
    context.getActionManager().setActionVisible( SettingsAction.class.getName(), true );
  }
}
