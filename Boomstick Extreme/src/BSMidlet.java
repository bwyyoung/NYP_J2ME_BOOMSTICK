/*
 * BSMidlet.java
 *
 * Created on May 7, 2007, 10:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * Weeeeeeeeeeeeeeeed
 */

/**
 *
 * @author Bryan
 */

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.*;

public class BSMidlet extends MIDlet {
    public BSCanvas mCanvas;
    public Display mDisplay;
    
    /** Creates a new instance of BSMidlet */
    public BSMidlet() {
    }
    
    public void startApp()
    {
        mCanvas = new BSCanvas(this);
        mDisplay = Display.getDisplay(this);
        mDisplay.setCurrent(mCanvas);
    }
    
    public void pauseApp()
    {
        
    }
    
    public void destroyApp(boolean q)
    {
        
    }
}
