import React from 'react';

function DoppelgangerOptions({ card, decisions, handleInputChange, playerHand }) {
  const validCards = playerHand.filter(handCard => handCard.id !== card.id);

  return (
    <div>
      <label>
        Seleccionar carta objetivo para copiar propiedades:
        <select
          value={decisions[card.id]?.targetCard || ''}
          onChange={e => handleInputChange(card.id, 'targetCard', e.target.value)}
          disabled={validCards.length === 0}
        >
          <option value="">Seleccionar carta</option>
          {validCards.map(handCard => (
            <option key={handCard.id} value={handCard.id}>
              {handCard.name}
            </option>
          ))}
        </select>
      </label>
      {validCards.length === 0 && (
        <p>No hay cartas válidas disponibles para copiar.</p>
      )}
    </div>
  );
}

export default DoppelgangerOptions;
