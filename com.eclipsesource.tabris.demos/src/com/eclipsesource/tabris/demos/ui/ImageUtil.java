
package com.eclipsesource.tabris.demos.ui;

import org.eclipse.swt.graphics.Image;

public class ImageUtil {

  static Image resizeImageToHeight( Image image, int height ) {
    float ratio = ( float )image.getBounds().height / ( float )image.getBounds().width;
    int width = Math.round( height / ratio );
    return new Image( image.getDevice(), image.getImageData().scaledTo( width, height ) );
  }
}
