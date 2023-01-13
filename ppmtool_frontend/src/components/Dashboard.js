import React, { Component } from "react";
import CreateProjectButton from "./Project/CreateProjectButton";
import ProjectItem from "./Project/ProjectItem";
import { PropTypes } from "prop-types";
import { getAllProjects } from "../actions/projectActions";
import { connect } from "react-redux";

class Dashboard extends Component {
  async componentDidMount() {
    let lmao = await this.props.getAllProjects();
    console.log("lmao", lmao);
  }

  componentWillReceiveProps(nextProps) {
    console.log("next props!", nextProps);
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
  projects: PropTypes.array.isRequired,
};

export default connect(
  (state) => {
    console.log("connect with state: ", state);
    return {
      errors: state.errors,
      projects: state.getAllProjects.projects,
    };
  },
  { getAllProjects }
)(Dashboard);
