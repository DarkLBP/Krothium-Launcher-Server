package kml;

import kml.matchers.CheckServerMatcher;
import kml.matchers.HasJoinedMatcher;
import kml.matchers.URLMatcher;

/**
 * @author DarkLBP
 * website https://krothium.com
 */

public class Constants {
    public static final int KERNEL_BUILD = 1;
    public static final String KERNEL_BUILD_NAME = "1.0.0";
    public static final URLMatcher[] HTTP_MATCHERS = {new CheckServerMatcher()};
    public static final URLMatcher[] HTTPS_MATCHERS = {new HasJoinedMatcher()};
}