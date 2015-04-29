/*
 * BSGraphics.java
 *
 * Created on May 9, 2007, 9:59 PM
 */

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author  Israel
 * @version 1.0
 * 
 */

/**
 * Class Name: BSGraphics
 * Version 1.0
 * Implementation Stage: Base
 * Description:
 *    A facade for Graphics class. For platform portability.
 *    Most but not all functions in Graphics Class has been covered under this facade.
 *    Functions from the Object class like 'equals' and 'getClass' are also not covered under this facade.
*/
public class BSGraphics extends Object {
    public BSMainApp mApp;      //Reference to MainApp
    public Graphics g;          //Java graphics class
    public Image mBackBuffer;   //The Back buffer
    
    static final int SCREEN_WIDTH = 176;
    static final int SCREEN_HEIGHT = 220;
    static final int BASELINE = Graphics.BASELINE;
    static final int BOTTOM = Graphics.BOTTOM;
    static final int DOTTED = Graphics.DOTTED;
    static final int HCENTER = Graphics.HCENTER;
    static final int VCENTER = Graphics.VCENTER;
    static final int LEFT = Graphics.LEFT;
    static final int RIGHT = Graphics.RIGHT;
    static final int SOLID = Graphics.SOLID;
    static final int TOP = Graphics.TOP;
    
    public BSGraphics(BSMainApp theApp)
    {
        //Init reference to MainApp
        mApp = theApp;
        //Initialize Backbuffer size
        mBackBuffer = Image.createImage(SCREEN_HEIGHT, SCREEN_WIDTH);
        g = mBackBuffer.getGraphics();
    }
    
    /** Creates a BSImage and initialize it to I */
    //NOMINAL
    public BSImage NewImage(String theName)
    {
        BSImage Temp = new BSImage();
        try {
            Temp.I = Image.createImage(theName);
        }
        catch (Exception e) {
        }
        Temp.mGraphics = this;
        return Temp;
    }
    /** Creates a BSImage and initialize it to I */
    //NOMINAL
    public BSImage NewImage(String theName, int x, int y)
    {
        BSImage Temp = new BSImage();
        try {
            Temp.I = Image.createImage(theName);
        }
        catch (Exception e) {
        }
        Temp.setPosition(x,y);
        Temp.mGraphics = this;
        return Temp;
    }
    
    /** Creates a BSSprite */
    //NOMINAL
    public BSSprite NewSprite(BSImage I, int frameWidth, int frameHeight)
    {
        BSSprite Temp = new BSSprite(this);
        Temp.s = new Sprite(I.I, frameWidth, frameHeight);
        Temp.setPosition(0,0);
        Temp.setRefPixel(0,0);
        return Temp;
    }
    
    //Draws a string using java's font
    //BASE: UNTESTED
    public void drawString(String str, int x, int y, int anchor)
    {
        g.drawString(str, x, y, anchor);
    }
    
    //NOMINAL
    public void Flip(Graphics gr)
    {
        RotateCanvas(gr);
        //gr.drawImage(mBackBuffer, 0, 0, Graphics.TOP | Graphics.LEFT);
        //clear the backbuffer
        g.setColor(0, 0, 0);
        g.fillRect(0, 0, SCREEN_HEIGHT, SCREEN_WIDTH);
    }
    
    //NOMINAL
    /** Rotates Canvas */
    public void RotateCanvas(Graphics gr)
    {
        Sprite Temp = new Sprite(mBackBuffer, SCREEN_HEIGHT, SCREEN_WIDTH);
        Temp.setPosition(0, 0);
        
        if (mApp.is_RightHanded) {
            Temp.setTransform(Sprite.TRANS_ROT90);
            Temp.move(SCREEN_WIDTH - 1, 0);
        }
        else {
            Temp.setTransform(Sprite.TRANS_ROT270);
            Temp.move(0,(SCREEN_HEIGHT - 1));
        }
        
        Temp.paint(gr);
    }
    
    //Draws a string using Bitmap Fonts
    //NOMINAL
    public void drawBMString(String str, int x, int y, int thefont)
    {
        g.setColor(255,0,0);
        g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, 
                   Font.STYLE_BOLD, Font.SIZE_SMALL));
        g.drawString(str, x, y, Graphics.HCENTER|Graphics.BASELINE);
//        graphics.setColor(255,255,255);
//        graphics.fillRect(0, 0, getWidth(), getHeight());
//        graphics.setColor(255,0,0);
//        graphics.setFont(Font.getFont(Font.FACE_PROPORTIONAL, 
//                          Font.STYLE_BOLD, Font.SIZE_SMALL));
//        graphics.drawString("Profound statement.", 50, 10, Graphics.HCENTER|Graphics.BASELINE);
    
    }
    //Draws a string using Bitmap Fonts with specified color
    //NOMINAL
    public void drawBMString(String str, int x, int y, int thefont, int red, int green, int blue)
    {
        g.setColor(red,green,blue);
        g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, 
                   Font.STYLE_BOLD, Font.SIZE_SMALL));
        g.drawString(str, x, y, Graphics.HCENTER|Graphics.BASELINE);
    }
    
    //Draws a string using Bitmap Fonts with specified color and anchor
    //NOMINAL
    public void drawBMString(String str, int x, int y, int thefont, int red, int green, int blue, int anchor)
    {
        g.setColor(red,green,blue);
        g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, 
                   Font.STYLE_BOLD, Font.SIZE_SMALL));
        g.drawString(str, x, y, anchor);
    }
    
    public void drawBMTextBox(String str, int x, int y, int length, int thefont, int red, int green, int blue, int anchor)
    {
        g.setColor(red,green,blue);
        g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, 
                   Font.STYLE_BOLD, Font.SIZE_SMALL));
        int lines = 1 + str.length()/length;
        char Temp[];
        Temp = new char [length];
        String Line;
        
        int begin=0;
        for (int i=0;i<lines;i++) {
            if (i == lines-1) {
                Temp = new char [str.length()-begin];
                str.getChars(begin,str.length(),Temp,0);
            }
            else {
                str.getChars(begin,begin+length-1,Temp,0);
            }
            Line = String.valueOf(Temp);
            g.drawString(Line, x, y+i*14, anchor);
            begin += length-1;
        }
        Temp = null;
        System.gc();
    }
}
    
