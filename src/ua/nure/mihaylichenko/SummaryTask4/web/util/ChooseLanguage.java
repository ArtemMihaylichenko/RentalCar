package ua.nure.mihaylichenko.SummaryTask4.web.util;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
/**
 * Choose locale language from session.
 * Formatting messages for UI 
 * @author A.Mihaylichenko
 *
 */
public class ChooseLanguage {
	
	private static final Locale RU = new Locale("ru");
	private static final Locale EN = new Locale("en");

	private static final ResourceBundle BUNDLE_EN = ResourceBundle.getBundle("bundle", EN);
	private static final ResourceBundle BUNDLE_RU = ResourceBundle.getBundle("bundle", RU);

	private static final Logger LOG = Logger.getLogger(ChooseLanguage.class);

	private static Locale getLocale(HttpSession session) {
		String language = (String) session.getAttribute("language");
		if (language != null && language.equals("en")) {
			return EN;
		}
		return RU;
	}

	public static String getValue(String key, HttpSession session) {
		Locale locale = getLocale(session);
		String value = "";
		if (key != null && session != null) {
			if (locale == EN) {
				LOG.info("locale --> " + locale + " = en");
				value = BUNDLE_EN.getString(key);
			} else {
				LOG.info("locale --> " + locale + " = en");
				value = BUNDLE_RU.getString(key);
			}
		} else if (session == null){
			value = BUNDLE_EN.getString(key);
			LOG.error("Locale == null" + " value --> " + value);
		}
		return value;
	}

}
