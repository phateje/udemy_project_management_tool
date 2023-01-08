import { combineReducers } from "redux";
import errorReducer from "./errorReducer";

export default combineReducers({
  // all reducers we're creating will be stuffed in here
  errors: errorReducer,
});
