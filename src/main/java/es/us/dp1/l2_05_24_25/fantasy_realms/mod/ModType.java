package es.us.dp1.l2_05_24_25.fantasy_realms.mod;


/**
 * Enumeración que define los tipos de modificadores (Mods) en el juego.
 * 
 * Cada tipo representa un comportamiento o efecto que puede aplicar un modificador a las cartas 
 * y está asociado con una prioridad que define el orden en que se deben procesar los modificadores.
 * 
 */
public enum ModType {
    PENALTY(8),
    BONUS(7),
    CLEAR(3),
    BLANK(4),
    STATE(2),
    ALLHAND(6),
    PENALTY_AND_BLANK(5),
    NECROMANCER(1);

    private int priority;

    /**
     * Constructor para los tipos de modificadores sin una prioridad explícita.
     * Asigna la menor prioridad posible.
     */
    ModType() {
        this.priority = Integer.MAX_VALUE; // menor prioridad
    }

    /**
     * Constructor para los tipos de modificadores con una prioridad definida.
     * 
     * @param priority Prioridad del modificador (valor más bajo significa mayor prioridad).
     */
    ModType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

}


