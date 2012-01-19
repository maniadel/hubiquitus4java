/*
 * Copyright (c) Novedia Group 2012.
 *
 *     This file is part of Hubiquitus.
 *
 *     Hubiquitus is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Hubiquitus is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Hubiquitus.  If not, see <http://www.gnu.org/licenses/>.
 */


package org.hubiquitus.hapi.utils.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static final String FORMAT_DATE_YYYYslMMslDD_HHMMSS = "yyyy/MM/dd HH:mm:ss";
	
	public static final String FORMAT_DATE_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	
    public static Date parseDate(String date, String format) throws ParseException {
        	SimpleDateFormat formatter = new SimpleDateFormat(format);
        	return formatter.parse(date);
        
    }
    
    
   /**
    * Format a date
    * @param date the date do format
    * @param dateFormat the format
    * @return the date formated
    */
   public static String formatDate(Date date, String dateFormat) {
	   SimpleDateFormat updateDateFormat = new SimpleDateFormat(dateFormat);
       String dat = "";
    
       try {
           if ((date != null) && (!date.equals("")))
               dat = updateDateFormat.format(date);
       } catch (Exception e) {
           dat = "";
       }
       return dat;
   }
}
