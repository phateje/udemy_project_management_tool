import { combineReducers } from "redux";
import errorReducer from "./errorReducer";
import { newProjectReducer, getProjectsReducer } from "./dataReducer";

export default combineReducers({
  // all reducers we're creating will be stuffed in here
  errors: errorReducer,
  newProject: newProjectReducer,
  getAllProjects: getProjectsReducer,
  getProject: getProjectsReducer,
});
