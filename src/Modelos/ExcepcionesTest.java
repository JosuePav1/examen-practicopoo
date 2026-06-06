package modelo;

import excepciones.CasillaYaDescubiertaException;
import excepciones.CoordenadasInvalidasException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas unitarias para las excepciones personalizadas del juego.
 * Aplica TDD: se prueba el comportamiento ante entradas inválidas.
 */
public class ExcepcionesTest {

    @Test
    public void testCasillaYaDescubiertaContieneMensaje() {
        CasillaYaDescubiertaException ex = new CasillaYaDescubiertaException(0, 4);
        assertNotNull(ex.getMessage());
        assertTrue(ex.getMessage().contains("A5"));
    }

    @Test
    public void testCasillaYaDescubiertaGuardaCoordenadas() {
        CasillaYaDescubiertaException ex = new CasillaYaDescubiertaException(2, 7);
        assertEquals(2, ex.getFila());
        assertEquals(7, ex.getColumna());
    }

    @Test
    public void testCoordenadasInvalidasContieneCoordenada() {
        CoordenadasInvalidasException ex = new CoordenadasInvalidasException("Z99");
        assertNotNull(ex.getMessage());
        assertEquals("Z99", ex.getCoordenada());
    }

    @Test
    public void testCoordenadasInvalidasMensajeDescriptivo() {
        CoordenadasInvalidasException ex = new CoordenadasInvalidasException("XYZ");
        assertTrue(ex.getMessage().toLowerCase().contains("inválida") ||
                   ex.getMessage().toLowerCase().contains("invalida"));
    }
}
