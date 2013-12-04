/*******************************************************************************
 * Copyright (c) 2012 EclipseSource and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.entrypoints;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.eclipsesource.tabris.demos.button.Questions;
import com.eclipsesource.tabris.widgets.enhancement.Widgets;

public class ButtonControlsDemo implements EntryPoint {

  private Questions questions;
  private Button buttonA;
  private Button buttonB;
  private Button buttonC;
  private Button buttonNext;
  private Button buttonPrevious;
  private Label labelQuestion;

  @Override
  public int createUI() {
    Display display = new Display();
    Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    GridLayoutFactory.fillDefaults().applyTo( shell );
    createToolbar( shell );
    createContent( shell );
    createNavigation( shell );
    shell.open();
    reset();
    return 0;
  }

  private void createToolbar( Composite parent ) {
    ToolBar toolBar = new ToolBar( parent, SWT.NONE );
    GridDataFactory.fillDefaults().grab( true, false ).align( SWT.FILL, SWT.TOP ).applyTo( toolBar );

    ToolItem titleItem = new ToolItem( toolBar, SWT.NONE );
    Widgets.onToolItem( titleItem ).useAsTitle();
    titleItem.setText( "You don't know that?" );

    ToolItem resetItem = new ToolItem( toolBar, SWT.PUSH );
    resetItem.setText( "Restart" );
    resetItem.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        reset();
        updateUI();
      }
    } );
  }

  private void createContent( Composite parent ) {
    Composite content = new Composite( parent, SWT.NONE );
    GridDataFactory.fillDefaults().grab( true, true ).align( SWT.FILL, SWT.FILL ).applyTo( content );
    GridLayoutFactory.fillDefaults().margins( 16, 16 ).applyTo( content );
    createQuestion( content );
    createAnswers( content );
  }

  public void createQuestion( Composite parent ) {
    labelQuestion = new Label( parent, SWT.WRAP | SWT.CENTER );
    GridDataFactory.fillDefaults().align( SWT.FILL, SWT.FILL ).grab( true, true ).applyTo( labelQuestion );
    FontDescriptor labelFontDescriptor = FontDescriptor.createFrom( labelQuestion.getFont() );
    FontDescriptor bigFontDescriptor = labelFontDescriptor.setStyle( SWT.BOLD ).increaseHeight( 7 );
    labelQuestion.setFont( bigFontDescriptor.createFont( parent.getDisplay() ) );
    labelQuestion.setForeground( parent.getDisplay().getSystemColor( SWT.COLOR_BLACK ) );
  }

  private void createAnswers( Composite parent ) {
    Composite result = new Composite( parent, SWT.NONE );
    GridDataFactory.fillDefaults().grab( true, true ).align( SWT.FILL, SWT.FILL ).applyTo( result );
    GridLayoutFactory.fillDefaults().applyTo( result );
    buttonA = createAnswerButton( result, new RGB(43, 232, 105) );
    buttonB = createAnswerButton( result, new RGB(182, 107, 153) );
    buttonC = createAnswerButton( result, new RGB(225, 134, 87) );
  }

  private Button createAnswerButton( Composite result, RGB color ) {
    final Button button = new Button( result, SWT.RADIO | SWT.WRAP );
    button.setForeground( new Color( button.getDisplay(), color ) );
    button.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        if( button.getSelection() ) {
          answerSelected( button.getText() );
        }
      }
    } );
    return button;
  }

  private void createNavigation( Composite parent ) {
    Composite navigation = new Composite( parent, SWT.NONE );
    GridDataFactory.fillDefaults().grab( true, false ).align( SWT.FILL, SWT.FILL ).applyTo( navigation );
    GridLayoutFactory.fillDefaults().numColumns( 2 ).margins( 8, 8 ).equalWidth( true ).applyTo( navigation );

    createNavigationPrevious( navigation );
    createNavigationNext( navigation );
  }

  public void createNavigationNext( Composite parent ) {
    buttonNext = new Button( parent, SWT.PUSH | SWT.BORDER );
    buttonNext.setText( "Next Question" );
    GridDataFactory.fillDefaults().grab( true, true ).align( SWT.END, SWT.END ).applyTo( buttonNext);
    buttonNext.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        next();
      }
    } );
  }

  public void createNavigationPrevious( Composite parent ) {
    buttonPrevious = new Button( parent, SWT.PUSH );
    buttonPrevious.setText( "Last Question" );
    GridDataFactory.fillDefaults().grab( true, true ).align( SWT.BEGINNING, SWT.END ).applyTo( buttonPrevious );
    buttonPrevious.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        previous();
      }
    } );
  }

  /**
   * Update UI state
   */
  protected void answerSelected( String answer ) {
    String currentQuestion = questions.getCurrentQuestion();
    boolean correct = questions.isCorrect( currentQuestion, answer );
    if( correct ) {
      buttonNext.setForeground( buttonNext.getDisplay().getSystemColor( SWT.COLOR_DARK_GREEN ) );
    } else {
      buttonNext.setForeground( buttonNext.getDisplay().getSystemColor( SWT.COLOR_DARK_RED ) );
    }
    buttonNext.setEnabled( correct && questions.hasNextQuestion() );
  }

  private void updateUI() {
    String currentQuestion = questions.getCurrentQuestion();
    labelQuestion.setText( currentQuestion );
    labelQuestion.getParent().layout();
    String[] answers = questions.getAnswers( currentQuestion );
    buttonA.setText( answers[0] );
    buttonB.setText( answers[1] );
    buttonC.setText( answers[2] );
    buttonA.getParent().layout();
    buttonPrevious.setEnabled( questions.hasPreviousQuestion() );
    buttonNext.setEnabled( false );
  }

  /**
   * Navigate the Questions
   */

  private void previous() {
    questions.getPreviousQuestion();
    resetButtons();
    updateUI();
  }

  protected void next() {
    questions.getNextQuestion();
    resetButtons();
    updateUI();
  }

  private void resetButtons() {
    buttonA.setSelection( false );
    buttonB.setSelection( false );
    buttonC.setSelection( false );
  }

  private void reset() {
    questions = new Questions();
    updateUI();
    resetButtons();
  }

}