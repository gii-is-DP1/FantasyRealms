import React, { useRef } from "react";
import tokenService from "../../services/token.service";
import FormGenerator from "../../components/formGenerator/formGenerator";
import { registerFormBasicInputs, registerFormOwnerInputs } from "./form/registerFormOwnerInputs";
import CenteredCard from "../../components/background/CenteredCard";
import BackgroundContainer from "../../components/background/BackgroundContainer";
import Section from "../../components/Section";

export default function Register() {
  const registerFormRef = useRef();

  function handleSubmit({ values }) {
    if (!registerFormRef.current.validate()) return;

    const request = { ...values, authority: "PLAYER" };

    fetch("/api/v1/auth/signup", {
      headers: { "Content-Type": "application/json" },
      method: "POST",
      body: JSON.stringify(request),
    })
      .then((response) => {
        if (response.status === 200) {
          const loginRequest = {
            username: request.username,
            password: request.password,
          };

          fetch("/api/v1/auth/signin", {
            headers: { "Content-Type": "application/json" },
            method: "POST",
            body: JSON.stringify(loginRequest),
          })
            .then((response) => {
              if (response.status === 200) {
                return response.json();
              } else {
                return response.json().then((data) => Promise.reject(data));
              }
            })
            .then((data) => {
              tokenService.setUser(data);
              tokenService.updateLocalAccessToken(data.token);
              window.location.href = "/";
            })
            .catch((error) => alert(error.message));
        }
      })
      .catch((error) => alert(error.message));
  }

  return (
    <BackgroundContainer>
      <CenteredCard style={{  paddingTop: "150px", paddingBottom: "150px", 
        marginTop: "80px", paddingRight:"500px", paddingLeft:"500px" }}>
        <Section title="Register" style={{ padding: "20px 30px" }}>
          <FormGenerator
            ref={registerFormRef}
            inputs={registerFormBasicInputs}
            onSubmit={handleSubmit}
            numberOfColumns={1}
            listenEnterKey
            buttonText="Register"
            buttonClassName="auth-button"
          />
        </Section>
      </CenteredCard>
    </BackgroundContainer>
  );
}
