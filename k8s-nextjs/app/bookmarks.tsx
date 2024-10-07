'use client';

import { useState, useEffect } from 'react';
import { getBookmarks } from './services/bookmarkService'

interface Bookmark {
  id: string;
  description: string;
  url: string;
  createdAt: string;
}

const Bookmarks: React.FC = () => {
  const [bookmarks, setBookmarks] = useState<Bookmark[]>([]);
  const [page, setPage] = useState<number>(1);
  const [query, setQuery] = useState<string>('');
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchBookmarks = async () => {
      setLoading(true);
      try {
        const data = await getBookmarks(page, query);
        setBookmarks(data.bookmarks);
      } catch (error) {
        console.error('Failed to fetch bookmarks');
      } finally {
        setLoading(false);
      }
    };
    fetchBookmarks();
  }, [page, query]);

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    setPage(1); // Reset to page 1 when searching
  };

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Bookmarks</h1>

      <form onSubmit={handleSearch} className="mb-4">
        <input
          type="text"
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          placeholder="Search bookmarks..."
          className="border p-2 mr-2 rounded"
        />
        <button type="submit" className="bg-blue-500 text-white p-2 rounded">
          Search
        </button>
      </form>

      {loading ? (
        <p className="text-center">Loading bookmarks...</p>
      ) : bookmarks.length === 0 ? (
        <p className="text-center">No bookmarks found.</p>
      ) : (
        <div className="overflow-x-auto">
          {/* Table for displaying bookmarks */}
          <table className="min-w-full bg-white shadow-md rounded-lg overflow-hidden">
            <thead className="bg-blue-500 text-white">
              <tr>
                <th className="py-3 px-4 text-left">ID</th>
                <th className="py-3 px-4 text-left">Title</th>
                <th className="py-3 px-4 text-left">URL</th>
                <th className="py-3 px-4 text-left">Created At</th>
              </tr>
            </thead>
            <tbody>
              {bookmarks.map((bookmark) => (
                <tr key={bookmark.id} className="border-b hover:bg-gray-50 transition">
                  <td className="py-3 px-4">{bookmark.id}</td>
                  <td className="py-3 px-4 font-medium">{bookmark.description}</td>
                  <td className="py-3 px-4">
                    <a href={bookmark.url} target="_blank" rel="noopener noreferrer" className="text-blue-600 underline">
                      {bookmark.url}
                    </a>
                  </td>
                  <td className="py-3 px-4">{new Date(bookmark.createdAt).toLocaleDateString()}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {/* Pagination */}
      <div className="mt-6 flex justify-center">
        <button
          onClick={() => setPage((prev) => Math.max(prev - 1, 1))}
          disabled={page === 1}
          className="bg-gray-200 p-2 rounded mr-2 hover:bg-gray-300 transition disabled:opacity-50"
        >
          Previous
        </button>
        <button
          onClick={() => setPage((prev) => prev + 1)}
          className="bg-gray-200 p-2 rounded hover:bg-gray-300 transition"
        >
          Next
        </button>
      </div>
    </div>
  );
};

export default Bookmarks;
