import axios from "axios";
import { GET_ERRORS } from "./types";

export const createProject = (project) => {
  return async (dispatch) => {
    console.log("request with project", project);
    try {
      const res = await axios.post(
        "http://localhost:8080/api/project",
        project
      );
      console.log("response: ", res);
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
