import React from 'react';
import { Button } from 'reactstrap';
import './HoverButton.css';
import tokenService from '../services/token.service';

const CardModal = ({
  selectedCard,
  closeModal,
  currentPlayer,
  currentTurn,
  matchId,
}) => {
  if (!selectedCard) return null;

  const canDiscard =
    currentTurn?.username === currentPlayer?.username &&
    currentTurn.drawSource !== "NONE" &&
    !currentTurn?.discarded;

  const handleDiscardCard = async () => {
    try {
      const response = await fetch(
        `/api/v1/players/discard/${matchId}/${selectedCard.id}`,
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${tokenService.getLocalAccessToken()}`,
          },
        }
      );

      if (response.ok) {
        console.log('Carta descartada con éxito');
        //alert(`Has descartado la carta: ${selectedCard.name}`);
        closeModal();
      } else {
        console.error('Error al descartar carta:', response.statusText);
        alert('No se pudo descartar la carta. Intenta nuevamente.');
      }
    } catch (err) {
      console.error('Error al realizar la solicitud:', err);
      alert('Error de red. Intenta nuevamente.');
    }
  };

  return (
    <div
      onClick={closeModal}
      style={{
        position: 'fixed',
        top: 0,
        left: 0,
        width: '100vw',
        height: '100vh',
        backgroundColor: 'rgba(0, 0, 0, 0.5)',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        zIndex: 1000,
      }}
    >
      <div
        onClick={(e) => e.stopPropagation()}
        style={{
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          padding: '20px',
          borderRadius: '10px',
        }}
      >
        <img
          src={`/Cartas/${selectedCard.image}`}
          alt={selectedCard.name}
          style={{
            width: '500px',
            height: '670px',
            objectFit: 'cover',
            marginBottom: '20px',
          }}
        />
        {canDiscard && (
          <Button
            className="hover-button"
            color="danger"
            onClick={handleDiscardCard}
          >
            Descartar
          </Button>
        )}
      </div>
    </div>
  );
};

export default CardModal;
