import React, { useState, useEffect } from 'react';
import { useParams, Navigate } from 'react-router-dom';
import tokenService from '../services/token.service';
import useFetchState from '../util/useFetchState';
import { Alert } from 'reactstrap';

const MatchPrivateRoute = ({ children }) => {
  const [isLoading, setIsLoading] = useState(true);
  const [isAuthorized, setIsAuthorized] = useState(false);
  const [error, setError] = useState(null);
  const { id: matchId } = useParams();
  const jwt = tokenService.getLocalAccessToken();
  const [currentUser, setCurrentUser] = useFetchState([], "/api/v1/currentuser", jwt);

  useEffect(() => {

    if (!currentUser || !currentUser.username) return;

    const fetchMatch = async () => {
      try {

        const response = await fetch(`/api/v1/matches/${matchId}`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        });

        if (!response.ok) {
          throw new Error(`Error ${response.status}: ${response.statusText}`);
        }

        const data = await response.json();

        const isPlayer = data.players.some(player => player.username === currentUser.username);

        setIsAuthorized(isPlayer);
      } catch (error) {
        console.error("Error al obtener los datos de la partida:", error);
        setError(error.message);
        setIsAuthorized(false);
      } finally {
        setIsLoading(false);
      }
    };

    fetchMatch();
  }, [matchId, jwt, currentUser]);

  if (isLoading || !currentUser || !currentUser.username) {
    return <div>Loading...</div>;
  }

  if (!isAuthorized) {
    return (
      <>
        <Navigate to="/games" />
      </>
    );
  }
  

  return children;
};

export default MatchPrivateRoute;
