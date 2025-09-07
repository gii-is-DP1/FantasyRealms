import React, { useState, useEffect, useCallback, useRef } from 'react';
import BackgroundContainer from "../components/background/BackgroundContainer";
import Section from '../components/Section';
import CenteredCard from '../components/background/CenteredCard';
import '../static/css/profile/profile.css';
import PlayerList from './PlayerList';
import getIdFromUrl from '../util/getIdFromUrl';
import tokenService from "../services/token.service";
import Chat from './Chat';
import Hand from './Hand';
import Deck from './Deck';
import Discard from './Discard';
import useFetchState from '../util/useFetchState';
import SpecialOptionsModal from './SpecialOptionsModal';
import { useNavigate } from 'react-router-dom';

const jwt = tokenService.getLocalAccessToken();

export default function Match() {
  const navigate = useNavigate();
  const matchId = getIdFromUrl(2);
  const [matchData, setMatchData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [deck, setDeck] = useState(null);
  const [selectedPlayer, setSelectedPlayer] = useState(null);
  const [currentUser, setCurrentUser] = useFetchState(
    [],
    "/api/v1/currentuser",
    jwt
  );
  const [showSpecialOptions, setShowSpecialOptions] = useState(false);
  const [specialCardsInHand, setSpecialCardsInHand] = useState([]);
  const intervalRef = useRef(null);

  const specialCards = [
    "Fuente de la Vida",
    "Libro de los Cambios",
    "Isla",
    "Cambiaformas",
    "Necromancer",
    "Espejismo",
    "Doppelganger",
  ];

  const currentPlayer = matchData?.players?.find(
    (player) => player.username === currentUser?.username
  ) || null;

  useEffect(() => {
    if (matchData?.inProgress === false) {
      if (intervalRef.current) {
        clearInterval(null);
      }
      navigate(`/matches/${matchId}/ranking`);
    }
  }, [matchData, matchId, navigate]);
  

  useEffect(() => {
    if (!matchData?.deckId || deck) return;

    const fetchDeck = async () => {
      try {
        const response = await fetch(`/api/v1/deck/${matchData.deckId}`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        });

        if (!response.ok) {
          throw new Error(`Error ${response.status}: ${response.statusText}`);
        }

        const data = await response.json();
        setDeck(data);
      } catch (error) {
        console.error("Error al obtener los datos del mazo:", error);
        setError(error.message);
      }
    };

    fetchDeck();
  }, [matchData, deck, jwt]);

 const skipTurn = useCallback(async () => {
     try {
       const response = await fetch(`/api/v1/turns/${matchId}/specialTurn`, {
         method: 'POST',
         headers: {
           'Authorization': `Bearer ${jwt}`,
           'Content-Type': 'application/json',
         },
         body: JSON.stringify({ decisions: [] }),
       });
 
       if (!response.ok) {
         throw new Error(`Error ${response.status}: ${response.statusText}`);
       }
     } catch (error) {
       console.error('Error al pasar el turno especial:', error);
     }
   }, [matchId, jwt]);
 
   useEffect(() => {
     if (matchData?.inScoringPhase) {
       const currentTurn = matchData?.currentTurn;
       const currentPlayer = matchData?.players?.find(
         (player) => player.username === currentUser?.username
       );
 
       if (!currentTurn || !currentPlayer) {
         return;
       }
 
       const isCurrentPlayerTurn = currentPlayer.username === currentTurn.username;
       if (isCurrentPlayerTurn) {
         const specialCardsFound = currentPlayer.playerHand.filter(card =>
           specialCards.includes(card.name)
         );
 
         if (specialCardsFound.length === 0) {
           console.log("Skipping turn for player:", currentPlayer.username);
           skipTurn();
         } else {
           console.log("Showing special options for player:", currentPlayer.username);
           setSpecialCardsInHand(specialCardsFound);
           setShowSpecialOptions(true);
         }
       }
     }
   }, [
     matchData?.inScoringPhase,
     matchData?.currentTurn?.turnCount,
     currentUser?.username,
     matchData?.players,
     skipTurn,
   ]);


   useEffect(() => {
    let intervalId;
  
    const initialize = async () => {
      let role;
      try {
        const roleResponse = await fetch(`/api/v1/matches/${matchId}/player-role`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        });
  
        if (!roleResponse.ok) {
          throw new Error(`Error ${roleResponse.status}: ${roleResponse.statusText}`);
        }
  
        role = await roleResponse.text();
        console.log("Rol del jugador:", role);
      } catch (error) {
        console.error("Error al obtener el rol del jugador:", error);
        setError(error.message);
        setLoading(false);
        return; 
      }
  
      const fetchMatch = async () => {
        try {
          const endpoint = role === "ESPECTADOR"
            ? `/api/v1/matches/${matchId}`
            : `/api/v1/matches/${matchId}/private`;
  
          const response = await fetch(endpoint, {
            headers: {
              Authorization: `Bearer ${jwt}`,
            },
          });
  
          if (!response.ok) {
            throw new Error(`Error ${response.status}: ${response.statusText}`);
          }
  
          const data = await response.json();
          console.log("Datos de la partida:", data);
  
          if (data.inProgress === false) {
            if (intervalId) {
              clearInterval(intervalId);
            }
            navigate(`/matches/${matchId}/ranking`);
            return;
          }
  
          setMatchData(data);
        } catch (error) {
          console.error("Error al obtener los datos de la partida:", error);
          setError(error.message);
        } finally {
          setLoading(false);
        }
      };
  
      await fetchMatch();
      intervalId = setInterval(fetchMatch, 1000);
    };
  
    initialize();
  
    return () => {
      if (intervalId) {
        clearInterval(intervalId);
      }
    };
  }, [matchId, jwt, navigate]);
  
  
  

  useEffect(() => {
    if (
      currentPlayer?.rol === "ESPECTADOR" &&
      matchData?.players?.length > 0 &&
      !selectedPlayer
    ) {
      const firstNonSpectator = matchData.players.find(
        (player) => player.rol !== "ESPECTADOR"
      );
      setSelectedPlayer(firstNonSpectator || null);
    }
  }, [currentPlayer, matchData, selectedPlayer]);
  

  const handleSelectPlayer = (playerId) => {
    const player = matchData?.players?.find(player => player.id === playerId);
    setSelectedPlayer(player || null);
  };

  if (loading) return <p>Cargando...</p>;
  if (error) return <p>Error: {error}</p>;

  const currentTurn = matchData?.currentTurn;

  const drawable = currentPlayer && currentTurn &&
  currentPlayer.username === currentTurn.username &&
  currentTurn.drawSource === "NONE" &&
  !matchData?.inScoringPhase;

  const discardable = currentPlayer && currentTurn &&
  currentPlayer.username === currentTurn.username &&
  currentTurn.drawSource !== "NONE" &&
  !currentTurn.discarded &&
  !matchData?.inScoringPhase;

  return (
    <BackgroundContainer>
      <CenteredCard style={{ paddingTop: "0.1vh", paddingBottom: "0.5vh", height: "95vh" }}>
        <div style={{ display: 'flex', flexDirection: 'row', boxSizing: 'border-box', height: '100%' }}>
          <Section style={{ padding: "1em", flex: 9, display: 'flex', flexDirection: 'column', height: '85vh', margin: '1em' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '1em', flexDirection: 'row' }}>
              <Section style={{ paddingBottom: "20px", flex: 1, marginRight: '3em', border: drawable ? "10px solid #ba68c8" : "5px solid #4a148c" }}>
                <Deck currentPlayer={currentPlayer} currentTurn={currentTurn} matchId={matchId} matchState={matchData?.inScoringPhase}/>
              </Section>
              <Section style={{ flex: 4, border: drawable && matchData?.discard?.cards?.length > 0 ? "10px solid #ba68c8" : "5px solid #4a148c" }}>
                <Discard
                  discardedCards={matchData?.discard?.cards}
                  currentPlayer={currentPlayer}
                  currentTurn={currentTurn}
                  matchId={matchId}
                />
              </Section>
            </div>
            <Section style={{ flex: 1, border: discardable ? "10px solid #ba68c8" : "5px solid #4a148c", paddingBottom: "0.1em" }}>
              <Hand
                playerHand={currentPlayer?.rol === "ESPECTADOR" ? selectedPlayer?.playerHand : currentPlayer?.playerHand}
                currentPlayer={currentPlayer}
                currentTurn={currentTurn}
                matchId={matchId}
              />
            </Section>

            {currentPlayer?.rol === "ESPECTADOR" && (
              <div style={{ marginTop: '1em', display: 'flex', justifyContent: 'center' }}>
                <label style={{ marginRight: '1em' }}>Ver mano de:</label>
                <select
                  value={selectedPlayer?.id || ''}
                  onChange={(e) => handleSelectPlayer(Number(e.target.value))}
                  style={{ padding: '0.5em', borderRadius: '5px', border: '1px solid #ccc' }}
                >
                  <option value="" disabled>Seleccionar jugador</option>
                  {matchData?.players
                    .filter(player => player.rol !== "ESPECTADOR")
                    .map(player => (
                      <option key={player.id} value={player.id}>
                        {player.username}
                      </option>
                    ))}
                </select>
              </div>
            )}
          </Section>
          <Section style={{ flex: 2, display: 'flex', flexDirection: 'column', padding: "1em", marginTop: '1em', height: '85vh' }}>
            <div style={{ marginTop: '1em', flex: 1, display: 'flex', flexDirection: 'column' }}>
              <PlayerList
                players={(matchData?.players || []).filter(player => player.rol !== "ESPECTADOR")}
                currentTurn={currentTurn}
                matchId={matchId}
                currentPlayerUsername={currentPlayer?.username || ""}
                inScoringPhase={matchData?.inScoringPhase}
              />
            </div>
            <div style={{ flex: 2, display: 'flex', flexDirection: 'column' }}>
              <Chat playerName={currentUser?.username} avatar={currentUser?.avatar} />
            </div>
          </Section>
        </div>
        {showSpecialOptions && currentPlayer?.username === currentTurn?.username && (
          <SpecialOptionsModal
            specialCards={specialCardsInHand}
            currentPlayer={currentPlayer}
            discardPile={matchData?.discard?.cards || []}
            allCards={deck?.initialCards}
            onClose={() => setShowSpecialOptions(false)}
            matchId={matchId}
            jwt={jwt}
          />
        )}
      </CenteredCard>
    </BackgroundContainer>
  );
}
