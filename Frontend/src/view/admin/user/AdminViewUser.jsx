export default function AdminViewUser({ user }) {
    return (
        <div className="bg-white border border-gray-200 rounded-2xl p-5 shadow-sm hover:shadow-lg transition-shadow duration-300">
            <div className="flex items-center justify-between mb-3">
                <h2 className="text-xl font-semibold text-gray-800">{user.username}</h2>
                <span className={`text-xs font-medium px-2 py-1 rounded-full 
                    ${user.role === 'ADMIN' ? 'bg-red-100 text-red-600' : 'bg-blue-100 text-blue-600'}`}>
                    {user.role}
                </span>
            </div>
            <div className="space-y-2 text-sm text-gray-700">
                <div className="flex justify-between">
                    <span className="font-medium text-gray-600">Email:</span>
                    <span>{user.email}</span>
                </div>
                <div className="flex justify-between">
                    <span className="font-medium text-gray-600">Telefon:</span>
                    <span>{user.phoneNumber}</span>
                </div>
                <div className="flex justify-between">
                    <span className="font-medium text-gray-600">Kraj:</span>
                    <span>{user.country}</span>
                </div>
            </div>
        </div>
    );
}
