import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { Table, Button, ButtonGroup } from "reactstrap";
import tokenService from "../../services/token.service";
import "../../static/css/admin/adminPage.css";
import deleteFromList from "../../util/deleteFromList";
import getErrorModal from "../../util/getErrorModal";
import BackgroundContainer from "../../components/background/BackgroundContainer";
import CenteredCard from "../../components/background/CenteredCard";

const jwt = tokenService.getLocalAccessToken();

export default function LogrosListAdmin() {
  const [message, setMessage] = useState(null);
  const [visible, setVisible] = useState(false);
  const [alerts, setAlerts] = useState([]);
  const [allAchievements, setAllAchievements] = useState([]); // Todos los logros
  const [currentPage, setCurrentPage] = useState(1);
  const achievementsPerPage = 5;

  useEffect(() => {
    const fetchAchievements = async () => {
      try {
        const response = await fetch(`/api/v1/achievements`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        });

        if (!response.ok) {
          throw new Error("Failed to fetch achievements");
        }

        const data = await response.json();
        console.log("Data received from server:", data);
        setAllAchievements(data || []); // Guardar todos los logros
      } catch (error) {
        console.error("Error al obtener logros:", error);
        setMessage(error.message);
        setVisible(true);
      }
    };

    fetchAchievements();
  }, []);

  // Logros para la página actual
  const startIndex = (currentPage - 1) * achievementsPerPage;
  const currentAchievements = allAchievements.slice(
    startIndex,
    startIndex + achievementsPerPage
  );

  // Total de páginas
  const totalPages = Math.ceil(allAchievements.length / achievementsPerPage);

  const handleDelete = (id) => {
    deleteFromList(
      `/api/v1/achievements/${id}`,
      id,
      [allAchievements, setAllAchievements],
      [alerts, setAlerts],
      setMessage,
      setVisible
    );
  };

  const goToNextPage = () => {
    if (currentPage < totalPages) setCurrentPage(currentPage + 1);
  };

  const goToPreviousPage = () => {
    if (currentPage > 1) setCurrentPage(currentPage - 1);
  };

  const modal = getErrorModal(setVisible, visible, message);

  const achievementList = currentAchievements.map((achievement) => (
    <tr key={achievement.id}>
      <td style={{ overflowX: "auto", whiteSpace: "nowrap" }}>
        <div style={{ overflowX: "auto", whiteSpace: "nowrap" }}>
          {achievement.description}
        </div>
      </td>
      <td>{achievement.condition}</td>
      <td>{achievement.requiredValue}</td>
      <td>
        <ButtonGroup>
          <Button
            size="sm"
            color="primary"
            tag={Link}
            to={`/achievements/edit/${achievement.id}`}
          >
            Edit
          </Button>
          <Button
            size="sm"
            color="danger"
            onClick={() => handleDelete(achievement.id)}
          >
            Delete
          </Button>
        </ButtonGroup>
      </td>
    </tr>
  ));

  return (
    <BackgroundContainer>
      <CenteredCard style={{ maxWidth: "1200px", padding: "20px" }}>
        <h2 style={{ textAlign: "center", marginBottom: "40px", marginTop: "20px" }}>
          Lista de Logros
        </h2>
        {modal}
        <Table
          striped
          hover
          responsive
          style={{ tableLayout: "auto", width: "100%" }}
        >
          <thead>
            <tr>
              <th>Descripción</th>
              <th>Condición</th>
              <th>Valor Requerido</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {achievementList.length > 0 ? (
              achievementList
            ) : (
              <tr>
                <td colSpan="4" style={{ textAlign: "center" }}>No achievements available.</td>
              </tr>
            )}
          </tbody>
        </Table>
        <div
          style={{
            display: "flex",
            justifyContent: "center",
            marginTop: "20px",
          }}
        >
          <Button
            color="primary"
            onClick={goToPreviousPage}
            disabled={currentPage === 1}
          >
            Previous
          </Button>
          <span style={{ margin: "0 1rem", alignSelf: "center" }}>
            Page {currentPage} of {totalPages}
          </span>
          <Button
            color="primary"
            onClick={goToNextPage}
            disabled={currentPage === totalPages}
          >
            Next
          </Button>
        </div>
        <div style={{ display: "flex", justifyContent: "center", marginTop: "40px" }}>
          <Link
            to="/achievements/edit/new"
            className="btn btn-success"
            style={{ maxWidth: "300px" }}
          >
            Add Achievement
          </Link>
        </div>
      </CenteredCard>
    </BackgroundContainer>
  );
}
