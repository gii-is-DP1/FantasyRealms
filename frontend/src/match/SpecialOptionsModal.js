import React, { useState } from 'react';
import './SpecialOptionsModal.css';
import ChangeBookOptions from './SpecialModals/ChangeBookOptions';
import LifeSourceOptions from './SpecialModals/LifeSourceOptions';
import IslandOptions from './SpecialModals/IslandOptions';
import DoppelgangerOptions from './SpecialModals/DoppelgangerOptions';
import NecromancerOptions from './SpecialModals/NecromancerOptions';
import MirageOptions from './SpecialModals/MirageOptions';
import ShapeShifterOptions from './SpecialModals/ShapeShifterOptions';

function SpecialOptionsModal({ specialCards, currentPlayer, discardPile, allCards, onClose, matchId, jwt }) {
  const [cardDecisions, setCardDecisions] = useState(
    specialCards.reduce((acc, card) => {
      acc[card.id] = {};
      return acc;
    }, {})
  );
  const [isModalVisible, setIsModalVisible] = useState(true);

  const handleInputChange = (cardId, field, value) => {
    setCardDecisions(prevDecisions => ({
      ...prevDecisions,
      [cardId]: {
        ...prevDecisions[cardId],
        [field]: value,
      },
    }));
  };

  const handleSubmit = async () => {
    console.log("Submit iniciado");
    try {
      const decisionsBody = Object.entries(cardDecisions).map(([cardId, decision]) => {
        const card = specialCards.find(card => card.id === parseInt(cardId, 10));
  
        
        const validCards = currentPlayer.playerHand.filter(handCard => {
          if (card.name === "Fuente de la Vida") {
            return (
              ["ARMA", "INUNDACION", "LLAMA", "TIERRA", "AIRE"].includes(handCard.cardType) &&
              handCard.id !== card.id 
            );
          }
          if (card.name === "Isla") {
            return (
              ["INUNDACION", "LLAMA"].includes(handCard.cardType) &&
              handCard.id !== card.id 
            );
          }
          if (card.name === "Doppelganger") {
            return handCard.id !== card.id; 
          }
          return true;
        });
  
        
        if (!decision.targetCard || validCards.length === 0) {
          decision.targetCard = null;
        }
  
        return {
          modId: card.modsDTO[0].id,
          cardId: decision.targetCard ? parseInt(decision.targetCard, 10) : null,
          cardType: decision.targetCardType || null,
        };
      }).filter(Boolean); 
  
      console.log("Datos enviados:", JSON.stringify({ decisions: decisionsBody }, null, 2));
  
      const response = await fetch(`/api/v1/turns/${matchId}/specialTurn`, {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${jwt}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ decisions: decisionsBody }),
      });
  
      if (!response.ok) {
        throw new Error(`Error ${response.status}: ${response.statusText}`);
      }
  
      console.log("Decisiones enviadas exitosamente.");
      setIsModalVisible(false);
      onClose();
    } catch (error) {
      console.error("Error al enviar las decisiones:", error);
      alert(error.message);
    }
  };  
  
  

  return (
    <>
      {isModalVisible ? (
        <div className="special-modal-overlay">
          <div className="special-modal-content">
            <h2>Opciones Especiales</h2>
            <div className="special-cards-container">
              {specialCards.map(card => (
                <div key={card.id} className="special-card-options">
                  <h3>{card.name}</h3>
                  {card.name === "Libro de los Cambios" && (
                    <ChangeBookOptions
                      card={card}
                      decisions={cardDecisions}
                      handleInputChange={handleInputChange}
                      playerHand={currentPlayer.playerHand}
                    />
                  )}
                  {card.name === "Fuente de la Vida" && (
                    <LifeSourceOptions
                      card={card}
                      decisions={cardDecisions}
                      handleInputChange={handleInputChange}
                      playerHand={currentPlayer.playerHand}
                    />
                  )}
                  {card.name === "Necromancer" && (
                    <NecromancerOptions
                      card={card}
                      decisions={cardDecisions}
                      handleInputChange={handleInputChange}
                      discardPile={discardPile}
                    />
                  )}
                  {card.name === "Espejismo" && (
                    <MirageOptions
                      card={card}
                      decisions={cardDecisions}
                      handleInputChange={handleInputChange}
                      allCards={allCards}
                    />
                  )}
                  {card.name === "Cambiaformas" && allCards && (
                    <ShapeShifterOptions
                      card={card}
                      decisions={cardDecisions}
                      handleInputChange={handleInputChange}
                      allCards={allCards}
                    />
                  )}
                  {card.name === "Isla" && (
                    <IslandOptions
                      card={card}
                      decisions={cardDecisions}
                      handleInputChange={handleInputChange}
                      playerHand={currentPlayer.playerHand}
                    />
                  )}
                  {card.name === "Doppelganger" && (
                    <DoppelgangerOptions
                      card={card}
                      decisions={cardDecisions}
                      handleInputChange={handleInputChange}
                      playerHand={currentPlayer.playerHand}
                    />
                  )}
                </div>
              ))}
            </div>
            <div className="special-modal-buttons">
              <button onClick={handleSubmit} className="special-confirm-button">
                Confirmar
              </button>
              <button onClick={() => setIsModalVisible(false)} className="special-close-button">
                Cerrar
              </button>
            </div>
          </div>
        </div>
      ) : (
        <div style={{ textAlign: 'center', marginTop: '20px' }}>
          <button onClick={() => setIsModalVisible(true)} className="special-modal-reopen-button">
            Reabrir Opciones Especiales
          </button>
        </div>
      )}
    </>
  );
}

export default SpecialOptionsModal;
