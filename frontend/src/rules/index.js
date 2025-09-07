import React, { useState } from "react";
import "../static/css/home/home.css";
import BackgroundContainer from "../components/background/BackgroundContainer";
import Section from '../components/Section';
import CenteredCard from "../components/background/CenteredCard";
import { Collapse, Button } from "reactstrap";
import "./rules.css";

const List = ({ items }) => (
  <ul>
    {items.map((item, index) => (
      <li key={index} style={{ color: "#000000" }}>{item}</li>
    ))}
  </ul>
);

const FAQItem = ({ question, answer }) => (
  <div className="faq-item">
    <h3 style={{ color: "#4a148c" }}>{question}</h3>
    <p style={{ color: "#000000" }}>{answer}</p>
  </div>
);

export default function Rules() {
  const [openSection, setOpenSection] = useState(null);

  const toggleSection = (sectionName) => {
    setOpenSection(openSection === sectionName ? null : sectionName);
  };

  return (
    <BackgroundContainer>
      <CenteredCard style={{ marginTop: "20px", padding: "30px", width: "80%", maxWidth: "1000px", minHeight: "500px"
        , display: "flex", flexDirection: "column", justifyContent: "space-between"
       }}>
        <Button className="collapse-button" onClick={() => toggleSection("overview")}>Introducción</Button>
        <Collapse isOpen={openSection === "overview"}>
          <Section title="">
            <p>
              En <em>Fantasy Realms</em> eres el gobernante supremo de una tierra lejana. Tu objetivo es construir el reino más poderoso del mundo.
            </p>
          </Section>
        </Collapse>

        <Button className="collapse-button" onClick={() => toggleSection("components")}>Componentes del juego</Button>
        <Collapse isOpen={openSection === "components"}>
          <Section title="">
            <List
              items={[
                "55 cartas",
                "Duración aproximada: 20 minutos",
                "Edad: 14+",
                "Jugadores: 3-6"
              ]}
            />
          </Section>
        </Collapse>

        <Button className="collapse-button" onClick={() => toggleSection("gameSummary")}>Resumen del Juego</Button>
        <Collapse isOpen={openSection === "gameSummary"}>
          <Section title="">
            <p>
              En <em>Fantasy Realms</em> robarás cartas del mazo o del área de descarte para formar las mejores combinaciones posibles.
            </p>
          </Section>
        </Collapse>

        <Button className="collapse-button" onClick={() => toggleSection("cardInfo")}>¿Cómo son las cartas?</Button>
        <Collapse isOpen={openSection === "cardInfo"}>
          <Section title="">
            <List
              items={[
                "Nombre: Cada carta tiene un nombre único.",
                "Tipo: Cada tipo tiene un color distinto. Hay diez: Agua, Arma, Líder, Fuego, Aire, Tierra, Bestia, Artefacto, Ejército, Mago y Variables (comodínes).",
                "Fuerza base: puntos base que nos sumara cada carta.",
                "Efecto: Las cartas pueden tener efectos que afectan al resto de las que el jugador tenga en la mano. Estos pueden ser, en general: Bonus, Penalización, Anulación."
              ]}
            />
          </Section>
        </Collapse>

        <Button className="collapse-button" onClick={() => toggleSection("gameFlow")}> ¿Cómo se juega?</Button>
        <Collapse isOpen={openSection === "gameFlow"}>
          <Section title="">
            <Section title="1) Preparación">
              Reparte siete cartas a cada jugador. Se elige un jugador inicial al azar y el juego avanza en sentido horario.
            </Section>
            <Section title="2) Turnos">
              En cada turno, el jugador puede robar la carta superior del mazo o tomar cualquier carta visible del área de descarte.
            </Section>
            <Section title="3) Fin del Juego">
              El juego termina cuando hay diez cartas en el área de descarte. Gana el jugador con la mano que tenga la puntuación más alta.
            </Section>
          </Section>
        </Collapse>

        <Button className="collapse-button" onClick={() => toggleSection("faq")}>Casos especiales</Button>
        <Collapse isOpen={openSection === "faq"}>
          <Section title="">
            <FAQItem
              question="¿Qué pasa si el Doppelgänger copia el Basilisco?"
              answer="Si no hay ninguna carta que elimine sus penalizaciones, ambas cartas serán anuladas."
            />
            <FAQItem
              question="¿Cómo funciona el Libro de Cambios?"
              answer="El Libro de Cambios transforma el tipo de otra carta. Esto debe hacerse antes de aplicar cualquier bonus o penalización de cualquier carta."
            />
            <FAQItem
              question="¿Los Exploradores protegen a mis ejércitos del Incendio?"
              answer="No. La carta de los Exploradores solo elimina la palabra 'Ejército' de la sección de penalización de todas las cartas."
            />
            <FAQItem
              question="¿Cómo funcionan las cartas Shapeshifter y Mirage?"
              answer="Estas cartas adoptan el nombre y el tipo de cualquier otra carta del juego."
            />
          </Section>
        </Collapse>
      </CenteredCard>
    </BackgroundContainer>
  );
}
