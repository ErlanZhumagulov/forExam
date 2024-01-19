import { YMaps, Map, Placemark } from "@pbe/react-yandex-maps";
import { useEffect, useState } from "react";

const MapComponent = () => {
  const [placemarkGeometry, setPlacemarkGeometry] = useState([55.753215, 37.622504]);

  const mapState = {
    center: [55.753215, 37.622504], // Координаты центра карты
    zoom: 10 // Масштаб карты
  };

  const handleMapClick = (event: any) => {
    const coordinates = event.get('coords'); // Получаем координаты места, на которое кликнули
    console.log('Clicked coordinates:', coordinates);
    setPlacemarkGeometry(coordinates); // Обновляем координаты метки
  };

  return (
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
  );
};

export default  MapComponent