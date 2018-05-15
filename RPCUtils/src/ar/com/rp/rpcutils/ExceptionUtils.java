package ar.com.rp.rpcutils;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.google.common.base.Throwables;

public class ExceptionUtils {

	public static String exception2String(Exception error) {
	    
		StringWriter writer = new StringWriter();
	    PrintWriter printWriter = new PrintWriter( writer );
	    if(error.getCause() != null){
	    	error.getCause().printStackTrace( printWriter );
	    } else {
	    	error.printStackTrace( printWriter );
	    }
	    printWriter.flush();
	    
	    return Throwables.getStackTraceAsString(error);
	}
}
