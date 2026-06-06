package modelo;

import excepciones.CasillaYaDescubiertaException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas unitarias para la clase Tablero.
 * Aplica TDD: las pruebas validan la lógica del modelo.
 */
public class TableroTest {

    private Tablero tablero;

    @Before
    public void setUp() {
        tablero = new Tablero();
    }

    // --- Inicialización ---

    @Test
    public void testTableroTiene10x10Casillas() {
        assertEquals(10, Tablero.FILAS);
        assertEquals(10, Tablero.COLUMNAS);
    }

    @Test
    public void testTableroTiene10Minas() {
        int minaContadas = 0;
        for (int f = 0; f < Tablero.FILAS; f++) {
            for (int c = 0; c < Tablero.COLUMNAS; c++) {
                if (tablero.getCasilla(f, c).tieneMina()) minaContadas++;
            }
        }
        assertEquals(Tablero.TOTAL_MINAS, minaContadas);
    }

    @Test
    public void testTableroIniciaConJuegoNoTerminado() {
        assertFalse(tablero.isJuegoTerminado());
    }

    @Test
    public void testTableroIniciaConCeroDescubiertas() {
        assertEquals(0, tablero.getCasillasDescubiertas());
    }

    // --- Posición válida ---

    @Test
    public void testPosicionValidaDentroDelTablero() {
        assertTrue(tablero.esPosicionValida(0, 0));
        assertTrue(tablero.esPosicionValida(9, 9));
        assertTrue(tablero.esPosicionValida(5, 5));
    }

    @Test
    public void testPosicionInvalidaFueraDelTablero() {
        assertFalse(tablero.esPosicionValida(-1, 0));
        assertFalse(tablero.esPosicionValida(0, -1));
        assertFalse(tablero.esPosicionValida(10, 0));
        assertFalse(tablero.esPosicionValida(0, 10));
    }

    // --- Descubrir casillas ---

    @Test
    public void testDescubrirCasillaSeguraAumentaContador() throws CasillaYaDescubiertaException {
        // Buscar una casilla sin mina para la prueba
        for (int f = 0; f < Tablero.FILAS; f++) {
            for (int c = 0; c < Tablero.COLUMNAS; c++) {
                if (!tablero.getCasilla(f, c).tieneMina()) {
                    int antes = tablero.getCasillasDescubiertas();
                    tablero.descubrirCasilla(f, c);
                    assertTrue(tablero.getCasillasDescubiertas() > antes);
                    return;
                }
            }
        }
    }

    @Test(expected = CasillaYaDescubiertaException.class)
    public void testDescubrirCasillaYaDescubiertaLanzaExcepcion() throws CasillaYaDescubiertaException {
        // Encontrar casilla sin mina
        for (int f = 0; f < Tablero.FILAS; f++) {
            for (int c = 0; c < Tablero.COLUMNAS; c++) {
                if (!tablero.getCasilla(f, c).tieneMina()) {
                    tablero.descubrirCasilla(f, c);
                    tablero.descubrirCasilla(f, c); // Segunda vez → excepción
                    return;
                }
            }
        }
    }

    // --- Marcar casillas ---

    @Test
    public void testMarcarCasillaActualizaMarca() {
        Casilla casilla = tablero.getCasilla(0, 0);
        assertFalse(casilla.estaMarcada());
        tablero.marcarCasilla(0, 0);
        assertTrue(casilla.estaMarcada());
        tablero.marcarCasilla(0, 0);
        assertFalse(casilla.estaMarcada());
    }

    @Test
    public void testMinasRestantesDisminuyeAlMarcar() {
        int antes = tablero.getMinasRestantes();
        tablero.marcarCasilla(0, 0);
        assertEquals(antes - 1, tablero.getMinasRestantes());
    }

    // --- Adyacencias ---

    @Test
    public void testCasillasSinMinaTienenAdyacenciaEnRango() {
        for (int f = 0; f < Tablero.FILAS; f++) {
            for (int c = 0; c < Tablero.COLUMNAS; c++) {
                Casilla cas = tablero.getCasilla(f, c);
                if (!cas.tieneMina()) {
                    assertTrue(cas.getMinasCercanas() >= 0);
                    assertTrue(cas.getMinasCercanas() <= 8);
                }
            }
        }
    }
}
