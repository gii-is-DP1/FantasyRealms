import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { Button, ButtonGroup, Table } from "reactstrap";
import tokenService from "../../services/token.service";
import "../../static/css/admin/adminPage.css";
import deleteFromList from "../../util/deleteFromList";
import getErrorModal from "../../util/getErrorModal";
import Section from "../../components/Section";
import BackgroundContainer from "../../components/background/BackgroundContainer";
import CenteredCard from "../../components/background/CenteredCard";

const jwt = tokenService.getLocalAccessToken();

export default function UserListAdmin() {
  const [message, setMessage] = useState(null);
  const [visible, setVisible] = useState(false);
  const [alerts, setAlerts] = useState([]);
  const [users, setUsers] = useState([]); 
  const [currentPage, setCurrentPage] = useState(1); 
  const [totalPages, setTotalPages] = useState(1); 
  
  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await fetch(
          `/api/v1/users?page=${currentPage - 1}&size=5`,
          {
            headers: {
              Authorization: `Bearer ${jwt}`,
            },
          }
        );

        if (!response.ok) throw new Error("Failed to fetch users");

        const data = await response.json();
        setUsers(data.content); 
        setTotalPages(data.totalPages); 
      } catch (error) {
        setMessage(error.message);
        setVisible(true);
      }
    };

    fetchUsers();
  }, [currentPage]); 

  
  const goToNextPage = () => {
    if (currentPage < totalPages) setCurrentPage(currentPage + 1);
  };

  const goToPreviousPage = () => {
    if (currentPage > 1) setCurrentPage(currentPage - 1);
  };

  const userList = users.map((user) => (
    <tr key={user.id}>
      <td style={{ textAlign: "center", width: "33%" }}>{user.username}</td>
      <td style={{ textAlign: "center", width: "33%" }}>{user.authority}</td>
      <td style={{ textAlign: "center", width: "33%" }}>
        <ButtonGroup>
          <Button
            size="sm"
            style={{ backgroundColor: "#ba68c8" }}
            aria-label={"edit-" + user.id}
            tag={Link}
            to={"/users/" + user.id}
          >
            Edit
          </Button>
          <Button
            size="sm"
            color="danger"
            aria-label={"delete-" + user.id}
            onClick={() =>
              deleteFromList(
                `/api/v1/users/${user.id}`,
                user.id,
                [users, setUsers],
                [alerts, setAlerts],
                setMessage,
                setVisible
              )
            }
          >
            Delete
          </Button>
        </ButtonGroup>
      </td>
    </tr>
  ));

  const modal = getErrorModal(setVisible, visible, message);

  return (
    <BackgroundContainer>
      <CenteredCard>
        <Section>
          <div>
            {alerts.map((a) => a.alert)}
            {modal}
            <div style={{ display: "flex", justifyContent: "center", marginBottom: "20px" }}>
              <Button
                style={{ backgroundColor: "#4a148c" }}
                tag={Link}
                to="/users/new"
              >
                Add User
              </Button>
            </div>
            <div>
              <Table aria-label="users" className="mt-4" style={{ tableLayout: "fixed", width: "100%" }}>
                <thead>
                  <tr>
                    <th style={{ textAlign: "center", width: "33%" }}>Username</th>
                    <th style={{ textAlign: "center", width: "33%" }}>Authority</th>
                    <th style={{ textAlign: "center", width: "33%" }}>Actions</th>
                  </tr>
                </thead>
                <tbody>{userList}</tbody>
              </Table>
            </div>
            <div
              style={{
                display: "flex",
                justifyContent: "center",
                marginTop: "20px",
              }}
            >
              <Button
                style={{ backgroundColor: "#4a148c", marginRight: "1rem" }}
                onClick={goToPreviousPage}
                disabled={currentPage === 1}
              >
                Previous
              </Button>
              <span style={{ alignSelf: "center", margin: "0 1rem" }}>
                Page {currentPage} of {totalPages}
              </span>
              <Button
                style={{ backgroundColor: "#4a148c", marginLeft: "1rem" }}
                onClick={goToNextPage}
                disabled={currentPage === totalPages}
              >
                Next
              </Button>
            </div>
          </div>
        </Section>
      </CenteredCard>
    </BackgroundContainer>
  );
}
