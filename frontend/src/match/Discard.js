import React, { useEffect, useState } from 'react';
import CardModalDiscard from './CardModalDiscard';

const Discard = ({ discardedCards, currentPlayer, currentTurn, matchId }) => {
  const [marginLeft, setMarginLeft] = useState('0vw');
  const [selectedCard, setSelectedCard] = useState(null);

  const updateSolapamiento = () => {
    const screenWidth = window.innerWidth;
    let overlap;

    if (screenWidth < 600) {
      overlap = '-15vw';
    } else if (screenWidth < 1000) {
      overlap = '-20vw';
    } else {
      overlap = '-5vw';
    }

    setMarginLeft(overlap);
  };

  useEffect(() => {
    updateSolapamiento();
    window.addEventListener('resize', updateSolapamiento);

    return () => window.removeEventListener('resize', updateSolapamiento);
  }, []);

  const handleCardClick = (card) => {
    setSelectedCard(card);
  };

  const closeModal = () => {
    setSelectedCard(null);
  };

  return (
    <div
      style={{
        position: 'relative',
        display: 'flex',
        overflowX: 'auto',
        gap: '0px',
        maxWidth: '100%', 
        height: '25vh',
      }}
    >
      {discardedCards && discardedCards.length > 0 && (
        discardedCards.map((card, index) => (
          <div
            key={index}
            onClick={() => handleCardClick(card)}
            style={{
              position: 'relative',
              width: '10vw',
              height: '25vh',
              marginLeft: index !== 0 ? '-5vw' : '0', 
              zIndex: index,
              cursor: 'pointer',
            }}
          >
            <img
              src={`/Cartas/${card.image}`}
              alt={card.name}
              style={{
                width: '100%',
                height: '100%',
                objectFit: 'fill',
                borderRadius: '5px',
              }}
            />
          </div>
        ))
      )}
      <CardModalDiscard
        selectedCard={selectedCard}
        closeModal={closeModal}
        currentPlayer={currentPlayer}
        currentTurn={currentTurn}
        matchId={matchId}
      />
    </div>
  );  
};

export default Discard;
