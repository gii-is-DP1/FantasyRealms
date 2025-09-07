import React, { useState } from "react";
import { Alert, Button } from "reactstrap";
import FormGenerator from "../../components/formGenerator/formGenerator";
import tokenService from "../../services/token.service";
import "../../static/css/auth/authButton.css";
import { loginFormInputs } from "./form/loginFormInputs";
import BackgroundContainer from "../../components/background/BackgroundContainer";
import { Link } from "react-router-dom";
import "../../static/css/auth/authPage.css";
import Section from "../../components/Section";
import CenteredCard from "../../components/background/CenteredCard";

export default function Login() {
  const [message, setMessage] = useState(null);
  const loginFormRef = React.createRef();

  async function handleSubmit({ values }) {
    const reqBody = values;
    setMessage(null);
    await fetch("/api/v1/auth/signin", {
      headers: { "Content-Type": "application/json" },
      method: "POST",
      body: JSON.stringify(reqBody),
    })
      .then(function (response) {
        if (response.status === 200) return response.json();
        else return Promise.reject("Invalid login attempt");
      })
      .then(function (data) {
        tokenService.setUser(data);
        tokenService.updateLocalAccessToken(data.token);
        window.location.href = "/";
      })
      .catch((error) => {
        setMessage(error);
      });
  }

  return (
    <BackgroundContainer>
      <CenteredCard style={{ display: "flex", flexDirection: "column", alignItems: "center", padding: "30px", maxWidth: "500px" }}>
        <Section title="Login" style={{ width: "100%", textAlign: "center", marginBottom: "20px" }}>
          <div className="auth-form-container" style={{ marginTop: "20px" }}>
            <FormGenerator
              ref={loginFormRef}
              inputs={loginFormInputs}
              onSubmit={handleSubmit}
              numberOfColumns={1}
              listenEnterKey
              buttonText="Login"
              buttonClassName="auth-button"
            />
          </div>
        </Section>
        
        <Section title="Don't have an account?" style={{ width: "100%", textAlign: "center" }}>
          <div style={{ display: "flex", justifyContent: "center", marginTop: "10px" }}>
            <Button
              tag={Link}
              to="/register"
              style={{ color: "white", backgroundColor: "#ba68c8", border: "solid 2px #4a148c" }}
            >
              Register now!
            </Button>
          </div>
        </Section>
      </CenteredCard>
    </BackgroundContainer>
  );
}
