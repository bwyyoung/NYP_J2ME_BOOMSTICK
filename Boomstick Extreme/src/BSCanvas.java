/*
 * Canvas.java
 *
 * Created on May 7, 2007, 10:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Bryan
 */

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

public class BSCanvas extends Canvas implements Runnable {
    public boolean bRunning;
    
    public BSMainApp mMainApp;
    /** Back reference to the MIDlet class */
    public MIDlet mApp;
    
    /** FPS to run the game app. */
    public static final int FPS = 8;
    public static final int MSPF = 1000/FPS;
    
    Thread gameLoop;
    
    /** Creates a new instance of Canvas */
    public BSCanvas(MIDlet theApp) {
        mApp = theApp;
        mMainApp = new BSMainApp(this);
        // Sets the game to fullscreen mode.
        setFullScreenMode(true);
        gameLoop = new Thread(this);
        bRunning = true;
        gameLoop.start();
    }

    public void paint(Graphics g)
    {
        g.setColor(0, 0, 0);
        g.fillRect(0, 0, getWidth(), getHeight());
        mMainApp.Render(g);
    }
    
    /** This is the main game loop, created by the thread above. */
    public void run()
    {
        while (bRunning)
        {
            long LastTime = System.currentTimeMillis();
            // Do Update here, then Draw stuff.
            mMainApp.Update();
            mMainApp.Draw();
            repaint();
            long WaitTime = (1000/FPS) - (System.currentTimeMillis() - LastTime);
            
            try
            {
                gameLoop.sleep(WaitTime);
            }
            catch (Exception e)
            {
                // Just ignore it, it's prolly a negative number error.
            }
        }
    }

    protected void keyPressed(int keyCode) 
    {
        mMainApp.keyPressed(keyCode);
    }
    
    protected void keyReleased(int keyCode)
    {
        mMainApp.keyReleased(keyCode);
    }
}
