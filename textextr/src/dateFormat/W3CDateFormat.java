package dateFormat;

import java.text.SimpleDateFormat; 
import java.util.TimeZone;
/** 
 * Title: 
 * Description: Parse ISO8601 limited to the profile defined by the W3C, and 
 * 
 * Copyright:    Copyright (c) 2001 
 * Company: 
 * @author Paul Hill 
 * @version 1.0 
 */ 
public class W3CDateFormat extends MultiDateFormat { 
	private static final long serialVersionUID = 3311505138403311201L;
/** 
    * a table of possible formats for W3C ISO8301 Profile 
    */

    protected static SimpleDateFormat[] format = {
    	new SimpleDateFormat( "yyyyMMdd"),
        new SimpleDateFormat( "dd.MM.yyyy" ),
        new SimpleDateFormat( "dd.MM.yy" ),
        new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss.Szzz" ), 
        new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss.S" ), 
        new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss" ), 
        new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:sszzz" ), 
        new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm" ), 
        new SimpleDateFormat( "yyyy-MM-dd'T'HH:mmzzz" ), 
        new SimpleDateFormat( "yyyy-MM-dd" ), 
        new SimpleDateFormat( "yyyy-MM" ), 
        new SimpleDateFormat( "yyyy" )
        
    }; 
    /** 
     * Set all of the formats to the GMT timezone and strict (don't allow 
     * hour 25 and other curiosities. 
     */ 
    static { 
        TimeZone utc = TimeZone.getTimeZone( "GMT" ); 
        for ( int i = 0; i < format.length; i++ ) { 
            format[ i ].setLenient( false ); 
            format[ i ].setTimeZone( utc );
        } 
    } 
    public W3CDateFormat() { 
        super( W3CDateFormat.format ); 
    } 
} 