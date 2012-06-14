/*******************************************************************************
 * Copyright (c) 2011 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.enron;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


final class EnronDataset {

  private final File root;

  public EnronDataset( File root ) {
    this.root = root;
  }

  public Node getRootNode() throws IOException {
    new EnronDatasetIndexer( root ).index();
    return new Folder( root );
  }

  static class Node {

    private final Folder parent;
    private final String name;
    private final String subject;
    private final String from;

    private Node( Folder parent, String name, String subject, String from ) {
      this.parent = parent;
      this.name = name;
      this.subject = subject;
      this.from = from;
    }

    public boolean hasChildren() {
      return false;
    }

    public int getChildCount() {
      return 0;
    }

    public File getFile() {
      return new File( parent.file, name );
    }

    public String getTitle() {
      return subject;
    }
    
    public String getFrom() {
      return from;
    }

    public Folder getParent() {
      return parent;
    }

    public String readContents() throws IOException {
      return readFromFile( getFile() );
    }

    protected String readFromFile( File file ) throws FileNotFoundException, IOException {
      StringBuilder resultBuffer = new StringBuilder();
      FileReader reader = new FileReader( file );
      try {
        char[] charBuffer = new char[ 8196 ];
        int charsRead = 0;
        while( ( charsRead = reader.read( charBuffer  ) ) != -1 ) {
          resultBuffer.append( charBuffer, 0, charsRead );
        }
      } finally {
        reader.close();
      }
      return resultBuffer.toString();
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ( ( from == null )
                                                  ? 0
                                                  : from.hashCode() );
      result = prime * result + ( ( name == null )
                                                  ? 0
                                                  : name.hashCode() );
      result = prime * result + ( ( parent == null )
                                                    ? 0
                                                    : parent.hashCode() );
      result = prime * result + ( ( subject == null )
                                                     ? 0
                                                     : subject.hashCode() );
      return result;
    }

    @Override
    public boolean equals( Object obj ) {
      if( this == obj )
        return true;
      if( obj == null )
        return false;
      if( getClass() != obj.getClass() )
        return false;
      Node other = ( Node )obj;
      if( from == null ) {
        if( other.from != null )
          return false;
      } else if( !from.equals( other.from ) )
        return false;
      if( name == null ) {
        if( other.name != null )
          return false;
      } else if( !name.equals( other.name ) )
        return false;
      if( parent == null ) {
        if( other.parent != null )
          return false;
      } else if( !parent.equals( other.parent ) )
        return false;
      if( subject == null ) {
        if( other.subject != null )
          return false;
      } else if( !subject.equals( other.subject ) )
        return false;
      return true;
    }
  }

  static class Folder extends Node {

    private final File file;
    private final int childCount;
    private Node[] children;

    private Folder( File file ) {
      super( null, null, null, null );
      this.file = file;
      readChildrenFromIndex();
      this.childCount = children.length;
    }

    private Folder( Folder parent, String name, int count ) {
      super( parent, name , name, name );
      this.file = new File( parent.file, name );
      this.childCount = count;
    }

    @Override
    public boolean hasChildren() {
      return childCount > 0;
    }

    @Override
    public int getChildCount() {
      return childCount;
    }

    @Override
    public String readContents() throws IOException {
      return "";
    }

    public Node getChild( int index ) {
      readChildrenFromIndex();
      return children[ index ];
    }

    public Node[] getChildren() {
      readChildrenFromIndex();
      return children;
    }

    private void readChildrenFromIndex() {
      if( children == null ) {
        try {
          children = readIndex();
//          if( childCount != 0 && children.length != childCount ) {
//            throw new RuntimeException( "Children count in index ("
//                                        + children.length
//                                        + ") does not match default ("
//                                        + childCount
//                                        + "): "
//                                        + file.getAbsolutePath()
//                                        + " " );
//          }
        } catch( IOException e ) {
          throw new RuntimeException( "Failed to read index for " + file.getAbsolutePath() );
        }
      }
    }

    private Node[] readIndex() throws IOException {
      File indexFile = new File( file, ".index" );
      String indexString = readFromFile( indexFile );
      String[] lines = indexString.split( "\n" );
      List<Node> nodes = new ArrayList<Node>();
      for( int i = 0; i < lines.length; i++ ) {
        String line = lines[ i ];
        String[] parts = line.split( "\t" );
        if( parts.length == 5 ) {
          if( "d".equals( parts[ 0 ] ) ) {
            nodes.add( new Folder( this, parts[ 1 ], Integer.parseInt( parts[ 4 ] ) ) );
          } else if( "f".equals( parts[ 0 ] ) ) {
            nodes.add( new Node( this, parts[ 1 ], parts[ 2 ], parts[ 3 ] ) );
          }
        }
      }
      Node[] result = new Node[ nodes.size() ];
      nodes.toArray( result );
      return result;
    }
  }

}
