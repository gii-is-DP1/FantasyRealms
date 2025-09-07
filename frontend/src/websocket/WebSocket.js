const WebSocket = require('ws');

const server = new WebSocket.Server({ port: 8081 });

server.on('connection', (ws) => {
  console.log("Cliente conectado");

  ws.on('message', (message) => {
    console.log("Mensaje recibido del cliente:", message);

    server.clients.forEach((client) => {
      if (client.readyState === WebSocket.OPEN) {
        client.send(message);
      }
    });
  });

  ws.on('close', () => {
    console.log("Cliente desconectado");
  });

  ws.on('error', (error) => {
    console.error("Error en el servidor WebSocket:", error);
  });
});

console.log("Servidor WebSocket en ejecución en el puerto 8081");
