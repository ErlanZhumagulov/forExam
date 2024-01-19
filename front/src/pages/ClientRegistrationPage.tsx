import React, { useState } from 'react';
import { loginAsBuyer, loginAsSeller } from '../services/auth';
import { useNavigate } from 'react-router-dom';
import { univarsalRegistration } from '../services/registration';
import MapComponent from '../components/MapComponent';
import { YMaps, Map, Placemark } from "@pbe/react-yandex-maps";





const ClientRegistrationPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [firstname, setFirstname] = useState('');
    const [lastname, setLastname] = useState('');

    const [placemarkGeometry, setPlacemarkGeometry] = useState([55.753215, 37.622504]);

    const [center, setCenterCoordinates] = useState([55.753215, 37.622504]);

    const mapState = {
      center, // Координаты центра карты
      zoom: 10 // Масштаб карты
    };
  
    const handleMapClick = (event: any) => {
      const coordinates = event.get('coords'); // Получаем координаты места, на которое кликнули
      console.log('Clicked coordinates:', coordinates);
      setPlacemarkGeometry(coordinates); // Обновляем координаты метки
      setCenterCoordinates(coordinates);

    };


    const registrationAsBuyer = async () => {
        try {
            const token = await univarsalRegistration(firstname, lastname, username, password, "USER", placemarkGeometry[0], placemarkGeometry[1]);

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
            <button onClick={registrationAsBuyer}>Зарегистрироваться как покупатель</button>
            <div>
                <YMaps>
                    <Map
                        state={mapState}
                        onClick={handleMapClick}
                        width="100%"
                        height="400px"
                    >
                        <Placemark geometry={placemarkGeometry} /> {/* Местоположение метки */}
                    </Map>
                </YMaps>
            </div>
        </div>
    );
};

export default ClientRegistrationPage;