import './styles.css'
import logo from '../../assets/m.jpg'
import React from 'react';
import {Link} from "react-router-dom";
import {FiArrowLeft} from "react-icons/fi";

export default function NewBook() {
    return (
        <div className="new-book-container">
            <div className="content">
                <section className="form">
                    <img src={logo} alt="logo" />
                    <h1>Add new book</h1>
                    <p>Enter the book information and click on 'Add'</p>
                    <Link className="back-link" to="/book">
                        <FiArrowLeft size={16} color="#251FC5"/>
                        Home
                    </Link>
                </section>
                <form>
                    <input placeholder="Title"/>
                    <input placeholder="Author"/>
                    <input placeholder="Price"/>
                    <input placeholder="Release date"/>
                    <button className="button" type="submit">Add</button>
                </form>
            </div>
        </div>
    );
}