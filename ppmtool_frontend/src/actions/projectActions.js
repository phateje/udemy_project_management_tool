import axios from "axios";
import {
  GET_ERRORS,
  CREATE_NEW_PROJECT,
  GET_ALL_PROJECTS,
  DELETE_PROJECT,
} from "./types";

const createProject = (project, useDispatch = true) => {
  return async (dispatch) => {
    console.log("request with project", project);
    try {
      const res = await axios.post(
        "http://localhost:8080/api/project",
        project
      );
      console.log("response: ", res);
      if (useDispatch) {
        dispatch({
          type: CREATE_NEW_PROJECT,
          payload: res.data,
        });
      }
      // window.location.replace("/");
    } catch (err) {
      console.error("whoops!", err);
      if (useDispatch) {
        dispatch({
          type: GET_ERRORS,
          payload: err.response.data,
        });
      } else {
        throw err.response;
      }
    }
  };
};

const getAllProjects = () => {
  return async (dispatch) => {
    console.log("fetch all projects");
    try {
      const res = await axios.get("http://localhost:8080/api/project");
      console.log("response: ", res);
      dispatch({
        type: GET_ALL_PROJECTS,
        payload: res.data,
      });
    } catch (err) {
      console.error("whoops!", err);
      dispatch({
        type: GET_ERRORS,
        payload: err.response.data,
      });
    }
  };
};

const getProject = (id) => {
  return async () => {
    const res = await axios.get(`http://localhost:8080/api/project/${id}`);
    return res.data;
  };
};

const deleteProject = (id) => async (dispatch) => {
  try {
    await axios.delete(`http://localhost:8080/api/project/${id}`);
    dispatch({
      type: DELETE_PROJECT,
      projectId: id,
    });
  } catch (err) {
    console.log("error", err);
    dispatch({
      type: GET_ERRORS,
      payload:
        err.response.data === ""
          ? { error: [`couldn't delete project ${id}`] }
          : err.response.data,
    });
  }
};

const getTasks = (id) => {
  return async () => {
    const res = await axios.get(`http://localhost:8080/api/task/all/${id}`);
    return res.data;
  };
};

const getTask = (id) => {
  return async () => {
    const res = await axios.get(`http://localhost:8080/api/task/${id}`);
    return res.data;
  };
};

const upsertTask = (projId, task) => {
  return async () => {
    const res = await axios.post(
      `http://localhost:8080/api/task/${projId}`,
      task
    );
    return res.data;
  };
};

const deleteTask = (id) => {
  return async () => {
    const res = await axios.delete(`http://localhost:8080/api/task/${id}`);
    return res.data;
  };
};

const login = (email, password) => {
  return async () => {
    const res = await axios.post(`http://localhost:8080/api/users/login`, {
      username: email,
      password,
    });
    return res.data;
  };
};

export {
  createProject,
  getAllProjects,
  getProject,
  deleteProject,
  getTasks,
  getTask,
  upsertTask,
  deleteTask,
  login,
};
