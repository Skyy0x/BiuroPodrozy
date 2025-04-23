export default function formatDate([year, month, day]) {
    const date = new Date(year, month - 1, day);
    return date.toLocaleDateString('pl', {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
    });
}
