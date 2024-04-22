import useCheckTokenExpiration from '../../Utils/useCheckTokenExpiration.jsx';

const Service = () =>  {
    useCheckTokenExpiration();
    return (
        <div>
            <h1>Welcome to the service page</h1>
        </div>
    );
}

export default Service;