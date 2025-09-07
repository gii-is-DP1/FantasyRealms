import React, { useEffect, useState } from "react";
import BackgroundContainer from "../components/background/BackgroundContainer";
import CenteredCard from "../components/background/CenteredCard";
import Section from "../components/Section";
import { CardBody, CardTitle, ListGroup, ListGroupItem, FormGroup, Label, Input } from 'reactstrap';
import tokenService from "../services/token.service";
import useFetchState from '../util/useFetchState';

const jwt = tokenService.getLocalAccessToken();

export default function Ranking() {
  const [ranking, setRanking] = useState([]);
  const [sortBy, setSortBy] = useState("WINS");

  const [currentUser, setCurrentUser] = useFetchState(
      [],
      "/api/v1/currentuser",
      jwt
    );
  
    const fetchRanking = async (sortByParam = "WINS") => {
      try {
        const response = await fetch(`/api/v1/statistics/global-ranking?sortBy=${sortByParam}`, {
          headers: { 'Authorization': `Bearer ${jwt}` }
        });
  
        if (response.ok) {
          const data = await response.json();
          setRanking(Object.entries(data)); // Convertir el diccionario en un array de pares clave-valor
        } else {
          console.error('Error fetching ranking');
        }
      } catch (error) {
        console.error('Network error:', error);
      }
    };

    useEffect(() => {
      fetchRanking(sortBy);
    }, [sortBy]);

    const handleSortChange = (newSortBy) => {
      setSortBy(newSortBy);
    };

  //console.log(sortBy);
  console.log(ranking);

  return (
    <BackgroundContainer>
      <CenteredCard>
        <Section style={{ marginBottom: "35px" }}>
          <CardBody>
            <CardTitle tag="h2" style={{ color: "#4a148c", textAlign: "center", marginBottom: "25px" }}>
              Ranking Global
            </CardTitle>
  
            <div style={{ display: 'flex', justifyContent: 'center', marginBottom: '20px' }}>
              <FormGroup check inline>
                <Label check>
                  <Input 
                    type="radio" 
                    name="sortBy" 
                    checked={sortBy === 'WINS'} 
                    onChange={() => setSortBy('WINS')} 
                  />{' '}
                  Victorias
                </Label>
              </FormGroup>
              <FormGroup check inline>
                <Label check>
                  <Input 
                    type="radio" 
                    name="sortBy" 
                    checked={sortBy === 'POINTS'} 
                    onChange={() => setSortBy('POINTS')} 
                  />{' '}
                  Puntos
                </Label>
              </FormGroup>
            </div>
  
            <div style={{ maxHeight: "535px", overflowY: "auto" }}>
              <ListGroup>
                {ranking.length > 0 ? (
                  ranking.map(([username, stats], index) => {
                    const [wins, points] = stats; // Desestructurar victorias y puntos
                    let backgroundColor = "white";
                    if (index === 0) {
                      backgroundColor = "#ffd700"; // Oro
                    } else if (index === 1) {
                      backgroundColor = "#c0c0c0"; // Plata
                    } else if (index === 2) {
                      backgroundColor = "#cd7f32"; // Bronce
                    }
  
                    return (
                      <ListGroupItem
                        key={username}
                        style={{
                          display: "flex",
                          justifyContent: "space-between",
                          alignItems: "center",
                          padding: "15px",
                          borderRadius: "8px",
                          marginBottom: "10px",
                          backgroundColor: backgroundColor,
                          fontWeight: index === 0 ? "bold" : "normal",
                        }}
                      >
                        <div style={{ flex: "1", textAlign: "left", fontSize: "18px" }}>{index + 1}.</div>
                        <div style={{ flex: "2", textAlign: "center", fontSize: "18px" }}>{username}</div>
                        <div style={{ flex: "1", textAlign: "right", color: "#4a148c", fontSize: "18px" }}>
                          {sortBy === 'WINS' ? `${wins} Victorias` : `${points} Puntos`}
                        </div>
                      </ListGroupItem>
                    );
                  })
                ) : (
                  <p style={{ color: "#4a148c", fontWeight: "bold", paddingTop: "20px", textAlign: "center" }}>
                    No hay datos disponibles para el ranking.
                  </p>
                )}
              </ListGroup>
            </div>
          </CardBody>
        </Section>
      </CenteredCard>
    </BackgroundContainer>
  );
  
}
