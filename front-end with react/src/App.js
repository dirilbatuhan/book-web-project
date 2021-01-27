import React from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import Register from "./Register";
import Login from "./Login";
import Dashboard from "./Dashboard";

export default function App() {
  return (
    <Router>
      <div>
        <Switch>
          <Route exact path="/">
            <Register />
          </Route>
          <Route path="/login">
            <Login showRegisterLink />
          </Route>
          <Route path="/dashboard">
            <Dashboard />
          </Route>
          <Route path="*">404 | Aradığınız sayfa bulunamadı</Route>
        </Switch>
      </div>
    </Router>
  );
}
