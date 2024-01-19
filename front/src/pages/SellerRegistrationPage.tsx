import React, { useState } from 'react';
import { loginAsBuyer, loginAsSeller } from '../services/auth';
import { useNavigate } from 'react-router-dom';
import { univarsalRegistration } from '../services/registration';




const SellerRegistrationPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [firstname, setFirstname] = useState('');
    const [lastname, setLastname] = useState('');
 


    const registrationAsBuyer = async () => {
        try {
            const token = await univarsalRegistration(firstname, lastname, username, password, "ADMIN", 0, 0);

            // navigate('/seller')

        } catch (error) {
            // Обработка ошибок
        }
    };






    return (
        <div>
              <input
                type="text"
                placeholder="Имя"
                value={firstname}
                onChange={(e) => setFirstname(e.target.value)}
            />
            <input
                type="text"
                placeholder="Фамилия"
                value={lastname}
                onChange={(e) => setLastname(e.target.value)}
            />
            <input
                type="text"
                placeholder="Логин"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
            />
            <input
                type="password"
                placeholder="Пароль"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <button onClick={registrationAsBuyer}>Зарегистрироваться как продавец</button>
        </div>
    );
};

export default SellerRegistrationPage;