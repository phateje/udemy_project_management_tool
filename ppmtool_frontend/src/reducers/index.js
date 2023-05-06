import { combineReducers } from "redux";
import errorReducer from "./errorReducer";
import dataReducer from "./dataReducer";
import backlogReducer from "./backlogReducer";

export default combineReducers({
  // all reducers we're creating will be stuffed in here
  errors: errorReducer,
  projectReducer: dataReducer,
  backlogReducer: backlogReducer,
});
