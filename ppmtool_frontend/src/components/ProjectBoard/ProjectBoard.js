import React from "react";
import { Link, useLoaderData } from "react-router-dom";
import { getTasks } from "../../actions/projectActions";
import Task from "./Tasks/Task";

export async function loader({ params }) {
  let res = await getTasks(params.projectId)();
  if (!res) {
    throw new Error(`Something broke :(`);
  }
  // hacky workaround to pass the project id to the actual component body
  // you can use params in the constructor, but I'm not sure how that would
  // work given the use of the router and the fact that we don't have a
  // variable state we can use there
  return { tasks: res, projectId: params.projectId };
}

export default function ProjectBoard() {
  const { tasks, projectId } = useLoaderData();
  console.log("loader data: ", tasks, projectId);
  return (
    <div className="container">
      {/* TODO WIRE ME UP YOO */}
      <Link to={`/addTask/${projectId}`} className="btn btn-primary mb-3">
        <i className="fas fa-plus-circle"> Create Project Task</i>
      </Link>
      <br />
      <hr />
      <div className="container">
        <div className="row">
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-secondary text-white">
                <h3>TO DO</h3>
              </div>
            </div>
            {tasks
              .filter((task) => task.status === "TO DO")
              .map((task) => (
                <Task task={task} />
              ))}
          </div>
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-primary text-white">
                <h3>In Progress</h3>
              </div>
            </div>
            {tasks
              .filter((task) => task.status === "IN PROGRESS")
              .map((task) => (
                <Task task={task} />
              ))}
          </div>
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-success text-white">
                <h3>Done</h3>
              </div>
            </div>
            {tasks
              .filter((task) => task.status === "DONE")
              .map((task) => (
                <Task task={task} />
              ))}
          </div>
        </div>
      </div>
    </div>
  );
}
