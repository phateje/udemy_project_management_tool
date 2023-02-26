import Dashboard from "./components/Dashboard";
import Header from "./components/Layout/Header";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import AddProject from "./components/Project/AddProject";
import UpdateProject from "./components/Project/UpdateProject";
import { Provider } from "react-redux";
import store from "./store";

function App() {
  return (
    <Provider store={store}>
      <Router>
        <div className="App">
          <Header />

          <Routes>
            <Route exact path="/" element={<Dashboard />}></Route>
            <Route exact path="/addProject" element={<AddProject />}></Route>
            <Route
              exact
              path="/updateProject/:projectId"
              element={<UpdateProject />}
            ></Route>
          </Routes>
        </div>
      </Router>
    </Provider>
  );
}

export default App;
