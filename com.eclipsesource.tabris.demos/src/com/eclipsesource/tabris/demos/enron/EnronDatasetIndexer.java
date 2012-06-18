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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


class EnronDatasetIndexer {

  private final File root;
  private int fileCount;
  private int dirCount;

  public static void main( String[] args ) {
    if( args.length == 0 ) {
      System.err.println( "Missing root directory" );
      System.exit( 42 );
    }
    File root = new File( args[ 0 ] );
    System.out.println( "Indexing " + root.getAbsolutePath() + " ..." );
    EnronDatasetIndexer indexer = new EnronDatasetIndexer( root );
    try {
      indexer.index();
    } catch( IOException exception ) {
      throw new RuntimeException( exception );
    }
    System.out.println( "Done." );
    System.out.println( "Processed directories: " + indexer.dirCount );
    System.out.println( "Processed files: " + indexer.fileCount );
    System.out.println( "Total: " + ( indexer.dirCount + indexer.fileCount ) );
  }

  public EnronDatasetIndexer( File root ) {
    this.root = root;
    fileCount = 0;
    dirCount = 0;
  }

  public void index() throws IOException {
    index( root );
  }

  private int index( File file ) throws IOException {
    int count = 0;
    File indexFile = getIndexFile( file );
    if( indexFile.exists() ) {
      count = readChildCountFromIndexFile( indexFile );
    } else {
      File[] children = file.listFiles();
      if( children == null ) {
        throw new RuntimeException( "no child count available for " + file.getAbsolutePath() );
      }
      List<FileEntry> list = new ArrayList<FileEntry>();
      for( File child : children ) {
        if( !".index".equals( child.getName() ) ) {
          count++;
          count( child );
          if( child.isDirectory() ) {
            int childCount = index( child );
            list.add( new FileEntry( child.getName(), child.getName(), child.getName(), 'd', childCount ) );
          } else {
            String[] displayTexts = getDisplayTexts( child );
            list.add( new FileEntry( child.getName(), displayTexts[ 0 ], displayTexts[ 1 ], 'f', 0 ) );
          }
        }
      }
      createIndexFile( indexFile, list );
    }
    return count;
  }

  private String[] getDisplayTexts( File child ) {
    String[] result = new String[] { "[No Subject]", "[Unknown]" };
    BufferedReader bufferedReader = null;
    try {
      FileReader reader = new FileReader( child );
      bufferedReader = new BufferedReader( reader );
      String line;
      String subject = "Subject: ";
      String from = "From: ";
      boolean fromFound = false;
      boolean subjectFound = false;
      while( ( line = bufferedReader.readLine() ) != null && ( !fromFound || !subjectFound ) ) {
        int indexOfSubject = line.indexOf( subject );
        if( indexOfSubject != -1 ) {
          subjectFound = true;
          String subjectText = line.substring( indexOfSubject + subject.length(), line.length() );
          if( !"".equals( subjectText ) ) {
            result[ 0 ] = subjectText;
          }
        }
        int indexOfFrom = line.indexOf( from );
        if( indexOfFrom != -1 ) {
          fromFound = true;
          String fromText = line.substring( indexOfFrom + from.length(), line.length() );
          if( !"".equals( fromText ) ) {
            result[ 1 ] = fromText;
          }
        }
      }
    } catch ( IOException e ) {
       // do nothing
    } finally {
      if( bufferedReader != null ) {
        try {
          bufferedReader.close();
        } catch( IOException e ) {
          throw new IllegalStateException( e );
        }
      }
    }
    return result;
  }
  
  private void count( File file ) {
    if( file.isDirectory() ) {
      dirCount++;
    } else {
      fileCount++;
    }
  }

  private static void createIndexFile( File indexFile, List<FileEntry> list ) throws IOException {
    sortFileList( list );
    String string = createString( list );
    writeToFile( indexFile, string );
  }

  private static File getIndexFile( File file ) {
    File indexFile = new File( file, ".index" );
    return indexFile;
  }

  private static void sortFileList( List<FileEntry> list ) {
    Collections.sort( list, new Comparator<FileEntry>() {

      public int compare( FileEntry file1, FileEntry file2 ) {
        int result = 0;
        if( file1.type < file2.type ) {
          result = -1;
        } else if( file1.type > file2.type ) {
          result = 1;
        } else if( file1.name.endsWith( "." ) && file2.name.endsWith( "." ) ) {
          int number1 = Integer.parseInt( file1.name.substring( 0, file1.name.length() - 1 ) );
          int number2 = Integer.parseInt( file2.name.substring( 0, file2.name.length() - 1 ) );
          if( number1 < number2 ) {
            result = -1;
          } else if( number1 > number2 ) {
            result = 1;
          }
        } else {
          result = file1.name.compareTo( file2.name );
        }
        return result;
      }
    } );
  }

  private static String createString( List<FileEntry> list ) {
    StringBuffer buffer = new StringBuffer();
    for( FileEntry file : list ) {
      buffer.append( file.type );
      buffer.append( "\t" );
      buffer.append( file.name );
      buffer.append( "\t" );
      buffer.append( file.subject );
      buffer.append( "\t" );
      buffer.append( file.from );
      buffer.append( "\t" );
      buffer.append( file.count );
      buffer.append( "\n" );
    }
    return buffer.toString();
  }

  private static void writeToFile( File file, String string ) throws IOException {
    BufferedWriter writer = new BufferedWriter( new FileWriter( file ) );
    try {
      writer.write( string );
    } finally {
      writer.close();
    }
  }

  private static int readChildCountFromIndexFile( File indexFile ) throws IOException {
    int count = 0;
    BufferedReader reader = new BufferedReader( new FileReader( indexFile ) );
    try {
      boolean done = false;
      while( !done ) {
        String line = reader.readLine();
        if( line == null ) {
          done = true;
        } else if( line.trim().length() > 0 ) {
          count++;
        }
      }
    } finally {
      reader.close();
    }
    return count;
  }

  private static class FileEntry {
    final String name;
    final int count;
    final char type;
    final String subject;
    final String from;
    
    FileEntry( String name, String subject, String from, char type, int count ) {
      this.name = name;
      this.subject = subject;
      this.from = from;
      this.count = count;
      this.type = type;
    }
  }
}
