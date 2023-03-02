import { useRouteError } from "react-router-dom";

export default function ErrorPage() {
  const error = useRouteError();
  console.error("yo wtf", error);

  return (
    <div id="error-page">
      <h1>Oops!</h1>
      <p>Sorry, an unexpected error has occurred.</p>

      {!error.response && (
        <p>
          <i>{error.statusText || error.message}</i>
        </p>
      )}

      {error.response?.data &&
        Object.keys(error.response.data).map((heading) => (
          <div>
            <p>{heading}</p>
            <ul>
              {error.response.data[heading].map((err) => (
                <li>{err}</li>
              ))}
            </ul>
          </div>
        ))}
    </div>
  );
}
