/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservaCanchas.suites.agregarCancha;

import net.sf.sahi.client.Browser;
import org.testng.annotations.*;
import reservaCanchas.common.Config;
import reservaCanchas.common.DDT;
import reservaCanchas.features.menu.TopMenuFeature;
import reservaCanchas.features.reservaCancha.AgregarCanchaFeature;

/**
 *
 * @author Khronos
 */
public class AgregarCanchaTest {

    private Browser browser;
    private AgregarCanchaFeature agregarCancha;
    
    @Test(dataProvider = "agregarCancha")
    public void AgregarCanchaTest(String nombreTestCase, String nombreCancha,
            String pathImagen, String precioHora, String tipoCancha,
            String tipoSuelo, String horaInicio, String horaFin, String resultado) {
        if (resultado.equalsIgnoreCase("Agregado")) {
            if(pathImagen.isEmpty())
                agregarCancha.agregarCancha(nombreCancha, precioHora,
                    tipoCancha, tipoSuelo, horaInicio, horaFin);
            else
                agregarCancha.agregarCancha(nombreCancha, pathImagen, precioHora,
                    tipoCancha, tipoSuelo, horaInicio, horaFin);

        }
    }

    @DataProvider(name = "agregarCancha")
    public static Object[][] data() {
        return DDT.DDTReaderFull("DDT/AgregarCancha/AgregarCancha.csv");
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        this.browser = Config.getBrowser();
        TopMenuFeature topMenu = new TopMenuFeature(browser);
        agregarCancha = topMenu.gotoCanchas();
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        //browser.close();
    }
}
