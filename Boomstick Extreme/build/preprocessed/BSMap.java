/*
 * BSMap.java
 *
 * Created on May 9, 2007, 10:46 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import com.sun.midp.io.j2me.storage.File;
import java.io.*;

/**
 *
 * @author Bryan
 */
public class BSMap {
    public BSMaploader mMaploader;
    
    //Map data
    public int mSequences; // Number of sequences
    public int mHoles[][]; // Holes to pop out from during each sequence
    public int mSequenceTime; // Time between sequences
    
    /** Creates a new instance of BSMap */
    public BSMap(BSMaploader theMaploader) {
        mMaploader = theMaploader;
    }
    
    public boolean LoadMap(String thePath)
    {
        BSFileReader fr = new BSFileReader();
        if (!fr.Open(thePath))
            return false;
        
        try
        {
            mSequences = Integer.parseInt(fr.ReadLine(), 10);
            mSequenceTime = Integer.parseInt(fr.ReadLine(), 10);
        }
        catch (Exception e)
        {
            return false;
        }
        mHoles = new int[mSequences][9];

        for (int i = 0; i < mSequences; i ++)
        {
            for (int j = 0; j < 9; j ++)
            {
                try
                {
                    mHoles[i][j] = Integer.parseInt(fr.ReadLine(), 10);
                }
                catch (Exception e)
                {
                    return false;
                }
            }
        }
        
        return true;
    }
}
