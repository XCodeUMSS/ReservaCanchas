package ReservaCanchas.common.Server;

/**
 * Server Class defined as an URL and the used port to be used by SAHI
 * 
 * @date 8/26/2013
 * @author Walter Ramirez
 */
public class Server {
    private String url;
    private String port;
    /**
     * Creates a new Server object instance
     * @param url Server USRL
     * @param port Server Port
     */
    public Server(String url, String port){
        this.url = url;
        this.port = port;
    }

    /**
     * Returns the current port used by this Server
     * @return port
     */
    public String getPort() {
        return port;
    }

    /**
     * Sets the port value to the Current Server
     * @param port
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * Returns thecurrent URL used by this Server
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL value to this Server
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    /**
     * This function creates the URL for this server using the http protocol
     * @return The url path of this Server using a http connection
     */
    @Override
    public String toString() {
        return "http://" + url + ":" + port;
    }
}
