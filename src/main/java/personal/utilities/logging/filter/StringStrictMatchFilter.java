package personal.utilities.logging.filter;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;
import personal.utilities.format.TextUtils;

/**
 * <p> <b>Case : </b>
 * Usually LOG messages should be filtered and displayed if it contains any of specified words or symbols. <br />
 * 
 * <p> <b>Practical motivation: </b>
 * Applying certain messages pattern is good way to find and follow desired LOG messages. <br />
 * However, sometimes you need to split those messages in the same class within same <i>LOGGING LEVEL</i>
 * and save it to different files, or apply specific appenders for it. It should help to filter out specific messages 
 * before any action is applied on it (saving to file, displaying to console and so on).
 * <br /> <br />
 * <b>  !< It helps to omit MESSAGES which haven't specified word(s), or symbol(s) >! </b>
 * 
 * @author Ernestas
 */
public class StringStrictMatchFilter extends Filter {
	
	protected String stringToMatch;
	protected boolean acceptOnMatch;
	 
	@Override
	public int decide(LoggingEvent event) {
		String msg = event.getRenderedMessage();
		
	    if (TextUtils.anyStringsEmpty(msg, stringToMatch)) {
	        return Filter.NEUTRAL;
	    }
	    
	    if (msg.indexOf(stringToMatch) == -1) {
	    	return Filter.DENY;
	    } 
	    return Filter.ACCEPT;
	}
	
	  public void setStringToMatch(String stringToMatch) {
	    this.stringToMatch = stringToMatch;
	  }
	  
	  public String getStringToMatch() {
	    return stringToMatch;
	  }
	  
	  public void setAcceptOnMatch(boolean acceptOnMatch) {
	    this.acceptOnMatch = acceptOnMatch;
	  }
	  
	  public
	  boolean getAcceptOnMatch() {
	    return acceptOnMatch;
	  }
}
