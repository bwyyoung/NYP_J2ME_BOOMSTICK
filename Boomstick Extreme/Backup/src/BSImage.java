/*
 * BSImage.java
 *
 * Created on May 11, 2007, 10:08 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import javax.microedition.lcdui.*;

/**
 *
 * @author Israel
 * Class Name: BSImage
 * Version 1.0
 * Implementation Stage: BASE
 * Description: A facade for Image class. For platform portability and coder friendliness.
*/
public class BSImage {
    public BSGraphics mGraphics;// Reference to Graphics
    
    public Image I;             // Instance to Image class
    public int mX;              // X-Position of image
    public int mY;              // Y-Position of image
    public int mAnchor1;        // Anchor 1;
    public int mAnchor2;        // Anchor 2;
    
    
    
    /** Creates a new instance of BSImage */
    public BSImage() {
        mAnchor1 = Graphics.TOP;
        mAnchor2 = Graphics.LEFT;
    }
    
    /** Renders Image at position (mX, mY) with anchors (mAnchor1 | mAnchor2) */
    public void Render()
    {
        mGraphics.mBackBuffer.getGraphics().drawImage(I, mX, mY, mAnchor1 | mAnchor2);
    }
    
    /** Set functions */
    public void setPosition(int x, int y)   
    {
        mX = x; mY = y;
    }
    public void Translate(int x, int y)   
    {
        mX += x; mY += y;
    }
    
    //BASE: SCREWED AND LAGGY
    /* Scales an image. Should not be used as it is FRIGGIN processor intensive.
       Does not support alpha values. MIDP 1.0 compatible. */
    public void ScaleImage(int sX, int sY)
    {
        //Original Width and Height of Image
        int oWidth = I.getWidth();
        int oHeight = I.getHeight();
        //Scaled Width and Height of Image
        int sWidth = oWidth*sX;
        int sHeight = oHeight*sY;
        
        Image sImage = Image.createImage(I);
        I = Image.createImage(sWidth, sHeight);
        Graphics buff = I.getGraphics();
        for (int j=0;j<sHeight;j++) {
            for (int i=0;i<sWidth;i++) {
                buff.setClip(i,j,1,1);
                buff.drawImage(sImage, i*oWidth/sWidth, j*oHeight/sHeight, buff.TOP|buff.LEFT);
            }
        }
    }
    
    //BASE: LAGGY AND DOES NOT WORK
    /* Scales an image. Should not be used as it is FRIGGIN processor intensive.
       Supports alpha values. Compatible only with MIDP 2.0 and above. */
    public void ScaleImageX(int sX, int sY)
    {
        //Original Width and Height of Image
        int oWidth = I.getWidth();
        int oHeight = I.getHeight();
        //Scaled Width and Height of Image
        int sWidth = oWidth*sX;
        int sHeight = oHeight*sY;
        
        int buff[];
        buff = new int [oWidth*oHeight];
        I.getRGB(buff,0,oWidth,0,0,oWidth,oHeight);
        
        int sbuff[];
        sbuff = new int [sWidth*sHeight];
        for (int j=0;j<sHeight;j++) {
            for (int i=0;i<sWidth;i++) {
                int c1 = j*sWidth;
                int c2 = (j*oHeight/sHeight)*oWidth;
                sbuff[i+c1] = buff[i*oWidth/sWidth + c2];
            }
        }
        I.createRGBImage(sbuff,sWidth,sHeight,true);
    }
    
    /* Got this off the net for reference */
    public static Image scale(Image src, int width, int height)
    {
        long start = System.currentTimeMillis();
        int scanline = src.getWidth();
        int srcw = src.getWidth();
        int srch = src.getHeight();
        int buf[] = new int[srcw * srch];
        src.getRGB(buf, 0, scanline, 0, 0, srcw, srch);
        int buf2[] = new int[width*height];
        for (int y=0;y<height;y++)
                {
                int c1 = y*width;
                int c2 = (y*srch/height)*scanline;
                for (int x=0;x<width;x++)
                        {
                        buf2[c1 + x] = buf[c2 + x*srcw/width];
                        }
                }		
        Image img = Image.createRGBImage(buf2, width, height, true);
        long end = System.currentTimeMillis();
        //System.out.println("Scaled "+src.getWidth()+"x"+src.getHeight()+" in "+((end-start)/1000)+" seconds");
        return img;
    }

    
    /* Rotates An Image. Takes in the type of rotation. Rotation values are Specified in the Sprite class. */
    public void RotateImage(int transform)
    {
        Image Temp = Image.createImage(I,0,0,I.getWidth(),I.getHeight(),transform);
        I = Temp;
    }
    
    public int GetWidth()
    {
        return I.getWidth();
    }
    
    public int GetHeight()
    {
        return I.getHeight();
    }
    
}
