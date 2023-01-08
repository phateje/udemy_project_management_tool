import axios from "axios";
import { GET_ERRORS } from "./types";

export const createProject = (project, history) => async (dispatch) => {
  try {
    const res = await axios.post("http://localhost:8080/api/project", project);

    // this is old as balls stuff, so got to figure out an alternative
    // https://stackoverflow.com/questions/71638639/react-routers-v6-history-push-giving-error-of-undefined-reading-pathnam
    //history.push("/dashboard");
  } catch (err) {
    console.error("whoops!", err);
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data,
    });
  }
};
