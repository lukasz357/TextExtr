package dateFormat;

import java.text.*; 
import java.util.*; 
import junit.framework.*; 
import junit.textui.*; 
import dateFormat.W3CDateFormat; 
/** 
 * Title: 
 * Description: 
 * Copyright:    Copyright (c) 2001 
 * Company: 
 * @author Paul Hill 
 * @version 1.0 
 */ 
public class W3CDateTest extends junit.framework.TestCase { 
    DateFormat w3; 
    /** 
     * @param testName name of method to invoke to run the test 
     */ 
    public W3CDateTest( String testName ) throws Exception { 
        super( testName ); 
    } 
    public static void main(String args[]) { 
        TestRunner.run( W3CDateTest.class ); 
    } 
    protected void setUp() { 
        w3 = new W3CDateFormat(); 
    } 
    public void testBasicFormat() throws Exception { 
        // first we try a basic date and time 
        String s = w3.format( w3.parse(  "2001-10-30T10:11:12.5" ) ); 
        // There result is practically ISO 8601, but includes "GMT" instead of +0:00 
        assertEquals( "2001-10-30T10:11:12.5GMT", s ); 
        // here is another one that is parsable, but results in reasonale ISO 8610 ignoring 
        // the timezone 
        s = w3.format( w3.parse(  "2001-10-30" ) ); 
        assertEquals( "2001-10-30T00:00:00.0GMT", s ); 
        // but what about the following! 
        s = w3.format( w3.parse( "2001-10-30T09:11:12.5" ) ); 
        assertEquals( "2001-10-30T09:11:12.5GMT", s ); 
        // this next one isn't a valid 8601 date, but it is parsed by a SimpleDateFormat 
        // it succeeds because the parse doesn't worry about extra spaces. 
//        s = w3.format( w3.parse( " 2001- 10- 30T 10: 11:12.5 +100" ) ); 
//        assertEquals( "2001-10-30T09:11:12.5GMT", s ); 
//        TimeZone tz = TimeZone.getAvailableIDs(); 
//        w3.setTimeZone( tz ); 
//        s = w3.format( w3.parse( "2001-10-30T10:11:12.5 +100" ) ); 
//        // But ISO8601 should be simply +100 like the input, i.e. without the "GMT" 
//        assertEquals( "2001-10-30T09:11:12.5GMT+00:00", s ); 
        // System.out.println( s ); 
    } 
}
