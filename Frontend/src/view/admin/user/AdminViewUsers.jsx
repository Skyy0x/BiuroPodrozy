import { useEffect, useState } from "react";
import api from "../../../services/axios";
import Error from "../../../component/Error";
import AdminViewUser from "./AdminViewUser";

export default function AdminViewUsers() {
    const [users, setUsers] = useState([]);
    const [error, setError] = useState("");

    useEffect(() => {
        api.get('/manage/users')
            .then((response) => {
                setUsers(response.data.data.users || []);
            })
            .catch(() => {
                setError('Nie udało się pobrać użytkowników.');
            });
    }, []);

    if (error) return <Error message={error} />;

    return (
        <div className="p-6 max-w-4xl mx-auto">
            <h1 className="text-2xl font-semibold mb-6">Użytkownicy</h1>
            <div className="grid sm:grid-cols-2 lg:grid-cols-3 gap-4">
                {users.map((user) => (
                    <AdminViewUser key={user.username} user={user} />
                ))}
            </div>
        </div>
    );
}
