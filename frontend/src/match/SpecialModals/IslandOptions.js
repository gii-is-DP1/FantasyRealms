import React from 'react';

function IslandOptions({ card, decisions, handleInputChange, playerHand }) {
  // Filtrar cartas válidas excluyendo "Isla" a sí misma
  const validCards = playerHand.filter(
    handCard =>
      ["INUNDACION", "LLAMA"].includes(handCard.cardType) &&
      handCard.id !== card.id // Excluir la carta actual
  );

  const handleChange = (e) => {
    const value = e.target.value;
    handleInputChange(card.id, 'targetCard', value || null); // Guarda null si no se selecciona nada
  };

  return (
    <div>
      <label>
        Seleccionar carta de Agua o Fuego para ignorar penalización:
        <select
          value={decisions[card.id]?.targetCard || ''}
          onChange={handleChange}
          disabled={validCards.length === 0} // Desactivar si no hay cartas válidas
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

export default IslandOptions;
