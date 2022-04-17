package Handlers;

import java.net.URL;

public abstract class ResourceHandler {
    private static final ClassLoader cl = ClassLoader.getSystemClassLoader();

    public static URL getPlayerResource(String direction) {
        if (cl.getResource("player/"+direction+".png")!=null) {
            return cl.getResource("player/"+direction+".png");
        }
        return null;
    }
}
