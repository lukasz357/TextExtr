package dateFormat;
import java.text.*; 
import java.util.Date; 
/** 
 * Title: MultiDateFormat 
 * Description: MultiDateFormat is DateFormat defined with more than one format. It parses 
 * using all of these formats, but formats using only the one. 
 * Parsing is done in order as given in the constructor. 
 * If you provide two formats that could read the same date, this class will use the first one. 
 * If you have extended versions of the pattern, i.e. YYYY-MM-DD and  YYYY-MM-DD T hh:mm, 
 * you should place the longer ones first. 
 * Copyright:    Copyright (c) 2001 
 * @author Paul Hill goodh...@xmission.com 
 * @version 1.0 
 */ 
public class MultiDateFormat extends SimpleDateFormat { 
	private static final long serialVersionUID = -5791448343554834634L;
	/** 
     * List of possible formats with which to parse a String 
     */ 
    protected DateFormat parseFormat[]; 
    /** 
     * The format to use to create a String from a Date. 
     */ 
    protected DateFormat defaultFormat; 
    /** 
     * Construct a MultiDateFormat using the given DateFormats. Set 
     * @param someParseFormats an array of formats which to try in order when parsing. 
     * @param aDefaultFormat the format to use create a String from a Date. 
     * 
     * @see java.text.SimpleDateFormat 
     */ 
    public MultiDateFormat( DateFormat[] someParseFormats, DateFormat aDefaultFormat ) { 
        this( someParseFormats ); 
        this.setDefaultFormat( aDefaultFormat ); 
    } 
    /** 
     * Construct a MultiDateFormat using the given DateFormats and setting the default format 
pattern. 
     * 
     * @see java.text.SimpleDateFormat 
     */ 
    public MultiDateFormat( DateFormat[] someParseFormats ) { 
        super(); 
        if ( someParseFormats == null || someParseFormats.length < 1 ) { 
            throw new IllegalArgumentException( "length of someParseFormats must be greater than zero" ); 
        } 
        this.parseFormat = someParseFormats; 
        this.defaultFormat = someParseFormats[ 0 ]; 
        this.setCalendar( someParseFormats[ 0 ].getCalendar() ); 
    } 
    /** 
     * Formats a Date into a date/time string using the default format. 
     * @param date a Date to for formatting into a date/time string. 
     * @param toAppendTo the string buffer in which return a date/time string. 
     * @param fieldPosition contains the position of the field within the returned string. 
     * On input: an alignment field, if desired. 
     * On output: the offsets of the requested field. 
     * @return a StringBuffer with the Date formated in it. 
     * @see java.text.DateFormat 
     */ 
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) { 
        return this.defaultFormat.format( date, toAppendTo, fieldPosition ); 
    } 
    /** 
     * Overrides DateFormat.parse to try all possible formats 
     * @see java.text.DateFormat 
     */ 
    public Date parse(String text, ParsePosition pos) { 
        Date date = null; 
        for ( int i = 0; date == null && i < this.parseFormat.length; i++ ) { 
            date = parseFormat[ i ].parse( text, pos ); 
        } 
        return date; 
    } 
    /** 
     * Set a different DateFormat as the format to use when calling format. 
     */ 
    public void setDefaultFormat( DateFormat aFormat ) { 
         if ( aFormat == null ) { 
            throw new IllegalArgumentException( "The default format can not be set to null." ); 
        } 
       this.defaultFormat = aFormat; 
    } 
} 