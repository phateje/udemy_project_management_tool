import { getProject } from "../../actions/projectActions";
import { useLoaderData } from "react-router";

export async function loader({ params }) {
  let res = await getProject(params.projectId)();
  return res;
}

export default function UpdateProject() {
  const proj = useLoaderData();
  let newProj = { ...proj };

  function handleChange(event) {
    // gotta use defaultValue or the fields won't be editable
    // newProj is the thing we can commit now :)
    newProj[event.target.getAttribute("data-field")] = event.target.value;
  }

  return (
    <div className="project">
      <div className="container">
        <div className="row">
          <div className="col-md-8 m-auto">
            <h5 className="display-4 text-center">Update Project form</h5>
            <hr />
            <form>
              <div className="form-group">
                <input
                  type="text"
                  className="form-control form-control-lg "
                  placeholder="Project Name"
                  defaultValue={proj.projectName}
                  onChange={handleChange}
                  data-field="projectName"
                />
              </div>
              <div className="form-group">
                <input
                  type="text"
                  className="form-control form-control-lg"
                  placeholder="Unique Project ID"
                  disabled
                  defaultValue={proj.projectId}
                />
              </div>
              <div className="form-group">
                <textarea
                  className="form-control form-control-lg"
                  placeholder="Project Description"
                  defaultValue={proj.description}
                  onChange={handleChange}
                  data-field="description"
                />
              </div>
              <h6>Start Date</h6>
              <div className="form-group">
                <input
                  type="date"
                  className="form-control form-control-lg"
                  name="start_date"
                  defaultValue={proj.startDate.slice(0, 10)}
                  onChange={handleChange}
                  data-field="startDate"
                />
              </div>
              <h6>Estimated End Date</h6>
              <div className="form-group">
                <input
                  type="date"
                  className="form-control form-control-lg"
                  name="end_date"
                  defaultValue={proj.endDate.slice(0, 10)}
                  onChange={handleChange}
                  data-field="endDate"
                />
              </div>

              <input type="submit" className="btn btn-primary btn-block mt-4" />
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
