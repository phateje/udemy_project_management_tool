import { React, useState } from "react";
import { Link, useLoaderData, useParams } from "react-router-dom";
import { getTasks } from "../../actions/projectActions";
import Task from "./Tasks/Task";

export async function loader({ params }) {
  let res = await getTasks(params.projectId)();
  if (!res) {
    throw new Error(`Something broke :(`);
  }
  return res;
}

export default function ProjectBoard() {
  const projectId = useParams().projectId;
  const [tasks, setTasks] = useState(useLoaderData());

  const updateTasks = (removedTaskId) => {
    setTasks(tasks.filter((t) => t.projectSequence !== removedTaskId));
  };

  return (
    <div className="container">
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
                <Task task={task} updateTasks={updateTasks} />
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
