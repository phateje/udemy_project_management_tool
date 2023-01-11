import reducers from ".";
import { GET_NEW_PROJECT } from "../actions/types";

const initialState = {};

function newProjectReducer(state = initialState, action) {
  // console.log("data reducer: ", state, action);
  switch (action.type) {
    case GET_NEW_PROJECT:
      return action.payload;
    default:
      return {}; // like for the error reducer, if I create a good project, then get a bad request, I don't want a misleading stale project hanging out in here
  }
}

export default newProjectReducer;
