import axios from 'axios';


const getApiUrl = process.env.NEXT_PUBLIC_CLIENT_SIDE_API_BASE_URL;

// Define the type for the response data
export interface Bookmark {
  id: string;
  description: string;
  url: string;
  createdAt: string;
}

interface CreateBookmarkRequest {
  description: string;
  url: string;
}

export const getBookmarks = async (page = 1, query = ''): Promise<Bookmark[]> => {
  const response = await axios.get(`${getApiUrl}/api/bookmarks`, {
    params: { page, query }
  });
  return response.data.bookmarks;
};

export const createBookmark = async (data: CreateBookmarkRequest) => {
  const response = await axios.post(`${getApiUrl}/api/bookmarks`, data);
  return response.data;
};

export const deleteBookmark = async (id: string) => {
  await axios.delete(`${getApiUrl}/api/bookmarks?id=${id}`);
};

export const updateBookmark = async (id: string, data: CreateBookmarkRequest) => {
  const response = await axios.put(`${getApiUrl}/api/bookmarks/${id}`, data);
  return response.data;
};
