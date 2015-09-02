Tabris Demos
============

This repository contains a single Eclipse project with sources for all demos shown in the [Tabris Screencasts](http://developer.eclipsesource.com/tabris/demos/). The steps to get this running are described [here](http://developer.eclipsesource.com/tabris/docs/1.4/#getting-started). After running this project the following Entrypoints are available:

* http://SERVER:9090/input
* http://SERVER:9090/buttons
* http://SERVER:9090/simple-tree 
* http://SERVER:9090/location
* http://SERVER:9090/draw
* http://SERVER:9090/launcher
* http://SERVER:9090/camera
* http://SERVER:9090/gallery
* http://SERVER:9090/video
* http://SERVER:9090/keyboard 
* http://SERVER:9090/virtual-tree (Read the note below!) 
* http://SERVER:9090/swipe 
* http://SERVER:9090/appevents
* http://SERVER:9090/ui
* http://SERVER:9090/dynamic-ui
* http://SERVER:9090/device 

To list all available Entrypoints: http://SERVER:9090/index.json

Please replace SERVER with your ip address.

IMPORTANT: Virtual Tree
-----------------------
To run the virtual tree example you need to download the [Enron Email Dataset](http://www.cs.cmu.edu/~enron/) 
to your local machine. After this is done you need to change the constant DEFAULT_DATASET_DIR within the 
com.eclipsesource.tabris.demos.enron.EnronExample class to point to your maildir destination.
After this please point your web browser to http://SERVER:9090/virtual-tree. It may take som eminutes before
the ui will be displayed because the example needs to create some .index files. Don't worry, this happens only once.  

Build
-----
There are some build projects located in the build directory. The build compiles the demo project into a war file that can be deployed
on a servlet container. The build uses [Maven Tycho](http://eclipse.org/tycho/) and can be launched when Maven 3 is installed. Simply step to the build/com.eclipsesource.tabris.demos.build folder and run "mvn clean verify". After the build has succeeded you will find the .war file within the build/com.eclipsesource.tabris.demos.product/target folder. 

Alternatively you can use the [Eclipse m2e Tooling](http://eclipse.org/m2e/) and run the launch configuration located in build/com.eclipsesource.tabris.demos.build.

License
-------
The code is published under the terms of the [Eclipse Public License, version 1.0](http://www.eclipse.org/legal/epl-v10.html).
