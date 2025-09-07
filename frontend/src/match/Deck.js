import React, { useState } from 'react';
import tokenService from '../services/token.service';

const jwt = tokenService.getLocalAccessToken();

const renderCarta = (index) => {
  return (
    <div
      key={index}
      style={{
        position: 'absolute',
        left: `${index * 5}px`,
      }}
    >
      <img
        style={{
          width: '10vw',
          height: '25vh',
          borderRadius: '5px',
        }}
        src={require('../static/images/cards/reverso.png')}
        alt="mazo"
      />
    </div>
  );
};

export default function Deck({ currentPlayer, currentTurn, matchId, matchState }) {

  const isCurrentPlayerTurn =
    currentPlayer?.username === currentTurn?.username &&
    currentTurn?.drawSource === "NONE"
    && !matchState;

  const handleDrawCard = async () => {
    try {
      const response = await fetch(`/api/v1/players/drawCard/deck/${matchId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${jwt}`,
        },
      });

      if (response.ok) {
        const playerData = await response.json();
        console.log('Carta robada con éxito:', playerData);
      } else {
        console.error('Error al robar carta:', response.statusText);
      }
    } catch (err) {
      console.error('Error al realizar la solicitud:', err);
    }
  };

  return (
    <div
      style={{
        position: 'relative',
        width: '10vw',
        height: '25vh',
        objectFit: 'fill',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
      }}
    >
      <div
        style={{
          position: 'relative',
          width: '100%',
          height: '100%',
        }}
      >
        {[...Array(5)].map((_, index) => renderCarta(index))}
      </div>
      {isCurrentPlayerTurn && (
        <button
          className="hover-button"
          style={{
            position: 'absolute',
            backgroundColor: '#ba68c8',
            color: 'white',
            fontWeight: 'bold',
            padding: '10px 20px',
            border: 'none',
            borderRadius: '5px',
            cursor: 'pointer',
            zIndex: 10,
          }}
          onClick={handleDrawCard}
        >
          Robar
        </button>
      )}
    </div>
  );
}
