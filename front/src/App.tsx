import {Route, Routes } from "react-router-dom";

import LoginPage from "./pages/LoginPage";
import SellerPage from "./pages/SellerPage";
import ClientRegistrationPage from "./pages/ClientRegistrationPage";
import SellerRegistrationPage from "./pages/SellerRegistrationPage";
import ShopDetailsPage from "./pages/ShopDetailsPage";
import ProductPage from "./pages/ProductPage";
import ClientPage from "./pages/ClientPage";





function  App() {
  return (
    <Routes>
      <Route path="/" element = {<LoginPage />} />
      <Route path="/client" element = {<ClientPage />} />
      <Route path="/seller" element = {<SellerPage />} />
      <Route path="/registration_for_seller" element = {<SellerRegistrationPage />} />
      <Route path="/registration_for_buyer" element = {<ClientRegistrationPage/>} />
      <Route path="/shop/:id" element={<ShopDetailsPage />} />
      <Route path="/product/:id" element={<ProductPage />} />
    </Routes>
  );
}

export default App;
