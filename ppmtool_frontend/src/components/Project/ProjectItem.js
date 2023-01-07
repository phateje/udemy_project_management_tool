import React, { Component } from "react";

export default class ProjectItem extends Component {
  render() {
    return (
      <div className="container">
        <div className="card card-body bg-light mb-3">
          <div className="row">
            <div className="col-2">
              <span className="mx-auto">REACT</span>
            </div>
            <div className="col-lg-6 col-md-4 col-8">
              <h3>Spring / React Project</h3>
              <p>Project to create a Kanban Board with Spring Boot and React</p>
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
                  Update Project Info
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
