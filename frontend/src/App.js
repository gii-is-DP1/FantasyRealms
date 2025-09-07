import React from "react";
import { Route, Routes } from "react-router-dom";
import jwt_decode from "jwt-decode";
import { ErrorBoundary } from "react-error-boundary";
import AppNavbar from "./AppNavbar";
import Home from "./home";
import PrivateRoute from "./privateRoute";
import Register from "./auth/register";
import Login from "./auth/login";
import Logout from "./auth/logout";
import PlanList from "./public/plan";
import tokenService from "./services/token.service";
import UserListAdmin from "./admin/users/UserListAdmin";
import UserEditAdmin from "./admin/users/UserEditAdmin";
import SwaggerDocs from "./public/swagger";
import Rules from "./rules"; 
import Profile from "./profile";
import MatchList from "./match/MatchList";
import CurrentUser from "./home/currentuser";
import MatchDetail from "./matchDetail/MatchDetail";
import Match from "./match/Match";
import MatchPrivateRoute from "./privateRoute/MatchPrivateRoute";
import MatchRanking from "./match/MatchRanking";
import Friends from "./friends/Friends";
import Connection from "./friends/Connection";
import Ranking from "./ranking/Ranking";
import EditLogrosForm from "./admin/logros/LogrosEditAdmin";
import LogrosEditAdmin from "./admin/logros/LogrosEditAdmin";
import LogrosListAdmin from "./admin/logros/LogrosListAdmin";

function ErrorFallback({ error, resetErrorBoundary }) {
  return (
    <div role="alert">
      <p>Something went wrong:</p>
      <pre>{error.message}</pre>
      <button onClick={resetErrorBoundary}>Try again</button>
    </div>
  )
}

function App() {
  const jwt = tokenService.getLocalAccessToken();
  let roles = [];
  if (jwt) {
    roles = getRolesFromJWT(jwt);
  }

  function getRolesFromJWT(jwt) {
    return jwt_decode(jwt).authorities;
  }

  let adminRoutes = <></>;
  let ownerRoutes = <></>;
  let userRoutes = <></>;

  roles.forEach((role) => {
    if (role === "ADMIN") {
      adminRoutes = (
        <>
          <Route path="/users" exact={true} element={<PrivateRoute><UserListAdmin /></PrivateRoute>} />
          <Route path="/users/:username" exact={true} element={<PrivateRoute><UserEditAdmin /></PrivateRoute>} />   
          <Route path="/achievements" exact={true} element={<PrivateRoute><LogrosListAdmin /></PrivateRoute>}/>  
          <Route path="/achievements/edit/:userId" exact={true} element={<PrivateRoute><LogrosEditAdmin /></PrivateRoute>}/>   
        </>
      );
    }
    if (role === "PLAYER") {
      ownerRoutes = (
        <>
        </>
      );
    }
  });

  if (!jwt) {
    userRoutes = (
      <>
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
      </>
    );
  } else {
    userRoutes = (
      <>
        <Route path="/logout" element={<Logout />} />
        <Route path="/login" element={<Login />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/matches" element={<MatchList />} />
        <Route path="/currentuser" element={<CurrentUser />} />
        <Route path="/matches/:id" element={<MatchDetail />} />
        <Route path="/matches/create" element={<MatchList />} />
        <Route path="/matches/:id/creator" element={<MatchDetail />} />
        <Route
          path="/matches/:id/game"
          element={
            <MatchPrivateRoute>
              <Match />
            </MatchPrivateRoute>
          }
        />
        <Route path="/matches/:id/ranking" element={<MatchRanking />} />
        <Route path="/friends" element={<Friends />} />
        <Route path="/globalranking" element={<Ranking />} />
      </>
    );
  }

  return (
    <div>
      <ErrorBoundary FallbackComponent={ErrorFallback} >
        <AppNavbar />
        <Connection />
        <Routes>
          <Route path="/" exact={true} element={<Home />} />
          <Route path="/docs" element={<SwaggerDocs />} />
          <Route path="/rules" element={<Rules />} />
          {userRoutes}
          {adminRoutes}
          {ownerRoutes}
        </Routes>
      </ErrorBoundary>
    </div>
  );
}

export default App;
