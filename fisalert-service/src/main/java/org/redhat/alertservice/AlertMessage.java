package org.redhat.alertservice;

public class AlertMessage {

	public static String enumInIf(String type, UserInfo userInfo) {
	       if(type .equalsIgnoreCase(Types.accident.name())) {
	           return " This service will alert to accident infromation can be used by traffic police department to inform about accident automatically ";
	       }else if (type .equalsIgnoreCase( Types.weather.name())) {
	    	   return " This service will alert about weather condition can be used to inform early morning message about current day weather with respect to area address ";
	       }else if (type .equalsIgnoreCase( Types.mailbox.name())) {
	    	   return " This service will alert about delievry or mail box alert  ";
	       }else if (type .equalsIgnoreCase( Types.transcation.name())) {
	    	   return " This is alert service will alert about transcation";
	       }else if (type .equalsIgnoreCase( Types.appointment.name())) {
	    	   return " This is alert service will alert about appointment";
	       }else if (type .equalsIgnoreCase( Types.advertisement.name())) {
	    	   return " This is alert service will alert about advertisement";
	       }else {
	    	   return " Service alert message  ";
	       }
	   }
}


