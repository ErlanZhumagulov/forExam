import axios, { AxiosInstance } from 'axios';




export const getTokenFromLocalStorage = () => {
    const token = localStorage.getItem('token') as string;


    return token;
};


export const saveTokenToLocalStorage = (token: string) => {
    localStorage.setItem('token', token);
};