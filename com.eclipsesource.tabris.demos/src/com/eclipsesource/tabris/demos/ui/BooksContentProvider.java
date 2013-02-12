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
package com.eclipsesource.tabris.demos.ui;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class BooksContentProvider implements ITreeContentProvider {

  private List<Book> books;

  public void dispose() {
  }

  @SuppressWarnings("unchecked")
  public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {
    books = ( List<Book> )newInput;
  }

  public Object[] getElements( Object inputElement ) {
    return books.toArray();
  }

  public Object[] getChildren( Object parentElement ) {
    return null;
  }

  public Object getParent( Object element ) {
    return null;
  }

  public boolean hasChildren( Object element ) {
    return false;
  }

}