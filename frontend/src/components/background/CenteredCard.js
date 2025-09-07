import React from "react";
import { Card } from "reactstrap";

const CenteredCard = ({ children, style }) => (
  <Card
    style={{
      width: "95%",
      alignItems: "space-between",
      backgroundColor: "rgba(255, 255, 255, 0.7)",
      paddingTop: "100px",
      paddingBottom: "100px",
      paddingLeft: "2%",
      paddingRight: "3%",
      borderRadius: "50px",
      border: "10px solid #4a148c",
      boxShadow: "0 0 10px rgba(212, 175, 55, 0.8)", 
      ...style,
    }}
  >
    {children}
  </Card>
);

export default CenteredCard;
