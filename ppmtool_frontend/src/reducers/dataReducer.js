import {
  CREATE_NEW_PROJECT,
  GET_ALL_PROJECTS,
  GET_PROJECT,
} from "../actions/types";

const initialState = {
  projects: [],
  project: {},
};

function newProjectReducer(state = initialState, action) {
  // console.log("data reducer: ", state, action);
  switch (action.type) {
    case CREATE_NEW_PROJECT:
      return {
        ...state,
        project: action.payload,
      };
    default:
      return initialState.project;
  }
}

function getProjectsReducer(state = initialState, action) {
  console.log("getProjectsReducer", state, action);
  switch (action.type) {
    case GET_ALL_PROJECTS:
      return {
        ...state,
        projects: action.payload,
      };
    case GET_PROJECT:
      return {
        ...state,
        project: action.payload,
      };
    default:
      return state;
  }
}

export { newProjectReducer, getProjectsReducer };
