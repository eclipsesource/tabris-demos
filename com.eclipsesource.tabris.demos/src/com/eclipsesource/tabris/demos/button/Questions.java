/*******************************************************************************
 * Copyright (c) 2013 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Questions {
  
  private static Map<String, String[]> database;
  private List<String> questions;
  private int currentQuestionIndex = 0;
  
  static {
    database = new HashMap<String, String[]>();
    database.put( "What TV cartoon character wrote on his school chalkboard. \"I will not aim at the head\" and \"My name is not Dr. Death\"?",
                   new String[]{"Bart Simpson","Butthead","Dennis the menace"} );
    database.put( "How many friends are there in Friends?",
                   new String[]{"Six","Five","Four"} );
    database.put( "What's Fran's last name on The Nanny?",
                   new String[]{"Fine","Cline","Zine"} );
    database.put( "How many fingers does Homer Simpson have?",
                   new String[]{"Eight","Nine","Ten"} );
    database.put( "In what year did Star Trek's voyages begin on screen?",
                   new String[]{"1966","1978","1952"} );
    database.put( "Who led The A Team?",
                   new String[]{"Hannibal Smith","Templeton Peck","Bosco Albert Baracus"} );
    database.put( "In Sledge Hammer, what did the Inspector have on his car sticker?",
                   new String[]{"I Love Violence","Trust me. I know what I’m doing.","I love Susi"} );
    database.put( "Which cult series asked, \"Who killed Laura Palmer?\"",
                   new String[]{"Twin Peaks","Hart to Hart","Desperate Housewives"} );
    database.put( "What was the name of the physician in The Incredible Hulk?",
                   new String[]{"Dr. David Banner","Professor Charles Xavier","Dr. Henry 'Hank' McCoy"} );
    database.put( "Who was the captain of the Enterprise on the pilot episode of Star Trek?",
                   new String[]{"Christopher Pike","James Tiberius Kirk","Khan Noonien Singh"} );
    database.put( "Which series was based on the novel 'Cyborg' by Martin Caidin?",
                   new String[]{"The Six Million Dollar Man","Robocop","Battlestar Galactica"} );
    database.put( "What kid's show's interracial cast needed riot police protection during a 1969 trip to Mississippi?",
                   new String[]{"Sesame Street","Muppet Show","The Addams Family"} );
    database.put( "What sitcom was among the top 20 most watched shows every season during its entire run, form 1984 to 1992?",
                   new String[]{"The Cosby Show","Roseanne","Star Trek TNG"} );
    database.put( "Who appeared in Return of the Killer Tomatoes before he landed a role on ER?",
                   new String[]{"George Clooney","Noah Wyle","Eriq La Salle"} );
    database.put( "What reformed con artist shared a detective agency with Laura Holt?",
                   new String[]{"Remington Steele","Ethan Hunt","Agent Maxwell Smart"} );
    database.put( "What two cartoon rodents attempt every night to take over the world from their cages in Acme Labs?",
                   new String[]{"Pinky and the Brain", "Chip 'n' Dale","Benjy and Frankie"} );
    database.put( "What TV star did 500,000 people show up to watch sing at the Berlin Wall?",
                   new String[]{"David Hasselhoff","Michael Jackson","William Shatner"} );
    database.put( "What does Mr. Spock of Star Trek have to have every seven years?",
                   new String[]{"Sex","A new body","A vulcan mind melt"} );
  }

  public Questions() {
    List<String> keys = new ArrayList<String>(database.keySet());
    Collections.shuffle(keys);
    questions = keys;
  }
  
  public static Map<String, String[]> getQuestions() {
    if( database == null) {
    }
    return database;
  }
  
  public boolean hasNextQuestion() {
    return currentQuestionIndex+1 < database.size();
  }
  
  public String getNextQuestion() {
    if( hasNextQuestion() ) {
      currentQuestionIndex++;
    }
    return questions.get( currentQuestionIndex );
  }

  public boolean hasPreviousQuestion() {
    return currentQuestionIndex > 0;
  }
  
  public String getPreviousQuestion() {
    if( hasPreviousQuestion() ) {
      currentQuestionIndex--;
    }
    return questions.get( currentQuestionIndex );
  }

  public boolean isCorrect(String question, String answer ) {
    boolean result = false;
    if( getQuestions().containsKey( question ) ) {
      String[] strings = getQuestions().get( question );
      result = strings[ 0 ].equals( answer );
    }
    return  result;
  }

  public String getCurrentQuestion() {
    return questions.get( currentQuestionIndex );
  }

  public String[] getAnswers( String currentQuestion ) {
    List<String> answers = new ArrayList<String>(Arrays.asList( database.get( currentQuestion ) ));
    Collections.shuffle(answers);
    return answers.toArray( new String[answers.size()] );
  }
}
