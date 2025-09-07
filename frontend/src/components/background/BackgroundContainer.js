import React from 'react';
import './css/BackgroundContainer.css';

export default function BackgroundContainer({ children }) {
  return (
    <div className="background-container">
      {children}
    </div>
  );
}