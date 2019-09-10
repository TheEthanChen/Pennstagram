/*
 * The purpose of this assignment is to (re-) acquaint you with Java.
 *
 * You should fill in the body of the functions of this class. We have provided
 * a framework which will use these functions to manipulate images. As always,
 * you can add helper functions if you want. However, now that we're working in
 * Java, it is a good idea to make helper functions "private" (sort of like not
 * including functions in the MLI file in OCaml).
 *
 * You will need to look at the provided PixelPicture.java and Pixel.java files
 * to see if there are any helpful functions in them. However, you should not
 * modify these files, and you don't need to understand the code in each
 * function - you just need to understand how to use any functions you need.
 * Similarly, you will need to use two provided classes, ColorMap and IntQueue,
 * in order to complete the reducePalette() and flood() functions, respectively.
 * Instructions for using those classes are included with the instructions for
 * each problem.
 *
 * In each problem, don't modify the original picture. You should create a copy
 * of the picture passed to each function, modify it, and return it.
 *
 * Hint: think of a picture as a 2-dimensional array of Pixels. This
 * representation of images is called a Bitmap.
 */
public class SimpleManipulations {

  /**
   * Rotate a picture 90 degrees clockwise.
   *
   * <p>For example, consider this bitmap, where each pixel is labeled by its coordinates:
   *
   * <p>(0, 0) (1, 0) (2, 0) (0, 1) (1, 1) (2, 1)
   *
   * <p>Rotating this bitmap CW will produce the following bitmap, with relabeled coordinates:
   *
   * <p>(0, 1) (0, 0) (1, 1) (1, 0) (2, 1) (2, 0)
   *
   * <p>This method implements this "relabeling," copying pixels from their old coordinates to their
   * new coordinates.
   *
   * @param pic The original picture to rotate.
   * @return The rotated picture.
   */
  public static PixelPicture rotateCW(PixelPicture pic) {
    int w = pic.getWidth();
    int h = pic.getHeight();

    Pixel[][] src = pic.getBitmap();
    Pixel[][] tgt = new Pixel[h][w]; // swap coordinates

    for (int x = 0; x < w; x++) {
      for (int y = 0; y < h; y++) {
        tgt[h - y - 1][x] = src[x][y]; // swap coordinates
      }
    }

    return new PixelPicture(tgt);
  }

  /**
   * Rotate a picture 90 degrees counter-clockwise.
   *
   * <p>For example, consider this bitmap, where each pixel is labeled by its coordinates:
   *
   * <p>(0, 0) (1, 0) (2, 0) (0, 1) (1, 1) (2, 1)
   *
   * <p>Rotating this bitmap CCW will produce the following bitmap, with relabeled coordinates:
   *
   * <p>(2, 0) (2, 1) (1, 0) (1, 1) (0, 0) (0, 1)
   *
   * <p>Your job is to implement this "relabeling," copying pixels from their old coordinates to
   * their new coordinates.
   *
   * @param pic The original picture to rotate.
   * @return The rotated picture.
   */
  public static PixelPicture rotateCCW(PixelPicture pic) {
    int w = pic.getWidth();
    int h = pic.getHeight();

    Pixel[][] src = pic.getBitmap();
    Pixel[][] tgt = new Pixel[h][w]; // swap coordinates

    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        tgt[y][w - x - 1] = src[x][y]; // swap coordinates
      }
    }

    return new PixelPicture(tgt);
  }

  /**
   * Create a new image by adding a border to a specified image.
   *
   * @param pic the original picture
   * @param borderWidth number of pixels in the border
   * @param borderColor color of the border.
   * @return a copy of the input picture with a border
   */
  public static PixelPicture border(PixelPicture pic, int borderWidth, Pixel borderColor) {
    int w = pic.getWidth();
    int h = pic.getHeight();

    Pixel[][] src = pic.getBitmap();
    Pixel[][] tgt = new Pixel[w + 2 * borderWidth][h + 2 * borderWidth];

    // set image within border
    for (int x = 0; x < w; x++) {
      for (int y = 0; y < h; y++) {
        tgt[x + borderWidth][y + borderWidth] = src[x][y];
      }
    }

    // draw left border
    for (int x = 0; x < borderWidth; x++) {
      for (int y = 0; y < h + 2 * borderWidth; y++) {
        tgt[x][y] = borderColor;
      }
    }

    // draw right border
    for (int x = 0; x < borderWidth; x++) {
      for (int y = 0; y < h + 2 * borderWidth; y++) {
        tgt[w + borderWidth + x][y] = borderColor;
      }
    }

    // draw top border
    for (int y = 0; y < borderWidth; y++) {
      for (int x = 0; x < 2 * borderWidth + w; x++) {
        tgt[x][y] = borderColor;
      }
    }

    // draw bottom border
    for (int y = 0; y < borderWidth; y++) {
      for (int x = 0; x < 2 * borderWidth + w; x++) {
        tgt[x][borderWidth + h + y] = borderColor;
      }
    }
    return new PixelPicture(tgt);
  }

  /**
   * Transforms a picture to its GrayScale equivalent using the luminosity algorithm.
   *
   * <p>Luminosity is a weighted average that adjusts the GrayScale value based on human perception.
   * We are more sensitive to green, so it is weighted more heavily.
   *
   * <p>Different image manipulation programs use different values. We will use the one Photoshop
   * uses: round(0.299*R + 0.587*G + 0.114*B).
   *
   * <p>NOTE: round is a static method in the Math class from the Java standard library:
   * https://docs.oracle.com/javase/8/docs/api/java/lang/Math.html#round-double-
   *
   * <p>Use Math.round at the very end of your calculation before casting to an int.
   *
   * <p>After computing the weighted average, create a new pixel with this average as its component
   * values. For example, the reddish color (180, 100, 80) becomes (122, 122, 122).
   */
  public static PixelPicture grayScaleLuminosity(PixelPicture pic) {
    int w = pic.getWidth();
    int h = pic.getHeight();

    Pixel[][] bmp = pic.getBitmap();
    for (int x = 0; x < w; x++) {
      for (int y = 0; y < h; y++) {
        Pixel p = bmp[x][y];
        int r = p.getRed();
        int g = p.getGreen();
        int b = p.getBlue();
        int avg = (int) Math.round(0.299 * r + 0.587 * g + 0.114 * b);
        bmp[x][y] = new Pixel(avg, avg, avg);
      }
    }
    return new PixelPicture(bmp);
  }

  /**
   * Create a new image by inverting the color of each pixel.
   *
   * <p>To do this, simply create a new PixelPicture where each color component of each pixel is the
   * inverse of the original value. To invert a color component, subtract it from 255.
   *
   * @param pic the picture to be inverted
   */
  public static PixelPicture invertColors(PixelPicture pic) {
    int w = pic.getWidth();
    int h = pic.getHeight();

    Pixel[][] bmp = pic.getBitmap();
    for (int x = 0; x < w; x++) {
      for (int y = 0; y < h; y++) {
        Pixel p = bmp[x][y];
        int r = p.getRed();
        int g = p.getGreen();
        int b = p.getBlue();
        bmp[x][y] = new Pixel(255 - r, 255 - g, 255 - b);
      }
    }
    return new PixelPicture(bmp);
  }

  /**
   * Transform a colored picture to its grayscale equivalent using an averaging algorithm.
   *
   * <p>To do this, simply find the average of the color components of each pixel in question, and
   * create a new pixel with this average as its component values. For example, the reddish color
   * (180, 100, 80) becomes (120, 120, 120).
   *
   * <p>That is, the formula is (R + G + B) / 3.0
   *
   * <p>Note: / in the formula above is a double operation.
   *
   * <p>Use Math.round at the very end of your calculation before casting to an int.
   *
   * @param pic the original picture
   */
  public static PixelPicture grayScaleAverage(PixelPicture pic) {
    int w = pic.getWidth();
    int h = pic.getHeight();

    Pixel[][] bmp = pic.getBitmap();
    for (int x = 0; x < w; x++) {
      for (int y = 0; y < h; y++) {
        Pixel p = bmp[x][y];
        int r = p.getRed();
        int g = p.getGreen();
        int b = p.getBlue();
        int avg = (int) Math.round((r + g + b) / 3.0);
        bmp[x][y] = new Pixel(avg, avg, avg);
      }
    }
    return new PixelPicture(bmp);
  }

  /**
   * Scale the color components of a picture.
   *
   * <p>To do this, simply replace each pixel with a new one where each color component is the
   * original value multiplied by the scaling factor for that color. Note that each component of the
   * resulting pixel must have values in the range 0 <= color <= 255.
   *
   * <p>Use Math.round before converting double values to ints.
   *
   * @param rfactor red factor
   * @param gfactor green factor
   * @param bfactor blue factor
   */
  public static PixelPicture scaleColors(
      PixelPicture pic, double rfactor, double gfactor, double bfactor) {
    int w = pic.getWidth();
    int h = pic.getHeight();

    Pixel[][] bmp = pic.getBitmap();
    for (int x = 0; x < w; x++) {
      for (int y = 0; y < h; y++) {
        Pixel p = bmp[x][y];
        int r = (int) Math.round(p.getRed() * rfactor);
        int g = (int) Math.round(p.getGreen() * gfactor);
        int b = (int) Math.round(p.getBlue() * bfactor);
        bmp[x][y] = new Pixel(r, g, b);
      }
    }
    return new PixelPicture(bmp);
  }

  /**
   * Compute the weighted average of two integers.
   *
   * <p>If alpha is 0.5, the weightedAverage is the same as the average of the two numbers. If alpha
   * is 1.0, then x is returned. Likewise, if it is 0.0, than the result is y.
   */
  public static int weightedAverage(double alpha, int x, int y) {
    return (int) Math.round(x * alpha + y * (1 - alpha));
  }

  /**
   * Blend two pictures together by taking a weighted average of each pixel.
   *
   * <p>The weighted average of two int values is given by the static method above. This
   * transformation applies this formula to each color in the corresponding pixels of the two
   * images.
   *
   * <p>The two images must be exactly the same size for this transformation to apply. If the
   * dimensions of the second image do not match those of the first, then the first image is
   * returned unchanged.
   *
   * @param alpha
   * @param pic
   * @param f
   * @return the blended image
   */
  public static PixelPicture alphaBlend(double alpha, PixelPicture pic, PixelPicture f) {
    int wpic = pic.getWidth();
    int hpic = pic.getHeight();
    int wf = f.getWidth();
    int hf = f.getHeight();
    
    //only run if pictures of same dimensions
    if (wpic == wf && hpic == hf) {
      Pixel[][] picbmp = pic.getBitmap();
      Pixel[][] fbmp = f.getBitmap();
    for (int x = 0; x < wpic; x++) {
      for (int y = 0; y < hpic; y++) {
        Pixel picp = picbmp[x][y];
        Pixel fp = fbmp[x][y];
        int r = weightedAverage(alpha, picp.getRed(), fp.getRed());
        int g = weightedAverage(alpha, picp.getGreen(), fp.getGreen());
        int b = weightedAverage(alpha, picp.getBlue(), fp.getBlue());
        picbmp[x][y] = new Pixel(r, g, b);
      }
    }
    return new PixelPicture(picbmp);
    }
    else {
      return pic;
    }
  }

  /*
   * We've done VIGNETTE for you. Nothing to do here. Enjoy!
   */

  /**
   * Adds dark edges to an image to draw interest to the center.
   *
   * <p>http://en.wikipedia.org/wiki/Vignetting
   *
   * <p>We'll do this by calculating the distance from each pixel to the center of the image, and
   * then multiplying the color values of that pixel by one minus the square of that distance. In
   * other words,
   *
   * <p>1. Find the x,y coordinates of the center pixels of the image, cx and cy. For example, if
   * the image has width 5 and height 3, then the center pixel is located at (2,1). We'll use
   * doubles to represent this value, in order to account for cases where the picture has an even
   * number of pixels. For example, if the width is 4 and the height 6, then the center is at (1.5,
   * 2.5).
   *
   * <p>2. For each pixel in the image, find its (proportional) distance d from the center. This is
   * the distance from the center in the x and y directions, divided by the total distance from the
   * center to any corner.
   *
   * <p>d = Math.sqrt (dx * dx + dy * dy) / Math.sqrt (cx * cx + cy * cy)
   *
   * <p>3. Compute the multiplicative factor f from the distance. This factor should be 1.0 at the
   * center of the picture (i.e. d=0) and then decrease to 0.0 near the edges.
   *
   * <p>f = 1.0 - (d * d)
   *
   * <p>4. Multiply each component of the pixel by the factor f. Use Math.round to convert the
   * result to an integer value before casting to an int.
   *
   * <p>5. There is one special case. If the distance from the center to any corner is zero (i.e. if
   * the picture contains a single pixel) this method should just return the original input.
   */
  public static PixelPicture vignette(PixelPicture pic) {
    int w = pic.getWidth();
    int h = pic.getHeight();
    double cx = (w - 1) / 2.0;
    double cy = (h - 1) / 2.0; // cx, cy is center pixel in the image

    Pixel[][] bmp = pic.getBitmap();
    for (int x = 0; x < w; x++) {
      for (int y = 0; y < h; y++) {
        double dx = (double) (x - cx);
        double dy = (double) (y - cy);

        double r = Math.sqrt(cx * cx + cy * cy);
        // check for division by zero
        if (r == 0) {
          return pic;
        }

        double d = Math.sqrt((dx * dx) + (dy * dy)) / r;
        double factor = 1.0 - d * d;

        bmp[x][y] =
            new Pixel(
                (int) Math.round(bmp[x][y].getRed() * factor),
                (int) Math.round(bmp[x][y].getGreen() * factor),
                (int) Math.round(bmp[x][y].getBlue() * factor));
      }
    }
    return new PixelPicture(bmp);
  }
}
