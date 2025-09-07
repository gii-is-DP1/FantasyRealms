import React, { useEffect, useRef, useState } from 'react';

const Chat = ({ playerName, avatar }) => {
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState('');
  const ws = useRef(null);
  const messagesEndRef = useRef(null);

  useEffect(() => {
    ws.current = new WebSocket('ws://localhost:8080/ws/chat');

    ws.current.onopen = () => {
      console.log("Conectado al servidor WebSocket del backend");
    };

    ws.current.onmessage = (event) => {
      console.log("Mensaje recibido del servidor:", event.data);
      try {
        const parsedMessage = JSON.parse(event.data);
        setMessages((prevMessages) => [...prevMessages, parsedMessage]);
      } catch {
        console.error("Error al parsear el mensaje:", event.data);
      }
    };

    ws.current.onclose = () => {
      console.log("Desconectado del servidor WebSocket");
    };

    ws.current.onerror = (error) => {
      console.error("Error en la conexión WebSocket:", error);
    };

    return () => {
      if (ws.current) {
        ws.current.close();
      }
    };
  }, []);

  useEffect(() => {
    if (messagesEndRef.current) {
      messagesEndRef.current.scrollIntoView({ behavior: 'smooth' });
    }
  }, [messages]);

  const sendMessage = () => {
    if (ws.current && ws.current.readyState === WebSocket.OPEN && input.trim() !== '') {
      const messageData = {
        avatar: avatar,
        playerName: playerName,
        message: input,
      };

      console.log("Enviando mensaje al servidor:", messageData);

      ws.current.send(JSON.stringify(messageData));

      setInput('');
    } else {
      console.error("WebSocket no está en un estado abierto.");
    }
  };

  const handleKeyDown = (event) => {
    if (event.key === 'Enter') {
      sendMessage();
    }
  };

  return (
    <div
      style={{
        display: 'flex',
        flexDirection: 'column',
        width: '100%',
        maxWidth: '30hv',
        border: '1px solid #ddd',
        borderRadius: '5px',
        overflow: 'hidden',
      }}
    >
      <div
        style={{
          padding: '10px',
          overflowY: 'auto',
          height: '30vh',
          backgroundColor: '#f9f9f9',
        }}
      >
        {messages.map((messageObj, index) => (
          <p key={index}>
            <img
              src={messageObj.avatar}
              alt={`${messageObj.playerName}'s avatar`}
              style={{ width: '30px', height: '30px', borderRadius: '50%', marginRight: '10px' }}
            />
            <strong>{messageObj.playerName}:</strong> {messageObj.message}
          </p>
        ))}
        <div ref={messagesEndRef} />
      </div>
      <input
        type="text"
        value={input}
        onChange={(e) => setInput(e.target.value)}
        onKeyDown={handleKeyDown}
        style={{
          width: '80%',
          padding: '10px',
          alignSelf: 'center',
          marginBottom: '2em',
          marginTop: '2em',
        }}
      />
    </div>
  );
};

export default Chat;
