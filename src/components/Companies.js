import api from '../utils/axiosConfig';

// API çağrıları
const fetchCompanies = async () => {
    try {
        const response = await api.get('/api/companies');
        setCompanies(response.data);
    } catch (error) {
        setError('Firmalar yüklenirken hata oluştu');
    }
}; 