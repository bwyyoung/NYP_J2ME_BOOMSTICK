/*
 * BSResourceHolder.java
 *
 * Created on June 23, 2007, 12:39 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Bryan
 */
public class BSResourceHolder {
    BSResource mResources[];
    int mLoadedResources;
    
    /** Creates a new instance of BSResourceHolder */
    public BSResourceHolder() {
        mLoadedResources = 0;
    }
    
    public BSImage GetImageThrow(String theName)
    {
        for (int i = 0; i < mResources.length; i ++)
        {
            if (mResources[i].Type == BSResource.BSIMAGERESOURCE && mResources[i].Name.equalsIgnoreCase(theName))
            {
                return ((BSImageResource)mResources[i]).Res;
            }
        }
        
        return null;
    }
}
