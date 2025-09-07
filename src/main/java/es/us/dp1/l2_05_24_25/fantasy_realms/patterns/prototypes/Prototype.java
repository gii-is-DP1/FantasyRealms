package es.us.dp1.l2_05_24_25.fantasy_realms.patterns.prototypes;

import org.jpatterns.gof.PrototypePattern;

/**
 * Interfaz genérica para el Patrón Prototype.
 * Cada clase que sea clonable (ConcretePrototype) implementará este contrato.
 */
@PrototypePattern
public interface Prototype<T> {

    /**
     * Método que devuelve una copia del objeto (clon).
     * @return un clon del objeto que implementa esta interfaz
     */
    @PrototypePattern.Operation
    T getClone();
}
