/*
 * BSRms.java
 *
 * Created on June 29, 2007, 5:39 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import javax.microedition.rms.*;

/**
 *
 * @author Bryan
 */
public class BSRms {
    
    /** Creates a new instance of BSRms */
    public BSRms() {
    }
    
    static public boolean CheckBoolean(byte RMSDat)
    {
        if (RMSDat == 1)
            return true;
        else
            return false;
    }
    
    static public byte AddBoolean(boolean Bool)
    {
        if (Bool)
            return 1;
        else
            return 0;
    }
    
    static public void SaveUnlockedLevels(boolean UnlockedLevels[], RecordStore Rec)
    {
        try
        {
            byte bData[] = new byte[UnlockedLevels.length];
            for (int i = 0; i < UnlockedLevels.length; i ++)
            {
                bData[i] = AddBoolean(UnlockedLevels[i]);
            }
            
            Rec.setRecord(2, bData, 0, bData.length);
        }
        catch (Exception e)
        {
            
        }
    }
}
