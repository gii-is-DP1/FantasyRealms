import React from 'react';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

const LogrosInfoModal = ({ isOpen, toggle, logro }) => {
  return (
    <Modal
      isOpen={isOpen}
      toggle={toggle}
      centered
      contentClassName="custom-modal-content"
      backdrop={false}
    >
      <ModalHeader style={{ color: "#4a148c" }} toggle={toggle}>
        {logro.description || "Descripción"}
      </ModalHeader>
      <ModalBody>
        <div style={{ marginBottom: "10px" }}>
          <strong>Nivel de Dificultad:</strong> {logro.tier || "No especificado"}
        </div>
        <div style={{ marginBottom: "10px" }}>
          <strong>Fecha de Obtención:</strong> {logro.date || "No disponible"}
        </div>
        <div style={{ marginBottom: "10px" }}>
          <strong>Tipo:</strong> {logro.type || "No especificado"}
        </div>
      </ModalBody>
      <ModalFooter>
        <Button
          style={{ color: "#fff", backgroundColor: "#4a148c", border: "none" }}
          onClick={toggle}
        >
          Cerrar
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default LogrosInfoModal;
