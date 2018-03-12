/*******************************************************************************
 * Copyright (c) 2013, 2018 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.entrypoints;

import static com.eclipsesource.tabris.widgets.enhancement.Widgets.onToolItem;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.RowLayoutFactory;
import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.eclipsesource.tabris.widgets.ScrollingComposite;
import com.eclipsesource.tabris.widgets.enhancement.Widgets;


public class ScrollDemo implements EntryPoint {

  private final List<Control> controls;
  private Composite scrollingArea;

  public ScrollDemo() {
    controls = new ArrayList<Control>();
  }

  @Override
  public int createUI() {
    Display display = new Display();
    Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    shell.setLayout( new FillLayout() );
    shell.setBackground( display.getSystemColor( SWT.COLOR_WHITE ) );
    createContent( display, shell, SWT.V_SCROLL );
    shell.open();
    shell.setVisible( true );
    return 0;
  }

  private void handleIndexOutOfBound( final Text jumpTo ) {
    MessageBox messageBox = new MessageBox( jumpTo.getShell(), SWT.ICON_ERROR );
    messageBox.setMessage( "Control does not exist." );
    messageBox.setText( "Error" );
    messageBox.open( null );
  }

  private void handleWrongNumber( final Text jumpTo ) {
    MessageBox messageBox = new MessageBox( jumpTo.getShell(), SWT.ICON_ERROR );
    messageBox.setMessage( "Number must be an Integer" );
    messageBox.setText( "Number Error" );
    messageBox.open( null );
  }

  private void createContent( Display display, final Shell shell, int scrollStyle ) {
    scrollingArea = new Composite( shell, SWT.NONE );
    scrollingArea.setLayout( GridLayoutFactory.fillDefaults().numColumns( 1 ).spacing( 0, 0 ).create() );
    createToolBar();
    ScrollingComposite scrollingParent = createScrollingComposite( display, scrollingArea, scrollStyle );
    addData( scrollingParent );
    Composite controlsParent = createActionsArea( scrollingArea );
    Text itemNumberText = new Text( controlsParent, SWT.BORDER );
    Widgets.onText( itemNumberText ).useNumberKeyboard();
    itemNumberText.setMessage( "Enter Item # and ..." );
    addShowAction( controlsParent, scrollingParent, itemNumberText );
    addCheckVisibilityAction( controlsParent, scrollingParent, itemNumberText );
    addFlipAction( controlsParent, scrollingParent );
  }

  private void createToolBar() {
    ToolBar toolBar = new ToolBar( scrollingArea, SWT.NONE );
    toolBar.setLayoutData( GridDataFactory.fillDefaults().grab( true, false ).create() );
    ToolItem title = new ToolItem( toolBar, SWT.NONE );
    title.setText( "Top Characters" );
    onToolItem( title ).useAsTitle();
  }

  private Composite createActionsArea( Composite parent ) {
    Composite controlsParent = new Composite( parent, SWT.NONE );
    controlsParent.setLayoutData( GridDataFactory.fillDefaults().grab( true, false ).create() );
    controlsParent.setLayout( GridLayoutFactory.fillDefaults().numColumns( 4 ).margins( 5, 5 ).create() );
    controlsParent.setBackground( parent.getDisplay().getSystemColor( SWT.COLOR_GRAY ) );
    return controlsParent;
  }

  private ScrollingComposite createScrollingComposite( Display display, Composite parent, int scrollStyle ) {
    final ScrollingComposite scrollableParent = new ScrollingComposite( parent, scrollStyle );
    int layoutStyle = scrollStyle == SWT.V_SCROLL ? SWT.VERTICAL : SWT.HORIZONTAL;
    scrollableParent.setLayout( RowLayoutFactory.fillDefaults().type( layoutStyle ).wrap( false ).fill( true ).create() );
    scrollableParent.setLayoutData( GridDataFactory.fillDefaults().grab( true, true ).align( SWT.CENTER, SWT.FILL ).create() );
    scrollableParent.setBackground( new Color( display, 255, 255, 255 ) );
    return scrollableParent;
  }

  private void addData( final ScrollingComposite scrollableParent ) {
    String[] data = getData();
    for( String name : data ) {
      addContentItem( scrollableParent, name );
    }
  }

  private void addShowAction( Composite controlsParent,
                              final ScrollingComposite scrollableParent,
                              final Text jumpTo )
  {
    Button showButton = new Button( controlsParent, SWT.PUSH );
    showButton.setBackground( controlsParent.getDisplay().getSystemColor( SWT.COLOR_DARK_GRAY ) );
    showButton.setForeground( controlsParent.getDisplay().getSystemColor( SWT.COLOR_WHITE ) );
    showButton.setText( "Show" );
    showButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e ) {
        String number = jumpTo.getText();
        try {
          int index = Integer.parseInt( number );
          Control control = controls.get( index );
          scrollableParent.reveal( control );
        } catch( NumberFormatException nfe ) {
          handleWrongNumber( jumpTo );
        } catch( IndexOutOfBoundsException ibe ) {
          handleIndexOutOfBound( jumpTo );
        }
      }

    } );
  }

  private void addCheckVisibilityAction( Composite controlsParent,
                                         final ScrollingComposite scrollableParent,
                                         final Text jumpTo )
  {
    Button checkVisibility = new Button( controlsParent, SWT.PUSH );
    checkVisibility.setBackground( controlsParent.getDisplay().getSystemColor( SWT.COLOR_DARK_GRAY ) );
    checkVisibility.setForeground( controlsParent.getDisplay().getSystemColor( SWT.COLOR_WHITE ) );
    checkVisibility.setText( "Check Visibility" );
    checkVisibility.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        String number = jumpTo.getText();
        try {
          int index = Integer.parseInt( number );
          Control control = controls.get( index );
          boolean revealed = scrollableParent.isRevealed( control );
          MessageBox messageBox = new MessageBox( jumpTo.getShell(), SWT.ICON_ERROR );
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append( "Item at index " + index + " is " );
          if( !revealed ) {
            stringBuilder.append( "NOT " );
          }
          stringBuilder.append( "visible." );
          messageBox.setMessage( stringBuilder.toString() );
          messageBox.setText( "Visibility" );
          messageBox.open( null );
        } catch( NumberFormatException nfe ) {
          handleWrongNumber( jumpTo );
        } catch( IndexOutOfBoundsException ibe ) {
          handleIndexOutOfBound( jumpTo );
        }
      }
    } );
  }

  private void addFlipAction( Composite controlsParent, final ScrollingComposite scrollableParent ) {
    Button changeStyle = new Button( controlsParent, SWT.PUSH );
    changeStyle.setText( "Flip Scroll Style" );
    changeStyle.setBackground( controlsParent.getDisplay().getSystemColor( SWT.COLOR_DARK_GRAY ) );
    changeStyle.setForeground( controlsParent.getDisplay().getSystemColor( SWT.COLOR_WHITE ) );
    changeStyle.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        int style = scrollableParent.getStyle();
        if( ( style & SWT.V_SCROLL ) != 0 ) {
          style = SWT.H_SCROLL;
        } else {
          style = SWT.V_SCROLL;
        }
        Display display = scrollingArea.getDisplay();
        Shell shell = scrollingArea.getShell();
        controls.clear();
        scrollingArea.dispose();
        createContent( display, shell, style );
        shell.layout( true, true );
      }
    } );
  }

  private Control addContentItem( final ScrollingComposite scrollableParent, String name ) {
    Composite content = new Composite( scrollableParent, SWT.NONE );
    content.setBackground( new Color( content.getDisplay(), 100, 100, 100 ) );
    content.setBackgroundMode( SWT.INHERIT_FORCE );
    controls.add( content );
    content.setLayout( GridLayoutFactory.fillDefaults().margins( 10, 10 ).equalWidth( true ).create() );
    Label label = new Label( content, SWT.NONE );
    label.setLayoutData( GridDataFactory.fillDefaults().align( SWT.CENTER, SWT.CENTER ).create() );
    label.setForeground( new Color( content.getDisplay(), 255, 255, 255 ) );
    label.setText( "# " + ( controls.indexOf( content ) ) + " " + name );
    label.setLayoutData( GridDataFactory.fillDefaults().grab( true, true ).align( SWT.CENTER, SWT.CENTER ).create() );
    return content;
  }

  private String[] getData() {
    return new String[] {
      "Tim Riggins from Friday Night Lights",
      "The Bride from Kill Bill",
      "Lisbeth Salander from The Girl With the Dragon Tattoo and its sequels",
      "Violet Weston from August: Osage County",
      "Bernie Mac from The Bernie Mac Show",
      "Wilhelmina from Ugly Betty",
      "Truman from The Truman Show",
      "Hancock from Hancock",
      "Marge Gunderson from Fargo",
      "Wikus van de Merwe from District 9",
      "Napoleon Dynamite from Napoleon Dynamite",
      "Tony Stark from the Iron Man series",
      "Karen Walker and Jack McFarland from Will & Grace",
      "Daniel Plainview from There Will Be Blood",
      "Dr. Gregory House from House, M.D.",
      "Jen Yu from Crouching Tiger, Hidden Dragon",
      "Tracy Flick from Election",
      "Amanda Woodward from Melrose Place",
      "Gorillaz, the animated rock band",
      "Elphaba from Wicked",
      "Patty Hewes from Damages",
      "Mimi Marquez from Rent",
      "Tyler Durden from Fight Club",
      "David Brent from The Office (original version)",
      "Don Draper from Mad Men",
      "Catherine Trammell from Basic Instinct",
      "Kara “Starbuck” Thrace from Battlestar Galactica",
      "Det. Alonzo Harris from Training Day",
      "Mary Katherine Gallagher from Saturday Night Live",
      "Miranda Priestly from The Devil Wears Prada",
      "Effie White from Dreamgirls",
      "Allie and Noah from The Notebook",
      "Lorelai and Rory Gilmore from Gilmore Girls",
      "Maximus from Gladiator",
      "John Locke from Lost",
      "Jimmy Corrigan from Jimmy Corrigan: The Smartest Kid on Earth",
      "Vic Mackey from The Shield",
      "Mary Jones from Precious: Based on the Novel Push by Saphhire",
      "Master Chief from the Halo series",
      "Thelma and Louise from Thelma & Louise",
      "Clayton Bigsby from Chappelle’s Show",
      "Barney Stinson from How I Met Your Mother",
      "Tracy Jordan from 30 Rock",
      "Juno from Juno",
      "Edward Cullen from the Twilight saga",
      "Annie Wilkes from Misery",
      "Omar Little from The Wire",
      "Pearl the Landlord from FunnyorDie.com",
      "Vivian Ward from Pretty Woman",
      "Red from The Shawshank Redemption",
      "Corky St. Clair Waiting for Guffman",
      "Jerry Maguire from Jerry Maguire",
      "Stewie Griffin from Family Guy",
      "Jack Bauer from 24",
      "Cal Stephanides from Middlesex",
      "Sydney Bristow from Alias",
      "Harold and Kumar from the Harold & Kumar series",
      "Ron Burgundy from Anchorman: The Legend of Ron Burgundy",
      "Gob Bluth from Arrested Development",
      "Elmo from Sesame Street",
      "Keyser Söze from The Usual Suspects",
      "Gollum from The Lord of the Rings",
      "Dexter Morgan from Dexter",
      "Cher from Clueless",
      "Sarah Connor from Terminator 2: Judgment Day",
      "Beavis and Butt-Head from Beavis and Butt-Head",
      "Forrest Gump from Forrest Gump",
      "“Stephen Colbert” from The Colbert Report",
      "Vincent Vega and Jules Winnfield from Pulp Fiction",
      "Madea from several Tyler Perry films and plays",
      "Frasier from Frasier",
      "Kavalier and Clay from The Amazing Adventures of Kavalier & Clay",
      "Woody from the Toy Story series",
      "Felicity Porter from Felicity",
      "Austin Powers from the Austin Powers series",
      "Eric Cartman from South Park",
      "Roseanne Conner from Roseanne",
      "Ally McBeal from Ally McBeal",
      "Morpheus from The Matrix series",
      "Sue Sylvester from Glee",
      "Lara Croft from the Tomb Raider franchise",
      "Bridget Jones from the Bridget Jones series",
      "Shrek from the Shrek series",
      "Jeff “The Dude” Lebowski from The Big Lebowski",
      "Jack Sparrow from the Pirates of the Caribbean series",
      "Fox Mulder and Dana Scully from The X-Files",
      "Cosmo Kramer from Seinfeld",
      "SpongeBob SquarePants from SpongeBob SquarePants",
      "Carrie Bradshaw from Sex and the City",
      "Hannibal Lecter from The Silence of the Lambs and its sequels",
      "Edward Scissorhands from Edward Scissorhands",
      "Rachel Green from Friends",
      "The Joker from The Dark Knight",
      "Tony Soprano from The Sopranos",
      "Buffy from Buffy the Vampire Slayer",
      "Harry Potter from the Harry Potter series",
      "Homer Simpson from The Simpsons"
    };
  }

}
