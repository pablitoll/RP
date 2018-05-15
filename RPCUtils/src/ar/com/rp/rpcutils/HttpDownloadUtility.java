package ar.com.rp.rpcutils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

import javax.swing.JProgressBar;

	/**
	 * A utility that downloads a file from a URL.
	 * @author www.codejava.net
	 *
	 */
	public class HttpDownloadUtility  {

		private static final int BUFFER_SIZE = 4096;
	 
		
		public static boolean existURL(String fileURL) throws IOException{
	        URL url = new URL(fileURL);
	        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
	        int responseCode = httpConn.getResponseCode();	 
	        return responseCode == HttpURLConnection.HTTP_OK;
		}
		
	    /**
	     * Downloads a file from a URL
	     * @param fileURL HTTP URL of the file to be downloaded
	     * @param saveDir path of the directory to save the file
	     * @throws DownloadException 
	     * @throws IOException
	     */
		public static boolean downloadFile(String fileURL, String saveDir, String usr_ws, String pass_usr_ws) throws Exception {
			return downloadFile(fileURL, saveDir, usr_ws, pass_usr_ws, null);
		}
		
		public static boolean downloadFile(String fileURL, String saveDir, String usr_ws, String pass_usr_ws, JProgressBar barra) throws Exception{
	    	boolean retorno = false;
	    	
	    	if((usr_ws != null) && !usr_ws.trim().equals("")){

	    		Authenticator.setDefault (new Authenticator() {
				    protected PasswordAuthentication getPasswordAuthentication() {
						try {
							return new PasswordAuthentication (usr_ws, pass_usr_ws.toCharArray());
						} catch (Exception e) {
							e.printStackTrace();
							throw(e);
						}
				    }
				});
	    	}
	    	
	        URL url = new URL(fileURL);
	        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
	        int responseCode = httpConn.getResponseCode();
	 
	        // always check HTTP response code first
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            String fileName = "";
	            String disposition = httpConn.getHeaderField("Content-Disposition");
	 
	            if (disposition != null) {
	                // extracts file name from header field
	                int index = disposition.indexOf("filename=");
	                if (index > 0) {
	                    fileName = disposition.substring(index + 10,
	                            disposition.length() - 1);
	                }
	            } else {
	                // extracts file name from URL
	                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
	                        fileURL.length());
	            }
	 
	            // opens input stream from the HTTP connection
	            InputStream inputStream = httpConn.getInputStream();
	            String saveFilePath = saveDir + File.separator + fileName;
	             
	            // opens an output stream to save into file
	            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
	 
	            int bytesRead = -1;
	            byte[] buffer = new byte[BUFFER_SIZE];
	            long totalSize = httpConn.getContentLength();
	            long readSize = 0;
	            int base = 0;
	            if(barra != null){
	            	base = barra.getValue();
	            }
	            while ((bytesRead = inputStream.read(buffer)) != -1) {
	                outputStream.write(buffer, 0, bytesRead);	                
	                
	                if(barra != null){
	                	readSize += bytesRead;
	                	int avance = (int)(readSize * 100 / totalSize);
	                	
	                	barra.setValue(base + avance);
	                }
	            }	 
	            outputStream.close();
	            inputStream.close();
	            retorno = true;
	        } else {
//	            System.out.println("No file to download. " + fileURL + " .Server replied HTTP code: " + responseCode);
	            throw new DownloadException("No file to download. " + fileURL + " .Server replied HTTP code: " + responseCode);
	        }
	        httpConn.disconnect();
	        return retorno;
	    }
	}