import React, { useEffect, useState } from 'react'
import { addMarket, getMarkets, getToken } from '../services/demo'
import { saveImage } from '../services/loadImage';
import ShopItem from '../components/ShopItem';
import './styles_page/seller.css';
import { YMaps, Map, Placemark } from "@pbe/react-yandex-maps";




const SellerPage = () => {
    const [selectedImage, setSelectedImage] = useState<File | null | undefined>(null);

    const [nameMarket, setNameMarket] = useState('');
    const [descriptionMarket, setDescriptionMarket] = useState('');
    const [addressMarket, setAddressMarket] = useState('');

    const [markets, setMarkets] = useState<ShopItem[]>([]);


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


    const handleImageChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const file = event.target.files?.[0];
        setSelectedImage(file);
    };

    const handleUpload = async () => {
        if (selectedImage) {
            try {
                const imageUrl = await saveImage(selectedImage);
                console.log('Загруженное изображение:', imageUrl);
            } catch (error) {
                console.error('Ошибка загрузки изображения:', error);
            }
        }
    };

    const setMarketName = async () => {
        if (nameMarket) {
            try {
                addMarket(nameMarket, descriptionMarket, addressMarket, placemarkGeometry[0], placemarkGeometry[1]);
                console.log('Отправили запрос на установку названия магазина');
            } catch (error) {
                console.error('Ошибка установления названия:', error);
            }
        }
    };



    interface ShopItem {
        id: number;
        marketName: string;
        marketDescription: string;
        address: string;
        x: number;
        y: number;
        seller: any;
        products: any[];

    }



    useEffect(() => {
        const fetchData = async () => {
            try {
                const fetchedMarkets = await getMarkets();
                setMarkets(fetchedMarkets);
            } catch (error) {
                console.log('Error fetching markets:', error);
            }
        };

        fetchData();
    }, []);


    const show = async () => {
        console.log(markets);
    };



    return (
        <>
            <div className="seller-container">
                <div className="seller-greeting">Привет, продавец</div>
    

                <input
                    className="seller-input"
                    type="text"
                    placeholder="Название магазина"
                    value={nameMarket}
                    onChange={(e) => setNameMarket(e.target.value)}
                />
                <input
                    className="seller-input"
                    type="text"
                    placeholder="Описание магазина"
                    value={descriptionMarket}
                    onChange={(e) => setDescriptionMarket(e.target.value)}
                />
                <input
                    className="seller-input"
                    type="text"
                    placeholder="Адрес магазина"
                    value={addressMarket}
                    onChange={(e) => setAddressMarket(e.target.value)}
                />
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
                <button className="seller-button" onClick={setMarketName}>
                    Имя магазина
                </button>
           

                <div className="shop-items">
                    {markets.map((market) => (
                        <ShopItem
                            key={market.id}
                            id={market.id}
                            marketName={market.marketName}
                            marketDescription={market.marketDescription}
                            address={market.address}
                            seller={market.seller}
                            products={market.products}
                        />
                    ))}
                </div>
            </div>
        </>
    );
};

export default SellerPage;



