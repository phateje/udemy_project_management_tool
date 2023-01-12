import reducers from ".";
import { CREATE_NEW_PROJECT, GET_ALL_PROJECTS } from "../actions/types";

const initialState = {};

function newProjectReducer(state = initialState, action) {
  // console.log("data reducer: ", state, action);
  switch (action.type) {
    case CREATE_NEW_PROJECT:
      return action.payload;
    default:
      return {}; // like for the error reducer, if I create a good project, then get a bad request, I don't want a misleading stale project hanging out in here
  }
}

function getProjectsReducer(state = [], action) {
  switch (action.type) {
    case GET_ALL_PROJECTS:
      return action.payload;
    default:
      return state;
  }
}

export default newProjectReducer;
