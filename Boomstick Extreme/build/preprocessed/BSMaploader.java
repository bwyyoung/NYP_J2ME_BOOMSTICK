/*
 * BSMap.java
 *
 * Created on May 9, 2007, 10:26 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import java.util.*;

/**
 *
 * @author Bryan
 */
public class BSMaploader {
    public BSMainApp mApp;
    int mMapIndex = 0;
    BSMap mMaps[];
    Vector Maps;
    int MapIndex;
    
    /** Creates a new instance of BSMap */
    public BSMaploader(BSMainApp theApp) {
        mApp = theApp;
        Maps = new Vector();
        MapIndex = 0;
    }
    
    public boolean LoadMap(String Path)
    {
        BSMap newMap = new BSMap(this);
        if (!newMap.LoadMap(Path))
            return false;
        Maps.setSize(MapIndex + 1);
        Maps.setElementAt(newMap, MapIndex);
        MapIndex++;
//        BSMap temp[] = null;
//        if (mMapIndex > 0)
//        {
//            temp = new BSMap[mMapIndex];
//            for (int i = 0; i < mMapIndex; i ++)
//            {
//                temp[i] = mMaps[i];
//            }
//        }
//        mMapIndex ++;
//        mMaps = new BSMap[mMapIndex];
//        mMapIndex --;
//        if (mMapIndex > 0)
//        {
//            for (int i = 0; i < mMapIndex; i ++)
//            {
//                mMaps[i] = temp[i];
//            }
//        }
//        if (!mMaps[mMapIndex].LoadMap(Path))
//            return false;
//        mMapIndex ++;
        return true;
    }
    
    public void GetMaps(BSMap theMaps[])
    {
        theMaps = mMaps;
    }
    
    public BSMap GetMap(int Index)
    {
        return (BSMap) Maps.elementAt(Index);
    }
}
