/*
 * BSFileReader.java
 *
 * Created on May 10, 2007, 8:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import java.io.*;

/**
 *
 * @author Bryan
 */
public class BSFileReader {
    InputStream is = null;
    
    /** Creates a new instance of BSFileReader */
    public BSFileReader() {
    }
    
    public boolean Open(String theFilePath)
    {
        try
        {
            is = this.getClass().getResourceAsStream(theFilePath);
        }
        catch (Exception e)
        {
            return false;
        }
        
        return true;
    }
    
    public String ReadLine()
    {
        StringBuffer temp = new StringBuffer();
        InputStreamReader ir = new InputStreamReader(is);
        int character = 0;     
        
        while (true)
        {
            try
            {
                character = ir.read();       
            }
            catch (EOFException e)
            {
                return "EOF";
            }
            catch (Exception e)
            {
                System.out.println(e);
                break;
            }
            
            if ((char)character != '\n' && (char)character != '\r')
            {
                temp.append((char)character);
            }
            else
            {
                //temp.delete(temp.length()-1, temp.length());
                try
                {
                    character = ir.read();
                }
                catch (Exception e)
                {
                    
                }
                break;
            }
        }
        
        return temp.toString();
    }
    
    public String GetParameter(char Token, StringBuffer theLine)
    {
        boolean cols = false;
        StringBuffer temp = theLine;
        StringBuffer Ret = new StringBuffer("");
        for (int i = 0; i < temp.length(); i ++)
        {
            char a = temp.charAt(i);
            if (a == '\"')
            {
                if (cols)
                    cols = false;
                else
                    cols = true;
            }
            else if (a != Token || cols)
            {
                Ret.append(a);
            }
            else if (a == Token && !cols)
            {
                temp.delete(0, i + 1);
                break;
            }
        }
        
        if (Ret.charAt(0) == '\"')
            Ret.deleteCharAt(0);
        if (Ret.charAt(Ret.length() - 1) == '\"')
            Ret.deleteCharAt(Ret.length() - 1);
        
        return Ret.toString();
    }
}
