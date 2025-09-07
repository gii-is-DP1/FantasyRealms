import React from "react";
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from "reactstrap";
import "../../static/css/auth/authButton.css";
import "../../static/css/auth/authPage.css";
import tokenService from "../../services/token.service";

const LogoutModal = ({ isOpen, toggle }) => {
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
    <Modal isOpen={isOpen} toggle={toggle} centered style={{ borderBottomLeftRadius: "50"}}>
      <ModalHeader 
        toggle={toggle} 
        style={{ backgroundColor: "#4a148c", color: "white", textAlign: "center" , border: "none"}}
      >
        Confirm Logout
      </ModalHeader>
      <ModalFooter 
        style={{ backgroundColor: "#4a148c", display: "flex", justifyContent: "center", border: "none" }}
      >
        <Button 
          color="light" 
          onClick={sendLogoutRequest} 
          style={{ backgroundColor: "#ba68c8", border: "none", marginRight: "10px" }}
        >
          Yes, Logout
        </Button>
        <Button 
          color="secondary" 
          onClick={toggle} 
          style={{ backgroundColor: "#ffffff", border: "none", color: "#4a148c" }}
        >
          No, Cancel
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default LogoutModal;
