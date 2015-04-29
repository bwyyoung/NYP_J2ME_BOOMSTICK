import java.io.*;
import java.util.*;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/*
 * BSResource.java
 *
 * Created on May 11, 2007, 10:44 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
/**
 *
 * @author Bryan Yeo
 */
class BSResource
{
    static final int BSIMAGERESOURCE = 1;
    static final int BSSOUNDRESOURCE = 2;
    
    int Type;
    
    public BSResourceManager mMgr;
    String Name;
    String Path;
    
    public boolean LoadResource()
    {
        return false;
    }
}

class BSImageResource extends BSResource
{
    public BSImage Res;
    
    public BSImageResource(BSResourceManager theMgr, String theName, String thePath)
    {
        mMgr = theMgr;
        Name = theName;
        Path = thePath;
        Type = BSResource.BSIMAGERESOURCE;
    }
    
    public boolean LoadResource()
    {
        Res = mMgr.mApp.mGraphics.NewImage(Path);
        
        if (Res != null)
            return true;
        else
            return false;
    }
}

class BSSoundResource extends BSResource
{
    public BSSound Res;
    
    public BSSoundResource(BSResourceManager theMgr, String theName, String thePath)
    {
        mMgr = theMgr;
        Name = theName;
        Path = thePath;
        Type = BSResource.BSSOUNDRESOURCE;
    }
    
    public boolean LoadResource()
    {
        Res = new BSSound(Path, 100);
        
        if (Res != null)
            return true;
        else
            return false;
    }
}

public class BSResourceManager 
{
    BSMainApp mApp;
    BSFileReader Read;
    int Num;
    
    private String ImageDef = new String();
    private String WeaponDef = new String();
    
    /** Creates a new instance of  BSResource */
    
    public BSResourceManager(BSMainApp theApp)
    {
        mApp = theApp;
        ImageDef = "image";
        WeaponDef = "sound";
    }
 
    public boolean LoadResource(String name, BSResourceHolder ret)
    {
        Read = new BSFileReader();
        String Type = new String();
        String loop = new String();
        int num=0;
        
        if (Read.Open(name))
        {
            String curLine = "meh";
            
            while (true)
            {
                curLine = Read.ReadLine();
                if (curLine.equalsIgnoreCase("eof"))
                {
                    break;
                }
                num ++;
            }
        }
        else
        {
            System.out.println("Cannot open file!");
            return false;
        }
        
        ret.mResources = new BSResource[num];
        num = 0;
        
        if (Read.Open(name))
        {
            String curLine = "meh";
            
            while (true)
            {
                curLine = Read.ReadLine();
                if (curLine.equalsIgnoreCase("eof"))
                {
                    break;
                }
                StringBuffer sbLine = new StringBuffer(curLine);
                Type = Read.GetParameter(' ', sbLine);
                
                if (Type.equalsIgnoreCase(ImageDef))
                {
                    String Name = Read.GetParameter(' ', sbLine);
                    String Path = Read.GetParameter(' ', sbLine);
                    ret.mResources[num] = new BSImageResource(this, Name, Path);
                    System.out.println(Name);
                }
                if (Type.equalsIgnoreCase("sound"))
                {
                    String Name = Read.GetParameter(' ', sbLine);
                    String Path = Read.GetParameter(' ', sbLine);
                    ret.mResources[num] = new BSSoundResource(this, Name, Path);
                    System.out.println(Name);
                }
                if (Type.equalsIgnoreCase("music"))
                {
                    String Name = Read.GetParameter(' ', sbLine);
                    String Path = Read.GetParameter(' ', sbLine);
                    ret.mResources[num] = new BSSoundResource(this, Name, Path);
                    System.out.println(Name);
                }
                
                num ++;
            }
        }
        else
        {
            System.out.println("Cannot open file!");
            return false;
        }
        Num = 0;
        
        return true;
    }
  
    public int LoadNextResource(BSResourceHolder theResourceHolder)
    {
        if (theResourceHolder.mLoadedResources == theResourceHolder.mResources.length)
            return 100;
        
        if (!theResourceHolder.mResources[theResourceHolder.mLoadedResources].LoadResource())
            mApp.mCanvas.mApp.notifyDestroyed();
        
        theResourceHolder.mLoadedResources++;
        return (int) theResourceHolder.mLoadedResources * 100/theResourceHolder.mResources.length;
    }
}
