import reducers from ".";
import { GET_NEW_PROJECT } from "../actions/types";

const initialState = {};

function newProjectReducer(state = initialState, action) {
  switch (action.type) {
    case GET_NEW_PROJECT:
      return action.payload;
    default:
      return state;
  }
}

export default newProjectReducer;
