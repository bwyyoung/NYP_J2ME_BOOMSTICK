/*
 * BSGameCanvas.java
 *
 * Created on May 12, 2007, 11:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Bryan
 */
public class BSGameCanvas extends BSState {
    public BS_MenuManager mMM;  //Instance to menu manager
    
    BSMaploader mMaploader;
    BSHoleManager mHoleManager;
    BSPlayerModel mPlayerModel;
    BSResourceHolder mResources;
    /** Creates a new instance of BSGameCanvas */
    //BASE
    public BSGameCanvas(BS_MenuManager theManager)
    {
        mMM = theManager;
        mMM.mSM.NewState(this);   
        mResources = new BSResourceHolder();
    }
    
    //NULL
    public void Init()
    {
        mMaploader = new BSMaploader(mApp);
        mMaploader.LoadMap("/test.txt");
        mHoleManager = new BSHoleManager(this);
        mHoleManager.SetMap(mMaploader.GetMap(0));
        
        mPlayerModel = new BSPlayerModel(mApp);
    }
    
    //NULL
    public void Update()
    {
        mHoleManager.Update();
        mPlayerModel.Update();
    }
    //NULL
    public void Draw()
    {
       
        mHoleManager.Render();
        mPlayerModel.PlayerRender();
      
    }   
    
    //NULL
    /* Gets the key pressed and passes into the parameter */
    public void GetKeyPressed(int KeyPressed)
    {
        //whee lets test monsters!
        mPlayerModel.KeyDetect(KeyPressed);
        if (KeyPressed == 51)
            mHoleManager.HoleIsShot(0, 1);
        if (KeyPressed == 54)
            mHoleManager.HoleIsShot(1, 1);
        if (KeyPressed == 57)
            mHoleManager.HoleIsShot(2, 1);
        if (KeyPressed == 50)
            mHoleManager.HoleIsShot(3, 1);
        if (KeyPressed == 53)
            mHoleManager.HoleIsShot(4, 1);
        if (KeyPressed == 56)
            mHoleManager.HoleIsShot(5, 1);
        if (KeyPressed == 49)
            mHoleManager.HoleIsShot(6, 1);
        if (KeyPressed == 52)
            mHoleManager.HoleIsShot(7, 1);
        if (KeyPressed == 55)
            mHoleManager.HoleIsShot(8, 1);
        //mHoleManager.HoleIsShot(KeyPressed - 49, 1);
    }
    
    public void EnterState()
    {
        mIsEnteringState = true;
    }
    
    public void ExitState()
    {
        mIsExitingState = true;
    }
}
