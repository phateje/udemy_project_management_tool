import React, { Component } from "react";
import { PropTypes } from "prop-types";
import { connect } from "react-redux";
import { createProject } from "../../actions/projectActions";

class AddProject extends Component {
  constructor() {
    super();

    this.state = {
      projectName: "",
      projectId: "",
      description: "",
      startDate: "",
      endDate: "",
      errors: {},
    };

    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  onChange(evt) {
    this.setState({
      [evt.target.name]: evt.target.value,
    });
  }

  onSubmit(evt) {
    evt.preventDefault();
    const project = {
      projectName: this.state.projectName,
      projectId: this.state.projectId,
      description: this.state.description,
      startDate: this.state.startDate,
      endDate: this.state.endDate,
    };
    console.log(project);
    this.props.createProject(project);
  }

  // this thing is beyond deprecated, look into updating it to something a bit more relevant
  componentWillReceiveProps(nextProps) {
    // console.log("next props!", nextProps);
    this.setState({ errors: nextProps.errors });
  }

  render() {
    // if you don't clone it, the error.message will be added up every time the component re-renders, yikes
    // nasty hack is nasty afterall
    let errors = { ...this.state.errors };
    // nasty hack
    if (errors.message) {
      if (!errors.projectId) {
        errors.projectId = [];
      }
      errors.projectId.push(errors.message);
    }

    return (
      <div>
        <div className="project">
          <div className="container">
            <div className="row">
              <div className="col-md-8 m-auto">
                <h5 className="display-4 text-center">Create Project form</h5>
                <hr />
                <form onSubmit={this.onSubmit}>
                  <div className="form-group mb-2">
                    <input
                      type="text"
                      className={
                        "form-control form-control-lg " +
                        (errors.projectName ? "is-invalid" : "")
                      }
                      placeholder="Project Name"
                      name="projectName"
                      value={this.state?.projectName}
                      onChange={this.onChange}
                    />
                    {errors.projectName ? (
                      <div className="invalid-feedback">
                        {" "}
                        {errors.projectName.join(", ")}{" "}
                      </div>
                    ) : (
                      ""
                    )}
                  </div>
                  <div className="form-group mb-2">
                    <input
                      type="text"
                      className={
                        "form-control form-control-lg " +
                        (errors.projectId || errors.message ? "is-invalid" : "")
                      }
                      placeholder="Unique Project ID"
                      name="projectId"
                      value={this.state?.projectId}
                      onChange={this.onChange}
                    />
                    {errors.projectId || errors.message ? (
                      <div className="invalid-feedback">
                        {errors.projectId?.join(", ")}
                      </div>
                    ) : (
                      ""
                    )}
                  </div>
                  <div className="form-group mb-2">
                    <textarea
                      className="form-control form-control-lg"
                      placeholder="Project Description"
                      name="description"
                      value={this.state?.description}
                      onChange={this.onChange}
                    />
                  </div>
                  <h6>Start Date</h6>
                  <div className="form-group mb-2">
                    <input
                      type="date"
                      className="form-control form-control-lg"
                      name="startDate"
                      value={this.state?.startDate}
                      onChange={this.onChange}
                    />
                  </div>
                  <h6>Estimated End Date</h6>
                  <div className="form-group mb-2">
                    <input
                      type="date"
                      className="form-control form-control-lg"
                      name="endDate"
                      value={this.state?.endDate}
                      onChange={this.onChange}
                    />
                  </div>

                  <input
                    type="submit"
                    className="btn btn-primary btn-block mt-4"
                  />
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

AddProject.propTypes = {
  createProject: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => {
  // console.log("mapping state to props: ", state);
  return {
    errors: state.errors,
    createdProject: { ...state.newProject },
  };
};

export default connect(mapStateToProps, { createProject })(AddProject);
