import axios from "axios";
import { GET_ERRORS, GET_NEW_PROJECT } from "./types";

export const createProject = (project) => {
  return async (dispatch) => {
    console.log("request with project", project);
    try {
      const res = await axios.post(
        "http://localhost:8080/api/project",
        project
      );
      console.log("response: ", res);
      dispatch({
        type: GET_NEW_PROJECT,
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
