import React from 'react';

function MirageOptions({ card, decisions, handleInputChange, allCards }) {
  return (
    <div>
      <label>
        Seleccionar carta objetivo para copiar nombre y tipo:
        <select
          value={decisions[card.id]?.targetCard || ''}
          onChange={e => handleInputChange(card.id, 'targetCard', e.target.value)}
        >
          <option value="">Seleccionar carta</option>
          {allCards
            .filter(
              targetCard =>
                ['EJERCITO', 'TIERRA', 'TIEMPO', 'INUNDACION', 'LLAMA'].includes(targetCard.cardType)
            )
            .map(targetCard => (
              <option key={targetCard.id} value={targetCard.id}>
                {targetCard.name} ({targetCard.cardType})
              </option>
            ))}
        </select>
      </label>
    </div>
  );
}

export default MirageOptions;