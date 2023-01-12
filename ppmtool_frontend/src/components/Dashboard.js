import React, { Component } from "react";
import CreateProjectButton from "./Project/CreateProjectButton";
import ProjectItem from "./Project/ProjectItem";
import { PropTypes } from "prop-types";
import { getAllProjects } from "../actions/projectActions";
import { connect } from "react-redux";

class Dashboard extends Component {
  constructor() {
    super();
    this.state = {
      projects: [],
    };

    // huehuehue, props not available in this context, so leverage js event order
    setTimeout(() => console.log(this.props.getAllProjects()), 0);
  }

  render() {
    return (
      <div className="projects">
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              <h1 className="display-4 text-center">Projects</h1>
              <br />
              <CreateProjectButton />
              <br />
              <hr />
              <ProjectItem />
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Dashboard.propTypes = {
  getAllProjects: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired,
};

export default connect(
  (state) => {
    return {
      errors: state.errors,
      projects: state.projects,
    };
  },
  { getAllProjects }
)(Dashboard);
