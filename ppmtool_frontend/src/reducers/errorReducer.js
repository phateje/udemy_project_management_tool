import reducers from ".";
import { GET_ERRORS } from "../actions/types";

const initialState = {};

function errorReducer(state = initialState, action) {
  // console.log("error reducer: ", state, action);
  switch (action.type) {
    case GET_ERRORS:
      return action.payload;
    default:
      return {}; // I don't want the initial state here, if we don't have any errors I don't want the old errors carrying forward
  }
}

export default errorReducer;
