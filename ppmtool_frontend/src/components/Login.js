import React from "react";
import { login } from "../actions/projectActions";

export default function Login() {
  const handleSubmit = async (evt) => {
    evt.preventDefault();
    const email = evt.target[0].value;
    const password = evt.target[1].value;
    const res = await login(email, password)();
    console.log(res);

    // TODO redirect to dashboard, also figure out if router can conditionally display the login page
    // when not logged in, or automatically redirect from index to dashboard if you are already logged in.
    // proper rest urls feel nicer than the same url displaying different stuff
  };

  return (
    <div>
      <div className="project">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h5 className="display-4 text-center">Login</h5>

              <form onSubmit={handleSubmit}>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    name="email"
                    placeholder="your email"
                  />
                  <input
                    type="password"
                    className="form-control form-control-lg"
                    name="password"
                    placeholder="your password"
                  />
                  <input
                    type="submit"
                    className="btn btn-primary btn-block mt-4"
                  />
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
