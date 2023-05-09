console.log("hi fren");

async function post(url, body) {
  const rawResponse = await fetch(url, {
    method: "POST",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  });
  return await rawResponse.json();
}

(async () => {
  const localhost = "http://localhost:8080/api";
  const id = "1201";

  // console.log("fetching " + `${localhost}/project/${id}`);
  // let proj = await (await fetch(`${localhost}/project/${id}`)).json();
  // console.log(proj);

  let res = await post(`${localhost}/project`, {
    projectName: "testProject",
    projectId: id,
    description: "this is a script loaded test project woo",
  });
  console.log(`create project response: ${res}`);

  [
    {
      summary: "test 1",
      status: "TO DO",
    },
    {
      summary: "test 2",
      status: "TO DO",
    },
    {
      summary: "test 3",
      status: "IN PROGRESS",
    },
    {
      summary: "test 4",
      status: "TO DO",
    },
    {
      summary: "test 5",
      status: "IN PROGRESS",
    },
    {
      summary: "test 6",
      status: "TO DO",
    },
    {
      summary: "test 7",
      status: "DONE",
    },
    {
      summary: "test 8",
      status: "TO DO",
    },
    {
      summary: "test 9",
      status: "DONE",
    },
  ].forEach(async (task, idx) => {
    let res = await post(`${localhost}/task/${id}`, task);
    console.log(`create task ${idx} response: `, res);
  });
})();
