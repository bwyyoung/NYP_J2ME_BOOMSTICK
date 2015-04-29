/*
 * BS_MenuManager.java
 *
 * Created on May 15, 2007, 10:26 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Israel
 */
public class BS_MenuManager {
    public BSStateManager mSM;  //Instance of state manager
    private int mNumMenus;      //Total number of menus
    public BSState mStates[];  //Array of menus
    
    //States enum
    public static final int MAIN = 0;
    public static final int START = 1;
    public static final int SETTINGS = 2;
    public static final int ABOUT = 3;
    //public static final int LOAD = 4;
    //public static final int IN_GAME = 5;
    
    //Button values after rotation
    public int B_UP, B_DOWN, B_LEFT, B_RIGHT, B_CTR;
    //These buttons are the shooting buttons with S1 as the top-left button and S9 as the bottom-right button.
    public int B_S1, B_S2, B_S3, B_S4, B_S5, B_S6, B_S7, B_S8, B_S9; 
    public int B_TOP, B_BOT;            //The command buttons. B_TOP would be the command button on top. B_BOT would be the one below.
    //public int B_AST, B_0, B_HASH;    //Not used at the moment
    
    BSResourceHolder mResources;
    /** Creates a new instance of BS_MenuManager */
    public BS_MenuManager(BSStateManager theManager) {
        mSM = theManager;
        
        //Initialize array of menus
        mNumMenus = 4;
        mStates = new BSState [mNumMenus];
        
        //mStates[LOAD] = new BSLoadScreen(this);
        
        //Initialize Buttons
        B_CTR = -5;
        SetButtonMode(mSM.mApp.is_RightHanded);
        
        //Resource loader
        mResources = new BSResourceHolder();
    }
    
    //NOMINAL
    /* */
    public void SetButtonMode(boolean mode)
    {
        if (mSM.mApp.is_RightHanded) {
            B_UP = -4;
            B_DOWN = -3;
            B_LEFT = -1;
            B_RIGHT = -2;
            B_TOP = -7;
            B_BOT = -6;
            B_S1 = 51;
            B_S2 = 54;
            B_S3 = 57;
            B_S4 = 50;
            B_S5 = 53;
            B_S6 = 56;
            B_S7 = 49;
            B_S8 = 52;
            B_S9 = 55;
        }
        else {
            B_UP = -3;
            B_DOWN = -4;
            B_LEFT = -2;
            B_RIGHT = -1;
            B_TOP = -6;
            B_BOT = -7;
            B_S1 = 55;
            B_S2 = 52;
            B_S3 = 49;
            B_S4 = 56;
            B_S5 = 53;
            B_S6 = 50;
            B_S7 = 57;
            B_S8 = 54;
            B_S9 = 51;
        }
    }
    public void LoadMenus() {
        mStates[MAIN] = new State_MainMenu(this);
        mStates[START] = new State_StartMenu2(this);
        mStates[SETTINGS] = new State_SettingsMenu(this);
        mStates[ABOUT] = new State_About(this);
    }
    
    public void DeInitMenus() {
        for (int i=0;i<mNumMenus;i++)
            mStates[i].Destroy();
    }
}
