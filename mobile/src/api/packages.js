const API_URL = 'http://192.168.25.216:8083';

export async function fetchAllPackages() {
    const response = await fetch(`${API_URL}/package/all`);

    if (!response.ok) {
        throw new Error(`The server responded with status ${response.status}`);
    }

    return response.json();
}