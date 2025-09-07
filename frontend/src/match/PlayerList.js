import React, { useState, useEffect } from 'react';
import tokenService from '../services/token.service';
import './PlayerList.css';

const jwt = tokenService.getLocalAccessToken();

export default function PlayerList({
  players,
  currentTurn,
  matchId,
  currentPlayerUsername,
  inScoringPhase
}) {

  const [timeRemaining, setTimeRemaining] = useState(60);


  const [activePlayer, setActivePlayer] = useState(currentTurn?.username);

  useEffect(() => {
    if (currentTurn?.username !== activePlayer) {
      setActivePlayer(currentTurn?.username);
      setTimeRemaining(60); 
    }
  }, [currentTurn, activePlayer]);

  useEffect(() => {
    if (inScoringPhase) return;

    const fetchTimeLeft = async () => {
      try {
        const response = await fetch(`/api/v1/turns/${matchId}/timeLeft`, {
          headers: {
            Authorization: `Bearer ${jwt}`
          }
        });
        if (!response.ok) {
          throw new Error(`Error HTTP: ${response.status}`);
        }
        const data = await response.json();
        setTimeRemaining(data);
      } catch (error) {
        console.error('Error al obtener el tiempo restante:', error);
      }
    };

    fetchTimeLeft();

    const intervalId = setInterval(fetchTimeLeft, 1000);

    return () => clearInterval(intervalId);
  }, [matchId, inScoringPhase]);

  if (!players || players.length === 0) {
    return <p>No hay jugadores disponibles.</p>;
  }

  return (
    <div className="player-list-container">
      <ul>
        {players.map((player, index) => {
          const isTurnPlayer = player.username === currentTurn?.username;
          const colorClass = inScoringPhase ? 'blue' : '';

          return (
            <li key={index} className="player-list-item">
              <span className="player-list-username">{player.username}</span>
              {isTurnPlayer && (
                <span className={`player-list-turn ${colorClass}`}>
                  {inScoringPhase ? 'Turno Especial' : timeRemaining > 0 ? `${timeRemaining}s` : 'Tiempo agotado'}
                </span>
              )}
            </li>
          );
        })}
      </ul>
    </div>
  );
}
