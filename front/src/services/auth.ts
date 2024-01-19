import axios from 'axios';
import { saveTokenToLocalStorage } from './token';


interface TokenResponse {
    token: string;
}

interface AuthenticationRequest {
    email: string;
    password: string;
    role: string;
}






async function getToken(email: string, password: string, role: string) {
    const requestBody: AuthenticationRequest = {

        email: email,
        password: password,
        role: role
    };

    try {
        const response = await axios.post<AuthenticationRequest>('http://localhost:8081/api/v1/auth/authentication', requestBody);
        console.log(response.data); // Результат запроса

        const jsonString: string = JSON.stringify(response.data);
        const jsonObject: any = JSON.parse(jsonString);
        const token: string = jsonObject.token;

        console.log(token);

        return token;
    }

    catch (error) {
        console.error('Ошибка при получении токена:', error);
        throw error;
    }

}



export const loginAsBuyer = async (username: string, password: string) => {
    try {
        const token = await getToken(username, password, "USER");
       
 
        saveTokenToLocalStorage(token);
        console.log('Полученный токен:', token);

        if(token != undefined) return("NEXT")

    } catch (error) {
        console.error('Ошибка при обработке токена:', error);
    }

};

export const loginAsSeller = async (username: string, password: string)=> {
    try {
        const token = await getToken(username, password, "ADMIN");
       
 
        saveTokenToLocalStorage(token);
        console.log('Полученный токен:', token);
        if(token != undefined) return("NEXT")

    } catch (error) {
        console.error('Ошибка при обработке токена:', error);
    }

};
