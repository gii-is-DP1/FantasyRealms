import React, { useState, useEffect } from 'react';
import BackgroundContainer from "../components/background/BackgroundContainer";
import Section from '../components/Section';
import EditProfileForm from './EditProfileForm';
import LogrosInfoModal from './LogrosInfoModal';
import { Button, Card, CardBody, CardTitle, FormGroup, ListGroup, Form, Label, Input } from 'reactstrap';
import '../static/css/profile/profile.css';
import CenteredCard from '../components/background/CenteredCard';
import useFetchState from '../util/useFetchState';
import tokenService from "../services/token.service";

const jwt = tokenService.getLocalAccessToken();

export default function Profile() {
  const [isEditing, setIsEditing] = useState(false);
  const [modalOpen, setModalOpen] = useState(false);
  const [selectedAchievement, setSelectedAchievement] = useState([]);

  const [currentUser, setCurrentUser] = useFetchState(
    [],
    "/api/v1/currentuser",
    jwt
  );

  const [estadisticas, setEstadisticas] = useState({
    totalPartidas: 0,
    duracionMedia: 0,
    duracionTotal: 0,
    duracionMaxima: 0,
    duracionMinima: 0,
    duracionMediaGlobal: 0,
    duracionMaximaGlobal: 0,
    duracionMinimaGlobal: 0,
    mediaJugadores: 0,
    maximoJugadores: 0,
    minimoJugadores: 0,
    maximoJugadoresGlobal: 0,
    minimoJugadoresGlobal: 0,
    mediaJugadoresGlobal: 0,
    mediaPuntos: 0,
    maximoPuntos: 0,
    minimoPuntos: 0,
    porcentajeVictorias: 0,
    rankingMedio: 0,
    mediaTurnos: 0,
    cartasFavoritas: null
  });

  const [showOnlyCreatorMatches, setShowOnlyCreatorMatches] = useState(false);
  const [matches, setMatches] = useState([]);

  const formatNumber = (value) => {
    if (value === null || isNaN(value)) return value;
    const number = parseFloat(value);
    return number % 1 === 0 ? number.toString() : number.toFixed(3);
  };
  

  const fetchEstadisticas = async () => {
    try {
      const responses = await Promise.all([
        fetch('/api/v1/statistics/total-matches', { headers: { 'Authorization': `Bearer ${jwt}` } }),
        fetch('/api/v1/statistics/average-match-duration', { headers: { 'Authorization': `Bearer ${jwt}` } }),
        fetch('/api/v1/statistics/total-match-duration', { headers: { 'Authorization': `Bearer ${jwt}` } }),
        fetch('/api/v1/statistics/max-match-duration', { headers: { 'Authorization': `Bearer ${jwt}` } }),
        fetch('/api/v1/statistics/min-match-duration', { headers: { 'Authorization': `Bearer ${jwt}` } }),
        fetch('/api/v1/statistics/global-match-duration-average', { headers: { 'Authorization': `Bearer ${jwt}` } }),
        fetch('/api/v1/statistics/global-match-max-duration', { headers: { 'Authorization': `Bearer ${jwt}` } }),
        fetch('/api/v1/statistics/global-match-min-duration', { headers: { 'Authorization': `Bearer ${jwt}` } }),
        fetch('/api/v1/statistics/average-players-per-match', { headers: { 'Authorization': `Bearer ${jwt}` } }),
        fetch('/api/v1/statistics/match-players-max', { headers: { 'Authorization': `Bearer ${jwt}` } }),
        fetch('/api/v1/statistics/match-players-min', { headers: { 'Authorization': `Bearer ${jwt}` } }),
        fetch('/api/v1/statistics/global-match-players-max', { headers: { 'Authorization': `Bearer ${jwt}` } }),
        fetch('/api/v1/statistics/global-match-players-min', { headers: { 'Authorization': `Bearer ${jwt}` } }),
        fetch('/api/v1/statistics/global-match-players-average', { headers: { 'Authorization': `Bearer ${jwt}` } }),
        fetch('/api/v1/statistics/average-points', { headers: { 'Authorization': `Bearer ${jwt}` } }),
        fetch('/api/v1/statistics/max-points', { headers: { 'Authorization': `Bearer ${jwt}` } }),
        fetch('/api/v1/statistics/min-points', { headers: { 'Authorization': `Bearer ${jwt}` } }),
        fetch('/api/v1/statistics/win-percentage', { headers: { 'Authorization': `Bearer ${jwt}` } }),
        fetch('/api/v1/statistics/average-ranking-position', { headers: { 'Authorization': `Bearer ${jwt}` } }),
        fetch('/api/v1/statistics/average-turns-per-match', { headers: { 'Authorization': `Bearer ${jwt}` } }),
        fetch('/api/v1/statistics/most-frequent-cards', { headers: { 'Authorization': `Bearer ${jwt}` } }),
      ]);

      const data = await Promise.all(responses.map(res => res.json()));

      setEstadisticas({
        totalPartidas: formatNumber(data[0]),
        duracionMedia: formatNumber(data[1]),
        duracionTotal: formatNumber(data[2]),
        duracionMaxima: formatNumber(data[3]),
        duracionMinima: formatNumber(data[4]),
        duracionMediaGlobal: formatNumber(data[5]),
        duracionMaximaGlobal: formatNumber(data[6]),
        duracionMinimaGlobal: formatNumber(data[7]),
        mediaJugadores: formatNumber(data[8]),
        maximoJugadores: formatNumber(data[9]),
        minimoJugadores: formatNumber(data[10]),
        maximoJugadoresGlobal: formatNumber(data[11]),
        minimoJugadoresGlobal: formatNumber(data[12]),
        mediaJugadoresGlobal: formatNumber(data[13]),
        mediaPuntos: formatNumber(data[14]),
        maximoPuntos: formatNumber(data[15]),
        minimoPuntos: formatNumber(data[16]),
        porcentajeVictorias: formatNumber(data[17]),
        rankingMedio: formatNumber(data[18]),
        mediaTurnos: formatNumber(data[19]),
        cartasFavoritas: data[20]
      });

    } catch (error) {
      console.error("Error fetching statistics:", error);
    }
  };

  useEffect(() => {
    fetchEstadisticas();
  }, []);

  const fetchMatches = async () => {
    const url = showOnlyCreatorMatches ? '/api/v1/matches/myMatches?onlyCreator=true' : '/api/v1/matches/myMatches';
    try {
      const response = await fetch(url, {
        headers: { 'Authorization': `Bearer ${jwt}` }
      });
      if (response.ok) {
        const data = await response.json();
        setMatches(data);
      } else {
        console.error('Error fetching matches');
      }
    } catch (error) {
      console.error('Network error:', error);
    }
  };

  useEffect(() => {
    fetchMatches();
  }, [showOnlyCreatorMatches, currentUser]);

  const toggleModal = () => setModalOpen(!modalOpen);

  const handleMoreInfoClick = (logro) => {
    setSelectedAchievement(logro);
    toggleModal();
  };

  const [userInfo, setUserInfo] = useState({
    username: currentUser.username,
    email: currentUser.email,
    avatar: currentUser.avatar
  });

  const handleAvatarSelect = (selectedAvatar) => {
    setUserInfo({
      ...userInfo,
      avatar: selectedAvatar
    });
  };

  const handleEditToggle = () => {
    setIsEditing(!isEditing);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUserInfo({ ...userInfo, [name]: value });
  };

  const handleSave = async () => {
    const updatedUser = {
      username: userInfo.username || currentUser.username,
      email: userInfo.email || currentUser.email,
      avatar: userInfo.avatar || currentUser.avatar,
    };

    try {
      const response = await fetch(`/api/v1/users/${currentUser.id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${jwt}`
        },
        body: JSON.stringify(updatedUser),
      });

      if (response.ok) {
        const updatedUserResponse = await response.json();
        setCurrentUser(updatedUserResponse);
        setIsEditing(false);
      } else {
        console.error('Error al guardar los cambios');
        alert('Error al guardar los cambios');
      }
    } catch (error) {
      console.error('Error de red:', error);
      alert('Error al guardar los cambios');
    }
  };

  const getColorByTier = (tier) => {
    switch (tier) {
      case "FACIL":
        return "#B87333"
      case "INTERMEDIO":
        return "silver"
      case "DIFICIL":
        return "gold"
      default:
        return "black"
    }
  };

  const getColorByRanking = (posicion) => {
    switch (posicion) {
      case 1:
        return "gold"
      case 2:
        return "silver"
      case 3:
        return "#B87333"
      default:
        return "black"
    }
  };

  function formatCustomDate(dateString) {
    if (!dateString) return "Fecha no disponible";
    const date = new Date(dateString);
    const options = {
      day: 'numeric',
      month: 'long',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    };
    return date.toLocaleString('es-ES', options);
  }

  return (
    <BackgroundContainer>
      <CenteredCard>
      <div 
        style={{
          display: 'flex', 
          flexDirection: 'row',         
          boxSizing: 'border-box'
        }}
      >
        <Section 
          style={{width: "calc(50% - 10px)", padding: "10px", margin: '5px'}}>
          <CardBody>
            <CardTitle tag="h2" style={{ color: "#4a148c",  textAlign: "center", marginBottom: "25px"}}>
              Perfil del Usuario
            </CardTitle>

            {isEditing ? (
              <EditProfileForm
                userInfo={userInfo}
                onInputChange={handleInputChange}
                onSave={handleSave}
                onCancel={handleEditToggle}
                onAvatarSelect={handleAvatarSelect}
              />
            ) : (
              <Form style={{ display: "flex", flexDirection: "column", gap: "10px" }}>
                <FormGroup style={{}}>
                  <strong style={{padding: '10px'}}>Nombre de Usuario:</strong> 
                  <Card>
                    <CardBody>
                      {currentUser.username}
                    </CardBody>
                  </Card>
                </FormGroup>
                <FormGroup>
                  <strong style={{padding: '10px'}}>Correo Electrónico:</strong>
                  <Card>
                    <CardBody>
                      {currentUser.email||"Todavia no tienes correo asociado"}
                    </CardBody>
                  </Card>
                </FormGroup>

                <FormGroup style={{}}>
                  <strong style={{padding: '10px', display: 'block'}}>Avatar:</strong> 
                    {currentUser.avatar ? (
                      <img
                          src={currentUser.avatar}
                          alt="Avatar del usuario"
                          style={{ width: '50px', height: '50px', borderRadius: '50%', marginTop: '10px' }} />) : 
                          (<p style={{margin:"20px"}}>No hay avatar disponible</p>)}
                </FormGroup>

                <Button
                  style={{ color: "#fff", backgroundColor: "#4a148c", border: "none", marginTop: "10px", width: "30%" }}
                  onClick={() => {handleEditToggle()}}
                  //;handleLogOutCambioPerfil()
                >
                  Editar Perfil
                </Button>
              </Form>
            )}
          </CardBody>
        </Section>

        <Section
          style={{
            width: "calc(50% - 10px)",
            padding: "10px",
            margin: '5px',
            display: "flex",
            flexDirection: "column",
            alignItems: "center", // Centrar horizontalmente
            justifyContent: "center" // Centrar verticalmente
          }}
        >
          <CardBody style={{ width: "100%" }}> {/* Mantener la tarjeta alineada */}
            <CardTitle tag="h2" style={{ color: "#4a148c", textAlign: "center", marginBottom: "15px" }}>
              Logros Conseguidos
            </CardTitle>
            <div style={{ maxHeight: "250px", overflowY: "auto", display: "flex", flexDirection: "column", alignItems: "center" }}>
              <ListGroup style={{ listStyleType: 'none', width: "100%", padding: 0 }}>
                {currentUser.achievements ? (
                  Array.isArray(currentUser.achievements) && currentUser.achievements.length > 0 ? (
                    currentUser.achievements.map((logro, index) => (
                      <li key={index} style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', marginBottom: '15px' }}>
                        <img
                          src={logro.icon}
                          style={{
                            width: '50px',
                            height: '50px',
                            marginRight: '20px',
                            borderRadius: '5px',
                            border: `3px solid ${getColorByTier(logro.tier)}`
                          }}
                        />
                        <div 
                          style={{ 
                            flex: 1, 
                            overflowX: "auto", 
                            whiteSpace: "nowrap", 
                            marginRight: "20px",
                            padding: "5px",
                            border: "1px solid #ddd",
                            borderRadius: "5px",
                            textAlign: "center" // Centrar texto dentro del contenedor
                          }}
                        >
                          {logro.description}
                        </div>
                        <div>
                          <Button
                            style={{ color: "#fff", backgroundColor: "#4a148c", border: "none" }}
                            onClick={() => handleMoreInfoClick(logro)}
                          >
                            info
                          </Button>
                          <LogrosInfoModal
                            isOpen={modalOpen}
                            toggle={toggleModal}
                            logro={selectedAchievement}
                          />
                        </div>
                      </li>
                    ))
                  ) : (
                    <p style={{ color: "#4a148c", fontWeight: "bold", paddingTop: "50px", textAlign: 'center' }}>
                      Todavía no has desbloqueado ningún logro. ¡Mucho ánimo!
                    </p>
                  )
                ) : (
                  <p>Buscando Logros...</p>
                )}
              </ListGroup>
            </div>
          </CardBody>
        </Section>
      </div>

      <Section style={{ marginTop: "35px" }}>
        <CardBody>
          <CardTitle tag="h2" style={{ color: "#4a148c", textAlign: "center" }}>
            Estadísticas
          </CardTitle>
          <div style={{ display: "flex", justifyContent: "space-around", alignItems: "center", maxHeight: "535px", overflowY: "auto" }}>
            {/* Columna izquierda */}
            <div style={{ flex: 1, textAlign: "center", padding: "10px" }}>
              <ul style={{ listStyleType: 'none', paddingLeft: 0 }}>
                <li style={{ marginBottom: "20px" }}>
                  <div style={{ color: "#4a148c", fontWeight: "bold" }}>Total Partidas:</div>
                  <div>{estadisticas.totalPartidas}</div>
                </li>
                <li style={{ marginBottom: "20px" }}>
                  <div style={{ color: "#4a148c", fontWeight: "bold" }}>Duración Media:</div>
                  <div>{estadisticas.duracionMedia} minutos</div>
                </li>
                <li style={{ marginBottom: "20px" }}>
                  <div style={{ color: "#4a148c", fontWeight: "bold" }}>Duración Total:</div>
                  <div>{estadisticas.duracionTotal}</div>
                </li>
                <li style={{ marginBottom: "20px" }}>
                  <div style={{ color: "#4a148c", fontWeight: "bold" }}>Duración Máxima:</div>
                  <div>{estadisticas.duracionMaxima}</div>
                </li>
                <li style={{ marginBottom: "20px" }}>
                  <div style={{ color: "#4a148c", fontWeight: "bold" }}>Duración Mínima:</div>
                  <div>{estadisticas.duracionMinima}</div>
                </li>
                <li style={{ marginBottom: "20px" }}>
                  <div style={{ color: "#4a148c", fontWeight: "bold" }}>Duración Media Global:</div>
                  <div>{estadisticas.duracionMediaGlobal}</div>
                </li>
                <li style={{ marginBottom: "20px" }}>
                  <div style={{ color: "#4a148c", fontWeight: "bold" }}>Duración Máxima Global:</div>
                  <div>{estadisticas.duracionMaximaGlobal}</div>
                </li>
              </ul>
            </div>

            {/* Columna central */}
            <div style={{ flex: 1, textAlign: "center", padding: "10px" }}>
              <ul style={{ listStyleType: 'none', paddingLeft: 0 }}>
                <li style={{ marginBottom: "20px" }}>
                  <div style={{ color: "#4a148c", fontWeight: "bold" }}>Duración Mínima Global:</div>
                  <div>{estadisticas.duracionMinimaGlobal}%</div>
                </li>
                <li style={{ marginBottom: "20px" }}>
                  <div style={{ color: "#4a148c", fontWeight: "bold" }}>Media Jugadores:</div>
                  <div>{estadisticas.mediaJugadores}</div>
                </li>
                <li style={{ marginBottom: "20px" }}>
                  <div style={{ color: "#4a148c", fontWeight: "bold" }}>Máximo Jugadores:</div>
                  <div>{estadisticas.maximoJugadores}</div>
                </li>
                <li style={{ marginBottom: "20px" }}>
                  <div style={{ color: "#4a148c", fontWeight: "bold" }}>Minimo Jugadores:</div>
                  <div>{estadisticas.minimoJugadores}</div>
                </li>
                <li style={{ marginBottom: "20px" }}>
                  <div style={{ color: "#4a148c", fontWeight: "bold" }}>Máximo Jugadores Global:</div>
                  <div>{estadisticas.maximoJugadoresGlobal}</div>
                </li>
                <li style={{ marginBottom: "20px" }}>
                  <div style={{ color: "#4a148c", fontWeight: "bold" }}>Minimo Jugadores Global:</div>
                  <div>{estadisticas.minimoJugadoresGlobal}</div>
                </li>
                <li style={{ marginBottom: "20px" }}>
                  <div style={{ color: "#4a148c", fontWeight: "bold" }}>Media Jugadores Global:</div>
                  <div>{estadisticas.mediaJugadoresGlobal}</div>
                </li>
              </ul>
            </div>

            {/* Columna derecha */}
            <div style={{ flex: 1, textAlign: "center", padding: "10px" }}>
              <ul style={{ listStyleType: 'none', paddingLeft: 0 }}>
                <li style={{ marginBottom: "20px" }}>
                  <div style={{ color: "#4a148c", fontWeight: "bold" }}>Media Puntos:</div>
                  <div>{estadisticas.mediaPuntos}</div>
                </li>
                <li style={{ marginBottom: "20px" }}>
                  <div style={{ color: "#4a148c", fontWeight: "bold" }}>Máximo Puntos:</div>
                  <div>{estadisticas.maximoPuntos}</div>
                </li>
                <li style={{ marginBottom: "20px" }}>
                  <div style={{ color: "#4a148c", fontWeight: "bold" }}>Minimo Puntos:</div>
                  <div>{estadisticas.minimoPuntos}</div>
                </li>
                <li style={{ marginBottom: "20px" }}>
                  <div style={{ color: "#4a148c", fontWeight: "bold" }}>Porcentaje Victorias:</div>
                  <div>{estadisticas.porcentajeVictorias}</div>
                </li>
                <li style={{ marginBottom: "20px" }}>
                  <div style={{ color: "#4a148c", fontWeight: "bold" }}>Posición Media Ranking:</div>
                  <div>{estadisticas.rankingMedio}</div>
                </li>
                <li style={{ marginBottom: "20px" }}>
                  <div style={{ color: "#4a148c", fontWeight: "bold" }}>Media Turnos:</div>
                  <div>{estadisticas.mediaTurnos}</div>
                </li>
                <li style={{ marginBottom: "6px", color: "#4a148c", fontWeight: "bold" }}>Cartas Favoritas:</li>
                {estadisticas.cartasFavoritas ? estadisticas.cartasFavoritas.map((carta, index) => (
                  <li key={index} style={{ marginBottom: "10px" }}>
                    {carta}
                  </li>
                ))
                :
                'Todavía no tienes cartas favoritas'
                }
              </ul>
            </div>
          </div>
        </CardBody>
      </Section>

      <Section style={{ marginTop: "35px" }}>
          <CardBody>
            <CardTitle tag="h2" style={{ color: "#4a148c", textAlign: "center" }}>
              Historial de Partidas
            </CardTitle>
            <FormGroup check style={{ textAlign: 'center', marginBottom: '20px' }}>
              <Label check>
                <Input 
                  type="switch" 
                  checked={showOnlyCreatorMatches} 
                  onChange={(e) => setShowOnlyCreatorMatches(e.target.checked)} 
                />{' '}
                Mostrar solo partidas creadas
              </Label>
            </FormGroup>
            <div style={{ maxHeight: "535px", overflowY: "auto" }}>
              {matches && matches.content ? (matches.content.length > 0 ? (
                matches.content.filter((match) => match.startDate && match.endDate)
                .map((match) => (
                  <li key={match.id} style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
                    <Section style={{ width: "100%", textAlign: "center", marginBottom: "10px" }}>
                      Id: {match.id} <br />
                      Fecha Inicio: {formatCustomDate(match.startDate)} <br />
                      Fecha Fin: {formatCustomDate(match.endDate)} <br />
                      Creador: {match.creator}<br />
                    </Section>
                  </li>
                ))) : (
                <p style={{ color: "#4a148c", fontWeight: "bold", paddingTop: "50px", textAlign: 'center' }}>
                  Todavía no has finalizado ninguna partida. ¡ÁNIMO!
                </p>
              )) : (
                <p>Buscando Partidas...</p>
              )}
            </div>
          </CardBody>
        </Section>
      </CenteredCard>
    </BackgroundContainer>
  );
}
