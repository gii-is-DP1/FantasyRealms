/* eslint-disable no-unused-vars */
import React, { useState, useEffect } from 'react';
import { Navbar, NavbarBrand, Button, NavbarToggler, Collapse, Dropdown, DropdownMenu, DropdownItem, DropdownToggle, Nav } from 'reactstrap';
import { Link, Route, Routes, useNavigate } from 'react-router-dom';
import tokenService from './services/token.service';
import jwt_decode from "jwt-decode";
import Logout from './auth/logout';
import './AppNavbar.css';
import useFetchState from './/util/useFetchState';  

function AppNavbar() {
    const [jwt, setJwt] = useState(tokenService.getLocalAccessToken());
    const [username, setUsername] = useState("");
    const [dropdownOpen, setDropdownOpen] = useState(false);
    const [isLogOutModalOpen, setIsLogOutModalOpen] = useState(false);
    const [roles, setRoles] = useState([]);
    const [currentUser, setCurrentUser] = useState(null);
    const [currentMatchId, setCurrentMatchId] = useState(null);
    const navigate = useNavigate();

    const toggleLogOutModal = () => setIsLogOutModalOpen(!isLogOutModalOpen);
    const toggleDropdown = () => setDropdownOpen(!dropdownOpen);

    useEffect(() => {
        if (jwt) {
            const decoded = jwt_decode(jwt);
            setUsername(decoded.sub);

            const rolesFromToken = decoded.authorities || [];
            setRoles(rolesFromToken);
        }
    }, [jwt]);

    useEffect(() => {
        if (jwt) {
            const fetchCurrentUser = async () => {
                try {
                    const response = await fetch("/api/v1/currentuser", {
                        headers: { Authorization: `Bearer ${jwt}` }
                    });
                    if (response.ok) {
                        const data = await response.json();
                        setCurrentUser(data);  
                    } else {
                        setCurrentUser(null);  
                    }
                } catch (error) {
                    console.error('Error fetching current user:', error);
                    setCurrentUser(null); 
                }
            };

            fetchCurrentUser();
        } else {
            setCurrentUser(null);  
        }
    }, [jwt]);

    useEffect(() => {
        if (currentUser && currentUser.matches) {
            const matchInProgress = currentUser.matches.find((match) => match.inProgress);
            if (matchInProgress) {
                setCurrentMatchId(matchInProgress.id); 
            } else {
                setCurrentMatchId(null);
            }
        }
    }, [currentUser]);

    const handleMatchClick = () => {
        if (currentMatchId) {
            navigate(`/matches/${currentMatchId}/game`);
        } else {
            navigate('/matches');
        }
    };

    useEffect(() => {
        if (jwt) {
            const intervalId = setInterval(async () => {
                try {
                    const response = await fetch("/api/v1/currentuser", {
                        headers: { Authorization: `Bearer ${jwt}` },
                    });
                    if (response.ok) {
                        const data = await response.json();
                        const matchInProgress = data.matches?.find((match) => match.inProgress);
                        setCurrentMatchId(matchInProgress ? matchInProgress.id : null);
                    }
                } catch (error) {
                    console.error("Error fetching current user:", error);
                    setCurrentMatchId(null);
                }
            }, 15000);
    
            return () => clearInterval(intervalId);
        }
    }, [jwt]);
    

    console.log(currentMatchId);

    return (
        <div>
            <Navbar expand="md" style={{ backgroundColor: "#4a148c" }}>
                <NavbarBrand style={{ color:"white" }} href='/'>
                    Fantasy Realms
                </NavbarBrand>
                <div className="d-flex align-items-center ms-3">
                    <Dropdown isOpen={dropdownOpen} toggle={toggleDropdown} inNavbar>
                        <DropdownToggle style={{ color: "#ffffff", backgroundColor: "#ba68c8", border: "none" }}>
                            MENU
                        </DropdownToggle>
                        <DropdownMenu style={{ backgroundColor: "#ba68c8" , minWidth: "120px", maxWidth: "200px" }}>
                            {jwt ? (
                                <>
                                    <DropdownItem className="custom-dropdown-item" tag={Link} to="/profile">Perfil</DropdownItem>
                                    <DropdownItem className="custom-dropdown-item" onClick={handleMatchClick}>
                                        Partidas
                                    </DropdownItem>
                                    <DropdownItem className="custom-dropdown-item" tag={Link} to="/friends">Amigos</DropdownItem>
                                    <DropdownItem className="custom-dropdown-item" tag={Link} to="/globalranking">Ranking</DropdownItem>
                                    <DropdownItem className="custom-dropdown-item" tag={Link} to="/rules">Reglas</DropdownItem>
                                </>
                            ) : (
                                <>
                                    <DropdownItem className="custom-dropdown-item" tag={Link} to="/rules">Reglas</DropdownItem>
                                    <DropdownItem className="custom-dropdown-item" tag={Link} to="/register">Register</DropdownItem>
                                </>
                            )}
                        </DropdownMenu>
                    </Dropdown>
                </div>
                <Nav className="ms-auto mb-2 mb-lg-0" navbar>

                {roles.includes("ADMIN") && (
                        <Button
                            tag={Link}
                            to="/achievements"
                            style={{
                                color: "white",
                                backgroundColor: "#ba68c8",
                                border: "none",
                                marginRight: "10px",
                            }}
                        >
                            Logros
                        </Button>
                    )}

                    {roles.includes("ADMIN") && (
                        <Button
                            tag={Link}
                            to="/users"
                            style={{
                                color: "white",
                                backgroundColor: "#ba68c8",
                                border: "none",
                                marginRight: "10px",
                            }}
                        >
                            Users
                        </Button>
                    )}
                    {jwt ? (
                        <div>
                            <Button
                            onClick={toggleLogOutModal}
                            style={{
                                color: 'white',
                                backgroundColor: '#ba68c8',
                                border: 'none',
                            }}
                        >
                            Logout
                        </Button>
                        <Button
                            tag={Link} to='/profile'
                            style={{
                                color: 'white',
                                backgroundColor: '#ba68c8',
                                border: 'none',
                                marginLeft: '10px'
                            }}
                        >
                            {username}
                        </Button>           
                        </div>
                    ) : (
                        <Button tag={Link} to="/login" style={{ color: "white", backgroundColor: "#ba68c8", border: "none" }}>
                            Login
                        </Button>
                    )}
                </Nav>
            </Navbar>
            <Logout isOpen={isLogOutModalOpen} toggle={toggleLogOutModal} />
        </div>
    );
}

export default AppNavbar;
