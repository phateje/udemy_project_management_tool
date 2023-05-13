import React from "react";
import { Link, useParams } from "react-router-dom";
import { deleteTask } from "../../../actions/projectActions";

export default function Task(props) {
  const task = props.task;
  const updateTasks = props.updateTasks;
  const projectId = useParams().projectId;

  const onDelete = () => {
    deleteTask(task.projectSequence)();
    updateTasks(task.projectSequence);
  };

  return (
    <div className="card mb-1 bg-light">
      <div className="card-header text-primary">
        ID: {task.projectSequence} --{" "}
        {{ 1: "High", 2: "Medium", 3: "Low" }[task.priority]}
      </div>
      <div className="card-body bg-light">
        <h5 className="card-title">{task.summary}</h5>
        <p className="card-text text-truncate ">{task.acceptanceCriteria}</p>
        <Link
          to={`/addTask/${projectId}/${task.projectSequence}`}
          className="btn btn-primary"
        >
          View / Update
        </Link>

        <button className="btn btn-danger ml-4" onClick={onDelete}>
          Delete
        </button>
      </div>
    </div>
  );
}
