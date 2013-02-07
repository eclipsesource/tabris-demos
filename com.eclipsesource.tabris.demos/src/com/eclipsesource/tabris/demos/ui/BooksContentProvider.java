
package com.eclipsesource.tabris.demos.ui;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class BooksContentProvider implements ITreeContentProvider {

  private static final long serialVersionUID = 442622814824116939L;

  private List<Book> books;

  public void dispose() {
  }

  @SuppressWarnings("unchecked")
  public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {
    this.books = ( List<Book> )newInput;
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