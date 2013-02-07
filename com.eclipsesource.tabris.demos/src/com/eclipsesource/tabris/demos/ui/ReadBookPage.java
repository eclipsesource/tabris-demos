package com.eclipsesource.tabris.demos.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.eclipsesource.tabris.demos.entrypoints.UiUtil;
import com.eclipsesource.tabris.ui.Page;
import com.eclipsesource.tabris.ui.UIContext;

public class ReadBookPage implements Page {

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
    textLabel.setText( "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum. Typi non habent claritatem insitam; est usus legentis in iis qui facit eorum claritatem. Investigationes demonstraverunt lectores legere me lius quod ii legunt saepius. Claritas est etiam processus dynamicus, qui sequitur mutationem consuetudium lectorum. Mirum est notare quam littera gothica, quam nunc putamus parum claram, anteposuerit litterarum formas humanitatis per seacula quarta decima et quinta decima. Eodem modo typi, qui nunc nobis videntur parum clari, fiant sollemnes in futurum." );
    Book book = context.getPageStore().get( BOOK_ITEM, Book.class );
    context.setTitle( book.getTitle() );
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
    context.getActionManager().setActionVisible( SettingsAction.class.getName(), visible );
  }
}
