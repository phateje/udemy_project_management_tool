import { combineReducers } from "redux";
import errorReducer from "./errorReducer";
import dataReducer from "./dataReducer";

export default combineReducers({
  // all reducers we're creating will be stuffed in here
  errors: errorReducer,
  newProject: dataReducer,
  projects: dataReducer,
});
