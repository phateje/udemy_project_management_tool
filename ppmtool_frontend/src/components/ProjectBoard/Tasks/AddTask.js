import React from "react";
import { Link, useParams, useNavigate, useLoaderData } from "react-router-dom";
import { getTask, upsertTask } from "../../../actions/projectActions";

export async function loader({ params }) {
  console.log("loader params", params);
  if (!params.taskId) return;

  let res = await getTask(params.taskId)();
  if (!res) {
    throw new Error(`Something broke :(`);
  }
  return res;
}

export default function AddProjectTask() {
  const projectId = useParams().projectId;
  const navigate = useNavigate();
  const loadedTask = { ...useLoaderData() };

  console.log("loaded task", loadedTask);

  const handleSubmit = async (evt) => {
    evt.preventDefault();

    let task = { ...loadedTask };
    [...evt.target]
      .filter((fld) => fld.name)
      .map((fld) => (task[fld.name] = fld.value));
    console.log("the task: ", task, projectId);

    const res = await upsertTask(projectId, task)();
    console.log("the response: ", res);

    navigate(`/projectBoard/${projectId}`);
  };

  return (
    <div className="add-PBI">
      <div className="container">
        <div className="row">
          <div className="col-md-8 m-auto">
            <Link to={`/projectBoard/${projectId}`} className="btn btn-light">
              Back to Project Board
            </Link>
            <h4 className="display-4 text-center">
              {loadedTask.projectSequence ? "Update" : "Add"} Task
            </h4>
            {loadedTask.projectSequence ? (
              <p className="lead text-center">{loadedTask.projectSequence}</p>
            ) : (
              ""
            )}

            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <input
                  type="text"
                  className="form-control form-control-lg"
                  name="summary"
                  placeholder="Project Task summary"
                  defaultValue={loadedTask.summary}
                />
              </div>
              <div className="form-group">
                <textarea
                  className="form-control form-control-lg"
                  placeholder="Acceptance Criteria"
                  name="acceptanceCriteria"
                  defaultValue={loadedTask.acceptanceCriteria}
                />
              </div>
              <h6>Due Date</h6>
              <div className="form-group">
                <input
                  type="date"
                  className="form-control form-control-lg"
                  name="dueDate"
                  defaultValue={loadedTask.dueDate?.slice(0, 10)}
                />
              </div>
              <div className="form-group">
                <select
                  className="form-control form-control-lg"
                  name="priority"
                  defaultValue={loadedTask.priority}
                >
                  <option value={0}>Select Priority</option>
                  <option value={1}>High</option>
                  <option value={2}>Medium</option>
                  <option value={3}>Low</option>
                </select>
              </div>

              <div className="form-group">
                <select
                  className="form-control form-control-lg"
                  name="status"
                  defaultValue={loadedTask.status}
                >
                  <option value="TO DO">TO DO</option>
                  <option value="IN PROGRESS">IN PROGRESS</option>
                  <option value="DONE">DONE</option>
                </select>
              </div>

              <input type="submit" className="btn btn-primary btn-block mt-4" />
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
