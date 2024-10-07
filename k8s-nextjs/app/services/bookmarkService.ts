import axios from 'axios'; 

const BASE_URL = 'http://localhost:8080'; // Your Spring Boot API base URL

// Define the type for the response data
export interface Bookmark {
  id: string;
  title: string;
  url: string;
}

export const getBookmarks = async (page = 1, query = ''): Promise<Bookmark[]> => {
  try {
    const response = await axios.get(`${BASE_URL}/api/bookmarks`, {
      params: { page, query }
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching bookmarks:', error);
    throw error;
  }
};
