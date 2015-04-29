/*
 * BSSprite.java
 *
 * Created on May 11, 2007, 3:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.Sprite;
/**
 *
 * @author Israel
 */
public class BSSprite {
    public Sprite s;
    public BSGraphics mGraphics;// Reference to Graphics
    
    //Frame Sequences variables. A frame sequence is a bunch of frames that make an animation
    private boolean mIsAnimating;  // True if an animation is currently playing. False if animation is stagnant at one frame.
    private boolean mLoop;         // True if current playing animation loops. False if otherwise.
    private int mMSPerFrame;       // The amount of time in Milliseconds before changing frames.
    private int mFrameTimePassed;  // The time passed in Milliseconds since previous frame update.
    private int mCurSequence;      // The current animation sequence the sprite is set to play.
    private int mNumSequences;     // The number of animation sequences.
    public int [][] mSequences;    // Stores animation sequences.
    
    public static int TRANS_NONE = Sprite.TRANS_NONE;
    public static int TRANS_ROT90 = Sprite.TRANS_ROT90;
    public static int TRANS_ROT180 = Sprite.TRANS_ROT180;
    public static int TRANS_ROT270 = Sprite.TRANS_ROT270;
    public static int TRANS_MIRROR = Sprite.TRANS_MIRROR;
    public static int TRANS_MIRROR_ROT90 = Sprite.TRANS_MIRROR_ROT90;
    public static int TRANS_MIRROR_ROT180 = Sprite.TRANS_MIRROR_ROT180;
    public static int TRANS_MIRROR_ROT270 = Sprite.TRANS_MIRROR_ROT270;
    
    /** Creates a new instance of BSSprite */
    public BSSprite(BSGraphics g) {
        mMSPerFrame = BSCanvas.MSPF;      //Default MS per frame
        mFrameTimePassed = 0;
        mNumSequences = 0;
        mCurSequence = -1;
        
        mGraphics = g;
    }
    
    //NOMINAL
    public void setPosition(int x, int y) {
        s.setRefPixelPosition(x, y);
    }
    
    //NOMINAL
    public void Translate(int x, int y) {
        s.setRefPixelPosition(s.getRefPixelX()+x, s.getRefPixelY()+y);
    }
    
    //NOMINAL
    public void setRefPixel(int x, int y) {
        s.defineReferencePixel(x,y);
    }
    
    //NOMINAL
    /** Adds a new animation sequence to BSSprite. Returns id (int) of sequence */
    public int addFrameSequence(int[] sequence) {
        int [][] Buff = new int [1][1];  //Temporary Buffer to store sequence in mFrameSequence
        
        //NOTE: A single sequence is an array. Thus, we create an array of arrays
        //      to store multiple sequences.
        
        if (mNumSequences != 0) {
            //Stores sequences in mFrameSequences into a temp Buffer
            Buff = new int [mNumSequences][];
            for (int i=0;i<mNumSequences;i++) {
                Buff[i] = new int [mSequences[i].length];
                for (int j=0;j<mSequences[i].length;j++) {
                    Buff[i][j] = mSequences[i][j];
                }
            }
        }
        
        //Re-declare frame sequences buffer, adding one more sequence to it
        mNumSequences++;
        mSequences = new int [mNumSequences][];
        
        //Gets sequences from the old frame sequences buffer and store it into the new
        if (mNumSequences-1 != 0) {
            for (int i=0;i<mNumSequences-1;i++) {
                mSequences[i] = new int [Buff[i].length];
                for (int j=0;j<Buff[i].length;j++) {
                    mSequences[i][j] = Buff[i][j];
                }
            }
        }
        
        //Store new sequence into frame sequence buffer
        mSequences[mNumSequences-1] = new int [sequence.length];
        for (int i=0;i<sequence.length;i++) {
            mSequences[mNumSequences-1][i] = sequence[i];
        }
        
        return mNumSequences-1;
    }
    
    //NOMINAL
    //Plays an animation sequence at current MS per frame
    public void playSequence(int curSequence, boolean loop)
    {
        mCurSequence = curSequence;
        mLoop = loop;
        s.setFrameSequence(mSequences[mCurSequence]);
        mIsAnimating = true;
    }
    
    //NOMINAL
    //Plays an animation sequence at specified MS per frame
    public void playSequence(int curSequence, boolean loop, int MillisecondsPerFrame)
    {
        mMSPerFrame = MillisecondsPerFrame;
        mCurSequence = curSequence;
        mLoop = loop;
        s.setFrameSequence(mSequences[mCurSequence]);
        mIsAnimating = true;
    }
    
    //NOMINAL
    public void Continue()
    {
        mIsAnimating = true;
    }
    
    //NOMINAL
    public void Pause()
    {
        mIsAnimating = false;
    }
    
    //NOMINAL
    /* Updates sprite's animation frame. Must be called within game loop. */
    public void Update()
    {
        if (mNumSequences == 0) return; //If there are no sequences to be played, return.
        if (mCurSequence == -1) return; //If Current playing sequence is not initiated, return.
        if (mIsAnimating) {
            //Update time passed since last frame was played
            mFrameTimePassed += BSCanvas.MSPF;
            //Check if it is time to update frame. Return if it is not time.
            if (mFrameTimePassed < mMSPerFrame) return;
            mFrameTimePassed -= mMSPerFrame;
            
            //If current frame is the last frame...
            if (s.getFrame() == (s.getFrameSequenceLength() - 1)) {
                //Play next frame (1st frame) if animation loops
                if (mLoop) s.nextFrame();
                //Stops animation if animation does not loop
                else {
                    mIsAnimating = false;
                    //return;
                }
            }
            //If current frame is NOT the last frame, just play the next frame
            else {
                s.nextFrame();
            }
        }
    }
    
    //NULL
    /* Renders Sprite */
    public void Render() {
        s.paint(mGraphics.mBackBuffer.getGraphics());
    }
    
    public boolean GetIsAnimating() {
        return mIsAnimating;
    }
    public int GetCurSequence() {
        return mCurSequence;
    }
    public int GetX() {
        return s.getRefPixelX();
    }
    public int GetY() {
        return s.getRefPixelY();
    }
}
