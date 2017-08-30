package kml;

import kml.enums.OS;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.net.URL;
import java.security.cert.X509Certificate;

/**
 * @author DarkLBP
 * website https://krothium.com
 */

public class Utils {
    public static boolean ignoreHTTPSCert(){
        try {
            SSLContext t = SSLContext.getInstance("SSL");
            t.init(null, new X509TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) {}
                public void checkServerTrusted(X509Certificate[] chain, String authType) {}
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }}, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(t.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((s, sslSession) -> true);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    public static OS getPlatform(){
        final String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")){
            return OS.WINDOWS;
        }else if (osName.contains("mac")){
            return OS.OSX;
        }else if (osName.contains("linux") || osName.contains("unix")){
            return OS.LINUX;
        }
        return OS.UNKNOWN;
    }
    public static File getWorkingDirectory(){
        final String userHome = System.getProperty("user.home", ".");
        File workingDirectory;
        switch (getPlatform()){
            case LINUX:
                workingDirectory = new File(userHome, ".minecraft/");
                break;
            case WINDOWS:
                final String applicationData = System.getenv("APPDATA");
                final String folder = (applicationData != null) ? applicationData : userHome;
                workingDirectory = new File(folder, ".minecraft/");
                break;
            case OSX:
                workingDirectory = new File(userHome, "Library/Application Support/minecraft");
                break;
            default:
                workingDirectory = new File(userHome, "minecraft/");
        }
        return workingDirectory;
    }
    public static URL stringToURL(String url){
        try{
            return new URL(url);
        }
        catch(Exception ex){
            return null;
        }
    }
}
