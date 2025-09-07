import React from 'react';

function LifeSourceOptions({ card, decisions, handleInputChange, playerHand }) {
  // Filtrar cartas válidas excluyendo "Fuente de la Vida"
  const validCards = playerHand.filter(
    handCard =>
      ["ARMA", "INUNDACION", "LLAMA", "TIERRA", "AIRE"].includes(handCard.cardType) &&
      handCard.id !== card.id // Excluir la carta "Fuente de la Vida"
  );

  return (
    <div>
      <label>
        Seleccionar carta objetivo para sumar fuerza base:
        <select
          value={decisions[card.id]?.targetCard || ''}
          onChange={e => handleInputChange(card.id, 'targetCard', e.target.value)}
          disabled={validCards.length === 0} // Deshabilitar si no hay cartas válidas
        >
          <option value="">Seleccionar carta</option>
          {validCards.map(handCard => (
            <option key={handCard.id} value={handCard.id}>
              {handCard.name}
            </option>
          ))}
        </select>
      </label>
      {validCards.length === 0 && <p>No hay cartas válidas en tu mano.</p>}
    </div>
  );
}

export default LifeSourceOptions;
