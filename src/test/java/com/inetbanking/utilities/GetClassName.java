package com.inetbanking.utilities;

public class GetClassName {
	
	    public static String getCallerClassName() { 
	        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
	        for (int i=1; i<stElements.length; i++) {
	            StackTraceElement ste = stElements[i];
	            if (!ste.getClassName().equals(GetClassName.class.getName()) && ste.getClassName().indexOf("java.lang.Thread")!=0) {
	                return ste.getClassName();
	            }
	        }
	        return null;
	     }
	   
	    public static String getCallerCallerClassName() { 
	        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
	        String callerClassName = null;
	        for (int i=1; i<stElements.length; i++) {
	            StackTraceElement ste = stElements[i];
	            if (!ste.getClassName().equals(GetClassName.class.getName())&& ste.getClassName().indexOf("java.lang.Thread")!=0) {
	                if (callerClassName==null) {
	                    callerClassName = ste.getClassName();
	                } else if (!callerClassName.equals(ste.getClassName())) {
	                    return ste.getClassName();
	                }
	            }
	        }
	        return null;
	     }
	}
