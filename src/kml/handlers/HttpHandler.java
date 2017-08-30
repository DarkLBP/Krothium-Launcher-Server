package kml.handlers;

import kml.Constants;
import kml.matchers.URLMatcher;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author DarkLBP
 * website https://krothium.com
 */
class HttpHandler extends sun.net.www.protocol.http.Handler{

    @Override
    protected URLConnection openConnection(URL url) throws IOException {
        return openConnection(url, null);
    }
    
    @Override
    protected URLConnection openConnection(URL url, Proxy proxy) throws IOException {
        System.out.println("URL requested: " + url.toString());
        for (URLMatcher m : Constants.HTTP_MATCHERS){
            if (m.match(url)){
                return new ConnectionHandler(url, m);
            }
        }
        return super.openConnection(url, proxy);
    }
}
