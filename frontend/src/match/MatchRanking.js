import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { List, ListGroupItem, Button } from 'reactstrap';
import BackgroundContainer from '../components/background/BackgroundContainer';
import CenteredCard from '../components/background/CenteredCard';
import Section from '../components/Section';
import tokenService from '../services/token.service';

export default function MatchRanking() {
    const { id } = useParams();
    const [players, setPlayers] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        // Verificar si la página ya se ha recargado
        const hasReloaded = sessionStorage.getItem('hasReloaded');

        if (!hasReloaded) {
            sessionStorage.setItem('hasReloaded', 'true'); // Marcar como recargado
            window.location.reload(); // Recargar la página
        } else {
            // Aquí puedes poner la lógica normal para cargar los datos del componente
            const fetchMatch = async () => {
                try {
                    const token = tokenService.getLocalAccessToken();
                    const response = await fetch(`/api/v1/matches/${id}`, {
                        method: 'GET',
                        headers: {
                            'Authorization': `Bearer ${token}`,
                            'Content-Type': 'application/json',
                        },
                    });
                    if (!response.ok) {
                        throw new Error('Error fetching match.');
                    }
                    const matchData = await response.json();

                    const sortedPlayers = matchData.players
                        .filter(player => player.rol !== 'ESPECTADOR')
                        .sort((a, b) => b.score - a.score);

                    setPlayers(sortedPlayers);
                    setLoading(false);
                } catch (error) {
                    console.error('Error fetching match.', error);
                    setLoading(false);
                }
            };

            fetchMatch();
        }
    }, [id]);

    if (loading) {
        return <p>Cargando ranking...</p>;
    }

    return (
        <BackgroundContainer>
            <CenteredCard
                style={{
                    maxWidth: '65%',
                    display: 'flex',
                    flexDirection: 'column',
                    justifyContent: 'space-between',
                    height: '100%',
                    padding: '20px',
                }}
            >
                <Section
                    style={{
                        display: 'flex',
                        justifyContent: 'center',
                        alignItems: 'center',
                        textAlign: 'center',
                        marginBottom: '20px',
                    }}
                >
                    <h3 style={{ color: '#4a148c', fontWeight: 'bold' }}>RANKING</h3>
                </Section>

                <Section style={{ display: 'flex', marginBottom: '10px' }}>
                    <List style={{ width: '100%', marginLeft: '-17px', marginTop: '15px' }}>
                        {players.map((player, index) => (
                            <ListGroupItem
                                key={player.username}
                                style={{
                                    display: 'flex',
                                    justifyContent: 'space-between',
                                    alignItems: 'center',
                                    padding: '15px',
                                    borderRadius: '8px',
                                    marginBottom: '10px',
                                    backgroundColor: index === 0 ? '#ffd700' : index === 1 ? '#c0c0c0' : index === 2 ? '#cd7f32' : 'white',
                                    fontWeight: index === 0 ? 'bold' : 'normal',
                                }}
                            >
                                <div style={{ flex: '1', textAlign: 'left', marginLeft: '20px', fontSize: '20px' }}>
                                    {index + 1}.
                                </div>
                                <div style={{ flex: '2', textAlign: 'center', fontSize: '18px' }}>
                                    {player.username}
                                </div>
                                <div style={{ flex: '1', textAlign: 'right', color: '#4a148c', marginRight: '20px', fontSize: '15px' }}>
                                    {player.score} PUNTOS
                                </div>
                            </ListGroupItem>
                        ))}
                    </List>
                </Section>

                <div style={{ textAlign: 'center', marginTop: '20px' }}>
                    <Button
                        onClick={() => navigate('/matches')}
                        style={{
                            backgroundColor: '#4a148c',
                            color: 'white',
                            padding: '10px 20px',
                            border: 'none',
                            borderRadius: '5px',
                            cursor: 'pointer',
                            width: '30%',
                            maxWidth: '500px',
                        }}
                    >
                        Volver a la lista de partidas
                    </Button>
                </div>
            </CenteredCard>
        </BackgroundContainer>
    );
}
