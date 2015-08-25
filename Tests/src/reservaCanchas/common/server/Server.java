package reservaCanchas.common.server;

/**
 * Clase Server, define la URL que va a ser utilizada por SAHI como página de inicio
 * 
 * @date 24/08/2015
 * @author Khronos
 */
public class Server {
    private String url;
    /**
     * Crea una nueva instancia del objeto Server
     * @param url URL del Servidor
     */
    public Server(String url){
        this.url = url;
    }
    /**
     * Retorna La URL actual usada por este servidor
     * @return La URL de la pagina de inicio del servidor
     */
    public String getUrl() {
        return url;
    }

    /**
     * Configura la URL de la pagina de inicio de este servidor
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    /**
     * Esta funcion crea la url para el servidor utlizando el protocolo http
     * @return el Path URL para el servidor usando una coneccion http
     */
    @Override
    public String toString() {
        return "http://" + url;
    }
}
