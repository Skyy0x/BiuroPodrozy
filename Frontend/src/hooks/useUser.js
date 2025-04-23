import api from "../services/axios";

const useUser = () => {
    const getUser = async () => {
        try {
            const res = await api.get("/user");
            return res.data.data.user;
        } catch (err) {
            console.error("Fetch user error:", err);
            if (err.response?.status === 401) {
                sessionStorage.removeItem("token");
                window.location.href = "/login";
            }
            throw err;
        }
    };

    return { getUser };
};

export default useUser;
