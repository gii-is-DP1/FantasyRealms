import { useEffect } from "react";
import tokenService from "../services/token.service";

const Connection = () => {
  useEffect(() => {
    const sendHeartbeat = async () => {
    const jwt = tokenService.getLocalAccessToken();
      if (jwt) {
        try {
          await fetch("/api/v1/heartbeat", {
            method: "POST",
            headers: {
              Authorization: `Bearer ${jwt}`,
            },
          });
        } catch (error) {
          console.error("Failed to send heartbeat:", error);
        }
      }
    };

    const intervalId = setInterval(sendHeartbeat, 15000);

    return () => clearInterval(intervalId);
  }, []);

  return null;
};

export default Connection;
