import React from 'react';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Login from './pages/login';
import Book from "./pages/Book";
import NewBook from "./pages/NewBook";

export default function RoutesApp() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" exact element={<Login />} />
                <Route path="/book" element={<Book />} />
                <Route path="/book/new" element={<NewBook />} />
            </Routes>
        </BrowserRouter>
    );
}