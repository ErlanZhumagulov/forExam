import React, { useState } from 'react';
import { loginAsBuyer, loginAsSeller } from '../services/auth';
import { useNavigate } from 'react-router-dom';
import './styles_page/login.css';


const LoginPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    // ВХОД ДЛЯ ПОКУПАТЕЛЯ
    const handleLoginAsBuyer = async () => {
        try {

            if (await loginAsBuyer(username, password) == "NEXT") navigate('/client');

        } catch (error) {
            // Обработка ошибок
        }
    };

    const handleLoginAsSeller = async () => {
        try {

            if (await loginAsSeller(username, password) == "NEXT") navigate('/seller');

        } catch (error) {
            // Обработка ошибок
        }
    };




    const registrationAsBuyer = async () => {
        try {

            navigate('/registration_for_buyer')
        } catch (error) {
            // Обработка ошибок
        }
    };

    const registrationAsSeller = async () => {
        try {
            navigate('/registration_for_seller')
        } catch (error) {
            // Обработка ошибок
        }
    };

    return (
        <>
            <div className="login-container">
                <input
                    className="login-input"
                    type="text"
                    placeholder="Логин"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
                <input
                    className="login-input"
                    type="password"
                    placeholder="Пароль"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <div className="login-buttons">
                    <button className="login-button" onClick={handleLoginAsBuyer}>
                        Войти как покупатель
                    </button>
                    <button className="login-button" onClick={handleLoginAsSeller}>
                        Войти как продавец
                    </button>
                    <button className="login-button" onClick={registrationAsBuyer}>
                        Зарегистрироваться как покупатель
                    </button>
                    <button className="login-button" onClick={registrationAsSeller}>
                        Зарегистрироваться как продавец
                    </button>
                </div>
            </div>

        </>
    );
};

export default LoginPage;



