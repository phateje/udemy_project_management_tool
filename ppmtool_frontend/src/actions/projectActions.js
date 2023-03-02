import axios from "axios";
import {
  GET_ERRORS,
  CREATE_NEW_PROJECT,
  GET_ALL_PROJECTS,
  GET_PROJECT,
} from "./types";

const createProject = (project) => {
  return async (dispatch) => {
    console.log("request with project", project);
    try {
      const res = await axios.post(
        "http://localhost:8080/api/project",
        project
      );
      console.log("response: ", res);
      dispatch({
        type: CREATE_NEW_PROJECT,
        payload: res.data,
      });
      // window.location.replace("/");
    } catch (err) {
      console.error("whoops!", err);
      dispatch({
        type: GET_ERRORS,
        payload: err.response.data,
      });
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
  return async (dispatch) => {
    console.log("fetch project", id);
    try {
      const res = await axios.get(`http://localhost:8080/api/project/${id}`);
      console.log("response: ", res);
      // todo clean this up,
      // gotta figure out how to pass what happens in the route loader into the props
      // of the component.. sigh
      return res.data;
    } catch (err) {
      console.error("whoops!", err);
      // todo figure out error handling with new routes
      dispatch({
        type: GET_ERRORS,
        payload: err.response.data,
      });
    }
  };
};

export { createProject, getAllProjects, getProject };
