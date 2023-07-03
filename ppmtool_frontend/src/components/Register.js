import React from "react";
import { register } from "../actions/projectActions";

export default function Register() {
  const handleSubmit = async (evt) => {
    evt.preventDefault();
    const email = evt.target[0].value;
    const password = evt.target[1].value;
    const confirmPassword = evt.target[2].value;
    const res = await register(email, password, confirmPassword)();
    console.log(res);

    // same todo as login I guess
  };

  return (
    <div>
      <div className="project">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h5 className="display-4 text-center">Register</h5>

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
                    type="password"
                    className="form-control form-control-lg"
                    name="confirm password"
                    placeholder="type your password again"
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
