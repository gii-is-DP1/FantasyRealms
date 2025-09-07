import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Button, List, ListGroupItem } from 'reactstrap';
import BackgroundContainer from '../components/background/BackgroundContainer';
import CenteredCard from '../components/background/CenteredCard';
import Section from '../components/Section';
import tokenService from '../services/token.service';
import useFetchState from '../util/useFetchState';
import './MatchDetail.css';

const jwt = tokenService.getLocalAccessToken();

export default function MatchDetail() {
  const { id } = useParams();
  const [match, setMatch] = useState(null);
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [currentUser, setCurrentUser] = useFetchState([], "/api/v1/currentuser", jwt);
  const [matchCreator, setMatchCreator] = useState(null);
  const [started, setStarted] = useState(false);
  const [friends, setFriends] = useState([]);
  const [invitedFriends, setInvitedFriends] = useState([]);
  const refreshInterval = 5000;

  useEffect(() => {
    const fetchMatchDetails = async () => {
      try {
        const token = tokenService.getLocalAccessToken();
        const response = await fetch(`/api/v1/matches/${id}`, {
          method: 'GET',
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        });
        if (!response.ok) {
          throw new Error('Error fetching match details');
        }
        const matchData = await response.json();
        if (matchData.startDate) {
          setStarted(true);
        }
        setMatch(matchData);
        setLoading(false);
      } catch (error) {
        console.error('Error fetching match details', error);
        setLoading(false);
      }
    };

    const fetchMatchCreator = async () => {
      try {
        const response = await fetch(`/api/v1/matches/${id}/creator`, {
          method: 'GET',
          headers: {
            Authorization: `Bearer ${jwt}`,
            'Content-Type': 'application/json',
          },
        });
        if (!response.ok) {
          throw new Error('Error fetching match creator');
        }
        const creatorData = await response.json();
        setMatchCreator(creatorData);
        setLoading(false);
      } catch (error) {
        console.error('Error fetching match creator', error);
        setLoading(false);
      }
    };

    fetchMatchDetails();
    fetchMatchCreator();

    const intervalId = setInterval(() => {
      fetchMatchDetails();
      fetchMatchCreator();
    }, refreshInterval);

    return () => clearInterval(intervalId);
  }, [id, refreshInterval]);

  useEffect(() => {
    const fetchFriends = async () => {
      try {
        const response = await fetch(`/api/v1/friendships/friends`, {
          method: 'GET',
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        });
        if (!response.ok) {
          throw new Error('Error al obtener la lista de amigos.');
        }
        const friendsData = await response.json();
        const filteredFriends = friendsData.filter(friend => {
          const isSender = friend.senderId === currentUser.id;
          const friendName = isSender ? friend.receiverName : friend.senderName;
          const alreadyInMatch = match.players.some(
            player => player.username === friendName
          );
          return !alreadyInMatch && !invitedFriends.includes(friendName);
        });
        setFriends(filteredFriends);
      } catch (err) {
        console.log(err.message);
      }
    };

    if (match && currentUser) {
      fetchFriends();
    }
  }, [invitedFriends, match, currentUser]);

  useEffect(() => {
    if (started && match && currentUser) {
      handleNavigateMatch();
    }
  }, [started, match, currentUser]);

  const handleJoinMatch = async () => {
    try {
      const token = tokenService.getLocalAccessToken();
      const response = await fetch(`/api/v1/matches/${id}/join`, {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });
      if (!response.ok) {
        const errorData = await response.json();
        console.log('Error joining match:', errorData);
        throw new Error('Error joining match');
      }
      const updatedMatch = await response.json();
      setMatch(updatedMatch);
    } catch (error) {
      console.error('Error joining match', error);
    }
  };

  const handleStartMatch = async () => {
    try {
      const token = tokenService.getLocalAccessToken();
      const response = await fetch(`/api/v1/matches/${id}/start`, {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });
  
      if (!response.ok) {
        
        const errorData = await response.json();
        
        const backendMessage = errorData.message || 'Error al iniciar la partida';
       
        alert(`No se pudo iniciar la partida: ${backendMessage}`);
        console.log('Error starting match:', errorData);
        return;
      }
      const updatedMatch = await response.json();
      setMatch(updatedMatch);
      setStarted(true);
    } catch (error) {
      console.error('Error starting match', error);
      alert('Ha ocurrido un error inesperado al iniciar la partida.');
    }
  };

  const handleRemovePlayer = async (userIdToRemove) => {
    try {
      const response = await fetch(`/api/v1/matches/${id}/remove-player/${userIdToRemove}`, {
        method: 'DELETE',
        headers: {
          Authorization: `Bearer ${jwt}`,
          'Content-Type': 'application/json',
        },
      });
      if (!response.ok) {
        const errorData = await response.json();
        console.error('Error removing player:', errorData);
        alert(errorData.message || 'Error al eliminar jugador');
        return;
      }

      const updatedMatch = await response.json();
      setMatch(updatedMatch);
    } catch (err) {
      console.error('Error removing player:', err);
      alert('Ha ocurrido un error al eliminar jugador.');
    }
  };  

  const handleNavigateMatch = () => {
    if (
      match &&
      currentUser &&
      match.players.some(player => player.username === currentUser.username)
    ) {
      navigate(`/matches/${id}/game`);
    }
  };

  const handleGameInvite = async receiver => {
    try {
      const response = await fetch(
        `/api/v1/game-invitation/send/${receiver}/${id}`,
        {
          method: 'POST',
          headers: {
            Authorization: `Bearer ${jwt}`,
            'Content-Type': 'application/json',
          },
        }
      );
      if (!response.ok) {
        const errorData = await response.json();
        console.log('Error inviting friends to match:', errorData);
        throw new Error('Error inviting friends to match');
      }
      setInvitedFriends(prev => [...prev, receiver]);
    } catch (error) {
      console.error('Error inviting friends to match', error);
    }
  };

  const handleToggleRole = async () => {
    try {
      const response = await fetch(`/api/v1/matches/${id}/toggle-role`, {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${jwt}`,
          'Content-Type': 'application/json',
        },
      });
      if (!response.ok) {
        const errorData = await response.json();
        console.error('Error al cambiar el rol:', errorData);
        throw new Error('Error al cambiar el rol');
      }
      const updatedMatchResponse = await fetch(`/api/v1/matches/${id}`, {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${jwt}`,
          'Content-Type': 'application/json',
        },
      });
      if (!updatedMatchResponse.ok) {
        throw new Error('Error al obtener la partida actualizada');
      }
      const updatedMatch = await updatedMatchResponse.json();
      setMatch(updatedMatch);
      alert('El rol se ha cambiado correctamente.');
    } catch (error) {
      console.error('Error al cambiar el rol:', error);
      alert('No se pudo cambiar el rol. Inténtalo de nuevo más tarde.');
    }
  };

  const usuarioAutenticadoUnido =
    match &&
    currentUser &&
    match.players.some(player => player.username === currentUser.username);

  if (loading || !match || !currentUser || !matchCreator) {
    return <p>Cargando detalles...</p>;
  }

  console.log(match);
  console.log(currentUser);

  return (
    <BackgroundContainer>
      <CenteredCard className="centered-card">
        <Section
          style={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            textAlign: 'center',
            marginTop: '-60px',
          }}
        >
          <h3>{match?.name || 'Nombre de la partida'}</h3>
        </Section>
        <Section>
          <div className="players-container">
            <Section
              style={{
                flex: 1,
                padding: '1em',
              }}
            >
              <h5>Jugadores:</h5>
              {match.players.length === 0 ? (
                <p>No hay jugadores unidos aún.</p>
              ) : (
                <List className="players-list">
                  {match.players
                    .filter(player => player.rol !== 'ESPECTADOR')
                    .map((player, index) => (
                      <ListGroupItem key={index} className="player-list-item">
                        {matchCreator && player.username === matchCreator.username && (
                          <span className="star-icon-absolute">★</span>
                        )}
                        {player.username}
                        
                        {currentUser.username === matchCreator.username && player.rol !== 'CREADOR' && (
                          <Button
                            style={{ marginLeft: '10px' }}
                            color="danger"
                            onClick={() => handleRemovePlayer(player.id)}
                          >
                            Eliminar
                          </Button>
                        )}
                      </ListGroupItem>
                    ))
                  }
                </List>
              )}
            </Section>
            <Section
              style={{
                flex: 1,
                padding: '1em',
              }}
            >
              <h5>Espectadores:</h5>
              {match.players.filter(p => p.rol === 'ESPECTADOR').length === 0 ? (
                <p>No hay espectadores aún.</p>
              ) : (
                <List className="players-list">
                  {match.players
                    .filter(player => player.rol === 'ESPECTADOR')
                    .map((spectator, index) => (
                      <ListGroupItem key={index} className="player-list-item">
                        {matchCreator && spectator.username === matchCreator.username && (
                          <span className="star-icon-margin">★</span>
                        )}
                        {spectator.username}
                        {currentUser.username === matchCreator.username && spectator.rol !== 'CREADOR' && (
                          <Button
                            style={{ marginLeft: '10px' }}
                            color="danger"
                            onClick={() => handleRemovePlayer(spectator.id)} 
                          >
                            Eliminar
                          </Button>
                        )}
                      </ListGroupItem>
                    ))}
                </List>
              )}
            </Section>
          </div>
          <div className="toggle-role-container">
            {usuarioAutenticadoUnido && (
              <Button onClick={handleToggleRole} className="toggle-role-button">
                Cambiar Rol
              </Button>
            )}
          </div>
        </Section>
        <Section
          style={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            textAlign: 'center',
            marginTop: '1em',
          }}
        >
          <h5>Invitar amigos:</h5>
          {friends.length === 0 ? (
            <p>No tienes amigos para invitar.</p>
          ) : (
            <List className="friends-list">
              {friends.map((friend, index) => {
                const isSender = friend.senderId === currentUser.id;
                const friendName = isSender ? friend.receiverName : friend.senderName;
                const isOnline = isSender
                  ? friend.receiverIsOnline
                  : friend.senderIsOnline;
                return (
                  <ListGroupItem key={index} className="friends-list-item">
                    <Button
                      onClick={() => handleGameInvite(friendName)}
                      className={
                        `friend-button ${
                          isOnline ? 'friend-button-online' : 'friend-button-offline'
                        }`
                      }
                    >
                      {friendName} {isOnline && '(En línea)'}
                    </Button>
                  </ListGroupItem>
                );
              })}
            </List>
          )}
        </Section>
        <div className="bottom-buttons-container">
          {!usuarioAutenticadoUnido && ( currentUser.authority == "ADMIN" && match.startDate != null ? null :
            <Button onClick={handleJoinMatch} className="join-button">
              Unirme
            </Button>
          )}
          {currentUser &&
            matchCreator &&
            currentUser.username === matchCreator.username && (
              <Button
                onClick={handleStartMatch}
                className={
                  match.players.length > 2
                    ? 'start-match-button start-match-button-green'
                    : 'start-match-button start-match-button-gray'
                }
              >
                Comenzar Partida
              </Button>
            )}
        </div>
      </CenteredCard>
    </BackgroundContainer>
  );
}
