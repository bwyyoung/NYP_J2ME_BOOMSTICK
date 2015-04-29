/*
 * BSSound.java
 *
 * Created on June 27, 2007, 6:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import javax.microedition.media.*;
import javax.microedition.media.control.*;
import java.io.*;

/**
 *
 * @author Bryan
 */
public class BSSound implements PlayerListener
{
    String Type;
    Player p;
    boolean mLoop = false;
    
    public void SetVolume(int theVolume)
    {
        try
        {
            VolumeControl vc = (VolumeControl) p.getControl("VolumeControl");
            if (vc != null)
                vc.setLevel(theVolume);
        }
        catch (Exception e)
        {
            
        }
    }
    
    public void SetLoop(boolean theLoop)
    {
        mLoop = theLoop;
    }
    
    /** Creates a new instance of BSSound */
    public BSSound(String theFilePath, int Volume) {
        try
        {
            InputStream is = getClass().getResourceAsStream(theFilePath);
            if (theFilePath.substring(theFilePath.length() - 3).equalsIgnoreCase("mp3"))
                Type = "audio/mpeg";
            if (theFilePath.substring(theFilePath.length() - 3).equalsIgnoreCase("wav"))
                Type = "audio/x-wav";
            if (theFilePath.substring(theFilePath.length() - 3).equalsIgnoreCase("mid"))
                Type = "audio/midi";
            
            p = javax.microedition.media.Manager.createPlayer(is, Type);
            p.realize();
            VolumeControl vc = (VolumeControl) p.getControl("VolumeControl");
            if (vc != null)
                vc.setLevel(Volume);
            p.prefetch();
        }
        catch (Exception e)
        {
            
        }
    }
    
    public void Play()
    {
        try
        {
            p.stop();
            p.setMediaTime(0);
            p.start();
        }
        catch(Exception e)
        {
            
        }
    }
    
    public void Stop()
    {
        try
        {
            p.stop();
            p.setMediaTime(0);
        }
        catch (Exception e)
        {
            
        }
    }

    public void playerUpdate(Player player, String string, Object object) {
        if (mLoop && player == this.p && string == END_OF_MEDIA)
            try
            {
               p.stop();
               p.setMediaTime(0);
               p.start();
            }
            catch (Exception e)
            {
                
            }
    }
}
