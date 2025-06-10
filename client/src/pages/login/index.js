import React from 'react';
import './styles.css';
import logo from '../../assets/m.jpg';
import padlock from '../../assets/padlock.jpg';

export default function Login(){
    return (
        <div className="login-container">
            <section className="form">
                <img src={logo} className="logo" alt="logo" />
                <form>
                    <h1>Acess your account</h1>
                    <input placeholder="Username" />
                    <input type="password" placeholder="Password" />
                    <button className="button" type="submit">Login</button>
                </form>
            </section>
            <img src={padlock} alt="padlock"/>
        </div>
    )
}