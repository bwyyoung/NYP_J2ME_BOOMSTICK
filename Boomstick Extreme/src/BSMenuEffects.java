/*
 * BSMenuEffects.java
 *
 * Created on June 24, 2007, 7:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Isaac
 */
public class BSMenuEffects {
    BSMainApp mApp;
    boolean mCompleted; //True if effect is complete. False if otherwise.
    int mCurEffect;     //0 = none, 1 = FadeIn, 2 = FadeOut
    
    //Fading variables
    int mFadeSpeed;     //Pixels per frame
    BSImage IFade;      //Fade Image
    
    /** Creates a new instance of BSMenuEffects */
    public BSMenuEffects(BSMainApp theApp) {
        mApp = theApp;
        mCompleted = false;     //True if an effect has been executed and has completed
        mCurEffect = 0;
        mFadeSpeed = 500/mApp.mCanvas.FPS;
    }
    
    public void Init() {
        IFade = mApp.mMenuManager.mResources.GetImageThrow("BlackFade");
    }
    
    public void Update() {
        
        switch (mCurEffect) {
            case 0: return;
            case 1:
                if (mCompleted) return;
                IFade.Translate(mFadeSpeed,0);
                if (IFade.mX >= BSGraphics.SCREEN_HEIGHT) {
                    mCompleted = true;
                    IFade.setPosition(BSGraphics.SCREEN_HEIGHT,0);
                    IFade.RotateImage(BSSprite.TRANS_ROT180);
                }
                break;
            case 2:
                if (mCompleted) return;
                IFade.Translate(mFadeSpeed,0);
                if (IFade.mX >= 0) {
                    IFade.setPosition(0,0);
                    mCompleted = true;
                }
                break;
        }
    }
    public void Render() {
        switch (mCurEffect) {
            case 0: return;
            case 1:
                if (!mCompleted)
                    IFade.Render();
            case 2:
                IFade.Render();
        }
    }
    public void FadeIn() {
        IFade.setPosition(BSGraphics.SCREEN_HEIGHT-500,0);
        IFade.RotateImage(BSSprite.TRANS_ROT180);
        mCurEffect = 1;
        mCompleted = false;
    }
    public void FadeOut() {
        mCurEffect = 2;
        IFade.setPosition(-500,0);
        mCompleted = false;
    }
    public void Destroy() {
        IFade = null;
    }
}
