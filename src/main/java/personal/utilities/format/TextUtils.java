package personal.utilities.format;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;

import java.util.*;

/**
 * Text manipulation utils
 * @author Ernestas
 */
public class TextUtils {
	
	public final static Log LOGGER = LogFactory.getLog(TextUtils.class);
	
	public final static String BLANK = "";
	public final static String WHITESPACE = " ";
	
	// SEPARATORS
	public final static String NEW_LINE = "\n";
	public final static String DEFAULT_SEPARATOR = ",";
	
	// STYLES
	public final static String STYLE_BOLD = "<strong>%s</strong>";
	
	// FORMATS
	public final static String QUOTES = "\" %s \"";
	public final static String BRACKETS = "[%s]";
	
	/**
	 * Append target with default separator
	 * @param targets - text
	 * @return joined text
	 */
	public static String append(String... targets) {
		return join(DEFAULT_SEPARATOR, targets);
	}
	
	/**
	 * Append Strings with custom separator
	 * @param separator - separates text
	 * @param targets - text
	 * @return joined text
	 */
	public static String join(String separator, String... targets) {
		StringJoiner joiner = new StringJoiner(separator);
		Arrays.stream(targets).filter(Objects::nonNull).forEach(joiner::add);
		return joiner.toString();
	}
	
	public static String replaceAllByMultipleFormats(String target, String replacement, String... formats) {
		for(String format : formats) {
			target = target.replaceAll(format, replacement);
		}
		return target;
	}
	
	public static String join(String separator, String toStringSeparator, Collection<?> collection) {
		if(CollectionUtils.isEmpty(collection)) {
			return BLANK;
		}
		String[] splitted = collection.toString().split(toStringSeparator);
		return join(separator, splitted);
	}
 
	/**
	 * Reverse order format: 
	 *   Before = Simple Text
	 *   After  = Text Simple
	 * @param target
	 * @return
	 */
	public static String reverseOrder(String target, String pattern) {
		StringBuilder sb = new StringBuilder(target.length());
		String[] words = target.split(pattern);
		int size = words.length - 1;
		boolean skipFirst = true;
			for(int i = size; i >= 0 ; i--) {
				if(skipFirst) {
					skipFirst = false;
				} else {
					sb.append(DEFAULT_SEPARATOR);
				}
				sb.append(words[i]);
			}
		return sb.toString(); 
	}
	
	public static char booleanToChar(boolean value) {
		return value ? 'Y' : 'N';
	}
	
	public static boolean charToBoolean(char value) {
		return value == 'Y';
	}
	
	 public static String reverseOrder(String target) {
			return reverseOrder(target, DEFAULT_SEPARATOR);
	 }
	 
	 public static boolean anyStringsEmpty(String first, String... strings) {
         if (StringUtils.isEmpty(first)) {
             return true;
         }
		 return Arrays.stream(strings).filter(s-> StringUtils.isEmpty(s)).findFirst().isPresent();
	 }

	 public static boolean anyStringsNotBlank(String first, String... strings) {
        if (StringUtils.isNotBlank(first)) {
            return true;
        }
         return Arrays.stream(strings).filter(s-> StringUtils.isNotBlank(s)).findFirst().isPresent();
     }
	 
	 @SuppressWarnings("rawtypes")
	 public static <T extends Collection<?>> Collection collectDisplayValueByProperty(T collection, String property) {
		 if(collection == null || collection.isEmpty()) {
			 return Collections.emptyList();
		 }
		 Class<?> type = org.springframework.util.CollectionUtils.findCommonElementType(collection);
		 try {
			 BeanWrapper typeWrapper = new BeanWrapperImpl(type);
			 typeWrapper.getPropertyValue(property);
		 } catch(BeansException be) {
			 LOGGER.warn(type + " doesn't have property named '" + property + "'");
			 return Collections.emptyList();
		 }
		 return CollectionUtils.collect(collection, new BeanToPropertyValueTransformer(property, true));
	 }
}
