import React from "react";

export default function Task(props) {
  const task = props.task;
  return (
    <div className="card mb-1 bg-light">
      <div className="card-header text-primary">
        ID: {task.projectSequence} --{" "}
        {{ 1: "High", 2: "Medium", 3: "Low" }[task.priority]}
      </div>
      <div className="card-body bg-light">
        <h5 className="card-title">{task.summary}</h5>
        <p className="card-text text-truncate ">{task.acceptanceCriteria}</p>
        <a href="" className="btn btn-primary">
          View / Update
        </a>

        <button className="btn btn-danger ml-4">Delete</button>
      </div>
    </div>
  );
}
