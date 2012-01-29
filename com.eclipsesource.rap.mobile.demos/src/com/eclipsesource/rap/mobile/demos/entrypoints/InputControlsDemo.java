/*******************************************************************************
 * Copyright (c) 2012 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.rap.mobile.demos.entrypoints;

import org.eclipse.rwt.lifecycle.IEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.eclipsesource.rap.mobile.demos.ExampleUtil;


public class InputControlsDemo implements IEntryPoint {

  private Text firstNameField;
  private Text lastNameField;
  private Combo countryCombo;
  private Combo classCombo;
  private DateTime dateField;
  private Button vegetarianCheckbox;

  public int createUI() {
    Display display = new Display();
    Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized(true);
    shell.setLayout( new FillLayout() );
    shell.setBackground( display.getSystemColor( SWT.COLOR_WHITE ) );
    createContent( display, shell );
    shell.open();
    shell.setVisible(true);    
    return 0;
  }

  private void createContent( Display display, Shell shell ) {
    Composite parent = new Composite( shell, SWT.NONE );
    parent.setLayout( ExampleUtil.createGridLayout( 1, true, 0, 0 ) );
    createHeader( display, parent );
    Composite container = new Composite( parent, SWT.NONE );
    container.setLayout( ExampleUtil.createGridLayout( 2, false, 15, 5 ) );
    createInputForm( container );
    createPlaceReservationButton( display, parent );
  }

  private void createHeader( Display display, Composite parent ) {
    Composite labelParent = new Composite( parent, SWT.NONE );
    labelParent.setLayout( ExampleUtil.createGridLayout( 1, true, 0, 0 ) );
    Label label = new Label( labelParent, SWT.NONE );
    GridData layoutDataLabel = new GridData( SWT.CENTER, SWT.CENTER, true, true );
    label.setLayoutData( layoutDataLabel );
    GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, false );
    layoutData.heightHint = 40;
    labelParent.setLayoutData( layoutData );
    labelParent.setBackground( display.getSystemColor( SWT.COLOR_DARK_GREEN ) );
    label.setBackground( display.getSystemColor( SWT.COLOR_DARK_GREEN ) );
    label.setForeground( display.getSystemColor( SWT.COLOR_WHITE ) );
    label.setText( "Oceanic Flight 815 Reservation" );
  }

  private void createInputForm( Composite parent ) {
    firstNameField = createFirstNameField( parent );
    lastNameField = createLastNameField( parent );
    createPasswordField( parent );
    countryCombo = createCountryCombo( parent );
    classCombo = createClassCombo( parent );
    dateField = createDateField( parent );
    createVegetarianCheckbox( parent );
  }

  private Text createFirstNameField( Composite formComp ) {
    new Label( formComp, SWT.NONE ).setText( "First Name:" );
    final Text firstNameText = new Text( formComp, SWT.SINGLE | SWT.BORDER );
    GridData gridData1 = ExampleUtil.createHorzFillData();
    firstNameText.setLayoutData( gridData1 );
    return firstNameText;
  }

  private Text createLastNameField( Composite formComp ) {
    new Label( formComp, SWT.NONE ).setText( "Last Name:" );
    final Text lastNameText = new Text( formComp, SWT.SINGLE | SWT.BORDER );
    GridData gridData2 = ExampleUtil.createHorzFillData();
    lastNameText.setLayoutData( gridData2 );
    return lastNameText;
  }

  private Text createPasswordField( Composite formComp ) {
    new Label( formComp, SWT.NONE ).setText( "Passphrase:" );
    final Text passwordText = new Text( formComp, SWT.PASSWORD | SWT.BORDER );
    GridData gridData3 = ExampleUtil.createHorzFillData();
    passwordText.setLayoutData( gridData3 );
    return passwordText;
  }

  private Combo createCountryCombo( Composite formComp ) {
    new Label( formComp, SWT.NONE ).setText( "Country:" );
    final Combo combo = new Combo( formComp, SWT.BORDER );
    String[] countries = new String[] { "Germany", "Canada", "USA", "Bulgaria" };
    combo.setItems( countries );
    GridData gridData = ExampleUtil.createHorzFillData();
    combo.setLayoutData( gridData );
    combo.select( 0 );
    return combo;
  }

  private Combo createClassCombo( Composite formComp ) {
    new Label( formComp, SWT.NONE ).setText( "Class:" );
    final Combo classCombo = new Combo( formComp, SWT.READ_ONLY | SWT.BORDER );
    String[] classes = new String[] { "Business", "Economy", "Economy Plus" };
    classCombo.setItems( classes );
    GridData gridData = ExampleUtil.createHorzFillData();
    classCombo.setLayoutData( gridData );
    classCombo.select( 0 );
    return classCombo;
  }

  private DateTime createDateField( Composite formComp ) {
    new Label( formComp, SWT.NONE ).setText( "Date:" );
    int dateTimeStyle = SWT.READ_ONLY | SWT.BORDER;
    final DateTime dateTime = new DateTime( formComp, dateTimeStyle );
    return dateTime;
  }

  private void createVegetarianCheckbox( Composite parent ) {
    new Label( parent, SWT.NONE );
    vegetarianCheckbox = new Button( parent, SWT.CHECK );
    vegetarianCheckbox.setLayoutData( new GridData( SWT.LEFT, SWT.CENTER, true, false ) );
    vegetarianCheckbox.setText( "Vegetarian" );
    vegetarianCheckbox.setSelection( true );
  }

  private void createPlaceReservationButton( Display display, Composite parent ) {
    Composite buttonParent = new Composite( parent, SWT.NONE );
    buttonParent.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
    buttonParent.setLayout( ExampleUtil.createGridLayout( 1, true, 10, 10 ) );
    Button button = new Button( buttonParent, SWT.PUSH );
    button.setBackground( display.getSystemColor( SWT.COLOR_DARK_RED ) );
    button.setForeground( display.getSystemColor( SWT.COLOR_WHITE ) );
    button.setText( "Place Reservation" );
    button.setLayoutData( new GridData( SWT.FILL, SWT.BOTTOM, true, true ) );
    
    button.addSelectionListener( new SelectionAdapter() {
      public void widgetSelected( SelectionEvent e ) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( "First Name: " + firstNameField.getText() + "\n" );
        stringBuilder.append( "Last Name: " + lastNameField.getText() + "\n" );
        stringBuilder.append( "Country: " + countryCombo.getText() + "\n" );
        stringBuilder.append( "Class: " + classCombo.getText() + "\n" );
        stringBuilder.append( "Date: " + dateField.getYear() + "/" );
        stringBuilder.append( dateField.getMonth() + "/" );
        stringBuilder.append( dateField.getDay() + "\n" );
        stringBuilder.append( "Vegetarian: " + String.valueOf( vegetarianCheckbox.getSelection() ) + "\n" );
        stringBuilder.append( "-> want to flight to the Island!!!" );
        System.out.println( stringBuilder.toString() );
      }
    } );
  }
}
