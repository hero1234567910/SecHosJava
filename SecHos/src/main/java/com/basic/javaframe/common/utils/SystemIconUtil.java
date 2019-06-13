package com.basic.javaframe.common.utils;

import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;

public class SystemIconUtil {
	
	
	/**
	 * 获取小图标
	 * @param f
	 * @return
	 */
	    public static Icon getSmallIcon( File f )
	    {
	        if ( f != null && f.exists() )
	        {
	            FileSystemView fsv = FileSystemView.getFileSystemView();
	            return(fsv.getSystemIcon( f ) );
	        }
	        return(null);
	    }
	 
	 
	/**
	 * 获取大图标
	 * @param f
	 * @return
	 */
	    public static Icon getBigIcon( File f )
	    {
	        if ( f != null && f.exists() )
	        {
	            try {
	                sun.awt.shell.ShellFolder sf = sun.awt.shell.ShellFolder.getShellFolder( f );
	                return(new ImageIcon( sf.getIcon( true ) ) );
	            } catch ( FileNotFoundException e ) {
	/* TODO Auto-generated catch block */
	                e.printStackTrace();
	            }
	        }
	        return(null);
	    }
	

}
