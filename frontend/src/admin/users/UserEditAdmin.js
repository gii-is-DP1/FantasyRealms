import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { Form, Input, Label } from "reactstrap";
import tokenService from "../../services/token.service";
import "../../static/css/admin/adminPage.css";
import getErrorModal from "../../util/getErrorModal";
import getIdFromUrl from "../../util/getIdFromUrl";
import useFetchData from "../../util/useFetchData";
import useFetchState from "../../util/useFetchState";
import Section from "../../components/Section";
import BackgroundContainer from "../../components/background/BackgroundContainer";
import CenteredCard from "../../components/background/CenteredCard";


const jwt = tokenService.getLocalAccessToken();

export default function UserEditAdmin() {
  const emptyItem = {
    id: null,
    username: "",
    password: null,
    email: "",
    authority: null,
    achievements: [],
    players: [],
  };

  const id = getIdFromUrl(2);
  const [message, setMessage] = useState(null);
  const [visible, setVisible] = useState(false);
  const [oldUser, setOldUser] = useState(null);
  const [user, setUser] = useFetchState(
    emptyItem,
    `/api/v1/users/${id}`,
    jwt,
    setMessage,
    setVisible,
    id
  );

  const auths = useFetchData(`/api/v1/users/authorities`, jwt);
  const uniqueAuths = Array.from(
    new Set(auths.map((a) => a.authority))
  ).map((authority) => {
    return auths.find((a) => a.authority === authority);
  });

  useEffect(() => {
    if (user.id) {
      fetch(`/api/v1/users/${id}`, {
        headers: { Authorization: `Bearer ${jwt}` },
      })
        .then((response) => response.json())
        .then((data) => {
          setOldUser(data);
          setUser((prevUser) => ({ ...prevUser, authority: data.authority }));
        })
        .catch((error) => {
          console.error("Error al obtener el usuario:", error);
          setMessage("Error al obtener el usuario");
          setVisible(true);
        });
    }
  }, [id, jwt, setUser, user.id]);

  function handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    if (name === "authority") {
      const auth = uniqueAuths.find((a) => a.id === Number(value));
      setUser((prevUser) => ({ ...prevUser, authority: auth || null }));
    } else {
      setUser((prevUser) => ({ ...prevUser, [name]: value }));
    }
  }

  function handleSubmit(event) {
    event.preventDefault();

    if (!user.id) {
      const request = {
        username: user.username,
        password: user.password,
        authority: user.authority?.authority || "Player",
      };

      fetch("/api/v1/auth/signup", {
        headers: { "Content-Type": "application/json" },
        method: "POST",
        body: JSON.stringify(request),
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error("Error al registrar el usuario");
          }
          return response.json();
        })
        .then(() => {
          alert("Usuario registrado correctamente.");
          window.location.href = "/users";
        })
        .catch((error) => {
          console.error("Error en el registro:", error);
          alert("Hubo un problema al registrar el usuario.");
        });
    } else {
      const payload = {
        username: user.username,
        authority: user.authority?.authority || null,
        email: user.email,
      };

      fetch(`/api/v1/users/${user.id}`, {
        method: "PUT",
        headers: {
          Authorization: `Bearer ${jwt}`,
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error("Error al actualizar el usuario");
          }
          return response.json();
        })
        .then(() => {
          alert("Usuario actualizado correctamente.");
          window.location.href = "/users";
        })
        .catch((error) => {
          console.error("Error en la solicitud:", error);
          alert("Hubo un problema al guardar el usuario.");
        });
    }
  }

  const authSelectOptions = uniqueAuths.map((auth) => (
    <option key={auth.id} value={auth.id}>
      {auth.authority}
    </option>
  ));

  const modal = getErrorModal(setVisible, visible, message);

  return (
    <BackgroundContainer>
      <CenteredCard
        style={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          padding: "30px",
          maxWidth: "700px",
        }}
      >
        <Section
          title={user.id ? "Edit User" : "Add User"}
          style={{ width: "100%", textAlign: "center", marginBottom: "20px" }}
        >
          {modal}
          <div className="auth-form-container" style={{ marginTop: "20px" }}>
            <Form onSubmit={handleSubmit}>
              <div className="custom-form-input">
                <Label for="username" className="custom-form-input-label">
                  Usuario
                </Label>
                <Input
                  type="text"
                  required
                  name="username"
                  id="username"
                  value={user.username || ""}
                  onChange={handleChange}
                  className="custom-input"
                />
              </div>
              {!user.id && (
                <div className="custom-form-input">
                  <Label for="password" className="custom-form-input-label">
                    Contraseña
                  </Label>
                  <Input
                    type="password"
                    required
                    name="password"
                    id="password"
                    value={user.password || ""}
                    onChange={handleChange}
                    className="custom-input"
                  />
                </div>
              )}
              {user.id && (
                <div className="custom-form-input">
                  <Label for="email" className="custom-form-input-label">
                    Email
                  </Label>
                  <Input
                    type="email"
                    required
                    name="email"
                    id="email"
                    value={user.email || ""}
                    onChange={handleChange}
                    className="custom-input"
                  />
                </div>
              )}
              <div className="custom-form-input">
                <Label for="authority" className="custom-form-input-label">
                  Permisos
                </Label>
                <Input
                  type="select"
                  name="authority"
                  id="authority"
                  value={user.authority?.id || ""}
                  onChange={handleChange}
                  className="custom-input"
                >
                  <option value="">Seleccione un permiso</option>
                  {authSelectOptions}
                </Input>
              </div>
              <div className="custom-button-row">
                <button className="auth-button">Guardar</button>
                <Link
                  to={`/users`}
                  className="auth-button"
                  style={{ textDecoration: "none" }}
                >
                  Cancelar
                </Link>
              </div>
            </Form>
          </div>
        </Section>
      </CenteredCard>
    </BackgroundContainer>
  );
}