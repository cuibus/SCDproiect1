import { useState } from 'react';
import {
    ActivityIndicator,
    Pressable,
    StyleSheet,
    Text,
    View,
} from 'react-native';
import { fetchAllPackages } from '../api/packages';
import PackagesTable from '../components/PackagesTable';

export default function PackagesScreen() {
    const [packages, setPackages] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    async function handleLoadPackages() {
        setLoading(true);
        setError('');

        try {
            const data = await fetchAllPackages();
            setPackages(data);
        } catch (err) {
            setError(
                `Could not load packages. Check the server address and your connection. ${err.message}`
            );
        } finally {
            setLoading(false);
        }
    }

    return (
        <View style={styles.container}>
            <Text style={styles.title}>My Packages</Text>

            <Pressable
                style={({ pressed }) => [
                    styles.button,
                    pressed && styles.buttonPressed,
                ]}
                onPress={handleLoadPackages}
                disabled={loading}
            >
                <Text style={styles.buttonText}>
                    {loading ? 'Loading...' : 'Load packages'}
                </Text>
            </Pressable>

            {loading && <ActivityIndicator style={styles.loading} color="#2563EB" />}

            {!!error && <Text style={styles.error}>{error}</Text>}

            {packages.length > 0 && <PackagesTable packages={packages} />}

            {!loading && !error && packages.length === 0 && (
                <Text style={styles.note}>Press the button to load packages.</Text>
            )}
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        paddingTop: 60,
        paddingHorizontal: 16,
        backgroundColor: '#F8FAFC',
    },
    title: {
        fontSize: 24,
        fontWeight: 'bold',
        color: '#0F172A',
        marginBottom: 16,
    },
    button: {
        backgroundColor: '#2563EB',
        paddingVertical: 12,
        paddingHorizontal: 20,
        borderRadius: 8,
        alignItems: 'center',
    },
    buttonPressed: {
        backgroundColor: '#1D4ED8',
    },
    buttonText: {
        color: '#FFFFFF',
        fontSize: 16,
        fontWeight: '600',
    },
    loading: {
        margin: 16,
    },
    error: {
        color: '#DC2626',
        marginTop: 12,
    },
    note: {
        marginTop: 16,
        color: '#64748B',
    },
});