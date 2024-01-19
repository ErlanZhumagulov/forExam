import axios from 'axios';
import { getTokenFromLocalStorage, saveTokenToLocalStorage } from './token';


interface TokenResponse {
    token: string;
}

interface RegistrationRequest {
    firstname: string;
    lastname: string;
    email: string;
    password: string;
    role: string;
    x: number,
    y: number
}







export const univarsalRegistration = async (firstname: string, lastname: string, username: string, password: string, role: string, xCoordinate: number, yCoordinate: number) => {

    // const config = {
    //     headers: {
    //         'Content-Type': 'application/json',
    //     }
    // };

    const requestBody: RegistrationRequest = {

        firstname: firstname,
        lastname: lastname,
        email: username,
        password: password,
        role: role,
        x: xCoordinate,
        y: yCoordinate
    };

  //  console.log(requestBody.xCoordinate)
    console.log(yCoordinate)
    try {

        const response = await axios.post<RegistrationRequest>('http://localhost:8081/api/v1/auth/register', requestBody);
        console.log(response.data); // Результат запроса

        const jsonString: string = JSON.stringify(response.data);
        const jsonObject: any = JSON.parse(jsonString);
        const token: string = jsonObject.token;

        console.log(token);

        return token;
    }



    catch (error) {
        console.error('Ошибка при регистрации пользователя:', error);
    }

};

