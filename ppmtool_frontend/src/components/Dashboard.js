import React, { Component } from "react";
import CreateProjectButton from "./Project/CreateProjectButton";
import ProjectItem from "./Project/ProjectItem";
import { PropTypes } from "prop-types";
import { getAllProjects } from "../actions/projectActions";
import { connect } from "react-redux";

class Dashboard extends Component {
  componentDidMount() {
    this.props.getAllProjects();
  }

  // componentWillReceiveProps(nextProps) {
  //   console.log("next props!", nextProps, this.props);
  // }

  render() {
    const { projects } = this.props;
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
              {projects.map((project) => (
                <ProjectItem key={project.projectId} project={project} />
              ))}
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
  projects: PropTypes.array.isRequired,
};

export default connect(
  (state) => {
    console.log("connect with state: ", state);

    if (Object.keys(state.errors).length) {
      alert(state.errors.error[0]);
    }

    return {
      errors: state.errors,
      projects: state.getAllProjects.projects,
    };
  },
  { getAllProjects }
)(Dashboard);
