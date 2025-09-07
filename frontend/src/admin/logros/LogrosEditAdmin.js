import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Form, Input, Label, Button } from "reactstrap";
import tokenService from "../../services/token.service";
import "../../static/css/admin/adminPage.css";
import getErrorModal from "../../util/getErrorModal";
import getIdFromUrl from "../../util/getIdFromUrl";
import Section from "../../components/Section";
import BackgroundContainer from "../../components/background/BackgroundContainer";
import CenteredCard from "../../components/background/CenteredCard";

const jwt = tokenService.getLocalAccessToken();

const achievementTypeMap = {
  PROGRESO: "Progress",
  HABILIDAD: "Skill",
  SOCIAL: "Social",
};

const achievementConditionMap = {
  WIN_N_GAMES: "Win N Games",
  PLAY_N_GAMES: "Play N Games",
  WIN_STREAK: "Win Streak",
  WIN_WITH_SPECIFIC_CARDS: "Win with Specific Cards",
  WIN_WITH_MIN_POINTS: "Win with Min Points",
  WIN_WITH_NO_RARE_CARDS: "Win with No Rare Cards",
  WIN_AFTER_LAST_PLACE: "Win After Last Place",
  PLAY_N_CARDS_OF_TYPE: "Play N Cards of Type",
};

const tierTypeMap = {
  FACIL: "Fácil",
  INTERMEDIO: "Intermedio",
  DIFICIL: "Difícil",
};

export default function LogrosEditAdmin() {
  const emptyItem = {
    id: null,
    description: "",
    condition: "",
    requiredValue: "",
    extraData: "",
    type: null,
    tier: "",
  };

  const id = getIdFromUrl(3);
  const isEditing = id && id !== "new";
  const [message, setMessage] = useState(null);
  const [visible, setVisible] = useState(false);
  const [achievement, setAchievement] = useState(emptyItem);
  const [loading, setLoading] = useState(true);

  const navigate = useNavigate();

  useEffect(() => {
    if (isEditing) {
      fetch(`/api/v1/achievements/${id}`, {
        headers: { Authorization: `Bearer ${jwt}` },
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error("Error al obtener el logro");
          }
          return response.json();
        })
        .then((data) => {
          setAchievement(data);
          setLoading(false);
        })
        .catch((error) => {
          console.error("Error al obtener el logro:", error);
          setMessage("Error al obtener el logro");
          setVisible(true);
          setLoading(false);
        });
    } else {
      setLoading(false);
    }
  }, [id, isEditing]);

  function handleChange(event) {
    const { name, value } = event.target;
    setAchievement((prev) => ({ ...prev, [name]: value }));
  }

  function handleSubmit(event) {
    event.preventDefault();

    const payload = {
      description: achievement.description,
      condition: achievement.condition,
      requiredValue: achievement.requiredValue,
      extraData: achievement.extraData,
      type: achievement.type,
      tier: achievement.tier,
    };

    const url = isEditing
      ? `/api/v1/achievements/${id}`
      : `/api/v1/achievements`;

    const method = isEditing ? "PUT" : "POST";

    fetch(url, {
      method: method,
      headers: {
        Authorization: `Bearer ${jwt}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(payload),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error(
            isEditing
              ? "Error al actualizar el logro"
              : "Error al crear el logro"
          );
        }
        return response.json();
      })
      .then(() => {
        alert(
          isEditing
            ? "Logro actualizado correctamente."
            : "Logro creado correctamente."
        );
        navigate("/achievements");
      })
      .catch((error) => {
        console.error("Error en la solicitud:", error);
        alert("Hubo un problema al guardar el logro.");
      });
  }

  const modal = getErrorModal(setVisible, visible, message);

  if (loading) {
    return (
      <BackgroundContainer>
        <CenteredCard>
          <h2 style={{ textAlign: "center" }}>Cargando...</h2>
        </CenteredCard>
      </BackgroundContainer>
    );
  }

  return (
    <BackgroundContainer>
      <CenteredCard style={{ maxWidth: "800px", padding: "20px" }}>
        <h2 style={{ textAlign: "center", marginBottom: "30px" }}>
          {isEditing ? "Editar Logro" : "Añadir Logro"}
        </h2>
        {modal}
        <Form onSubmit={handleSubmit}>
          <div className="custom-form-input" style={{ marginBottom: "15px" }}>
            <Label for="description">Descripción</Label>
            <Input
              type="text"
              name="description"
              id="description"
              value={achievement.description || ""}
              onChange={handleChange}
              required
              style={{ maxWidth: "1000px", margin: "0 auto" }}
            />
          </div>

          <div className="custom-form-input" style={{ marginBottom: "15px" }}>
            <Label for="condition">Condición</Label>
            <Input
              type="select"
              name="condition"
              id="condition"
              value={achievement.condition || ""}
              onChange={handleChange}
              required
              style={{ maxWidth: "1000px", margin: "0 auto" }}
            >
              <option value="">Seleccione una condición</option>
              {Object.keys(achievementConditionMap).map((key) => (
                <option key={key} value={key}>
                  {achievementConditionMap[key]}
                </option>
              ))}
            </Input>
          </div>

          <div className="custom-form-input" style={{ marginBottom: "15px" }}>
            <Label for="requiredValue">Valor Requerido</Label>
            <Input
              type="number"
              name="requiredValue"
              id="requiredValue"
              value={achievement.requiredValue || ""}
              onChange={handleChange}
              required
              style={{ maxWidth: "1000px", margin: "0 auto" }}
            />
          </div>

          <div className="custom-form-input" style={{ marginBottom: "15px" }}>
            <Label for="extraData">Datos Extra</Label>
            <Input
              type="text"
              name="extraData"
              id="extraData"
              value={achievement.extraData || ""}
              onChange={handleChange}
              style={{ maxWidth: "1000px", margin: "0 auto" }}
            />
          </div>

          <div className="custom-form-input" style={{ marginBottom: "15px" }}>
            <Label for="type">Tipo</Label>
            <Input
              type="select"
              name="type"
              id="type"
              value={achievement.type || ""}
              onChange={handleChange}
              required
              style={{ maxWidth: "1000px", margin: "0 auto" }}
            >
              <option value="">Seleccione un tipo</option>
              {Object.keys(achievementTypeMap).map((key) => (
                <option key={key} value={key}>
                  {achievementTypeMap[key]}
                </option>
              ))}
            </Input>
          </div>

          <div className="custom-form-input" style={{ marginBottom: "15px" }}>
            <Label for="tier">Nivel</Label>
            <Input
              type="select"
              name="tier"
              id="tier"
              value={achievement.tier || ""}
              onChange={handleChange}
              required
              style={{ maxWidth: "1000px", margin: "0 auto" }}
            >
              <option value="">Seleccione un nivel</option>
              {Object.keys(tierTypeMap).map((key) => (
                <option key={key} value={key}>
                  {tierTypeMap[key]}
                </option>
              ))}
            </Input>
          </div>

          <div
            className="custom-button-row"
            style={{
              display: "flex",
              justifyContent: "center",
              marginTop: "20px",
            }}
          >
            <Button
              color="primary"
              type="submit"
              style={{ marginRight: "10px", width: "120px" }}
            >
              Guardar
            </Button>
            <Button
              color="secondary"
              onClick={() => navigate("/achievements")}
              style={{ width: "120px" }}
            >
              Cancelar
            </Button>
          </div>
        </Form>
      </CenteredCard>
    </BackgroundContainer>
  );
}
