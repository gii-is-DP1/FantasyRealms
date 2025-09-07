import React, { useState, useEffect } from 'react';
import { List, ListGroupItem, Button, Modal, ModalHeader, ModalBody, ModalFooter, Form, FormGroup, Label, Input } from 'reactstrap';
import tokenService from "../services/token.service";
import Section from "../components/Section";
import BackgroundContainer from "../components/background/BackgroundContainer";
import CenteredCard from "../components/background/CenteredCard";
import { useNavigate } from 'react-router-dom';
import useFetchState from '../util/useFetchState';

const jwt = tokenService.getLocalAccessToken();

const MatchList = () => {
    const [matches, setMatches] = useState([]);
    const [loading, setLoading] = useState(true);
    const [showModal, setShowModal] = useState(false);
    const [matchName, setMatchName] = useState('');
    const [totalPages, setTotalPages] = useState(0); 
    const [currentPage, setCurrentPage] = useState(0); 
    const navigate = useNavigate();
    const [matchesStatus, setMatchesStatus] = useState('lobby');
    const [currentUser, setCurrentUser] = useFetchState([], "/api/v1/currentuser", jwt);

    useEffect(() => {
        const fetchMatches = async () => {
            const url = matchesStatus=='lobby' ? `/api/v1/matches?page=${currentPage}&size=2` : (matchesStatus=='inProgress' ? `/api/v1/matches?status=inProgress&?page=${currentPage}&size=2` : `/api/v1/matches?status=finished&?page=${currentPage}&size=2`);
            try {
                const token = tokenService.getLocalAccessToken();
                const response = await fetch(url, { 
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json',
                    },
                });
                if (!response.ok) {
                    throw new Error('Error fetching matches');
                }
                const data = await response.json();
                setMatches(data.content);  
                setTotalPages(data.totalPages); 
                setLoading(false);
            } catch (error) {
                console.error('Error fetching matches', error);
                setLoading(false);
            }
        };

        fetchMatches(); 

        const intervalId = setInterval(fetchMatches, 1000);  

        return () => clearInterval(intervalId);
    }, [currentPage, matchesStatus]); 

    const handleCreateMatch = async () => {
        try {
            const token = tokenService.getLocalAccessToken();
            const queryParam = `matchName=${encodeURIComponent(matchName || 'Partida sin nombre')}`;
            const response = await fetch(`/api/v1/matches/create?${queryParam}`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                }
            });

            setShowModal(false);
            setMatchName('');
    
            if (!response.ok) {
                const errorData = await response.json();
                console.log("Error creating match:", errorData);
                throw new Error('Error creating match');
            }
    
            const createdMatch = await response.json();
            console.log(createdMatch);

            navigate('/matches'); 
        } catch (error) {
            console.error('Error creating match', error);
        }
    };

    const handleNavigate = (id) => {
        navigate(`/matches/${id}`);
    };

    const handleStatusChange = (status) => {
        setMatchesStatus(matchesStatus === status ? '' : status);
    };

    if (loading) {
        return <p>Cargando partidas...</p>;
    }

    console.log(currentUser.matches);

    return (
        <BackgroundContainer style={{ overflow: 'hidden', height: '100%' }}>
            <CenteredCard>
                <div style={{ display: 'flex', justifyContent: 'center', marginBottom: '30px', marginTop: '-80px' }}>
                    <button
                        onClick={() => setShowModal(true)}
                        style={{
                            color: 'white',
                            backgroundColor: '#4a148c',
                            padding: '10px 20px',
                            border: 'none',
                            borderRadius: '5px',
                            cursor: 'pointer',
                            fontWeight: 'bold',
                        }}
                    >
                        CREAR PARTIDA
                    </button>
                </div>

                <Modal isOpen={showModal} toggle={() => setShowModal(false)} centered>
                    <ModalHeader toggle={() => setShowModal(false)} style={{ textAlign: "center", display: "flex", justifyContent: "center" }}>
                        <div style={{ flex: 1, textAlign: "center" }}>Crear Nueva Partida</div>
                    </ModalHeader>
                    <ModalBody>
                        <Form onSubmit={(e) => { e.preventDefault(); handleCreateMatch(); }} style={{ textAlign: "center" }}>
                            <FormGroup>
                                <Label for="matchName">Nombre de la Partida:</Label>
                                <Input
                                    type="text"
                                    id="matchName"
                                    value={matchName}
                                    onChange={(e) => setMatchName(e.target.value)}
                                    required
                                />
                            </FormGroup>
                        </Form>
                    </ModalBody>
                    <ModalFooter style={{ display: "flex", justifyContent: "center", gap: "10px" }}>
                        <button
                            onClick={handleCreateMatch}
                            style={{
                                color: 'white',
                                backgroundColor: matchName.trim() ? '#4a148c' : '#818081',
                                padding: '10px 20px',
                                border: 'none',
                                borderRadius: '5px',
                                cursor: 'pointer',
                            }}
                            disabled={!matchName}
                        >
                            Crear
                        </button>
                        <button
                            onClick={() => setShowModal(false)}
                            style={{
                                backgroundColor: 'grey',
                                color: 'white',
                                padding: '10px 20px',
                                border: 'none',
                                borderRadius: '5px',
                                cursor: 'pointer',
                            }}
                        >
                            Cancelar
                        </button>
                    </ModalFooter>
                </Modal>

                <div style={{
                    display: 'flex', justifyContent: 'center', marginBottom: '10px', color: '#4a148c'
                }}>
                    <h8>LISTADO DE PARTIDAS</h8>
                </div>

                {currentUser.authority == 'ADMIN' ?
                    <div style={{ display: 'flex', justifyContent: 'center', marginBottom: '20px' }}>
                    <FormGroup check inline>
                        <Label check>
                            <Input 
                                type="radio" 
                                name="matchStatus" 
                                checked={matchesStatus === 'lobby'} 
                                onChange={() => handleStatusChange('lobby')} 
                            />{' '}
                            En Lobby
                        </Label>
                    </FormGroup>
                    <FormGroup check inline>
                        <Label check>
                            <Input 
                                type="radio" 
                                name="matchStatus" 
                                checked={matchesStatus === 'inProgress'} 
                                onChange={() => handleStatusChange('inProgress')} 
                            />{' '}
                            En Progreso
                        </Label>
                    </FormGroup>
                    <FormGroup check inline>
                        <Label check>
                            <Input 
                                type="radio" 
                                name="matchStatus" 
                                checked={matchesStatus === 'finished'} 
                                onChange={() => handleStatusChange('finished')} 
                            />{' '}
                            Terminadas
                        </Label>
                    </FormGroup>
                </div>
                : null}

                
                <div style={{ display: 'flex', justifyContent: 'center', width: '100%', padding: '20px 0', marginTop: '-30px' }}>
                    <List style={{ width: '90%', maxWidth: '800px', margin: '0 auto' }}>
                        {matches.map((match) => (
                            <ListGroupItem
                                key={match.id}
                                style={{
                                    display: "flex",
                                    justifyContent: "center",
                                    margin: '10px auto',
                                    width: '100%',
                                    maxWidth: '900px',
                                    borderRadius: '8px',
                                    padding: '20px',
                                }}
                            >
                                <Section
                                    style={{
                                        width: "100%",
                                        display: 'flex',
                                        justifyContent: 'space-between',
                                        alignItems: 'flex-start',
                                        flexDirection: 'row',
                                        flexWrap: 'wrap',
                                    }}
                                >
                                    <div style={{ marginLeft: '20px', textAlign: 'left', flex: '1', padding: '10px' }}>
                                        <div style={{ fontWeight: 'bold' }}>Creador:</div>
                                        <div style={{ color: '#4a148c' }}>{match.creator}</div>
                                    </div>

                                    <div style={{ marginLeft: '-50px', textAlign: 'center', flex: '1', padding: '10px' }}>
                                        <div style={{ fontWeight: 'bold' }}>Nombre:</div>
                                        <div style={{ color: '#4a148c' }}>{match.name}</div>
                                    </div>

                                    <div style={{ textAlign: 'center', flex: '1', padding: '10px' }}>
                                        <div style={{ fontWeight: 'bold' }}>Jugadores:</div>
                                        <div style={{ color: '#4a148c' }}>{match.players.length}/6</div>
                                    </div>

                                    <div style={{ width: '100%', textAlign: 'center', marginTop: '10px' }}>
                                        <button
                                            onClick={() => handleNavigate(match.id)}
                                            style={{
                                                color: 'white',
                                                backgroundColor: '#ba68c8',
                                                padding: '10px 20px',
                                                border: 'none',
                                                borderRadius: '5px',
                                                cursor: 'pointer',
                                                marginLeft: '-30px',
                                                marginBottom: '20px'
                                            }}
                                        >
                                            Ver Detalles
                                        </button>
                                    </div>
                                </Section>
                            </ListGroupItem>
                        ))}
                    </List>
                </div>

                <div style={{ textAlign: 'center' }}>
                    <Button 
                        onClick={() => setCurrentPage(currentPage - 1)} 
                        disabled={currentPage === 0} 
                        style={{ marginRight: '10px' }}
                    >
                        Anterior
                    </Button>
                    <Button 
                        onClick={() => setCurrentPage(currentPage + 1)} 
                        disabled={currentPage >= totalPages - 1}
                        style={{ marginLeft: '10px' }}
                    >
                        Siguiente
                    </Button>
                </div>
            </CenteredCard>
        </BackgroundContainer>
    );
};

export default MatchList;