import React from 'react';
import { Form, FormGroup, Label, Input, Button } from 'reactstrap';
import tokenService from "../services/token.service";

export default function EditProfileForm({ userInfo, onInputChange, onSave, onCancel, onAvatarSelect }) {
  const avatarOptions = [
    `${process.env.PUBLIC_URL}/avatares/avatar1.jpg`,
    `${process.env.PUBLIC_URL}/avatares/avatar2.jpg`,
    `${process.env.PUBLIC_URL}/avatares/avatar3.jpg`,
    `${process.env.PUBLIC_URL}/avatares/avatar5.jpg`,
    `${process.env.PUBLIC_URL}/avatares/avatar6.jpg`,
    `${process.env.PUBLIC_URL}/avatares/avatar7.jpg`,
    `${process.env.PUBLIC_URL}/avatares/avatar8.jpg`,
    `${process.env.PUBLIC_URL}/avatares/avatar9.jpg`,
  ];

  function sendLogoutRequest() {
    const jwt = window.localStorage.getItem("jwt");
    if (jwt || typeof jwt === "undefined") {
      tokenService.removeUser();
      window.location.href = "/";
    } else {
      alert("There is no user logged in");
    }
  }

  return (
    <Form>
      <FormGroup>
        <Label for="username">Nombre de Usuario</Label>
        <Input
          type="text"
          name="username"
          id="username"
          value={userInfo.username}
          onChange={onInputChange}
        />
      </FormGroup>
      <FormGroup>
        <Label for="email">Correo Electrónico</Label>
        <Input
          type="email"
          name="email"
          id="email"
          value={userInfo.email}
          onChange={onInputChange}
        />
      </FormGroup>
      <FormGroup>
        <Label>Seleccionar Avatar</Label>
        <div
          style={{
            display: 'flex',
            gap: '10px',
            maxWidth: '100%',
            overflowX: 'auto',
            whiteSpace: 'nowrap',
          }}
        >
          {avatarOptions.map((avatar, index) => (
            <img
              key={index}
              src={avatar}
              alt={`Avatar ${index + 1}`}
              onClick={() => onAvatarSelect(avatar)}
              style={{
                width: '50px',
                height: '50px',
                minWidth: '50px',
                borderRadius: '50%',
                cursor: 'pointer',
                border: userInfo.avatar === avatar
                  ? '3px solid #4a148c'
                  : '2px solid transparent',
              }}
            />
          ))}
        </div>
      </FormGroup>
      <div style={{ display: "flex", gap: "15px", marginTop: "20px" }}>
        <Button
          style={{
            color: "white",
            backgroundColor: "#4a148c",
            border: "2px solid #4a148c",
            padding: "10px 20px",
            flex: 1,
          }}
          onClick={() => {
            onSave();
            sendLogoutRequest();
          }}

        >
          Guardar
        </Button>
        <Button
          style={{
            color: "white",
            backgroundColor: "#4a148c",
            border: "2px solid #4a148c",
            padding: "10px 20px",
            flex: 1,
          }}
          onClick={onCancel}
        >
          Cancelar
        </Button>
      </div>
    </Form>
  );
}
