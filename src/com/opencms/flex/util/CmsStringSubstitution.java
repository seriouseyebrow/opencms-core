/*
 * File   : $Source: /alkacon/cvs/opencms/src/com/opencms/flex/util/Attic/CmsStringSubstitution.java,v $
 * Date   : $Date: 2003/03/07 15:16:36 $
 * Version: $Revision: 1.5 $
 *
 * This library is part of OpenCms -
 * the Open Source Content Mananagement System
 *
 * Copyright (C) 2002 - 2003 Alkacon Software (http://www.alkacon.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about Alkacon Software, please see the
 * company website: http://www.alkacon.com
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.opencms.flex.util;

import org.apache.oro.text.perl.Perl5Util;
import org.apache.oro.text.perl.MalformedPerl5PatternException;

/**
 * Provides a String substitution functionality
 * with Perl regular expressions.<p>
 * 
 * @author  Andreas Zahner (a.zahner@alkacon.com)
 * @version $Revision: 1.5 $
 * @since 5.0
 */
public class CmsStringSubstitution {

    /** DEBUG flag */
    private static final int DEBUG = 0;
    
    /** 
     * Default constructor (empty).<p>
     */
    public CmsStringSubstitution() {}
    
    /**
     * Substitutes searchString in content with replaceItem.<p>
     * 
     * @param content the content which is scanned
     * @param searchString the String which is searched in content
     * @param replaceItem the new String which replaces searchString
     * @return String the substituted String
     */
    public static String substitute(String content, String searchString, String replaceItem) {
    	return substitutePerl(content, escapePattern(searchString), escapePattern(replaceItem), "g");
    }
    
    /**
     * Substitutes first occurance of searchString in content with replaceItem.<p>
     * 
     * @param content the content which is scanned
     * @param searchString the String which is searched in content
     * @param replaceItem the new String which replaces searchString
     * @return String the substituted String
     */
    public static String substituteFirst(String content, String searchString, String replaceItem) {
    	return substitutePerl(content, escapePattern(searchString), escapePattern(replaceItem), "");
    }
    
    /**
     * Substitutes searchString in content with replaceItem.<p>
     * 
     * @param content the content which is scanned
     * @param searchString the String which is searched in content
     * @param replaceItem the new String which replaces searchString
     * @param occurences must be a "g" if all occurences of searchString shall be replaced
     * @return String the substituted String
     */
    public static String substitutePerl(String content, String searchString, String replaceItem, String occurences) {
    	String translationRule = "s#"+searchString+"#"+replaceItem+"#"+occurences;
    	Perl5Util perlUtil = new Perl5Util();
    	try {
    		return perlUtil.substitute(translationRule, content); 
    	} 
    	catch(MalformedPerl5PatternException e){
    		if (DEBUG>0) System.err.println("[CmsStringSubstitution]: "+e.toString());				
    	}
    	return content;		
    }
        
    /**
     * Escapes a String so it may be used as a Perl5 regular expression.<p>
     * 
     * This method replaces the following characters in a String:<br>
     * <code>{}[]()\$^.*+/</code>
     * 
     * 
     * @param source the string to escape
     * @return the escaped string
     */
    public static String escapePattern(String source) {
    	if (DEBUG>0) System.err.println("[CmsStringSubstitution]: escaping String: "+source);
    	if (source == null) return null;
    	StringBuffer result = new StringBuffer(source.length()*2);
    	for(int i = 0;i < source.length(); ++i) {
    		char ch = source.charAt(i);
    		switch (ch) {
                case '\\' :
                    result.append("\\\\");
                    break;                
    			case '/' :
    				result.append("\\/");
    				break;
    			case '$' :                
    				result.append("\\$");
    				break;
                case '^' :                
                    result.append("\\^");
                    break;    
                case '.' :                
                    result.append("\\.");
                    break; 
                case '*' :                
                    result.append("\\*");
                    break;   
                case '+' :                
                    result.append("\\+");
                    break;                      
                case '|' :                
                    result.append("\\|");
                    break;  
                case '?' :                
                    result.append("\\?");
                    break;   
                case '{' :
                    result.append("\\{");
                    break;          
                case '}' :
                    result.append("\\}");
                    break;    
                case '[' :
                    result.append("\\[");
                    break;          
                case ']' :
                    result.append("\\]");
                    break;        
                case '(' :
                    result.append("\\(");
                    break;          
                case ')' :
                    result.append("\\)");
                    break; 
    			default :
    				result.append(ch);
    		}
    	}
    	if (DEBUG>0) System.err.println("[CmsStringSubstitution]: escaped String to: "+result.toString());
    	return new String(result);
    }
}
