import org.junit.Test;
import static org.junit.Assert.*;


/** 
 *  Use this file to test your implementation of Pixel.
 * 
 *  We will manually grade this file to give you feedback
 *  about the completeness of your test cases.
 */

public class MyPixelTest {

    /*
     * Remember, UNIT tests should ideally have one point of failure. Below we
     * give you an example of a unit test for the Pixel constructor that takes
     * in three ints as arguments. We use the getRed(), getGreen(), and
     * getBlue() methods to check that the values were set correctly. This one
     * test does not comprehensively test all of Pixel so you must add your own.
     *
     * You might want to look into assertEquals, assertTrue, assertFalse, and
     * assertArrayEquals at the following:
     * http://junit.sourceforge.net/javadoc/org/junit/Assert.html
     *
     * Note, if you want to add global variables so that you can reuse Pixels
     * in multiple tests, feel free to do so.
     */

    @Test
    public void testConstructInBounds() {
        Pixel p = new Pixel(40, 50, 60);
        assertEquals(40, p.getRed());
        assertEquals(50, p.getGreen());
        assertEquals(60, p.getBlue());
    }

    /* ADD YOUR OWN TESTS BELOW */
  @Test
  public void testNegativeClipping() {
    Pixel p = new Pixel(-20, 0, 5);
    assertEquals(0, p.getRed());
    assertEquals(0, p.getGreen());
    assertEquals(5, p.getBlue());
  }
  
  @Test
  public void testPositiveClipping() {
    Pixel p = new Pixel(255, 300, 10);
    assertEquals(255, p.getRed());
    assertEquals(255, p.getGreen());
    assertEquals(10, p.getBlue());
  }
  
  @Test
  public void testShortComponents() {
    int[] array = {1, 2};
    Pixel p = new Pixel(array);
    assertEquals(1, p.getRed());
    assertEquals(2, p.getGreen());
    assertEquals(0, p.getBlue());
  }
  
  @Test
  public void testLongComponents() {
    int[] array = {1, 2, 3, 4, 5, 6, 6, 7};
    int[] endArray = {1, 2, 3};
    Pixel p = new Pixel(array);
    assertArrayEquals(endArray, (p.getComponents()));
  }
  
  @Test
  public void testDistanceNotSamePixels() {
    int[] array1 = {1, 2, 3};
    int[] array2 = {4, 5, 6};
    Pixel p = new Pixel(array1);
    Pixel px = new Pixel(array2);
    assertEquals(9, p.distance(px));
  }
  
  @Test
  public void testDistanceSamePixels() {
    int[] array1 = {1, 2, 3};
    int[] array2 = {1, 2, 3};
    Pixel p = new Pixel(array1);
    Pixel px = new Pixel(array2);
    assertEquals(0, p.distance(px));
  }
  
  @Test
  public void testToString() {
    int[] array = {1, 2, 3};
    Pixel p = new Pixel(array);
    assertEquals("(1, 2, 3)", p.toString());
  }
  
  @Test
  public void testEqualsSamePixel() {
    int[] array1 = {1, 2, 3};
    int[] array2 = {1, 2, 3};
    Pixel p = new Pixel(array1);
    Pixel px = new Pixel(array2);
    assertArrayEquals(p.getComponents(), px.getComponents());
  }
  
  @Test
  public void testEqualsDifferentPixel() {
    int[] array1 = {1, 2, 3};
    int[] array2 = {4, 5, 6};
    Pixel p = new Pixel(array1);
    Pixel px = new Pixel(array2);
    assertEquals(false, p.equals(px));
  }
  
}
