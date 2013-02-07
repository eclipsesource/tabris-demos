
package com.eclipsesource.tabris.demos.entrypoints;

import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.rap.rwt.application.EntryPointFactory;

import com.eclipsesource.tabris.demos.ui.BookStoreConfiguration;
import com.eclipsesource.tabris.ui.TabrisUI;

public class UiDemo implements EntryPointFactory {

  public EntryPoint create() {
    return new TabrisUI( new BookStoreConfiguration() );
  }
}