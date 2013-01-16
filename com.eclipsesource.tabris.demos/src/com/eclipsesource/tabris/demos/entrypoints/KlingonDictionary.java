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
package com.eclipsesource.tabris.demos.entrypoints;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;


public class KlingonDictionary {

  private final Map<String, String> dictionary;
  private final Random random;

  public KlingonDictionary() {
    random = new Random();
    dictionary = new HashMap<String, String>();
    dictionary.put( "yes", "HISlaH" );
    dictionary.put( "no", "ghobe’" );
    dictionary.put( "No problem.", "qay’be’" );
    dictionary.put( "Success! (shout)", "Qapla’" );
    dictionary.put( "What do you want? (greeting)", "nuqneH" );
    dictionary.put( "I understand.", "jIyaj" );
    dictionary.put( "I do not understand.", "jIyajbe’" );
    dictionary.put( "Do you speak Klingon?", "tlhIngan Hol Dajatlh’a’" );
    dictionary.put( "I cannot speak Klingon.", "tlhIngan Hol vIjatlhaHbe’" );
    dictionary.put( "Where is a good restaurant?", "nuqDaq ’oH Qe’ QaQ’e’" );
    dictionary.put( "Beam me aboard!", "HIjol" );
    dictionary.put( "Come here!", "HIghoS" );
    dictionary.put( "Pay now!", "DaH yIDIl" );
    dictionary.put( "You lie.", "bInep" );
    dictionary.put( "I did not do it.", "vIta’pu’be’" );
    dictionary.put( "I am a Klingon.", "tlhIngan jIH" );
    dictionary.put( "I want to sleep.", "jIQong vIneH" );
    dictionary.put( "Fire the torpedoes!", "cha yIbaH qara’DI’" );
    dictionary.put( "Where is the bathroom?", "nuqDaq ’oH puchpa’’e’" );
    dictionary.put( "I have a headache.", "jIwuQ" );
    dictionary.put( "Hurry up!", "tugh" );
    dictionary.put( "Is this seat taken?", "quSDaQ ba’lu’’a’" );
  }

  public String[] getRandomEntry() {
    Set<Entry<String, String>> set = dictionary.entrySet();
    int itemIndex = random.nextInt( dictionary.size() - 1 );
    int i = 0;
    for( Entry<String, String> entry : set ) {
      if( i == itemIndex ) {
        return new String[] { entry.getKey(), entry.getValue() };
      }
      i++;
    }
    return null;
  }
}
