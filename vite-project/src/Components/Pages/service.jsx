import useCheckTokenExpiration from './useCheckTokenExpiration';

const Service = () =>  {
    useCheckTokenExpiration();
    return (
        <div>
            <h1>Welcome to the service page</h1>
        </div>
    );
}

export default Service;