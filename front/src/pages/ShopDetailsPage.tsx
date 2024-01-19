import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { addProductInThisMarket } from "../services/shopDetails";
import { getAllProducts } from "../services/demo";
import ProductItem from "../components/ProductItem";


interface Product {
  id: number;
  nameProduct: string;
  price: string;
  description: string;
  availability: string;
  idImages: number[];
  additionalSettingsCriteriaList: string[];
  additionalSettingsValueList: string[];
  categoriesList: string[];
}

interface AdditionalSettingsRequest {
  criteria: string;
  criteriaValue: string;
}



const ShopDetailsPage = () => {

  const [additionalSettings, setAdditionalSettings] = useState<AdditionalSettingsRequest[]>([]);


  const [allProducts, setAllProducts] = useState<Product[]>([]);

  const [selectedImage, setSelectedImage] = useState<File | null | undefined>(null);

  const [nameProduct, setNameProduct] = useState('');

  const [description, setDescription] = useState('');

  const [price, setPrice] = useState(0);

  const [categoryProduct, setCategoryProduct] = useState('');


  const handleInputChange = (index: number, field: string, value: string) => {
    const updatedSettings = [...additionalSettings];
    updatedSettings[index] = {
      ...updatedSettings[index],
      [field]: value
    };
    setAdditionalSettings(updatedSettings);

  };

  const handleImageChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    setSelectedImage(file);
  }

  const { id } = useParams();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const fetchedProducts: Product[] = await getAllProducts(id);
        setAllProducts(fetchedProducts);
        console.log(fetchedProducts)
      } catch (error) {
        console.log('Error fetching markets:', error);
      }
    };

    fetchData();
  }, []);

  const handleCategoryChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setCategoryProduct(event.target.value);
  };
  

  const addProduct = async () => {
    if (selectedImage) {
      try {

        const result = await addProductInThisMarket(selectedImage, description, nameProduct, price, id, additionalSettings, [categoryProduct]);

        console.log(result);



      } catch (error) {
        // Обработка ошибок
      }
    }
  };

  const handleAddParameter = () => {
    setAdditionalSettings([...additionalSettings, { criteria: '', criteriaValue: '' }]);
  };


  const getProducts = async () => {

    setAllProducts(await getAllProducts(id));

  };

  // Здесь вы можете получить информацию о магазине с помощью идентификатора id и отобразить ее

  return (
    <div>
      <h2>Shop Details for ID: {id}</h2>

      <div>
        <label > Добавьте изображение товара:</label>
        <input type="file" onChange={handleImageChange} />
        <br />

        <label > Название товара</label>
        <input type="text" value={nameProduct} onChange={(e) => setNameProduct(e.target.value)} />
        <br />

        <label > Цена товара</label>
        <input type="number" value={price} onChange={(e) => setPrice(Number(e.target.value))} />
        <br />

        <label > Категория </label>
        <select value={categoryProduct} onChange={handleCategoryChange}>
          <option value="">Выберите категорию</option>
          <option value="CATEGORY1">CATEGORY1</option>
          <option value="CATEGORY2">CATEGORY2</option>
          <option value="CATEGORY3">CATEGORY3</option>
        </select>        <br />

        <label > Описание товара</label>
        <input type="text" value={description} onChange={(e) => setDescription(e.target.value)} />

        <button onClick={addProduct}>Добавить товар</button>

        <button onClick={getProducts}>Вывести все товары</button>
      </div>


      {additionalSettings.map((settings, index) => (
        <div key={index} style={{ display: 'flex', gap: '10px', alignItems: 'center' }} >

          <label > Критерий </label>
          <input
            type="text"
            value={settings.criteria}
            onChange={(e) => handleInputChange(index, 'criteria', e.target.value)}
          />
          <label >  Значение критерия  </label>
          <input
            type="text"
            value={settings.criteriaValue}
            onChange={(e) => handleInputChange(index, 'criteriaValue', e.target.value)}
          />


        </div>
      ))}

      <button onClick={handleAddParameter}>Добавить параметр</button>





      <div className="shop-items">
        {allProducts.map((product) => (
          <ProductItem
            key={product.id}
            id={product.id}
            nameProduct={product.nameProduct}
            price={product.price}
            availability={product.availability}
            idImages={product.idImages}
            description={product.description}

            additionalSettingsCriteriaList={product.additionalSettingsCriteriaList}
            additionalSettingsValueList={product.additionalSettingsValueList}
            categoriesList={product.categoriesList}

          />
        ))}
      </div>

    </div>
  );

}

export default ShopDetailsPage;