RAP mobile Demos
================

This repository contains a single Eclipse project that is used within the [RAP mobile Screencasts](http://rapmobile.eclipsesource.com/demos/). The steps to get this running are described [here](http://rapmobile.eclipsesource.com/developers/getting-started/). After running this project the following Entrypoints are available:

iOS:

* http://SERVER:9090/ios?startup=input
* http://SERVER:9090/ios?startup=buttons
* http://SERVER:9090/ios?startup=simple-tree

Android:

* http://SERVER:9090/android?startup=input
* http://SERVER:9090/android?startup=buttons
* http://SERVER:9090/android?startup=simple-tree

Please replace SERVER with your ip address.

IMPORTANT: Virtual Tree
-----------------------
To run the virtual tree example you need to download the [Enron Email Dataset](http://www.cs.cmu.edu/~enron/) 
to your local machine. After this is done you need to change the constant DEFAULT_DATASET_DIR within the 
com.eclipsesource.rap.mobile.demos.enron.EnronExample class to point to your maildir destination.
After this please point your web browser to http://SERVER:9090/ios?startup=virtual-tree. It may take som eminutes before
the ui will be displayed because the example needs to create some .index files. Don't worry, this happens only once.

License
-------

The code is published under the terms of the [Eclipse Public License, version 1.0](http://www.eclipse.org/legal/epl-v10.html).

Includes code from [jshint](https://github.com/jshint/jshint/), version r04, which is published under the terms of the MIT license with the addition "The Software shall be used for Good, not Evil."