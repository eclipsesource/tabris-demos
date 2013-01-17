/*******************************************************************************
 * Copyright (c) 2013 EclipseSource and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.swipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.eclipsesource.tabris.widgets.swipe.SwipeItem;
import com.eclipsesource.tabris.widgets.swipe.SwipeItemProvider;

public class DictionarySwipeItemProvider implements SwipeItemProvider {

  private final List<DictionarySwipeItem> items;

  public DictionarySwipeItemProvider() {
    items = new ArrayList<DictionarySwipeItem>();
    Map<String, String> dictionary = new HashMap<String, String>();
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
    int index = 0;
    for( Entry<String, String> entry : dictionary.entrySet() ) {
      items.add( new DictionarySwipeItem( entry.getKey(), entry.getValue(), index ) );
      index++;
    }
  }

  public SwipeItem getItem( int index ) {
    return items.get( index );
  }

  public int getItemCount() {
    return items.size();
  }
}