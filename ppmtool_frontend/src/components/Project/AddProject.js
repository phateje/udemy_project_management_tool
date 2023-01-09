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

  componentWillReceiveProps(nextProps) {
    console.log("next props!", nextProps);
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  render() {
    return (
      <div>
        {
          //check name attribute input fields
          //create constructor
          //set state
          //set value on input fields
          //create onChange function
          //set onChange on each input field
          //bind on constructor
          //check state change in the react extension
        }

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
                      className="form-control form-control-lg "
                      placeholder="Project Name"
                      name="projectName"
                      value={this.state?.projectName}
                      onChange={this.onChange}
                    />
                  </div>
                  <div className="form-group mb-2">
                    <input
                      type="text"
                      className="form-control form-control-lg"
                      placeholder="Unique Project ID"
                      name="projectId"
                      value={this.state?.projectId}
                      onChange={this.onChange}
                    />
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
  console.log("mapping state to props: ", state);
  return {
    errors: state.errors,
  };
};

export default connect(mapStateToProps, { createProject })(
  AddProject,
  window.history
);
