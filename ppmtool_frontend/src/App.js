import Header from "./components/Layout/Header";
import "bootstrap/dist/css/bootstrap.min.css";
import { Provider } from "react-redux";
import store from "./store";
import React from "react";
import { Outlet } from "react-router-dom";

function App() {
  return (
    <Provider store={store}>
      <Header />
      <div className="App" id="body">
        <Outlet />
      </div>
    </Provider>
  );
}

export default App;
