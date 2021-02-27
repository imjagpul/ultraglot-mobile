/*
 *  font4mobile version 0.25
 *
 *  (c) 2007 www.gsmdev.com
 *
 *  visit www.gsmdev.com for updates
 *
 *  For MIDP 1.0 & 2.0 compatible devices
 *  without transparency support
 *
 *  author: Adam Babol (adam.babol@gmail.com)
 *
 */

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class FontUtilWrapping {

    private int imgHeight;
    private char[] chars;
    private byte[] charsWidth;
    private Image[] charImage;
    private String lastStr = "";
    private Image lastImg;
    private int lastWrapWidth;

    /**
     * Crate instance of class
     */
    private FontUtilWrapping() {
    }

    
    /**
     * Initialize data, load information about characters from file and closes
     * the stream.
     * @throws              IOException
     *                      when an IO error occurs
     */
    public static FontUtilWrapping initialize(InputStream is) throws IOException {
        
        FontUtilWrapping instance = new FontUtilWrapping();
        Image img = null;

//        if(is==null) {
//            System.err.println("Font not found. "+fileName);
//        }

        DataInputStream dis = new DataInputStream(is);
        int len = dis.readInt();
        instance.chars = new char[len];
        instance.charImage = new Image[len];
        instance.charsWidth = new byte[len];
        for (int i = 0; i < len; i++) {
            instance.chars[i] = dis.readChar();
            instance.charsWidth[i] = dis.readByte();
        }
        int imgSize = dis.readInt();
        byte[] tmpBuffer = new byte[imgSize];
        dis.read(tmpBuffer, 0, imgSize);

        img = Image.createImage(tmpBuffer, 0, imgSize);
        if (img != null) {
            instance.imgHeight = img.getHeight();
            int x = 0;
            for (int i = 0; i < len; i++) {
                instance.charImage[i] = Image.createImage(instance.charsWidth[i], instance.imgHeight);
                Graphics g = instance.charImage[i].getGraphics();
                g.setClip(0, 0, instance.charsWidth[i], instance.imgHeight);
                g.drawImage(img, -x, 0, Graphics.TOP | Graphics.LEFT);
                x += instance.charsWidth[i];
                g = null;
            }
        }
        tmpBuffer = null;
        img = null;

        is.close();
        return instance;
    }
    private static final String WORD_BOUNDARY = " \r\n\t ,.:;!";
    
    //cache wrapping
    private Vector oldWraps;
    private String oldWrapText;
    private int oldWrapWidth;

    private Vector findWraps(String text, int wrapWidth) {
        if (text.equals(oldWrapText) && wrapWidth == oldWrapWidth) {
            return oldWraps;
        }

        Vector wraps = new Vector(); //index of first characters on line (except first line)

        //int startIndex=0;
        //int[] indxs = new int[text.length()];

        int i = 0;
        int lastWrapPoint = -1;

        //while(i < text.length()) {
        int width = 0;
        for (; i < text.length(); i++) {
            int val = charIndex(text.charAt(i));
            //indxs[i] = val;

            if (text.charAt(i) == '\n') {
                wraps.addElement(new Integer(i));
                width = 0;
                lastWrapPoint = -1;
            }

            if (val == -1) {
                continue;
            }

            if (WORD_BOUNDARY.indexOf(text.charAt(i)) != -1) {
                lastWrapPoint = i;
            }

            if (width + charsWidth[val] < wrapWidth) {
                width += charsWidth[val];
            } else {
                if (lastWrapPoint == -1) {
                    wraps.addElement(new Integer(i));
                    width = charsWidth[val];
                } else {
                    int beforeLastWrapPoint = 0;

                    if (wraps.size() > 0) {
                        beforeLastWrapPoint = ((Integer) wraps.elementAt(wraps.size() - 1)).intValue();
                    }

                    width -= stringWidth(text.substring(beforeLastWrapPoint, lastWrapPoint));

                    wraps.addElement(new Integer(lastWrapPoint));
                    lastWrapPoint = -1;
                }
            } //else ignore unknown character
        }

        oldWrapText = text;
        oldWrapWidth = width;
        oldWraps = wraps;

        return wraps;
    }

    /**
     * Render given text as image using font
     *
     * @param text text to render
     * @return rendered text as image
     */
    public Image renderTextWrapping(String text, int wrapWidth) {
        if (text.equals(lastStr) && lastWrapWidth == wrapWidth) //Eliminate possible creation of already created image
        {
            return lastImg;
        }

        Image result;

        Vector wraps = findWraps(text, wrapWidth);
        if (wraps.size() == 0) {
            int width = stringWidth(text);
            if (width == 0) {
                //empty string or string of unknown characters only
                return null;
            } else {
                result = Image.createImage(width, imgHeight);
            }
        } else {
            result = Image.createImage(wrapWidth, imgHeight * (wraps.size() + 1));
        }

        Graphics g = result.getGraphics();

        int y = 0;
        int i = 0;
        for (int j = 0; j < wraps.size() + 1; j++) {
            int nextWrap = j == wraps.size() ? text.length() : ((Integer) wraps.elementAt(j)).intValue();
            int x = 0;

            for (; i < nextWrap; i++) {
                int val = charIndex(text.charAt(i));
                if (val != -1) {
//                    g.setClip(x, y, charsWidth[val], result.getHeight());
                    g.drawImage(charImage[val], x, y, 20);
//                    g.setClip(0, y, result.getWidth(), result.getHeight());
                    x += charsWidth[val];
                }
            }

            y += imgHeight;
        }

        lastStr = text;
        lastWrapWidth = wrapWidth;
        lastImg = Image.createImage(result);
        return lastImg;
    }

    public Image renderColoredTextWrapping(String text, int wrapWidth, int srcColor, int dstColor, int dst2) {
        if (!text.equals(lastStr) || lastWrapWidth != wrapWidth) //Eliminate possible creation of already created image
        {
            lastImg = calcBlend(renderTextWrapping(text, wrapWidth), srcColor, dst2, dstColor);
        }
        return lastImg;
    }

    /**
     * Returns position of char in CHAR_TAB array
     *
     * @param ch character to find
     * @return position of char in characters array
     */
    private int charIndex(char ch) {
        int i = 0;
        boolean end = false;
        while (!end && i < chars.length) {
            if (chars[i] == ch) {
                end = true;
            } else {
                i++;
            }
        }
        if (!end) {
            i = -1;
            i = 0; //use the first char insted of unknowns
            System.err.println("Unknown character '" + ch + "' (\\u" + Integer.toHexString((int)ch) +")");
        }
        return i;
    }

    /**
     * Gets font height
     *
     * @return font height in pixels
     */
    public int getFontHeight() {
        return imgHeight;
    }

    /**
     * Gets the width of specified character
     *
     * @param ch - the character to be measured
     * @return width of the given character
     */
    public int charWidth(char ch) {
        int index = charIndex(ch);
        if (index != -1) {
            return charsWidth[index];
        } else {
            return 0;
        }
    }

    /**
     * Gets the total width for the specified string
     *
     * @param str the string to be measured
     * @return the total width
     */
    public int stringWidth(String str) {
        int sum = 0;
        for (int i = 0; i < str.length(); i++) {
            sum += charWidth(str.charAt(i));
        }
        return sum;
    }

    /**
     * Gets the width of last line of the specified text after wrap.
     *
     * @param str the string to be measured
     * @return the total width
     */
    public int stringWidthWrapped(String text, int wrapWidth) {
        Vector wraps = findWraps(text, wrapWidth);
        if(wraps.size()>0) {
            int lastWrap=((Integer)wraps.elementAt(wraps.size()-1)).intValue();
            return stringWidth(text.substring(lastWrap));
        } else {
            return stringWidth(text);
        }
    }

    public int stringLineCountWrapped(String text, int wrapWidth) {
        Vector wraps = findWraps(text, wrapWidth);
        return wraps.size()+1;
    }
    
    /**
     * Make selected color in the image transparent
     * Note: the font should be aliased or else borders with the old color may remain
     *
     * @param tmpImg Image object to process
     * @return processed Image object
     */
    private Image calcBlend(Image tmpImg, int srcColor, int dstColor, int dst2) {
        int tmpWidth = tmpImg.getWidth();
        int tmpHeight = tmpImg.getHeight();
        int[] raw = new int[tmpWidth * tmpHeight];
        tmpImg.getRGB(raw, 0, tmpWidth, 0, 0, tmpWidth, tmpHeight);
        for (int i = 0; i < raw.length; i++) {

            int color = (raw[i] & 0x00FFFFFF); // get the color of the pixel.

            if (color == srcColor) {
                color = dstColor;
            } else {
                color = dst2;
            }

            raw[i] = color;
        }
        return Image.createRGBImage(raw, tmpWidth, tmpHeight, false);
    }
}






