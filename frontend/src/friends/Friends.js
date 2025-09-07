import React, { useEffect, useState } from "react";
import BackgroundContainer from "../components/background/BackgroundContainer";
import CenteredCard from "../components/background/CenteredCard";
import tokenService from "../services/token.service";
import Section from "../components/Section";
import { useNavigate } from "react-router-dom";

export default function Friends() {
  const [friendRequests, setFriendRequests] = useState([]);
  const [friends, setFriends] = useState([]);
  const [error, setError] = useState(null);
  const [currentUser, setCurrentUser] = useState(null);
  const [receiverUsername, setReceiverUsername] = useState("");
  const [gameInvites, setGameInvites] = useState([]);
  const navigate = useNavigate();

  const jwt = tokenService.getLocalAccessToken();

  useEffect(() => {
    if (jwt) {
      const fetchCurrentUser = async () => {
        try {
          const response = await fetch("/api/v1/currentuser", {
            headers: { Authorization: `Bearer ${jwt}` },
          });
          if (response.ok) {
            const data = await response.json();
            setCurrentUser(data);
          } else {
            setCurrentUser(null);
          }
        } catch (error) {
          console.error("Error fetching current user:", error);
          setCurrentUser(null);
        }
      };
      fetchCurrentUser();
    } else {
      setCurrentUser(null);
    }
  }, [jwt]);

  const fetchFriendRequests = async () => {
    try {
      const response = await fetch(`/api/v1/friendships/pending`, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      });
      if (!response.ok) {
        throw new Error("Error al obtener solicitudes de amistad");
      }
      const data = await response.json();
      setFriendRequests(data);
    } catch (err) {
      setError(err.message);
    }
  };

  const fetchFriends = async () => {
    try {
      const response = await fetch(`/api/v1/friendships/friends`, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      });
      if (!response.ok) {
        throw new Error("Error al obtener la lista de amigos.");
      }
      const data = await response.json();
      console.log("Friends:", data);
      setFriends(data);
    } catch (err) {
      setError(err.message);
    }
  };

  const fetchGameInvites = async () => {
    try {
      const response = await fetch(`/api/v1/game-invitation`, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      });
      if (!response.ok) {
        throw new Error("Error al obtener las invitaciones a juegos.");
      }
      const data = await response.json();
      setGameInvites(data);
    } catch (err) {
      setError(err.message);
    }
  };

  useEffect(() => {
    if (!jwt || !currentUser) return;

    fetchFriendRequests();
    fetchFriends();
    fetchGameInvites();

    const interval = setInterval(() => {
      fetchFriendRequests();
      fetchFriends();
      fetchGameInvites();

    }, 7500);

    return () => clearInterval(interval);
  }, [jwt, currentUser]);

  const handleAccept = async (senderId) => {
    try {
      const response = await fetch(
        `/api/v1/friendships/accept?senderId=${senderId}`,
        {
          method: "POST",
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        }
      );

      if (!response.ok) {
        throw new Error("Error al aceptar la solicitud de amistad.");
      }

      const acceptedFriend = await response.json();

      setFriends((prevFriends) => [...prevFriends, acceptedFriend]);
      setFriendRequests((prevRequests) =>
        prevRequests.filter((fr) => fr.senderId !== senderId)
      );
    } catch (err) {
      setError(err.message);
    }
  };

  const handleRejectOrDelete = async (friendId, isSender) => {
    try {
      const idToSend = isSender ? friendId.receiverId : friendId.senderId;

      const response = await fetch(
        `/api/v1/friendships/delete?friendId=${idToSend}`,
        {
          method: "DELETE",
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        }
      );

      if (!response.ok) {
        throw new Error("Error al rechazar o eliminar la amistad.");
      }

      setFriendRequests((prevRequests) =>
        prevRequests.filter((fr) => fr.senderId !== idToSend)
      );

      setFriends((prevFriends) =>
        prevFriends.filter(
          (friend) =>
            !(friend.senderId === idToSend || friend.receiverId === idToSend)
        )
      );
    } catch (err) {
      setError(err.message);
    }
  };

  const handleSendFriendRequest = async (e) => {
    e.preventDefault();
    if (!currentUser) return;

    try {
      const response = await fetch(
        `/api/v1/friendships/request?receiverUsername=${receiverUsername}`,
        {
          method: "POST",
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        }
      );

      if (!response.ok) {
        throw new Error("Error al enviar la solicitud de amistad.");
      }

      setReceiverUsername("");
      alert("Solicitud de amistad enviada con éxito");
    } catch (err) {
      setError(err.message);
    }
  };

    const handleNavigateMatch = (id) => {
        navigate(`/matches/${id}`);
  };

  const handleJoinMatch = async (id, senderName) => {
    try {
      const token = tokenService.getLocalAccessToken();
      const response = await fetch(`/api/v1/game-invitation/accept/${id}/${senderName}`, {
        method: "POST",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });
  
      if (!response.ok) {
        throw new Error("Error al aceptar la invitación a la partida.");
      }
  
      setGameInvites((prevInvites) =>
        prevInvites.filter((invite) => invite.matchId !== id)
      );
  
      alert("Invitación aceptada correctamente.");
      handleNavigateMatch(id);
    } catch (error) {
      console.error("Error al aceptar la invitación a la partida", error);
      setError(error.message);
    }
  };
  

  const handleRejectInvite = async (matchId) => {
    try {
      const response = await fetch(`/api/v1/game-invitation/reject/${matchId}`, {
        method: "POST",
        headers: {
          Authorization: `Bearer ${jwt}`,
          "Content-Type": "application/json",
        },
      });
  
      if (!response.ok) {
        throw new Error("Error al rechazar la invitación a la partida.");
      }
  
      setGameInvites((prevInvites) =>
        prevInvites.filter((invite) => invite.matchId !== matchId)
      );
  
      alert("Invitación rechazada exitosamente.");
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <BackgroundContainer>
      <CenteredCard>
        {error && <p style={{ color: "red" }}>{error}</p>}

        <Section style={{ padding: "1em" }}>
          <div style={{ marginBottom: "1em" }}>
            <h3>Enviar nueva solicitud</h3>
            <form onSubmit={handleSendFriendRequest}>
              <label>
                Nombre del receptor:{" "}
                <input
                  type="text"
                  required
                  value={receiverUsername}
                  onChange={(e) => setReceiverUsername(e.target.value)}
                  style={{ marginRight: "1em" }}
                />
              </label>
              <button type="submit">Enviar</button>
            </form>
          </div>
        </Section>

        <Section style={{ padding: "1em", marginTop: "1em" }}>
          <h3>Solicitudes de amistad pendientes</h3>
          {friendRequests.length === 0 ? (
            <p>No tienes solicitudes de amistad pendientes.</p>
          ) : (
            <ul>
              {friendRequests.map((req, index) => {
                const { senderId, senderName } = req;
                return (
                  <li key={index}>
                    <span>Solicitud de: {senderName}</span>
                    <button
                      style={{ marginLeft: "10px" }}
                      onClick={() => handleAccept(senderId)}
                    >
                      Aceptar
                    </button>
                    <button
                      style={{ marginLeft: "10px" }}
                      onClick={() => handleRejectOrDelete(req, false)}
                    >
                      Rechazar
                    </button>
                  </li>
                );
              })}
            </ul>
          )}
        </Section>

        <Section style={{ padding: "1em", marginTop: "1em" }}>
          <h3>Mis Amigos</h3>
          {friends.length === 0 ? (
            <p>No tienes amigos en este momento.</p>
          ) : (
            <ul>
              {friends.map((friend, index) => {
                const isSender = friend.senderId === currentUser.id;
                const friendName = isSender
                  ? friend.receiverName
                  : friend.senderName;
                const isOnline = isSender
                  ? friend.receiverIsOnline
                  : friend.senderIsOnline;

                return (
                  <li key={index} style={{ marginBottom: "10px" }}>
                    <span style={{ color: isOnline ? "green" : "black" }}>
                      {friendName} {isOnline && "(En línea)"}
                    </span>
                    <button
                      style={{
                        marginLeft: "10px",
                        backgroundColor: "#ba68c8",
                        border: "none",
                        color: "white",
                        padding: "5px 10px",
                        cursor: "pointer",
                      }}
                      onClick={() =>
                        handleRejectOrDelete(friend, isSender)
                      }
                    >
                      Eliminar
                    </button>
                  </li>
                );
              })}
            </ul>
          )}
        </Section>

        <Section style={{ padding: "1em", marginTop: "1em" }}>
          <h3>Invitaciones a partidas</h3>
          {gameInvites.length === 0 ? (
            <p>No tienes invitaciones a partidas en este momento.</p>
          ) : (
            <ul>
              {gameInvites
                .filter((invite) => !invite.status)
                .map((invite, index) => (
                  <li key={index} style={{ marginBottom: "10px" }}>
                    <span>Invitación de: {invite.senderUsername}</span>
                    <div style={{ marginTop: "5px", display: "flex", gap: "10px" }}>
                      <button
                        style={{
                          backgroundColor: "#4caf50",
                          border: "none",
                          color: "white",
                          padding: "5px 10px",
                          cursor: "pointer",
                        }}
                        onClick={() => handleJoinMatch(invite.matchId, invite.senderUsername)}
                      >
                        Aceptar
                      </button>
                      <button
                        style={{
                          backgroundColor: "#f44336",
                          border: "none",
                          color: "white",
                          padding: "5px 10px",
                          cursor: "pointer",
                        }}
                        onClick={() => handleRejectInvite(invite.matchId)}
                      >
                        Rechazar
                      </button>
                    </div>
                  </li>
                ))}
            </ul>
          )}
        </Section>
      </CenteredCard>
    </BackgroundContainer>
  );
}
