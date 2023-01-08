import { initializeConnect } from "react-redux/es/components/connect";
import { createStore, applyMiddleware, compose } from "redux";
import thunk from "redux-thunk";
import rootReducer from "./reducers";

const initState = {};
const middleware = [thunk];

let store;

// this if else is only there to setup stuff for the redux chrome extension and kinda ghastly
// will try to fix it later, unimportant for now
if (window.navigator.userAgent.includes("Chrome") && false) {
  // this causes a funky error in the redux library, likely cause I don't have the redux devtools installed?
  store = createStore(
    rootReducer,
    initState,
    compose(
      applyMiddleware(...middleware),
      window.__REDUX_DEVTOOLS_EXTENSION__ &&
        window.__REDUX_DEVTOOLS_EXTENSION__()
    )
  );
} else {
  store = createStore(
    rootReducer,
    initState,
    compose(applyMiddleware(...middleware))
  );
}

export default store;
