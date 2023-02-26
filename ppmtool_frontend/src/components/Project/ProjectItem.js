import React, { Component } from "react";
import { Link } from "react-router-dom";

export default class ProjectItem extends Component {
  render() {
    const { project } = this.props;

    return (
      <div className="container">
        <div className="card card-body bg-light mb-3">
          <div className="row">
            <div className="col-2">
              <span className="mx-auto">{project.projectId}</span>
            </div>
            <div className="col-lg-6 col-md-4 col-8">
              <h3>{project.projectName}</h3>
              <p>{project.description}</p>
            </div>
            <div className="col-md-4 d-none d-lg-block">
              <ul className="list-group">
                <button
                  type="button"
                  className="list-group-item-primary list-group-item "
                >
                  Project Board
                </button>

                <button
                  type="button"
                  className="list-group-item-warning list-group-item "
                >
                  <Link to={`/updateProject/${project.projectId}`}>
                    Update Project Info
                  </Link>
                </button>

                <button
                  type="button"
                  className="list-group-item-danger list-group-item "
                >
                  Delete Project
                </button>
              </ul>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
