import React from 'react';

function ChangeBookOptions({ card, decisions, handleInputChange, playerHand }) {
  // Obtener la carta objetivo seleccionada
  const targetCard = playerHand.find(handCard => handCard.id === parseInt(decisions[card.id]?.targetCard, 10));
  const targetCardType = decisions[card.id]?.targetCardType;

  // Comprobar si el tipo seleccionado es el mismo que el tipo de la carta
  const isSameType = targetCard && targetCardType && targetCard.cardType === targetCardType;

  const handleCardChange = (e) => {
    const value = e.target.value;
    handleInputChange(card.id, 'targetCard', value || null); // Guardar null si no se selecciona nada
  };

  const handleTypeChange = (e) => {
    const value = e.target.value;
    handleInputChange(card.id, 'targetCardType', value || null); // Guardar null si no se selecciona nada
  };

  return (
    <div>
      <label>
        Seleccionar carta objetivo para cambiar tipo:
        <select
          value={decisions[card.id]?.targetCard || ''}
          onChange={handleCardChange}
        >
          <option value="">Seleccionar carta</option>
          {playerHand
            .filter(handCard => handCard.name !== "Libro de los Cambios") // Excluir "Libro de los Cambios"
            .map(handCard => (
              <option key={handCard.id} value={handCard.id}>
                {handCard.name}
              </option>
            ))}
        </select>
      </label>
      <label>
        Seleccionar tipo de carta:
        <select
          value={decisions[card.id]?.targetCardType || ''}
          onChange={handleTypeChange}
        >
          <option value="">Seleccionar tipo</option>
          {[
            'MAGO', 'BESTIA', 'EJERCITO', 'LIDER',
            'INUNDACION', 'ARMA', 'LLAMA', 'SALVAJE',
            'TIERRA', 'TIEMPO', 'ARTEFACTO',
          ].map(type => (
            <option key={type} value={type}>
              {type}
            </option>
          ))}
        </select>
      </label>
      {isSameType && (
        <p style={{ color: 'red', marginTop: '10px' }}>
          No puedes cambiar una carta al tipo que ya es.
        </p>
      )}
    </div>
  );
}

export default ChangeBookOptions;
