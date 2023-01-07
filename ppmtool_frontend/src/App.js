import Dashboard from "./components/Dashboard";
import Header from "./components/Layout/Header";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import AddProject from "./components/Project/AddProject";

function App() {
  return (
    <Router>
      <div className="App">
        <Header />
        <Dashboard />

        <Routes>
          <Route exact path="/addProject" element={<AddProject />}></Route>
        </Routes>
      </div>
    </Router>
  );
}

export default App;
