import { GET_BACKLOG, GET_TASK, DELETE_TASK } from "../actions/types";

const initialState = {
  task: {},
  tasks: [],
};

function reduce(state = initialState, action) {
  switch (action.type) {
    case GET_BACKLOG:
      return {
        ...state,
        tasks: action.payload,
      };

    case GET_TASK:
      return {
        ...state,
        task: action.payload,
      };

    case DELETE_TASK:
      return {
        // remove deleted task from the tasks list here probably..
        // let's even see if I'll use these reducers
        ...state,
      };

    default:
      return state;
  }
}

export default reduce;
