import React from "react";
import { Card, CardBody, CardTitle } from "reactstrap";

const Section = ({ title, children, style }) => (
  <Card
    className="section-card"
    style={{
      marginBottom: "20px",
      backgroundColor: "rgba(255, 255, 255, 0.75)",
      color: "#000000",
      border: "5px solid #4a148c",
      borderTopLeftRadius: "20px",
      borderBottomRightRadius: "20px",
      boxShadow: "0px 4px 8px rgba(0, 0, 0, 0.1)",
      ...style,
    }}
  >

      <CardTitle tag="h2" style={{ color: "#4a148c" }}>{title}</CardTitle>
        {children}
  </Card>
);

export default Section;
