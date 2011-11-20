package textextr;

import java.io.*;
import java.net.*;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.log4j.*;

public class URLDownloader {
	private static Logger log = Logger.getLogger( HTMLParser.class );
	final static int size=1024;
	public static void fileUrl(String fAddress, String localFileName, String destinationDir) {
		OutputStream outStream = null;
		URLConnection  uCon = null;
		InputStream is = null;
		try {
			URL Url;
			byte[] buf;
			int ByteRead,ByteWritten=0;
			Url= new URL(fAddress);
			outStream = new BufferedOutputStream(new FileOutputStream(destinationDir+"/"+localFileName));

			uCon = Url.openConnection();
			is = uCon.getInputStream();
			buf = new byte[size];
			while ((ByteRead = is.read(buf)) != -1) {
				outStream.write(buf, 0, ByteRead);
				ByteWritten += ByteRead;
			}
			log.info("Udalo sie pobrac");
			System.out.println("Pobrano pomyślnie.");
			System.out.println
			("Nazwa: \""+localFileName+ "\"\nRozmiar: " + ByteWritten/1024 + "KB");
		}
		catch (Exception e) {
			log.error("Blad podczas pobierania plików PDF");
			e.printStackTrace();
		}
		finally {
			try {
				is.close();
				outStream.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}}}
	public static void fileDownload(String fAddress, String destinationDir) throws Exception	{

		int slashIndex =fAddress.lastIndexOf('/');
		int periodIndex =fAddress.lastIndexOf('.');
		String fileName=fAddress.substring(slashIndex + 1);
		/*
		 * Tworzy TrustManagera, który nie sprawdza czy certyfikat jest wiarygodny
		 */
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[]{
				new X509TrustManager() {
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return null;
					}
					public void checkClientTrusted(
							java.security.cert.X509Certificate[] certs, String authType) {
					}
					public void checkServerTrusted(
							java.security.cert.X509Certificate[] certs, String authType) {
					}
				}
		};
		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
		}
		subscribe(fAddress);
		/*###########################################################################*/		

		if (periodIndex >=1 &&  slashIndex >= 0 
				&& slashIndex < fAddress.length()-1)
		{
			fileUrl(fAddress,fileName,destinationDir);
		}
		else {
			System.err.println("path or file name.");
		}
	}
	/*
	 * Olewa błąd niezgodności hostname w certyfikacie - wyżej wywołanie funkcji subscribe()
	 */
	@SuppressWarnings({ "deprecation" })
	public static String subscribe(String urlString) throws Exception{
		String resp = "";
		URL url;
		URLConnection urlConn;
		DataInputStream input;
		String str = "";
		try {


			// change proxy settings if required and enable the below lines
			// sysProperties.put("proxyHost", "proxy.starhub.net.sg");
			// sysProperties.put("proxyPort", "8080");
			// sysProperties.put("proxySet",  "true");

			// Now you are telling the JRE to ignore the hostname
			HostnameVerifier hv = new HostnameVerifier()
			{

				@Override
				public boolean verify(String urlHostName, SSLSession session)
				{
					System.out.println("Warning: URL Host: " + urlHostName + " vs. "
							+ session.getPeerHost());
					return true;
				}



			};
			// Now you are telling the JRE to trust any https server. 
			// If you know the URL that you are connecting to then this should not be a problem
			trustAllHttpsCertificates();
			HttpsURLConnection.setDefaultHostnameVerifier(hv);

			url = new URL(urlString);
			urlConn = url.openConnection();
			urlConn.setDoInput(true);
			urlConn.setUseCaches(false);

			urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			input = new DataInputStream(urlConn.getInputStream());

			while (null != ((str = input.readLine()))){
				if (str.length() >0){
					str = str.trim();
					if(!str.equals("")){
						//System.out.println(str);
						resp += str;
					}
				}
			}
			input.close();
		}catch(MalformedURLException mue){ mue.printStackTrace();}
		catch(IOException ioe){ ioe.printStackTrace();}

		return resp;
	}

	// Just add these two functions in your program 

	public static class miTM implements javax.net.ssl.TrustManager,
	javax.net.ssl.X509TrustManager
	{
		public java.security.cert.X509Certificate[] getAcceptedIssuers()
		{
			return null;
		}

		public boolean isServerTrusted(
				java.security.cert.X509Certificate[] certs)
		{
			return true;
		}

		public boolean isClientTrusted(
				java.security.cert.X509Certificate[] certs)
		{
			return true;
		}

		public void checkServerTrusted(
				java.security.cert.X509Certificate[] certs, String authType)
						throws java.security.cert.CertificateException
						{
			return;
						}

		public void checkClientTrusted(
				java.security.cert.X509Certificate[] certs, String authType)
						throws java.security.cert.CertificateException
						{
			return;
						}
	}


	private static void trustAllHttpsCertificates() throws Exception
	{

		//  Create a trust manager that does not validate certificate chains:

		javax.net.ssl.TrustManager[] trustAllCerts =

				new javax.net.ssl.TrustManager[1];

		javax.net.ssl.TrustManager tm = new miTM();

		trustAllCerts[0] = tm;

		javax.net.ssl.SSLContext sc =

				javax.net.ssl.SSLContext.getInstance("SSL");

		sc.init(null, trustAllCerts, null);

		javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(

				sc.getSocketFactory());

	}


}