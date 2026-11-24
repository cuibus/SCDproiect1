import { ScrollView, StyleSheet, Text, View } from 'react-native';

export default function PackagesTable({ packages }) {
    return (
        <View style={styles.tableWrapper}>
            <View style={[styles.row, styles.header]}>
                <Text style={[styles.cell, styles.headerText, styles.colId]}>ID</Text>
                <Text style={[styles.cell, styles.headerText, styles.colAddress]}>
                    Address
                </Text>
                <Text style={[styles.cell, styles.headerText, styles.colStatus]}>
                    Status
                </Text>
                <Text style={[styles.cell, styles.headerText, styles.colCourier]}>
                    Courier
                </Text>
            </View>

            <ScrollView>
                {packages.map((item) => (
                    <View key={item.id} style={styles.row}>
                        <Text style={[styles.cell, styles.colId]}>{item.id}</Text>
                        <Text style={[styles.cell, styles.colAddress]}>
                            {item.deliveryAddress}
                        </Text>
                        <Text style={[styles.cell, styles.colStatus]}>{item.status}</Text>
                        <Text style={[styles.cell, styles.colCourier]}>
                            {item.courier?.name ?? '-'}
                        </Text>
                    </View>
                ))}
            </ScrollView>
        </View>
    );
}

const styles = StyleSheet.create({
    tableWrapper: {
        marginTop: 20,
        flex: 1,
        backgroundColor: '#FFFFFF',
        borderRadius: 8,
        borderWidth: 1,
        borderColor: '#E2E8F0',
        overflow: 'hidden',
    },
    row: {
        flexDirection: 'row',
        borderBottomWidth: 1,
        borderBottomColor: '#E2E8F0',
        alignItems: 'center',
    },
    header: {
        backgroundColor: '#EFF6FF',
    },
    headerText: {
        fontWeight: '700',
        color: '#1E3A8A',
    },
    cell: {
        paddingVertical: 10,
        paddingHorizontal: 6,
        fontSize: 13,
        color: '#1E293B',
    },
    colId: {
        flex: 0.6,
    },
    colAddress: {
        flex: 2,
    },
    colStatus: {
        flex: 1.2,
    },
    colCourier: {
        flex: 1.2,
    },
});