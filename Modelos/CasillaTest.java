package modelo;

import excepciones.CasillaYaDescubiertaException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas unitarias para Casilla y CasillaMina.
 * Aplica TDD: verificación de encapsulamiento y polimorfismo.
 */
public class CasillaTest {

    // --- Casilla base ---

    @Test
    public void testCasillaIniciaNoDescubierta() {
        Casilla c = new Casilla(0, 0);
        assertFalse(c.estaDescubierto());
    }

    @Test
    public void testCasillaIniciaNoMarcada() {
        Casilla c = new Casilla(0, 0);
        assertFalse(c.estaMarcada());
    }

    @Test
    public void testCasillaNormalNoTieneMina() {
        Casilla c = new Casilla(3, 5);
        assertFalse(c.tieneMina());
    }

    @Test
    public void testDescubrirCasillaLaMarcaComoDescubierta() throws CasillaYaDescubiertaException {
        Casilla c = new Casilla(1, 1);
        c.descubrir();
        assertTrue(c.estaDescubierto());
    }

    @Test(expected = CasillaYaDescubiertaException.class)
    public void testDescubrirDosVecesLanzaExcepcion() throws CasillaYaDescubiertaException {
        Casilla c = new Casilla(2, 2);
        c.descubrir();
        c.descubrir();
    }

    @Test
    public void testToggleMarcaFunciona() {
        Casilla c = new Casilla(0, 0);
        c.toggleMarca();
        assertTrue(c.estaMarcada());
        c.toggleMarca();
        assertFalse(c.estaMarcada());
    }

    @Test
    public void testNoSePuedeMarcarcasillaDescubierta() throws CasillaYaDescubiertaException {
        Casilla c = new Casilla(0, 0);
        c.descubrir();
        c.toggleMarca();
        assertFalse(c.estaMarcada()); // No debe marcarse si ya está descubierta
    }

    @Test
    public void testSimboloCubiertaEsCuadro() {
        Casilla c = new Casilla(0, 0);
        assertEquals("■", c.getSimbolo());
    }

    @Test
    public void testSimboloDescubiertaVaciaEsPunto() throws CasillaYaDescubiertaException {
        Casilla c = new Casilla(0, 0);
        c.setMinasCercanas(0);
        c.descubrir();
        assertEquals("·", c.getSimbolo());
    }

    @Test
    public void testSimboloConMinasCercanasEsNumero() throws CasillaYaDescubiertaException {
        Casilla c = new Casilla(0, 0);
        c.setMinasCercanas(3);
        c.descubrir();
        assertEquals("3", c.getSimbolo());
    }

    // --- CasillaMina ---

    @Test
    public void testCasillaMinaTieneMina() {
        CasillaMina cm = new CasillaMina(0, 0);
        assertTrue(cm.tieneMina());
    }

    @Test
    public void testCasillaMinaSimboloReveladaEsExplosion() throws CasillaYaDescubiertaException {
        CasillaMina cm = new CasillaMina(0, 0);
        cm.descubrir();
        assertEquals("✸", cm.getSimbolo());
    }

    @Test
    public void testPolimorfismoCasilla() {
        Casilla normal = new Casilla(0, 0);
        Casilla mina   = new CasillaMina(1, 1);
        assertFalse(normal.tieneMina());
        assertTrue(mina.tieneMina());
    }
}
