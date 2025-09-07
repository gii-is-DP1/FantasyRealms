import React from 'react';

function NecromancerOptions({ card, decisions, handleInputChange, discardPile }) {
  // Filtrar las cartas compatibles
  const compatibleCards = discardPile.filter(discardCard =>
    ['EJERCITO', 'LIDER', 'MAGO', 'BESTIA'].includes(discardCard.cardType)
  );

  return (
    <div>
      <label>
        Seleccionar carta del descarte para añadir a la mano:
        {compatibleCards.length > 0 ? (
          <select
            value={decisions[card.id]?.targetCard || ''}
            onChange={e => handleInputChange(card.id, 'targetCard', e.target.value)}
          >
            <option value="">Seleccionar carta</option>
            {compatibleCards.map(discardCard => (
              <option key={discardCard.id} value={discardCard.id}>
                {discardCard.name} ({discardCard.cardType})
              </option>
            ))}
          </select>
        ) : (
          <p>No hay cartas compatibles en el descarte.</p>
        )}
      </label>
    </div>
  );
}

export default NecromancerOptions;
