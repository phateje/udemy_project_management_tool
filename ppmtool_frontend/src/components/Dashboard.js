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

  render() {
    const { projects } = this.props.projectReducer;
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
              {projects?.map((project) => (
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
  projectReducer: PropTypes.object.isRequired,
  getAllProjects: PropTypes.func.isRequired,
};

const mapStateToProps = (state) => {
  console.log("mapStateToProps: ", state);
  if (state.errors.error) {
    // hacky temp error alert
    alert(state.errors.error[0]);
  }

  return {
    projectReducer: state.projectReducer,
    errors: {},
  };
};

export default connect(mapStateToProps, { getAllProjects })(Dashboard);
