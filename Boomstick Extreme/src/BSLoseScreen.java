/*
 * mLoseScreen.java
 *
 * Created on June 29, 2007, 3:57 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Bryan
 */
public class BSLoseScreen {
    BSGameCanvas mApp;
    long StartTime = 0;
    long lasttime = 0;

    /** Creates a new instance of mLoseScreen */
    public BSLoseScreen(BSGameCanvas theCanvas) {
        mApp = theCanvas;
    }
    
    public void Update()
    {
        if (lasttime != 0)
            StartTime += System.currentTimeMillis() - lasttime;
        if (StartTime > 1500)
        {
            mApp.mApp.RestartGame();
        }
        lasttime = System.currentTimeMillis();
    }
    
    public void Draw()
    {
        mApp.mGraphics.drawBMString("GAME OVER", 110, 20, 0, 255, 0, 0);
    }
}
