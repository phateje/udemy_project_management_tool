import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import ErrorPage from "./components/ErrorPage";
import AddProject from "./components/Project/AddProject";
import AddTask, {
  loader as taskLoader,
} from "./components/ProjectBoard/Tasks/AddTask";
import Dashboard from "./components/Dashboard";
import UpdateProject, {
  loader as projectLoader,
} from "./components/Project/UpdateProject";
import ProjectBoard, {
  loader as tasksLoader,
} from "./components/ProjectBoard/ProjectBoard";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    errorElement: <ErrorPage />,
    children: [
      {
        path: "/",
        element: <Dashboard />,
      },
      {
        path: "/addProject",
        element: <AddProject />,
      },
      {
        path: "/updateProject/:projectId",
        element: <UpdateProject />,
        loader: projectLoader,
      },
      {
        path: "/projectBoard/:projectId",
        element: <ProjectBoard />,
        loader: tasksLoader,
      },
      {
        path: "/addTask/:projectId",
        element: <AddTask />,
        // todo add loader
      },
      {
        path: "/addTask/:projectId/:taskId",
        element: <AddTask />,
        loader: taskLoader,
      },
    ],
  },
]);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
