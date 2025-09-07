import React from 'react';
import '../App.css';
import '../static/css/home/home.css';
import BackgroundContainer from '../components/background/BackgroundContainer.js';
import CenteredCard from '../components/background/CenteredCard.js';

export default function Home() {
    return (
        <BackgroundContainer>
            <CenteredCard style={{
                width: "auto",
                padding: "20px",
                display: "flex",
                justifyContent: "center",
                alignItems: "center"
            }}>
                <img 
                    src={require('../static/images/FantasyRealms-Titulo.png')} 
                    alt="hero" 
                    className="hero-image" 
                    style={{ maxWidth: "100%", height: "auto" }}
                />
            </CenteredCard>
        </BackgroundContainer>
    );
}
