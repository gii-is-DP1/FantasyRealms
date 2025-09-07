import React from 'react';
import useFetchState from '../util/useFetchState';
import tokenService from "../services/token.service";

const jwt = tokenService.getLocalAccessToken();

export default function CurrentUser() {

    const [currentUser, setCurrentUser] = useFetchState(
        [],
        "/api/v1/currentuser",
        jwt
    )

    return (
        <div style={{ display: "flex", flexDirection: "column", justifyContent: "center", marginTop: "20px", marginLeft: "20px" }}>
            <h3>CURRENT USER</h3>
            <div>
                <h2>username: {currentUser.username}</h2>
                <h2>avatar: {currentUser.avatar ? currentUser.avatar : "No disponible"}</h2>
                <h2>email: {currentUser.email}</h2>
                <h2>logros:</h2>
                <ul>
                    {currentUser.achievements && currentUser.achievements.length > 0 ? (
                        currentUser.achievements.map((a) => (
                            <li key={a.id}>
                                <h4>Nombre: {a.name}</h4>
                                <p>Descripción: {a.description}</p>
                                <p>Icono: {a.icon}</p>
                                <p>Criterio: {a.criteria}</p>
                                <p>Nivel: {a.tier}</p>
                                <p>Puntos: {a.points}</p>
                                <p>Fecha: {a.dateAchieved}</p>
                            </li>
                        ))
                    ) : (
                        <h4>No hay logros disponibles</h4>
                    )}
                </ul>
                <ul>
                    {currentUser.matches && currentUser.matches.length > 0 ? (
                        currentUser.matches.map((m) => (
                            <li key={m.id}>
                                <h4>Fecha inicio: {m.startDate}</h4>
                                <p>Fecha Fin: {m.endDate}</p>
                                <p>Creador: {m.creator.username}</p>
                                <ul>
                                    {m.players && m.players.length > 0 ? (
                                        m.players.map((p) => (
                                            <li key={p.id}>
                                                <h4>Usuario: {p.username}</h4>
                                                <p>Rol: {p.rol}</p>
                                                <p>Score: {p.score != null ? p.score : "No hay score disponible"}</p>
                                            </li>
                                        ))
                                    ) : (
                                        <h4>No hay partidas disponibles</h4>
                                    )}
                                </ul>
                            </li>
                        ))
                    ) : (
                        <h4>No hay partidas disponibles</h4>
                    )}
                </ul>
            </div>
        </div>
    );

}