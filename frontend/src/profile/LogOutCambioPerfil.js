import React from 'react';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import LogoutModal from '../auth/logout';

const LogOutCambioPerfilModal = ({ isOpenP, toggleP, titleP}) => {
  return (
    <Modal
      isOpen={isOpenP}
      toggle={toggleP}
      centered
      contentClassName="custom-modal-content"
      backdrop={false}
    >
      <ModalHeader  style={{color: "#4a148c"}} toggle={toggleP}>{titleP}</ModalHeader>
      <ModalFooter>
        <Button style={{color: "#fff", backgroundColor: "#4a148c", border: "none"}} onClick={toggleP}>
          Cerrar
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default LogOutCambioPerfilModal;