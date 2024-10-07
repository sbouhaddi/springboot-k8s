import axios from 'axios';

const BASE_URL = 'http://localhost:8080'; // Your Spring Boot API base URL

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
  const response = await axios.get(`${BASE_URL}/api/bookmarks`, {
    params: { page, query }
  });
  return response.data.bookmarks;
};

export const createBookmark = async (data: CreateBookmarkRequest) => {
  const response = await axios.post(`${BASE_URL}/api/bookmarks`, data);
  return response.data;
};

export const deleteBookmark = async (id: string) => {
  await axios.delete(`${BASE_URL}/api/bookmarks?id=${id}`);
};

export const updateBookmark = async (id: string, data: CreateBookmarkRequest) => {
  const response = await axios.put(`${BASE_URL}/api/bookmarks/${id}`, data);
  return response.data;
};
