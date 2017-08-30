package kml;

import kml.handlers.URLHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.*;

/**
 * @author DarkLBP
 * website https://krothium.com
 */
public class StubLauncher {
    public static void load(File f, String[] args){
        System.out.println("Krothium Server Launcher " + Constants.KERNEL_BUILD_NAME);
        System.out.println("Using custom HTTPS certificate checker? | " + Utils.ignoreHTTPSCert());
        File usingFile = f;
        if (!usingFile.exists()){
            System.out.println("Specified file " + usingFile.getAbsolutePath() + " does not exist!");
        } else {
            System.out.println("Launching: " + usingFile.getAbsolutePath());
            try{
                JarFile jar = new JarFile(usingFile);
                Enumeration<JarEntry> entries = jar.entries();
                boolean RSAProtection = false;
                while (entries.hasMoreElements()){
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().contains("META-INF") && entry.getName().contains(".RSA")){
                        RSAProtection = true;
                    }
                }
                Attributes atrb = jar.getManifest().getMainAttributes();
                if (atrb.containsKey(Attributes.Name.MAIN_CLASS)){
                    if (RSAProtection){
                        System.out.println("JAR IS PROTECTED!");
                        File outJar = new File(Utils.getWorkingDirectory() + File.separator + "stub_unprotected.jar");
                        JarInputStream in = new JarInputStream(new FileInputStream(f));
                        JarOutputStream out = new JarOutputStream(new FileOutputStream(outJar));
                        JarEntry entry;
                        while ((entry = in.getNextJarEntry()) != null){
                            if (entry.getName().contains("META-INF") && entry.getName().contains(".RSA")){
                                continue;
                            }
                            out.putNextEntry(entry);
                            byte[] buffer = new byte[8192];
                            int read;
                            while ((read = in.read(buffer)) != -1){
                                out.write(buffer, 0, read);
                            }
                            in.closeEntry();
                            out.closeEntry();
                        }
                        in.close();
                        out.close();
                        usingFile = outJar;
                    }
                    URL.setURLStreamHandlerFactory(new URLHandler());
                    URLClassLoader classLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();
                    Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                    method.setAccessible(true);
                    method.invoke(classLoader, usingFile.toURI().toURL());
                    Class<?> cl = Class.forName(atrb.getValue(Attributes.Name.MAIN_CLASS));
                    Method meth = cl.getMethod("main", String[].class);
                    meth.invoke(null, (Object) args);
                } else {
                    System.out.println(f.getAbsolutePath() + " does not have a Main Class!");
                }
            } catch (Exception ex){
                System.out.println("Failed to start the stub.");
                ex.printStackTrace();
            }
        }
    }
}
