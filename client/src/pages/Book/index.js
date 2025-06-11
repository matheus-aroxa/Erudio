import React from 'react';
import './styles.css';
import {Link} from "react-router-dom";
import {FiPower, FiEdit, FiTrash2} from "react-icons/fi";
import logo from '../../assets/m.jpg';

export default function Book(){
    return (
        <div className="book-container">
            <header>
                <img src={logo} alt="logo"/>
                <span>Welcome, <strong>Leandro</strong></span>
                <Link className="button" to="/book/new">Add new book</Link>
                <button type="button">
                    <FiPower size={18} color="251FC5" />
                </button>
            </header>

            <h1>Registered books</h1>
            <ul>
                <li>
                    <strong>Title:</strong>
                    <p>Docker deep dive</p>
                    <strong>Author:</strong>
                    <p>Pedro Miranda</p>
                    <strong>Value:</strong>
                    <p>$19.23</p>
                    <strong>Release date:</strong>
                    <p>25/07/1998</p>
                    <button>
                        <FiEdit size={20} color="251FC5" />
                    </button>
                    <button>
                        <FiTrash2 size={20} color="251FC5" />
                    </button>
                </li>
            </ul>
        </div>
    );
}