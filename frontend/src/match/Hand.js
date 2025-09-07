import React, { useEffect, useState } from 'react';
import CardModal from './CardModal';

const Hand = ({ playerHand, currentPlayer, currentTurn, matchId }) => {
  const [marginLeft, setMarginLeft] = useState('0vw');
  const [selectedCard, setSelectedCard] = useState(null);

  const updateSolapamiento = () => {
    const screenWidth = window.innerWidth;
    let overlap;

    if (screenWidth < 600) {
      overlap = '-10vw';
    } else if (screenWidth < 1000) {
      overlap = '-7vw';
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
        gap: '0px',
        height: '100%',
        alignItems: 'center',
        overflow: 'hidden',
      }}
    >
      {playerHand && playerHand.length > 0 ? (
        playerHand.map((card, index) => (
          <div
            key={index}
            onClick={() => handleCardClick(card)}
            style={{
              position: 'relative',
              width: '15vw',
              height: '35vh',
              marginLeft: index !== 0 ? marginLeft : '0vw',
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
      ) : (
        <p>No tienes cartas en tu mano.</p>
      )}
      <CardModal
        selectedCard={selectedCard}
        closeModal={closeModal}
        currentPlayer={currentPlayer}
        currentTurn={currentTurn}
        matchId={matchId}
      />
    </div>
  );
};


export default Hand;
