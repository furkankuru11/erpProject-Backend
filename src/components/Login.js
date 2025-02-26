import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('/api/auth/login', {
                email: email,
                password: password
            });
            
            if (response.data.token) {
                localStorage.setItem('token', response.data.token);
                // Anasayfaya yönlendir
                navigate('/');
            }
        } catch (error) {
            setError('Giriş başarısız');
        }
    };

    return (
        <div>
            {/* Form için gerekli kodlar burada olacak */}
        </div>
    );
};

export default Login; 