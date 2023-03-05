import {
  CREATE_NEW_PROJECT,
  DELETE_PROJECT,
  GET_ALL_PROJECTS,
} from "../actions/types";

const initialState = {
  projects: [],
  project: {},
};

export default function dataReducer(state = initialState, action) {
  console.log("data reducer with action: ", action, "and state: ", state);
  switch (action.type) {
    case CREATE_NEW_PROJECT:
      return {
        ...state,
        project: action.payload,
      };

    case GET_ALL_PROJECTS:
      return {
        ...state,
        projects: action.payload,
      };

    case DELETE_PROJECT:
      return {
        ...state,
        projects: state.projects.filter(
          (p) => p.projectId !== action.projectId
        ),
      };
    default:
      return state;
  }
}
